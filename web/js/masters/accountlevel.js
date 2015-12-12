function UserMessage(msg) {
    addMessage = function (msgG) {
        $('#default').puigrowl('show', msgG);
    };
    addMessage([{severity: 'info', summary: 'SAFIRE', detail: msg}]);
}

function showPreview() {
    $('#sampleTxt').text('');

    var l1 = parseInt(($('#frm_l1').numberbox('getText') == '') ? '0' : $('#frm_l1').numberbox('getText'));
    var l2 = parseInt(($('#frm_l2').numberbox('getText') == '') ? '0' : $('#frm_l2').numberbox('getText'));
    var l3 = parseInt(($('#frm_l3').numberbox('getText') == '') ? '0' : $('#frm_l3').numberbox('getText'));
    var l4 = parseInt(($('#frm_l4').numberbox('getText') == '') ? '0' : $('#frm_l4').numberbox('getText'));
    var l5 = parseInt(($('#frm_l5').numberbox('getText') == '') ? '0' : $('#frm_l5').numberbox('getText'));
    var l6 = parseInt(($('#frm_l6').numberbox('getText') == '') ? '0' : $('#frm_l6').numberbox('getText'));

    if (parseInt(l1) == 0) {
        l2 = 0;
        l3 = 0;
        l4 = 0;
        l5 = 0;
        l6 = 0;
    }
    if (parseInt(l2) == 0) {
        l3 = 0;
        l4 = 0;
        l5 = 0;
        l6 = 0;
    }
    if (parseInt(l3) == 0) {
        l4 = 0;
        l5 = 0;
        l6 = 0;
    }
    if (parseInt(l4) == 0) {
        l5 = 0;
        l6 = 0;
    }
    if (parseInt(l5) == 0) {
        l6 = 0;
    }

    $('#frm_l1').numberbox('setValue', l1);
    $('#frm_l2').numberbox('setValue', l2);
    $('#frm_l3').numberbox('setValue', l3);
    $('#frm_l4').numberbox('setValue', l4);
    $('#frm_l5').numberbox('setValue', l5);
    $('#frm_l6').numberbox('setValue', l6);

    var total = parseInt(l1) + parseInt(l2) + parseInt(l3) + parseInt(l4) + parseInt(l5) + parseInt(l6);

    if (parseInt(total) == 0) {
        $('#sampleTxt').text('La longitud del formato no puede ser cero');
        return false;
    }
    if (parseInt(total) > 20) {
        $('#sampleTxt').text('La longitud del formato no debe exceder de 20 caracteres');
        return false;
    } else {
        if (l1 > 0) {
            for (i = 0; i < l1; i++) {
                $('#sampleTxt').text($('#sampleTxt').text() + 'X');
            }
        }

        if (l2 > 0) {
            $('#sampleTxt').text($('#sampleTxt').text() + '-');
            for (i = 0; i < l2; i++) {
                $('#sampleTxt').text($('#sampleTxt').text() + 'X');
            }
        }

        if (l3 > 0) {
            $('#sampleTxt').text($('#sampleTxt').text() + '-');
            for (i = 0; i < l3; i++) {
                $('#sampleTxt').text($('#sampleTxt').text() + 'X');
            }
        }

        if (l4 > 0) {
            $('#sampleTxt').text($('#sampleTxt').text() + '-');
            for (i = 0; i < l4; i++) {
                $('#sampleTxt').text($('#sampleTxt').text() + 'X');
            }
        }

        if (l5 > 0) {
            $('#sampleTxt').text($('#sampleTxt').text() + '-');
            for (i = 0; i < l5; i++) {
                $('#sampleTxt').text($('#sampleTxt').text() + 'X');
            }
        }
        if (l6 > 0) {
            $('#sampleTxt').text($('#sampleTxt').text() + '-');
            for (i = 0; i < l6; i++) {
                $('#sampleTxt').text($('#sampleTxt').text() + 'X');
            }

            return true;
        }
    }
}

function save() {
    if (showPreview() == true) {
        var tablePage = "accountlevel_data.jsp";
        var dataString = dataStringFunction();
        if ($('#opcion').val() == "E")
        {
            $.ajax({
                url: tablePage + '?' + dataString,
                type: 'POST',
                success: function (reply) {
                    UserMessage('El registro se ha modificado correctamente.');
                    LoadData();
                    $('#dataTable').datagrid({idField: 'COD_MODULO'});
                    $('#dataTable').datagrid('selectRecord', $('#COD_MODULO').val());
                },
                error: function (reply) {
                    UserMessage('Hubo un error al intentar modificar el registro.');
                }
            });
        } else
        {
            $.messager.confirm('SAFIRE', 'Desea agregar el nuevo registro?', function (r) {
                if (r) {
                    var js_data = [];
                    $('#opcion').val('A');
                    dataString = dataStringFunction();
                    $('#opcion').val('C');
                    $.ajax({
                        url: tablePage + '?' + dataString,
                        type: 'POST',
                        success: function (reply) {
                            UserMessage('El registro se ha agregado correctamente.');
                            LoadData();
                            $('#dataTable').datagrid({idField: 'COD_MODULO'});
                            $('#dataTable').datagrid('selectRecord', $('#COD_MODULO').val());
                        },
                        error: function (reply) {
                            UserMessage('Hubo un error al intentar agregar el registro.');
                        }
                    });
                }
            });
        }
    } else {
        UserMessage('El formato de cuenta no es valido.');
    }
}

