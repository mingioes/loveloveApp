package networkprogramming;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FriendMatchPanel extends JPanel {
    private JButton likeButton;
    private JLabel profileLabel;
    private Client client;
    private String currentProfile;

    public FriendMatchPanel(Client client) {
        this.client = client;
        client.setFriendMatchPanel(this);
        setLayout(new BorderLayout());

        profileLabel = new JLabel("User Profile Here", SwingConstants.CENTER);
        add(profileLabel, BorderLayout.CENTER);

        likeButton = new JButton("Heart");
        likeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentProfile != null) {
                    System.out.println("Liked profile: " + currentProfile); // 디버그 메시지
                    client.likeFriend(currentProfile);
                }
            }
        });
        add(likeButton, BorderLayout.SOUTH);
    }

    public void updateProfile(String profileName) {
        currentProfile = profileName;
        profileLabel.setText(profileName);
        System.out.println("Profile label updated to: " + profileName); // 디버그 메시지
    }
}
