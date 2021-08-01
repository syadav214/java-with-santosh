import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class Student implements Serializable {
    private String name;
    private int age;
    private int standard;

    Student(String name, int age, int standard){
        this.name = name;
        this.age = age;
        this.standard = standard;
    }
}

public class serializableTest {
    public static void main(String[] arg) {
        serielizedAndDesizerid();
    }
    static void serielizedAndDesizerid() {
        Student st = new Student("santo", 3,4);
        String fileName = "C:\\Users\\SantoshkumarYadav\\Documents\\testing\\advanceJava\\src\\main\\java\\test.txt";

        //serialize - Object (Student) to bytes
        FileOutputStream fileOut = null;
        ObjectOutputStream objOut = null;
        try {
            fileOut = new FileOutputStream(fileName);
            objOut = new ObjectOutputStream(fileOut);
            objOut.writeObject(st);;

            objOut.close();
            fileOut.close();

            System.out.println("Serialized:"+ st);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        //deserilize - file bytes to Object (Student)
        FileInputStream fileIn = null;
        ObjectInputStream objIn = null;
        try {
            fileIn = new FileInputStream(fileName);
            objIn = new ObjectInputStream(fileIn);
            Student st2 = (Student) objIn.readObject();
            System.out.println("Deserilized:" + st2);
            fileIn.close();
            objIn.close();

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
