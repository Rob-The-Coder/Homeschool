var NumUscite=0;
var ms=0;
console.log(MillisecondiQuestionario);
$('#ModalConsenso').modal('toggle');
//funzione per abilitare il fullscreen
function toggleFullScreen(){
  if(!document.fullscreenElement) {
    document.documentElement.requestFullscreen();
  }//endif
  if(NumUscite==0){
    var AggiornaTimer=function(){
      var Bar=document.getElementById("bar");
      var Progress=((ms*100)/MillisecondiQuestionario)*1000;
      Bar.style.width=Progress+"%";
      Bar.setAttribute("aria-valuenow", Progress);
      ms=ms+1;
      if(ms==MillisecondiQuestionario){
        clearInterval(timer);
      }//endif
    } 
    AggiornaTimer();
    window.setTimeout(function() { document.getElementById("formQuestionario").submit(); }, MillisecondiQuestionario);
    var timer=setInterval(AggiornaTimer, 1000)
  }//endif
  //correzione footer
  var offsetHeight=document.getElementsByClassName("footer")[0].getBoundingClientRect().y;
  var mt=1080-offsetHeight-document.getElementsByClassName("footer")[0].offsetHeight;
  if(mt>0){
    document.getElementsByClassName("footer")[0].style.marginTop=mt+"px";
  }//endif
}
//EventListener per sapere se l'utente è uscito dalla modalità a tutto schermo
$(document).on('webkitfullscreenchange mozfullscreenchange fullscreenchange', function(e){
  if(!window.screenTop && !window.screenY){
    if(NumUscite==1){
      document.getElementById("formQuestionario").submit();
    }else{
      $('#ModalAvviso').modal('toggle');
      NumUscite+=1;
    }//endif
  }//endif
});