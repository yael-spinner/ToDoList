import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository=taskRepository;
    }
    public TaskRepository getTaskRepository() {
        return taskRepository;
    }

    public Task createNewTask(String title,String description){
        return taskRepository.add(title,description);
    }

    public Task getTaskById(int id)
    {
        return taskRepository.getById(id);
    }

    public Task markTaskAsDone(int id){
        Task task = taskRepository.getById(id);
        if (task !=null && task.getStatus() != Status.DONE){
            task.setStatus(Status.DONE);
            return taskRepository.update(task);
        }
        return null;
    }
    public List<Task> searchTasks(String searchText){
        String lowerCaseSearchText=searchText.toLowerCase();
        return taskRepository.listAll().stream().filter(task->task.getTitle().toLowerCase().contains(lowerCaseSearchText)||
                task.getDescription().toLowerCase().contains(lowerCaseSearchText)
        ).collect(Collectors.toList());
    }
    public List<Task> getTasksSortedByStatus() {
        List<Task> allTasks = taskRepository.listAll();
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
    public Task updateTaskStatus(int id,  Status newStatus) {
        Task task = taskRepository.getById(id);

        if (task != null&&newStatus != null) {
                task.setStatus(newStatus);
            return taskRepository.update(task);
        }
        return null;
    }

}

