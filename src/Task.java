public class Task {
private int id;
private String title;
private String description;
private Status status;

    public Task(int id, String title, String description, Status status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }
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
    @Override
    public String toString() {
        return "Task [id=" + id + ", title='" + title + "', description='" + description + "', status=" + status + "]";
    }
}


