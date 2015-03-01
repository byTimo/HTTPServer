import java.io.*;

/**
 * Created by Racet_000 on 09.11.2014.
 */
public class CSSFile {
    File f;
    OutputStream os;
    public CSSFile(String filePath, OutputStream os)
    {
        f = new File(filePath);
        this.os = os;
    }
    public void SendRsponce()
    {
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(os));
            if(bf.ready()) {
                System.out.println("Отправляются заголовки");
                String headers = "HTTP/1.1 200 OK\r\n" +
                        //"Accept: text/html\r\n" +
                        "Server: MyFirsServer/2014-10-07\r\n" +
                        "Content-Type: text/css; charset = utf-8\r\n" +
                        "Accept-Language: ru\r\n" +
                        "Connection: keep-alive\r\n\r\n";
                pw.println(headers);
                pw.flush();
                System.out.println("Заголовки отправлены");
                while (bf.ready()) {
                    String cash = bf.readLine();
                    pw.println(cash);
                    pw.flush();
                }
                System.out.println("BufferedReader успешно отправил файл: " + f.getPath());

            } else {
                System.out.println("BufferedReader не готов считываеть файл: " + f.getPath());
            }
        }catch(IOException IOE)
        {
            System.out.println("Ошибка при создании BufferedReader " +IOE.getMessage());
        }
    }
}
