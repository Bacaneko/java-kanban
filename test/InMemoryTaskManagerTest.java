import model.Epic;
import model.SubTask;
import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.*;
import service.InMemoryTaskManager;
import service.Managers;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTaskManagerTest {

    private static InMemoryTaskManager inMemoryTaskManager = null;
    private final static ArrayList<Task> listTestTasks = new ArrayList<>();
    private final static ArrayList<SubTask> listTestSubtasks = new ArrayList<>();
    private final static ArrayList<Epic> listTestEpics = new ArrayList<>();

    @BeforeEach
    public  void beforeAll() {
        inMemoryTaskManager = (InMemoryTaskManager) Managers.getDefault();

        Task task1 = new Task("Task 1", "Task description 1", TaskStatus.NEW); //id 1
        Task task2 = new Task("Task 2", "Task description 2", TaskStatus.IN_PROGRESS); //id 2
        Task task3 = new Task("Task 3", "Task description 3", TaskStatus.DONE); //id 3

        Epic epic1 = new Epic("Epic 1", "Epic description 1"); //id 4
        Epic epic2 = new Epic("Epic 2", "Epic description 2"); //id 5
        Epic epic3 = new Epic("Epic 3", "Epic description 3"); //id 6

        SubTask subTask1 = new SubTask("Subtask 1", "Subtask of epic 1", TaskStatus.NEW, 4); //id 7
        SubTask subTask2 = new SubTask("Subtask 2", "Subtask of epic 1", TaskStatus.NEW, 4); //id 8
        SubTask subTask3 = new SubTask("Subtask 3", "Subtask of epic 2", TaskStatus.NEW, 5); //id 9
        SubTask subTask4 = new SubTask("Subtask 4", "Subtask of epic 2", TaskStatus.IN_PROGRESS, 5); //id 10
        SubTask subTask5 = new SubTask("Subtask 5", "Subtask of epic 3", TaskStatus.DONE, 6); //id 11

        listTestTasks.add(task1); //id 0
        listTestTasks.add(task2); //id 1
        listTestTasks.add(task3); //id 2
        inMemoryTaskManager.addTask(task1);
        inMemoryTaskManager.addTask(task2);
        inMemoryTaskManager.addTask(task3);

        listTestEpics.add(epic1); //id 0
        listTestEpics.add(epic2); //id 1
        listTestEpics.add(epic3); //id 2
        inMemoryTaskManager.addEpic(epic1);
        inMemoryTaskManager.addEpic(epic2);
        inMemoryTaskManager.addEpic(epic3);

        listTestSubtasks.add(subTask1); //id 0
        listTestSubtasks.add(subTask2); //id 1
        listTestSubtasks.add(subTask3); //id 2
        listTestSubtasks.add(subTask4); //id 3
        listTestSubtasks.add(subTask5); //id 4
        inMemoryTaskManager.addSubtask(subTask1);
        inMemoryTaskManager.addSubtask(subTask2);
        inMemoryTaskManager.addSubtask(subTask3);
        inMemoryTaskManager.addSubtask(subTask4);
        inMemoryTaskManager.addSubtask(subTask5);
    }

    @AfterEach
    public void afterEach() {
        inMemoryTaskManager.resetCounter();
        inMemoryTaskManager.deleteTasks();
        inMemoryTaskManager.deleteSubtasks();
        inMemoryTaskManager.deleteEpics();
        inMemoryTaskManager = null;
        listTestTasks.clear();
        listTestSubtasks.clear();
        listTestEpics.clear();
    }

    @Test
    public void shouldGetTasks() {
        ArrayList<Task>  list = new ArrayList<>(inMemoryTaskManager.getTasks().values());
        Assertions.assertEquals(listTestTasks, list);
    }

    @Test
    public void shouldGetSubtasks() {
        ArrayList<SubTask>  list = new ArrayList<>(inMemoryTaskManager.getSubtasks().values());
        Assertions.assertEquals(listTestSubtasks, list);
    }

    @Test
    public void shouldGetEpics() {
        ArrayList<Epic>  list = new ArrayList<>(inMemoryTaskManager.getEpics().values());
        Assertions.assertEquals(listTestEpics, list);
    }

    @Test
    public void shouldDeleteTasks() {
        inMemoryTaskManager.deleteTasks();
        Assertions.assertEquals(0, inMemoryTaskManager.getTasks().size());
    }

    @Test
    public void shouldDeleteSubtasks() {
        inMemoryTaskManager.deleteSubtasks();
        Assertions.assertEquals(0, inMemoryTaskManager.getSubtasks().size());
    }

    @Test
    public void shouldDeleteEpics() {
        inMemoryTaskManager.deleteEpics();
        Assertions.assertEquals(0, inMemoryTaskManager.getEpics().size());
    }

    @Test
    public void shouldGetTask() {
        Task task = inMemoryTaskManager.getTask(1);
        Assertions.assertEquals(listTestTasks.getFirst(), task);
    }

    @Test
    public void shouldGetSubTask() {
        SubTask subTask = inMemoryTaskManager.getSubtask(7);
        Assertions.assertEquals(listTestSubtasks.getFirst(), subTask);
    }

    @Test
    public void shouldGetEpic() {
        Epic epic = inMemoryTaskManager.getEpic(4);
        Assertions.assertEquals(listTestEpics.getFirst(), epic);
    }

    @Test
    public void shouldAddTask() {
        Task task = new Task("Test task 1", "Description test task 1", TaskStatus.NEW);
        inMemoryTaskManager.addTask(task);
        Assertions.assertEquals(4, inMemoryTaskManager.getTasks().size());
        Assertions.assertEquals(task, inMemoryTaskManager.getTask(12));
    }

    @Test
    public void shouldAddSubtask() {
        SubTask subTask = new SubTask("Test subtask 1",
                "Description test subtask 1",
                TaskStatus.NEW,
                4);
        inMemoryTaskManager.addSubtask(subTask);

        Assertions.assertEquals(6, inMemoryTaskManager.getSubtasks().size());
        Assertions.assertEquals(subTask, inMemoryTaskManager.getSubtask(12));
    }

    @Test
    public void shouldAddEpic() {
        Epic epic = new Epic("Test epic 1", "Description test epic 1");
        inMemoryTaskManager.addEpic(epic);

        Assertions.assertEquals(4, inMemoryTaskManager.getEpics().size());
        Assertions.assertEquals(epic, inMemoryTaskManager.getEpic(12));
    }

    @Test
    public void shouldUpdateTask() {
        Task updatedTask = inMemoryTaskManager.getTask(1);

        updatedTask.setName("Changed name");
        updatedTask.setDescription("Changed description");
        updatedTask.setStatus(TaskStatus.DONE);
        inMemoryTaskManager.updateTask(updatedTask);

        Assertions.assertEquals("Changed name", inMemoryTaskManager.getTask(1).getName());
        Assertions.assertEquals("Changed description", inMemoryTaskManager.getTask(1).getDescription());
        Assertions.assertEquals(TaskStatus.DONE, inMemoryTaskManager.getTask(1).getStatus());
    }

    @Test
    public void shouldUpdateSubtask() {
        SubTask updatedSubTask = inMemoryTaskManager.getSubtask(7);

        updatedSubTask.setName("Changed name");
        updatedSubTask.setDescription("Changed description");
        updatedSubTask.setStatus(TaskStatus.DONE);
        inMemoryTaskManager.updateSubTask(updatedSubTask);

        Assertions.assertEquals("Changed name", inMemoryTaskManager.getSubtask(7).getName());
        Assertions.assertEquals("Changed description", inMemoryTaskManager.getSubtask(7).getDescription());
        Assertions.assertEquals(TaskStatus.DONE, inMemoryTaskManager.getSubtask(7).getStatus());
    }

    @Test
    public void shouldUpdateEpic() {
        Epic updatedEpic = inMemoryTaskManager.getEpic(4);

        updatedEpic.setName("Changed name");
        updatedEpic.setDescription("Changed description");
        inMemoryTaskManager.updateEpic(updatedEpic);

        Assertions.assertEquals("Changed name", inMemoryTaskManager.getEpic(4).getName());
        Assertions.assertEquals("Changed description", inMemoryTaskManager.getEpic(4).getDescription());
    }

    @Test
    public void shouldDeleteTaskById() {
        inMemoryTaskManager.deleteTaskById(1);

        Assertions.assertNull(inMemoryTaskManager.getTask(1));
    }

    @Test
    public void shouldDeleteSubtaskById() {
        inMemoryTaskManager.deleteSubtaskById(7);

        Assertions.assertNull(inMemoryTaskManager.getSubtask(7));
    }

    @Test
    public void shouldDeleteEpicById() {
        inMemoryTaskManager.deleteEpicById(4);

        Assertions.assertNull(inMemoryTaskManager.getEpic(4));
    }

    @Test
    public void shouldGetSubtasksOfEpic() {
        ArrayList<SubTask> testListSubtasks = new ArrayList<>();
        testListSubtasks.add(listTestSubtasks.get(0));
        testListSubtasks.add(listTestSubtasks.get(1));

        Assertions.assertEquals(testListSubtasks, inMemoryTaskManager.getSubtasksOfEpic(4));
    }

    @Test
    public void shouldGetHistoryWith3Tasks() {
        inMemoryTaskManager.getTask(1);
        inMemoryTaskManager.getSubtask(7);
        inMemoryTaskManager.getEpic(4);

        List<Task> history = inMemoryTaskManager.getHistory();
        Assertions.assertEquals(3, history.size());
        Assertions.assertEquals(inMemoryTaskManager.getEpic(4), history.get(2));
    }

    @Test
    public void shouldGetHistoryWith11TasksAndDuplicates() {
        inMemoryTaskManager.getTask(1); // when 11 element added this will delete
        Task taskWithId2 = inMemoryTaskManager.getTask(2);
        Task taskWithId3 = inMemoryTaskManager.getTask(3);
        inMemoryTaskManager.getSubtask(7);
        inMemoryTaskManager.getSubtask(8);
        inMemoryTaskManager.getSubtask(9); // id 5 will be id 4
        inMemoryTaskManager.getSubtask(10);
        inMemoryTaskManager.getSubtask(11);
        inMemoryTaskManager.getEpic(4);
        inMemoryTaskManager.getEpic(5);
        inMemoryTaskManager.getTask(3); // 11 element with expected id 10 will be id 9

        List<Task> history = inMemoryTaskManager.getHistory();

        Assertions.assertEquals(taskWithId2, history.getFirst());
        Assertions.assertEquals(10, history.size());
        Assertions.assertEquals(taskWithId3, history.get(1));
        Assertions.assertEquals(taskWithId3, history.get(9));
    }

    @Test
    public void shouldUpdateEpicStatusAfterAddSubtaskIN_PROGRESS() {
        Epic epic = inMemoryTaskManager.getEpic(6);
        Assertions.assertEquals(TaskStatus.DONE, epic.getStatus());

        SubTask newSubTask = new SubTask("New Subtask", "Desc", TaskStatus.IN_PROGRESS, 6);
        inMemoryTaskManager.addSubtask(newSubTask);
        Assertions.assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus());
    }

    @Test
    public void shouldUpdateEpicStatus_IN_PROGRESS_AfterUpdateSubtask() {
        SubTask subTask = inMemoryTaskManager.getSubtask(7);
        Epic epic = inMemoryTaskManager.getEpic(4);

        subTask.setStatus(TaskStatus.DONE);
        inMemoryTaskManager.updateSubTask(subTask);
        Assertions.assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus());
    }

    @Test
    public void shouldUpdateEpicStatusAfterDeleteSubtask() {
        inMemoryTaskManager.deleteSubtaskById(7);
        Epic epic = inMemoryTaskManager.getEpic(4);
        Assertions.assertEquals(TaskStatus.NEW, epic.getStatus());
    }

    @Test
    public void shouldNotAddNullTask() {
        inMemoryTaskManager.addTask(null);
        Assertions.assertEquals(3, inMemoryTaskManager.getTasks().size());
    }

    @Test
    public void shouldNotUpdateNonExistingTask() {
        Task nonExistingTask = new Task("Non", "Desc", TaskStatus.NEW);
        nonExistingTask.setId(999);
        inMemoryTaskManager.updateTask(nonExistingTask);
        Assertions.assertNull(inMemoryTaskManager.getTask(999));
    }

    @Test
    public void shouldDeleteEpicsAndClearSubtasks() {
        inMemoryTaskManager.deleteEpics();
        Assertions.assertEquals(0, inMemoryTaskManager.getEpics().size());
        Assertions.assertEquals(0, inMemoryTaskManager.getSubtasks().size());
    }

    @Test
    public void shouldNotAddSubtaskWithInvalidEpicId() {
        SubTask invalidSubTask = new SubTask("Invalid", "Desc", TaskStatus.NEW, 999);
        Assertions.assertDoesNotThrow(() -> inMemoryTaskManager.addSubtask(invalidSubTask));
        Assertions.assertEquals(5, inMemoryTaskManager.getSubtasks().size());
    }
}
