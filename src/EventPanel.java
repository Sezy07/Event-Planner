import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

// Gives the event details in the EventListPanel
public class EventPanel extends JPanel {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
    private Event event;
    private JButton completeButton;

    // Constructor that takes an event and the event list panel
    public EventPanel(Event event, EventListPanel eventListPanel) {
        this.event = event;
        setLayout(new GridLayout(0, 1));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel nameLabel = new JLabel("Event: " + event.getName());
        JLabel timeLabel = new JLabel("Date/Time: " + event.getDateTime().format(FORMATTER));

        add(nameLabel);
        add(timeLabel);

        // If the event is a meeting, gives the additional information
        if (event instanceof Meeting) {
            Meeting meeting = (Meeting) event;
            add(new JLabel("Ends at: " + meeting.getEndDateTime().format(FORMATTER)));
            add(new JLabel("Location: " + meeting.getLocation()));
        }

        //  Displays the complete button
        if (event instanceof Completable) {
            Completable completableEvent = (Completable) event;
            completeButton = new JButton("Complete");

            // Disables the complete button if the button is already pressed
            if (completableEvent.isComplete()) {
                completeButton.setEnabled(false);
            } else {
                completeButton.addActionListener(e -> {
                    completableEvent.complete();
                    eventListPanel.markEventComplete(event);
                    completeButton.setEnabled(false);
                });
            }

            add(completeButton);
        }
    }
}