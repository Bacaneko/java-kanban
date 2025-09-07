package service;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.List;
import java.util.Map;

public interface TaskManager {
    Map<Integer, Task> getTasks();

    Map<Integer, SubTask> getSubtasks();

    Map<Integer, Epic> getEpics();

    void deleteTasks();

    void deleteSubtasks();

    void deleteEpics();

    Task getTask(int id);

    SubTask getSubtask(int id);

    Epic getEpic(int id);

    void addTask(Task task);

    void addSubtask(SubTask subTask);

    void addEpic(Epic epic);

    void updateTask(Task task);

    void updateSubTask(SubTask subtask);

    void updateEpic(Epic epic);

    void deleteTaskById(int taskId);

    void deleteSubtaskById(int subtaskId);

    void deleteEpicById(int epicId);

    List<SubTask> getSubtasksOfEpic(int epicId);
}
