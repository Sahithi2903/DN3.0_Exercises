class Task {
    private String taskId;
    private String taskName;
    private String status;

    public Task(String taskId, String taskName, String status) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.status = status;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId='" + taskId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

class TaskNode {
    Task task;
    TaskNode next;

    public TaskNode(Task task) {
        this.task = task;
        this.next = null;
    }
}

class TaskLinkedList {
    private TaskNode head;

    public TaskLinkedList() {
        this.head = null;
    }

    public void addTask(Task task) {
        TaskNode newNode = new TaskNode(task);
        if (head == null) {
            head = newNode;
        } else {
            TaskNode temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
    }

    public Task searchTask(String taskId) {
        TaskNode temp = head;
        while (temp != null) {
            if (temp.task.getTaskId().equals(taskId)) {
                return temp.task;
            }
            temp = temp.next;
        }
        return null;
    }

    public void traverseTasks() {
        TaskNode temp = head;
        while (temp != null) {
            System.out.println(temp.task);
            temp = temp.next;
        }
    }

    public void deleteTask(String taskId) {
        if (head == null) {
            return;
        }

        if (head.task.getTaskId().equals(taskId)) {
            head = head.next;
            return;
        }

        TaskNode temp = head;
        while (temp.next != null) {
            if (temp.next.task.getTaskId().equals(taskId)) {
                temp.next = temp.next.next;
                return;
            }
            temp = temp.next;
        }
    }
}

public class TaskManagement {
    public static void main(String[] args) {
        TaskLinkedList taskList = new TaskLinkedList();

        Task task1 = new Task("T001", "Design module", "Pending");
        Task task2 = new Task("T002", "Develop module", "In Progress");
        Task task3 = new Task("T003", "Test module", "Completed");

        taskList.addTask(task1);
        taskList.addTask(task2);
        taskList.addTask(task3);

        System.out.println("All Tasks:");
        taskList.traverseTasks();

        System.out.println("\nSearching for task with ID T002:");
        Task found = taskList.searchTask("T002");
        if (found != null) {
            System.out.println(found);
        } else {
            System.out.println("Task not found.");
        }

        System.out.println("\nDeleting task with ID T002:");
        taskList.deleteTask("T002");

        System.out.println("\nAll Tasks after deletion:");
        taskList.traverseTasks();
    }
}
