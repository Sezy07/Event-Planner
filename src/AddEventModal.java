import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class AddEventModal extends JDialog {
    public AddEventModal(EventListPanel eventListPanel) {

        // Sets the Title and the Size for the Window
        setTitle("Add Event");
        setSize(300, 200);
        setLayout(new GridLayout(0, 1));

        JTextField nameField = new JTextField();
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Deadline", "Meeting"});
        JButton addButton = new JButton("Add");

        // Adds the labels for the Events
        add(new JLabel("Event Name:"));
        add(nameField);
        add(new JLabel("Event Type:"));
        add(typeBox);
        add(addButton);

        // Gives the action for the add button when it is pressed
        addButton.addActionListener(e -> {
            String name = nameField.getText();

            // Gives an error if the box is left empty
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            LocalDateTime now = LocalDateTime.now();
            Event event;

            // Creates the event if it is either a deadline or event
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