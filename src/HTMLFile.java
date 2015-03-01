import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Created by Racet_000 on 09.11.2014.
 */
public class HTMLFile {
    File f;
    String path;
    OutputStream os;
    Vector<Link> Links;
    List<Snowboard> table;
    public HTMLFile(String filePath, OutputStream os)
    {
        f = new File(filePath); //Открываем файл, путь которого был задан конструктору
        this.os = os;// Получаем данные по выходящему потоку сокета
        this.path = filePath;
        //---
        Links= new Vector<Link>();
        Link first = new Link("index.html","О себе");
        Links.add(first);
        Link second = new Link("task.html","Индивидуальное задание");
        Links.add(second);
        Link third = new Link("table.html","Таблица");
        Links.add(third);
    }
    public void SendRsponce()//Метод отправки файла
    {
        String file_content = "";
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(f)));//Инициализируем BufferedReader, основанный на потоке из файла
            if(bf.ready())
            {
                while (bf.ready()){
                    file_content += bf.readLine();
                }
                    System.out.println("BufferedReader успешно отправил файл: " + f.getPath());

            } else {
                System.out.println("BufferedReader не готов считываеть файл: " + f.getPath());
            }
        }catch(Exception IOE)
        {
            System.out.println("Ошибка при создании BufferedReader " +IOE.getMessage());
        }
        Configuration cfg = new Configuration();
        HashMap content = new HashMap();
        String title = WhatLink(path);
        content.put("title",title);
        content.put("Links", Links);
        if(title.equals("Таблица"))
            try {
                content.put("content", HeadersHTML(cfg));
            }catch (Exception e){
                e.printStackTrace();
            }
        else
            content.put("content",file_content);
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(os));
        try{
            System.out.println("Отправляются заголовки");
            String headers = "HTTP/1.1 200 OK\r\n" +//Строка заголовков ответа
                    "Server: MyFirsServer/2014-10-07\r\n" +
                    "Content-Type: text/html; charset = utf-8\r\n" +//В этой строке единственное отличие между другими классами отправки файлов другого расширения
                    "Accept-Language: ru\r\n" +
                    "Connection: keep-alive\r\n\r\n";
            pw.println(headers);
            pw.flush();
            Template temp = cfg.getTemplate("mainHTML.html");
            Writer output = new OutputStreamWriter(os);
            temp.process(content,output);
            output.flush();
        }catch (IOException IOE)
        {
            System.out.println("Беда");
        }catch (TemplateException TE)
        {
            System.out.println("Не понятная беда");
        }

    }
    public String WhatLink(String URI)
    {
        for(Link l : Links)
        {
            if(l.getURI().equals(URI))
                return l.getName();
        }
        return "";
    }
   /* private StringWriter TableHTML(Configuration cfg) throws SQLException
    {
        StringWriter SW = new StringWriter();
        HashMap table_content = new HashMap();
        table = Factory.getInstance().getSnowboardDAO().getAllSnowboard();
        table_content.put("TableEntitys",table);
        try{
            Template temptable = cfg.getTemplate("tabletamp_dontwork.html");
            temptable.process(table_content,SW);
        }catch (Exception e)
        {
            System.out.println("Беда");
        }
        return  SW;
    }*/
    private StringWriter HeadersHTML(Configuration cfg){
        StringWriter SW =new StringWriter();
        HashMap header = new HashMap();
        String[] headers = new String [5];
        headers[0] = "Идентификатор";
        headers[1] = "Название сноуборда";
        headers[2] = "Название бренда";
        headers[3] = "Стиль катания";
        headers[4] = "Кнопки управления";
        header.put("Headers",headers);
        try {
            Template template = cfg.getTemplate("tabletamp.html");
            template.process(header,SW);
        }catch (Exception IOE){
            IOE.printStackTrace();
        }

        return SW;
    }
}
