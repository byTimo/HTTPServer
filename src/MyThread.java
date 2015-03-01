import com.sun.javafx.scene.layout.region.Margins;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Racet_000 on 09.11.2014.
 */
public class MyThread extends Thread {
    Socket s;
    InputStream is;
    OutputStream os;
    String methodRequest;

    public MyThread(Socket s) throws Throwable
    {
        this.s = s;
        is = s.getInputStream();
        os = s.getOutputStream();
    }
    public  void run() {
        String contentRequest = GetRequest();//Производиться заполнение строки запроса
        if (methodRequest.equals("GET")) {//На основе метода запроса выбирается способ ответа
            SendRequest_GETMethod(contentRequest);
        } else {
            SendRequest_POSTMethod(contentRequest);
        }
        try {
            s.close();
            System.out.println("Соединение закрыто!\r\n");
        } catch (IOException e) {
            System.out.println("Сокет не может быть закрыт!\r\n");
        }
    }
    private String GetRequest(){//Метод определения запроса
        String allRequest = "";//Полная строка запроса
        String method = "";//Строка, содержащая метод запроса
        String contentRequest = "";//Строка, содержащая содержание запроса
        //--------- Получение полного запроса
       try {
           System.out.println("Производиться считывание запроса:");
           byte[] buffer = new byte[64*1024];//Буффер для хранения байтов запроса (64Кб)
           int r = is.read(buffer);//Определение количества использованных байтов
           allRequest = new String(buffer,0,r);//На основании буффера и количества использованных байтов формируем строку
           System.out.println(allRequest);
        }catch (IOException IOE){
            System.out.println("Не получилось считать запрос, исключение: "+IOE.getMessage());
        }
        //------ Определение метода запроса
        if(allRequest.contains("POST")&&!allRequest.trim().equals("")){
            method = allRequest.substring(0,4);
            System.out.println("Метод " + method);
            this.methodRequest = method;
        }else if(allRequest.contains("GET")&&!allRequest.equals("")){
            method = allRequest.substring(0,3);
            System.out.println("Метод " + method);
            this.methodRequest = method;
        }else {
            System.out.println("Метод не может быть определён");
            this.methodRequest = "";
        }
        //------- Определение содержания запроса
        if(!allRequest.equals("")){
            if(method.equals("GET")) {
                String cash = allRequest.substring(allRequest.indexOf("/") + 1);
                contentRequest = cash.substring(0, cash.indexOf(" "));
            }else{
                String cash = allRequest.substring(allRequest.indexOf("\r\n\r\n"));
                contentRequest = cash.substring(4);
            }
            if (!contentRequest.equals("")) {
                System.out.println("Содержание запроса: " + contentRequest);
            } else {
                System.out.println("Запрос пустой!");
            }
        }
        return contentRequest.toLowerCase();//Возвращаем содержание запроса приведённое к нижнему регистру
    }
    private void SendRequest_GETMethod(String content){//Метод отправки ответа на GET запрос
       if(!content.equals("")){
           System.out.println("Определение расширения файла");
           String cash = content.substring(content.indexOf('.'));//Строка для определения разширения запрашиваемого файла
           if(cash.equals(".html")){
               System.out.println("Это HTML");
               new HTMLFile(content,os)//Создание экземпляра класса, ответственного за отправку любой HTML-страницы
                       .SendRsponce();//Отправка страницы
           }else if(cash.equals(".css")){
               System.out.println("Это CSS");
               new CSSFile(content,os)//Создание экземпляра класса, ответственного за отправку любой CSS-файла
                       .SendRsponce();
           }else if(cash.equals(".js")){
               System.out.println("Это JavaScript");
               new JSFile(content,os)//Создание экземпляра класса, ответственного за отправку любой JavaScript-файла
                       .SendRsponce();
           }else {
               System.out.println("Расширение не определено, запрос игнорируется");
           }
       }else {
           System.out.println("Запрос пуст, отправляется old_index.html");
           new HTMLFile("old_index.html",os)
                   .SendRsponce();//При пустом запросе отправляется главная страница
       }
    }
    private void SendRequest_POSTMethod(String content) {//Метод отправки ответа на POST запрос
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(os));//Инициализируем PrintWriter для отправки ответа
        String result = "";//Содеражание ответа
        //---- Определение результата для отправки ответа
        if (!content.equals("")) {
            System.out.println("Определение назначения запроса");


            if (content.contains("content=")) {//Означает, что данный POST-запрос пришёл на получение результата инд. задания
                System.out.println("Запрос индивидуального задания");
                String taskContent = content.substring(content.indexOf('=') + 1);//Получаем строку параметров
                result = new Line(taskContent).getResult();//Создаём экземпляр класса на основе параметров и получаем результат вычислений
                System.out.println("Результат выполнения задания: " + result);
                String heders = "HTTP/1.1 200 OK\r\n\r\n";
                System.out.println("Отправка заголовков");
                pw.println(heders);
                pw.flush();
                System.out.println("Отправка ответа");
                pw.println(result);
                pw.flush();


            } else if (content.contains("create;")) {//Озаначает, что запрос пришёд на создание новой строки таблицы
                System.out.println("Запрос создания новой строки таблицы");
                String[] rows = content.split(";");
                Snowboard snowboard = new Snowboard(rows[1],rows[2],rows[3]);
                try {
                    long id = Factory.getInstance().getSnowboardDAO().addSnowboard(snowboard);
                    String heders = "HTTP/1.1 200 OK\r\n\r\n";
                    System.out.println("Отправка заголовков");
                    pw.println(heders);
                    pw.flush();
                    System.out.println("Отправка ответа");
                    pw.println(id);
                    pw.flush();
                }catch (SQLException e){
                    System.out.println("База данных в беде!");
                }
                System.out.println("Запись добавлена");


            } else if (content.contains("delete;")) {//Озаначает, что запрос пришёд на удаление строки таблицы
                System.out.println("Запрос удаления строки таблицы");
                String[] rows = content.split(";");
                if(rows[1].contains("\n"))
                    rows[1] = ScanContent(rows[1]);
                Snowboard snowboard = new Snowboard(rows[1],rows[2],rows[3],rows[4]);
                try {
                    Factory.getInstance().getSnowboardDAO().deleteSnowboard(snowboard);
                }catch (SQLException e){
                    System.out.println("База данных в беде!");
                }
                System.out.println("Запись удалена");


            } else if (content.contains("check;")) {//Озаначает, что запрос пришёд на изменение строки таблицы
                System.out.println("Запрос изменения строки таблицы");
                String[] rows = content.split(";");
                if(rows[1].contains("\n"))
                    rows[1] = ScanContent(rows[1]);
                Snowboard snowboard = new Snowboard(rows[1], rows[2], rows[3], rows[4]);
                try {
                    Factory.getInstance().getSnowboardDAO().updateSnowboard(snowboard);
                } catch (SQLException e) {
                    System.out.println("База данных в беде!");
                }
                System.out.println("Запись исправлена");


            } else if(content.contains("size")) {
                try {
                    result = Integer.toString(Factory.getInstance().getSnowboardDAO().getAllSnowboard().size());
                    String heders = "HTTP/1.1 200 OK\r\n\r\n";
                    System.out.println("Отправка заголовков");
                    pw.println(heders);
                    pw.flush();
                    System.out.println("Отправка ответа");
                    pw.println(result);
                    pw.flush();
                } catch (SQLException e) {
                    System.out.println("База данных в беде!");
                }


            } else if(content.contains("list")){
                try {
                    List<Snowboard> snow = Factory.getInstance().getSnowboardDAO().getAllSnowboard();
                    Collections.sort(snow, new Comparator<Snowboard>() {
                        @Override
                        public int compare(Snowboard o1, Snowboard o2) {
                            return o1.getId()<o2.getId()?-1:1;
                        }
                    });
                    for(Snowboard s : snow){
                        result+=s.getId()+"$"+s.getName()+"$"+s.getBrand()+"$"+s.getStyle()+";";
                    }
                    String heders = "HTTP/1.1 200 OK\r\n\r\n";
                    System.out.println("Отправка заголовков");
                    pw.println(heders);
                    pw.flush();
                    System.out.println("Отправка ответа");
                    pw.println(result);
                    pw.flush();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    private String ScanContent(String par){
        String resulte = "";
        for(char c : par.toCharArray()){
            if(Character.isDigit(c))
                resulte+=c;
        }
        return resulte;
    }
}
