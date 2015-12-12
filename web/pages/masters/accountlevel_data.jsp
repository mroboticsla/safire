<%@ page import="Database.*"%>
<%@ page import="Masters_Data.*"%>
<%
    accountLevel lis = new accountLevel();
    
    response.setHeader("Cache-Control","no-cache"); 
    response.setHeader("Pragma","no-cache"); 
    response.setDateHeader ("Expires", -1);
    response.setContentType("application/json; charset=utf-8");
    
    String jv_usr = (String)session.getAttribute("user_cod");
    if(jv_usr==null || jv_usr.length()==0){jv_usr="";}
    
    //Formulario
    String jv_opcion= request.getParameter("opcion");
    if(jv_opcion==null || jv_opcion.length()==0){jv_opcion="";}
    
    String jv_frm_res = request.getParameter("frm_res");
    if(jv_frm_res==null || jv_frm_res.length()==0){jv_frm_res = "";}
    
    String jv_frm_l1 = request.getParameter("frm_l1");
    if(jv_frm_l1==null || jv_frm_l1.length()==0){jv_frm_l1 = "";}
    
    String jv_frm_l2 = request.getParameter("frm_l2");
    if(jv_frm_l2==null || jv_frm_l2.length()==0){jv_frm_l2="";}
    
    String jv_frm_l3 = request.getParameter("frm_l3");
    if(jv_frm_l3==null || jv_frm_l3.length()==0){jv_frm_l3="";}
    
    String jv_frm_l4 = request.getParameter("frm_l4");
    if(jv_frm_l4==null || jv_frm_l4.length()==0){jv_frm_l4="";}
    
    String jv_frm_l5 = request.getParameter("frm_l5");
    if(jv_frm_l5==null || jv_frm_l5.length()==0){jv_frm_l5="";}
    
    String jv_frm_l6 = request.getParameter("frm_l6");
    if(jv_frm_l6==null || jv_frm_l6.length()==0){jv_frm_l6="";}
    
    if (jv_opcion.equals("A"))
    {
        lis.add(jv_frm_res,jv_frm_l1,jv_frm_l2,jv_frm_l3,jv_frm_l4,jv_frm_l5,jv_frm_l6);
    }else if (jv_opcion.equals("E"))
    {
        lis.edit(jv_frm_res,jv_frm_l1,jv_frm_l2,jv_frm_l3,jv_frm_l4,jv_frm_l5,jv_frm_l6);
    }else if (jv_opcion.equals("D"))
    {
        lis.delete(jv_frm_res);
    }
    
    out.print(lis.Data());
%>