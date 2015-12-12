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
            <script type="text/javascript" src="../../js/masters/paymenttypes.js"></script>
            
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
            <div data-options="region:'north'" title="" style="height:100px">
                <div id="data_entry" title="Agregar Formas de Pago">
                    <form id="ff" method="post">
                        <input type="hidden" id="opcion" name="opcion" value="" />
                        <input type="hidden" id="frm_id" name="frm_id" value="" />
                        <table>
                            <tr>
                                <td>Nombre:</td>
                                <td><input style="width:300px;" class="easyui-validatebox" type="text" id="frm_name" name="frm_name" data-options="required:true, missingMessage:'Campo obligatorio'" /></td>
                                <td><input type="checkbox" id="info_req" value="S"><span>Requiere informaci&oacute;n adicional</span><br/></td>
                                <td><a href="javascript:void(0)" id="btnSave" name="btnSave" onClick="save()">Guardar</a></td>
                                <td><a href="javascript:void(0)" id="btnClean" name="btnClean" onClick="clearForm()">Limpiar</a></td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
            <div data-options="region:'center',split:true" title="">
                <div id="tableContainer" title="Catalogo de Formas de Pago" style="height: 95%;">
                    <table class="easyui-datagrid"  border="false" id="dataTable" showHeader="true" title="" iconcls="icon-search" fit="true"
                       data-options="singleSelect:true,collapsible:false,resizable:false, remoteSort:false">
                        <thead>
                            <tr style="height:auto;">
                                <th data-options="field:'corr_forma_pago',align:'left', width:70" sortable="true">
                                    <strong>Correlativo</strong>
                                </th>
                                <th data-options="field:'desc_forma_pago',align:'left', width:190" sortable="true">
                                    <strong>Descripci&oacute;n</strong>
                                </th>
                                <th data-options="field:'info_adicional',align:'left'" sortable="true">
                                    <strong>Requiere Inf. Adicional</strong>
                                </th>
                                <th data-options="field:'fecha_creación',align:'left'" sortable="true">
                                    <strong>Fecha de Creaci&oacute;n</strong>
                                </th>
                                <th data-options="field:'logo',align:'left', width:50">
                                    <strong>&nbsp;</strong>
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