function UserMessage(msg, sev){
    addMessage = function(msgG) {  
        $('#default').puigrowl('show', msgG);  
    };
    addMessage([{severity: sev, summary: 'SAFIRE', detail: msg}]);
}

$(function() {
    $('#dlgProcesando').puidialog({
        closable: false,
        minimizable: false,
        maximizable: false,
        resizable: false,
        modal: true
    });
    
    $('#dlgMayorizacion').puidialog({
        closable: false,
        minimizable: false,
        maximizable: false,
        resizable: false,
        modal: true,
        buttons: [{
                text: 'Aceptar',
                icon: 'ui-icon-check',
                click: function() {
                    $('#dlgMayorizacion').puidialog('hide');
                    do_mayorizar('M');
                }
            },{
                text: 'Cancelar',
                icon: 'ui-icon-close',
                click: function() {
                    $('#dlgMayorizacion').puidialog('hide');
                }
            }
        ]
    });
    
    $('#dlgDesMayorizacion').puidialog({
        closable: false,
        minimizable: false,
        maximizable: false,
        resizable: false,
        modal: true,
        buttons: [{
                text: 'Aceptar',
                icon: 'ui-icon-check',
                click: function() {
                    $('#dlgDesMayorizacion').puidialog('hide');
                    do_mayorizar('D');
                }
            },{
                text: 'Cancelar',
                icon: 'ui-icon-close',
                click: function() {
                    $('#dlgDesMayorizacion').puidialog('hide');
                }
            }
        ]
    });
    
    $('#dlgCierre').puidialog({
        closable: false,
        minimizable: false,
        maximizable: false,
        resizable: false,
        modal: true,
        buttons: [{
                text: 'Aceptar',
                icon: 'ui-icon-check',
                click: function() {
                    $('#dlgCierre').puidialog('hide');
                    do_mayorizar('CL');
                }
            },{
                text: 'Cancelar',
                icon: 'ui-icon-close',
                click: function() {
                    $('#dlgCierre').puidialog('hide');
                }
            }
        ]
    });
    
    $('#data_entry').puipanel({
        toggleable: true  
        ,closable: false  
    }); 

    $('#operations').puipanel({
        toggleable: true  
        ,closable: false  
    }); 
    
    $('#data').puipanel({
        toggleable: true  
        ,closable: false
    }); 

    $('#btnMayorizar').puibutton(); 
    $('#btnDesMayorizar').puibutton(); 
    $('#btnCerrar').puibutton();
    
    $('#btnN1').puibutton();
    $('#btnN2').puibutton();
    $('#btnN3').puibutton();
    $('#btnN4').puibutton();
    $('#btnN5').puibutton();

    $('#default').puigrowl();  
});

function dataStringFunction(){
    var _dataString = 'opcion=' + $("#opcion").val()
    + '&nivel=' + $("#nivel").val();
    return _dataString;
}

function mayorizar(){
    $('#dlgMayorizacion').puidialog('show');
}

function desmayorizar(){
    $('#dlgDesMayorizacion').puidialog('show');
}

function cerrar_periodo(){
    $('#dlgCierre').puidialog('show');
}

function do_mayorizar(opc){
    $('#dlgProcesando').puidialog('show');
    var tablePage = "mayor_data.jsp";
    $('#opcion').val(opc);
    var dataString = dataStringFunction();
    $('#opcion').val('C');
    $.ajax({
        url: tablePage + '?' + dataString,
        type: 'POST',
        success: function(reply) {
            LoadData();
            $('#dlgProcesando').puidialog('hide');
            UserMessage('Proceso finalizado correctamente.', 'info');
        },
        error: function(reply) {
            UserMessage('No se pudo completar el proceso.', 'warn');
        }
    });
}

function moneyFormat(val,row){
    return '$ '+val;
}

function setLevel(lvl){
    $('#nivel').val(lvl);
    LoadData();
}

var firstLoad = false;
function LoadData() {
    var dataString = dataStringFunction(); //Contiene los par�metros de busqueda contenidos en las cajas de texto del formulario principal.
    var targetTable = 'dataTable'; //Contiene el ID de la tabla en la que se ingresan los datos.
    var targetPage = 'mayor_data.jsp';
    
    if (!firstLoad){
        //datagridOnclickFunction(targetTable);
        firstLoad = true;
    }
    
    loadTableData(targetTable, targetPage, dataString);
    
    var dg = $('#' + targetTable);
    dg.datagrid('enableFilter');
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