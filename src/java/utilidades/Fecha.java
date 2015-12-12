/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import java.util.Calendar;
import java.util.Date;
/**
 *
 * @author Nieto Mendoza
 */
public class Fecha {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;
    private String dayname;
    private String monthname;
    
    public Fecha() {
        Calendar time = Calendar.getInstance();
        this.year= time.get(Calendar.YEAR);
        this.month = time.get(Calendar.MONTH)+1;
        this.day = time.get(Calendar.DATE);
        this.hour = time.get(Calendar.HOUR);
        this.minute = time.get(Calendar.MINUTE);
        this.second = time.get(Calendar.SECOND);
        
        String[] dias={"Domingo","Lunes","Martes", "Miércoles","Jueves","Viernes","Sábado"};
        String[] meses={"Enero","Febrero","Marzo", "Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
        Date hoy=new Date();
        int numeroDia=0;
        int numMes=0;
        time.setTime(hoy);
        numeroDia=time.get(Calendar.DAY_OF_WEEK);
        numMes=time.get(Calendar.MONTH);
        this.dayname=dias[numeroDia - 1];
        this.monthname=meses[numMes];
    }
    
    public String fecha1(){
        String datetime;
        String fecha = null;
        String hora = null;        
        if  (day <=9 && month <=9) {   
            fecha ="0"+day+"/0"+month+"/"+year;
        }
        else if (day <= 9 && month > 9) {
            fecha = "0"+day+"/"+month+"/"+year;
        }
        else if (day > 9 && month <=9) {
            fecha= day+"/0"+month+"/"+year;
        }
        else if(day > 9 && month > 9){
           fecha = day+"/"+month+"/"+year;
        }
        
        if (hour <=9 && minute > 9 && second > 9) {
            hora = "0"+hour+":"+minute+":"+second;
        }
        else if (hour <=9 && minute <= 9 && second > 9) {
            hora= "0"+hour+":0"+minute+":"+second;
        }
        else if(hour <=9 && minute <=9 && second <= 9) {   
            hora = "0"+hour+":0"+minute+":0"+second;
        }
        else if (hour >9 && minute <=9 && second <= 9) {
            hora = hour+":0"+minute+":0"+second;
        }
        else if (hour >9 && minute >9 && second <= 9) {
            hora = hour+":"+minute+":0"+second;
        }
        else if(hour >9 && minute > 9 && second > 9){
            hora = hour+":"+minute+":"+second;
        }
        datetime = fecha + " " + hora;
        
        return datetime;
    }
    public String fecha2(){
        String datetime;
        String fecha = null;
        String hora = null;
               
        if  (day <=9 && month <=9) {   
            fecha =year+"/0"+month+"/"+"0"+day;
        }
        else if (day <= 9 && month > 9) {
            fecha = year+"/"+month+"/"+"0"+day;
        }
        else if (day > 9 && month <=9) {
            fecha= year+"/0"+month+"/"+day;
        }
        else if(day > 9 && month > 9){
           fecha = year+"/"+month+"/"+day;
        }
        if (hour <=9 && minute > 9 && second > 9) {
            hora = "0"+hour+":"+minute+":"+second;
        }
        else if (hour <=9 && minute <= 9 && second > 9) {
            hora= "0"+hour+":0"+minute+":"+second;
        }
        else if(hour <=9 && minute <=9 && second <= 9) {   
            hora = "0"+hour+":0"+minute+":0"+second;
        }
        else if (hour >9 && minute <=9 && second <= 9) {
            hora = hour+":0"+minute+":0"+second;
        }
        else if (hour >9 && minute >9 && second <= 9) {
            hora = hour+":"+minute+":0"+second;
        }
        else if(hour >9 && minute > 9 && second > 9){
            hora = hour+":"+minute+":"+second;
        }
        datetime = fecha + " " + hora;
        
        return datetime;
    }
    
    public String fecha3(){
        String datetime;
        String fecha = null;
        String hora = null;        
        if  (day <=9 && month <=9) {   
            fecha ="0"+day+"-0"+month+"-"+year;
        }
        else if (day <= 9 && month > 9) {
            fecha = "0"+day+"-"+month+"-"+year;
        }
        else if (day > 9 && month <=9) {
            fecha= day+"-0"+month+"-"+year;
        }
        else if(day > 9 && month > 9){
           fecha = day+"-"+month+"-"+year;
        }
        
        if (hour <=9 && minute > 9 && second > 9) {
            hora = "0"+hour+":"+minute+":"+second;
        }
        else if (hour <=9 && minute <= 9 && second > 9) {
            hora= "0"+hour+":0"+minute+":"+second;
        }
        else if(hour <=9 && minute <=9 && second <= 9) {   
            hora = "0"+hour+":0"+minute+":0"+second;
        }
        else if (hour >9 && minute <=9 && second <= 9) {
            hora = hour+":0"+minute+":0"+second;
        }
        else if (hour >9 && minute >9 && second <= 9) {
            hora = hour+":"+minute+":0"+second;
        }
        else if(hour >9 && minute > 9 && second > 9){
            hora = hour+":"+minute+":"+second;
        }
        //datetime = fecha + " " + hora;
        
        return fecha;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public String getDayname() {
        return dayname;
    }

    public String getMonthname() {
        return monthname;
    }
    
}
