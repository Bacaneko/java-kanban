import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    private Task task;

    @BeforeEach
    public void beforeEach() {
        task = new Task("Test Task", "This is a description", TaskStatus.NEW);
        task.setId(1);
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals("Test Task", task.getName());
        assertEquals("This is a description", task.getDescription());
        assertEquals(TaskStatus.NEW, task.getStatus());
        assertEquals(1, task.getId());
    }

    @Test
    public void testSetters() {
        task.setName("Updated Name");
        task.setDescription("Updated Description");
        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setId(2);

        assertEquals("Updated Name", task.getName());
        assertEquals("Updated Description", task.getDescription());
        assertEquals(TaskStatus.IN_PROGRESS, task.getStatus());
        assertEquals(2, task.getId());
    }

    @Test
    public void testEqualsAndHashCodeSameObjects() {
        Task sameTask = new Task("Test Task", "This is a description", TaskStatus.NEW);
        sameTask.setId(1);

        assertEquals(task, sameTask);
        assertEquals(task.hashCode(), sameTask.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeDifferentObjects() {
        Task differentTask = new Task("Different Task", "Different description", TaskStatus.DONE);
        differentTask.setId(2);

        assertNotEquals(task, differentTask);
        assertNotEquals(task.hashCode(), differentTask.hashCode());
    }

    @Test
    public void testEqualsNullAndDifferentClass() {
        assertNotEquals(null, task);
        assertNotEquals(new Object(), task);
    }

    @Test
    public void testEqualsDifferentFields() {
        Task diffName = new Task("Diff Name", "This is a description", TaskStatus.NEW);
        diffName.setId(1);
        assertNotEquals(task, diffName);

        Task diffDesc = new Task("Test Task", "Diff desc", TaskStatus.NEW);
        diffDesc.setId(1);
        assertNotEquals(task, diffDesc);

        Task diffStatus = new Task("Test Task", "This is a description", TaskStatus.DONE);
        diffStatus.setId(1);
        assertNotEquals(task, diffStatus);

        Task diffId = new Task("Test Task", "This is a description", TaskStatus.NEW);
        diffId.setId(3);
        assertNotEquals(task, diffId);
    }
}