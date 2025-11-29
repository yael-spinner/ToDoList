import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
public class TaskService {
    private final TaskRepository taskRepository;
    public TaskService(TaskRepository taskRepository){
        this.taskRepository=taskRepository;
    }
    public Task createNewTask(String title,String description){
        return taskRepository.add(title,description);
    }
    public Task getTaskById(int id)
    {
        return taskRepository.getById(id);
    }
    public Task markTaskDone(int id){
        Task task=taskRepository.getById(id);
        if (task!=null&&task.getStatus() != TaskStatus.DONE){
            task.setStatus(taskStatus.DONE);
            return taskRepository.update(task);
        }
        return null;
    }
    public List<Task> searchTasks(String searchText){
        String lowerCaseSearchText=searchText.toLowerCase();
        return taskRepository.listAll().stream().filter(task->task.getTitel().toLowerCase()contains(lowerCaseSearchText)||
                task.getDescription().toLowerCase().contains(lowerCaseSearchText)
        ).collect(Collectors.toList());
    }
    public List<Task> getTasksSortedByStatus() {
        List<Task> allTasks = taskRepository.litAll();
        allTasks.sort(Comparator.comparing(task->{
            switch (task.getStatus()){
                case NEW:
                    return 1;
                case IN_PROGRESS:
                    return 2;
                case DONE:
                    return 3;
                default:
                    return 4;
            }
        }));
        return allTasks;
    }
}
