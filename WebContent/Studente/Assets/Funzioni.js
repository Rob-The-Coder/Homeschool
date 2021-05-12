function FiltraVoti(Materia){
  Wrap=document.getElementById("WrapVoti");
  VetVotiMateria=document.getElementsByClassName(Materia);
  VetVotiRestanti=document.getElementsByClassName("voto");
  i=0;
  while(i<VetVotiRestanti.length){
    if(VetVotiRestanti[i].classList[3]===Materia.toString()){
      RimuoviElemento(i);
    }else{
      i=i+1;
    }//endif
  }//endfor
  var App=0;
  var Html=`<div class="row pb-3">`;
  for(var i=0; i<VetVotiMateria.length; i=i+1){
    if(App%4==0){
      Html+=`</div><div class="row pb-3">`;
    }//endif
    Html+=`<div class="col-12 col-xl-3 voto ${Materia}">`;
    Html+=VetVotiMateria[i].innerHTML;
    Html+=`</div>`;
    App=App+1;
  }//endfor
  for(var i=0; i<VetVotiRestanti.length; i=i+1){
    if(App%4==0){
      Html+=`</div><div class="row pb-3">`;
    }//endif
    Html+=`<div class="col-12 col-xl-3 voto ${VetVotiRestanti[i].classList[3]} d-none">`;
    Html+=VetVotiRestanti[i].innerHTML;
    Html+=`</div>`;
    App=App+1;
  }//endfor
  Html+=`</div>`;
  Wrap.innerHTML=Html;
}
function RipristinaVoti(){
  Wrap=document.getElementById("WrapVoti");
  Voti=document.getElementsByClassName("voto");

  var Html=`<div class="row pb-3">`;
  for(var i=0; i<Voti.length; i=i+1){
    if(i%4==0){
      Html+=`</div><div class="row pb-3">`;
    }//endif
    Html+=`<div class="col-12 col-xl-3 voto ${Voti[i].classList[3]}">`;
    Html+=Voti[i].innerHTML;
    Html+=`</div>`;
  }//endfor
  Html+=`</div>`;
  Wrap.innerHTML=Html;
}
function RimuoviElemento(pos){
  var AppVet=new Array();
  var j=0;
  while(j<VetVotiRestanti.length){
    if(j!=pos){
      AppVet.push(VetVotiRestanti[j]);
    }//endif
    j=j+1;
  }//endwhile
  VetVotiRestanti=new Array();
  VetVotiRestanti=AppVet;
}
function addTimes (startTime, endTime) {
  var times = [ 0, 0, 0 ]
  var max = times.length
  var a = (startTime || '').split(':')
  var b = (endTime || '').split(':')
  //normalize time values
  for(var i = 0; i < max; i++){
    a[i] = isNaN(parseInt(a[i])) ? 0 : parseInt(a[i])
    b[i] = isNaN(parseInt(b[i])) ? 0 : parseInt(b[i])
  }//endif
  //store time values
  for (var i = 0; i < max; i++){
    times[i] = a[i] + b[i]
  }//endif
  var hours = times[0]
  var minutes = times[1]
  var seconds = times[2]
  if(seconds >= 60){
    var m = (seconds / 60) << 0
    minutes += m
    seconds -= 60 * m
  }//endif
  if(minutes >= 60){
    var h = (minutes / 60) << 0
    hours += h
    minutes -= 60 * h
  }//endif
  return ('0' + hours).slice(-2) + ':' + ('0' + minutes).slice(-2) + ':' + ('0' + seconds).slice(-2)
}