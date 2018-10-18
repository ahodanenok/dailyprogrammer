import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.net.Socket;
import java.net.ServerSocket;

/**
 * Dailyprogrammer: 15 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/q4dz1/2242012_challenge_15_difficult/
 */
public class Difficult {

    private static final int PORT = 5115;
    private static final String ECHO_ACTION = "echo";
    private static final String ECHO_REVERSE_ACTION = "echo-reverse";

    public static void main(String[] args) throws IOException {
        if ("client".equals(args[0])) {
            startClient(args[1]);
        } else if ("server".equals(args[0])) {
            startServer();
        }
    }

    private static void startServer() throws IOException {
        ServerSocket server = null;
        Socket client = null;
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            server = new ServerSocket(PORT);
            client = server.accept();
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);

            String action = in.readLine();
            System.out.println(action);
            if (!ECHO_ACTION.equals(action) && !ECHO_REVERSE_ACTION.equals(action)) {
                throw new IllegalStateException("unknown action: " + action);
            }

            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("Received: " + line);
                if (ECHO_ACTION.equals(action)) {
                    line = line;
                } else if (ECHO_REVERSE_ACTION.equals(action)) {
                    line = new StringBuilder(line).reverse().toString();
                }

                out.println(line);
                System.out.println("Sent: " + line);
            }
        } finally {
            if (out != null) out.close();
            if (in != null) in.close();
            if (client != null) client.close();
            if (server != null) server.close();
        }
    }

    private static void startClient(String action) throws IOException {
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            socket = new Socket("localhost", PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println(action);

            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String line;
            while ((line = stdIn.readLine()) != null) {
                out.println(line);
                System.out.println("Received:" + in.readLine());
            }
        } finally {
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null) socket.close();
        }
    }
}
