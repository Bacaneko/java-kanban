public class TaskUtil {

    private TaskUtil() {
    }

    //Calculates the epic's status
    public static void checkEpicSubTasks(Epic epic) {
        if (epic == null || epic.subTasks == null) {
            System.out.println("Эпик не существует или не содержит подзадач");
            return;
        }
        if (epic.subTasks.isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
            return;
        }
        boolean allNew = epic.subTasks.stream().allMatch(st -> st.getStatus() == TaskStatus.NEW);
        boolean allDone = epic.subTasks.stream().allMatch(st -> st.getStatus() == TaskStatus.DONE);
        if (allNew) {
            epic.setStatus(TaskStatus.NEW);
        } else if (allDone) {
            epic.setStatus(TaskStatus.DONE);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }
}
