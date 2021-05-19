function CambiaUtente(){
  var Wrap=document.getElementById("WrapClassi");

  if(document.querySelectorAll("[name^='TipoUtente']")[0].value=="Studente"){
    //Studente
    Wrap.innerHTML=`
      <div class="row py-2">
        <div class="col-4">
          <label class="form-label">Classe</label>
          <select class="form-select" aria-label="Classe" name="Classe">
            <option selected>Scegli la tua classe</option>`+
            ListaClassi()
          +`</select>
        </div>
      </div>
    `;
  }else{
    //Insegnante
    Wrap.innerHTML=`
      <div class="row py-2" id="WrapInterno1">
        <div class="col-3">
          <label class="form-label">Classe</label>
            <select class="form-select" aria-label="Classe" name="Classe1">
              <option selected>Classe</option>`+
              ListaClassi()
            +`</select>
        </div>
        <div class="col-3">
          <label class="form-label">Materia</label>
          <select class="form-select" aria-label="Materia" name="Materia1" onchange="AggiungiClasse(1)">
            <option selected>Materia insegnata</option>`+
            ListaMaterie()
          +`</select>
        </div>
      </div>
    `;
  }//endif
}

function AggiungiClasse(Num){
  //Salvo tutte le select
  var SelectMaterie=[];
  var NumSelect=document.querySelectorAll("[name^='Materia']").length;
  for(var i=1; i<=NumSelect; i=i+1){
    sel=document.getElementsByName("Materia"+i)[0];
    SelectMaterie.push(sel.selectedIndex);
  }//endfor
  //Salvo tutte le select
  var SelectClassi=[];
  var NumSelect=document.querySelectorAll("[name^='Classe']").length;
  for(var i=1; i<=NumSelect; i=i+1){
    sel=document.getElementsByName("Classe"+i)[0];
    SelectClassi.push(sel.selectedIndex);
  }//endfor

  var n=parseInt(Num)+1;

  if(n%2==0){
    //Stessa riga
    var wrap=document.getElementById("WrapInterno"+Num);
    wrap.innerHTML+=`
      <div class="col-3">
        <label class="form-label">Classe</label>
          <select class="form-select" aria-label="Classe" name="Classe${n}">
            <option selected>Classe</option>`+
            ListaClassi()
          +`</select>
      </div>
      <div class="col-3">
        <label class="form-label">Materia</label>
        <select class="form-select" aria-label="Materia" name="Materia${n}" onchange="AggiungiClasse(${n})">
          <option selected>Materia insegnata</option>`+
          ListaMaterie()
        +`</select>
      </div>
      `;
  }else{
    //Riga a capo
    var wrap=document.getElementById("WrapClassi");
    wrap.innerHTML+=`
      <div class="row py-2" id="WrapInterno${n}">
        <div class="col-3">
          <label class="form-label">Classe</label>
            <select class="form-select" aria-label="Classe" name="Classe${n}">
              <option selected>Classe</option>`+
              ListaClassi()
            +`</select>
        </div>
        <div class="col-3">
          <label class="form-label">Materia</label>
          <select class="form-select" aria-label="Materia" name="Materia${n}" onchange="AggiungiClasse(${n})">
            <option selected>Materia insegnata</option>`+
            ListaMaterie()
          +`</select>
        </div>
      </div>
    `;
  }//endif

  //Ripristino tutte le select
  var NumSelect=document.querySelectorAll("[name^='Materia']").length;
  for(var i=1; i<=NumSelect-1; i=i+1){
    sel=document.getElementsByName("Materia"+i)[0];
    sel.selectedIndex=SelectMaterie[i-1];
  }//endfor
  var NumSelect=document.querySelectorAll("[name^='Classe']").length;
  for(var i=1; i<=NumSelect-1; i=i+1){
    sel=document.getElementsByName("Classe"+i)[0];
    sel.selectedIndex=SelectClassi[i-1];
  }//endfor
}

function ListaClassi(){
	var Lista=``;
	
	for(var i=0; i<Classi.length; i=i+1){
		Lista+=`
			<option value="${Classi[i]}">${Classi[i]}</option>
		`;
	}//endfor

  return Lista;
}

function ListaMaterie(){
  var Lista=``;
	
	for(var i=0; i<Materie.length; i=i+1){
		Lista+=`
			<option value="${Materie[i]}">${Materie[i]}</option>
		`;
	}//endfor
	
	return Lista;
}