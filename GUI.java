import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.io.*;

class Task implements Serializable {
    private static final long serialVersionUID = 1L;
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
    private TaskManager taskManager;
    private DefaultListModel<String> taskListModel;
    private JList<String> taskList;
    private JTextField taskInput;

    public GUI() {
        taskManager = new TaskManager();
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

        // Update the initial list
        refreshTaskList();
    }

    private void refreshTaskList() {
        taskListModel.clear();
        ArrayList<Task> allTasks = taskManager.getAllTasks();
        System.out.println("Refreshing task list. Current tasks: " + allTasks.size());
        for (Task task : allTasks) {
            taskListModel.addElement(task.toString());
        }
    }

    // Button Listeners
    private class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String description = taskInput.getText().trim();
            if (!description.isEmpty()) {
                taskManager.addTask(new Task(description));
                refreshTaskList();
                taskInput.setText("");
            }
        }
    }

    private class CompleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = taskList.getSelectedIndex();
            if (selectedIndex != -1) {
                taskManager.getTask(selectedIndex).markComplete();
                refreshTaskList();
            }
        }
    }

    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = taskList.getSelectedIndex();
            if (selectedIndex != -1) {
                taskManager.deleteTask(selectedIndex);
                refreshTaskList();
            }
        }
    }

    private class EditButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = taskList.getSelectedIndex();
            if (selectedIndex != -1) {
                String newDescription = JOptionPane.showInputDialog(
                    GUI.this, "Edit Task Description:", taskManager.getTask(selectedIndex).getDescription()
                );
                if (newDescription != null && !newDescription.trim().isEmpty()) {
                    taskManager.getTask(selectedIndex).editDescription(newDescription.trim());
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
