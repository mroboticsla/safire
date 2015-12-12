/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

/**
 *
 * @author Nieto Mendoza
 */
public class MySQL {
    //Recibe la fecha en DD-MM-YYYY y la convierte a YYYY-MM-DD
    public String amdEntrada(String fechaIn){
        String formatIn = null;
        String dia, mes, anio;
        dia = fechaIn.substring(0, 2);
        mes= fechaIn.substring(3,5);
        anio = fechaIn.substring(6, 10);
        formatIn = anio+"-"+mes+"-"+dia;
        return formatIn;
    }
    
    //Recibe la fecha en YYYY-MM-DD y la convierte a DD-MM-YYYY
    public String dmaSalida(String fechaOut){
        String formatOut = null;
        String dia, mes, anio;
        dia = fechaOut.substring(0, 4);
        mes= fechaOut.substring(5,7);
        anio = fechaOut.substring(8, 10);
        formatOut = anio+"-"+mes+"-"+dia;
        return formatOut;
    }
    //Recibe un vector con numeros enteros y de vuelve el sucesivo del numero mayor.
    public int correlativoBanco (int [] numeros){
                        int mayor = 0;
                        int sucesivo = 0;
                        
                        for(int i=0; i<numeros.length; i++){
                            if (numeros[i]>mayor) {
                                    mayor = numeros[i];
                                }
                        }
                        sucesivo = mayor + 1;
                        
                        return sucesivo;
    }
    
    
}
