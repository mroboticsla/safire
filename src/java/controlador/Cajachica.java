/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modeladoDAO.MstGastoDAO;
import modeladoDAO.TChequeCCDAO;
import modeladoDAO.TGastoCCDAO;
import uml.MstGasto;
import uml.MstUsuario;
import uml.TChequeCC;
import uml.TGastoCC;
import utilidades.MySQL;

/**
 *
 * @author Nieto Mendoza
 */
public class Cajachica extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            /* Consultas a la tabla maestro de tipos de gasto */
            MstGastoDAO gastoDAO = new MstGastoDAO();
            MstGasto mtsgasto = new MstGasto();
            /* Consultas a la tabla gastos de caja chica */
            TGastoCCDAO gastoCCDAO = new TGastoCCDAO();
            TGastoCC gastocc = new TGastoCC();
            TGastoCC gastocc2 = new TGastoCC();
            TGastoCC gastocc3 = new TGastoCC();
            /*Convertidor de fecha */
            MySQL fechaIn = new MySQL();
            MySQL fechaOut = new MySQL();
            /*Variables necesarias en el proceso de captura e insercion de datos */
            RequestDispatcher rd = null;
            String sqlresp = null;
            String sqlresp2 = null;
            String addCheckConfirm = null;
            DecimalFormat df = new DecimalFormat("0.00");

            try {

                //******************** AGREGANDO MOVIMIENTOS/LIQUIDACION *****************************
                //Declaramos variables auxiliares.
                Date fechaLiquidacion = null;
                String noLiquidacion = null;
                double efectivoActual = 0;
                int correlativoTipoGasto = 0;
                String noReciboFactura = null;
                Date fechaDocto = null;
                double valorDocto = 0;
                String descripcionDocto = null;
                String estadoLiquidacion = "N";
                Date fechaIngreso = null;

                //Hacemos un trycatch por si surge un error no quiebre la aplicacion...
                try {
                    //Capturamos los valores enviados desde la pagina JSP
                    fechaLiquidacion = Date.valueOf(fechaIn.amdEntrada(request.getParameter("txtFechaLiquidacion")));
                    noLiquidacion = request.getParameter("txtNoLiquidacion");
                    efectivoActual = Double.parseDouble(request.getParameter("txtEfectivoActual"));
                    correlativoTipoGasto = Integer.parseInt(request.getParameter("sltCorrelativoTipoGasto"));
                    noReciboFactura = request.getParameter("txtNoReciboFactura");
                    fechaDocto = Date.valueOf(fechaIn.amdEntrada(request.getParameter("txtFechaDocto")));
                    valorDocto = Double.valueOf(request.getParameter("txtValorDocto"));
                    descripcionDocto = request.getParameter("txtDescripcionDocto");
                    fechaIngreso = Date.valueOf(LocalDate.now()); //Fecha autogenerada.
                } catch (Exception e) {
                    //Esto nos muestra un msj en caso que haya alguna excepcion de las intancias en la condicion
                    if (e instanceof NumberFormatException) {
                        sqlresp2 = "Algun dato numerico no es correcto: " + e.toString();
                    } else if (e instanceof IllegalAccessException) {
                        sqlresp2 = "Verifique los datos ingresados sean correctos: " + e.toString();
                    }
                }

                if (request.getParameter("btnAgregarMovimiento") != null) {

                    /*Extraemos los datos de Maestro de Gastos cargar los gastos en la liquidacion */
                    int codigoResidencial = 0; //Dato tomado a partir de el correlativo de tipo de gasto.
                    String cuentaContable = null; //Dato tomado a partide del correlativo de tipo de gasto.
                    String codigoUsuario = null; //VERIFICAR DE DONDE DONDE TOMO ESTE DATO O COMO

                    mtsgasto.setCorr_gasto(correlativoTipoGasto);
                    List<MstGasto> lstGasto = gastoDAO.search(mtsgasto);
                    for (MstGasto gasto : lstGasto) {
                        codigoResidencial = gasto.getCod_residencial();
                        cuentaContable = gasto.getCod_cta_conta();
                        codigoUsuario = gasto.getCod_usuario();//CAMBIAR YA QUE ESTE NO ES DE LA SESION ACTIVA
                    }

                    //VALIDACIONES DE LADO DEL SERVIDOR || VALIDACIONES DEL LADO DEL SERVIDOR
                    /*Preparamos la clase TGastosCC*/
                    gastocc3.setCod_residencial(codigoResidencial);
                    gastocc3.setNum_liquidacion(noLiquidacion);
                    gastocc3.setFecha_liquidacion(fechaLiquidacion);
                    gastocc3.setCorr_gasto(correlativoTipoGasto);
                    gastocc3.setNum_docto(noReciboFactura);
                    gastocc3.setFecha_docto(fechaDocto);
                    gastocc3.setValor_docto(valorDocto);
                    gastocc3.setCod_cta_conta(cuentaContable);
                    gastocc3.setDesc_gasto(descripcionDocto);
                    gastocc3.setEstado(estadoLiquidacion);
                    gastocc3.setCod_usuario(codigoUsuario);
                    gastocc3.setFecha_ingreso(fechaIngreso);

                    /*Validamos que el numero de liquidacion que se ingresa no haya sido registrado previamente*/
                    gastocc.setNum_liquidacion(noLiquidacion);
                    gastocc.setCorr_gasto(correlativoTipoGasto);
                    gastocc.setNum_docto(noReciboFactura);
                    gastocc.setFecha_docto(fechaDocto);

                    /*Declaramos variables para capturar los datos provenientes de la base*/
                    String existeLiqui = null;
                    String existeDocto = null;
                    String liquiPendiente = null;
                    existeLiqui = gastoCCDAO.comprobarLiquidacionNueva(gastocc); //Verificamos que la liquidacion no exista en la tabla fija.
                    existeDocto = gastoCCDAO.comprobarDocumento(gastocc); //Verificamos que el numero de documento no exista en la tabla fija.
                    liquiPendiente = gastoCCDAO.comprobarLiquidacion(gastocc); //Verificamos que el numero de liquidacion del movimiento sea igual que el de la nueva liquidacion
                    /*Declaro las siguientes variables para hacer vlidaciones en cuanto a montos maximos establecidos*/
                    double auxiliarEfectivo = 0; //Para indicar el valor maximo autorizado.
                    double totalMovimientos = 0; //
                    double efectivoCaja = 0; // Es el total restante del valor maximo autorizado

                    List<TGastoCC> lstGastoCC = gastoCCDAO.totalMovimientos();

                    for (TGastoCC gastocc1 : lstGastoCC) {
                        totalMovimientos = gastocc1.getTotal();
                        auxiliarEfectivo = gastocc1.getEfectivo();
                    }
                    if (auxiliarEfectivo == 0) {
                        efectivoCaja = 100; //LA ASIGNACION DE ESTA VARIABLE TIENE QUE SER DINAMICA /*SOLICITAR INFORMACION DE QUE TABLA PROVIENE*/
                    } else {
                        efectivoCaja = auxiliarEfectivo;
                    }
                    boolean incumplida = false;
                    /*double efectivoCaja = Double.parseDouble(request.getParameter("txtEfectivoCaja"));*/
                    if (valorDocto > 0) {
                        incumplida = false;
                        //Si es mayor que cero, pasa.
                        if (valorDocto <= 100) { //EL VALOR 100 TIENE QUE ASIGNARSE DE FORMA DINAMICA YA QUE SI CAMBIAN EL VALOR DE CAJA CHICA ESTE GENERARIA UN PROBLEMA /*/*SOLICITAR INFORMACION DE QUE TABLA PROVIENE*/*/
                            incumplida = false;
                            //Si es menor que cien, pasa.
                            if (valorDocto < efectivoCaja) {
                                incumplida = false;
                                //Si es mejor que el efectivo en caja disponible, pasa.
                                /*Verificamos que la liquidacion y el numero de documento no esten registrados */
                                //Verificamos que no exista la liquidacion en la tabla fija.
                                if (existeLiqui == null) {
                                    incumplida = false;
                                    //Verificamos que el documento no existe en la tabla fija.
                                    if (existeDocto == null) {
                                        incumplida = false;
                                        if (liquiPendiente==null) {
                                            incumplida = false;
                                            /*Si las condiciones anteriores se cumplen entonces agregamos el movimiento a la tabla temporarl*/
                                            request.setAttribute("noLiquidacion", request.getParameter("txtNoLiquidacion"));
                                            sqlresp = gastoCCDAO.create(gastocc3);
                                        }else{
                                            incumplida = true;
                                            sqlresp = "No se puede agregar movimiento. <br/>" + liquiPendiente;
                                        }
                                    } else {
                                        incumplida = true;
                                        sqlresp = "No se puede agregar movimiento. <br/>" + existeDocto;
                                    }
                                } else {
                                    incumplida = true;
                                    sqlresp = "No se puede agregar movimiento. <br/>" + existeLiqui;
                                }
                            } else {
                                incumplida = true;
                                sqlresp = "No se puede agregar movimiento. <br> El valor del documento sobrepasa el efectivo disponible.";
                            }
                        } else {
                            incumplida = true;
                            sqlresp = "No se puede agregar movimiento. <br> El valor del documento no puede ser mayor que el efectivo designado a caja chica.";
                        }
                    } else {
                        incumplida = true;
                        sqlresp = "No se puede agregar movimiento. <br> El valor del documento no puede ser negativo o cero.";
                    }

                    if (incumplida == true) {
                        /* Si no se cumple alguna condiciones mantenemos los datos ingresados */
                        request.setAttribute("fechaLiquidacion", request.getParameter("txtFechaLiquidacion"));
                        request.setAttribute("noLiquidacion", request.getParameter("txtNoLiquidacion"));
                        request.setAttribute("noReciboFactura", request.getParameter("txtNoReciboFactura"));
                        request.setAttribute("fechaDocto", request.getParameter("txtFechaDocto"));
                        request.setAttribute("descripcionDocto", request.getParameter("txtDescripcionDocto"));
                    }
                }
                //**************************** GUARDANDO CAMBIOS EN MOVIMIENTO MODIFICADO***********************
                if (request.getParameter("btnGuardarCambio") != null) {

                    /*Extraemos los datos de Maestro de Gastos cargar los gastos en la liquidacion */
                    int codigoResidencial = 0;
                    String cuentaContable = null;
                    String codigoUsuario = null; //CAPTURAR LA VARIABLE DE SESION PARA GUARDAR ROL O CODIGO DE USUARIO
                                                                        
                    mtsgasto.setCorr_gasto(correlativoTipoGasto);       
                    List<MstGasto> lstGasto = gastoDAO.search(mtsgasto);    
                    for (MstGasto gasto : lstGasto) {                      
                        codigoResidencial = gasto.getCod_residencial();         
                        cuentaContable = gasto.getCod_cta_conta();          
                        codigoUsuario = gasto.getCod_usuario();//CAMBIAR YA QUE ESTE NO ES DE LA SESION ACTIVA
                    }

                    //VALIDACIONES || VALIDACIONES
                    gastocc3.setCod_residencial(codigoResidencial);
                    gastocc3.setFecha_liquidacion(fechaLiquidacion);
                    gastocc3.setCorr_gasto(correlativoTipoGasto);
                    gastocc3.setNum_docto(noReciboFactura);
                    gastocc3.setFecha_docto(fechaDocto);
                    gastocc3.setValor_docto(valorDocto);
                    gastocc3.setCod_cta_conta(cuentaContable);
                    gastocc3.setDesc_gasto(descripcionDocto);
                    gastocc3.setEstado(estadoLiquidacion);
                    gastocc3.setCod_usuario(codigoUsuario);
                    gastocc3.setFecha_ingreso(fechaIngreso);
                    gastocc3.setNum_liquidacion(noLiquidacion);

                    /*Validamos que el numero de liquidacion que se ingresa no haya sido registrado previamente*/
                    gastocc.setNum_liquidacion(noLiquidacion);
                    gastocc.setCorr_gasto(correlativoTipoGasto);
                    gastocc.setNum_docto(noReciboFactura);
                    gastocc.setFecha_docto(fechaDocto);

                    /*Declaro las siguientes variables para hacer vlidaciones en cuanto a montos maximos establecidos*/
                    double auxiliarEfectivo = 0;
                    double totalMovimientos = 0;
                    double efectivoCaja = 0;

                    List<TGastoCC> lstGastoCC = gastoCCDAO.totalMovimientos();

                    for (TGastoCC gastocc1 : lstGastoCC) {
                        totalMovimientos = gastocc1.getTotal();
                        auxiliarEfectivo = gastocc1.getEfectivo();
                    }
                    if (auxiliarEfectivo == 0) {
                        efectivoCaja = 100; //LA ASIGNACION DE ESTA VARIABLE TIENE QUE SER DINAMICA /*SOLICITAR INFORMACION DE QUE TABLA PROVIENE*/
                    } else {
                        efectivoCaja = auxiliarEfectivo;
                    }

                    /*double efectivoCaja = Double.parseDouble(request.getParameter("txtEfectivoCaja"));*/
                    if (valorDocto > 0) {

                        //Si es mayor que cero, pasa.
                        if (valorDocto <= 100) { //EL VALOR 100 TIENE QUE ASIGNARSE DE FORMA DINAMICA YA QUE SI CAMBIAN EL VALOR DE CAJA CHICA ESTE GENERARIA UN PROBLEMA /*/*SOLICITAR INFORMACION DE QUE TABLA PROVIENE*/*/

                            //Si es menor que cien, pasa.
                            if (valorDocto < efectivoCaja) {
                                //Si es mejor que el efectivo en caja disponible, pasa.
                                sqlresp = gastoCCDAO.modificarMovimiento(gastocc3);
                                request.setAttribute("readonly", "");
                                request.setAttribute("buttonSavedChanges", "");
                            } else {

                                sqlresp = "No se puede agregar movimiento. <br> El valor del documento sobrepasa el efectivo disponible.";
                            }
                        } else {

                            sqlresp = "No se puede agregar movimiento. <br> El valor del documento no puede ser mayor que el efectivo designado a caja chica.";
                        }
                    } else {

                        sqlresp = "No se puede agregar movimiento. <br> El valor del documento no puede ser negativo o cero.";
                    }

                }
                //*********************** GRABANDO LIQUIDACION ************************************

                /* Cuando presionamos el boton grabar liquidacion se llaman los metodos correspondientes */
                if (request.getParameter("btnGrabarLiquidacion") != null) {
                    TGastoCCDAO tgastoccdao = new TGastoCCDAO();
                    TGastoCC gasto = new TGastoCC();
                    int residencial = 0;
                    String liquidacion = null;
                    String comprobarPendiente = tgastoccdao.comprobarLiquidacionPendiente();
                    
                    List<TGastoCC> lstLiquidacion = tgastoccdao.cargarMovimientos();
                    for (TGastoCC gastocc4 : lstLiquidacion) {
                        residencial = gastocc4.getCod_residencial();
                        liquidacion = gastocc4.getNum_liquidacion();
                    }
                    if (liquidacion!=null) {
                        if (comprobarPendiente==null) {
                            gasto.setCod_residencial(residencial);
                            gasto.setNum_liquidacion(liquidacion);
                            sqlresp2 = gastoCCDAO.grabarLiquidacion(gasto);
                        }else{
                        sqlresp2=comprobarPendiente;
                        }
                    }else{
                        sqlresp2 = "No hay ninguna liquidacion nueva.";
                    }
                }

                //************************ BORRANDO MOVIMIENTO ***************************************
                /* Cuando presionamos el boton borrar movimiento de liquidacion se llaman los metodos correspondientes */
                if (request.getParameter("btnBorrarMovto") != null) {
                    String movto = request.getParameter("rdEditarMovto");
                    /*Hago uso del objeto creado al incio gastocc2*/
                    gastocc2.setNum_docto(request.getParameter("rdEditarMovto"));
                    sqlresp = gastoCCDAO.borrarMovimiento(gastocc2);
                }

                //****************** ELIMINADO TODOS LOS REGISTROS (MOVIMIENTOS)DE UNA LIQUIDACION *****************
                /* Cuando presionamos el boton borrar liquidacion se llaman los metodos correspondientes */
                if (request.getParameter("btnBorrarLiquidacion") != null) {
                    int residencial = 0;
                    String liquidacion = null;

                    TGastoCCDAO tgastoccdao = new TGastoCCDAO();
                    TGastoCC gasto = new TGastoCC();
                    List<TGastoCC> lstLiquidacion = tgastoccdao.cargarMovimientos();
                    for (TGastoCC gastocc4 : lstLiquidacion) {
                        residencial = gastocc4.getCod_residencial();
                        liquidacion = gastocc4.getNum_liquidacion();
                    }
                    
                    if (liquidacion!=null) {
                        gasto.setCod_residencial(residencial);
                    gasto.setNum_liquidacion(liquidacion);
                    
                    sqlresp2 = gastoCCDAO.delete(gasto);
                    }else{
                        sqlresp2="No hay liquidación para eliminar.";
                    }
                }
                //*********CARGANDO LOS DATOS DEL MOVIMIENTO A MODIFICAR********************
                /* Cuando presionamos el boton modificar movimiento de liquidacion se llaman los metodos correspondientes */
                if (request.getParameter("btnModificarMovto") != null) {
                    try {
                        String movto = request.getParameter("rdEditarMovto");
                        /*Hago uso del objeto creado al incio gastocc2*/
                        gastocc2.setNum_docto(request.getParameter("rdEditarMovto"));
                        List<TGastoCC> lstMovto = gastoCCDAO.cargarMovimiento(gastocc2);
                        Date fliquidacion = null;
                        String nliquidacion = null;
                        String ndocto = null;
                        Date fdocto = null;
                        String desdocto = null;
                        for (TGastoCC mov : lstMovto) {
                            fliquidacion = mov.getFecha_liquidacion();
                            nliquidacion = mov.getNum_liquidacion();
                            ndocto = mov.getNum_docto();
                            fdocto = mov.getFecha_docto();
                            desdocto = mov.getDesc_gasto();
                        }
                        request.setAttribute("fechaLiquidacion", fechaOut.dmaSalida(fliquidacion.toString()));
                        request.setAttribute("noLiquidacion", nliquidacion);
                        request.setAttribute("noReciboFactura", ndocto);
                        request.setAttribute("fechaDocto", fechaOut.dmaSalida(fdocto.toString()));
                        request.setAttribute("descripcionDocto", desdocto);
                        request.setAttribute("readonly", "readonly"); //Para evitar que se modifique algun campo.
                        request.setAttribute("buttonSaveChanges", "<input type=\"submit\" id=\"btnList\" name=\"btnGuardarCambio\" value=\"Listo\" />");
                        //request.setAttribute("buttonCancelEdition", "<input type=\"submit\" id=\"btnCancelar\" name=\"btnCancelar\" value=\"Cancelar\" >");
                        sqlresp = request.getParameter("rdEditarMovto");
                    } catch (Exception e) {
                        if (e instanceof NumberFormatException) {
                            sqlresp2 = "Algun dato numerico no es correcto: " + e.toString();
                        } else if (e instanceof IllegalAccessException) {
                            sqlresp2 = "Verifique los datos ingresados sean correctos: " + e.toString();
                        } else if (e instanceof NullPointerException) {
                            sqlresp = "No hay movimientos que modificar. ";//+e.toString();  e = java.lang.NullPointerException si no se captura
                        }
                    }
                    
                }
                //******************* DESPLEGANDO CAMPOS PARA LA AUTORIZACION DEL CHEQUE *******************************

                if (request.getParameter("btnIngresarCheque") != null) {
                    /*El siquiente atritubo lo que hace es crear de forma dinamica una tabla con dos cajas
                     de texto para ingresar los datos de usuario que autoricen el cheque.
                     */
                    request.setAttribute("loginAutorizacion", "<table>\n"
                            + "                            <tr>\n"
                            + "                                 <td><label for=\"txtUsuario\">Usuario:</label></td>\n"
                            + "                                 <td><input type=\"text\" id=\"txtUsuario\" name=\"txtUsuario\" value=\"\" autofocus ></td>\n"
                            + "                            </tr>\n"
                            + "                            <tr>\n"
                            + "                                 <td><label for=\"txtPassword\">Contrase&ntilde;a:</label></td>\n"
                            + "                                 <td><input type=\"password\" id=\"txtPassword\" name=\"txtPassword\" value=\"\" ></td>\n"
                            + "                            </tr>\n"
                            + "                            <tr>\n"
                            + "                                <td></td>\n"
                            + "                                <td aling=\"center\">\n"
                            + "                                    <input type=\"submit\" id=\"btnConfirmar\" name=\"btnConfirmar\" value=\"Aceptar\" >\n"
                            + "                                     <input type=\"submit\" id=\"btnCancelar\" name=\"btnCancelar\" value=\"Cancelar\" >\n"
                            + "                                </td>\n"
                            + "                            </tr>\n"
                            + "                            </table>");
                }

                //*************************ACREDITANDO AUTORIZACION DEL CHEQUE**********************************
                /* Presionamos el boton ingresar cheque tambien se crean un boton de envio de credenciales*/
                if (request.getParameter("btnConfirmar") != null) {
                    //AQUI VALIDAMOS LOS ENVIADOS PARA LA AUTORIZACION DEL CHEQUE
                    String userCode = null;
                    String userPwd = null;
                    int rolCode = 0;
                    String estado = null;
                    MstUsuario usuario = new MstUsuario();
                    TChequeCCDAO chequedao = new TChequeCCDAO();
                    try {
                        userCode = request.getParameter("txtUsuario");
                        userPwd = request.getParameter("txtPassword");
                    } catch (Exception e) {
                        sqlresp2 = e.toString();
                    }
                    usuario.setCodigoUsuario(userCode);
                    usuario.setPassword(userPwd);
                    List<MstUsuario> lstUser = chequedao.autorizarCheque(usuario);
                    for (MstUsuario user : lstUser) {
                        rolCode = user.getCodigoRol();
                        estado = user.getEstado();
                    }

                    if (rolCode == 0) {
                        /*Si el valor retornado es igual a cero quiere decir que no hay usuario con las credenciales recibidas*/
                        request.setAttribute("loginAutorizacion", "<table>\n"
                                + "<tr>\n"
                                + "     <td colspan=\"9\" rowspan=\"2\"></td>\n"
                                + "     <td><label for=\"txtUsuario\">Usuario:</label></td>\n"
                                + "     <td><input type=\"text\" id=\"txtUsuario\" name=\"txtUsuario\" value=\"\" autofocus ></td>\n"
                                + "</tr>\n"
                                + "<tr>\n"
                                + "     <td><label for=\"txtPassword\">Contrase&ntilde;a:</label></td>\n"
                                + "     <td><input type=\"password\" id=\"txtPassword\" name=\"txtPassword\" value=\"\" >\n"
                                + "         <input type=\"submit\" id=\"btnConfirmar\" name=\"btnConfirmar\" value=\"Aceptar\" >\n"
                                + "         <input type=\"submit\" id=\"btnCancelar\" name=\"btnCancelar\" value=\"Cancelar\" >\n"
                                + "      </td>\n"
                                + "</tr>\n"
                                + "</table>");

                        sqlresp2 = "Correo o contraseña incorrectos.";

                    } else if (rolCode == 1) {
                        if (estado.equals("S")) {
                            //Si se autoriza entonces generamos un boton para que pueda agregar el cheque. :)
                            request.setAttribute("buttonAddCheck", "<input type=\"submit\" id=\"btnAgregarCheque\" value=\"Agregar Cheque\" name=\"btnAgregarCheque\" />");

                        } else {
                            sqlresp2 = "Hay problemas con su cuenta, comuniquese con el administrador del sistema.";
                        }
                    } else {
                        sqlresp2 = "No tiene privilegios suficientes.";
                    }

                }
                //****************************CANCELANDO TODA OPERACION**********************************

                if (request.getParameter("btnCancelar") != null) {
                    request.setAttribute("buttonAddCheck", "");//Desaparece el boton generado hasta que de nuevo haya autorizacion

                }
                //***************************** AGREGANDO CHEQUE**************************************
                if (request.getParameter("btnAgregarCheque") != null) {
                    try {
                        
                        TChequeCCDAO chequedao = new TChequeCCDAO();
                        TChequeCC cheque = new TChequeCC();
                        
                        
                        String numeroLiquiCheque = null;
                        Date fechaLiquiCheque = null;
                        int correlativoBanco = 0;
                        String cuentaBanco = null;
                        Date fechaCheque = null;
                        String numeroCheque = null;
                        String nombreCheque = null;
                        double valorCheque = 0;
                        String codigoUsuarioCheque = "Administrador"; //VERIFICAR DE DONDE TOMO ESTE DATO O COMO
                        Date ingresoCheque = Date.valueOf(LocalDate.now()); //Fecha de ingreso autogenerada.
                        
                        /*Filtro los datos de la liquidacion pendiente de cheque*/
                        List<TGastoCC> lstDatos = gastoCCDAO.datosChequeLiquidacionPendiente();
                        for (TGastoCC tc : lstDatos) {
                            numeroLiquiCheque = tc.getNum_liquidacion();
                            fechaLiquiCheque = tc.getFecha_liquidacion();
                        }
                    //Procesamos datos recibidos.
                        correlativoBanco = Integer.valueOf(request.getParameter("sltCuentaBanco"));
                        cuentaBanco = request.getParameter("txtCuentaBanco");
                        fechaCheque = Date.valueOf(fechaIn.amdEntrada(request.getParameter("txtFechaCheque")));
                        numeroCheque = request.getParameter("txtNoCheque");
                        nombreCheque = request.getParameter("txtNombreCheque");
                        valorCheque = Double.valueOf(request.getParameter("txtValorCheque"));
                        codigoUsuarioCheque = "Administrador";//Codigo de usuario

                        /* Preparamos la clase de la tabla cheques */
                        cheque.setNum_liquidacion(numeroLiquiCheque);
                        cheque.setFecha_liquidacion(fechaLiquiCheque);
                        cheque.setCorr_banco(correlativoBanco);
                        cheque.setNum_cta_banco(cuentaBanco);
                        cheque.setFecha_cheque(fechaCheque);
                        cheque.setNum_cheque(numeroCheque);
                        cheque.setNombre_cheque(nombreCheque);
                        cheque.setValor_cheque(valorCheque);
                        cheque.setCod_usuario(codigoUsuarioCheque);
                        cheque.setFecha_ingreso(ingresoCheque);
                        /**/
                        gastocc3.setNum_liquidacion(numeroLiquiCheque);
                        
                        String respuesta = chequedao.create(cheque);
                        
                        if (respuesta != null) {
                            sqlresp2 =  gastoCCDAO.cambiarEstadoLiquidacion(gastocc3);
                            addCheckConfirm = respuesta;
                            
                        } else {
                            addCheckConfirm = "No se agregó el cheque.";
                        }
                        request.setAttribute("buttonAddCheck", "");//Desaparece el boton generado hasta que de nuevo haya autorizacion

                    } catch (Exception e) {
                        if (e instanceof NullPointerException) {
                            sqlresp2 = "Algun dato no es correcto o es nulo: " + e.toString();
                        }
                    }
                }
                //rd = request.getRequestDispatcher("zonaprueba.jsp");

                //*************************ENVIO DE MENSAJE Y REDIRECCINAMIENTO *****************************
                request.setAttribute("sqlresp", sqlresp);
                request.setAttribute("sqlresp2", sqlresp2);
                request.setAttribute("addCheckConfirm", addCheckConfirm);
                rd = request.getRequestDispatcher("cajachica.jsp");
            } catch (Exception e) {
                request.setAttribute("error", e.toString());
                rd = request.getRequestDispatcher("error.jsp");
            }
            rd.forward(request, response);
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
