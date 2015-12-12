<%@ page import="Database.*"%>
<%@ page import="Masters_Data.*"%>
<%
    ccalendar lis = new ccalendar();
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
    
    String jv_frm_year = request.getParameter("frm_year");
    if(jv_frm_year==null || jv_frm_year.length()==0){jv_frm_year = "";}
    
    String jv_frm_month = request.getParameter("frm_month");
    if(jv_frm_month==null || jv_frm_month.length()==0){jv_frm_month = "";}
    
    String jv_frm_first = request.getParameter("frm_first");
    if(jv_frm_first==null || jv_frm_first.length()==0){jv_frm_first = "";}
    
    String jv_frm_last = request.getParameter("frm_last");
    if(jv_frm_last==null || jv_frm_last.length()==0){jv_frm_last = "";}
    
    String jv_frm_active = request.getParameter("frm_active");
    if(jv_frm_active==null || jv_frm_active.length()==0){jv_frm_active = "";}
    
    String jv_activeOnly = request.getParameter("activeOnly");
    if(jv_activeOnly==null || jv_activeOnly.length()==0){jv_activeOnly = "";}
    
    Boolean activeOnly = false;
    if (jv_activeOnly.equals("true")) activeOnly = true;
    
    if (jv_opcion.equals("A"))
    {
        lis.add(jv_frm_res, jv_frm_year, jv_frm_month, jv_frm_first, jv_frm_last, jv_frm_active, jv_usr);
    }else if (jv_opcion.equals("E"))
    {
        lis.edit(jv_frm_res, jv_frm_year, jv_frm_month, jv_frm_active, jv_usr);
    }else if (jv_opcion.equals("D"))
    {
        lis.close(jv_frm_res, jv_frm_year, jv_frm_month);
    }
    
    out.print(lis.Data(activeOnly));
%>