import model.Epic;
import model.SubTask;
import model.Task;
import model.TaskStatus;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private int tasksCounter = 0;
    private final Map<Integer, Task> taskList = new HashMap<>();
    private final Map<Integer, SubTask> subtaskList = new HashMap<>();
    private final Map<Integer, Epic> epicList = new HashMap<>();
    private final HistoryManager inMemoryHistoryManager;

    public InMemoryTaskManager() {
        inMemoryHistoryManager = Managers.getDefaultHistoryManager();
    }

    @Override
    public Map<Integer, Task> getTasks() {
        return taskList;
    }

    @Override
    public Map<Integer, SubTask> getSubtasks() {
        return subtaskList;
    }

    @Override
    public Map<Integer, Epic> getEpics() {
        return epicList;
    }


    @Override
    public void deleteTasks() {
        if (taskList.isEmpty()) {
            System.out.println("Список задач пуст. Нет задач к удалению");
            return;
        }
        taskList.clear();
    }

    @Override
    public void deleteSubtasks() {
        if (subtaskList.isEmpty()) {
            System.out.println("Список подзадач пуст. Нет подзадач к удалению");
            return;
        }
        for (SubTask value : subtaskList.values()) {
            Epic epic = epicList.get(value.getEpicId());
            epic.removeAllSubtasks();
            checkEpicSubTasks(epic);
        }
        subtaskList.clear();
    }

    @Override
    public void deleteEpics() {
        if (epicList.isEmpty()) {
            System.out.println("Список эпиков пуст. Нет эпиков к удалению");
            return;
        }
        subtaskList.clear();
        epicList.clear();
    }

    @Override
    public Task getTask(int id) {
        if (taskList.get(id) == null) {
            System.out.println("Задача не существует");
        }
        Task task = taskList.get(id);
        inMemoryHistoryManager.addTaskInHistory(task);
        return task;
    }

    @Override
    public SubTask getSubtask(int id) {
        if (subtaskList.get(id) == null) {
            System.out.println("Подзадача не существует");
        }

        SubTask subTask = subtaskList.get(id);
        inMemoryHistoryManager.addTaskInHistory(subTask);
        return subTask;
    }

    @Override
    public Epic getEpic(int id) {
        if (epicList.get(id) == null) {
            System.out.println("Эпик не существует");
        }

        Epic epic = epicList.get(id);
        inMemoryHistoryManager.addTaskInHistory(epic);
        return epic;
    }

    @Override
    public void addTask(Task task) {
        if (task == null) {
            System.out.println("Задача не существует");
            return;
        }
        int id = generateId();
        task.setId(id);
        taskList.put(id, task);
    }

    @Override
    public void addSubtask(SubTask subTask) {
        if (subTask == null) {
            System.out.println("Подзадача не существует");
            return;
        }
        if (!epicList.containsKey(subTask.getEpicId()) || epicList.get(subTask.getEpicId()) == null) {
            System.out.println("Эпик для подзадачи " + subTask.getName() + " не существует");
            return;
        }
        Epic epic = epicList.get(subTask.getEpicId());
        int id = generateId();
        subTask.setId(id);
        epic.addSubTask(subTask);
        subtaskList.put(id, subTask);
        checkEpicSubTasks(epic);
    }

    @Override
    public void addEpic(Epic epic) {
        if (epic == null) {
            System.out.println("Задачи не существует");
            return;
        }
        int id = generateId();
        epic.setId(id);
        epicList.put(id, epic);
    }

    @Override
    public void updateTask(Task task) {
        if (task == null || !taskList.containsKey(task.getId())) {
            System.out.println("Нельзя обновить несуществующую задачу");
            return;
        }
        taskList.put(task.getId(), task);
    }

    @Override
    public void updateSubTask(SubTask subtask) {
        if (subtask == null || !subtaskList.containsKey(subtask.getId())) {
            System.out.println("Нельзя обновить несуществующую задачу");
            return;
        }
        if (subtaskList.get(subtask.getId()).getEpicId() != subtask.getEpicId()) {
            System.out.println("Нельзя менять связь с эпиком");
            return;
        }
        Epic epic = epicList.get(subtask.getEpicId());
        epic.deleteSubtask(subtaskList.get(subtask.getId()));
        epic.addSubTask(subtask);
        subtaskList.put(subtask.getId(), subtask);
        checkEpicSubTasks(epic);
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epic == null || !epicList.containsKey(epic.getId())) {
            System.out.println("Нельзя обновить несуществующую задачу");
            return;
        }
        Epic oldEpic = epicList.get(epic.getId());
        if (epic.getSubTasks().size() != oldEpic.getSubTasks().size() || epic.getStatus() != oldEpic.getStatus()) {
            System.out.println("Нельзя обновлять внутренние данные вручную, не относящиеся к изменяемым");
            return;
        }
        oldEpic.setName(epic.getName());
        oldEpic.setDescription(epic.getDescription());
        checkEpicSubTasks(oldEpic);
    }

    @Override
    public void deleteTaskById(int taskId) {
        if (!taskList.containsKey(taskId)) {
            System.out.println("Задача с ID " + taskId + " не существует");
            return;
        }
        taskList.remove(taskId);
    }

    @Override
    public void deleteSubtaskById(int subtaskId) {
        if (!subtaskList.containsKey(subtaskId)) {
            System.out.println("Подзадача с ID " + subtaskId + " не существует");
            return;
        }
        SubTask subTask = subtaskList.get(subtaskId);
        Epic epic = epicList.get(subTask.getEpicId());
        if (epic != null) {
            epic.deleteSubtask(subTask);
            checkEpicSubTasks(epic);
        }
        subtaskList.remove(subtaskId);
    }

    @Override
    public void deleteEpicById(int epicId) {
        if (!epicList.containsKey(epicId)) {
            System.out.println("Эпик с ID " + epicId + " не существует");
            return;
        }
        Epic epic = epicList.get(epicId);
        for (SubTask subTask : new ArrayList<>(epic.getSubTasks())) {
            subtaskList.remove(subTask.getId());
        }
        epic.removeAllSubtasks();
        epicList.remove(epicId);
    }


    @Override
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

    public void resetCounter() {
        tasksCounter = 0;
    }

    public List<Task> getHistory() {
        return inMemoryHistoryManager.getHistory();
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
