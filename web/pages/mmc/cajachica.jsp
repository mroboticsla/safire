<%-- 
    Document   : index
    Created on : 03-09-2015, 09:17:48 PM
    Author     : Nieto Mendoza
--%>
<%@page import="modeladoDAO.TChequeCCDAO"%>
<%@page import="utilidades.MySQL"%>
<%@page import="uml.MstCuenta"%>
<%@page import="modeladoDAO.MstCuentaBDAO"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.sql.Date"%>
<%@page import="uml.MstGasto"%>
<%@page import="modeladoDAO.MstGastoDAO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="uml.TGastoCC"%>
<%@page import="modeladoDAO.TGastoCCDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>SAFIRES | Modulo Caja Chica</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
        <!--Script para cargar el valor  presupuestado (si lo hay) del tipo de gasto seleccionado -->
        <script type="text/javascript">
            $(function() {
                $('#titleContainer').puipanel({
                    toggleable: false  
                    ,closable: false  
                });
            });
            //Esta funcion hace que se cargue el valor del documento seleccionado.
            function obtenerValor() {
                var indice = document.frmGastos.sltCorrelativoTipoGasto.selectedIndex;
                document.frmGastos.sltCorrelativoTipoGasto2.selectedIndex = indice;
                document.frmGastos.txtValorDocto.value = document.getElementById("sltCorrelativoTipoGasto2").value;
            }
            //Esta funcion hace que se cargue el numero de cuenta del banco seleccionado.
            function obtenerCuenta() {
                var indice = document.frmCheque.sltCuentaBanco.selectedIndex;
                document.frmCheque.sltCuentaBanco2.selectedIndex = indice;
                document.frmCheque.txtCuentaBanco.value = document.getElementById("sltCuentaBanco2").value;
            }
            /*La siguiente funcion envia un menssaje para confirma si se ejecuta la accion señalada o no*/
            function validarEnvio() {
                if (confirm("¿Continuar?")) {
                    //alert("La liquidacion ha sido procesada.");
                    return true;
                }
                else {
                    alert("Proceso cancelado.");
                    return false;
                }
            }
            /*La siguiente funcion verifica que se haya seleccionado un elemento de radio para ejecutar la accion señalada*/
            function validarSeleccion() {
                opciones = document.getElementsByName("rdEditarMovto");

                var seleccionado = false;
                for (var i = 0; i < opciones.length; i++) {
                    if (opciones[i].checked) {
                        seleccionado = true;
                        break;
                    }
                }

                if (!seleccionado) {
                    alert("Debe seleccionar un movimiento.");
                    return false;
                    document.getElementsById("rdEditarMovto").focus();
                }
            }
        </script>
        <% request.setCharacterEncoding("UTF-8"); %>
    </head>
    <%!
        /* Consultas a la tabla maestro de tipos de gasto */
        MstGastoDAO gastoDAO = new MstGastoDAO();
        MstGasto mtsgasto = new MstGasto();
        List<MstGasto> lstGastoDAO = gastoDAO.read();
        /* Consultas a la tabla gastos de caja chica */
        TGastoCCDAO gastoccdao = new TGastoCCDAO();
        TGastoCC gastocc = new TGastoCC();
        TGastoCC gastocc3 = new TGastoCC();
        DecimalFormat df = new DecimalFormat("0.00");
        /* Consulta a las cuentas bancarias*/
        MstCuentaBDAO cuentadao = new MstCuentaBDAO();
        /*Consultar cheque*/
        TChequeCCDAO chequeccdao = new TChequeCCDAO();
        /*Conversor de fecha */
        MySQL fechaOut = new MySQL();
    %>
    <body>
        <div id="cuerpo">
            <div id="contenido">
                <!-- Borrar los fieldset que se utilicen solo para darle forma a la interfaz-->
                <div id="titleContainer" title="Caja Chica" style="height: 30px;"></div>
                
                <div id="">
                    
                    <form name="frmGastos" action="Cajachica" method="POST" >
                        <fieldset>
                            <center><strong>Ingreso de liquidaciones</strong></center><br>
                            <center><table>
                                <tr>
                                    <td><label for="txtFechaGasto">Fecha de Movimiento:</label></td>
                                    <td><input type="text" id="txtFechaLiquidacion" name="txtFechaLiquidacion" value="${fechaLiquidacion}" title="Digite una fecha" placeholder="dd-mm-aaaa" required pattern="^(?:(?:0?[1-9]|1\d|2[0-8])(\/|-)(?:0?[1-9]|1[0-2]))(\/|-)(?:[1-9]\d\d\d|\d[1-9]\d\d|\d\d[1-9]\d|\d\d\d[1-9])$|^(?:(?:31(\/|-)(?:0?[13578]|1[02]))|(?:(?:29|30)(\/|-)(?:0?[1,3-9]|1[0-2])))(\/|-)(?:[1-9]\d\d\d|\d[1-9]\d\d|\d\d[1-9]\d|\d\d\d[1-9])$|^(29(\/|-)0?2)(\/|-)(?:(?:0[48]00|[13579][26]00|[2468][048]00)|(?:\d\d)?(?:0[48]|[2468][048]|[13579][26]))$" /></td>
                                    <td><label for="txtNoLiquidacion">No. De Liquidaci&oacute;n:</label></td>
                                    <td><input type="text" id="txtNoLiquidacion" name="txtNoLiquidacion" value="${noLiquidacion}" ${readonly} required/></td>
                                    <td><label for="txtEfectivoActual">Efectivo Actual:</label></td>
                                    <td><%      double auxiliarEfectivoActual = 0;
                                                double efectivoActual = 0;
                                        auxiliarEfectivoActual = gastoccdao.efectivoActualCajaChica();
                                        if (auxiliarEfectivoActual != 0) {
                                            efectivoActual = auxiliarEfectivoActual;
                                        } else {
                                            efectivoActual = 100;
                                        }%>
                                        
                                        <input type="text" id="txtEfectivoActual" name="txtEfectivoActual" value="<%= df.format(efectivoActual) %>" readonly/><!--$ {Efectivo}--></td>
                                </tr>
                                </table></center>
                            <hr/>
                            <table>
                                <tr>
                                    <td><label for="sltCorrelativoTipoGasto">Tipo Gasto:</label></td>
                                    <td>
                                        <!--Cargamos un select con los tipos de datos registrados previamente-->
                                        <select id="sltCorrelativoTipoGasto" name="sltCorrelativoTipoGasto" required onChange="obtenerValor()">
                                            <option value="">Seleccione tipo de gasto</option>
                                            <%for (MstGasto gastos : lstGastoDAO) {%>
                                            <option value="<%= gastos.getCorr_gasto()%>"><%= gastos.getDesc_gasto()%></option>
                                            <%}%>
                                        </select>
                                        <!--Cargamos un segundo select con el atributo hidden que nos servira para capturar el valor del tipo de gasto seleccionado en el select anterior -->
                                        <select id="sltCorrelativoTipoGasto2" name="sltCorrelativoTipoGasto2" hidden>
                                            <option value="">Seleccione tipo de gasto</option>
                                            <%for (MstGasto gastos : lstGastoDAO) {%>
                                            <option value="<%= df.format(gastos.getValor_gasto())%>"><%= df.format(gastos.getValor_gasto())%></option>
                                            <%}%>
                                        </select>
                                    </td>
                                    <td><label for="txtNoReciboFactura">No. Recibo/Factura:</label></td>
                                    <td><input type="text" id="txtNoReciboFactura" name="txtNoReciboFactura" value="${noReciboFactura}" ${readonly} required/></td>
                                    <td><label for="txtFechaDocto">Fecha de Documento:</label></td>
                                    <td><input type="text" id="txtFechaDocto" name="txtFechaDocto" value="${fechaDocto}" title="Digite una fecha" placeholder="dd-mm-aaaa" required  pattern="^(?:(?:0?[1-9]|1\d|2[0-8])(\/|-)(?:0?[1-9]|1[0-2]))(\/|-)(?:[1-9]\d\d\d|\d[1-9]\d\d|\d\d[1-9]\d|\d\d\d[1-9])$|^(?:(?:31(\/|-)(?:0?[13578]|1[02]))|(?:(?:29|30)(\/|-)(?:0?[1,3-9]|1[0-2])))(\/|-)(?:[1-9]\d\d\d|\d[1-9]\d\d|\d\d[1-9]\d|\d\d\d[1-9])$|^(29(\/|-)0?2)(\/|-)(?:(?:0[48]00|[13579][26]00|[2468][048]00)|(?:\d\d)?(?:0[48]|[2468][048]|[13579][26]))$" /></td>
                                    <td><label for="txtValorDocto">Valor:</label></td>
                                    <td><input type="text" id="txtValorDocto" name="txtValorDocto" value="" required="true" /><br></td>
                                </tr>
                                <tr>
                                    <td colspan="2"></td>
                                    <td><label for="txtDescripcionDocto">Descripci&oacute;n:</label></td>
                                    <td  colspan="3"><input type="text" id="txtDescripcionDocto" name="txtDescripcionDocto" value="${descripcionDocto}" required/></td>
                                    <td colspan="2">
                                        <input type="submit" id="btnAgregarMovimiento" name="btnAgregarMovimiento" value="Agregar Movimiento"/>
                                        ${buttonSaveChanges} ${buttonCancelEdition}
                                    </td>
                                </tr>
                            </table>
                        </fieldset>
                    </form>
                    <form name="frmGastos2" action="Cajachica" method="POST">
                        <fieldset>
                            <center><strong>Movimientos de la &uacute;ltima liquidaci&oacute;n</strong></center><br>
                            <div style=" width: 100%; height: 150px; overflow-y: scroll; border: #aaa double;">
                                <table border="2" rules="all">
                                    <thead>
                                        <tr>
                                            <th>Cod. Residencial</th>
                                            <th>No. Liquidaci&oacute;n</th>
                                            <th>Fecha Liguidac&oacute;n</th>
                                            <th>Correlativo Gasto</th>
                                            <th>No. Documento</th>
                                            <th>Fecha Documento</th>
                                            <th>Valor Documento</th>
                                            <th>Cta. Contable</th>
                                            <th>Descripci&oacute;n Gasto</th>
                                            <th>Estado</th>
                                            <th>Cod. Usuario</th>
                                            <th>Fecha Ingreso</th>
                                            <th>Seleccion</th>
                                        </tr>
                                    </thead>
                                    <tbody border="2">
                                        <%
                                            TGastoCCDAO tgastoccdao = new TGastoCCDAO();
                                            List<TGastoCC> lstMovimientos = tgastoccdao.cargarMovimientos();
                                            for (TGastoCC gastocc : lstMovimientos) {
                                        %>
                                        <tr>
                                            <td><%= gastocc.getCod_residencial()%></td>
                                            <td><%= gastocc.getNum_liquidacion()%></td>
                                            <td><%= fechaOut.dmaSalida(gastocc.getFecha_liquidacion().toString())%></td>
                                            <td><%= gastocc.getCorr_gasto()%></td>
                                            <td><%= gastocc.getNum_docto()%></td>
                                            <td><%= fechaOut.dmaSalida(gastocc.getFecha_docto().toString())%></td>
                                            <td><%= df.format(gastocc.getValor_docto())%></td>
                                            <td><%= gastocc.getCod_cta_conta()%></td>
                                            <td><%= gastocc.getDesc_gasto()%></td>
                                            <td><%= gastocc.getEstado() %></td>
                                            <td><%= gastocc.getCod_usuario()%></td>
                                            <td><%= fechaOut.dmaSalida(gastocc.getFecha_ingreso().toString())%></td>

                                            <td><input type="radio" id="rdEditarMovto" name="rdEditarMovto" value="<%= gastocc.getNum_docto()%>"/>
                                            </td>
                                        </tr>
                                        <% }%>                                      
                                    </tbody>
                                </table>
                            </div>                    
                            <br>
                            <div>
                                <table>
                                    <%  double auxiliarEfectivoCaja = 0;
                                        double totalMovimientos = 0;
                                        double efectivoCaja = 0;

                                        List<TGastoCC> lstGastoCC = gastoccdao.totalMovimientos();

                                        for (TGastoCC gastocc2 : lstGastoCC) {
                                            totalMovimientos = gastocc2.getTotal();
                                            auxiliarEfectivoCaja = gastocc2.getEfectivo();
                                        }
                                        if (auxiliarEfectivoCaja == 0) {
                                            efectivoCaja = 100;
                                        } else {
                                            efectivoCaja = auxiliarEfectivoCaja;
                                        }%>
                                    <tr>
                                        <td colspan="9" rowspan="2"></td>
                                        <td><label for="txtTotalMovimientos">Total:</label></td>
                                        <td><input type="text" id="txtTotalMovimientos" name="txtTotalMovimientos" value="<%= df.format(totalMovimientos)%>" readonly></td>
                                    </tr>
                                    <tr>
                                        <td><label for="txtEfectivoCaja">Efectivo en Caja:</label></td>
                                        <td><input type="text" id="txtEfectivoCaja" name="txtEfectivoCaja" value="<%= df.format(efectivoCaja)%>" readonly></td>
                                    </tr>
                                </table>
                            </div>
                        </fieldset>
                        <fieldset>
                            <input type="submit" id="btnModificarMovto" value="Modificar Movimiento" name="btnModificarMovto" onclick="return validarSeleccion()" />
                            <input type="submit" id="btnBorrarMovto" value="Borrar Movimiento" name="btnBorrarMovto" onclick="return validarSeleccion()"/>


                            <input type="submit" id="btnIngresarCheque" value="Ingresar Cheque" name="btnIngresarCheque" onclick="return validarEnvio()"/>
                            <input type="submit" id="btnGrabarLiquidacion" value="Grabar Liquidaci&oacute;n" name="btnGrabarLiquidacion" onclick="return validarEnvio()"/>

                            <input type="submit" id="btnBorrarLiquidacion" value="Borrar Liquidacion" name="btnBorrarLiquidacion" onclick="return validarEnvio()"/>
                            <div>
                                ${loginAutorizacion}
                            </div> 
                        </fieldset>
                        <fieldset>
                            <strong> ${sqlresp}</strong><strong> ${sqlresp2}</strong><strong>${addCheckConfirm}</strong>
                        </fieldset>
                    </form>
                    <form name="frmCheque" action="Cajachica" method="POST">
                        <fieldset>
                            Cheque autorizado a emitirse:<br/><br/>
                            <table>
                                <tr>
                                    <td><label for="sltCuentaBanco">Banco:</label></td>
                                    <td>
                                        <select id="sltCuentaBanco" name="sltCuentaBanco" required onchange="obtenerCuenta()">
                                            <option value="">Seleccione banco</option>
                                            <%  List<MstCuenta> lstNombreBanco = cuentadao.cargarBancoCuenta();
                                                for (MstCuenta cuentaB : lstNombreBanco) {%>
                                            <option value="<%= cuentaB.getCorr_banco().getCorr_banco()%>"><%= cuentaB.getCorr_banco().getNombre_banco()%></option>
                                            <%}%>
                                        </select>
                                        <select id="sltCuentaBanco2" name="sltCuentaBanco2" hidden>
                                            <option value="">Seleccione banco</option>
                                            <%  List<MstCuenta> lstCuentaBanco = cuentadao.cargarBancoCuenta();
                                                for (MstCuenta cuenta : lstCuentaBanco) {%>
                                            <option value="<%= cuenta.getNum_cta_banco() %>"> <%= cuenta.getNum_cta_banco() %></option>
                                            <%}%>
                                        </select>
                                    </td>
                                    <td><label for="txtCuentaBanco">No. Cuenta Bancaria: </label></td>
                                    <td><input type="text" id="txtCuentaBanco" name="txtCuentaBanco" value="" readonly/></td>
                                    <td><label for="txtFechaCheque">Fecha cheque: </label></td>
                                    <td><input type="text" id="txtFechaChueque" name="txtFechaCheque" value="" title="Digite una fecha" placeholder="dd-mm-aaaa" required  pattern="^(?:(?:0?[1-9]|1\d|2[0-8])(\/|-)(?:0?[1-9]|1[0-2]))(\/|-)(?:[1-9]\d\d\d|\d[1-9]\d\d|\d\d[1-9]\d|\d\d\d[1-9])$|^(?:(?:31(\/|-)(?:0?[13578]|1[02]))|(?:(?:29|30)(\/|-)(?:0?[1,3-9]|1[0-2])))(\/|-)(?:[1-9]\d\d\d|\d[1-9]\d\d|\d\d[1-9]\d|\d\d\d[1-9])$|^(29(\/|-)0?2)(\/|-)(?:(?:0[48]00|[13579][26]00|[2468][048]00)|(?:\d\d)?(?:0[48]|[2468][048]|[13579][26]))$" /></td>
                                    <td><label for="txtNoCheque">No. Cheque: </label></td>
                                    <td><input type="text" id="txtNoCheque" name="txtNoCheque" value="" required/></td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td></td>
                                    <td><label for="txtNombreCheque">Cheque a nombre de: </label></td>
                                    <td colspan="3"><input type="text" id="txtNombreCheque" name="txtNombreCheque" value="" required/></td>

                                    <td><label for="txtValorCheque">Valor: </label></td>
                                    <td>
                                        <%      double auxiliarChequePendiente = 0;
                                                double chequePendiente = 0;
                                                
                                        auxiliarChequePendiente = chequeccdao.chequePendiente();
                                        if (auxiliarChequePendiente != 0) {
                                            chequePendiente = auxiliarChequePendiente;
                                        } else {
                                            chequePendiente = totalMovimientos;
                                        }%>
                                        <input type="text" id="txtValorCheque" name="txtValorCheque" value="<%= df.format(chequePendiente)%>"  readonly/></td>
                                </tr>
                            </table>
                            ${buttonAddCheck}
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
        <div id="pie">
            Pie
        </div>
    </body>
</html>
