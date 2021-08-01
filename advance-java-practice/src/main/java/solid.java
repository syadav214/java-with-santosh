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

interface LiskovMain extends ILiskovOne, ILiskovTwo {
    @Override
    public int getAge(int a);

    @Override
    public int getHeight(int a);
}

// constructor need to be same
class Liskov1 implements LiskovMain {
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


interface IParent {
    String getName();
}

class Child implements IParent {
    public String getName(){
        return "Child";
    }
}

class Child2 implements IParent {
    public String getName(){
        return "Child2";
    }
}

class RelationManager {
    public String getNameManager(IParent parent){
        return parent.getName();
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
        openClose.calculate(new AdditionClose(2,3));
        openClose.calculate(new SubtractionClose(3,2));

        System.out.println("------------------------");
        System.out.println("Liskov Substitution");

        List<LiskovMain> liskovMainList = new ArrayList<>();
        liskovMainList.add(new Liskov1("liskov1"));
        for(LiskovMain liskovMain: liskovMainList){
            System.out.println(liskovMain.getAge(1));
            System.out.println(liskovMain.getHeight(2));
        }

        // here we are using Liskov1 (Because it can be substitute from LiskovMain (main) to ILiskovTwo (subtype))
        List<ILiskovTwo> liskovTwoMainList = new ArrayList<>();
        liskovTwoMainList.add(new Liskov1("liskov1"));
        liskovTwoMainList.add(new Liskov2("liskov2"));
        for(ILiskovTwo iLiskovTwo: liskovTwoMainList){
            System.out.println(iLiskovTwo.getHeight(1));
        }

        System.out.println("------------------------");

        RelationManager relationManager = new RelationManager();
        System.out.println(relationManager.getNameManager(new Child()));
        System.out.println(relationManager.getNameManager(new Child2()));
    }


}
