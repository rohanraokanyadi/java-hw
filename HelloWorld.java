import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import javax.swing.JFrame;
import javax.swing.JLabel;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

public class HelloWorldGUI {
    private JFrame frame;

    public HelloWorldGUI() {
        frame = new JFrame("Hello World GUI");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Hello, World!");
        frame.add(label);
    }

    public void show() {
        frame.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        // set maximum heap size to 512 MB
        long maxHeapSize = 512 * 1024 * 1024;
        System.out.println("Setting maximum heap size to " + maxHeapSize + " bytes");
        try {
            Runtime.getRuntime().exec("java -Xmx" + maxHeapSize + " HelloWorldGUI");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // create a simple HTTP server on port 8080 with a health check endpoint
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/health", (exchange -> {
            String response = "OK";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }));
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port 8080");

        HelloWorldGUI gui = new HelloWorldGUI();
        gui.show();
    }
}
