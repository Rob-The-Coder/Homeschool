/*****************************************************/
//               VARIABILI GLOBALI                   //
/*****************************************************/
var BottoneON='<svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-toggle-on" fill="currentColor" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M5 3a5 5 0 0 0 0 10h6a5 5 0 0 0 0-10H5zm6 9a4 4 0 1 0 0-8 4 4 0 0 0 0 8z"/></svg>';
var BottoneOFF='<svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-toggle-off" fill="currentColor" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M11 4a4 4 0 0 1 0 8H8a4.992 4.992 0 0 0 2-4 4.992 4.992 0 0 0-2-4h3zm-6 8a4 4 0 1 1 0-8 4 4 0 0 1 0 8zM0 8a5 5 0 0 0 5 5h6a5 5 0 0 0 0-10H5a5 5 0 0 0-5 5z"/></svg>';
/*****************************************************/
//FUNZIONE ESEGUITA AL CARICAMENTO DEL DOCUMENTO HTML
//CON SCOPO QUELLO DI ESEGUIRE LE FUNZIONI RIGUARDANTI 
//IL CAMBIO DI TEMA ED IL RESTRINGIMENTO
/*****************************************************/
window.onload=function(){
  var h = new Date().getHours()   //Ottiene l'ora corrente
  //Decisione sul tema  
  if(h>9 && h<18){
    TemaChiaro()
  }else{
    TemaScuro()
  }//endif
  //Decisione sul logo
  ScegliLogo()
  CorreggiFooter();
};
/*****************************************************/
//FUNZIONE ESEGUITA AL RIDIMENSIONAMENTO DELLE PAGINA 
//CON SCOPO QUELLO DI DECIDERE SE USARE IL LOGO COMPLETO 
//OPPURE IL LOGO RIDOTTO
/*****************************************************/
window.addEventListener("resize", function(){
  ScegliLogo()
}, true);
/*****************************************************/
//FUNZIONE ESEGUITA AL CLICK DEL PULSANTE DEL DROPDOWN
//CON IL COMPITO DI CAMBIARE IL TEMA
/*****************************************************/
function CambiaTema(){
  if(localStorage.getItem('tema')==='tema-scuro'){
    TemaChiaro()
  }else{
    TemaScuro()
  }//endif
  ScegliLogo()
}
/*****************************************************/
//FUNZIONE ESEGUITA AL CLICK DELLA ROTELLA CON LO SCOPO
//DI AGGIUNGERE LA CLASSE "SHOW" ALL'ELEMENTO ROTELLA
//PER PERMETTE L'ANIMAZIONE
/*****************************************************/
function AggiungiClasse(){
  if(document.getElementById("rotella").classList.contains("show")){
    document.getElementById("rotella").classList.remove("show");
  }else{
    document.getElementById("rotella").classList.add("show");
  }//endif
}
/*****************************************************/
//FUNZIONE ESEGUITA PER IMPOSTARE I VALORI NECESSARI
//AL TEMA CHIARO
/*****************************************************/
function TemaChiaro(){
  //Impostazioni tema chiaro
  localStorage.setItem('tema', 'tema-chiaro')
  document.documentElement.className = 'tema-chiaro'
  document.getElementById("LOGO").setAttribute("src", "../Assets/SVG/LogoChiaro.svg")
  document.getElementById("BottoneTema").innerHTML=BottoneOFF
}
/*****************************************************/
//FUNZIONE ESEGUITA PER IMPOSTARE I VALORI NECESSARI
//AL TEMA SCURO
/*****************************************************/
function TemaScuro(){
  //Impostazioni tema scuro
  localStorage.setItem('tema', 'tema-scuro')
  document.documentElement.className = 'tema-scuro'
  document.getElementById("LOGO").setAttribute("src", "../Assets/SVG/LogoScuro.svg")
  document.getElementById("BottoneTema").innerHTML=BottoneON
}
/*****************************************************/
//FUNZIONE ESEGUITA PER DECIDERE SE IMPOSTARE IL LOGO
//NORMALE O LA VERSIONE MINIMALE
/*****************************************************/
function ScegliLogo(){
  //Decisione sul logo
  if(document.documentElement.clientWidth > 768){
    //Codice eseguito superati i 768px
    if(localStorage.getItem('tema')==='tema-scuro'){
      document.getElementById("LOGO").setAttribute('src', "../Assets/SVG/LogoScuro.svg")
    }else{
      document.getElementById("LOGO").setAttribute('src', "../Assets/SVG/LogoChiaro.svg")
    }//endif
  }else{
    //Codice eseguito sotto i 768px
    document.getElementById("LOGO").setAttribute('src', '../Assets/SVG/LogoMin.svg')
  }//endif
}
/*****************************************************/
//FUNZIONE ESEGUITA PER CORREGGERE LA VISUALIZZAZIONE
//DEL FOOTER SE IL CONTENUTO DELLA PAGINA E' CORTO
/*****************************************************/
function CorreggiFooter(){
  //Assegnazione dinamica di margin-top alla carta
  var offsetHeight=document.getElementsByClassName("footer")[0].getBoundingClientRect().y;
  var mt=window.innerHeight-offsetHeight-document.getElementsByClassName("footer")[0].offsetHeight;
  if(mt>0){
    document.getElementsByClassName("footer")[0].style.marginTop=mt+"px";
  }//endif
}