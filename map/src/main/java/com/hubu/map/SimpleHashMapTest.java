package com.hubu.map;
import java.util.Objects;
public class SimpleHashMapTest {
    public static void main(String[] args) {
        SimpleHashMap<Person, String> map = new SimpleHashMap<>(16);
        map.put(new Person(21,"lugang"),"hello");
        map.put(new Person(22,"lugang"),"hello");
        map.put(new Person(23,"lugang"),"hello");
        map.put(new Person(24,"lugang"),"hello");
        Person person=new Person(21,"lugang");
        map.put(new Person(25,"lugang"),"hello");
        map.get(person);
    }
    static class Person{
        public int age;
        public String name;
        public Person(int age,String name){
            this.age=age;
            this.name=name;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return age == person.age && Objects.equals(name, person.name);
        }
        @Override
        public int hashCode() {
            return Objects.hash(age, name);
        }
    }
}
