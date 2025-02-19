import javax.swing.*;
import java.awt.*;

public class EventPanel extends JPanel {
    private Event event;
    private JButton completeButton;

    public EventPanel(Event event) {
        this.event = event;
        setLayout(new GridLayout(0, 1));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel nameLabel = new JLabel("Event: " + event.getName());
        JLabel timeLabel = new JLabel("Date/Time: " + event.getDateTime().toString());

        add(nameLabel);
        add(timeLabel);

        if (event instanceof Meeting) {
            Meeting meeting = (Meeting) event;
            add(new JLabel("Ends at: " + meeting.getEndDateTime()));
            add(new JLabel("Location: " + meeting.getLocation()));
        }

        if (event instanceof Completable) {
            Completable completableEvent = (Completable) event;
            completeButton = new JButton("Complete");
            completeButton.addActionListener(e -> {
                completableEvent.complete();
                completeButton.setEnabled(false);
            });
            add(completeButton);
        }
    }
}