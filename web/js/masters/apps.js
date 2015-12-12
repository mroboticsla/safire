function save(){
    var tablePage = "Blocks_Data.jsp";
    var dataString = dataStringFunction();
    if ($('#opcion').val() == "E")
    {
        $.messager.confirm('Confirmar','Modificar datos del poligono?',function(r){
            if (r){
                $.ajax({
                    url: tablePage + '?' + dataString,
                    type: 'POST',
                    success: function(reply) {
                        $.messager.alert('','El registro se ha modificado correctamente.');
                        updateTable();
                        $('#dataTable').datagrid({idField:'poligono_nom'});
                        $('#dataTable').datagrid('selectRecord', $('#poligono_nom').val());
                    },
                    error: function(reply) {
                        $.messager.alert('','Hubo un error al intentar modificar el registro.');
                    }
                });
            }
        });
    }else
    {
        $.messager.confirm('Confirmar','Agregar nuevo poligono?',function(r){
            if (r){
                var js_data = [];
                $('#opcion').val('A');
                dataString = dataStringFunction();
                $('#opcion').val('C');
                $.ajax({
                    url: tablePage + '?' + dataString,
                    type: 'POST',
                    success: function(reply) {
                        $.messager.alert('', 'El registro se ha agregado correctamente.');
                        updateTable();
                        $('#dataTable').datagrid({idField:'poligono_nom'});
                        $('#dataTable').datagrid('selectRecord', $('#poligono_nom').val());
                    },
                    error: function(reply) {
                        $.messager.alert('', 'Hubo un error al intentar agregar el registro.');
                    }
                });
            }
        });
    }
}

function deleteRecord(id){
    var tablePage = "Blocks_Data.jsp";
    var dataString = dataStringFunction();
    $.messager.confirm('Confirmar','Eliminar poligono?',function(r){
            if (r){
                var js_data = [];
                $('#opcion').val('D');
                dataString = dataStringFunction();
                $('#opcion').val('C');
                $.ajax({
                    url: tablePage + '?' + dataString,
                    type: 'POST',
                    success: function(reply) {
                        $.messager.alert('', 'El registro se ha eliminado correctamente.');
                        updateTable();
                        clearForm();
                    },
                    error: function(reply) {
                        $.messager.alert('', 'Hubo un error al intentar eliminar el registro.');
                    }
                });
            }
        });
}

function loadRemote(){
        $('#ff').form('load', '../form/form_data1.json');
}
function clearForm(){
        $('#btnSave').linkbutton({text:'Agregar'});
        $('#ff').form('clear');
        $('#opcion').val('C');
}

function dataStringFunction(){
    var js_opcion = $("#opcion").val();
    
    var fr_poligono_id = $("#poligono_id").val();
    var fr_poligono_nom = $("#poligono_nom").val();
    var fr_poligogo_rep = $("#poligono_rep").val();
    var fr_poligogo_tel_rep = $("#poligono_tel_rep").val();
    
    var js_poligono_id = $("#ht_poligono_id").val();
    var js_poligono_nom = $("#ht_poligono_nom").val();
    var js_poligogo_rep = $("#ht_poligono_rep").val();
    var js_poligogo_tel_rep = $("#ht_poligono_tel_rep").val();
    
    if (js_opcion == undefined) js_opcion = 'C';
    
    if (fr_poligono_id == undefined) fr_poligono_id = '';
    if (fr_poligono_nom == undefined) fr_poligono_nom = '';
    if (fr_poligogo_rep == undefined) fr_poligogo_rep = '';
    if (fr_poligogo_tel_rep == undefined) fr_poligogo_tel_rep = '';
    
    if (js_poligono_id == undefined) js_poligono_id = '';
    if (js_poligono_nom == undefined) js_poligono_nom = '';
    if (js_poligogo_rep == undefined) js_poligogo_rep = '';
    if (js_poligogo_tel_rep == undefined) js_poligogo_tel_rep = '';
    
    var _dataString = 'opcion=' + js_opcion + '&ht_poligono_id=' + js_poligono_id + '&ht_poligono_nom=' + js_poligono_nom + '&ht_poligono_rep=' + js_poligogo_rep + '&ht_poligono_tel_rep=' + js_poligogo_tel_rep + '&poligono_id=' + fr_poligono_id + '&poligono_nom=' + fr_poligono_nom + '&poligono_rep=' + fr_poligogo_rep + '&poligono_tel_rep=' + fr_poligogo_tel_rep;
    return _dataString;
}

var firstLoad = false;
function updateTable() {
    var dataString = dataStringFunction(); //Contiene los parámetros de busqueda contenidos en las cajas de texto del formulario principal.
    var targetTable = 'dataTable'; //Contiene el ID de la tabla en la que se ingresan los datos.
    var targetPage = 'Blocks_Data.jsp';
    if (!firstLoad){
        datagridOnclickFunction(targetTable);
        firstLoad = true;
    }
    
    //Carga los datos necesarios en la tabla asignando los parámetros: 
    //@targetTable: 'Nombre de componente de tabla'
    //@targetPage: 'Página en la que se construye los datos de la tabla'
    //@dataString: 'Parámetros del Query' 
    //@formPage: 'Página en la que se genera el formulario de Datos'
    loadTableData(targetTable, targetPage, dataString);
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
                $('#btnSave').linkbutton({text:'Guardar'});
                $('#ff').form('load',{
                        poligono_id:rowData.poligono_id,
                        poligono_nom:rowData.poligono_nom,
                        poligono_rep:rowData.poligono_rep,
                        poligono_tel_rep:rowData.poligono_tel_rep
                });
            }
            //loadFilter:pagerFilter
        }
    );
}

function pagerFilter(data){
        if (typeof data.length == 'number' && typeof data.splice == 'function'){	// is array
                data = {
                        total: data.length,
                        rows: data
                }
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