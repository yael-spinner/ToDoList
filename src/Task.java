public class Task {
private int id;
private String title;
private String description;
private Status status;

    //Getters
    public int getId()
    {
        return id;
    }
    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
    public Status getStatus()
    {
        return status;
    }
    //Setters
    public void setTitle(String title){
        this.title=title;
    }
    public void setStatus(Status status){
        this.status=status;
    }
}


