import java.util.List;
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


}
