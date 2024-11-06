import java.io.*;
import java.util.ArrayList;

public class TaskManager {
    private ArrayList<Task> tasks;
    private static final String SAVE_FILE = "tasks.dat";

    public TaskManager() {
        tasks = new ArrayList<>();
        loadTasks();
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

    public void addTask(Task task) {
        tasks.add(task);
        saveTasks();
    }

    public void deleteTask(int index) {
        tasks.remove(index);
        saveTasks();
    }

    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    private void saveTasks() {
        try {
            File file = new File(SAVE_FILE);
            if (!file.exists()) {
                file.createNewFile();
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(tasks);
                System.out.println("Tasks saved successfully. Count: " + tasks.size());
            }
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadTasks() {
        File file = new File(SAVE_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                tasks = (ArrayList<Task>) ois.readObject();
                System.out.println("Tasks loaded successfully. Count: " + tasks.size());
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading tasks: " + e.getMessage());
                e.printStackTrace();
                // If there's an error loading, start with a fresh list
                tasks = new ArrayList<>();
            }
        } else {
            System.out.println("No existing tasks file found. Starting with empty list.");
            tasks = new ArrayList<>();
        }
    }
} 

