<%-- 
    Document   : zonaprueba
    Created on : 03-12-2015, 10:14:04 AM
    Author     : Nieto Mendoza
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Zona Prueba</title>
    </head>
    <body>
        <h1>Resultados de algun reenvio</h1>
        <hr/>
        

        <table border="1" rules="all">
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
            <th>Cod. Usuario</th>
            <th>Fecha Ingreso</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>${CodigoResidencial}</td>
            <td>${NoLiquidacion}</td>
            <td>${FechaLiquidacion}</td>
            <td>${CorrelativoTipoGasto}</td>
            <td>${NoReciboFactura}</td>
            <td>${FechaDocto}</td>
            <td>${ValorDocto}</td>
            <td>${CuentaContable}</td>
            <td>${DescripcionDocto}</td>
            <td>${CodigoUsuario}</td>
            <td>${FechaIngreso}</td>
        </tr>
        <tr>
            <th colspan="9" rowspan="9"></th>
            <th>Total:</th>
            <th>${Total}</th>
        <tr/>
        <tr>
            <th >Efectivo en Caja:</th>
            <th>${Efectivo}</th>
        </tr>

    </tbody>
</table>
       Tipo de gasto: <br>
       Efectivo actual: ${EfectivoActual} <br>
       Repuesta de la Base de Datos: ${sqlresp}
</html>
