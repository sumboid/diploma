package sample.controller.report;

import sample.model.report.Report;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

/**
 * Created by roman on 16.05.16.
 */
public class HTMLbuilding {
    public static String htmlBuild(Map<String, Report> reportMap){
        String resultString="";
        int n= reportMap.size();
        Set<String> names = reportMap.keySet();
        StringBuilder builder =new StringBuilder();
        builder.append("<Table><tr><td>ReportName</td><td>num_ants</td><td>lifeCycle</td><td>P</td><td>al</td>/<tr>");//<td></td>
        for(String name : names) {
            Report report = reportMap.get(name);
            builder.append("<tr><td>"+name+"</td>");
            builder.append("<td>"+report.getProblem().getParams().ANTS_NUMBER+"</td>");
            builder.append("<td>"+report.getProblem().getParams().lifeСycle+"</td>");
            builder.append("<td>"+report.getProblem().getParams().p+"</td>");
            builder.append("<td>"+report.getProblem().getParams().al+"</td></tr>");

        }
        builder.append("/Table");
        resultString=builder.toString();
        System.out.println(resultString);
        return resultString;
    }
    public static void save(String str){
      //  public static void write(String fileName, String text) {
            //Определяем файл
            File file = new File("htmlOO.html");

            try {
                //проверяем, что если файл не существует то создаем его
                if(!file.exists()){
                    file.createNewFile();
                }

                //PrintWriter обеспечит возможности записи в файл
                PrintWriter out = new PrintWriter(file.getAbsoluteFile());

                try {
                    //Записываем текст у файл
                    out.print(str);
                } finally {
                    //После чего мы должны закрыть файл
                    //Иначе файл не запишется
                    out.close();
                }
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
    }
}
