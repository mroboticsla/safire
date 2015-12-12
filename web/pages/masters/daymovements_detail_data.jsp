<%@ page import="Database.*"%>
<%@ page import="Masters_Data.*"%>
<%
    response.setHeader("Cache-Control","no-cache"); 
    response.setHeader("Pragma","no-cache"); 
    response.setDateHeader ("Expires", -1);
    
    daymovements_details lis = new daymovements_details();
    
    String jv_usr = (String)session.getAttribute("user_cod");
    if(jv_usr==null || jv_usr.length()==0){jv_usr="";}
    
    //Formulario
    String jv_mopcion = (request.getParameter("mopcion") != null) ? request.getParameter("mopcion"): "";
    String jv_id = (request.getParameter("mid") != null) ? request.getParameter("mid"): "";
    String jv_residencial = (request.getParameter("residencial") != null) ? request.getParameter("residencial"): "";
    String jv_periodo= (request.getParameter("periodo") != null) ? request.getParameter("periodo"): "";
    String jv_frm_ccc= (request.getParameter("frm_ccc") != null) ? request.getParameter("frm_ccc"): "";
    String jv_frm_num = (request.getParameter("frm_num") != null) ? request.getParameter("frm_num"): "";
    String jv_frm_desc = (request.getParameter("frm_desc") != null) ? request.getParameter("frm_desc"): "";
    String jv_frm_valor = (request.getParameter("frm_valor") != null) ? request.getParameter("frm_valor"): "";
    String jv_dc = (request.getParameter("dc") != null) ? request.getParameter("dc"): "";
    String jv_frm_movref = (request.getParameter("frm_movref") != null) ? request.getParameter("frm_movref"): "";
    
    String debe = "0";
    String haber = "0";

    if (jv_dc.equals("D")){
        debe = jv_frm_valor;
    }else{
        haber = jv_frm_valor;
    } 
    
    if (jv_mopcion.equals("A"))
    {
        lis.add(jv_residencial, jv_frm_num, jv_periodo.split(",")[1], jv_frm_ccc, debe, haber, jv_frm_desc, jv_frm_movref);
    }else if (jv_mopcion.equals("E"))
    {
        lis.edit(jv_residencial,jv_frm_num, jv_periodo.split(",")[1], jv_id, jv_frm_ccc, debe, haber, jv_frm_desc, jv_frm_movref);
    }else if (jv_mopcion.equals("D"))
    {
        lis.delete(jv_residencial,jv_frm_num, jv_periodo.split(",")[1], jv_id);
    }
    
    response.setContentType("application/json; charset=utf-8");
    out.print(lis.Data(jv_residencial, jv_frm_num));
%>