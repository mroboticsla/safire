<%@ page import="Database.*"%>
<%@ page import="Masters_Data.*"%>
<%
    response.setHeader("Cache-Control","no-cache"); 
    response.setHeader("Pragma","no-cache"); 
    response.setDateHeader ("Expires", -1);
    
    modules lis = new modules();
    response.setContentType("application/json; charset=utf-8");
    
    String jv_usr = (String)session.getAttribute("user_cod");
    if(jv_usr==null || jv_usr.length()==0){jv_usr="";}
    
    //Formulario
    String jv_opcion= request.getParameter("opcion");
    if(jv_opcion==null || jv_opcion.length()==0){jv_opcion="";}
    
    String jv_frm_id = request.getParameter("frm_id");
    if(jv_frm_id==null || jv_frm_id.length()==0){jv_frm_id = "";}
    
    String jv_frm_name = request.getParameter("frm_name");
    if(jv_frm_name==null || jv_frm_name.length()==0){jv_frm_name = "";}
    
    String jv_frm_order = request.getParameter("frm_order");
    if(jv_frm_order==null || jv_frm_order.length()==0){jv_frm_order="";}
    
    if (jv_opcion.equals("A"))
    {
        lis.add(jv_frm_name, jv_frm_order, jv_usr);
    }else if (jv_opcion.equals("E"))
    {
        lis.edit(jv_frm_id, jv_frm_name, jv_frm_order);
    }else if (jv_opcion.equals("D"))
    {
        lis.delete(jv_frm_id);
    }
    
    out.print(lis.Data());
%>