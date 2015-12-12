<!DOCTYPE html>
<html>
    <head>
        <title>Partida diaria</title>
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
        <script type="text/javascript" src="../../js/masters/daymovements.js"></script>
    </head>
    <body onload="javascript:LoadData();">
        <div id="p2" class="easyui-panel" title="" fit="true">
            <div id="data_entry" title="Agregar Partida Diaria" style="min-width: 1000px">
                <input type="hidden" id="opcion" name="opcion" value="" />
                <input type="hidden" id="mopcion" name="mopcion" value="" />
                
                <input type="hidden" id="estado" name="estado" value="" />

                <input type="hidden" id="id" name="id" value="" />
                <input type="hidden" id="mid" name="mid" value="" />

                <input type="hidden" id="mes_trans" name="mes_trans" value="" />
                <input type="hidden" id="ano_trans" name="ano_trans" value="" />

                <form id="ff" method="post">
                    <table style="width: 95%;">
                        <tr>
                            <th style="text-align: left;">Residencial:</th>
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
                                                                //CheckLevelLenght();
                                                            }
                                        ">
                            </td>
                            <th style="text-align: left;">Per&iacute;odo:</th>
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
                            <th style="text-align: left;">N° de Partida:</th>
                            <td>
                                <input class="easyui-numberbox" id="frm_num" name="frm_num" 
                                       data-options="min:0, required:true, missingMessage:'Campo obligatorio'
                                                    "
                                       style="width:80px;" />
                            </td>
                            <th style="text-align: left;">Nombre de Partida:</th>
                            <td><input style="width:300px;" class="easyui-validatebox" type="text" id="frm_name" name="frm_name" data-options="required:true, missingMessage:'Campo obligatorio'" /></td>
                        </tr>
                        <tr>
                            <th style="text-align: left;">Fecha de Transacci&oacute;n:</th>
                            <td colspan="5" ><input style="width:200px;" class="easyui-datebox" type="text" id="frm_date" name="frm_date"/></td>
                            <td colspan="2" style="text-align: right;">
                                <a href="javascript:void(0)" id="btnSave" name="btnSave" onClick="save()">Guardar</a>
                                <a href="javascript:void(0)" id="btnClean" name="btnClean" onClick="clearForm()">Limpiar</a>
                                <a href="javascript:void(0)" id="btn-show" name="btn-show">Lista de Movimientos</a>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        
            <div id="tableContainer" title="Listado de Partidas Diarias" style="height: 60%; min-width: 1000px">
                <table class="easyui-datagrid"  border="false" id="dataTable" showHeader="true" title="" iconcls="icon-search"
                       data-options="singleSelect:true,collapsible:false,resizable:false, remoteSort:false,height: '100%'">
                     <thead>
                         <tr style="height:auto;">
                             <th data-options="field:'corr_partida',align:'left'" sortable="true">
                                 <strong>Correlativo</strong>
                             </th>
                             <th data-options="field:'ano_contable',align:'left'" sortable="true">
                                 <strong>A&ntilde;o Contable</strong>
                             </th>
                             <th data-options="field:'nom_mes_contable',align:'left'" sortable="true">
                                 <strong>Mes Contable</strong>
                             </th>
                             <th data-options="field:'fecha_transaccion_format',align:'left'" sortable="true">
                                 <strong>Fecha de Transacci&oacute;n</strong>
                             </th>
                             <th data-options="field:'fecha_generacion',align:'left'" sortable="true">
                                 <strong>Fecha de Generaci&oacute;n</strong>
                             </th>
                             <th data-options="field:'usuario_creacion',align:'left'" sortable="true">
                                 <strong>Usuario</strong>
                             </th>
                             <th data-options="field:'nombre_partida',align:'left'" sortable="true">
                                 <strong>Nombre de Partida</strong>
                             </th>
                             <th data-options="field:'estado',align:'left'" sortable="true">
                                 <strong>Estado</strong>
                             </th>
                             <th data-options="field:'edit',align:'left', width:40">
                                 <strong>&nbsp;</strong>
                             </th>
                             <th data-options="field:'logo',align:'left', width:40">
                                 <strong>&nbsp;</strong>
                             </th>
                         </tr>
                     </thead>
                </table>
            </div>
        </div>
            
        <div id="dlg" title="Listado de Movimientos" style="overflow-y: auto;">  
            <form id="ffm" method="post">
                <table>
                    <tr>
                        <th style="text-align: left; width: 100px;">Descripci&oacute;n:</th>
                        <td>
                            <input style="width:345px;" class="easyui-validatebox" type="text" id="frm_desc" name="frm_desc" data-options="required:true, missingMessage:'Campo obligatorio'" />
                        </td>
                    </tr>
                </table>
                <table style="width: 100%;">
                    <tr>
                        <th style="text-align: left; width: 100px;">Cuenta Contable:</th>
                        <td>
                            <input class="easyui-combobox"
                                name="frm_ccc"
                                id="frm_ccc"
                                data-options="
                                    width: 350,
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
                <table>
                    <tr>
                        <th style="text-align: left; width: 100px;">Valor:</th>
                        <td>
                            <input class="easyui-numberbox" id="frm_valor" name="frm_valor" precision="2"
                                   data-options="min:0, required:true, missingMessage:'Campo obligatorio'
                                                "
                                   style="width:80px;" />
                        </td>
                        <td><input type="radio" name="dc" value="D" checked="checked"><span>Debito</span><br/></td>
                        <td><input type="radio" name="dc" value="C"><span>Credito</span><br/></td>
                    </tr>
                    <tr>
                        <th style="text-align: left; width: 100px;">Referencia:</th>
                        <td><input style="width:100px;" class="easyui-validatebox" type="text" id="frm_movref" name="frm_movref" data-options="required:true, missingMessage:'Campo obligatorio'" /></td>
                        <td colspan="2" style="text-align: center;">
                            <a href="javascript:void(0)" id="btnSaveMov" name="btnSave" onClick="saveMov()">Guardar</a>
                            <a href="javascript:void(0)" id="btnCleanMov" name="btnClean" onClick="clearMovForm()">Limpiar</a>
                        </td>
                    </tr>
                </table>
            </form>
            <div id="tableContainerMov" title="Detalle de movimientos:" style="height: 30px;"></div>
            <table class="easyui-datagrid"  border="false" id="dataTableMov" showHeader="true" title="" iconcls="icon-search"
                    data-options="singleSelect:true,collapsible:false,resizable:false, remoteSort:false, rownumbers: true,height: '180',  pagination:true">
                  <thead>
                      <tr style="height:auto;">
                          <th data-options="field:'cod_cta_conta',align:'left'" sortable="true">
                              <strong>Cuenta Contable</strong>
                          </th>
                          <th data-options="field:'desc_conta',align:'left'" sortable="true">
                              <strong>Nombre de Cuenta</strong>
                          </th>
                          <th data-options="field:'desc_movimiento',align:'left'" sortable="true">
                              <strong>Descripci&oacute;n del Movimiento</strong>
                          </th>
                          <th data-options="field:'debe',align:'left'" formatter="moneyFormat" sortable="true">
                              <strong>Debito</strong>
                          </th>
                          <th data-options="field:'haber',align:'left'" formatter="moneyFormat" sortable="true">
                              <strong>Credito</strong>
                          </th>
                          <th data-options="field:'referencia',align:'left'" sortable="true">
                              <strong>Referencia</strong>
                          </th>
                          <th data-options="field:'logo',align:'left', width:40, height: 40">
                              <strong>&nbsp;</strong>
                          </th>
                      </tr>
                  </thead>
            </table>
            <table>
                <tr>
                    <td>
                        <span>Total Debitos:</span>
                        <input class="easyui-numberbox" id="frm_tdebe" name="frm_tdebe" precision="2"
                            data-options="disabled: true, prefix: '$', groupSeparator: ','" />
                    </td>
                    <td>
                        <span>Total Creditos:</span>
                        <input class="easyui-numberbox" id="frm_thaber" name="frm_thaber" precision="2"
                            data-options="disabled: true, prefix: '$', groupSeparator: ','" />
                    </td>
                    <td>
                        <span>Diferencia:</span>
                        <input class="easyui-numberbox" id="frm_dif" name="frm_dif" precision="2"
                            data-options="disabled: true, prefix: '$', groupSeparator: ','" />
                    </td>
                </tr>
            </table>
        </div>
        
        <div id="dlgAddMov" title="SAFIRE">
            ¿Desea agregar movimientos a la partida nueva? 
        </div>
        
        <div id="dlgDeleteMov" title="SAFIRE">
            ¿Desea eliminar el movimiento? 
        </div> 
        
        <div id="dlgDeleteHeader" title="SAFIRE">
            ¿Desea eliminar la partida? 
        </div> 
        
        <div id="dlgRevert" title="SAFIRE">
            <table>
                <tr>
                    <th style="text-align: left; width:150px;">N° de Partida:</th>
                    <td>
                        <input class="easyui-numberbox" id="frm_num_revert" name="frm_num_revert" 
                               data-options="min:0, required:true, missingMessage:'Campo obligatorio'
                                            "
                               style="width:100px;" />
                    </td>
                </tr>
                <tr>
                    <th style="text-align: left; width:150px;">Nombre de Partida:</th>
                    <td>
                        <input style="width:250px;" class="easyui-validatebox" type="text" id="frm_name_revert" 
                               name="frm_name_revert" data-options="required:true, missingMessage:'Campo obligatorio'" />
                    </td>
                </tr>
            </table>
        </div> 
        
        <div id="dlgValidateMovs" title="SAFIRE">
            Existe diferencia entre los creditos y debitos de la partida, ¿Desea continuar? 
        </div>
        
        <div id="dlgTemplates" title="SAFIRE" style="z-index: 9999;">
            <div id="saveContainerTemp" title="Guardar Plantilla:" style="height: 30px;"></div>
            <table>
                <tr>
                    <th style="text-align: left;">Nombre:</th>
                </tr>
                <tr>
                    <td>
                        <input style="width: 240px;" class="easyui-validatebox" type="text" id="frm_templete_name" name="frm_templete_name" data-options="required:true, missingMessage:'Campo obligatorio'" />
                    </td>
                    <td>
                        <a href="javascript:void(0)" id="btnSaveTemp" name="btnSave" onClick="saveTemp()"></a>
                    </td>
                </tr>
            </table>
            <div id="loadContainerTemp" title="Cargar Plantilla:" style="height: 30px;"></div>
            <table class="easyui-datagrid"  border="false" id="dataTableTemplates" showHeader="true" title="" iconcls="icon-search"
                    data-options="fit: true, singleSelect:true,collapsible:false,resizable:false, remoteSort:false, rownumbers: true">
                  <thead>
                      <tr style="height:auto;">
                          <th data-options="field:'cod_cta_conta',align:'left'" sortable="true">
                              <strong>Nombre</strong>
                          </th>
                          <th data-options="field:'logo',align:'left', width:20">
                              <strong>&nbsp;</strong>
                          </th>
                      </tr>
                  </thead>
            </table>
        </div>

        <div id="default" style="z-index: 99999;"></div>
        <div id="movNotify" style="z-index: 99999;"></div>
    </body>
</html>