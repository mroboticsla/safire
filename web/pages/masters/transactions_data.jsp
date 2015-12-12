<%@ page import="Database.*"%>
<%@ page import="Masters_Data.*"%>
<%
    transactions lis = new transactions();
    response.setHeader("Cache-Control","no-cache"); 
    response.setHeader("Pragma","no-cache"); 
    response.setDateHeader ("Expires", -1);
    response.setContentType("application/json; charset=utf-8");
    
    String jv_usr = (String)session.getAttribute("user_cod");
    if(jv_usr==null || jv_usr.length()==0){jv_usr="";}
    
    //Formulario
    String jv_opcion= request.getParameter("opcion");
    if(jv_opcion==null || jv_opcion.length()==0){jv_opcion="";}
    
    String jv_frm_id = request.getParameter("frm_id");
    if(jv_frm_id==null || jv_frm_id.length()==0){jv_frm_id = "";}
    
    String jv_frm_res = request.getParameter("frm_res");
    if(jv_frm_res==null || jv_frm_res.length()==0){jv_frm_res = "";}
    
    String jv_frm_paymenttype = request.getParameter("frm_paymenttype");
    if(jv_frm_paymenttype==null || jv_frm_paymenttype.length()==0){jv_frm_paymenttype = "";}
    
    String jv_ie = request.getParameter("ie");
    if(jv_ie==null || jv_ie.length()==0){jv_ie = "I";}
    
    String jv_frm_name = request.getParameter("frm_name");
    if(jv_frm_name==null || jv_frm_name.length()==0){jv_frm_name = "";}
    
    String jv_frm_desc = request.getParameter("frm_desc");
    if(jv_frm_desc==null || jv_frm_desc.length()==0){jv_frm_desc = "";}
    
    String jv_frm_ccc = request.getParameter("frm_ccc");
    if(jv_frm_ccc==null || jv_frm_ccc.length()==0){jv_frm_ccc = "";}
    
    String jv_frm_cca = request.getParameter("frm_cca");
    if(jv_frm_cca==null || jv_frm_cca.length()==0){jv_frm_cca = "";}
    
    String tipo_i = "1";
    String tipo_e = "0";
    if (jv_ie.equals("E")){
        tipo_i = "0";
        tipo_e = "1";
    }
    
    if (jv_opcion.equals("A"))
    {
        lis.add(jv_frm_res, jv_frm_paymenttype, jv_frm_name, jv_frm_desc, tipo_i, tipo_e, jv_frm_ccc, jv_frm_cca, jv_usr);
    }else if (jv_opcion.equals("E"))
    {
        lis.edit(jv_frm_id, jv_frm_paymenttype, jv_frm_name, jv_frm_desc, tipo_i, tipo_e, jv_frm_ccc, jv_frm_cca);
    }else if (jv_opcion.equals("D"))
    {
        lis.delete(jv_frm_id);
    }
    
    out.print(lis.Data());
%>