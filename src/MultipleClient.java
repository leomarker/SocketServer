import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultipleClient  extends Thread{
    private ServerSocket serverSocket ;
    private int port;
    boolean running = false;

    public MultipleClient(int port){
        this.port = port;

    }


    public  void startServer() throws IOException {
        serverSocket = new ServerSocket( port);
        this.start();
    }

    public void stopeServer(){
        this.interrupt();
        running = false;
    }

    public void run(){
        while(running){
            System.out.println("Listening for a connection ");
            try {
                Socket socket = serverSocket.accept();
                RequestHandler requestHandler = new RequestHandler( socket );
                requestHandler.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {

    }
}

