import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

public class HelloWorld {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello, World!");

        // set maximum heap size to 512 MB
        long maxHeapSize = 512 * 1024 * 1024;
        System.out.println("Setting maximum heap size to " + maxHeapSize + " bytes");
        try {
            Runtime.getRuntime().exec("java -Xmx" + maxHeapSize + " HelloWorld");
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
    }
}