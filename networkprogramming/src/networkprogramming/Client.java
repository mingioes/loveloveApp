package networkprogramming;

import java.io.*;
import java.net.*;
import javax.swing.*;

public class Client {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String username;
    private ChatPanel chatPanel;
    private DefaultListModel<String> friendListModel;
    private FriendMatchPanel friendMatchPanel;

    public Client(String serverAddress, int port, String username, ChatPanel chatPanel, DefaultListModel<String> friendListModel) {
        try {
            this.username = username;
            this.chatPanel = chatPanel;
            this.friendListModel = friendListModel;
            socket = new Socket(serverAddress, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // 서버로 사용자 이름 전송
            out.println(username);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setFriendMatchPanel(FriendMatchPanel friendMatchPanel) {
        this.friendMatchPanel = friendMatchPanel;
        System.out.println("FriendMatchPanel set in Client");

        // FriendMatchPanel이 설정된 후, 서버로부터 랜덤 사용자 프로필 요청
        requestRandomUser();

        // 서버로부터 메시지 수신을 위한 스레드 시작
        new Thread(() -> {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Received message: " + message); // 디버그 메시지

                    if (message.startsWith("FRIEND:")) {
                        String friendName = message.substring(7);
                        addFriend(friendName); // 친구 목록에 추가
                    } else if (message.startsWith("RANDOM_USER:")) {
                        String randomUser = message.substring(12);
                        if (friendMatchPanel != null) {
                            friendMatchPanel.updateProfile(randomUser); // 랜덤 사용자 프로필 업데이트
                            System.out.println("Updated FriendMatchPanel with profile: " + randomUser);
                        } else {
                            System.out.println("friendMatchPanel is null");
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void likeFriend(String friendName) {
        out.println("LIKE:" + friendName);  // 서버에 좋아요 메시지 전송
    }

    public void requestRandomUser() {
        out.println("REQUEST_RANDOM_USER"); // 서버에 랜덤 사용자 요청
    }

    private void addFriend(String friendName) {
        SwingUtilities.invokeLater(() -> friendListModel.addElement(friendName));
    }
}
