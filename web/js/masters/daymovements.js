var maximized = false;
$(function() {
    $('#dlg').puidialog({
        hideEffect: 'clip',
        closable: false,
        minimizable: false,
        maximizable: false,
        resizable: false,
        modal: true,
        effectSpeed: 1000,
        width: '95%',
        height: 365,
        buttons: [{
                text: 'Guardar',
                icon: 'ui-icon-disk',
                click: function() {
                    if (dif !== 0){
                        $('#dlgValidateMovs').puidialog('show');
                    }else{
                        validateTotals();
                    }
                }
            },{
                text: 'Aplicar',
                icon: 'ui-icon-check',
                click: function() {
                    if (dif !== 0){
                        $('#dlgValidateMovs').puidialog('show');
                    }else{
                        applyHeader();
                    }
                }
            },{
                text: 'Revertir Partida',
                icon: 'ui-icon-arrowreturnthick-1-s',
                click: function() {
                    $('#dlgRevert').puidialog('show');
                } 
            },{
                text: 'Opciones de Plantilla',
                icon: 'ui-icon-folder-open',
                click: function() {
                    //$('#dlg').puidialog('hide');
                    $('#dlgTemplates').puidialog('show');
                } 
            },{
                text: 'Copiar Partida',
                icon: 'ui-icon-copy',
                click: function() {
                    //$('#dlg').puidialog('hide');
                    $('#dlgTemplates').puidialog('show');
                }
            },{
                text: 'Transacciones',
                icon: 'ui-icon-folder-open',
                click: function() {
                    //$('#dlg').puidialog('hide');
                    $('#dlgTemplates').puidialog('show');
                }
            },{
                text: 'Imprimir',
                icon: 'ui-icon-print',
                click: function() {
                    //$('#dlg').puidialog('hide');
                    var ano_contable = $("#periodo").combobox('getValue').split(",")[1];
                    var url = 'daymovements_detail_report.jsp?residencial=' + $("#residencial").combobox('getValue') + '&correlativo=' + $("#frm_num").numberbox('getValue') + '&ano_contable=' + ano_contable;
                    window.open(url,'_blank');
                    //$('#dlgTemplates').puidialog('show');
                }
            }
        ]
    });
    
    $('#dlgAddMov').puidialog({
        closable: false,
        minimizable: false,
        maximizable: false,
        resizable: false,
        modal: true,
        buttons: [{
                text: 'Aceptar',
                icon: 'ui-icon-check',
                click: function() {
                    $('#dlgAddMov').puidialog('hide');
                    showMovementsDetail();
                }
            },{
                text: 'Cancelar',
                icon: 'ui-icon-close',
                click: function() {
                    $('#dlgAddMov').puidialog('hide');
                }
            }
        ]
    });
    
    $('#dlgDeleteMov').puidialog({
        closable: false,
        minimizable: false,
        maximizable: false,
        resizable: false,
        modal: true,
        buttons: [{
                text: 'Aceptar',
                icon: 'ui-icon-check',
                click: function() {
                    $('#dlgDeleteMov').puidialog('hide');
                    deleteMovRecord();
                }
            },{
                text: 'Cancelar',
                icon: 'ui-icon-close',
                click: function() {
                    $('#dlgDeleteMov').puidialog('hide');
                }
            }
        ]
    });
    
    $('#dlgDeleteHeader').puidialog({
        closable: false,
        minimizable: false,
        maximizable: false,
        resizable: false,
        modal: true,
        buttons: [{
                text: 'Aceptar',
                icon: 'ui-icon-check',
                click: function() {
                    $('#dlgDeleteHeader').puidialog('hide');
                    deleteRecord();
                }
            },{
                text: 'Cancelar',
                icon: 'ui-icon-close',
                click: function() {
                    $('#dlgDeleteHeader').puidialog('hide');
                }
            }
        ]
    });
    
    $('#dlgValidateMovs').puidialog({
        closable: false,
        minimizable: false,
        maximizable: false,
        resizable: false,
        modal: true,
        buttons: [{
                text: 'Aceptar',
                icon: 'ui-icon-check',
                click: function() {
                    $('#dlgValidateMovs').puidialog('hide');
                    validateTotals();
                }
            },{
                text: 'Cancelar',
                icon: 'ui-icon-close',
                click: function() {
                    $('#dlgValidateMovs').puidialog('hide');
                }
            }
        ]
    });
    
    $('#dlgRevert').puidialog({
        hideEffect: 'clip',
        closable: true,
        minimizable: false,
        maximizable: false,
        resizable: false,
        modal: true,
        effectSpeed: 1000,
        width: 400,
        height: 60,
        buttons: [{
                text: 'Generar partida de reversion',
                icon: 'ui-icon-check',
                click: function() {
                    $('#dlgRevert').puidialog('hide');
                    revertHeader();
                }
            },{
                text: 'Cancelar',
                icon: 'ui-icon-close',
                click: function() {
                    $('#dlgRevert').puidialog('hide');
                }
            }
        ]
    });
    
    $('#dlgTemplates').puidialog({
        hideEffect: 'clip',
        closable: true,
        minimizable: false,
        maximizable: false,
        resizable: false,
        modal: true,
        effectSpeed: 1000,
        width: 520,
        height: 375
    });

    $('#data_entry').puipanel({
        toggleable: true  
        ,closable: false  
    }); 
    
    $('#tableContainer').puipanel({
        toggleable: false  
        ,closable: false  
    });
    
    $('#tableContainerMov').puipanel({
        toggleable: false  
        ,closable: false  
    });
    
    $('#saveContainerTemp').puipanel({
        toggleable: false  
        ,closable: false  
    });
    
    $('#loadContainerTemp').puipanel({
        toggleable: false  
        ,closable: false  
    });
    
    $('#opContainer').puipanel({
        toggleable: false  
        ,closable: false  
    });
    
    $('#btnSave').puibutton({icon: 'ui-icon-disk'}); 
    $('#btnClean').puibutton();
    $('#btnSaveMov').puibutton({icon: 'ui-icon-disk'}); 
    $('#btnSaveTemp').puibutton({icon: 'ui-icon-disk'});
    $('#btnCleanMov').puibutton();
    $('#btn-show').puibutton({
        icon: 'ui-icon-plus',
        click: function() {
            showMovementsDetail();
        }
    });
    $('#btn-show').puibutton('disable');

    $('#default').puigrowl();  
    
    $('#frm_date').datebox({
        required:true,
        missingMessage:'Campo obligatorio',
        formatter: myformatter,
        parser: myparser
    });
});

