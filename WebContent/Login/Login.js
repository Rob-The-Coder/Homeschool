!function(a){a(function(){a('[data-toggle="password"]').each(function(){var b = a(this); var c = a(this).parent().find(".input-group-text"); c.css("cursor", "pointer").addClass("input-password-hide"); c.on("click", function(){if (c.hasClass("input-password-hide")){c.removeClass("input-password-hide").addClass("input-password-show"); c.find(".fa").removeClass("fa-eye").addClass("fa-eye-slash"); b.attr("type", "text")} else{c.removeClass("input-password-show").addClass("input-password-hide"); c.find(".fa").removeClass("fa-eye-slash").addClass("fa-eye"); b.attr("type", "password")}})})})}(window.jQuery);

window.onload=function(){
  //Assegnazione dinamica di stili alla carta riguardanti il bordo
  document.getElementById("CARD").style.border="none";
  document.getElementById("CARD").style.boxShadow="0 0 35px 0 rgba(154,161,171,.15)";
  //Assegnazione dinamica di margin-top alla carta
  var offsetHeight=document.getElementById("CARD").offsetHeight;
  var mt=(window.innerHeight/2)-(offsetHeight/2);
  document.getElementById("CARD").style.marginTop=mt+"px";
  //Aggiunta e rimozione dinamica di offset alla carta
  if(document.documentElement.clientWidth > 1200){
    document.getElementById("log").classList.add("offset-2");
  }else{
    document.getElementById("log").classList.add("offset-1");
  }//endif
}
window.addEventListener("resize", function(){
  if(document.documentElement.clientWidth > 1200){
    //Eseguito sopra i 1200px
    document.getElementById("log").classList.remove("offset-1");
    document.getElementById("log").classList.add("offset-2");
  }else{
    //Eseguito sotto i 1200px
    document.getElementById("log").classList.remove("offset-2");
    document.getElementById("log").classList.add("offset-1");
  }//endif
}, true);