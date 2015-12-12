<%@ page import="Database.*"%>
<%@ page import="Masters_Data.*"%>
<%
    response.setHeader("Cache-Control","no-cache"); 
    response.setHeader("Pragma","no-cache"); 
    response.setDateHeader ("Expires", -1);
    
    daymovements_header lis = new daymovements_header();
    
    String jv_usr = (String)session.getAttribute("user_cod");
    if(jv_usr==null || jv_usr.length()==0){jv_usr="";}
    
    //Formulario
    String jv_opcion = (request.getParameter("opcion") != null) ? request.getParameter("opcion"): "";
    String jv_id = (request.getParameter("id") != null) ? request.getParameter("id"): "";
    String jv_residencial = (request.getParameter("residencial") != null) ? request.getParameter("residencial"): "";
    String jv_periodo= (request.getParameter("periodo") != null) ? request.getParameter("periodo"): "";
    String jv_frm_num = (request.getParameter("frm_num") != null) ? request.getParameter("frm_num"): "";
    String jv_frm_name = (request.getParameter("frm_name") != null) ? request.getParameter("frm_name"): "";
    String jv_mes_trans = (request.getParameter("mes_trans") != null) ? request.getParameter("mes_trans"): "";
    String jv_ano_trans = (request.getParameter("ano_trans") != null) ? request.getParameter("ano_trans"): "";
    String jv_frm_date = (request.getParameter("frm_date") != null) ? request.getParameter("frm_date"): "";
    String jv_estado = (request.getParameter("estado") != null) ? request.getParameter("estado"): "D";
    
    if (jv_opcion.equals("A"))
    {
        lis.add(jv_residencial, jv_frm_num, jv_periodo.split(",")[1], jv_frm_date,jv_ano_trans, jv_mes_trans, jv_periodo.split(",")[2], jv_usr, jv_frm_name);
    }else if (jv_opcion.equals("E"))
    {
        lis.edit(jv_residencial, jv_frm_num, jv_periodo.split(",")[1], jv_frm_date, jv_ano_trans, jv_mes_trans, jv_periodo.split(",")[2], jv_frm_name);
    }else if (jv_opcion.equals("D"))
    {
        lis.delete(jv_residencial, jv_frm_num);
    }else if (jv_opcion.equals("AP"))
    {
        lis.apply(jv_residencial, jv_frm_num);
    }else if (jv_opcion.equals("RP"))
    {
        //lis.revert(jv_residencial, jv_frm_num);
        lis.add(jv_residencial, jv_frm_num, jv_periodo.split(",")[1], jv_frm_date,jv_ano_trans, jv_mes_trans, jv_periodo.split(",")[2], jv_usr, jv_frm_name);
    }
    
    if (jv_opcion.equals("CM"))
    {
        response.setContentType("text/plain; charset=utf-8");
        out.print(lis.CheckMovementNumber(jv_residencial, jv_frm_num, jv_periodo.split(",")[1]));
    }else{
        response.setContentType("application/json; charset=utf-8");
        out.print(lis.Data());
    }
%>