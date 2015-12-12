<%@ page import="Database.*"%>
<%@ page import="Masters_Data.*"%>
<%
    response.setHeader("Cache-Control","no-cache"); 
    response.setHeader("Pragma","no-cache"); 
    response.setDateHeader ("Expires", -1);
    
    paymenttypes lis = new paymenttypes();
    response.setContentType("application/json; charset=utf-8");
    
    String jv_usr = (String)session.getAttribute("user_cod");
    if(jv_usr==null || jv_usr.length()==0){jv_usr="";}
    
    //Formulario
    String jv_opcion= request.getParameter("opcion");
    if(jv_opcion==null || jv_opcion.length()==0){jv_opcion="";}
    
    String jv_frm_id = request.getParameter("id");
    if(jv_frm_id==null || jv_frm_id.length()==0){jv_frm_id = "";}
    
    String jv_frm_desc = request.getParameter("desc");
    if(jv_frm_desc==null || jv_frm_desc.length()==0){jv_frm_desc = "";}
    
    String jv_frm_info = request.getParameter("info");
    if(jv_frm_info==null || jv_frm_info.length()==0){jv_frm_info = "N";}
    
    if (jv_opcion.equals("A"))
    {
        lis.add(jv_frm_desc, jv_frm_info, jv_usr);
    }else if (jv_opcion.equals("E"))
    {
        lis.edit(jv_frm_id, jv_frm_desc, jv_frm_info);
    }else if (jv_opcion.equals("D"))
    {
        lis.delete(jv_frm_id);
    }
    
    out.print(lis.Data());
%>