import java.util.*;

public class TaskManager {
    private int tasksCounter = 0;
    private final Map<Integer, Task> taskList = new HashMap<>();

    public Map<Integer, Task> getTasks() {
        return taskList;
    }

    public void deleteTasks() {
        if (taskList.isEmpty()) {
            System.out.println("Список задач пуст. Нет задач к удалению");
            return;
        }
        taskList.clear();
        tasksCounter = 0;
    }

    public Task getTask(int id) {
        return taskList.get(id);
    }

    public void addTask(Task task) {
        if (task == null) {
            System.out.println("Задачи не существует");
            return;
        }
        int id = generateId();
        task.setId(id);
        taskList.put(id,task);
    }

    public void addSubTask(int epicId, SubTask subTask) {
        if (subTask == null) {
            System.out.println("Подзадачи не существует");
            return;
        }
        if (!taskList.containsKey(epicId) || !(taskList.get(epicId) instanceof Epic)) {
            System.out.println("Эпик для подзадачи " + subTask.getName() + " не существует");
            return;
        }
            int id = generateId();
            subTask.setId(id);
            subTask.setEpicId(epicId);
            Epic epic = (Epic) taskList.get(epicId);
            epic.addSubTask(subTask);
            taskList.put(id, subTask);
            TaskUtil.checkEpicSubTasks(epic);
        }

    public void updateTask(Task task) {
        if (task == null || !taskList.containsKey(task.getId())) {
            System.out.println("Нельзя обновить несуществующую задачу");
            return;
        }
        Task updatedTask  = taskList.get(task.getId());
        updatedTask.setName(task.getName());
        updatedTask.setDescription(task.getDescription());
        updatedTask.setStatus(task.getStatus());
        if (task instanceof SubTask subTask) {
            if (!taskList.containsKey(subTask.getEpicId()) || !(taskList.get(subTask.getEpicId()) instanceof Epic)) {
                System.out.println("Эпик для подзадачи " + subTask.getName() + " не существует");
                return;
            }
            Epic epic = (Epic) taskList.get(subTask.getEpicId());
            TaskUtil.checkEpicSubTasks(epic);
        }
    }

    public void deleteTaskById(int taskId) {
        if (!taskList.containsKey(taskId)) {
            System.out.println("Задача не существует");
        }
        if (taskList.get(taskId) instanceof SubTask subTask) {
            if (!taskList.containsKey(subTask.getEpicId()) || !(taskList.get(subTask.getEpicId()) instanceof Epic)) {
                System.out.println("Эпик для подзадачи не существует.");
            }
            Epic epic = (Epic) taskList.get(subTask.getEpicId());
            epic.getSubTasks().remove(subTask);
            taskList.remove(taskId);
            TaskUtil.checkEpicSubTasks(epic); // Обновляем статус эпика
        } else {
            taskList.remove(taskId);
        }

    }

    public List<Task> getSubtasksOfEpic(int epicId) {
        Epic epic = (Epic) taskList.get(epicId);
        return epic.subTasks;
    }

    private int generateId() {
        return ++tasksCounter;
    }
}
