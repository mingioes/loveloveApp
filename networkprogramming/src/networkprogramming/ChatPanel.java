package networkprogramming;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatPanel extends JPanel {
    private JTextArea chatArea;
    private JTextField chatInput;
    private Client client;

    public ChatPanel() {
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        chatInput = new JTextField();
        chatInput.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        add(chatInput, BorderLayout.SOUTH);
    }

    public void setClient(Client client) {
        this.client = client;
    }

    private void sendMessage() {
        String message = chatInput.getText();
        if (!message.isEmpty() && client != null) {
            client.sendMessage(message);
            chatInput.setText("");
        }
    }

    public void appendMessage(String message) {
        chatArea.append(message + "\n");
    }
}
