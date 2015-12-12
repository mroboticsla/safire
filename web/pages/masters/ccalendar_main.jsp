<!DOCTYPE html>
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
            <script type="text/javascript" src="../../js/masters/ccalendar.js"></script>
            
            <script type="text/javascript">  
                $(function() {
                    $('#data_entry').puipanel({
                        toggleable: false  
                        ,closable: false  
                    }); 
                    
                    $('#tableContainer').puipanel({
                        toggleable: false  
                        ,closable: false  
                    });
                    
                    $('#btnSave').puibutton(); 
                    $('#btnClean').puibutton();
                    
                    $('#default').puigrowl();  
                });  
            </script>
    </head>
    <body onload="javascript:LoadData();">
        <div id="roleLayout" class="easyui-layout" fit="true">
            <div data-options="region:'west'" title="" style="height:105px; width: 320px">
                <div id="data_entry" title="Agregar Transaccion">
                    <form id="ff" method="post">
                        
                        <input type="hidden" id="opcion" name="opcion" value="" />
                        <input type="hidden" id="frm_closed" name="frm_closed" value="" />
                        
                        <table style="width: 300px;">
                            <tr>
                                <th style="text-align: left;">Residencial</th>
                                <td>
                                    <input class="easyui-combobox" 
                                        name="language"
                                        id="frm_res"
                                        data-options="
                                            url:'residencial_data.jsp',
                                            method:'get',
                                            valueField:'cod_residencial',
                                            textField:'nombre_residencial',
                                            panelHeight:'auto',
                                            required:true, 
                                            missingMessage:'Campo obligatorio'
                                        ">
                                </td>
                            </tr>
                            <tr>
                                <th style="text-align: left;">A&ntilde;o:</th>
                                <td>
                                    <input class="easyui-numberspinner" id="frm_year" name="frm_year" 
                                           data-options="min:2000,max:2099,
                                                            required:true, 
                                                            missingMessage:'Campo obligatorio',
                                                            onChange: function(newValue, oldValue){
                                                                        calculateRange($('#frm_month').combo('getValue'));
                                                                      }
                                                        "
                                           style="width:80px;" />
                                </td>
                            </tr>
                            <tr>
                                <th style="text-align: left;">Mes:</th>
                                <td>
                                    <select class="easyui-combobox" id="frm_month" name="frm_month" style="width:200px;"
                                            data-options="onSelect: function(record){
                                                                calculateRange(record.value);
                                                            }, editable: true">
                                        <option value="1">Enero</option>
                                        <option value="2">Febrero</option>
                                        <option value="3">Marzo</option>
                                        <option value="4">Abril</option>
                                        <option value="5">Mayo</option>
                                        <option value="6">Junio</option>
                                        <option value="7">Julio</option>
                                        <option value="8">Agosto</option>
                                        <option value="9">Septiembre</option>
                                        <option value="10">Octubre</option>
                                        <option value="11">Noviembre</option>
                                        <option value="12">Diciembre</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <th style="text-align: left;">Dia Inicial:</th>
                                <td style="text-align: left;">
                                    <span id="AutoGeneratedTextStart" style="font-family: monospace; font-weight: bold;">
                                        
                                    </span>
                                </td>
                            </tr>
                            <tr>
                                <th style="text-align: left;">Dia Final:</th>
                                <td style="text-align: left;">
                                    <span id="AutoGeneratedTextEnd" style="font-family: monospace; font-weight: bold;">
                                        
                                    </span>
                                </td>
                            </tr>
                            <tr>
                                <th style="text-align: left;">Activo:</th>
                                <td style="text-align: left;">
                                    <input type="checkbox" id="frm_active" value="1">
                                </td>
                            </tr>
                            <tr>
                                <td style="text-align: center">
                                    <a href="javascript:void(0)" id="btnSave" name="btnSave" onClick="save()">Guardar</a>
                                </td>
                                <td style="text-align: center">
                                    <a href="javascript:void(0)" id="btnClean" name="btnClean" onClick="clearForm()">Limpiar</a>
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
            <div data-options="region:'center',split:true" title="">
                <div id="tableContainer" title="Listado de Meses Contables" style="height: 80%; overflow: visible;">
                    <table class="easyui-datagrid"  border="false" id="dataTable" showHeader="true" title="" iconcls="icon-search" fit="true"
                       data-options="singleSelect:true,collapsible:false,resizable:false, remoteSort:false" rownumbers="true">
                        <thead>
                            <tr style="height:auto;">
                                <th data-options="field:'ano_periodo',align:'left'" sortable="true">
                                    <strong>A&ntilde;o</strong>
                                </th>
                                <th data-options="field:'nom_mes_periodo',align:'left'" sortable="true">
                                    <strong>Mes</strong>
                                </th>
                                <th data-options="field:'nom_activo',align:'center'">
                                    <strong>Activo</strong>
                                </th>
                                <th data-options="field:'nom_cerrado',align:'center'">
                                    <strong>Cerrado</strong>
                                </th>
                            </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
        <div id="default" />
    </body>
</html>