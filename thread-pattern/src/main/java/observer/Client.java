package observer;

public class Client {
    public static void main(String[] args) {
        Subject subject=new Subject(0);
        Observer observer=new Observer(subject);
        Observer observer2=new Observer(subject);
        observer.take(subject);
        observer2.take(subject);
        subject.setState(2);
    }
}
