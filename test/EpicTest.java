import model.Epic;
import model.SubTask;
import model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    private Epic epic;
    private SubTask subTask1;
    private SubTask subTask2;

    @BeforeEach
    public void beforeEach() {
        epic = new Epic("Test Epic", "This is an epic description");
        epic.setId(1);

        subTask1 = new SubTask("SubTask 1", "Description 1", TaskStatus.NEW, 1);
        subTask1.setId(2);

        subTask2 = new SubTask("SubTask 2", "Description 2", TaskStatus.IN_PROGRESS, 1);
        subTask2.setId(3);
    }

    @Test
    public void testConstructorAndGetters() {

        assertEquals("Test Epic", epic.getName());
        assertEquals("This is an epic description", epic.getDescription());
        assertEquals(TaskStatus.NEW, epic.getStatus());
        assertEquals(1, epic.getId());
        assertTrue(epic.getSubTasks().isEmpty());
    }


    @Test
    public void testAddAndGetSubTasks() {
        epic.addSubTask(subTask1);
        epic.addSubTask(subTask2);

        List<SubTask> subTasks = epic.getSubTasks();
        assertEquals(2, subTasks.size());
        assertTrue(subTasks.contains(subTask1));
        assertTrue(subTasks.contains(subTask2));

        subTasks.remove(subTask1);
        assertEquals(2, epic.getSubTasks().size());
    }

    @Test
    public void testDeleteSubtask() {
        epic.addSubTask(subTask1);
        epic.addSubTask(subTask2);

        epic.deleteSubtask(subTask1);
        assertEquals(1, epic.getSubTasks().size());
        assertFalse(epic.getSubTasks().contains(subTask1));
        assertTrue(epic.getSubTasks().contains(subTask2));
    }

    @Test
    public void testRemoveAllSubtasks() {
        epic.addSubTask(subTask1);
        epic.addSubTask(subTask2);

        epic.removeAllSubtasks();
        assertTrue(epic.getSubTasks().isEmpty());
    }

    @Test
    public void testEqualsAndHashCodeSameObjects() {
        Epic sameEpic = new Epic("Test Epic", "This is an epic description");
        sameEpic.setId(1);
        sameEpic.addSubTask(subTask1);
        epic.addSubTask(subTask1);

        assertEquals(epic, sameEpic);
        assertEquals(epic.hashCode(), sameEpic.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeDifferentObjects() {
        Epic differentEpic = new Epic("Different Epic", "Different description");
        differentEpic.setId(2);
        differentEpic.addSubTask(subTask2);

        assertNotEquals(epic, differentEpic);
        assertNotEquals(epic.hashCode(), differentEpic.hashCode());
    }

    @Test
    public void testEqualsNullAndDifferentClass() {
        assertNotEquals(null, epic);
        assertNotEquals(new Object(), epic);
    }

    @Test
    public void testEqualsDifferentFields() {

        Epic diffName = new Epic("Diff Name", "This is an epic description");
        diffName.setId(1);
        assertFalse(epic.equals(diffName));

        Epic diffDesc = new Epic("Test Epic", "Diff desc");
        diffDesc.setId(1);
        assertFalse(epic.equals(diffDesc));
    }
}