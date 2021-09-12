package immutable;
/**
 *
 * 多线程设计模式========不可变
 */
public final class Person {
    protected final int age;
    protected final String name;
    public Person(int age,String name){
        this.age=age;
        this.name=name;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}