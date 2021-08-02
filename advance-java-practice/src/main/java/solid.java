import java.util.*;

//Single responsibilty
class Single {
    // it takes care of only one responsibilty
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

//Open-close
interface IOpen{
    int operation();
}

class AdditionClose implements IOpen {
    int a;
    int b;

    public AdditionClose(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public int operation() {
        return a + b;
    }
}

class SubtractionClose implements IOpen {
    int a;
    int b;

    public SubtractionClose(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public int operation() {
        return a - b;
    }
}

// this class closed for modification, but it is open for modification through other classes
class OpenClose {
    public void calculate(IOpen open) {
        System.out.println(open.operation());
    }
}

// Liskov Substitution
interface ILiskovOne {
    int getAge(int a);
}

interface ILiskovTwo {
    int getHeight(int a);
}

interface ILiskovMain extends ILiskovOne, ILiskovTwo {
    @Override
    public int getAge(int a);

    @Override
    public int getHeight(int a);
}

// constructor need to be same
class Liskov1 implements ILiskovMain {
    String name;
    public Liskov1(String name) {
        this.name = name;
    }

    @Override
    public int getAge(int age) {
        return age;
    }

    @Override
    public int getHeight(int height) {
        return height;
    }

}

// constructor need to be same
class Liskov2 implements ILiskovTwo {
    String name;
    public Liskov2(String name) {
        this.name = name;
    }

    @Override
    public int getHeight(int height) {
        return height;
    }
}

// Interface Segregation
interface IOrder {
    int getSingleDiscount();
    int getMultipleDiscount()  throws Exception;
}

class B2BusinessOrder implements IOrder {

    @Override
    public int getSingleDiscount() {
        return 10;
    }

    @Override
    public int getMultipleDiscount() {
        return 100;
    }
}

class B2CustomerOrder implements IOrder {
    @Override
    public int getMultipleDiscount() throws Exception {
        throw new Exception("Not Eligible");
    }

    @Override
    public int getSingleDiscount() {
        return 0;
    }
}

// segragating interface. B2CustomerOrder does not need getSingleDiscount so we can create separate interface
// keeping this principle, we can avoid Exception in getMultipleDiscount
interface INormalOrder {
    int getSingleDiscount();
}

interface IB2BOrder {
    int getMultipleDiscount();
}

class B2BusinessOrder2 implements INormalOrder, IB2BOrder {
    @Override
    public int getSingleDiscount() {
        return 10;
    }

    @Override
    public int getMultipleDiscount() {
        return 100;
    }
}

class B2CustomerOrder2 implements INormalOrder {
    @Override
    public int getSingleDiscount() {
        return 10;
    }
}

// Dependency Inversion
class Insurance {
    private DBRepository dbRepository;

    public Insurance(DBRepository dbRepository){
        this.dbRepository = dbRepository;
    }

    public void save(){
        this.dbRepository.save();
    }
}

class DBRepository {
    public void save() {
        // some code
    }
}

// above code is coupled. we need to decouple it by introducting abstraction (interface)

interface IRepository {
    String save();
}

class MongoDBRepository implements IRepository {
    @Override
    public String save() {
        return "Saved";
    }
}

class AddInsurance {
    private IRepository iRepository;

    public AddInsurance(IRepository iRepository){
        this.iRepository = iRepository;
    }

    public String save() {
       return this.iRepository.save();
    }
}


public class solid {
    public static void main(String[] arg){
        System.out.println("Single Responsibility");
        Single single = new Single();
        single.setName("Single");
        System.out.println(single.getName());

        System.out.println("------------------------");
        System.out.println("Open/Close");
        OpenClose openClose= new OpenClose();
        // open to extension via other classes, but closed for modification
        openClose.calculate(new AdditionClose(2,3));
        openClose.calculate(new SubtractionClose(3,2));

        System.out.println("------------------------");
        System.out.println("Liskov Substitution");

        List<ILiskovMain> liskovMainList = new ArrayList<>();
        liskovMainList.add(new Liskov1("liskov1"));
        for(ILiskovMain liskovMain: liskovMainList){
            System.out.println(liskovMain.getAge(1));
            System.out.println(liskovMain.getHeight(2));
        }

        // here we are using Liskov1 (Because it can substitute from ILiskovMain (main) to ILiskovTwo (subtype))
        List<ILiskovTwo> liskovTwoMainList = new ArrayList<>();
        liskovTwoMainList.add(new Liskov1("liskov1"));
        liskovTwoMainList.add(new Liskov2("liskov2"));
        for(ILiskovTwo iLiskovTwo: liskovTwoMainList){
            System.out.println(iLiskovTwo.getHeight(1));
        }

        System.out.println("------------------------");
        System.out.println("Interface Segregation");

        B2BusinessOrder2 b2BusinessOrder2 = new B2BusinessOrder2();
        System.out.println(b2BusinessOrder2.getMultipleDiscount());
        System.out.println(b2BusinessOrder2.getSingleDiscount());

        // segragated the interface
        B2CustomerOrder2 b2CustomerOrder2 = new B2CustomerOrder2();
        System.out.println(b2CustomerOrder2.getSingleDiscount());

        System.out.println("------------------------");
        System.out.println("Dependency Inversion");

        // we can pass any repository/any data access layer.
        // it would be easier to test
        AddInsurance addInsurance = new AddInsurance(new MongoDBRepository());
        System.out.println(addInsurance.save());
    }
}
