import java.util.ArrayList;
import java.util.Scanner;

class Task {
    private String description;
    private boolean isComplete;

    public Task(String description) {
        this.description = description;
        this.isComplete = false;
    }

    public String getDescription() {
        return description;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void markComplete() {
        isComplete = true;
    }

    public void editDescription(String newDescription) {
        this.description = newDescription;
    }

    @Override
    public String toString() {
        return (isComplete ? "[Done] " : "[ ] ") + description;
    }
}

public class ToDoList {
    private ArrayList<Task> tasks;
    private Scanner scanner;

    public ToDoList() {
        tasks = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void addTask() {
        System.out.print("Enter task description: ");
        String description = scanner.nextLine();
        tasks.add(new Task(description));
        System.out.println("Task added!");
    }

    public void viewTasks() {
        System.out.println("\nYour To-Do List:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    public void markTaskComplete() {
        viewTasks();
        System.out.print("\nEnter the task number to mark as complete: ");
        int taskNum = scanner.nextInt();
        scanner.nextLine(); // clear the buffer
        if (taskNum > 0 && taskNum <= tasks.size()) {
            tasks.get(taskNum - 1).markComplete();
            System.out.println("Task marked as complete!");
        } else {
            System.out.println("Invalid task number.");
        }
    }

    public void deleteTask() {
        viewTasks();
        System.out.print("\nEnter the task number to delete: ");
        int taskNum = scanner.nextInt();
        scanner.nextLine(); // clear the buffer
        if (taskNum > 0 && taskNum <= tasks.size()) {
            tasks.remove(taskNum - 1);
            System.out.println("Task deleted!");
        } else {
            System.out.println("Invalid task number.");
        }
    }

    public void editTask() {
        viewTasks();
        System.out.print("\nEnter the task number to edit: ");
        int taskNum = scanner.nextInt();
        scanner.nextLine(); // clear the buffer
        if (taskNum > 0 && taskNum <= tasks.size()) {
            System.out.print("Enter the new description: ");
            String newDescription = scanner.nextLine();
            tasks.get(taskNum - 1).editDescription(newDescription);
            System.out.println("Task updated!");
        } else {
            System.out.println("Invalid task number.");
        }
    }

    public void menu() {
        while (true) {
            System.out.println("\nTo-Do List Menu:");
            System.out.println("1. Add task");
            System.out.println("2. View tasks");
            System.out.println("3. Mark task as complete");
            System.out.println("4. Delete task");
            System.out.println("5. Edit task");
            System.out.println("6. Exit");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // clear the buffer

            switch (choice) {
                case 1 -> addTask();
                case 2 -> viewTasks();
                case 3 -> markTaskComplete();
                case 4 -> deleteTask();
                case 5 -> editTask();
                case 6 -> {
                    System.out.println("Exiting To-Do List. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        ToDoList toDoList = new ToDoList();
        toDoList.menu();
    }
}
