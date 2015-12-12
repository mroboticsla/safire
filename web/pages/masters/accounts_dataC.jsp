<%@ page import="Database.*"%>
<%@ page import="Masters_Data.*"%>
<%
    response.setHeader("Cache-Control","no-cache"); 
    response.setHeader("Pragma","no-cache"); 
    response.setDateHeader ("Expires", -1);
    response.setContentType("application/json; charset=utf-8");
    transactions lis = new transactions();
    out.print(lis.AccountsData());
%>