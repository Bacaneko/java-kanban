import model.SubTask;
import model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubTaskTest {
    private SubTask subTask;

    @BeforeEach
    public void beforeEach() {
        subTask = new SubTask("Test SubTask", "This is a subtask description", TaskStatus.IN_PROGRESS, 1);
        subTask.setId(2);
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals("Test SubTask", subTask.getName());
        assertEquals("This is a subtask description", subTask.getDescription());
        assertEquals(TaskStatus.IN_PROGRESS, subTask.getStatus());
        assertEquals(2, subTask.getId());
        assertEquals(1, subTask.getEpicId());
    }

    @Test
    public void testSettersFromTask() {
        subTask.setName("Updated SubTask");
        subTask.setDescription("Updated Description");
        subTask.setStatus(TaskStatus.DONE);
        subTask.setId(3);

        assertEquals("Updated SubTask", subTask.getName());
        assertEquals("Updated Description", subTask.getDescription());
        assertEquals(TaskStatus.DONE, subTask.getStatus());
        assertEquals(3, subTask.getId());
    }

    @Test
    public void testEqualsAndHashCodeSameObjects() {
        SubTask sameSubTask = new SubTask("Test SubTask", "This is a subtask description", TaskStatus.IN_PROGRESS, 1);
        sameSubTask.setId(2);

        assertEquals(subTask, sameSubTask);
        assertEquals(subTask.hashCode(), sameSubTask.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeDifferentObjects() {
        SubTask differentSubTask = new SubTask("Different SubTask", "Different description", TaskStatus.DONE, 2);
        differentSubTask.setId(3);
        assertNotEquals(subTask, differentSubTask);
        assertNotEquals(subTask.hashCode(), differentSubTask.hashCode());
    }

    @Test
    public void testEqualsNullAndDifferentClass() {
        assertNotEquals(null, subTask);
        assertNotEquals(new Object(), subTask);
    }

    @Test
    public void testEqualsDifferentFields() {
        SubTask diffName = new SubTask("Diff Name", "This is a subtask description", TaskStatus.IN_PROGRESS, 1);
        diffName.setId(2);
        assertNotEquals(subTask, diffName);

        SubTask diffDesc = new SubTask("Test SubTask", "Diff desc", TaskStatus.IN_PROGRESS, 1);
        diffDesc.setId(2);
        assertNotEquals(subTask, diffDesc);

        SubTask diffStatus = new SubTask("Test SubTask", "This is a subtask description", TaskStatus.DONE, 1);
        diffStatus.setId(2);
        assertNotEquals(subTask, diffStatus);

        SubTask diffId = new SubTask("Test SubTask", "This is a subtask description", TaskStatus.IN_PROGRESS, 1);
        diffId.setId(4);
        assertNotEquals(subTask, diffId);
    }
}