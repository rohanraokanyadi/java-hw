import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

public class HelloWorldGUI {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello, World!");

        // set maximum heap size to 512 MB
        long maxHeapSize = 512 * 1024 * 1024;
        System.out.println("Setting maximum heap size to " + maxHeapSize + " bytes");
        try {
            Runtime.getRuntime().exec("java -Xmx" + maxHeapSize + " HelloWorldGUI");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // create a simple GUI with a "Hello, World!" label and a health check button
        JFrame frame = new JFrame("Hello, World!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        JLabel label = new JLabel("Hello, World!");
        label.setFont(new Font("Serif", Font.BOLD, 24));
        panel.add(label);

        JButton healthCheckButton = new JButton("Health Check");
        healthCheckButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String response = "OK";
                    HttpExchange exchange = (HttpExchange) e.getSource();
                    exchange.sendResponseHeaders(200, response.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        panel.add(healthCheckButton);

        frame.getContentPane().add(panel);
        frame.setPreferredSize(new Dimension(400, 200));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

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
    }
}
