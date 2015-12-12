<%@ page import="Database.*"%>
<%@ page import="Masters_Data.*"%>
<%
    residencial lis = new residencial();
    response.setHeader("Cache-Control","no-cache"); 
    response.setHeader("Pragma","no-cache"); 
    response.setDateHeader ("Expires", -1);
    response.setContentType("application/json; charset=utf-8");
    out.print(lis.Data());
%>