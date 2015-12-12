<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : index
    Created on : 03-09-2015, 09:17:48 PM
    Author     : Nieto Mendoza
--%>

<%@page import="uml.TCCuenta"%>
<%@page import="modeladoDAO.MstBancoDAO"%>
<%@page import="uml.MstBanco"%>
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
        <script language="JavaScript" type="text/javascript">
            
            $(function() {
                $('#titleContainer').puipanel({
                    toggleable: false  
                    ,closable: false  
                });
            });
            
            //Esta funcion hace que se cargue el saldo de la cuenta contable seleccionada a la cuenta bancaria.
            function obtenerSaldo() {
                var indice = document.frmCuentasBancarias.sltCuentaConta.selectedIndex;
                document.frmCuentasBancarias.sltCuentaConta2.selectedIndex = indice;
                //document.frmCuentasBancarias.txtCuentaSaldo.value = document.getElementById("sltCuentaConta2").value;
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
        <%
            request.setCharacterEncoding("UTF-8");

        %>
    </head>
    <%!
        /*Mostrar dos decimales en las cantidades numericas tipo real*/
        DecimalFormat df = new DecimalFormat("0.00");
        /* Consulta a las cuentas bancarias*/
        MstBancoDAO bancodao = new MstBancoDAO();
        MstBanco banco = new MstBanco();
        MstCuentaBDAO cuentadao = new MstCuentaBDAO();

        /*Conversor de fecha */
        MySQL fechaOut = new MySQL();
        MySQL sql = new MySQL();
    %>

    <body>
        <!-- Borrar los fieldset que se utilicen solo para darle forma a la interfaz-->
        
        <div id="titleContainer" title="Bancos" style="height: 30px;"></div>
        
        <div id="cuerpo">
                <div id=""><fieldset>Cuentas Bancarias</fieldset></div>
                <div id=""><fieldset>Bienvenido | Usuario</fieldset></div>
                <div >
                    <form name="frmBancos" action="Bancos" method="POST">
                        <fieldset>
                            <center><strong>Agregar Bancos</strong></center><br/>
                            <label for="txtNombreBanco">Nombre del banco:</label>
                            <input type="text" id="txtNombreBanco" name="txtNombreBanco" value="" title="Digite nombre del banco" required />
                            <label for="txtFechaCreacion">Fecha de creaci&oacute;n:</label>
                            <input type="text" id="txtFechaCreacion" name="txtFechaCreacion" value="" placeholder="dd-mm-aaaa"   title="Digite una fecha" pattern="^(?:(?:0?[1-9]|1\d|2[0-8])(\/|-)(?:0?[1-9]|1[0-2]))(\/|-)(?:[1-9]\d\d\d|\d[1-9]\d\d|\d\d[1-9]\d|\d\d\d[1-9])$|^(?:(?:31(\/|-)(?:0?[13578]|1[02]))|(?:(?:29|30)(\/|-)(?:0?[1,3-9]|1[0-2])))(\/|-)(?:[1-9]\d\d\d|\d[1-9]\d\d|\d\d[1-9]\d|\d\d\d[1-9])$|^(29(\/|-)0?2)(\/|-)(?:(?:0[48]00|[13579][26]00|[2468][048]00)|(?:\d\d)?(?:0[48]|[2468][048]|[13579][26]))$" required/>
                            <input type="submit" id="btnAgregarBanco" name="btnAgregarBanco" value="Agregar Banco">
                            <br/>
                        </fieldset>
                    </form>

                    <form  name="frmCuentasBancarias" action="Bancos" method="POST"> 
                        <fieldset id="formFieldsetSpace">
                            <center><strong>Agregar Cuentas Bancarias</strong></center><br/>
                            <div id="bankList">
                                <table border="2" rules="all">
                                    <thead>
                                        <tr>
                                            <th>No. Correlativo</th>
                                            <th>Nombre del banco</th>
                                            <th>Selecci&oacute;n</th>
                                        </tr>
                                    </thead>
                                    <%  List<MstBanco> lstNombreBanco = bancodao.read();
                                            for (MstBanco banco2 : lstNombreBanco) {%>
                                    <tbody>
                                        <tr>
                                            <td><%= banco2.getCorr_banco()%></td>
                                            <td><%= banco2.getNombre_banco()%></td>
                                            <td><input type="radio" id="rdSelectBank" name="rdSelectBank" title="Para poder agregar un numero de cuenta" value="<%= banco2.getCorr_banco()%>" required/></td>
                                        </tr>
                                    </tbody>
                                    <%}%>
                                </table><br/>
                            </div>
                            <div id="addBanckAccount">
                                <label for="txtNoCuenta">No. Cuenta Bancaria: </label><br/>
                                <input type="" id="txtNoCuenta" name="txtNoCuenta" value="" title="Digite cuenta bancaria" required/><br/><br/>

                                <label for="sltCuentaConta">Cuenta Contable: </label><br/>

                                <select id="sltCuentaConta" name="sltCuentaConta" onChange="obtenerSaldo()" style="width: 200px;" required>
                                    <option value="">Seleccione cuenta contable</option>
                                    <%  List<TCCuenta> lstCuentaConta = cuentadao.cargarCuentaCuenta();
                                            for (TCCuenta cuentaConta : lstCuentaConta) {%>
                                    <option value="<%= cuentaConta.getCod_cta_conta()%>"><%= cuentaConta.getCod_cta_conta() + " " + cuentaConta.getDesc_cta_contab()%> </option>
                                    <%}%>
                                </select>
                                <select id="sltCuentaConta2" name="sltCuentaConta2" hidden required>
                                    <option value="">Seleccione cuenta contable</option>
                                    <%  for (TCCuenta cuentaConta : lstCuentaConta) {%>
                                    <option value="<%= cuentaConta.getSaldo()%>"><%= cuentaConta.getSaldo()%> </option>
                                    <%}%>
                                </select><br/><br/>
                                <input type="submit" id="btnAgregarCuenta" name="btnAgregarCuenta" value="Agregar Cuenta">
                            </div>
                        </fieldset>
                        <fieldset>
                            <strong> ${sqlresp}</strong><strong> ${sqlresp2}</strong><strong>${addCheckConfirm}</strong>
                        </fieldset>
                        <fieldset>
                            <center><strong>Agregar Cuentas Bancarias</strong></center><br/>
                            <div style=" width: 100%; height: 150px; overflow-y: scroll;">
                                <center><table border="2" rules="all">
                                    <thead>
                                        <tr>
                                            <th>No. Correlativo</th>
                                            <th>Nombre del banco</th>
                                            <th>No. De Cuenta</th>
                                            <th>Fecha Creación</th>
                                            <th>Cuenta Contable</th>
                                            <th>Saldo Actual</th>

                                        </tr>
                                    </thead>
                                    <%  List<MstCuenta> lstCuentasBancarias = cuentadao.cargarBancoCuenta();

                                        for (MstCuenta cuenta : lstCuentasBancarias) {%>
                                    <tbody>
                                        <tr>
                                            <td><%= cuenta.getCorr_banco().getCorr_banco()%></td>
                                            <td><%= cuenta.getCorr_banco().getNombre_banco()%></td>
                                            <td><%= cuenta.getNum_cta_banco()%></td>
                                            <td><%= cuenta.getFecha_creacion()%></td>
                                            <td><%= cuenta.getCod_cta_conta()%></td>
                                            <td><%= cuenta.getSaldo_cta()%></td>
                                        </tr>
                                    </tbody>
                                    <%}%>
                                </table></center><br/>
                            </div>    

                        </fieldset>
                    </form>
                </div>
            </div>
            <div id="pie">
                Pie
            </div>
    </body>
</html>
