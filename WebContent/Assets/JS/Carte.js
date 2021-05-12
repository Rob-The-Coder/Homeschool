/*****************************************************/
//FUNZIONE ESEGUITA AL CARICAMENTO DEL DOCUMENTO HTML
//CON SCOPO QUELLO DI ESEGUIRE LE FUNZIONI RIGUARDANTI 
//IL CAMBIO DI TEMA ED IL RESTRINGIMENTO
/*****************************************************/
window.addEventListener("load", function(){
  var h = new Date().getHours()   //Ottiene l'ora corrente
  //Decisione sul tema  
  if(h>9 && h<18){
    Chiaro()
  }else{
    Scuro()
  }//endif
  /*****************************************************/
  //FUNZIONE ESEGUITA AL CLICK DEL PULSANTE DEL DROPDOWN
  //CON IL COMPITO DI CAMBIARE IL TEMA
  /*****************************************************/
  document.getElementById("BottoneTema").addEventListener("click", function(){
    if(localStorage.getItem('tema')==='tema-scuro'){
      Scuro()
    }else{
      Chiaro()
    }//endif
  })
});
/*****************************************************/
//FUNZIONE ESEGUITA PER IMPOSTARE I VALORI NECESSARI
//AL TEMA CHIARO
/*****************************************************/
function Chiaro(){
  //aggiunta bordo nel tema chiaro
  var VetCarte=document.getElementsByClassName("card");
  for(var i=0; i<VetCarte.length; i=i+1){
    VetCarte.item(i).style.border="none";
    VetCarte.item(i).style.boxShadow="0 0 35px 0 rgba(154,161,171,.15)";
  }//endfor
}
/*****************************************************/
//FUNZIONE ESEGUITA PER IMPOSTARE I VALORI NECESSARI
//AL TEMA SCURO
/*****************************************************/
function Scuro(){
  //rimozione bordo nel tema scuro
  var VetCarte=document.getElementsByClassName("card");
  for(var i=0; i<VetCarte.length; i=i+1) {
    VetCarte.item(i).style.border="none";
    VetCarte.item(i).style.boxShadow="none";
  }//endfor
}	