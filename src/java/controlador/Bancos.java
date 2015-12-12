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
import modeladoDAO.MstBancoDAO;
import modeladoDAO.MstCuentaBDAO;
import uml.MstBanco;
import uml.MstCuenta;
import utilidades.MySQL;

/**
 *
 * @author Nieto Mendoza
 */
public class Bancos extends HttpServlet {

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
            RequestDispatcher rd = null;
            String sqlresp = null;
            MySQL sql = new MySQL();
            MstBanco banco = new MstBanco();
            MstBancoDAO bancodao = new MstBancoDAO();
            MstCuenta cuenta = new MstCuenta();
            MstCuentaBDAO cuentadao = new MstCuentaBDAO();
            DecimalFormat df = new DecimalFormat("0.00");
            try {
                /*****************************AGREGAR BANCO*********************************************************/
                if (request.getParameter("btnAgregarBanco") != null) {
                    //Variables globales para insertar nuevo banco
                    int codigoResidencial = 1; //COMPROBAR DE DONDE SALE ESTE DATO
                    int correlativoBanco = 0;
                    String nombreBanco = null;
                    Date fechaCreacion = null;
                    String codigoUsuario = "Admin"; //COMPROBAR DE DONDE SALE ESTE DATO
                    String activo = "S"; //COMPROBAR DE DONDE SALE ESTE DATO

                    //Auto incrementamos el valor correlativo del banco respecto al numero mayor registrado.
                    int registros = bancodao.numeroRegistros();
                    int numeros[];
                    numeros = new int[registros];
                    List<MstBanco> lstCorrelativos = bancodao.read();
                    int i = 0;
                    for (MstBanco banco2 : lstCorrelativos) {
                        numeros[i] = banco2.getCorr_banco();
                        i++;
                    }
                    //Capturamos el numero sucesivo del ultimo correlativo y lo asignamos al nuevo registro.
                    correlativoBanco = sql.correlativoBanco(numeros);

                    try {
                        //Recibiendo los campos nombre de banco y fecha de creacion.
                        nombreBanco = request.getParameter("txtNombreBanco");
                        fechaCreacion = Date.valueOf(sql.amdEntrada(request.getParameter("txtFechaCreacion")));
                        //Preparamos la clase MstBanco para la inyeccin de datos.
                        banco.setCod_residencial(codigoResidencial);
                        banco.setCorr_banco(correlativoBanco);
                        banco.setNombre_banco(nombreBanco.trim());
                        banco.setFecha_creacion(fechaCreacion);
                        banco.setCod_usuario(codigoUsuario.trim());
                        banco.setActivo(activo.trim());

                        String existe = bancodao.comprobarBanco(banco);
                        if (existe == null) {
                            sqlresp = bancodao.create(banco);
                        } else {
                            sqlresp = existe;
                        }

                    } catch (Exception e) {
                        if (e instanceof NullPointerException) {
                            sqlresp = "Vefique los datos digitados sean correctos.";
                        }
                    }

                }
                /**
                 * **************************AGREGAR CUENTA BANCARIA********************************
                 */
                if (request.getParameter("btnAgregarCuenta") != null) {
                    //qlresp = "Boton Agregar Cuenta presionado";
                    /*Agregando numeros de cuenta*/
                    int corrBanco = 0;
                    String numCtaBanco = null;
                    String numCtaConta = null;
                    double saldoCta = 0; 
                    Date creacionCta = Date.valueOf(LocalDate.now());
                    String codigoUsuario = "Admin"; //VERIFICAR DE DONDE VIENE ESTE DATO //Bien se puede usar la variable en la linea 59
                    String activo = "S"; //VERIFICAR DE DONDE VIENE ESTE DATO //Bien se puede usar las variable en la linea 60
                    try {
                        /*Recibiendo el correlativo del banco, el numero de cuenta y el numero de cuenta contable*/
                        corrBanco = Integer.parseInt(request.getParameter("rdSelectBank"));
                        numCtaBanco = request.getParameter("txtNoCuenta");
                        numCtaConta = request.getParameter("sltCuentaConta");
                        saldoCta = Double.parseDouble(request.getParameter("sltCuentaConta2"));
                        /* Preparamos la clase MstCuenta */
                        banco.setCorr_banco(corrBanco);
                        cuenta.setCorr_banco(banco);
                        cuenta.setNum_cta_banco(numCtaBanco);
                        cuenta.setCod_cta_conta(numCtaConta);
                        cuenta.setSaldo_cta(saldoCta);
                        cuenta.setFecha_creacion(creacionCta);
                        cuenta.setCod_usuario(codigoUsuario); //Usando la variable de la linea 59
                        cuenta.setActivo(activo); //Usando la variable de la linea 60

                        String existe = cuentadao.comprobarCuenta(cuenta);

                        if (existe == null) {
                            sqlresp = cuentadao.create(cuenta);
                        } else {
                            sqlresp = existe;
                        }
                    } catch (Exception e) {
                        if (e instanceof NullPointerException) {
                            sqlresp = "Verifique que todo este correcto.";
                        }
                    }

                }

                request.setAttribute("sqlresp", sqlresp);
                rd = request.getRequestDispatcher("cajachica_bancos.jsp");
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
