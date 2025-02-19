import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class EventPanel extends JPanel {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
    private Event event;
    private JButton completeButton;

    public EventPanel(Event event, EventListPanel eventListPanel) {
        this.event = event;
        setLayout(new GridLayout(0, 1));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel nameLabel = new JLabel("Event: " + event.getName());
        JLabel timeLabel = new JLabel("Date/Time: " + event.getDateTime().format(FORMATTER));

        add(nameLabel);
        add(timeLabel);

        if (event instanceof Meeting) {
            Meeting meeting = (Meeting) event;
            add(new JLabel("Ends at: " + meeting.getEndDateTime().format(FORMATTER)));
            add(new JLabel("Location: " + meeting.getLocation()));
        }

        if (event instanceof Completable) {
            Completable completableEvent = (Completable) event;
            completeButton = new JButton("Complete");

            // Check stored completion state
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