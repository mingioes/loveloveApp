package networkprogramming;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GroupChatPanel extends JPanel {
    private JTextArea groupChatArea;
    private JTextField groupChatInput;

    public GroupChatPanel() {
        setLayout(new BorderLayout());

        groupChatArea = new JTextArea();
        groupChatArea.setEditable(false);
        add(new JScrollPane(groupChatArea), BorderLayout.CENTER);

        groupChatInput = new JTextField();
        groupChatInput.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendGroupMessage();
            }
        });
        add(groupChatInput, BorderLayout.SOUTH);
    }

    private void sendGroupMessage() {
        String message = groupChatInput.getText();
        if (!message.isEmpty()) {
            groupChatArea.append("Me: " + message + "\n");
            groupChatInput.setText("");
        }
    }
}
