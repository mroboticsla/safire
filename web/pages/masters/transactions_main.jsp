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
            <script type="text/javascript" src="../../js/masters/transactions.js"></script>
            
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
            <div data-options="region:'north'" title="" style="height:290px">
                <div id="data_entry" title="Agregar Transaccion">
                    <form id="ff" method="post">
                        <input type="hidden" id="opcion" name="opcion" value="" />
                        <input type="hidden" id="frm_id" name="frm_id" value="" />
                        <table style="width: 95%;">
                            <tr>
                                <th style="text-align: left;">Residencial</th>
                                <td colspan="3">
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
                                <th style="text-align: left;">Forma de Pago:</th>
                                <td colspan="2">
                                    <input class="easyui-combobox" 
                                        name="paymenttype"
                                        id="frm_paymenttype"
                                        data-options="
                                            url:'paymenttypes_data.jsp',
                                            method:'get',
                                            valueField:'corr_forma_pago',
                                            textField:'desc_forma_pago',
                                            panelHeight:'auto',
                                            required:true,
                                            missingMessage:'Campo obligatorio'
                                        ">
                                </td>
                                <td colspan="2">
                                    <table>
                                        <tr>
                                            <th colspan="2">Tipo de Movimiento:</th>
                                        </tr>
                                        <tr>
                                            <td><input type="radio" name="ie" value="I" checked="checked"><span>Ingreso</span><br/></td>
                                            <td><input type="radio" name="ie" value="E"><span>Egreso</span><br/></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <th style="text-align: left;">Nombre de Transacci&oacute;n:</th>
                                <td colspan="3"><input style="width:85%;" class="easyui-validatebox" type="text" id="frm_name" name="frm_name" data-options="required:true, missingMessage:'Campo obligatorio'" /></td>
                            </tr>
                            <tr>
                                <th style="text-align: left;">Concepto de la Transacci&oacute;n:</th>
                                <td colspan="3">
                                    <textarea style="width:85%; height: 50px;" id="frm_desc" name="frm_desc"></textarea>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <table>
                                        <tr>
                                            <th>Cuenta Contable a la que Carga:</th>
                                        </tr>
                                        <tr>
                                            <td>
                                                 <input class="easyui-combobox" 
                                                style="width: 90%;"
                                                name="ccc"
                                                id="frm_ccc"
                                                data-options="
                                                    url:'accounts_dataC.jsp',
                                                    method:'get',
                                                    valueField:'cod_cta_conta',
                                                    textField:'desc_cta_contab',
                                                    panelHeight:'auto',
                                                    required:true, 
                                                    missingMessage:'Campo obligatorio'
                                                ">
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td colspan="2">
                                   <table>
                                        <tr>
                                            <th>Cuenta Contable a la que Abona:</th>
                                        </tr>
                                        <tr>
                                            <td>
                                                 <input class="easyui-combobox" 
                                                style="width: 90%;"
                                                name="cca"
                                                id="frm_cca"
                                                data-options="
                                                    url:'accounts_dataC.jsp',
                                                    method:'get',
                                                    valueField:'cod_cta_conta',
                                                    textField:'desc_cta_contab',
                                                    panelHeight:'auto',
                                                    required:true, 
                                                    missingMessage:'Campo obligatorio'
                                                ">
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="4" style="text-align: center">
                                    <a href="javascript:void(0)" id="btnSave" name="btnSave" onClick="save()">Guardar</a>
                                    <a href="javascript:void(0)" id="btnClean" name="btnClean" onClick="clearForm()">Limpiar</a>
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
            <div data-options="region:'center',split:true" title="">
                <div id="tableContainer" title="Catalogo de Transacciones" style="height: 80%; overflow: visible;">
                    <table class="easyui-datagrid"  border="false" id="dataTable" showHeader="true" title="" iconcls="icon-search" fit="true"
                       data-options="singleSelect:true,collapsible:false,resizable:false, remoteSort:false" rownumbers="true">
                        <thead>
                            <tr style="height:auto;">
                                <th rowspan="2" data-options="field:'cod_transaccion_mask',align:'left'" sortable="true">
                                    <strong>Correlativo</strong>
                                </th>
                                <th rowspan="2" data-options="field:'movimiento',align:'left'" sortable="true">
                                    <strong>Movimiento</strong>
                                </th>
                                <th rowspan="2" data-options="field:'nom_forma_pago',align:'left'" sortable="true">
                                    <strong>Forma de Pago</strong>
                                </th>
                                <th rowspan="2" data-options="field:'desc_transaccion',align:'left'" sortable="true">
                                    <strong>Tipo de Transacci&oacute;n</strong>
                                </th>
                                <th colspan="2">
                                    <strong>Cuenta a Cargar</strong>
                                </th>
                                <th colspan="2">
                                    <strong>Cuenta a Abonar</strong>
                                </th>
                                <th rowspan="2" data-options="field:'logo',align:'left', width:50">
                                    <strong>&nbsp;</strong>
                                </th>
                            </tr>
                            <tr>
                                <th data-options="field:'cod_cta_contab_debe',align:'left'" sortable="true">
                                    <strong>Codigo</strong>
                                </th>
                                <th data-options="field:'nom_cuenta_debe',align:'left'" sortable="true">
                                    <strong>Nombre</strong>
                                </th>
                                <th data-options="field:'cod_cta_contab_haber',align:'left'" sortable="true">
                                    <strong>Codigo</strong>
                                </th>
                                <th data-options="field:'nom_cuenta_haber',align:'left'" sortable="true">
                                    <strong>Nombre</strong>
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