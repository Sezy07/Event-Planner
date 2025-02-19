import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

// Gives the panel that will handle displaying and interacting with events entered
public class EventListPanel extends JPanel {
    private ArrayList<Event> events;
    private HashMap<Event, Boolean> completionStatus; // Stores completion status
    private JPanel displayPanel;
    private JComboBox<String> sortDropDown;
    private JCheckBox filterCompleted, filterDeadlines, filterMeetings, removeFilters;
    private JButton addEventButton;

    //
    public EventListPanel() {
        events = new ArrayList<>();
        completionStatus = new HashMap<>();
        setLayout(new BorderLayout());

        // Control Panel
        JPanel controlPanel = new JPanel();

        // This is the dropdown to sort the order of events
        sortDropDown = new JComboBox<>(new String[]{"Sort by Name", "Sort by Date", "Sort by Reverse Name"});
        sortDropDown.addActionListener(e -> sortEvents());

        // Creates the checkboxes for the Filters
        filterCompleted = new JCheckBox("Hide Completed");
        filterDeadlines = new JCheckBox("Hide Deadlines");
        filterMeetings = new JCheckBox("Hide Meetings");
        removeFilters = new JCheckBox("Remove All Filters");

        // Updates the display based on the filters selected
        filterCompleted.addActionListener(e -> updateDisplay());
        filterDeadlines.addActionListener(e -> updateDisplay());
        filterMeetings.addActionListener(e -> updateDisplay());
        removeFilters.addActionListener(e -> resetFilters());


        addEventButton = new JButton("Add Event");
        addEventButton.addActionListener(e -> new AddEventModal(this));

        // Adds these to the control panel
        controlPanel.add(sortDropDown);
        controlPanel.add(filterCompleted);
        controlPanel.add(filterDeadlines);
        controlPanel.add(filterMeetings);
        controlPanel.add(removeFilters);
        controlPanel.add(addEventButton);
        add(controlPanel, BorderLayout.NORTH);

        displayPanel = new JPanel();
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
        add(new JScrollPane(displayPanel), BorderLayout.CENTER);
    }

    // Methods that adds the new event and then updates the display
    public void addEvent(Event event) {
        events.add(event);
        completionStatus.put(event, event instanceof Completable && ((Completable) event).isComplete());
        updateDisplay();
    }

    // This is the sorting of events based on filters selected
    private void sortEvents() {
        String selectedSort = (String) sortDropDown.getSelectedItem();
        if ("Sort by Name".equals(selectedSort)) {
            events.sort(Comparator.comparing(Event::getName));
        } else if ("Sort by Date".equals(selectedSort)) {
            events.sort(Comparator.comparing(Event::getDateTime));
        } else if ("Sort by Reverse Name".equals(selectedSort)) {
            events.sort(Comparator.comparing(Event::getName).reversed());
        }
        updateDisplay();
    }

    // Updates the display based on current filters
    private void updateDisplay() {
        displayPanel.removeAll();
        for (Event event : events) {
            if (filterCompleted.isSelected() && completionStatus.getOrDefault(event, false)) {
                continue;
            }
            if (filterDeadlines.isSelected() && event instanceof Deadline) {
                continue;
            }
            if (filterMeetings.isSelected() && event instanceof Meeting) {
                continue;
            }

            EventPanel eventPanel = new EventPanel(event, this);
            displayPanel.add(eventPanel);
        }
        displayPanel.revalidate();
        displayPanel.repaint();
    }

    private void resetFilters() {
        filterCompleted.setSelected(false);
        filterDeadlines.setSelected(false);
        filterMeetings.setSelected(false);
        updateDisplay();
    }

    public void markEventComplete(Event event) {
        if (event instanceof Completable) {
            completionStatus.put(event, true);
        }
    }
}