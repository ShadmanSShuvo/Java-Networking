
// Client.java
import java.awt.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class Client extends JFrame {
    private JTextField ipField, portField, messageField;
    private JTextArea chatArea;
    private JButton connectBtn, sendBtn;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Client() {
        setTitle("Chat Client");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        buildUI();
    }

    private void buildUI() {
        JPanel connectPanel = new JPanel();
        ipField = new JTextField("127.0.0.1", 10);
        portField = new JTextField("12345", 5);
        connectBtn = new JButton("Connect");

        connectPanel.add(new JLabel("Server IP:"));
        connectPanel.add(ipField);
        connectPanel.add(new JLabel("Port:"));
        connectPanel.add(portField);
        connectPanel.add(connectBtn);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        JPanel messagePanel = new JPanel();
        messageField = new JTextField(20);
        sendBtn = new JButton("Send");
        sendBtn.setEnabled(false); // Disabled until connected

        messagePanel.add(messageField);
        messagePanel.add(sendBtn);

        add(connectPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(messagePanel, BorderLayout.SOUTH);

        connectBtn.addActionListener(e -> connectToServer());
        sendBtn.addActionListener(e -> sendMessage());

        messageField.addActionListener(e -> sendMessage());
    }

    private void connectToServer() {
        try {
            String ip = ipField.getText().trim();
            int port = Integer.parseInt(portField.getText().trim());
            socket = new Socket(ip, port);

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            chatArea.append("Connected to server.\n");
            sendBtn.setEnabled(true);
            new Thread(this::listenForMessages).start();
        } catch (Exception ex) {
            chatArea.append("Connection failed: " + ex.getMessage() + "\n");
        }
    }

    private void sendMessage() {
        String msg = messageField.getText().trim();
        if (!msg.isEmpty() && out != null) {
            out.println(msg);
            messageField.setText("");
        }
    }

    private void listenForMessages() {
        String msg;
        try {
            while ((msg = in.readLine()) != null) {
                chatArea.append("Server: " + msg + "\n");
            }
        } catch (IOException e) {
            chatArea.append("Disconnected from server.\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Client().setVisible(true));
    }
}
