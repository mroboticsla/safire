function UserMessage(msg){
    addMessage = function(msgG) {  
        $('#default').puigrowl('show', msgG);  
    };
    addMessage([{severity: 'info', summary: 'SAFIRE', detail: msg}]);
}

function save(){
    var tablePage = "roles_data.jsp";
    var dataString = dataStringFunction();
    
    //Valida el formulario
    if ($('#ff').form('validate')) {
        if ($('#opcion').val() == "E")
        {
            $.ajax({
                url: tablePage + '?' + dataString,
                type: 'POST',
                success: function(reply) {
                    UserMessage('El registro se ha modificado correctamente.');
                    LoadRoles();
                    $('#dataTable').datagrid({idField:'COD_MODULO'});
                    $('#dataTable').datagrid('selectRecord', $('#COD_MODULO').val());
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
                    LoadRoles();
                    $('#dataTable').datagrid({idField:'COD_MODULO'});
                    $('#dataTable').datagrid('selectRecord', $('#COD_MODULO').val());
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
    var tablePage = "roles_data.jsp";
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
                    LoadRoles();
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

function deleteMenuOption(role, module, app){
    var tablePage = "roleMenu_data.jsp";
    var dataString = 'opcion=D&ht_role=' + role + '&ht_module=' + module + '&ht_app=' + app;
    $.ajax({
        url: tablePage + '?' + dataString,
        type: 'POST',
        success: function(reply) {
            UserMessage('El registro se ha eliminado correctamente.');
        },
        error: function(reply) {
            UserMessage('Hubo un error al intentar agregar el registro.');
        }
    });
}

function clearForm(){
        $('#btnSave').linkbutton({text:'Agregar'});
        $('#ff').form('clear');
        $('#opcion').val('C');
}

function dataStringFunction(){
    var js_opcion = $("#opcion").val();

    var fr_id = $("#frm_id").val();
    var fr_name = $("#frm_name").val();
    var js_usr = '1';
    
    if (js_opcion == undefined) js_opcion = 'C';
    
    if (fr_id == undefined) fr_id = '';
    if (fr_name == undefined) fr_name = '';
    
    if (js_usr == undefined) js_usr = '';
    
    var _dataString = 'opcion=' + js_opcion + '&ht_usr=' + js_usr + '&frm_id=' + fr_id + '&frm_name=' + fr_name;
    return _dataString;
}

var firstLoad = false;
function LoadRoles() {
    var dataString = dataStringFunction(); //Contiene los par�metros de busqueda contenidos en las cajas de texto del formulario principal.
    var targetTable = 'roles_dataTable'; //Contiene el ID de la tabla en la que se ingresan los datos.
    var targetPage = 'roles_data.jsp';
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
                $('#btnSave').linkbutton({text:'Guardar'});
                $('#ff').form('load',{
                        frm_id:rowData.COD_ROL,
                        frm_name:rowData.DESC_ROL
                });
                
                var p = $('#roleLayout').layout('panel', 'center');
                p.panel({
                    title: rowData.DESC_ROL
                });
                
                $('#tt').tree({
                    url:'roleMenu_data.jsp?ht_role=' + rowData.COD_ROL,
                    checkbox: true,
                    onCheck: function(node, checked){
                        /*
                        alert("Codigo de rol: " + node.role);
                        alert("Codigo de modulo: " + node.module);
                        alert("Codigo de aplicacion: " + node.id);
                        alert("Codigo de usuario: " + node.usr);
                        */
                        if (checked){
                            saveMenuOption(node.role, node.module, node.id);
                        }else{
                            deleteMenuOption(node.role, node.module, node.id);
                        }
                    }
                });
            },
            loadFilter:pagerFilter
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


