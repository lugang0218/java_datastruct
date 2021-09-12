package observer.thread;

public class Observer {


    private SubjectRunnable subjectRunnable;


    public void setSubjectRunnable(SubjectRunnable subjectRunnable){
        this.subjectRunnable=subjectRunnable;
    }
    /**
     *
     * event 就是一种状态值
     */
    public void onEvent(Event event){
        if(event.getMessage()==null){
            System.out.println("线程"+event.getThread().getName()+"正常执行完毕");
        }
        else{
            System.out.println("线程"+event.getThread().getName()+"执行出错");
            System.out.println(event.getMessage().throwable.getMessage());
        }
    }
}
