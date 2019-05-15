import java.io.File;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import javax.websocket.OnMessage;
import javax.websocket.server.ServerEndpoint;

/**
 * Dailyprogrammer: 29 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/r8a7o/3222012_challenge_29_intermediate/
 */
@WebServlet(name="intermediate", urlPatterns = { "/data" })
@ServerEndpoint("/data")
public class Intermediate extends HttpServlet {

    private File outputFile = new File("D:\\output.txt");;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        write(request.getParameter("data"));
        response.sendRedirect(request.getContextPath());
    }

    @OnMessage
    public void onMessage(String data) throws IOException {
        System.out.println("message received: " + data);
        write(data);
    }

    private void write(String data) throws IOException {
        if (data == null || data.trim().isEmpty()) return;

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(outputFile, true));
            writer.write('\n');
            writer.write(data.trim());
        } finally {
            if (writer != null) writer.close();
        }
    }
}
