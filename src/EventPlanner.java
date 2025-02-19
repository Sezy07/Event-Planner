import javax.swing.*;
import java.awt.*;

public class EventPlanner {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Event Calendar");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);

            EventListPanel eventListPanel = new EventListPanel();
            addDefaultEvents(eventListPanel);

            frame.add(eventListPanel, BorderLayout.CENTER);
            frame.setVisible(true);
        });
    }

    static void addDefaultEvents(EventListPanel events) {
        events.addEvent(new Deadline("Project Deadline", java.time.LocalDateTime.now().plusDays(7)));
        events.addEvent(new Meeting("Team Meeting", java.time.LocalDateTime.now().plusDays(2),
                java.time.LocalDateTime.now().plusDays(2).plusHours(1), "Conference Room"));
    }
}
