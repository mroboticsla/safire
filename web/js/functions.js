//FUNCION PARA PASAR A MAYUSCULAS LOS TEXTBOX
function toUpperCaseValue(obj){
    obj.value = obj.value.toUpperCase();
}


//FUNCION PARA VALIDAR CONTROL QUE SOLO ACEPTE NUMEROS
function onlyNumbers(evt)
      {
        var keyPressed = (evt.which) ? evt.which : event.keyCode
        return !(keyPressed > 31 && (keyPressed < 48 || keyPressed > 57 || keyPressed==13 ));
      }

//funcion para deshabilitar textos       
function cleanDisabled(dst) {
    document.getElementById(dst).value="";
    //document.getElementById(dst).disabled=true;
}


function EnableText(dst) {
    document.getElementById(dst).value="";
    document.getElementById(dst).disabled=false;
}

//Envio el enfoque a un elemento
function getfocus(obj){
    document.getElementById(obj).focus()
}