function validateTotals(){
    $('#dlg').puidialog('hide');
    UserMessage('Se han guardado los cambios para la partida "' + $('#frm_num').numberbox('getValue') + '"', 'info');
    $('#dataTableMov').datagrid({data: []});
    $('#frm_tdebe').numberbox('setValue', 0);
    $('#frm_thaber').numberbox('setValue', 0);
    $('#frm_dif').numberbox('setValue', 0);
}

function applyHeader(){
    if ($('#estado').val() !== "A"){
        var tablePage = "daymovements_header_data.jsp";
        var dataString = dataStringFunction();
        $('#opcion').val('AP');
        dataString = dataStringFunction();
        $('#opcion').val('C');
        $.ajax({
            url: tablePage + '?' + dataString,
            type: 'POST',
            success: function(reply) {
                $('#dlg').puidialog('hide');
                UserMessage('Se ha aplicado la partida "' + $('#frm_num').numberbox('getValue') + '" a la contabilidad', 'info');
                $('#dataTableMov').datagrid({data: []});
                $('#frm_tdebe').numberbox('setValue', 0);
                $('#frm_thaber').numberbox('setValue', 0);
                $('#frm_dif').numberbox('setValue', 0);
                LoadData();
                clearForm();
            },
            error: function(reply) {
                UserMessage('Hubo un error al intentar eliminar el registro.', 'warn');
            }
        });
    }else{
        UserMessage('Partida aplicada en la contabilidad.', 'warn');
    }
}

function revertHeader(){
    if ($('#estado').val() === "A"){
        CheckMovement('R');
    }else{
        UserMessage('Partida no aplicada en la contabilidad.', 'warn');
    }
}
    

function DeleteHeader(){
    $('#dlgDeleteHeader').puidialog('show');
}

function DeleteMov(){
    $('#dlgDeleteMov').puidialog('show');
}

function showMovementsDetail(){
    if (!maximized){
        //$('#dlg').puidialog('toggleMaximize');
        maximized = true;
    }
    $('#dlg').puidialog('show');
    LoadDataMov();
}

