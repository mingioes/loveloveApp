package networkprogramming;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class lovelove extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private Client client;
    private DefaultListModel<String> friendListModel;
    private String username;

    public lovelove(Client client, DefaultListModel<String> friendListModel, String username) {
        this.client = client;
        this.friendListModel = friendListModel;
        this.username = username; // 로그인한 사용자 이름을 설정
        setTitle("LoveLove Chat");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(940, 698);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        // JTabbedPane 설정
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("프로필 & 친구목록", createProfilePanel());
        tabbedPane.addTab("채팅", new ChatPanel());
        tabbedPane.addTab("그룹채팅", new GroupChatPanel());

        FriendMatchPanel friendMatchPanel = new FriendMatchPanel(client);
        client.setFriendMatchPanel(friendMatchPanel);  // Client에 FriendMatchPanel 설정
        tabbedPane.addTab("친구 매칭하기", friendMatchPanel);

        contentPane.add(tabbedPane, BorderLayout.CENTER);
    }

    // 프로필 및 친구 목록 패널 생성
    private JPanel createProfilePanel() {
        JPanel profilePanel = new JPanel();
        profilePanel.setBackground(new Color(255, 182, 193)); // 핑크색 배경
        profilePanel.setLayout(new BorderLayout());

        // 사용자 프로필 정보
        JPanel profileInfoPanel = new JPanel();
        profileInfoPanel.setBackground(new Color(255, 182, 193));
        profileInfoPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));

        // 프로필 이미지 및 사용자 이름 설정
        JLabel profileImage = new JLabel(new ImageIcon("C:/Users/vhehd/OneDrive/바탕 화면/3학년 2학기/네트워크프로그래밍/person.png"));
        profileImage.setPreferredSize(new Dimension(80, 80));
        JLabel profileName = new JLabel(username); // 로그인한 사용자 이름 표시
        profileName.setFont(new Font("Malgun Gothic", Font.BOLD, 24));

        profileInfoPanel.add(profileImage);
        profileInfoPanel.add(profileName);

        profilePanel.add(profileInfoPanel, BorderLayout.NORTH);

        // 친구 목록 표시
        JList<String> friendList = new JList<>(friendListModel); 
        friendList.setCellRenderer(new FriendListRenderer()); // 렌더러 설정으로 아이콘 및 텍스트 크기 조정
        friendList.setBackground(Color.WHITE);
        profilePanel.add(new JScrollPane(friendList), BorderLayout.CENTER);

        return profilePanel;
    }

    // 친구 목록 아이템의 폰트 및 아이콘 렌더러 설정
    private class FriendListRenderer extends DefaultListCellRenderer {
        private static final long serialVersionUID = 1L;
        private ImageIcon friendIcon;

        public FriendListRenderer() {
            // 아이콘 크기를 작게 조정
            ImageIcon originalIcon = new ImageIcon("C:/Users/vhehd/OneDrive/바탕 화면/3학년 2학기/네트워크프로그래밍/person.png");
            Image scaledImage = originalIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH); // 아이콘 크기를 20x20으로 설정
            friendIcon = new ImageIcon(scaledImage);
        }

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setIcon(friendIcon); // 작은 아이콘 설정
            label.setFont(new Font("Malgun Gothic", Font.BOLD, 18)); // 폰트 크기 설정
            label.setIconTextGap(10); // 아이콘과 텍스트 간격 설정
            return label;
        }
    }

}
