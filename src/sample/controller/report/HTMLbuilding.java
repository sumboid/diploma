package sample.controller.report;

import javafx.collections.ObservableList;
import sample.controller.report.internal.ReportView;
import sample.model.algorithm.data.Parameters;
import sample.model.report.Report;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

public class HTMLbuilding {
    private static String genTable(ObservableList<ReportView> reports) {
        StringBuilder builder =new StringBuilder();
        builder
                .append("<table class='report-table'>")
                    .append("<thead>")
                        .append("<tr>")
                            .append("<th>Название</th>")
                            .append("<th>Количество городов</th>")
                            .append("<th>Количество муравьев</th>")
                            .append("<th>Количество итераций</th>")
                            .append("<th>Коэффициент испарения</th>")
                            .append("<th>Вес следа феромона</th>")
                            .append("<th>Вес расстояния</th>")
                            .append("<th>Q</th>")
                            .append("<th>Число элитных муравьев</th>")
                            .append("<th>Кратчайший путь</th>")
                        .append("</tr>")
                    .append("</thead>");
        builder.append("<tbody>");
        for(ReportView report : reports) {
            builder
                    .append("<tr>")
                        .append("<td>"+report.getName()+"</td>")
                        .append("<td>"+report.getSize() + "</td>")
                        .append("<td>"+report.getAntsNumber()+"</td>")
                        .append("<td>"+report.getIterationsNumber()+"</td>")
                        .append("<td>"+report.getP()+"</td>")
                        .append("<td>"+report.getAl()+"</td>")
                        .append("<td>"+report.getB()+"</td>")
                        .append("<td>"+report.getQ()+"</td>")
                        .append("<td>"+report.getEliteAntsNumber()+"</td>")
                        .append("<td>"+report.getLength()+"</td>")
                    .append("</tr>");
        }

        builder.append("</tbody>");

        return builder.append("</table>").toString();
    }

    private static String genImage(String image) {
        return "<img class='report-image-src' src='data:image/png;base64," + image + "'/>";
    }

    private static String genHead() {
        StringBuilder builder =new StringBuilder();
        builder
                .append("<head>")
                    .append("<meta charset=\"utf-8\">")
                    .append("<style>")
                        .append(".report-table-container {")
                            .append("width: 100%;")
                        .append("}")
                        .append(".report-table {")
                            .append("width: 100%;")
                        .append("}")
                        .append("td, th {")
                            .append("border-spacing: 0.5rem;")
                            .append("border: 1px solid #999;")
                            .append("height: 30px;")
                            .append("font-family: sans-serif;")
                            .append("font-size: 12px;")
                        .append("}")
                        .append("h1 {")
                            .append("font-family: sans-serif;")
                        .append("}")
                    .append("</style>")
                .append("</head>");

        return builder.toString();
    }

    public static String htmlBuild(ObservableList<ReportView> reports, String image, String text){
        StringBuilder builder =new StringBuilder();

        builder
                .append("<!DOCTYPE html>")
                .append("<html>")
                    .append(genHead())
                    .append("<body>")
                    .append("<center>")
                        .append("<h1>Отчет</h1>")
                        .append(genImage(image))
                    .append("</center>")
                    .append("<div class='report-table-container'>")
                        .append(genTable(reports))
                    .append("</div>")
                    .append("<div class='report-text'>")
                        .append("<br />")
                        .append("<br />")
                        .append("<hr />")
                        .append("<br />")
                        .append(text)
                    .append("</div>")
                    .append("</body>")
                .append("</html>");

        return builder.toString();
    }
    public static void save(String str, String path){
        File file = new File(path);

        try {
            if(!file.exists()){
                file.createNewFile();
            }

            PrintWriter out = new PrintWriter(file.getAbsoluteFile());
            try {
                out.print(str);
            } finally {
                out.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
