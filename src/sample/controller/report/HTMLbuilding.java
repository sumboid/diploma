package sample.controller.report;

import sample.model.report.Report;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

public class HTMLbuilding {
    private static String genTable(Map<String, Report> reportMap) {
        Set<String> names = reportMap.keySet();
        StringBuilder builder =new StringBuilder();
        builder
                .append("<table class='report-table'>")
                    .append("<thead>")
                        .append("<tr>")
                            .append("<th>ReportName</th>")
                            .append("<th>Cities</th>")
                            .append("<th>num_ants</th>")
                            .append("<th>lifeCycle</th>")
                            .append("<th>P</th>")
                            .append("<th>al</th>")
                            .append("<th>b</th>")
                            .append("<th>Элитные муравьи</th>")
                            .append("<th> Количество Э.м.</th>")
                        .append("</tr>")
                    .append("</thead>");
        builder.append("<tbody>");
        for(String name : names) {
            Report report = reportMap.get(name);
            builder
                    .append("<tr>")
                        .append("<td>"+name+"</td>")
                        .append("<td>"+report.getProblem().getDMatrix().n + "</td>")
                        .append("<td>"+report.getProblem().getParams().ANTS_NUMBER+"</td>")
                        .append("<td>"+report.getProblem().getParams().lifeСycle+"</td>")
                        .append("<td>"+report.getProblem().getParams().p+"</td>")
                        .append("<td>"+report.getProblem().getParams().al+"</td>")
                        .append("<td>"+report.getProblem().getParams().b+"</td>")
                        .append("<td>"+report.getProblem().getOverParams().useEliteAnts+ "</td>")
                        .append("<td>"+report.getProblem().getOverParams().eliteNumberAnt+"</td>")
                    .append("</tr>");
        }

        builder.append("</tbody>");

        return builder.append("</table>").toString();
    }

    private static String genImage(String image) {
        return "<img class='report-image-src' src='" + image + "'/>";
    }

    private static String genHead() {
        StringBuilder builder =new StringBuilder();
        builder
                .append("<head>")
                    .append("<style>")
                        .append("* {")
                            .append("font-family: sans-serif;")
                            .append("font-size: 12px;")
                        .append("}")
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
                        .append("}")
                    .append("</style>")
                .append("</head>");

        return builder.toString();
    }

    public static String htmlBuild(Map<String, Report> reportMap, String image){
        Set<String> names = reportMap.keySet();
        StringBuilder builder =new StringBuilder();

        builder
                .append("<html>")
                    .append(genHead())
                    .append("<body>")
                    .append(genImage(image))
                    .append("<div class='report-table-container'>")
                        .append(genTable(reportMap))
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
