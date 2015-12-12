<%-- 
    Document   : rpt_Liquidacion
    Created on : 03-30-2015, 04:31:22 PM
    Author     : Nieto Mendoza
--%>

<%@page import="uml.TGastoCC"%>
<%@page import="java.util.List"%>
<%@page import="modeladoDAO.TGastoCCDAO"%>
<%@page import="modeladoDAO.BDConexion"%>
<%@page import="net.sf.jasperreports.engine.JasperRunManager"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.io.File"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Liquidación Caja Chica</title>
    </head>
    <%!
        BDConexion bd = new BDConexion();
        TGastoCCDAO tgastoccdao = new TGastoCCDAO();
        String numeroLiquidacion = null;
        RequestDispatcher rd = null;
    %>

    <%
        try {
            List<TGastoCC> lstLiquidacion = tgastoccdao.cargarMovimientos();
            for (TGastoCC gastocc : lstLiquidacion) {
                numeroLiquidacion = gastocc.getNum_liquidacion();
            }
        } catch (Exception e) {
            e.toString();
        }
        if (numeroLiquidacion!=null) {
                Connection cn;
        Class.forName(bd.getDriver());
        cn = DriverManager.getConnection(bd.getUrl(), bd.getUser(), bd.getPasswd());
        File reportFile = new File(application.getRealPath("resources/ireport/safire_rep_cajachica_gastos.jasper"));

        Map parameters = new HashMap();
        parameters.put("num_liquidacion", numeroLiquidacion);
        byte[] bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), parameters, cn);
        response.setContentType("application/pdf");
        response.setContentLength(bytes.length);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes, 0, bytes.length);
        outputStream.flush();
        outputStream.close();
        cn.close();
            } else{
            request.setAttribute("error", "Agregue una liquidación y genere el reporte antes de guardarla."
                    + "<br/> <br/><strong>Nota: </strong><i>Si lo que desea es generar un reporte de una liquidación procesada anteriormente comuniquese con el administrador para sugerir petición.</i> ");
            rd = request.getRequestDispatcher("error.jsp");
        }
        
        rd.forward(request, response);
    %>

</html>
