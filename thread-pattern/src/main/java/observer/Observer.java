package observer;
public class Observer {



    private Subject subject;
    public Observer(Subject subject){
        this.subject=subject;
    }
    /**
     *
     * 当主题的状态发生改变之后,这个方法将会被触发
     * @param state
     */
    public void subSquere(int state) {
        System.out.println(state);
    }


    /**
     * 取消订阅某种主题
     * @param subject
     */
    public void cancelTake(Subject subject){
        subject.removeObserver(this);
    }

    //订阅某种主题
    public void take(Subject subject){
        subject.addObserver(this);
    }
}