function deleteRecord() {
    var tablePage = "accountlevel_data.jsp";
    $.messager.confirm('SAFIRE', 'Desea Eliminar el registro?', function (r) {
        if (r) {
            var js_data = [];
            $('#opcion').val('D');
            var dataString = dataStringFunction();
            $.ajax({
                url: tablePage + '?' + dataString,
                type: 'POST',
                success: function (reply) {
                    UserMessage('El registro se ha eliminado correctamente.');
                    LoadData();
                    clearForm();
                },
                error: function (reply) {
                    UserMessage('Hubo un error al intentar eliminar el registro.');
                }
            });
        }
    });
}

function loadRemote() {
    $('#ff').form('load', '../form/form_data1.json');
}
function clearForm() {
    $('#sampleTxt').text('XXXX-XXXX-XXXX-XXXX-XXXX');
    $('#ff').form('clear');
    $('#opcion').val('C');
}

function dataStringFunction() {
    var js_opcion = $("#opcion").val();
    var fr_id = $('#frm_res').combobox('getValue');
    var fr_l1 = $("#frm_l1").val();
    var fr_l2 = $("#frm_l2").val();
    var fr_l3 = $("#frm_l3").val();
    var fr_l4 = $("#frm_l4").val();
    var fr_l5 = $("#frm_l5").val();
    var fr_l6 = $("#frm_l6").val();
    var js_usr = '1';

    if (js_opcion == undefined)
        js_opcion = 'C';

    if (fr_id == undefined)
        fr_id = '';
    if (fr_l1 == undefined)
        fr_l1 = '';
    if (fr_l2 == undefined)
        fr_l2 = '';
    if (fr_l3 == undefined)
        fr_l3 = '';
    if (fr_l4 == undefined)
        fr_l4 = '';
    if (fr_l5 == undefined)
        fr_l5 = '';
    if (fr_l6 == undefined)
        fr_l6 = '';
    if (js_usr == undefined)
        js_usr = '';

    var _dataString = 'opcion=' + js_opcion
            + '&frm_res=' + fr_id
            + '&frm_l1=' + fr_l1
            + '&frm_l2=' + fr_l2
            + '&frm_l3=' + fr_l3
            + '&frm_l4=' + fr_l4
            + '&frm_l5=' + fr_l5
            + '&frm_l6=' + fr_l6;
    return _dataString;
}

var firstLoad = false;
function LoadData() {
    var dataString = dataStringFunction(); //Contiene los par�metros de busqueda contenidos en las cajas de texto del formulario principal.
    var targetTable = 'dataTable'; //Contiene el ID de la tabla en la que se ingresan los datos.
    var targetPage = 'accountlevel_data.jsp';
    if (!firstLoad) {
        datagridOnclickFunction(targetTable);
        firstLoad = true;
    }

    loadTableData(targetTable, targetPage, dataString);

    var dg = $('#' + targetTable);
    dg.datagrid('enableFilter');
    var comp = dg.datagrid('getFilterComponent', 'logo');
    comp.hide();
}

//Funcion para cargar datos en las tablas dinamicamente con ajax en formato JSON
function loadTableData(tableID, tablePage, dataString) {
    var js_data = [];
    $.ajax({
        url: tablePage + '?' + dataString,
        type: 'POST',
        success: function (reply) {
            js_data = reply;
            $('#' + tableID).datagrid('getPager').pagination('select', 1);
            $('#' + tableID).datagrid('loadData', js_data);
        }
    });
}

function datagridOnclickFunction(tableID) {
    $('#' + tableID).datagrid(
            {
                onClickRow: function (rowIndex, rowData) {
                    $('#opcion').val('E');
                    $('#frm_res').combobox('setValue', rowData.cod_residencial);
                    $('#ff').form('load', {
                        frm_id: rowData.cod_residencial,
                        frm_l1: rowData.nivel1,
                        frm_l2: rowData.nivel2,
                        frm_l3: rowData.nivel3,
                        frm_l4: rowData.nivel4,
                        frm_l5: rowData.nivel5,
                        frm_l6: rowData.nivel6
                    });
                },
                loadFilter: pagerFilter
            }
    );
}

function pagerFilter(data) {
    if (typeof data.length == 'number' && typeof data.splice == 'function') {	// is array
        data = {
            total: data.length,
            rows: data
        }
    }
    var dg = $(this);
    var opts = dg.datagrid('options');
    var pager = dg.datagrid('getPager');
    pager.pagination({
        onSelectPage: function (pageNum, pageSize) {
            opts.pageNumber = pageNum;
            opts.pageSize = pageSize;
            pager.pagination('refresh', {
                pageNumber: pageNum,
                pageSize: pageSize
            });
            dg.datagrid('loadData', data);
        }
    });
    if (!data.originalRows) {
        data.originalRows = (data.rows);
    }
    var start = (opts.pageNumber - 1) * parseInt(opts.pageSize);
    var end = start + parseInt(opts.pageSize);
    data.rows = (data.originalRows.slice(start, end));
    return data;
}