<%@ page import="java.sql.*, java.io.*, java.util.*"%>
<%@ page import="Database.*"%>
<%@ page import="net.sf.jasperreports.engine.*"%>
<%
    Connect conexion = new Connect();
    Connection conn = conexion.CreateConnection();
    Statement stmt = conn.createStatement();
    
    String residencial = request.getParameter("residencial");
    if (residencial == null) residencial = "";
    
    String correlativo = request.getParameter("correlativo");
    if (correlativo == null) correlativo = "";
    
    String ano_contable = request.getParameter("ano_contable");
    if (ano_contable == null) ano_contable = "";
    
    File reportFile = new File(application.getRealPath("reportes/safire_rep_partida_diaria.jasper"));
    Map parameters = new HashMap();
    
    System.out.println(residencial + ", " + correlativo + ", " + ano_contable + ", " + conn.toString());
    
    parameters.put("residencial", residencial);
    parameters.put("correlativo", correlativo);
    parameters.put("anio", ano_contable);
    
    byte[] bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), parameters, conn);
    response.setContentType("application/pdf");
    //response.setContentLength(bytes.length);
    ServletOutputStream outputStream = response.getOutputStream();
    outputStream.write(bytes, 0, bytes.length);
    outputStream.flush();
    outputStream.close();
    response.flushBuffer();
%>