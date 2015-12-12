<!DOCTYPE html>
<html>
    <head>
            <meta charset="UTF-8">
            <title>Modulos y Aplicaciones</title>
            <link rel="stylesheet" type="text/css" href="../../themes/metro-gray/easyui.css">
            <link rel="stylesheet" type="text/css" href="../../themes/icon.css">
            <link rel="stylesheet" type="text/css" href="../../css/demo.css">
            <script type="text/javascript" src="../../js/jquery.min.js"></script>
            <script type="text/javascript" src="../../js/jquery.easyui.min.js"></script>
            <script type="text/javascript" src="../../js/functions.js"></script>
            <script type="text/javascript" src="../../js/masters/modules.js"></script>
            <script type="text/javascript" src="../../js/datagrid-filter.js"></script>
    </head>
    <body onload="javascript:LoadModules();">
        <div class="easyui-layout" fit="true">
            <div data-options="region:'north'" title="Maestro de Modulos y Aplicaciones" style="height:70px">
                <div>
                    <form id="ff" method="post">
                        <input type="hidden" id="opcion" name="opcion" value="" />
                        <input type="hidden" id="frm_id" name="frm_id" value="" />
                        <table>
                            <tr>
                                <td>Nombre:</td>
                                <td><input style="width:300px;" class="easyui-validatebox" type="text" id="frm_name" name="frm_name" data-options="required:true, missingMessage:'Campo obligatorio'" /></td>
                                <td>Orden:</td>
                                <td><input style="width:50px;" class="easyui-validatebox" type="text" id="frm_order" name="frm_order" data-options="required:true, missingMessage:'Campo obligatorio'" /></td>
                                <td><a href="javascript:void(0)" id="btnSave" name="btnSave" class="easyui-linkbutton" onClick="save()">Agregar</a></td>
                                <td><a href="javascript:void(0)" id="btnClean" name="btnClean" class="easyui-linkbutton" onClick="clearForm()">Limpiar</a></td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
            <div data-options="region:'west',split:true" title="Modulos" style="width:390px;">
                <table class="easyui-datagrid"  border="false" id="modules_dataTable" showHeader="true" title="" iconcls="icon-search" fit="true"
                   data-options="singleSelect:true,collapsible:false,resizable:false, remoteSort:false">
                    <thead>
                        <tr style="height:auto;">
                            <th data-options="field:'COD_MODULO',align:'left', width:70" sortable="true">
                                <strong>Codigo</strong>
                            </th>
                            <th data-options="field:'DESC_MODULO',align:'left', width:175" sortable="true">
                                <strong>Nombre</strong>
                            </th>
                            <th data-options="field:'ORDEN_MODULO',align:'left', width:50" sortable="true">
                                <strong>Orden</strong>
                            </th>
                            <th data-options="field:'logo',align:'left', width:50">
                                <strong>&nbsp;</strong>
                            </th>
                        </tr>
                    </thead>
                </table>
            </div>
            <div data-options="region:'center',title:'Main Title',iconCls:'icon-ok'">
                <img src="../../resources/images/delete.png" border=0 width="16" height="16" />
                <%=session.getAttribute("user_name")%>
            </div>
        </div>
    </body>
</html>