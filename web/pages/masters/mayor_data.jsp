<%@ page import="Database.*"%>
<%@ page import="Masters_Data.*"%>
<%
    mayorizacion lis = new mayorizacion();
    response.setHeader("Cache-Control","no-cache"); 
    response.setHeader("Pragma","no-cache"); 
    response.setDateHeader ("Expires", -1);
    response.setContentType("application/json; charset=utf-8");
    
    String jv_usr = (String)session.getAttribute("user_cod");
    if(jv_usr==null || jv_usr.length()==0){jv_usr="";}
    
    String jv_residencial = (String)session.getAttribute("residencial");
    if(jv_residencial==null || jv_residencial.length()==0){jv_residencial="";}
    
    String jv_periodo= (request.getParameter("periodo") != null) ? request.getParameter("periodo"): "";
    
    //Formulario
    String jv_opcion= request.getParameter("opcion");
    if(jv_opcion==null || jv_opcion.length()==0){jv_opcion="";}
    
    String jv_nivel = request.getParameter("nivel");
    if(jv_nivel==null || jv_nivel.length()==0){jv_nivel = "";}
    
    if (jv_opcion.equals("M"))
    {
        lis.mayorizar(jv_residencial);
    }else if (jv_opcion.equals("D"))
    {
        lis.desmayorizar(jv_residencial);
    }else if (jv_opcion.equals("C"))
    {
        lis.desmayorizar(jv_residencial);
    }
    
    out.print(lis.Data(jv_residencial,jv_nivel));
%>