function myformatter(date){
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    return (d<10?('0'+d):d)+'/'+(m<10?('0'+m):m)+'/'+y;
}
function myparser(s){
    if (!s) return new Date();
    var ss = (s.split('/'));
    var y = parseInt(ss[2],10);
    var m = parseInt(ss[1],10);
    var d = parseInt(ss[0],10);
    if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
        $("#mes_trans").val(m);
        $("#ano_trans").val(y);
        return new Date(y,m-1,d);
    } else {
        return new Date();
    }
}

function UserMessage(msg, severity){
    addMessage = function(msgG) {  
            $('#default').puigrowl('show', msgG);  
    };
    addMessage([{severity: severity, summary: 'SAFIRE', detail: msg}]);
}

var nl = "0";

function editRecord(){
    if (!maximized){
        //$('#dlg').puidialog('toggleMaximize');
        maximized = true;
    }
    $('#dlg').puidialog('show');
}

function save(){
    if ($('#ff').form('validate'))
    {
        if ($('#estado').val() !== "A"){
            var tablePage = "daymovements_header_data.jsp";
            var dataString = dataStringFunction();
            if ($('#opcion').val() === "" || $('#opcion').val() === "C")
            {
                CheckMovement('A');
            }else if ($('#opcion').val() === "E"){
                clearForm();
                $.ajax({
                    url: tablePage + '?' + dataString,
                    type: 'POST',
                    success: function(reply) {
                        UserMessage('El registro se ha modificado correctamente.', 'info');
                        LoadData();
                    },
                    error: function(reply) {
                        UserMessage('Hubo un error al intentar modificar el registro.', 'warn');
                    }
                });
            }else{
                CheckMovement('B');
            }
        }else{
            UserMessage('Partida aplicada en la contabilidad.', 'warn');
        }
    }
}

function saveMov(){
    if ($('#ffm').form('validate'))
    {
        if ($('#estado').val() !== "A"){
            var tablePage = "daymovements_detail_data.jsp";
            var dataString = dataStringFunctionMov();
            if ($('#mopcion').val() === "" || $('#mopcion').val() === "C")
            {
                $('#mopcion').val('A');
                dataString = dataStringFunctionMov();
                $('#mopcion').val('C');
                $.ajax({
                    url: tablePage + '?' + dataString,
                    type: 'POST',
                    success: function(reply) {
                        UserMessage('El registro se ha agregado correctamente.', 'info');
                        LoadDataMov();
                        clearMovForm();
                    },
                    error: function(reply) {
                        UserMessage('Hubo un error al intentar agregar el registro.', 'warn');
                    }
                });
            }else if ($('#mopcion').val() === "E"){
                $.ajax({
                    url: tablePage + '?' + dataString,
                    type: 'POST',
                    success: function(reply) {
                        UserMessage('El registro se ha modificado correctamente.', 'info');
                        LoadData();
                        clearMovForm();
                    },
                    error: function(reply) {
                        UserMessage('Hubo un error al intentar modificar el registro.', 'warn');
                    }
                });
            }else
            {
                CheckMovement('B');
            }
        }else{
            UserMessage('Partida aplicada en la contabilidad.', 'warn');
        }
    }
}

