
<%@ page import="java.sql.*, java.io.*, java.util.*"%>
<%@ page import="Database.*"%>
<%@ page import="net.sf.jasperreports.engine.*"%>
<%

    Connect conexion = new Connect();
    Connection conn = conexion.CreateConnection();
    
    File reportFile = new File(application.getRealPath("reportes/safire_rep_catalogo.jasper"));
    Map parameters = new HashMap();
    
    byte[] bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), parameters, conn);
    response.setContentType("application/pdf");
    ServletOutputStream outputStream = response.getOutputStream();
    outputStream.write(bytes, 0, bytes.length);
    outputStream.flush();
    outputStream.close();
    response.flushBuffer();
%>