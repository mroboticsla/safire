function UserMessage(msg, severity){
    addMessage = function(msgG) {  
            $('#default').puigrowl('show', msgG);  
    };
    addMessage([{severity: severity, summary: 'SAFIRE', detail: msg}]);
}

var nl = "0";

function save(){
    if ($('#ff').form('validate'))
    {
        if ($('#num').val().length !== parseInt(nl)){
            UserMessage('La longitud de la cuenta debe ser de ' + nl + ' digitos.', 'warn');
            $('#num').focus();
            return;
        }
        
        var tablePage = "accounts_data.jsp";
        var dataString = dataStringFunction();
        if ($('#opcion').val() === "NL")
        {
            CheckAccount('A');
        }else if ($('#opcion').val() === "E")
        {   
            if ($('#cta_menor').val().trim() !== '0' && $("#acepta_mov").prop('checked')){
                UserMessage('El registro seleccionado tiene cuentas activas asociadas, No puede aceptar movimientos', 'warn');
                return;
            }
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
        }else
        {
            CheckAccount('B');
        }
    }
}

function CheckLevelLenght(){
    var tablePage = "accounts_data.jsp";
    var dataString = dataStringFunction();
    var lastOption = $('#opcion').val();
    $('#opcion').val('CL');
    dataString = dataStringFunction();
    $.ajax({
        url: tablePage + '?' + dataString,
        type: 'POST',
        success: function(reply) {
            //UserMessage(reply, 'info');
            nl = reply;
        },
        error: function(reply) {
            UserMessage('Hubo un error al intentar consultar el formato de la cuenta, revisar la configuracion.', 'warn');
        }
    });
    $('#opcion').val(lastOption);
}

function CheckAccount(opc){
    $('#opcion').val('CA');
    var tablePage = "accounts_data.jsp";
    var dataString = dataStringFunction();
    var lastOption = $('#opcion').val();
    $.ajax({
        url: tablePage + '?' + dataString,
        type: 'POST',
        success: function(reply) {
            var str = reply;
            str = str.trim();
            if(str !== 'true'){
                if (opc === 'A'){
                    $('#opcion').val('DE');
                    dataString = dataStringFunction();
                    $('#opcion').val('NL');
                    clearForm();
                    $.ajax({
                        url: tablePage + '?' + dataString,
                        type: 'POST',
                        success: function(reply) {
                            UserMessage('El registro se ha agregado correctamente.', 'info');
                            LoadData();
                        },
                        error: function(reply) {
                            UserMessage('Hubo un error al intentar modificar el registro.', 'warn');
                        }
                    });
                }else if (opc === 'B'){
                    $('#opcion').val('A');
                    dataString = dataStringFunction();
                    clearForm();
                    $.ajax({
                        url: tablePage + '?' + dataString,
                        type: 'POST',
                        success: function(reply) {
                            UserMessage('El registro se ha agregado correctamente.', 'info');
                            LoadData();
                        },
                        error: function(reply) {
                            UserMessage('Hubo un error al intentar agregar el registro.', 'warn');
                        }
                    });
                }
            }else{
                UserMessage('El numero de cuenta seleccionado ya fue utilizado (Puede estar inactivo)', 'warn');
            }
        },
        error: function(reply) {
            UserMessage('Hubo un error al intentar consultar la disponibilidad de la cuenta, revisar la configuracion.', 'warn');
        }
    });
    $('#opcion').val(lastOption);
}

function deleteRecord(){
    var tablePage = "accounts_data.jsp";
    var dataString = dataStringFunction();
    $.messager.confirm('SAFIRE','Desea Eliminar el registro?',function(r){
        if (r){
            if ($('#cta_menor').val().trim() !== '0'){
                UserMessage('El registro seleccionado tiene cuentas activas asociadas', 'warn');
                return;
            }else if ($('#saldo').val().trim() !== '0.00' && $('#saldo').val().trim() !== '' && $('#saldo').val().trim() !== 'undefined'){
                UserMessage('La cuenta posee saldo diferente a cero. ($' + $('#saldo').val().trim() + ')', 'warn');
                return;
            }else{
                $('#opcion').val('D');
                dataString = dataStringFunction();
                $('#opcion').val('C');
                $.ajax({
                    url: tablePage + '?' + dataString,
                    type: 'POST',
                    success: function(reply) {
                        UserMessage('El registro se ha desactivado correctamente.', 'info');
                        LoadData();
                        clearForm();
                    },
                    error: function(reply) {
                        UserMessage('Hubo un error al intentar eliminar el registro.', 'warn');
                    }
                });
            }
        }
    });
}

function nextLevel(){
    $.messager.confirm('SAFIRE','Desea configurar las sub-cuentas relacionadas?',function(r){
        if (r){
            $('#residencial_de').val($('#residencial').combobox('getValue'));
            $('#residencial').combo({
                disabled:true
            });
            $('#num').prop({
                disabled:false
            });
            $('#residencial').combobox('setValue', $('#residencial_de').val());
            $('#cta_mayor').val($('#id').val());
            $('#num').val('');
            $('#nivel').val(parseInt($('#nivel').val()) + 1);
            $('#data_entry').puipanel('titleProp','Agregar Cuenta Nivel ' + $('#nivel').val() + ' - ' + $('#desc').val());
            $('#tableContainer').puipanel('titleProp', 'Catalogo de Cuentas - Nivel ' + $('#nivel').val() + ' (' + $('#desc').val() + ')');
            $('#opcion').val('NL');
            $('#desc').val('');
            LoadData();
        }
    });
}

