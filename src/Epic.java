import java.util.ArrayList;
import java.util.List;

public class Epic extends Task{
    List<Task> subTasks;

    public Epic(String name, String description) {
        super(name, description);
        subTasks = new ArrayList<>();
    }
    public  List<Task> getSubTasks(){
        return subTasks;
    }
    public void addSubTask(SubTask subTask) {
        subTasks.add(subTask);
    }
}
