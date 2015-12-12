<!DOCTYPE html>
<html>
    <head>
            <meta charset="UTF-8">
            <title>Configurar Cuentas</title>
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
            <script type="text/javascript" src="../../js/masters/accountlevel.js"></script>
            
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
    <body onload="LoadData();">
        <div class="easyui-layout" fit="true">
            <div data-options="region:'north'" title="" style="height:190px">
                <div id="data_entry" title="Configurar Longitud de Niveles de Cuenta">
                    <form id="ff" method="post">
                        <input type="hidden" id="opcion" name="opcion" value="" />
                        <input type="hidden" id="frm_id" name="frm_id" value="" />
                        <table style="width: 95%">
                            <tr>
                                <td>Residencial</td>
                                <td colspan="11">
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
                                <td>Nivel 1:</td>
                                <td><input style="width:50px;" class="easyui-numberbox" precision="0" type="text" id="frm_l1" name="frm_l1" value="0" data-options="required:true, missingMessage:'Campo obligatorio', min:0, onChange: function(newValue,oldValue){showPreview();}" /></td>
                                <td>Nivel 2:</td>
                                <td><input style="width:50px;" class="easyui-numberbox" precision="0" type="text" id="frm_l2" name="frm_l2" value="0" data-options="required:true, missingMessage:'Campo obligatorio', min:0, onChange: function(newValue,oldValue){showPreview();}" /></td>
                                <td>Nivel 3:</td>
                                <td><input style="width:50px;" class="easyui-numberbox" precision="0" type="text" id="frm_l3" name="frm_l3" value="0" data-options="required:true, missingMessage:'Campo obligatorio', min:0, onChange: function(newValue,oldValue){showPreview();}" /></td>
                                <td>Nivel 4:</td>
                                <td><input style="width:50px;" class="easyui-numberbox" precision="0" type="text" id="frm_l4" name="frm_l4" value="0" data-options="required:true, missingMessage:'Campo obligatorio', min:0, onChange: function(newValue,oldValue){showPreview();}" /></td>
                                <td>Nivel 5:</td>
                                <td><input style="width:50px;" class="easyui-numberbox" precision="0" type="text" id="frm_l5" name="frm_l5" value="0" data-options="required:true, missingMessage:'Campo obligatorio', min:0, onChange: function(newValue,oldValue){showPreview();}" /></td>
                                <td>Nivel 6:</td>
                                <td><input style="width:50px;" class="easyui-numberbox" precision="0" type="text" id="frm_l6" name="frm_l6" value="0" data-options="required:true, missingMessage:'Campo obligatorio', min:0, onChange: function(newValue,oldValue){showPreview();}" /></td>
                                <td><a href="javascript:void(0)" id="btnSave" name="btnSave" onClick="save()">Guardar</a></td>
                                <td><a href="javascript:void(0)" id="btnClean" name="btnClean" onClick="clearForm()">Limpiar</a></td>
                            </tr>
                            <tr>
                                <td colspan="12" style="text-align: center;">
                                    <div id="p" class="easyui-panel" title="" 
                                            style="width:400px;height:60px;padding:10px;background:#fafafa; text-align: center;"
                                            data-options="closable:false,fit:true,
                                                    collapsible:false,minimizable:false,maximizable:false">
                                        <p id="sampleTxt" style="font-weight: bold; font-size: large;">XXXX-XXXX-XXXX-XXXX-XXXX</p>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
            <div data-options="region:'center',split:true" title="">
                <div id="tableContainer" title="Configuracion de Cuentas Contables" style="height: 90%">
                    <table class="easyui-datagrid"  border="false" id="dataTable" showHeader="true" title="" iconcls="icon-search" fit="true"
                       data-options="singleSelect:true,collapsible:false,resizable:false, remoteSort:false">
                        <thead>
                            <tr style="height:auto;">
                                <th data-options="field:'nombre_residencial',align:'left', width:250" sortable="true">
                                    <strong>RESIDENCIAL</strong>
                                </th>
                                <th data-options="field:'nivel1',align:'left', width:80">
                                    <strong>NIVEL 1</strong>
                                </th>
                                <th data-options="field:'nivel2',align:'left', width:80">
                                    <strong>NIVEL 2</strong>
                                </th>
                                <th data-options="field:'nivel3',align:'left', width:80">
                                    <strong>NIVEL 3</strong>
                                </th>
                                <th data-options="field:'nivel4',align:'left', width:80">
                                    <strong>NIVEL 4</strong>
                                </th>
                                <th data-options="field:'nivel5',align:'left', width:80">
                                    <strong>NIVEL 5</strong>
                                </th>
                                <th data-options="field:'nivel6',align:'left', width:80">
                                    <strong>NIVEL 6</strong>
                                </th>
                                <th data-options="field:'logo',align:'left', width:80">
                                    <strong>&nbsp;</strong>
                                </th>
                            </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>