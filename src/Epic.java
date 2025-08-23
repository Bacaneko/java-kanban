import java.util.ArrayList;
import java.util.List;

public class Epic extends Task{
    private final List<SubTask> subTasks;

    public Epic(String name, String description) {
        super(name, description, TaskStatus.NEW);
        subTasks = new ArrayList<>();
    }

    public void deleteSubtaskById(int subId) {
        subTasks.remove(subId);
    }

    public void removeAllSubtasks() {
        subTasks.clear();
    }

    public  List<SubTask> getSubTasks(){
        return new ArrayList<>(subTasks);
    }
    public void addSubTask(SubTask subTask) {
        subTasks.add(subTask);
    }
}
