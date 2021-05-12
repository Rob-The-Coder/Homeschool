window.addEventListener("load", function(){
  var h = new Date().getHours()   //Ottiene l'ora corrente
  //Decisione sul tema
  try{
    if(h>9 && h<18){
      document.getElementById("ImmagineAdd").setAttribute("src", "Assets/AddChiaro.svg");
    }else{
      document.getElementById("ImmagineAdd").setAttribute("src", "Assets/AddScuro.svg");
    }//endif
    document.getElementById("BottoneTema").addEventListener("click", function(){
      if(localStorage.getItem('tema')==='tema-scuro'){
        document.getElementById("ImmagineAdd").setAttribute("src", "Assets/AddScuro.svg");
      }else{
        document.getElementById("ImmagineAdd").setAttribute("src", "Assets/AddChiaro.svg");
      }//endif
    });
  }catch(err){}
});