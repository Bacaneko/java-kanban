import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    void shouldGetDefaultReturnsTaskManager() {
        TaskManager taskManager = Managers.getDefault();
        assertNotNull(taskManager, "TaskManager не должен быть null");
        assertInstanceOf(InMemoryTaskManager.class, taskManager,
                "Должен возвращать экземпляр InMemoryTaskManager");
    }

    @Test
    void shouldGetDefaultHistoryManagerReturnsHistoryManager() {
        HistoryManager historyManager = Managers.getDefaultHistoryManager();
        assertNotNull(historyManager, "HistoryManager не должен быть null");
        assertInstanceOf(InMemoryHistoryManager.class, historyManager,
                "Должен возвращать экземпляр InMemoryHistoryManager");
    }
}