function CheckMovement(opc){
    $('#opcion').val('CM');
    var tablePage = "daymovements_header_data.jsp";
    var dataString = dataStringFunction();
    var lastOption = $('#opcion').val();
    
    if (opc === 'R'){
        dataString = dataStringRevertFunction();
    }
    
    $.ajax({
        url: tablePage + '?' + dataString,
        type: 'POST',
        success: function(reply) {
            var str = reply;
            str = $.trim(str);
            alert(str);
            if(str !== 'true'){
                if (opc === 'A'){
                    $('#opcion').val('A');
                    dataString = dataStringFunction();
                    $.ajax({
                        url: tablePage + '?' + dataString,
                        type: 'POST',
                        success: function(reply) {
                            UserMessage('El registro se ha agregado correctamente.', 'info');
                            $('#opcion').val('E');
                            $('#btn-show').puibutton('enable');
                            $('#dlg').puidialog('changeTitle', 'Listado de Movimientos - ' + $('#frm_name').val());
                            $('#frm_num').numberbox({disabled: true});
                            $('#dlgAddMov').puidialog('show');
                            LoadData();
                        },
                        error: function(reply) {
                            UserMessage('Hubo un error al intentar agregar el registro.', 'warn');
                        }
                    });
                }else if (opc === 'R'){
                    $('#opcion').val('RP');
                    dataString = dataStringRevertFunction();
                    $.ajax({
                        url: tablePage + '?' + dataString,
                        type: 'POST',
                        success: function(reply) {
                            //$('#dlgRevert').puidialog('hide');
                            $('#dlg').puidialog('hide');
                            UserMessage('Se ha generado la partida de reversion "' + $('#frm_num_revert').numberbox('getValue') + '"', 'info');
                            $('#dataTableMov').datagrid({data: []});
                            $('#frm_tdebe').numberbox('setValue', 0);
                            $('#frm_thaber').numberbox('setValue', 0);
                            $('#frm_dif').numberbox('setValue', 0);
                            $('#opcion').val('C');
                            LoadData();
                            clearForm();
                        },
                        error: function(reply) {
                            UserMessage('Hubo un error al intentar agregar el registro.', 'warn');
                        }
                    });
                }
            }else{
                $('#opcion').val('C');
                UserMessage('El numero de partida ya fue utilizado', 'warn');
            }
        },
        error: function(reply) {
            UserMessage('Hubo un error al intentar consultar la disponibilidad del numero de partida, revisar la configuracion.', 'warn');
        }
    });
    $('#opcion').val(lastOption);
}

function deleteRecord(){
    if ($('#estado').val() !== "A"){
        var tablePage = "daymovements_header_data.jsp";
        var dataString = dataStringFunction();
        $('#opcion').val('D');
        dataString = dataStringFunction();
        $('#opcion').val('C');
        $.ajax({
            url: tablePage + '?' + dataString,
            type: 'POST',
            success: function(reply) {
                UserMessage('El registro se ha eliminado correctamente.', 'info');
                LoadData();
                clearForm();
            },
            error: function(reply) {
                UserMessage('Hubo un error al intentar eliminar el registro.', 'warn');
            }
        });
    }else{
        UserMessage('Partida aplicada en la contabilidad.', 'warn');
    }
}

function deleteMovRecord(){
    if ($('#estado').val() !== "A"){
        var tablePage = "daymovements_detail_data.jsp";
        var dataString = dataStringFunctionMov();
        $('#mopcion').val('D');
        dataString = dataStringFunctionMov();
        $('#mopcion').val('C');
        $.ajax({
            url: tablePage + '?' + dataString,
            type: 'POST',
            success: function(reply) {
                UserMessage('El registro se ha eliminado correctamente.', 'info');
                LoadDataMov();
                clearMovForm();
            },
            error: function(reply) {
                UserMessage('Hubo un error al intentar eliminar el registro.', 'warn');
            }
        });
    }else{
        UserMessage('Partida aplicada en la contabilidad.', 'warn');
    }
}

function clearForm(){
        //$('#ff').form('clear');
        $('#btn-show').puibutton('disable');
        $('#num').val('');
        $('#desc').val('');
        $('#frm_num').numberbox({disabled: false});
        $('#residencial').combo({
            disabled:false
        });
        $('#ff').form('clear');
        $('#opcion').val('C');
        LoadData();
}

function clearMovForm(){
        $('#ffm').form('clear');
        $('#mopcion').val('C');
        LoadDataMov();
}

function dataStringFunction(){
    var _dataString = 'opcion=' + $("#opcion").val()
            + '&id=' + $("#id").val()
            + '&estado=' + $("#estado").val()
            + '&residencial=' + $("#residencial").combobox('getValue')
            + '&periodo=' + $("#periodo").combobox('getValue')
            + '&frm_num=' + $("#frm_num").numberbox('getValue')
            + '&frm_name=' + $("#frm_name").val()
            + '&mes_trans=' + $("#mes_trans").val()
            + '&ano_trans=' + $("#ano_trans").val()
            + '&frm_date=' + $("#frm_date").datebox('getValue');
    return _dataString;
}

