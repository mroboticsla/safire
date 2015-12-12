<%@ page import="Database.*"%>
<%@ page import="Masters_Data.*"%>
<%
    response.setHeader("Cache-Control","no-cache"); 
    response.setHeader("Pragma","no-cache"); 
    response.setDateHeader ("Expires", -1);
    
    accounts lis = new accounts();
    
    String jv_usr = (String)session.getAttribute("user_cod");
    if(jv_usr==null || jv_usr.length()==0){jv_usr="";}
    
    //Formulario
    String jv_opcion = (request.getParameter("opcion") != null) ? request.getParameter("opcion"): "";
    String jv_residencial = (request.getParameter("residencial") != null) ? request.getParameter("residencial"): "";
    String jv_residencial_de = (request.getParameter("residencial_de") != null) ? request.getParameter("residencial_de"): "";
    String jv_id = (request.getParameter("id") != null) ? request.getParameter("id"): "";
    String jv_num = (request.getParameter("num") != null) ? request.getParameter("num"): "";
    String jv_desc = (request.getParameter("desc") != null) ? request.getParameter("desc"): "";
    String jv_dh = (request.getParameter("dh") != null) ? request.getParameter("dh"): "";
    String jv_tipo = (request.getParameter("tipo") != null) ? request.getParameter("tipo"): "";
    String jv_manejo = (request.getParameter("manejo") != null) ? request.getParameter("manejo"): "";
    String jv_acepta_mov = (request.getParameter("acepta_mov") != null) ? request.getParameter("acepta_mov"): "";
    String jv_nivel = (request.getParameter("nivel") != null) ? request.getParameter("nivel"): "";
    String jv_cod_cta_mayor = (request.getParameter("cta_mayor") != null) ? request.getParameter("cta_mayor"): "";
    String jv_tamano_cod_cta = (request.getParameter("tamano_cod_cta") != null) ? request.getParameter("tamano_cod_cta"): "";
    
    if (jv_opcion.equals("A"))
    {
        String ad = (jv_dh.equals("D"))?"S":"N";
        String ah = (jv_dh.equals("H"))?"S":"N";
        
        lis.add(jv_residencial, jv_num, jv_desc, jv_tipo, jv_acepta_mov, ad, ah, jv_nivel, jv_cod_cta_mayor, jv_tamano_cod_cta, jv_manejo, jv_usr);
    }else if (jv_opcion.equals("E"))
    {
        String ad = (jv_dh.equals("D"))?"S":"N";
        String ah = (jv_dh.equals("H"))?"S":"N";
        
        lis.edit(jv_residencial, jv_id, jv_desc, jv_tipo, jv_acepta_mov, ad, ah, jv_manejo);
    }else if (jv_opcion.equals("D"))
    {
        lis.delete(jv_residencial, jv_id);
    }else if (jv_opcion.equals("DE"))
    {
        String ad = (jv_dh.equals("D"))?"S":"N";
        String ah = (jv_dh.equals("H"))?"S":"N";
        
        lis.add(jv_residencial_de, jv_cod_cta_mayor + jv_num, jv_desc, jv_tipo, jv_acepta_mov, ad, ah, jv_nivel, jv_cod_cta_mayor, jv_tamano_cod_cta, jv_manejo, jv_usr);
    }
    
    if (jv_opcion.equals("CL"))
    {
        response.setContentType("text/plain; charset=utf-8");
        String res_value = (jv_residencial.equals(""))?jv_residencial_de:jv_residencial;
        out.print(lis.CheckLevelLenght(res_value, jv_nivel));
    }else if (jv_opcion.equals("CA"))
    {
        response.setContentType("text/plain; charset=utf-8");
        String res_value = (jv_residencial.equals(""))?jv_residencial_de:jv_residencial;
        out.print(lis.CheckAccountNumber(res_value, jv_cod_cta_mayor + jv_num));
    }else{
        response.setContentType("application/json; charset=utf-8");
        if (!jv_cod_cta_mayor.equals(""))
        {
            out.print(lis.DataNL(jv_residencial_de, jv_nivel, jv_cod_cta_mayor));
        }else{
            out.print(lis.Data(jv_nivel));
        }
    }
%>