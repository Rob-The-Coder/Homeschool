window.onload=function(){
  var h = new Date().getHours()   //Ottiene l'ora corrente
  //Decisione sul tema  
  if(h>9 && h<18){
    var VetBtn=document.getElementsByClassName("impostazioni");
    for(var i=0; i<VetBtn.length; i=i+1){
      VetBtn[i].classList.add("btn-outline-dark");
    }//endfor
    TemaChiaro()
  }else{
    var VetBtn=document.getElementsByClassName("impostazioni");
    for(var i=0; i<VetBtn.length; i=i+1){
      VetBtn[i].classList.add("btn-outline-light");
    }//endfor
    document.getElementsByClassName("border-bottom")[0].classList.add("border-dark");
    TemaScuro()
  }//endif
  //Decisione sul logo
  ScegliLogo()
  CorreggiFooter();
};
function CambiaTema2(){
  if(localStorage.getItem('tema')==='tema-scuro'){
    var VetBtn=document.getElementsByClassName("impostazioni");
    for(var i=0; i<VetBtn.length; i=i+1){
      VetBtn[i].classList.remove("btn-outline-light");
      VetBtn[i].classList.add("btn-outline-dark");
    }//endfor
    document.getElementsByClassName("border-bottom")[0].classList.remove("border-dark");
    TemaChiaro()
  }else{
    var VetBtn=document.getElementsByClassName("impostazioni");
    for(var i=0; i<VetBtn.length; i=i+1){
      VetBtn[i].classList.remove("btn-outline-dark");
      VetBtn[i].classList.add("btn-outline-light");
    }//endfor
    document.getElementsByClassName("border-bottom")[0].classList.add("border-dark");
    TemaScuro()
  }//endif
  ScegliLogo()
}
function Immagine(id){document.getElementById(id).click();}
function LoadFile(event, id){
  Img=id.slice(-1);
  document.getElementById(id).innerHTML=`
    <form action="/Homeschool/Impostazioni" method="POST">
      <div class="row py-2 d-flex align-items-center">
        <div class="col d-flex justify-content-start">
          <p class="font-weight-light">L'immagine apparir&#224; cos&#236;: </p>
        </div>
        <div class="col d-flex justify-content-center">
          <img id="img" width="150px" height="150px" style="border-radius: 50%">
        </div>
        <div class="col d-flex justify-content-end">
          <input type="hidden" name="Tipo" value="Immagine">
          <input type="hidden" name="Immagine" id="Immagine">
          <button type="submit" class="btn impostazioni">conferma</button>
        </div>
      </div>
    </form>
  `;
  var output=document.getElementById("img");
  output.src=URL.createObjectURL(event.target.files[0]);
  
  const file = event.target.files[0];
  const reader = new FileReader();
  reader.onloadend = () => {
    document.getElementById("Immagine").value=reader.result;
  };
  reader.readAsDataURL(file);
  
  var h = new Date().getHours()   //Ottiene l'ora corrente
  //Decisione sul tema  
  if(h>9 && h<18){
    var VetBtn=document.getElementsByClassName("impostazioni");
    for(var i=0; i<VetBtn.length; i=i+1){
      VetBtn[i].classList.add("btn-outline-dark");
    }//endfor
  }else{
    var VetBtn=document.getElementsByClassName("impostazioni");
    for(var i=0; i<VetBtn.length; i=i+1){
      VetBtn[i].classList.add("btn-outline-light");
    }//endfor
  }//endif
}