function showReport() {
    var url = 'accounts_main_report.jsp';
    window.open(url,'_blank');
}

function clearForm(){
        //$('#ff').form('clear');
        $('#num').val('');
        $('#desc').val('');
        $('#num').prop({
            disabled:false
        });
        if ($('#nivel').val() === '1'){
            $('#opcion').val('C');
            $('#residencial').combo({
                disabled:false
            });
        }else{
            $('#opcion').val('NL');
            $('#residencial').combobox('setValue', $('#residencial_de').val());
        }
}

function initForm(){
        $('#nivel').val('1');
        $('#cta_mayor').val('');
        $('#residencial_de').val('');
        $('#residencial').combo({
                disabled:false
            });
        $('#num').prop({
                disabled:false
            });
        $('#data_entry').puipanel('titleProp','Datos de la Cuenta');
        $('#tableContainer').puipanel('titleProp', 'Catalogo de Cuentas - Nivel ' + $('#nivel').val());

        $('#ff').form('clear');
        $('#opcion').val('C');
        LoadData();
}

function dataStringFunction(){
    var acep_m = "N";
    if ($("#acepta_mov").prop('checked')) acep_m = "S";
    var _dataString = 'opcion=' + $("#opcion").val()
            + '&residencial=' + $("#residencial").combobox('getValue')
            + '&residencial_de=' + $("#residencial_de").val()
            + '&id=' + $("#id").val()
            + '&num=' + $("#num").val()
            + '&desc=' + $("#desc").val()
            + '&dh=' + $('input[name="dh"]:checked').val()
            + '&tipo=' + $("#tipo").combobox('getValue')
            + '&manejo=' + $("#manejo").combobox('getValue')
            + '&acepta_mov=' + acep_m
            + '&nivel=' + $("#nivel").val()
            + '&cta_mayor=' + $("#cta_mayor").val();
    return _dataString;
}

var firstLoad = false;
function LoadData() {
    CheckLevelLenght();
    var dataString = dataStringFunction(); //Contiene los par�metros de busqueda contenidos en las cajas de texto del formulario principal.
    var targetTable = 'dataTable'; //Contiene el ID de la tabla en la que se ingresan los datos.
    var targetPage = 'accounts_data.jsp';
    if (!firstLoad){
        datagridOnclickFunction(targetTable);
        firstLoad = true;
    }

    loadTableData(targetTable, targetPage, dataString);
    
    var dg = $('#' + targetTable);
    dg.datagrid('enableFilter');
    var comp = dg.datagrid('getFilterComponent', 'logo');
    comp.hide();
    var compe = dg.datagrid('getFilterComponent', 'edit');
    compe.hide();
}

//Funcion para cargar datos en las tablas dinamicamente con ajax en formato JSON
function loadTableData(tableID, tablePage, dataString) {
    var js_data = [];
    $.ajax({
        url: tablePage + '?' + dataString,
        type: 'POST',
        success: function(reply) {
            js_data = reply;
            $('#' + tableID).datagrid('getPager').pagination('select', 1);
            $('#' + tableID).datagrid('loadData', js_data);
        }
    });
}

function datagridOnclickFunction(tableID){
    $('#' + tableID).datagrid(
        {
            onClickRow: function(rowIndex, rowData){
                $('#opcion').val('E');
                $('#id').val(rowData.cod_cta_conta);
                $('#cta_menor').val(rowData.cta_menor);
                $('#residencial_de').val(rowData.cod_residencial);
                $('#saldo').val(rowData.saldo);
                $('#residencial').combo({
                    disabled:true
                });
                $('#residencial').combobox('setValue', rowData.cod_residencial);
                $('#tipo').combobox('setValue', rowData.cod_tipo_cta);
                $('#manejo').combobox('setValue', rowData.estado_balance);
                
                if (rowData.acepta_movs === 'S'){
                    $("#acepta_mov").prop('checked', true);
                }else{
                    $("#acepta_mov").prop('checked', false);
                }
                
                if (rowData.aplica_debe === 'S'){
                    $('input[name="dh"][value="D"]').prop('checked', true);
                }else{
                    $('input[name="dh"][value="H"]').prop('checked', true);
                }
                
                //alert($('input[name="dh"]:checked').val());
                
                var cta_mod = rowData.cod_cta_conta;
                var cta_mayor_lenght = parseInt($("#cta_mayor").val().length);
                
                if($('#nivel').val() !== '1'){
                    cta_mod = cta_mod.substring(cta_mayor_lenght);
                }
                
                $('#ff').form('load',{
                    num: cta_mod,
                    desc:rowData.desc_cta_contab
                });
                
                $('#num').prop({
                    disabled:true
                });
                
                CheckLevelLenght();
            },
            loadFilter:pagerFilter
        }
    );
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


