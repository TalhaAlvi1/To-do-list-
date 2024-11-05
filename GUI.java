import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

public class GUI extends JFrame {
    private ArrayList<Task> tasks;
    private DefaultListModel<String> taskListModel;
    private JList<String> taskList;
    private JTextField taskInput;

    public GUI() {
        tasks = new ArrayList<>();
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        setTitle("To-Do List");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Task input field
        taskInput = new JTextField(20);
        JButton addButton = new JButton("Add Task");
        addButton.addActionListener(new AddButtonListener());

        JPanel inputPanel = new JPanel();
        inputPanel.add(taskInput);
        inputPanel.add(addButton);

        // Buttons for actions
        JButton completeButton = new JButton("Mark Complete");
        JButton deleteButton = new JButton("Delete Task");
        JButton editButton = new JButton("Edit Task");

        completeButton.addActionListener(new CompleteButtonListener());
        deleteButton.addActionListener(new DeleteButtonListener());
        editButton.addActionListener(new EditButtonListener());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(completeButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);

        // Layout setup
        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(taskList), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addTaskWithAnimation(Task task) {
        taskListModel.addElement(task.toString());
        taskList.setSelectedIndex(taskListModel.size() - 1); // Select the new task
        fadeIn(taskListModel.size() - 1); // Start fade-in animation for the new task
    }

    private void fadeIn(int index) {
        Timer timer = new Timer(30, new ActionListener() {
            float opacity = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                opacity += 0.1f;
                if (opacity >= 1) {
                    opacity = 1;
                    ((Timer) e.getSource()).stop(); // Stop timer when opacity is 1
                }
                taskList.repaint();
            }
        });
        timer.start();
    }

    private void deleteTaskWithAnimation(int index) {
        Timer timer = new Timer(30, new ActionListener() {
            float opacity = 1;

            @Override
            public void actionPerformed(ActionEvent e) {
                opacity -= 0.1f;
                if (opacity <= 0) {
                    opacity = 0;
                    tasks.remove(index);
                    taskListModel.remove(index);
                    ((Timer) e.getSource()).stop(); // Stop timer when opacity is 0
                }
                taskList.repaint();
            }
        });
        timer.start();
    }

    private void refreshTaskList() {
        taskListModel.clear();
        for (Task task : tasks) {
            taskListModel.addElement(task.toString());
        }
    }

    // Button Listeners
    private class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String description = taskInput.getText().trim();
            if (!description.isEmpty()) {
                Task newTask = new Task(description);
                tasks.add(newTask);
                addTaskWithAnimation(newTask); // Use animation for adding task
                taskInput.setText("");
            }
        }
    }

    private class CompleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = taskList.getSelectedIndex();
            if (selectedIndex != -1) {
                tasks.get(selectedIndex).markComplete();
                refreshTaskList();
            }
        }
    }

    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = taskList.getSelectedIndex();
            if (selectedIndex != -1) {
                deleteTaskWithAnimation(selectedIndex); // Use animation for deleting task
            }
        }
    }

    private class EditButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = taskList.getSelectedIndex();
            if (selectedIndex != -1) {
                String newDescription = JOptionPane.showInputDialog(
                    GUI.this, "Edit Task Description:", tasks.get(selectedIndex).getDescription()
                );
                if (newDescription != null && !newDescription.trim().isEmpty()) {
                    tasks.get(selectedIndex).editDescription(newDescription.trim());
                    refreshTaskList();
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUI toDoListGUI = new GUI();
            toDoListGUI.setVisible(true);
        });
    }
}