function dataStringRevertFunction(){
    var _dataString = 'opcion=' + $("#opcion").val()
            + '&id=' + $("#frm_num_revert").val()
            + '&estado=D'
            + '&residencial=' + $("#residencial").combobox('getValue')
            + '&periodo=' + $("#periodo").combobox('getValue')
            + '&frm_num=' + $("#frm_num_revert").numberbox('getValue')
            + '&frm_name=' + $("#frm_name_revert").val()
            + '&mes_trans=' + $("#mes_trans").val()
            + '&ano_trans=' + $("#ano_trans").val()
            + '&frm_date=' + $("#frm_date").datebox('getValue');
    return _dataString;
}

function dataStringFunctionMov(){
    var _dataString = 'mopcion=' + $("#mopcion").val()
            + '&mid=' + $("#mid").val()
            + '&estado=' + $("#estado").val()
            + '&residencial=' + $("#residencial").combobox('getValue')
            + '&periodo=' + $("#periodo").combobox('getValue')
            + '&frm_ccc=' + $("#frm_ccc").combobox('getValue')
            + '&frm_num=' + $("#frm_num").numberbox('getValue')
            + '&frm_name=' + $("#frm_name").val()
            + '&frm_desc=' + $("#frm_desc").val()
            + '&frm_valor=' + $("#frm_valor").numberbox('getValue')
            + '&dc=' + $('input[name="dc"]:checked').val()
            + '&frm_movref=' + $("#frm_movref").val();
    return _dataString;
}

function dataStringFunctionTemp(){
    var _dataString = 'mopcion=' + $("#mopcion").val()
            + '&mid=' + $("#mid").val()
            + '&residencial=' + $("#residencial").combobox('getValue')
            + '&periodo=' + $("#periodo").combobox('getValue')
            + '&frm_ccc=' + $("#frm_ccc").combobox('getValue')
            + '&frm_num=' + $("#frm_num").numberbox('getValue')
            + '&frm_name=' + $("#frm_name").val()
            + '&frm_desc=' + $("#frm_desc").val()
            + '&frm_valor=' + $("#frm_valor").numberbox('getValue')
            + '&dc=' + $('input[name="dc"]:checked').val()
            + '&frm_movref=' + $("#frm_movref").val();
    return _dataString;
}

var firstLoad = false;
var firstLoadMov = false;
var firstLoadTemp = false;
function LoadData() {
    var dataString = dataStringFunction(); //Contiene los par�metros de busqueda contenidos en las cajas de texto del formulario principal.
    var targetTable = 'dataTable'; //Contiene el ID de la tabla en la que se ingresan los datos.
    var targetPage = 'daymovements_header_data.jsp';
    if (!firstLoad){
        datagridOnclickFunction(targetTable);
        firstLoad = true;
    }

    loadTableData(targetTable, targetPage, dataString, setMainDatagridButtons);
    
    var dg = $('#' + targetTable);
    dg.datagrid('enableFilter');
    var comp = dg.datagrid('getFilterComponent', 'logo');
    comp.hide();
    var compe = dg.datagrid('getFilterComponent', 'edit');
    compe.hide();
}

function LoadDataMov() {
    var dataString = dataStringFunctionMov(); //Contiene los par�metros de busqueda contenidos en las cajas de texto del formulario principal.
    var targetTable = 'dataTableMov'; //Contiene el ID de la tabla en la que se ingresan los datos.
    var targetPage = 'daymovements_detail_data.jsp';
    if (!firstLoadMov){
        datagridOnclickFunctionMov(targetTable);
        firstLoadMov = true;
    }

    loadTableData(targetTable, targetPage, dataString, setMovTotals);
    
    var dg = $('#' + targetTable);
    dg.datagrid('enableFilter');
    var comp = dg.datagrid('getFilterComponent', 'logo');
    comp.hide();
    var compe = dg.datagrid('getFilterComponent', 'edit');
    compe.hide();
}

function LoadDataTemp() {
    var dataString = dataStringFunctionMov(); //Contiene los par�metros de busqueda contenidos en las cajas de texto del formulario principal.
    var targetTable = 'dataTableTemplates'; //Contiene el ID de la tabla en la que se ingresan los datos.
    var targetPage = 'daymovements_templates_data.jsp';
    if (!firstLoadTemp){
        datagridOnclickFunctionMov(targetTable);
        firstLoadTemp = true;
    }

    loadTableData(targetTable, targetPage, dataString, setDatagridButtons);
    
    var dg = $('#' + targetTable);
    dg.datagrid('enableFilter');
    var comp = dg.datagrid('getFilterComponent', 'logo');
    comp.hide();
}

