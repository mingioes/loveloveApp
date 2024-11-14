package networkprogramming;

import javax.swing.*;
import java.awt.*;

public class ProfileAndFriendsPanel extends JPanel {
    private DefaultListModel<String> friendListModel;

    public ProfileAndFriendsPanel() {
        setLayout(new BorderLayout());

        JLabel profileLabel = new JLabel("My Profile", SwingConstants.CENTER);
        add(profileLabel, BorderLayout.NORTH);

        friendListModel = new DefaultListModel<>();
        JList<String> friendList = new JList<>(friendListModel);
        add(new JScrollPane(friendList), BorderLayout.CENTER);
    }

    public DefaultListModel<String> getFriendListModel() {
        return friendListModel;
    }
}
