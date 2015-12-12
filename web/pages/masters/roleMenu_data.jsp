<%@ page import="Database.*"%>
<%@ page import="Masters_Data.*"%>
<%
    String role = request.getParameter("ht_role");
    if (role == null) role = "";
    
    String module = request.getParameter("ht_module");
    if (module == null) module = "";
    
    String app = request.getParameter("ht_app");
    if (app == null) app = "";
    
    String user = (String)session.getAttribute("user_cod");
    if(user==null || user.length()==0){user="";}
    
    String opc = request.getParameter("opcion");
    if (opc == null) opc = "";
    
    roleMenu lis = new roleMenu();
    response.setHeader("Cache-Control","no-cache"); 
    response.setHeader("Pragma","no-cache"); 
    response.setDateHeader ("Expires", -1);
    response.setContentType("application/json; charset=utf-8");
    
    if (opc.equals("A")){
        lis.add(role, module, app, user);
    }else if (opc.equals("D")){
        lis.delete(role, module, app);
    }
    
    if (role == ""){
        out.print(lis.AllData());
    }else{
        out.print(lis.RoleData(role));
    }
%>