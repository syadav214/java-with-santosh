import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

class Movie {
    String director;
    Integer revenue;
    String genre;
    String actorName;
    String movieName;
    Integer year;

    public String getDirector() {
        return director;
    }

    public Integer getRevenue() {
        return revenue;
    }

    public String getActorName() {
        return actorName;
    }

    public Integer getYear() {
        return year;
    }

    public Movie(String director, Integer revenue, String genre, String actorName, String movieName, Integer year) {
        this.director = director;
        this.revenue = revenue;
        this.genre = genre;
        this.actorName = actorName;
        this.movieName = movieName;
        this.year = year;
    }
}

public class MovieAnalysis {
    public static void main(String[] args) throws FileNotFoundException {
        FileReader filereader = new FileReader("data/movie_metadata.csv");
        CSVReader reader = new CSVReaderBuilder(filereader)
                .withSkipLines(1)
                .build();

        List<Movie> list = new ArrayList<>();
        try {
            String[] nextRecord;
            while ((nextRecord = reader.readNext()) != null) {
                list.add(new Movie(
                        nextRecord[1],
                        nextRecord[8].isEmpty() ? 0 : Integer.parseInt(nextRecord[8]),
                        nextRecord[9],
                        nextRecord[10],
                        nextRecord[11],
                        nextRecord[23].isEmpty() ? 0 :Integer.parseInt(nextRecord[23])
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        }

        if(list == null || list.size() == 0){
            System.out.println("Cannot process the file.");
            return;
        }

        Optional<Map.Entry<String, Long>> maxActionMap = list.stream()
                .filter(movie -> movie.genre.contains("Action"))
                .collect(Collectors.groupingBy(Movie::getActorName, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue));

        String actorWhoDoneMostAction = maxActionMap != null && maxActionMap.isPresent() ? maxActionMap.get().getKey() : "Not Found";

        System.out.print("Which actor / actress has done the most number of Action movies? => ");
        System.out.println(bold(actorWhoDoneMostAction));

        int totalRevenue = list.stream()
                .filter(movie -> movie.getDirector().equals("Barry Sonnenfeld")
                        && movie.getActorName().equals("Will Smith")
                        && movie.getYear() > 2000)
                .mapToInt(movie -> movie.getRevenue())
                .sum();

        // the csv file does not have Production house column, so used director column
        System.out.println("What is the total revenue of movies directed by \"Barry Sonnenfeld\"");
        System.out.print("that were released after the year 2000 and had the actor Will Smith in it? => " );
        System.out.println(bold(String.valueOf(totalRevenue)));

    }

    static final String SET_PLAIN_TEXT = "\033[0;0m";
    static final String SET_BOLD_TEXT = "\033[0;1m";
    static String bold(String str) {
        return (SET_BOLD_TEXT + str + SET_PLAIN_TEXT);
    }
}

//execution time=> 440 ms