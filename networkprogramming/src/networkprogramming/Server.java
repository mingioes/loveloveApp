package networkprogramming;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 12345;
    private static Set<ClientHandler> clientHandlers = new HashSet<>();
    private static Map<String, ClientHandler> userMap = new HashMap<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandlers.add(clientHandler);
                clientHandler.start();
                System.out.println("New client connected: " + clientSocket.getInetAddress());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String username;
        private Set<String> likedUsers = new HashSet<>(); // 이 클라이언트가 좋아요를 누른 사용자 목록

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // 클라이언트가 전송한 첫 번째 메시지를 사용자 이름으로 설정
                username = in.readLine();
                userMap.put(username, this);
                System.out.println("User connected: " + username);

                // 새로운 사용자가 접속했으므로 모든 기존 사용자에게 이 사용자 프로필 전송
                broadcastNewUserProfile(username);

                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Received from " + username + ": " + message);
                    if (message.startsWith("LIKE:")) {
                        String likedUser = message.substring(5);
                        handleLike(likedUser);
                    } else if (message.equals("REQUEST_RANDOM_USER")) {
                        sendRandomUserProfile();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                    clientHandlers.remove(this);
                    userMap.remove(username);
                    System.out.println("User disconnected: " + username);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // 새로운 사용자가 접속할 때 기존 사용자에게 프로필을 전송
        private void broadcastNewUserProfile(String newUser) {
            for (ClientHandler clientHandler : clientHandlers) {
                if (!clientHandler.username.equals(newUser)) { // 자신을 제외하고 다른 사용자에게 전송
                    clientHandler.out.println("RANDOM_USER:" + newUser);
                    System.out.println("Sent profile of " + newUser + " to " + clientHandler.username);
                }
            }
        }

        // 현재 클라이언트에게 랜덤 사용자 프로필 전송
        private void sendRandomUserProfile() {
            List<String> userList = new ArrayList<>(userMap.keySet());
            userList.remove(username); // 자기 자신은 제외
            if (!userList.isEmpty()) {
                String randomUser = userList.get(new Random().nextInt(userList.size()));
                out.println("RANDOM_USER:" + randomUser);
                System.out.println("Sent profile of " + randomUser + " to " + username);
            } else {
                System.out.println("No other users to match with for " + username);
            }
        }

        private void handleLike(String likedUser) {
            likedUsers.add(likedUser);
            System.out.println(username + " liked " + likedUser);

            // 서로 하트를 누른 경우
            ClientHandler likedUserHandler = userMap.get(likedUser);
            if (likedUserHandler != null && likedUserHandler.likedUsers.contains(username)) {
                this.out.println("FRIEND:" + likedUser);
                likedUserHandler.out.println("FRIEND:" + this.username);
                System.out.println(username + " and " + likedUser + " are now friends");
            }
        }
    }
}
