function UserMessage(msg, severity){
    addMessage = function(msgG) {  
        $('#default').puigrowl('show', msgG);  
    };
    addMessage([{severity: severity, summary: 'SAFIRE', detail: msg}]);
}

function save(){
    var tablePage = "ccalendar_data.jsp";
    var dataString = dataStringFunction();
    
    //Valida el formulario
    if ($('#ff').form('validate')) {
        if ($('#frm_closed').val() === '1'){
            UserMessage('No se puede modificar un periodo que ya fue cerrado.', 'warn');
            return;
        }
        
        if ($('#opcion').val() === "E")
        {
            $.ajax({
                url: tablePage + '?' + dataString,
                type: 'POST',
                success: function(reply) {
                    UserMessage('El registro se ha modificado correctamente.', 'info');
                    $('#opcion').val('C');
                    LoadData();
                },
                error: function(reply) {
                    UserMessage('Hubo un error al intentar modificar el registro.', 'warn');
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
                    UserMessage('El registro se ha agregado correctamente.', 'info');
                    LoadData();
                },
                error: function(reply) {
                    UserMessage('Hubo un error al intentar agregar el registro.', 'warn');
                }
            });
        }
    }else {
        return;
    }
}

function openMonth(){
    UserMessage('Los periodos cerrados ya no pueden ser abiertos desde esta pantalla', 'warn');
    return;
}

function closeMonth(){
    //Ya no se ocupa esta funcion
    return;
    var tablePage = "ccalendar_data.jsp";
    var dataString = dataStringFunction();
    $.messager.confirm('SAFIRE','Desea cerrar el periodo? \nEsta operacion no se puede deshacer.',function(r){
        if (r){
            if ($("#frm_active").prop('checked')){
                UserMessage('No se puede cerrar un periodo que se encuentra activo.', 'warn');
                return;
            }
            
            $('#opcion').val('D');
            dataString = dataStringFunction();
            $('#opcion').val('C');
            $.ajax({
                url: tablePage + '?' + dataString,
                type: 'POST',
                success: function(reply) {
                    UserMessage('Periodo cerrado correctamente.', 'info');
                    LoadData();
                    clearForm();
                },
                error: function(reply) {
                    UserMessage('Hubo un error al intentar cerrar el periodo.', 'warn');
                }
            });
        }
    });
}

function clearForm(){
        $('#AutoGeneratedTextStart').text('');
        $('#AutoGeneratedTextEnd').text('');
        
        $('#frm_res').combo({
            disabled:false
        });
        $('#frm_year').numberspinner({
            disabled:false
        });
        $('#frm_month').combo({
            disabled:false
        });
                
        $('#ff').form('clear');
        $('#opcion').val('C');
}

function calculateRange(month){
    var primerDia = new Date($('#frm_year').numberbox('getValue'), month, 1);
    var ultimoDia = new Date($('#frm_year').numberbox('getValue'), month, 0);
    var mn = month;
    if (parseInt(mn) < 10) mn = '0' + mn;
    $('#AutoGeneratedTextStart').text(primerDia.getDate() + '/' + mn + '/' + $('#frm_year').numberbox('getValue'));
    $('#AutoGeneratedTextEnd').text(ultimoDia.getDate() + '/' + mn + '/' + $('#frm_year').numberbox('getValue'));
}

function dataStringFunction(){
    var active = "0";
    if ($("#frm_active").prop('checked')) active = "1";
    var _dataString = 'opcion=' + $("#opcion").val()
            + '&frm_res=' + $("#frm_res").combobox('getValue')
            + '&frm_year=' + $("#frm_year").numberspinner('getValue')
            + '&frm_month=' + $("#frm_month").combobox('getValue')
            + '&frm_first=' + $("#AutoGeneratedTextStart").text()
            + '&frm_last=' + $("#AutoGeneratedTextEnd").text()
            + '&frm_active=' + active;
    return _dataString;
}

var firstLoad = false;
function LoadData() {
    var dataString = dataStringFunction(); //Contiene los par�metros de busqueda contenidos en las cajas de texto del formulario principal.
    var targetTable = 'dataTable'; //Contiene el ID de la tabla en la que se ingresan los datos.
    var targetPage = 'ccalendar_data.jsp';
    if (!firstLoad){
        datagridOnclickFunction(targetTable);
        firstLoad = true;
    }
    loadTableData(targetTable, targetPage, dataString);
    var dg = $('#' + targetTable);
    dg.datagrid('enableFilter');
    var comp = dg.datagrid('getFilterComponent', 'nom_activo');
    comp.hide();
    comp = dg.datagrid('getFilterComponent', 'nom_cerrado');
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
                
                if (rowData.cerrado) {
                    $('#frm_closed').val('1');
                }else{
                    $('#frm_closed').val('');
                }
                
                $('#frm_res').combo({
                    disabled:true
                });
                $('#frm_res').combobox('setValue', rowData.cod_residencial);
                
                $('#frm_year').numberspinner({
                    disabled:true
                });
                $('#frm_year').numberspinner('setValue', rowData.ano_periodo);
                
                $('#frm_month').combo({
                    disabled:true
                });
                $('#frm_month').combobox('setValue', rowData.mes_periodo);
                
                $('#AutoGeneratedTextStart').text(rowData.fecha_inicio);
                $('#AutoGeneratedTextEnd').text(rowData.fecha_fin);
                
                if (rowData.activo){
                    $("#frm_active").prop('checked', true);
                }else{
                    $("#frm_active").prop('checked', false);
                }
            } ,
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