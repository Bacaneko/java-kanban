import model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> tasksHistory = new ArrayList<>(10);


    @Override
    public void addTaskInHistory(Task task) {
        if (task == null) {
            System.out.println("Нельзя добавить в историю просмотренных задач несуществующую задачу!");
            return;
        }
        if (tasksHistory.size() == 10) {
            tasksHistory.removeFirst();
            tasksHistory.add(task);
        } else {
            tasksHistory.add(task);
        }
    }

    @Override
    public List<Task> getHistory() {
        return tasksHistory;
    }
}
