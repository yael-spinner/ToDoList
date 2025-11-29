import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



public class TaskRepository {
    private final String FILE_PATH = "task.json";
    private Map<Integer,Task> taskMap;
    private final Gson gson=new Gson();
    private AtomicInteger nextId;

    public TaskRepository(){
        taskMap=loadTaskFromFile();
        nextId=new AtomicInteger(taskMap.keySet().stream().mapToInt(v-> v).max().orElse(0)+1);
    }
    private Map<Integer,Task> loadTaskFromFile(){
        try (FileReader reader=new FileReader(FILE_PATH)){
            Type type=new TypeToken<Map<Integer,Task>>(){}.getType();
            Map<Integer,Task>loadedMap=gson.fromJson(reader,type);
            return loadedMap !=null ?loadedMap:new HashMap<>();
        }catch(IOException e){
            return new HashMap<>();
        }
    }
    private void saveTasksToFile(){
        try (FileWriter writer=new FileWriter(FILE_PATH)){
            gson.toJson(taskMap,writer);
        }catch (IOException e){
            System.err.println("Error writing tasks to file: "+e.getMessage());
        }
    }
    public Task add(String title ,String description){
        int id =nextId.getAndIncrement();
        Task newTask = new Task(id,title,description,TaskStatus.NEW);
        taskMap.put(id,newTask);
        saveTasksToFile();
        return newTask
    }
    public Task update(Task updateTask){
        if (taskMap.containsKey(updateTask.getId())){
            taskMap.put(updateTask.getId(),updateTask);
            saveTasksToFile();
            return updateTask;
        }
        return null
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
