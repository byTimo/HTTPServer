import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Racet_000 on 09.11.2014.
 */
public class Server {

    public static void main(String[] arg) throws  Throwable
    {
        ServerSocket server = new ServerSocket(8080);//Создаём серверный сокет
        while (true)
        {
            Socket s = server.accept();
            System.out.println("Успешно установленно соединение");
            MyThread my = new MyThread(s);
            new Thread(my).start();
        }
    }
}
