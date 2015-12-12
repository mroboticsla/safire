<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Cuentas Contables</title>
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
        <script type="text/javascript" src="../../js/masters/accounts.js"></script>

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
                $('#btnInicio').puibutton();
                $('#btnReport').puibutton();

                $('#default').puigrowl();  
            });  
        </script>
    </head>
    <body onload="javascript:LoadData();">
        <div class="easyui-layout" fit="true">
            <div data-options="region:'north'" title="" style="height:105px">
                <div id="data_entry" title="Datos de la Cuenta">
                    <input type="hidden" id="opcion" name="opcion" value="" />
                    <input type="hidden" id="id" name="id" value="" />
                    <input type="hidden" id="nivel" name="nivel" value="1" />
                    <input type="hidden" id="residencial_de" name="residencial_de" value="" />
                    <input type="hidden" id="cta_mayor" name="cta_mayor" value="" />
                    <input type="hidden" id="cta_menor" name="cta_menor" value="" />
                    <input type="hidden" id="saldo" name="saldo" value="" />
                    
                    <form id="ff" method="post">
                        <table style="width: 100%;">
                            <tr>
                                <td>Residencial:</td>
                                <td>
                                    <input class="easyui-combobox" 
                                            name="residencial"
                                            id="residencial"
                                            style="width: 150px; height: 20px"
                                            data-options="
                                                url:'residencial_data.jsp',
                                                method:'get',
                                                valueField:'cod_residencial',
                                                textField:'nombre_residencial',
                                                panelHeight:'auto',
                                                required:true, 
                                                missingMessage:'Campo obligatorio',
                                                onSelect: function(record){
                                                                    CheckLevelLenght();
                                                                }
                                            ">
                                </td>
                                <td>N°de Cuenta:</td>
                                <td><input class="easyui-validatebox" type="text" size="4" id="num" name="num" data-options="required:true, missingMessage:'Campo obligatorio'" /></td>
                                <td>Descripcion:</td>
                                <td style="width: 50%"><input style="width:95%;" class="easyui-validatebox" type="text" id="desc" name="desc" data-options="required:true, missingMessage:'Campo obligatorio'" /></td>
                                <td><input type="radio" name="dh" value="D" checked="checked"><span>Debe</span><br/></td>
                                <td><input type="radio" name="dh" value="H"><span>Haber</span><br/></td>
                            </tr>
                        </table>
                        <table style="width: 100%;">
                            <tr>
                                <td>Tipo de Cuenta:</td>
                                <td>
                                    <select class="easyui-combobox" id="tipo" style="width: 150px; height: 20px" 
                                            data-options="required:true, 
                                                          missingMessage:'Campo obligatorio'">
                                        <option value="" selected="true">Ninguno</option>
                                        <option value="A">Activo</option>
                                        <option value="P">Pasivo</option>
                                        <option value="C">Capital</option>
                                        <option value="I">Ingreso</option>
                                        <option value="E">Egreso</option>
                                        <option value="V">Ventas</option>
                                    </select>
                                </td>
                                <td>Manejo de Cuenta:</td>
                                <td>
                                    <select class="easyui-combobox" id="manejo" style="width: 150px; height: 20px" 
                                            data-options="required:true, 
                                                          missingMessage:'Campo obligatorio'">
                                        <option value="E" selected="true">Estado de Cuenta</option>
                                        <option value="B">Balance General</option>
                                        <option value="O">Otros</option>
                                    </select>
                                </td>
                                <td><input type="checkbox" id="acepta_mov" value="S"><span>Acepta Movimientos</span><br/></td>
                                <td style="text-align: right">
                                    <a href="javascript:void(0)" id="btnSave" name="btnSave" onClick="save()">Guardar</a>
                                    <a href="javascript:void(0)" id="btnClean" name="btnClean" onClick="clearForm()">Limpiar</a>
                                    <a href="javascript:void(0)" id="btnInicio" name="btnInicio" onClick="initForm()">Ir a Nivel 1</a>
                                    <a href="javascript:void(0)" id="btnReport" name="btnReport" onClick="showReport()">Reporte de Cat&aacute;logo de Cuentas</a>
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
            <div data-options="region:'center',split:true" title="">
                <div id="tableContainer" title="Catálogo de Cuentas - Nivel 1" style="height: 90%;">
                    <table class="easyui-datagrid"  border="false" id="dataTable" showHeader="true" title="" iconcls="icon-search" fit="true"
                        data-options="singleSelect:true,collapsible:false,resizable:false, remoteSort:false">
                         <thead>
                             <tr style="height:auto;">
                                 <th data-options="field:'cod_cta_conta',align:'left'" sortable="true">
                                     <strong>No. Cuenta</strong>
                                 </th>
                                 <th data-options="field:'nombre_residencial',align:'left'" sortable="true">
                                     <strong>Residencial</strong>
                                 </th>
                                 <th data-options="field:'desc_cta_contab',align:'left', width:200" sortable="true">
                                     <strong>Descripcion</strong>
                                 </th>
                                 <th data-options="field:'cod_tipo_cta',align:'left', width:115" sortable="true">
                                     <strong>Tipo de Cuenta</strong>
                                 </th>
                                 <th data-options="field:'fecha_creacion',align:'left', width:150" sortable="true">
                                     <strong>Fecha de Creacion</strong>
                                 </th>
                                 <th data-options="field:'acepta_movs',align:'left', width:150" sortable="true">
                                     <strong>Acepta Movimientos</strong>
                                 </th>
                                 <th data-options="field:'edit',align:'left', width:20">
                                     <strong>&nbsp;</strong>
                                 </th>
                                 <th data-options="field:'logo',align:'left', width:20">
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