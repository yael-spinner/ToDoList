import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;



public class TaskRepository {
    private final String FILE_PATH = "task.data";
    private Map<Integer,Task> taskMap;
    private transient AtomicInteger nextId;

    public TaskRepository(){
        taskMap=loadTaskFromFile();
        nextId=new AtomicInteger(taskMap.keySet().stream().mapToInt(v-> v).max().orElse(0)+1);
    }
    private Map<Integer,Task> loadTaskFromFile(){
        try (ObjectInputStream ois=new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            Map<Integer, Task> loadMap = (Map<Integer, Task>) ois.readObject();
            return loadMap != null ? loadMap : new HashMap<>();
        }catch (FileNotFoundException e){
            return new HashMap<>();
        }catch(IOException | ClassNotFoundException e){
            System.err.println("Error loading tasks: " + e.getMessage());
            return new HashMap<>();
        }
    }
    private void saveTasksToFile(){
        try (ObjectOutputStream oos =new ObjectOutputStream(new FileOutputStream(FILE_PATH))){
            oos.writeObject(taskMap);
        }catch (IOException e){
            System.err.println("Error writing tasks to file: "+e.getMessage());
        }
    }
    public Task add(String title ,String description){
        int id =nextId.getAndIncrement();
        Task newTask = new Task(id,title,description,Status.NEW);
        taskMap.put(id,newTask);
        saveTasksToFile();
        return newTask;
    }
    public Task update(Task updateTask){
        if (taskMap.containsKey(updateTask.getId())){
            taskMap.put(updateTask.getId(),updateTask);
            saveTasksToFile();
            return updateTask;
        }
        return null;
    }
    public boolean delete(int id){
        if (taskMap.containsKey(id)){
            taskMap.remove(id);
            saveTasksToFile();
            return true;
        }
        return false;
    }
    public Task getById(int id){
        return taskMap.get(id);
    }
    public List<Task>listAll(){
        return new ArrayList<>(taskMap.values());
    }
}
