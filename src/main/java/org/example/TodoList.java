package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

public class TodoList {
    private final JTextField textInput;
    private final DefaultListModel<String> model = new DefaultListModel<>();
    private final JList<String> list = new JList<>(model);
    private Integer taskSelection = null;

    public TodoList() {
        JFrame frame = new JFrame("Liste de tâches");
        frame.setLayout(new BorderLayout());
        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);

        JPanel panelTodo = new JPanel(new BorderLayout());
        textInput = new JTextField(20);
        panelTodo.add(textInput, BorderLayout.NORTH);
        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting())
                taskSelection = list.getSelectedIndex();
        });
        panelTodo.add(new JScrollPane(list), BorderLayout.CENTER);
        frame.add(panelTodo, BorderLayout.CENTER);

        JPanel panel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Ajouter");
        addButton.addActionListener(e -> addTask());
        panel.add(addButton);
        JButton deleteButton = new JButton("Supprimer");
        deleteButton.addActionListener(e -> removeTask());
        JButton finishButton = new JButton("Terminer");
        finishButton.addActionListener(e -> finishTask());
        panel.add(deleteButton);
        panel.add(finishButton);
        frame.add(panel, BorderLayout.SOUTH);

        frame.setVisible(true);

    }

    private void addTask() {
        if (!textInput.getText().isEmpty()) {
            model.addElement(textInput.getText());
            textInput.setText("");
        }
    }

    private void removeTask() {
        if (taskSelection != null) {
            model.remove(taskSelection);
            taskSelection = null;
        }
    }

    private void finishTask() {
        if (taskSelection != null) {
            String task = model.getElementAt(taskSelection);
            if(task.contains("<html><s>")){
                task=task.replace("<html><s>","");
                task=task.replace("</s></html>","");
                model.setElementAt(task,taskSelection);
            }else {
                task = "<html><s>" + task + "</s></html>";
                model.setElementAt(task, taskSelection);
            }
            list.addListSelectionListener(e->{
                if (!e.getValueIsAdjusting())
                    taskSelection = list.getSelectedIndex();
            });
        }
    }

    public static void main(String[] args) {
        new TodoList();
    }
}
