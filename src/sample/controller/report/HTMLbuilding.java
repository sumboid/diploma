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
    public static String htmlBuild(Map<String, Report> reportMap, String image){
        String resultString="";
        int n= reportMap.size();
        Set<String> names = reportMap.keySet();
        StringBuilder builder =new StringBuilder();
        builder.append("<html><head></head><body>");
        builder.append("<img src=\"" + image + "\"/>");
        builder.append("<table border=\"1\"><tr><td>ReportName</td><td>num_ants</td><td>lifeCycle</td><td>P</td><td>al</td></tr>");//<td></td>
        for(String name : names) {
            Report report = reportMap.get(name);
            builder.append("<tr><td>"+name+"</td>");
            builder.append("<td>"+report.getProblem().getParams().ANTS_NUMBER+"</td>");
            builder.append("<td>"+report.getProblem().getParams().lifeСycle+"</td>");
            builder.append("<td>"+report.getProblem().getParams().p+"</td>");
            builder.append("<td>"+report.getProblem().getParams().al+"</td></tr>");
        }
        builder.append("</table>");
        builder.append("</body></html>");
        resultString=builder.toString();
        System.out.println(resultString);
        return resultString;
    }
    public static void save(String str, String path){
      //  public static void write(String fileName, String text) {
            //Определяем файл
            File file = new File(path);

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
