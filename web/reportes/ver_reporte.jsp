<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="net.sf.jasperreports.engine.JasperRunManager"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.io.File"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="Database.*"%>
<%@page trimDirectiveWhitespaces="true" %>
<%  
    String reporte = request.getParameter("reporte");
    String CODCOMP = (request.getParameter("CODCOMP") == null) ? "" : request.getParameter("CODCOMP");
    String recibo_ini = (request.getParameter("recibo_ini") == null) ? "0" : request.getParameter("recibo_ini");
    String recibo_fin = (request.getParameter("recibo_fin") == null) ? "0" : request.getParameter("recibo_fin");
    
    Connect obj_connect = new Connect();

    File reportFile = new File(application.getRealPath("reportes/" + reporte));

    Map parameters = new HashMap();
    parameters.put("CODCOMP", CODCOMP);
    parameters.put("recibo_ini", 1);
    parameters.put("recibo_fin", 5);

    byte[] bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), parameters, obj_connect.CreateConnection());

    response.setContentType("application/pdf");
    response.setContentLength(bytes.length);
    ServletOutputStream ouputStream = response.getOutputStream();
    ouputStream.write(bytes, 0, bytes.length);
    ouputStream.flush();
    ouputStream.close();
%>