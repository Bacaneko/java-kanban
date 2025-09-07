import org.junit.jupiter.api.Test;
import service.*;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    void shouldGetDefaultReturnsTaskManager() {
        TaskManager taskManager = Managers.getDefault();
        assertNotNull(taskManager, "service.TaskManager не должен быть null");
        assertInstanceOf(InMemoryTaskManager.class, taskManager,
                "Должен возвращать экземпляр service.InMemoryTaskManager");
    }

    @Test
    void shouldGetDefaultHistoryManagerReturnsHistoryManager() {
        HistoryManager historyManager = Managers.getDefaultHistoryManager();
        assertNotNull(historyManager, "service.HistoryManager не должен быть null");
        assertInstanceOf(InMemoryHistoryManager.class, historyManager,
                "Должен возвращать экземпляр service.InMemoryHistoryManager");
    }
}