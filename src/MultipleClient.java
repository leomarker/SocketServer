import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MultipleClient  extends Thread{
    private ServerSocket serverSocket ;
    private int port;
    boolean running = false;

    public MultipleClient(int port){
        this.port = port;
    }


    public  void startServer() throws IOException {
        serverSocket = new ServerSocket( port);
        System.out.println("server started");
        this.start();
        running = true;
    }

    public void stopServer(){
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

    public static void main(String[] args) throws IOException {

         MultipleClient multipleClient = new MultipleClient(8080);
         multipleClient.startServer();
        System.out.println("server");
        try
        {
            Thread.sleep( 60000 );
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }

        multipleClient.stopServer();
    }

}

class RequestHandler extends Thread{
    private Socket socket;

    RequestHandler(Socket socket){
        this.socket = socket;
    }

    public void run() throws RuntimeException {

        System.out.println("we got a connection");


        InputStream is = null;
        try {
            is = socket.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        OutputStream os = null;
        try {
            os = socket.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        DataInputStream dis = new DataInputStream(is);
        DataOutputStream dos = new DataOutputStream(os);

        System.out.print("server:");
        String st = "server:"+ new Scanner(System.in).nextLine();
        String line ;

        try {
            line = dis.readUTF();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            dos.writeUTF(st);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(line);


    }
}
