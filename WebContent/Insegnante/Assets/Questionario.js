var Testo=false;

function AggiungiRisposta(id){
  wrap=document.getElementById(id); 
  idDomanda=id.slice(-1);
  numero=parseInt(wrap.getElementsByClassName("risposta").length)+1;
  idRisposta="Risposta"+id.slice(-1)+"."+numero;

  //Salvo tutte le risposte inserite precedentemente
  Risposte=[];
  select=document.getElementById("Menu"+idDomanda);
  NumRisposte=wrap.querySelectorAll("[type^='text']").length;
  VetRisposte=wrap.querySelectorAll("[type^='text']");
  for(var i=0; i<NumRisposte; i=i+1){
    Risposte.push(VetRisposte[i].value);
  }//endfor
  //Salvo lo stato di tutte le risposte
  var CheckedRadio=[];
  var NumRisposte=wrap.querySelectorAll("[type^='radio']").length;
  var VetRisposte=wrap.querySelectorAll("[type^='radio']");
  for(var j=0; j<NumRisposte; j=j+1){
    if(VetRisposte[j].checked){
      CheckedRadio.push(true);
    }else{
      CheckedRadio.push(false);
    }//endif
  }//endfor
  var CheckedCheckbox=[];
  var NumRisposte=wrap.querySelectorAll("[type^='checkbox']").length;
  var VetRisposte=wrap.querySelectorAll("[type^='checkbox']");
  for(var j=0; j<NumRisposte; j=j+1){
    if(VetRisposte[j].checked){
      CheckedCheckbox.push(true);
    }else{
      CheckedCheckbox.push(false);
    }//endif
  }//endfor

  AppHTML=`
  <div class="row py-1 px-3 align-items-center risposta" id=${idRisposta}>
    <div class="input-group">
      <div class="input-group-prepend">
        <div class="input-group-text">`;
        if(select.options[select.selectedIndex].value=="Radio"){
          AppHTML+=`<input type="radio" class="TipoRisposta" id=${idRisposta} name=${idDomanda} value="${numero}"></input>`;
        }else{
          AppHTML+=`<input type="checkbox" class="TipoRisposta" id=${idRisposta} name=${idRisposta} value="${numero}"></input>`;
        }//endif
        AppHTML+=`
        </div>
      </div>
      <input type="text" name=${idRisposta} placeholder="Risposta...">
      <div class="input-group-append">
        <button type="button" class="btn btn-link pb-0" onclick="RimuoviRisposta('${idRisposta}')">
          <span style="font-size: 1.5rem"><i class="fa fa-times"></i></span>
        </button>
      </div>
    </div>
  </div>
  `;

  wrap.innerHTML+=AppHTML;

  //Ripristino tutte le risposte
  VetRisposte=wrap.querySelectorAll("[type^='text']");
  for(var i=0; i<VetRisposte.length-1; i=i+1){
    VetRisposte[i].value=Risposte[i];
  }//endfor
  //Ripristino lo stato di tutte le risposte
  var NumRisposte=wrap.querySelectorAll("[type^='radio']").length;
  var VetRisposte=wrap.querySelectorAll("[type^='radio']");
  for(var j=0; j<NumRisposte; j=j+1){
    if(CheckedRadio[j]==true){
      VetRisposte[j].checked=true;
    }else{
      VetRisposte[j].checked=false;
    }//endif
  }//endfor
  var NumRisposte=wrap.querySelectorAll("[type^='checkbox']").length;
  var VetRisposte=wrap.querySelectorAll("[type^='checkbox']");
  for(var j=0; j<NumRisposte; j=j+1){
    if(CheckedCheckbox[j]==true){
      VetRisposte[j].checked=true;
    }else{
      VetRisposte[j].checked=false;
    }//endif
  }//endfor
  Converti()
}
function RimuoviRisposta(id){
  document.getElementById(id).remove();
  idDomanda=id.toString().split(".");
  idDomanda[0]=idDomanda[0].slice(-1);
  select=document.getElementById("Menu"+idDomanda[0]);

  var VetRisposte=document.getElementById("RisposteWrap"+idDomanda[0]).getElementsByClassName("risposta");
  for(var i=0; i<VetRisposte.length; i=i+1){
    numero=parseInt(i)+1;
    idRisposta=idDomanda[0]+"."+numero;
    VetRisposte[i].setAttribute("id", "Risposta"+idRisposta);
    
    if(select.options[select.selectedIndex].value=="Radio"){
      radio=VetRisposte[i].querySelectorAll('input[type=radio]');
      radio[0].setAttribute("id", "Risposta"+idRisposta);
      radio[0].setAttribute("name", idDomanda[0]);
      radio[0].setAttribute("value", idRisposta.slice(-1));
    }else if(select.options[select.selectedIndex].value=="Checkbox"){
      checkbox=VetRisposte[i].querySelectorAll('input[type=checkbox]');
      checkbox[0].setAttribute("id", "Risposta"+idRisposta);
      checkbox[0].setAttribute("name", "Risposta"+idRisposta);
      checkbox[0].setAttribute("value", idRisposta.slice(-1));
    }//endif

    text=VetRisposte[i].querySelectorAll('input[type=text]');
    text[0].setAttribute("name", "Risposta"+idRisposta);

    btn=VetRisposte[i].getElementsByClassName("btn btn-link pb-0");
    btn[0].setAttribute("onclick", "RimuoviRisposta('Risposta"+idRisposta+"')");
  }//endfor
  Converti()
}
function AggiungiDomanda(){

  //Salvo tutte le domande inserite precedentemente
  var Domande=[];
  var NumDomande=document.querySelectorAll("[id^='Domanda']").length/2;
  for(var i=1; i<=NumDomande; i=i+1){
    Domande.push(document.getElementsByName("Domanda"+i)[0].value);
  }//endfor
  //Salvo tutte le select
  var Select=[];
  var NumSelect=document.querySelectorAll("[id^='Menu']").length;
  for(var i=1; i<=NumSelect; i=i+1){
    sel=document.getElementsByName("Menu"+i)[0];
    Select.push(sel.selectedIndex);
  }//endfor
  //Salvo tutti i punteggi
  var Punteggi=[];
  var NumPunteggi=document.querySelectorAll("[id^='Punteggio']").length/3;
  for(var i=1; i<=NumPunteggi; i=i+1){
    Punteggi.push(document.getElementById("PunteggioGiusto"+i).value);
    Punteggi.push(document.getElementById("PunteggioSbagliato"+i).value);
    Punteggi.push(document.getElementById("PunteggioVuoto"+i).value);
  }//endfor
  //Salvo tutte le risposte inserite precedentemente
  var Risposte=[];
  var NumRisposteWrap=document.querySelectorAll("[id^='RisposteWrap']").length;
  for(var i=0; i<NumRisposteWrap; i=i+1){
    var RisposteWrap=document.getElementById("RisposteWrap"+(i+1));
    var NumRisposte=RisposteWrap.querySelectorAll("[type^='text']").length;
    var VetRisposte=RisposteWrap.querySelectorAll("[type^='text']");
    Risposte.push([]);
    for(var j=0; j<NumRisposte; j=j+1){
      Risposte[i].push(VetRisposte[j].value);
    }//endfor
  }//endfor
  //Salvo lo stato di tutte le risposte
  var CheckedRadio=[];
  var NumRisposteWrap=document.querySelectorAll("[id^='RisposteWrap']").length;
  for(var i=0; i<NumRisposteWrap; i=i+1){
    var RisposteWrap=document.getElementById("RisposteWrap"+(i+1));
    var NumRisposte=RisposteWrap.querySelectorAll("[type^='radio']").length;
    var VetRisposte=RisposteWrap.querySelectorAll("[type^='radio']");
    CheckedRadio.push([]);
    for(var j=0; j<NumRisposte; j=j+1){
      if(VetRisposte[j].checked){
        CheckedRadio[i].push(true);
      }else{
        CheckedRadio[i].push(false);
      }//endif
    }//endfor
  }//endfor
  var CheckedCheckbox=[];
  var NumRisposteWrap=document.querySelectorAll("[id^='RisposteWrap']").length;
  for(var i=0; i<NumRisposteWrap; i=i+1){
    var RisposteWrap=document.getElementById("RisposteWrap"+(i+1));
    var NumRisposte=RisposteWrap.querySelectorAll("[type^='checkbox']").length;
    var VetRisposte=RisposteWrap.querySelectorAll("[type^='checkbox']");
    CheckedCheckbox.push([]);
    for(var j=0; j<NumRisposte; j=j+1){
      if(VetRisposte[j].checked){
        CheckedCheckbox[i].push(true);
      }else{
        CheckedCheckbox[i].push(false);
      }//endif
    }//endfor
  }//endfor

  wrap=document.getElementById("DomandeWrap");
  numero=parseInt(document.getElementsByClassName("domanda").length)+1;
  idDomanda="Domanda"+numero;

  wrap.innerHTML+=`
  <div class="card mb-3 domanda" id=${idDomanda}>
    <div class="card-body py-2">
      <!-- DESCRIZIONE DOMANDA E MENU -->
      <div class="row py-1 align-items-center">
        <div class="col-12 col-xl-6">
          <input type="text" name=${idDomanda} class="TestoDomanda" placeholder="Domanda..." id=${idDomanda}>
        </div>
        <div class="col-1 col-xl-1">
          <input type="file" accept="image/*" onchange="LoadFile(event, 'ImgWrap${numero}')" style="display: none!important;" id="InputImg${numero}" name="InputImg${numero}" />
          <button type="button" class="btn btn-link" onclick="AggiungiImmagine('InputImg${numero}')">
            <span style="font-size: 1.5rem"><i class="fa fa-image"></i></span>
          </button>
          <input type="hidden" name="ImmagineDomanda${numero}">
        </div>
        <div class="col-11 col-xl-5">
          <select id="Menu${numero}" name="Menu${numero}" class="custom-select" onchange="CambiaTipo(this)">
            <option value="Radio">&#xf192; Scelta multipla</option>
            <option value="Checkbox">&#xf14a; Caselle di controllo</option>
            <option value="Textarea">&#xf11c; Testo</option>
          </select>
        </div>
      </div>
      <div class="row py-1 align-items-center">
        <div class="col-2">
          <label>risposta giusta</label>
        </div>
        <div class="col-2">
          <input type="number" class="form-control" id="PunteggioGiusto${numero}" name="PunteggioGiusto${numero}" placeholder="punti...">
        </div>
        <div class="col-2">
          <label>risposta errata</label>
        </div>
        <div class="col-2">
          <input type="number" class="form-control" id="PunteggioSbagliato${numero}" name="PunteggioSbagliato${numero}" placeholder="punti...">
        </div>
        <div class="col-2">
          <label>risposta vuota</label>
        </div>
        <div class="col-2">
          <input type="number" class="form-control" id="PunteggioVuoto${numero}" name="PunteggioVuoto${numero}" placeholder="punti...">
        </div>
      </div>
      <div id="ImgWrap${numero}"></div>
      <!-- INIZIO RISPOSTE -->
      <div id="RisposteWrap${numero}">
        <div class="row py-1 px-3 align-items-center risposta" id="Risposta${numero}.1">
          <div class="input-group">
            <div class="input-group-prepend">
              <div class="input-group-text">
                <input type="radio" class="TipoRisposta" id="Risposta${numero}.1" name="${numero}" value="1">
              </div>
            </div>
            <input type="text" name="Risposta${numero}.1" placeholder="Risposta...">
            <div class="input-group-append">
              <button type="button" class="btn btn-link pb-0" onclick="RimuoviRisposta('Risposta${numero}.1')">
                <span style="font-size: 1.5rem"><i class="fa fa-times"></i></span>
              </button>
            </div>
          </div>
        </div>
      </div>
      <button type="button" id="Aggiungi${numero}" class="btn btn-link my-2" onclick="AggiungiRisposta('RisposteWrap${numero}')">Aggiungi risposta</button>
    </div>
    <div class="card-footer py-2">
      <div class="row d-flex justify-content-end">
        <button type="button" class="btn btn-link" onclick="RimuoviDomanda('${idDomanda}')">
          <span style="font-size: 1.5rem"><i class="fa fa-trash"></i></span>
        </button>
      </div>
    </div>
  </div>
  `;

  //Ripristino tutte le domande
  var NumDomande=document.querySelectorAll("[id^='Domanda']").length/2;
  for(var i=1; i<NumDomande; i=i+1){
    document.getElementsByName("Domanda"+i)[0].value=Domande[i-1];
  }//endfor
  //Ripristino tutte le select
  var NumSelect=document.querySelectorAll("[id^='Menu']").length;
  for(var i=1; i<=NumSelect-1; i=i+1){
    sel=document.getElementsByName("Menu"+i)[0];
    sel.selectedIndex=Select[i-1];
  }//endfor
  //Ripristino tutti i punteggi
  for(var i=3; i<=Punteggi.length; i=i+3){
    document.getElementById("PunteggioGiusto"+(i/3)).value=Punteggi[i-3];
    document.getElementById("PunteggioSbagliato"+(i/3)).value=Punteggi[i-2];
    document.getElementById("PunteggioVuoto"+(i/3)).value=Punteggi[i-1];
  }//endfor
  //Ripristino tutte le risposte
  for(var i=0; i<NumRisposteWrap; i=i+1){
    var RisposteWrap=document.getElementById("RisposteWrap"+(i+1));
    var NumRisposte=RisposteWrap.querySelectorAll("[type^='text']").length;
    var VetRisposte=RisposteWrap.querySelectorAll("[type^='text']");
    for(var j=0; j<NumRisposte; j=j+1){
      VetRisposte[j].value=Risposte[i][j];
    }//endfor
  }//endfor
  //Ripristino lo stato di tutte le risposte
  for(var i=0; i<NumRisposteWrap; i=i+1){
    var RisposteWrap=document.getElementById("RisposteWrap"+(i+1));
    var NumRisposte=RisposteWrap.querySelectorAll("[type^='radio']").length;
    var VetRisposte=RisposteWrap.querySelectorAll("[type^='radio']");
    for(var j=0; j<NumRisposte; j=j+1){
      if(CheckedRadio[i][j]==true){
        VetRisposte[j].checked=true;
      }else{
        VetRisposte[j].checked=false;
      }//endif
    }//endfor
  }//endfor
  for(var i=0; i<NumRisposteWrap; i=i+1){
    var RisposteWrap=document.getElementById("RisposteWrap"+(i+1));
    var NumRisposte=RisposteWrap.querySelectorAll("[type^='checkbox']").length;
    var VetRisposte=RisposteWrap.querySelectorAll("[type^='checkbox']");
    for(var j=0; j<NumRisposte; j=j+1){
      if(CheckedCheckbox[i][j]==true){
        VetRisposte[j].checked=true;
      }else{
        VetRisposte[j].checked=false;
      }//endif
    }//endfor
  }//endfor

  if(localStorage.getItem('tema')==='tema-scuro'){
    Scuro()
  }else{
    Chiaro()
  }//endif
  Converti()
  document.getElementsByClassName("footer")[0].style.marginTop="0px";
  CorreggiFooter()
}
function RimuoviDomanda(id){
  document.getElementById(id).remove();

  var VetDomande=document.getElementById("DomandeWrap").getElementsByClassName("domanda");
  for(var i=0; i<VetDomande.length; i=i+1){
    numero=parseInt(i)+1;
    idDomanda="Domanda"+numero;
    VetDomande[i].setAttribute("id", idDomanda);
    
    input=VetDomande[i].getElementsByClassName("TestoDomanda")[0];
    input.setAttribute("id", idDomanda);
    input.setAttribute("name", idDomanda);

    inputimg=VetDomande[i].querySelectorAll("[id^='InputImg']")[0];
    inputimg.setAttribute("id", "InputImg"+numero);
    inputimg.setAttribute("onchange", "LoadFile(event, 'ImgWrap"+numero+"')");

    BtnImg=VetDomande[i].getElementsByClassName("btn btn-link");
    BtnImg[0].setAttribute("onclick", "AggiungiImmagine('InputImg"+numero+"')");

    URLimg=VetDomande[i].querySelectorAll("[name^='ImmagineDomanda']")[0];
    URLimg.setAttribute("name", "ImmagineDomanda"+numero);

    select=VetDomande[i].querySelectorAll("[id^='Menu']")[0];
    select.setAttribute("id", "Menu"+numero);
    select.setAttribute("name", "Menu"+numero);

    Punteggi=VetDomande[i].querySelectorAll("[id^='Punteggio']");
    Punteggi[0].setAttribute("id", "PunteggioGiusto"+numero);
    Punteggi[0].setAttribute("name", "PunteggioGiusto"+numero);
    Punteggi[1].setAttribute("id", "PunteggioSbagliato"+numero);
    Punteggi[1].setAttribute("name", "PunteggioSbagliato"+numero);
    Punteggi[2].setAttribute("id", "PunteggioVuoto"+numero);
    Punteggi[2].setAttribute("name", "PunteggioVuoto"+numero);

    Iwrap=VetDomande[i].querySelectorAll("[id^='ImgWrap']")[0];
    Iwrap.setAttribute("id", "ImgWrap"+numero);

    RisposteWrap=VetDomande[i].querySelectorAll("[id^='RisposteWrap']")[0];
    RisposteWrap.setAttribute("id", "RisposteWrap"+numero);

    var VetRisposte=RisposteWrap.getElementsByClassName("risposta");
    for(var j=0; j<VetRisposte.length; j=j+1){
      index=parseInt(j)+1;
      idRisposta=numero+"."+index;
      VetRisposte[j].setAttribute("id", "Risposta"+idRisposta);
      
      if(select.options[select.selectedIndex].value=="Radio"){
        radio=VetRisposte[j].querySelectorAll('input[type=radio]');
        radio[0].setAttribute("id", "Risposta"+idRisposta);
        radio[0].setAttribute("name", numero);
        radio[0].setAttribute("value", idRisposta.slice(-1));
      }else if(select.options[select.selectedIndex].value=="Checkbox"){
        checkbox=VetRisposte[j].querySelectorAll('input[type=checkbox]');
        checkbox[0].setAttribute("id", "Risposta"+idRisposta);
        checkbox[0].setAttribute("name", "Risposta"+idRisposta);
        checkbox[0].setAttribute("value", idRisposta.slice(-1));
      }//endif

      text=VetRisposte[j].querySelectorAll('input[type=text]');
      text[0].setAttribute("name", "Risposta"+idRisposta);

      var TipoRisposta=select.options[select.selectedIndex].value;
      if(TipoRisposta=="Radio" || TipoRisposta=="Checkbox"){
        btn=VetRisposte[j].getElementsByClassName("btn btn-link pb-0");
        btn[0].setAttribute("onclick", "RimuoviRisposta('Risposta"+idRisposta+"')");
      }//endif
      
    }//endfor

    btnAggiungi=VetDomande[i].querySelectorAll("[id^='Aggiungi']")[0];
    btnAggiungi.setAttribute("onclick", "AggiungiRisposta('RisposteWrap"+numero+"')");
    btnAggiungi.setAttribute("id", "Aggiungi"+numero);

    btnRimuovi=VetDomande[i].getElementsByClassName("card-footer")[0].getElementsByClassName("btn btn-link")[0];
    btnRimuovi.setAttribute("onclick", "RimuoviDomanda('"+idDomanda+"')");

  }//endfor
  Converti()
  document.getElementsByClassName("footer")[0].style.marginTop="0px";
  CorreggiFooter()
}
function AggiungiImmagine(id){document.getElementById(id).click();}
function LoadFile(event, id){
  Img=id.slice(-1);
  document.getElementById(id).innerHTML=`
    <div class="row py-1 d-flex justify-content-center">
      <img id=${Img}  width="50%">
    </div>
  `;
  var output=document.getElementById(Img);
  output.src=URL.createObjectURL(event.target.files[0]);
  
  const file = event.target.files[0];
  const reader = new FileReader();
  reader.onloadend = () => {
    document.querySelectorAll("[name^='ImmagineDomanda"+Img+"']")[0].value=reader.result;
  };
  reader.readAsDataURL(file);

  Converti()
}
function CambiaTipo(select){
  var idDomanda=select.id.slice(-1);
  var RisposteWrap=document.getElementById("RisposteWrap"+idDomanda);
  var input=RisposteWrap.getElementsByClassName("TipoRisposta");
  if(select.options[select.selectedIndex].value=="Radio"){
    if(Testo){
      document.getElementById("PunteggioVuoto"+idDomanda).disabled=false;
      document.getElementById("PunteggioVuoto"+idDomanda).value="punti...";
      RisposteWrap.innerHTML=`
      <div class="row py-1 px-3 align-items-center risposta" id="Risposta${idDomanda}.1">
        <div class="input-group">
          <div class="input-group-prepend">
            <div class="input-group-text">
              <input type="radio" class="TipoRisposta" id="Risposta${idDomanda}.1" name="1" value="1">
            </div>
          </div>
          <input type="text" name="Risposta${idDomanda}.1" placeholder="Risposta...">
          <div class="input-group-append">
            <button type="button" class="btn btn-link pb-0" onclick="RimuoviRisposta('Risposta${idDomanda}.1')">
              <span style="font-size: 1.5rem"><i class="fa fa-times"></i></span>
            </button>
          </div>
        </div>
      </div>
      `;
    }else{
      for(var i=0; i<input.length; i=i+1){
        input[i].setAttribute("type", "radio")
        input[i].setAttribute("name", idDomanda);
      }//endfor
    }//endif
    document.getElementById("Aggiungi"+idDomanda).hidden=false;
    Testo=false;
  }else if(select.options[select.selectedIndex].value=="Checkbox"){
    if(Testo){
      document.getElementById("PunteggioVuoto"+idDomanda).disabled=false;
      document.getElementById("PunteggioVuoto"+idDomanda).value="punti...";
      RisposteWrap.innerHTML=`
      <div class="row py-1 px-3 align-items-center risposta" id="Risposta${idDomanda}.1">
        <div class="input-group">
          <div class="input-group-prepend">
            <div class="input-group-text">
              <input type="checkbox" class="TipoRisposta" id="Risposta${idDomanda}.1" name="Risposta${idDomanda}.1" value="1">
            </div>
          </div>
          <input type="text" name="Risposta${idDomanda}.1" placeholder="Risposta...">
          <div class="input-group-append">
            <button type="button" class="btn btn-link pb-0" onclick="RimuoviRisposta('Risposta${idDomanda}.1')">
              <span style="font-size: 1.5rem"><i class="fa fa-times"></i></span>
            </button>
          </div>
        </div>
      </div>
      `;
    }else{
      for(var i=0; i<input.length; i=i+1){
        input[i].setAttribute("type", "checkbox")
        input[i].setAttribute("name", input[i].id);
      }//endfor
    }//endif
    document.getElementById("Aggiungi"+idDomanda).hidden=false;
    Testo=false;
  }else{
    document.getElementById("PunteggioVuoto"+idDomanda).value=0;
    document.getElementById("PunteggioVuoto"+idDomanda).disabled=true;
    RisposteWrap.innerHTML=`
    <div class="row py-1 px-3 align-items-center risposta" id="Risposta${idDomanda}.1">
      <div class="col-7">
        <div class="input-group">
          <div class="input-group-prepend">
            <div class="input-group-text">
              <input type="hidden" class="TipoRisposta" id="Risposta${idDomanda}.1" name="1" value="1">
            </div>
          </div>
          <input type="text" name="Risposta${idDomanda}.1" placeholder="Risposta...">
        </div>
      </div>
    </div>
    `;
    document.getElementById("Aggiungi"+idDomanda).hidden=true;
    Testo=true;
  }//endif
  Converti()
}
function Converti(){
  var node = document.getElementById("capture");
  domtoimage.toPng(node)
  .then(function (dataUrl) {
    document.getElementById("ImgQ").value=dataUrl;
  })
  .catch(function (error) {
    console.error('oops, something went wrong!', error);
  });
}