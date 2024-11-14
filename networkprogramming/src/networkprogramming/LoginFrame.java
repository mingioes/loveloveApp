package networkprogramming;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField usernameField;
    private JTextField serverAddressField;
    private JTextField portField;

    public LoginFrame() {
        setTitle("LoveLove Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(940, 698);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(255, 182, 193));  // 핑크색 배경
        setLayout(new BorderLayout());

        // 이미지 로고 패널 (상단 중앙에 배치)
        ImageIcon logoIcon = new ImageIcon("C:/Users/vhehd/OneDrive/바탕 화면/3학년 2학기/네트워크프로그래밍/heart.png");
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(logoLabel, BorderLayout.NORTH);

        // 입력 필드 및 버튼 패널
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(null);
        inputPanel.setBackground(new Color(255, 182, 193));  // 핑크색 배경

        // 이름 필드
        usernameField = new JTextField();
        usernameField.setBounds(320, 50, 300, 40);
        usernameField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.add(usernameField);

        JLabel usernameLabel = new JLabel("이름", SwingConstants.CENTER);
        usernameLabel.setBounds(320, 20, 300, 30);
        inputPanel.add(usernameLabel);

        // 서버 주소 필드
        serverAddressField = new JTextField("localhost");
        serverAddressField.setBounds(320, 130, 300, 40);
        serverAddressField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.add(serverAddressField);

        JLabel serverLabel = new JLabel("IP 주소", SwingConstants.CENTER);
        serverLabel.setBounds(320, 100, 300, 30);
        inputPanel.add(serverLabel);

        // 포트 번호 필드
        portField = new JTextField("12345");  // 기본 포트 번호
        portField.setBounds(320, 210, 300, 40);
        portField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.add(portField);

        JLabel portLabel = new JLabel("포트번호", SwingConstants.CENTER);
        portLabel.setBounds(320, 180, 300, 30);
        inputPanel.add(portLabel);

        // 접속 버튼
        JButton loginButton = new JButton("접속");
        loginButton.setBounds(420, 280, 100, 40);
        loginButton.setFocusPainted(false);
        loginButton.setBackground(Color.WHITE);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        inputPanel.add(loginButton);

        add(inputPanel, BorderLayout.CENTER); // 입력 패널을 중앙에 배치
    }

    private void login() {
        String username = usernameField.getText();
        String serverAddress = serverAddressField.getText();
        String portText = portField.getText();
        int port = 12345;

        try {
            port = Integer.parseInt(portText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "포트 번호는 숫자여야 합니다.");
            return;
        }

        if (!username.isEmpty()) {
            ChatPanel chatPanel = new ChatPanel();
            DefaultListModel<String> friendListModel = new DefaultListModel<>();
            Client client = new Client(serverAddress, port, username, chatPanel, friendListModel);
            chatPanel.setClient(client);

            // lovelove 프레임 생성 및 표시, 사용자 이름 전달
            lovelove mainApp = new lovelove(client, friendListModel, username);
            mainApp.setVisible(true);
            dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}
