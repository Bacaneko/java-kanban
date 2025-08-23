import java.util.*;

public class TaskManager {
    private int tasksCounter = 0;
    private final Map<Integer, Task> taskList = new HashMap<>();
    private final Map<Integer, SubTask> subtaskList = new HashMap<>();
    private final Map<Integer, Epic> epicList = new HashMap<>();

    public Map<Integer, Task> getTasks() {
        return taskList;
    }

    public Map<Integer, SubTask> getSubtasks() {
        return subtaskList;
    }

    public Map<Integer, Epic> getEpics() {
        return epicList;
    }

    public void deleteTasks() {
        if (taskList.isEmpty()) {
            System.out.println("Список задач пуст. Нет задач к удалению");
            return;
        }
        taskList.clear();
    }

    public void deleteSubtasks() {
        if (subtaskList.isEmpty()) {
            System.out.println("Список подзадач пуст. Нет подзадач к удалению");
            return;
        }
        subtaskList.clear();
    }

    public void deleteEpics() {
        if (epicList.isEmpty()) {
            System.out.println("Список эпиков пуст. Нет эпиков к удалению");
            return;
        }
        for (Epic epic : epicList.values()) {
            for (SubTask subTask : epic.getSubTasks()) {
                subtaskList.remove(subTask.getId());
            }
        }
        epicList.clear();
    }

    public Task getTask(int id) {
        if (taskList.get(id) == null) {
            System.out.println("Задача не существует");
        }
        return taskList.get(id);
    }

    public SubTask getSubtask(int id) {
        if (subtaskList.get(id) == null) {
            System.out.println("Подзадача не существует");
        }
        return subtaskList.get(id);
    }

    public Task getEpic(int id) {
        if (epicList.get(id) == null) {
            System.out.println("Эпик не существует");
        }
        return epicList.get(id);
    }

    public void addTask(Task task) {
        if (task == null) {
            System.out.println("Задача не существует");
            return;
        }
        int id = generateId();
        task.setId(id);
        taskList.put(id, task);
    }
    // проверить целесообразность проверки типа Epic
    public void addSubtask(SubTask subTask) {
        if (subTask == null) {
            System.out.println("Подзадача не существует");
            return;
        }
        if (!epicList.containsKey(subTask.getEpicId()) || !(epicList.get(subTask.getEpicId()) instanceof Epic epic)) {
            System.out.println("Эпик для подзадачи " + subTask.getName() + " не существует");
            return;
        }
        int id = generateId();
        subTask.setId(id);
        epic.addSubTask(subTask);
        subtaskList.put(id, subTask);
        checkEpicSubTasks(epic);
    }

    public void addEpic(Epic epic) {
        if (epic == null) {
            System.out.println("Задачи не существует");
            return;
        }
        int id = generateId();
        epic.setId(id);
        epicList.put(id, epic);
    }

    public void updateTask(Task task) {
        if (task == null || !taskList.containsKey(task.getId())) {
            System.out.println("Нельзя обновить несуществующую задачу");
            return;
        }
        taskList.put(task.getId(), task);
    }

    public void updateSubTask(SubTask subtask) {
        if (subtask == null || !subtaskList.containsKey(subtask.getId())) {
            System.out.println("Нельзя обновить несуществующую задачу");
            return;
        }
        if (subtaskList.get(subtask.getId()).getEpicId() != subtask.getEpicId()) {
            System.out.println("Нельзя менять связь с эпиком");
            return;
        }
        subtaskList.put(subtask.getId(), subtask);
        checkEpicSubTasks(epicList.get(subtask.getEpicId()));
    }

    public void updateEpic(Epic epic) {
        if (epic == null || !epicList.containsKey(epic.getId())) {
            System.out.println("Нельзя обновить несуществующую задачу");
            return;
        }
        epicList.put(epic.getId(), epic);
        checkEpicSubTasks(epic);
    }


    public void deleteTaskById(int taskId) {
        if (!taskList.containsKey(taskId)) {
            System.out.println("Задача с ID " + taskId + " не существует");
            return;
        }
        taskList.remove(taskId);
    }

    public void deleteSubtaskById(int subtaskId) {
        if (!subtaskList.containsKey(subtaskId)) {
            System.out.println("Подзадача с ID " + subtaskId + " не существует");
            return;
        }
        SubTask subTask = subtaskList.get(subtaskId);
        Epic epic = epicList.get(subTask.getEpicId());
        if (epic != null) {
            epic.getSubTasks().remove(subTask);  // Удаляем объект
            checkEpicSubTasks(epic);
        }
        subtaskList.remove(subtaskId);
    }

    public void deleteEpicById(int epicId) {
        if (!epicList.containsKey(epicId)) {
            System.out.println("Эпик с ID " + epicId + " не существует");
            return;
        }
        Epic epic = epicList.get(epicId);
        for (SubTask subTask : new ArrayList<>(epic.getSubTasks())) {
            subtaskList.remove(subTask.getId());
        }
        epic.getSubTasks().clear();
        epicList.remove(epicId);
    }


    public List<SubTask> getSubtasksOfEpic(int epicId) {
        Epic epic = epicList.get(epicId);
        if (epic == null) {
            System.out.println("Эпик с ID " + epicId + " не существует");
            return new ArrayList<>();
        }
        return new ArrayList<>(epic.getSubTasks());
    }

    private int generateId() {
        return ++tasksCounter;
    }

    private void checkEpicSubTasks(Epic epic) {
        if (epic == null || epic.getSubTasks() == null) {
            System.out.println("Эпик не существует или не содержит подзадач");
            return;
        }
        if (epic.getSubTasks().isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
            return;
        }
        boolean allNew = epic.getSubTasks().stream().allMatch(st -> st.getStatus() == TaskStatus.NEW);
        boolean allDone = epic.getSubTasks().stream().allMatch(st -> st.getStatus() == TaskStatus.DONE);
        if (allNew) {
            epic.setStatus(TaskStatus.NEW);
        } else if (allDone) {
            epic.setStatus(TaskStatus.DONE);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }
}
