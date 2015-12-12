function UserMessage(msg){
    addMessage = function(msgG) {  
        $('#default').puigrowl('show', msgG);  
    };
    addMessage([{severity: 'info', summary: 'SAFIRE', detail: msg}]);
}

function save(){
    var tablePage = "transactions_data.jsp";
    var dataString = dataStringFunction();
    
    //Valida el formulario
    if ($('#ff').form('validate')) {
        if ($('#opcion').val() === "E")
        {
            $.ajax({
                url: tablePage + '?' + dataString,
                type: 'POST',
                success: function(reply) {
                    UserMessage('El registro se ha modificado correctamente.');
                    LoadData();
                },
                error: function(reply) {
                    UserMessage('Hubo un error al intentar modificar el registro.');
                }
            });
        }else{
            $('#opcion').val('A');
            dataString = dataStringFunction();
            $('#opcion').val('C');
            $.ajax({
                url: tablePage + '?' + dataString,
                type: 'POST',
                success: function(reply) {
                    UserMessage('El registro se ha agregado correctamente.');
                    LoadData();
                },
                error: function(reply) {
                    UserMessage('Hubo un error al intentar agregar el registro.');
                }
            });
        }
    }else {
        return;
    }
}

function deleteRecord(id){
    var tablePage = "transactions_data.jsp";
    var dataString = dataStringFunction();
    $.messager.confirm('SAFIRE','Desea Eliminar el registro?',function(r){
        if (r){
            var js_data = [];
            $('#opcion').val('D');
            dataString = dataStringFunction();
            $('#opcion').val('C');
            $.ajax({
                url: tablePage + '?' + dataString,
                type: 'POST',
                success: function(reply) {
                    UserMessage('El registro se ha eliminado correctamente.');
                    LoadData();
                    clearForm();
                },
                error: function(reply) {
                    UserMessage('Hubo un error al intentar eliminar el registro.');
                }
            });
        }
    });
}

function saveMenuOption(role, module, app){
    var tablePage = "roleMenu_data.jsp";
    var dataString = 'opcion=A&ht_role=' + role + '&ht_module=' + module + '&ht_app=' + app;
    $.ajax({
        url: tablePage + '?' + dataString,
        type: 'POST',
        success: function(reply) {
            UserMessage('El registro se ha agregado correctamente.');
        },
        error: function(reply) {
            UserMessage('Hubo un error al intentar agregar el registro.');
        }
    });
}

function clearForm(){
        $('#ff').form('clear');
        $('#opcion').val('C');
}

function dataStringFunction(){
    var _dataString = 'opcion=' + $("#opcion").val()
            + '&frm_id=' + $("#frm_id").val()
            + '&frm_res=' + $("#frm_res").combobox('getValue')
            + '&frm_paymenttype=' + $("#frm_paymenttype").combobox('getValue')
            + '&ie=' + $('input[name="ie"]:checked').val()
            + '&frm_name=' + $("#frm_name").val()
            + '&frm_desc=' + $("#frm_desc").val()
            + '&frm_ccc=' + $("#frm_ccc").combobox('getValue')
            + '&frm_cca=' + $("#frm_cca").combobox('getValue');
    return _dataString;
}

var firstLoad = false;
function LoadData() {
    var dataString = dataStringFunction(); //Contiene los par�metros de busqueda contenidos en las cajas de texto del formulario principal.
    var targetTable = 'dataTable'; //Contiene el ID de la tabla en la que se ingresan los datos.
    var targetPage = 'transactions_data.jsp';
    if (!firstLoad){
        datagridOnclickFunction(targetTable);
        firstLoad = true;
    }

    loadTableData(targetTable, targetPage, dataString);
    
    var dg = $('#' + targetTable);
    dg.datagrid('enableFilter');
    var comp = dg.datagrid('getFilterComponent', 'logo');
    comp.hide();
}

function selectRecord(record){
    $('#dataTable').datagrid('selectRecord', {poligono_id:record});
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
                
                $('#frm_res').combo({
                    disabled:true
                });
                $('#frm_res').combobox('setValue', rowData.cod_residencial);
                
                $('#frm_paymenttype').combobox('setValue', rowData.corr_forma_pago);
                $('#frm_ccc').combobox('setValue', rowData.cod_cta_contab_debe);
                $('#frm_cca').combobox('setValue', rowData.cod_cta_contab_haber);
                
                if (rowData.tipo_ingreso){
                    $('input[name="ie"][value="I"]').prop('checked', true);
                }else{
                    $('input[name="ie"][value="E"]').prop('checked', true);
                }
                
                $('#ff').form('load',{
                        frm_id:rowData.cod_transaccion,
                        frm_name:rowData.desc_transaccion,
                        frm_desc:rowData.concepto_transaccion
                });
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
        };
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