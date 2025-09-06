import model.Task;

import model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    private InMemoryHistoryManager historyManager;
    private Task task1;
    private Task task2;
    private Task task3;
    private Task task4;
    private Task task5;
    private Task task6;
    private Task task7;
    private Task task8;
    private Task task9;
    private Task task10;
    private Task task11;

    @BeforeEach
    void setUp() {
        historyManager = new InMemoryHistoryManager();

        task1 = new Task("Task 1", "Desc 1", TaskStatus.NEW);
        task2 = new Task("Task 2", "Desc 2", TaskStatus.NEW);
        task3 = new Task("Task 3", "Desc 3", TaskStatus.NEW);
        task4 = new Task("Task 4", "Desc 4", TaskStatus.NEW);
        task5 = new Task("Task 5", "Desc 5", TaskStatus.NEW);
        task6 = new Task("Task 6", "Desc 6", TaskStatus.NEW);
        task7 = new Task("Task 7", "Desc 7", TaskStatus.NEW);
        task8 = new Task("Task 8", "Desc 8", TaskStatus.NEW);
        task9 = new Task("Task 9", "Desc 9", TaskStatus.NEW);
        task10 = new Task("Task 10", "Desc 10", TaskStatus.NEW);
        task11 = new Task("Task 11", "Desc 11", TaskStatus.NEW);
    }

    @Test
    void testAddTaskInHistoryLessThan10() {
        historyManager.addTaskInHistory(task1);
        historyManager.addTaskInHistory(task2);
        historyManager.addTaskInHistory(task3);

        List<Task> history = historyManager.getHistory();
        assertEquals(3, history.size());
        assertEquals(task1, history.get(0));
        assertEquals(task2, history.get(1));
        assertEquals(task3, history.get(2));
    }

    @Test
    void testAddTaskInHistoryExactly10() {
        historyManager.addTaskInHistory(task1);
        historyManager.addTaskInHistory(task2);
        historyManager.addTaskInHistory(task3);
        historyManager.addTaskInHistory(task4);
        historyManager.addTaskInHistory(task5);
        historyManager.addTaskInHistory(task6);
        historyManager.addTaskInHistory(task7);
        historyManager.addTaskInHistory(task8);
        historyManager.addTaskInHistory(task9);
        historyManager.addTaskInHistory(task10);

        List<Task> history = historyManager.getHistory();
        assertEquals(10, history.size());
        assertEquals(task1, history.get(0));
        assertEquals(task10, history.get(9));
    }

    @Test
    void testAddTaskInHistoryMoreThan10() {
        historyManager.addTaskInHistory(task1);
        historyManager.addTaskInHistory(task2);
        historyManager.addTaskInHistory(task3);
        historyManager.addTaskInHistory(task4);
        historyManager.addTaskInHistory(task5);
        historyManager.addTaskInHistory(task6);
        historyManager.addTaskInHistory(task7);
        historyManager.addTaskInHistory(task8);
        historyManager.addTaskInHistory(task9);
        historyManager.addTaskInHistory(task10);
        historyManager.addTaskInHistory(task11);

        List<Task> history = historyManager.getHistory();
        assertEquals(10, history.size());
        assertEquals(task2, history.get(0));
        assertEquals(task11, history.get(9));
    }

    @Test
    void testGetHistoryEmpty() {
        List<Task> history = historyManager.getHistory();
        assertTrue(history.isEmpty());
    }

    @Test
    void testAddDuplicateTasks() {
        historyManager.addTaskInHistory(task1);
        historyManager.addTaskInHistory(task1);

        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size());
        assertEquals(task1, history.get(0));
        assertEquals(task1, history.get(1));
    }
}