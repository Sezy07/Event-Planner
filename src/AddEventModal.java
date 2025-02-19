import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class AddEventModal extends JDialog {
    public AddEventModal(EventListPanel eventListPanel) {
        setTitle("Add Event");
        setSize(300, 200);
        setLayout(new GridLayout(0, 1));

        JTextField nameField = new JTextField();
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Deadline", "Meeting"});
        JButton addButton = new JButton("Add");

        add(new JLabel("Event Name:"));
        add(nameField);
        add(new JLabel("Event Type:"));
        add(typeBox);
        add(addButton);

        addButton.addActionListener(e -> {
            String name = nameField.getText();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            LocalDateTime now = LocalDateTime.now();
            Event event;

            if (typeBox.getSelectedItem().equals("Deadline")) {
                event = new Deadline(name, now.plusDays(5));
            } else {
                event = new Meeting(name, now.plusDays(2), now.plusDays(2).plusHours(1), "Default Location");
            }

            eventListPanel.addEvent(event);
            dispose();
        });

        setVisible(true);
    }
}