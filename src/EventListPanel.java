import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class EventListPanel extends JPanel {
    private ArrayList<Event> events;
    private JPanel displayPanel;
    private JComboBox<String> sortDropDown;
    private JCheckBox filterCompleted;
    private JButton addEventButton;

    public EventListPanel() {
        events = new ArrayList<>();
        setLayout(new BorderLayout());

        // Control Panel
        JPanel controlPanel = new JPanel();
        sortDropDown = new JComboBox<>(new String[]{"Sort by Name", "Sort by Date"});
        sortDropDown.addActionListener(e -> sortEvents());
        filterCompleted = new JCheckBox("Hide Completed");
        filterCompleted.addActionListener(e -> updateDisplay());
        addEventButton = new JButton("Add Event");
        addEventButton.addActionListener(e -> new AddEventModal(this));

        controlPanel.add(sortDropDown);
        controlPanel.add(filterCompleted);
        controlPanel.add(addEventButton);
        add(controlPanel, BorderLayout.NORTH);

        // Display Panel
        displayPanel = new JPanel();
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
        add(new JScrollPane(displayPanel), BorderLayout.CENTER);
    }

    public void addEvent(Event event) {
        events.add(event);
        updateDisplay();
    }

    private void sortEvents() {
        if (sortDropDown.getSelectedItem().equals("Sort by Name")) {
            events.sort(Comparator.comparing(Event::getName));
        } else {
            events.sort(Comparator.comparing(Event::getDateTime));
        }
        updateDisplay();
    }

    private void updateDisplay() {
        displayPanel.removeAll();
        for (Event event : events) {
            if (filterCompleted.isSelected() && event instanceof Completable && ((Completable) event).isComplete()) {
                continue;
            }
            displayPanel.add(new EventPanel(event));
        }
        displayPanel.revalidate();
        displayPanel.repaint();
    }
}