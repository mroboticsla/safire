<!DOCTYPE html>
<%@ page import="Masters_Data.*"%>
<%
    mayorizacion ma = new mayorizacion();
    String cod_residencial = (String)session.getAttribute("residencial");
    if(cod_residencial==null || cod_residencial.length()==0){cod_residencial="";}
%>
<html>
    <head>
            <meta charset="UTF-8">
            <title>Formas de Pago</title>
            <link href="../../js/development/themes/rocket/theme.css" rel="stylesheet" type="text/css"/>
            <link href="../../js/development/primeui-1.1.css" rel="stylesheet" type="text/css"/>
            <link href="../../js/development/jquery-ui.css" rel="stylesheet" type="text/css"/>
            <script type="text/javascript" src="../../js/jquery.min.js"></script>
            <script type="text/javascript" src="../../js/jquery-ui.min.js"></script>
            <script type="text/javascript" src="../../js/development/primeui-1.1.js"></script>
            <script type="text/javascript" src="../../js/development/js/panel/panel.js"></script>
            <script src="../../js/development/js/button/button.js" type="text/javascript"></script>
            <script src="../../js/development/js/growl/growl.js" type="text/javascript"></script>
            
            <link rel="stylesheet" type="text/css" href="../../themes/metro-gray/easyui.css">
            <link rel="stylesheet" type="text/css" href="../../themes/icon.css">
            <link rel="stylesheet" type="text/css" href="../../resources/css/demo.css">
            <script type="text/javascript" src="../../js/jquery.easyui.min.js"></script>
            <script type="text/javascript" src="../../js/functions.js"></script>
            <script type="text/javascript" src="../../js/datagrid-filter.js"></script>
            <script type="text/javascript" src="../../js/masters/mayorizacion.js"></script>
    </head>
    <body onload="javascript:LoadData();">
        
        <input type="hidden" id="opcion" name="opcion" value="" />
        <input type="hidden" id="nivel" name="nivel" value="1" />
        
        <div id="data_entry" title="Modulo de Cierre y Mayorizacion" style="min-width: 700px; border: 0">
            <table>
                <tr>
                    <th>Periodo Actual:</th>
                    <td><%=ma.AnoVigente(cod_residencial)%></td>
                    <td style="width: 50px;"></td>
                    <th>Mes Activo:</th>
                    <td><%=ma.MesVigente(cod_residencial)%></td>
                </tr>
            </table>
        </div>
                
        <div id="operations" title="Operaciones de Periodo Contable" style="min-width: 700px; border: 0">
            <table>
                <tr>
                    <td style="width: 33%; text-align: center;"><a href="javascript:void(0)" id="btnMayorizar" name="btnMayorizar" onClick="mayorizar()">Mayorizar</a></td>
                    <td style="width: 33%; text-align: center;"><a href="javascript:void(0)" id="btnDesMayorizar" name="btnDesMayorizar" onClick="desmayorizar()">Desmayorizar</a></td>
                    <th style="text-align: right;">Per&iacute;odo:</th>
                    <td>
                        <input class="easyui-combobox" 
                                name="periodo"
                                id="periodo"
                                style="width: 150px; height: 20px"
                                data-options="
                                    url:'ccalendar_data.jsp?activeOnly=true',
                                    method:'get',
                                    valueField:'combo_cod',
                                    textField:'combo_nom',
                                    panelHeight:'auto',
                                    required:true, 
                                    missingMessage:'Campo obligatorio',
                                    onSelect: function(record){
                                                        //CheckLevelLenght();
                                                    }
                                ">
                    </td>
                    <td style="width: 33%; text-align: center;"><a href="javascript:void(0)" id="btnCerrar" name="btnCerrar" onClick="cerrar_periodo()">Cerrar Periodo Contable</a></td>
                </tr>
            </table>
        </div>
                
        <div id="data" title="Resumen de Cuentas de Mayor" style="min-width: 700px;">
            <table>
                <tr>
                    <td><a href="javascript:void(0)" id="btnN1" name="btnMayorizar" onClick="setLevel('1')">Nivel 1</a></td>
                    <td><a href="javascript:void(0)" id="btnN2" name="btnMayorizar" onClick="setLevel('2')">Nivel 2</a></td>
                    <td><a href="javascript:void(0)" id="btnN3" name="btnMayorizar" onClick="setLevel('3')">Nivel 3</a></td>
                    <td><a href="javascript:void(0)" id="btnN4" name="btnMayorizar" onClick="setLevel('4')">Nivel 4</a></td>
                    <td><a href="javascript:void(0)" id="btnN5" name="btnMayorizar" onClick="setLevel('5')">Nivel 5</a></td>
                </tr>
            </table>
            <table class="easyui-datagrid"  border="false" id="dataTable" showHeader="true" title="" iconcls="icon-search"
                    data-options="singleSelect:true,collapsible:false,resizable:false, remoteSort:false, rownumbers: true,height: '180'">
                  <thead>
                      <tr style="height:auto;">
                          <th data-options="field:'cod_cta_conta',align:'left'" sortable="true">
                              <strong>Cuenta Contable</strong>
                          </th>
                          <th data-options="field:'desc_cta_contab',align:'left'" sortable="true">
                              <strong>Nombre de Cuenta</strong>
                          </th>
                          <th data-options="field:'cod_tipo_cta',align:'left'" sortable="true">
                              <strong>Tipo de Cuenta</strong>
                          </th>
                          <th data-options="field:'saldo',align:'left'" formatter="moneyFormat" sortable="true">
                              <strong>Saldo</strong>
                          </th>
                          <th data-options="field:'debe',align:'left'" formatter="moneyFormat" sortable="true">
                              <strong>Debe</strong>
                          </th>
                          <th data-options="field:'haber',align:'left'" formatter="moneyFormat" sortable="true">
                              <strong>Haber</strong>
                          </th>
                      </tr>
                  </thead>
            </table>
        </div>
        <div id="dlgProcesando" title="SAFIRE">
            Se est&aacute; procesando la informaci&oacute;n... 
        </div>
        <div id="dlgMayorizacion" title="SAFIRE">
            Se har&aacute; el proceso de mayorizaci&oacute;n, ¿Desea continuar? 
        </div>
        <div id="dlgDesMayorizacion" title="SAFIRE">
            Est&aacute; a punto de revertir el proceso de mayorizaci&oacute;n, ¿Desea continuar? 
        </div>
        <div id="dlgCierre" title="SAFIRE">
            Est&aacute; a punto de cerrar el per&iacute;odo contable. Este proceso no es reversible, ¿Desea continuar? 
        </div>
        <div id="default" style="z-index: 99999;"></div>
    </body>
</html>