var successFunction;
//Funcion para cargar datos en las tablas dinamicamente con ajax en formato JSON
function loadTableData(tableID, tablePage, dataString, successFn) {
    var js_data = [];
    
    clearTimeout(successFunction);
    
    $.ajax({
        url: tablePage + '?' + dataString,
        type: 'POST',
        success: function(reply) {
            js_data = reply;
            $('#' + tableID).datagrid('getPager').pagination('select', 1);
            $('#' + tableID).datagrid('loadData', js_data);
            successFunction = setTimeout(successFn, 0);
        }
    });
}

function setMainDatagridButtons(){
    $('.btnEdit').puibutton({icon: 'ui-icon-newwin'});
    $('.btnDelete').puibutton({icon: 'ui-icon-minusthick'});
}

function setDatagridButtons(){
    $('.btnDelete').puibutton({icon: 'ui-icon-minusthick'});
}
var dif = 0;
function setMovTotals(){
    var rows = $('#dataTableMov').datagrid('getRows');
    if(rows.length > 0){
        $('#frm_tdebe').numberbox('setValue', rows[0].TD);
        $('#frm_thaber').numberbox('setValue', rows[0].TH);
        $('#frm_dif').numberbox('setValue', rows[0].DIF);
        dif = rows[0].DIF;
    }
    setDatagridButtons();
}

function datagridOnclickFunction(tableID){
    $('#' + tableID).datagrid(
        {
            onSelect: function(rowIndex, rowData){
                $('#dlg').puidialog('changeTitle', 'Listado de Movimientos - ' + rowData.nombre_partida);
                $('#frm_num').numberbox({disabled: true});
                $('#opcion').val('E');
                
                $('#estado').val(rowData.estado);
                
                $('#id').val(rowData.corr_partida);
                $('#btn-show').puibutton('enable');
                $('#residencial').combo({
                    disabled:true
                });
                $('#residencial').combobox('setValue', rowData.cod_residencial);
                $('#frm_date').datebox('setValue', rowData.fecha_transaccion_format);
                
                //alert(rowData.fecha_transaccion_format);
                
                $('#periodo').combobox('setValue', rowData.cod_residencial + ',' + rowData.ano_contable + ',' + rowData.mes_contable);

                $('#ff').form('load',{
                    frm_num: rowData.corr_partida,
                    frm_name:rowData.nombre_partida
                });
                
                LoadDataMov();
            },
            loadFilter:pagerFilter
        }
    );
}

function datagridOnclickFunctionMov(tableID){
    $('#' + tableID).datagrid(
        {
            onSelect: function(rowIndex, rowData){
                $('#mopcion').val('E');
                
                $('#mid').val(rowData.corr_movi);
                
                $('#frm_ccc').combobox('setValue', rowData.cod_cta_conta);
                
                if (parseInt(rowData.debe) > 0){
                    $('input[name="dc"][value="D"]').prop('checked', true);
                    $('#frm_valor').numberbox('setValue', rowData.debe);
                }else{
                    $('input[name="dc"][value="C"]').prop('checked', true);
                    $('#frm_valor').numberbox('setValue', rowData.haber);
                }

                $('#ffm').form('load',{
                    frm_desc: rowData.desc_movimiento,
                    frm_movref:rowData.referencia
                });
                
            },
            loadFilter:pagerFilter
        }
    );
}

function moneyFormat(val,row){
    return '$ '+val;
}

function pagerFilter(data){
        if (typeof data.length === 'number' && typeof data.splice === 'function'){	// is array
                data = {
                        total: data.length,
                        rows: data
                };
        }
        var dg = $(this);
        var opts = dg.datagrid('options');
        var pager = dg.datagrid('getPager');
        pager.pagination({
            onSelectPage:function(pageNum, pageSize){
                    opts.pageNumber = pageNum;
                    opts.pageSize = pageSize;
                    pager.pagination('refresh',{
                            pageNumber:pageNum,
                            pageSize:pageSize
                    });
                    dg.datagrid('loadData',data);
            }
        });
        if (!data.originalRows){
                data.originalRows = (data.rows);
        }
        var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
        var end = start + parseInt(opts.pageSize);
        data.rows = (data.originalRows.slice(start, end));
        return data;
}