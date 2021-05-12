<%@ page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
Boolean Login;
if(request.getSession().getAttribute("Login") != null){
  Login=(Boolean) request.getSession().getAttribute("Login");
  if(!Login){
  	response.sendRedirect("../Login/Login.jsp");
  }//endif
}else{
  response.sendRedirect("../Login/Login.jsp");
}//endif
String TipoUtente="";
if (request.getSession().getAttribute("TipoUtente") != null) {
  TipoUtente=(String)request.getSession().getAttribute("TipoUtente");
} //endif
ArrayList<String> InfoUtente=new ArrayList<String>();
if (request.getSession().getAttribute("InfoUtente") != null) {
  InfoUtente=(ArrayList<String>) request.getSession().getAttribute("InfoUtente");
} //endif
Boolean ErroreVecchiaPassword=false;
if(request.getSession().getAttribute("ErroreVecchiaPassword") != null){
  ErroreVecchiaPassword=(Boolean) request.getSession().getAttribute("ErroreVecchiaPassword");
}//endif
Boolean ErroreNuovaPassword=false;
if(request.getSession().getAttribute("ErroreNuovaPassword") != null){
  ErroreNuovaPassword=(Boolean) request.getSession().getAttribute("ErroreNuovaPassword");
}//endif
%>
<!doctype html>
<html>
  <head>
    <title>Impostazioni</title>
    <meta charset="ISO-8859-1">
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <!-- Dashboard CSS -->
    <link rel="stylesheet" href="../Assets/CSS/StileBase.css" type="text/css">
    <link rel="stylesheet" href="../Assets/CSS/Animazioni.css" type="text/css">
    <!-- Stile studenti -->
    <link rel="stylesheet" href="Assets/StileImpostazioni.css" type="text/css">
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" />
  </head>
  <body>
    <!-- Dashboard JS -->
    <script type="text/javascript" src="../Assets/JS/FunzioniBase.js"></script>
    <script type="text/javascript" src="../Assets/JS/Carte.js"></script>
    <!-- Impostazioni JS -->
    <script type="text/javascript" src="Assets/FunzioniImpostazioni.js"></script>
    <!-- NAVBAR -->
    <nav class="navbar" id="navbar">
      <!-- LOGO HOMESCHOOL -->
      <% if(TipoUtente.equals("Studente")){ %>
        <a class="navbar-brand" href="../Studente/Dashboard.jsp"><img id="LOGO"></a>
      <% }else{ %>
        <a class="navbar-brand" href="../Insegnante/DashboardClassi.jsp"><img id="LOGO"></a>
      <% }//endif %>
      <!-- COMPONENTI NAVBAR -->
      <ul class="nav align-items-center">
        <li class="nav-item">
          <div class="dropdown">
            <button class="btn dropdown" type="button" data-toggle="dropdown" aria-expanded="false" id="DropdownButton" onclick="AggiungiClasse()">
              <svg id="rotella" xmlns="http://www.w3.org/2000/svg" width="17" height="17" fill="currentColor" class="bi bi-gear" viewBox="0 0 17 17"><path d="M8 4.754a3.246 3.246 0 1 0 0 6.492 3.246 3.246 0 0 0 0-6.492zM5.754 8a2.246 2.246 0 1 1 4.492 0 2.246 2.246 0 0 1-4.492 0z" /><path d="M9.796 1.343c-.527-1.79-3.065-1.79-3.592 0l-.094.319a.873.873 0 0 1-1.255.52l-.292-.16c-1.64-.892-3.433.902-2.54 2.541l.159.292a.873.873 0 0 1-.52 1.255l-.319.094c-1.79.527-1.79 3.065 0 3.592l.319.094a.873.873 0 0 1 .52 1.255l-.16.292c-.892 1.64.901 3.434 2.541 2.54l.292-.159a.873.873 0 0 1 1.255.52l.094.319c.527 1.79 3.065 1.79 3.592 0l.094-.319a.873.873 0 0 1 1.255-.52l.292.16c1.64.893 3.434-.902 2.54-2.541l-.159-.292a.873.873 0 0 1 .52-1.255l.319-.094c1.79-.527 1.79-3.065 0-3.592l-.319-.094a.873.873 0 0 1-.52-1.255l.16-.292c.893-1.64-.902-3.433-2.541-2.54l-.292.159a.873.873 0 0 1-1.255-.52l-.094-.319zm-2.633.283c.246-.835 1.428-.835 1.674 0l.094.319a1.873 1.873 0 0 0 2.693 1.115l.291-.16c.764-.415 1.6.42 1.184 1.185l-.159.292a1.873 1.873 0 0 0 1.116 2.692l.318.094c.835.246.835 1.428 0 1.674l-.319.094a1.873 1.873 0 0 0-1.115 2.693l.16.291c.415.764-.42 1.6-1.185 1.184l-.291-.159a1.873 1.873 0 0 0-2.693 1.116l-.094.318c-.246.835-1.428.835-1.674 0l-.094-.319a1.873 1.873 0 0 0-2.692-1.115l-.292.16c-.764.415-1.6-.42-1.184-1.185l.159-.291A1.873 1.873 0 0 0 1.945 8.93l-.319-.094c-.835-.246-.835-1.428 0-1.674l.319-.094A1.873 1.873 0 0 0 3.06 4.377l-.16-.292c-.415-.764.42-1.6 1.185-1.184l.292.159a1.873 1.873 0 0 0 2.692-1.115l.094-.319z" /></svg>
            </button>
            <!-- CONTENUTO DROPDOWN -->
            <div class="dropdown-menu" id="DropdownImpostazioni">
              <div class="row no-gutters">
                <p class="dropdown-header">Cambia tema</p>
              </div>
              <div class="dropdown-divider"></div>
              <div class="row no-gutters justify-content-center">
                <div class="col-5 d-flex justify-content-start align-self-center">
                  <p class="NavbarText">Tema chiaro</p>
                </div>
                <div class="col-2 d-flex justify-content-center align-self-center">
                  <button class="btn" id="BottoneTema" onclick=CambiaTema2()></button>
                </div>
                <div class="col-5 d-flex justify-content-end align-self-center">
                  <p class="NavbarText">Tema scuro</p>
                </div>
              </div>
            </div>
            <!-- FINE CONTENUTO DROPDOWN -->
          </div>
        </li>
        <li class="nav-item">
          <div class="dropdown">
            <button type="button" class="btn dropdown" id="BottoneUtente" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              <div class="row no-gutters">
                <div class="col d-flex align-self-center">
                  <p class="avatar-text"><%= InfoUtente.get(1).split("\\s+")[0] %></p>
                </div>
                <div class="col d-flex align-self-center justify-content-center">
                  <img src=<%= InfoUtente.get(0) %> class="avatar">
                </div>
              </div>
            </button>
            <!-- INIZIO CONTENUTO DROPDOWN -->
            <div class="dropdown-menu" id="DropdownUtente">
              <button class="dropdown-item" type="button" onclick="location.href='../Impostazioni/Impostazioni.jsp'">
                <p class="NavbarText"><i class="fa fa-cog" aria-hidden="true"></i> Impostazioni</p>
              </button>
              <form action="/Homeschool/Disconnessione" method="POST">
                <button class="dropdown-item" type="submit">
                  <p class="NavbarText"><i class="fa fa-sign-out" aria-hidden="true"></i> Disconnettiti</p>
                </button>
              </form>
            </div>
            <!-- FINE CONTENUTO DROPDOWN -->
          </div>
        </li>
      </ul>
      <!-- FINE COMPONENTI NAVBAR -->
    </nav>
    <!-- FINE NAVBAR -->
    <!-- CONTENUTO PAGINA -->
    <div class="container-fluid ContenutoPagina-Q">
      <div class="row mb-3">
        <div class="col">
          <p class="font-weight-bold h5"><i class="fa fa-cog" aria-hidden="true"></i>   Impostazioni Utente</p>
        </div>
      </div>
      <div class="row mb-3">
        <div class="col">
          <p class="TestoNormale font-weight-light border-bottom pl-1">preferenze</p>
        </div>
      </div>
      <div class="row d-flex align-items-center">
        <div class="col">
          <div class="row mt-3">
            <div class="col">
              <p class="font-weight-bold">Cambia password</p>
            </div>
          </div>
          <div class="row mb-3">
            <div class="col">
              <p class="font-weight-light medicina">La password deve contenere almeno 6 caratteri</p>
            </div>
          </div>
        </div>
        <div class="col d-flex justify-content-end">
          <button type="button" class="btn impostazioni" data-toggle="modal" data-target="#ModalPassword">cambia</button>
        </div>
      </div>
      <div class="row d-flex align-items-center">
        <div class="col">
          <div class="row mt-3">
            <div class="col">
              <p class="font-weight-bold">Immagine Avatar</p>
            </div>
          </div>
          <div class="row mb-3">
            <div class="col">
              <p class="font-weight-light medicina">L 'immagine deve essere in formato .png o .jpg</p>
            </div>
          </div>
        </div>
        <div class="col d-flex justify-content-end">
          <input type="file" accept="image/*" onchange="LoadFile(event, 'ImgWrap')" style="display: none!important;" id="Img"/>
          <button type="button" class="btn impostazioni" onclick="Immagine('Img')">cambia</button>
        </div>
      </div>
      <div id="ImgWrap"></div>
    </div>
    <!-- Modal -->
    <div class="modal fade show" id="ModalPassword" tabindex="-1" role="dialog" aria-labelledby="ModalPasswordCenterTitle" aria-modal="true">
      <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
          <div class="modal-body pl-0 py-0">
            <div class="container-fluid">
              <div class="row">
                <div class="col-2 bg-info" style="border-top-left-radius: 2px; border-bottom-left-radius: 2px;"></div>
                <div class="col pr-0">
                  <div class="row">
                    <div class="col pr-0">
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true" style="font-size: 2rem">×</span>
                      </button>
                    </div>
                  </div>
                  <div class="row my-2">
                    <div class="col">
                      <img src="../Assets/SVG/LogoMin.svg" width="25vw">
                    </div>
                  </div>
                  <div class="row mb-2">
                    <div class="col">
                      <p class="font-weight-bold h6">Cambia la tua password</p>
                    </div>
                  </div>
                  <% if(ErroreVecchiaPassword){ %>
						        <div class="row mt-3">
						          <div class="col-12">
						            <div class="alert alert-danger alert-dismissible fade show p-3 w-75" role="alert">
						              Attenzione!! La vecchia password inserita non coincide con quella attuale.
						            </div>
						          </div>
						        </div>
						      <% }//endif %>
						      <% 
					          request.getSession().removeAttribute("ErroreVecchiaPassword");
					          request.getSession().setAttribute("ErroreVecchiaPassword", false); 
						      %>
						      <% if(ErroreNuovaPassword){ %>
                    <div class="row mt-3">
                      <div class="col-12">
                        <div class="alert alert-danger alert-dismissible fade show p-3 w-75" role="alert">
                          Attenzione!! La nuova password e la conferma non coincidono.
                        </div>
                      </div>
                    </div>
                  <% }//endif %>
                  <% 
                    request.getSession().removeAttribute("ErroreNuovaPassword");
                    request.getSession().setAttribute("ErroreNuovaPassword", false); 
                  %>
                  <form action="/Homeschool/Impostazioni" method="POST">
                    <div class="row py-2">
                      <div class="col">
                        <div class="input-group w-75">
                          <input type="password" class="form-control" name="VecchiaPassword" placeholder="vecchia password..." data-toggle="password" >
                          <div class="input-group-append">
                            <div class="input-group-text"><i class="fa fa-eye"></i></div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="row py-2">
                      <div class="col">
                        <div class="input-group w-75">
                          <input type="password" class="form-control" name="NuovaPassword" placeholder="nuova password..." data-toggle="password" >
                          <div class="input-group-append">
                            <div class="input-group-text"><i class="fa fa-eye"></i></div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="row py-2">
                      <div class="col">
                        <div class="input-group w-75">
                          <input type="password" class="form-control" name="ConfermaNuovaPassword" placeholder="conferma la nuova password..." data-toggle="password" >
                          <div class="input-group-append">
                            <div class="input-group-text"><i class="fa fa-eye"></i></div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="row pt-2 pb-5">
                      <div class="col">
                        <input type="hidden" name="Tipo" value="Password">
                        <button type="submit" class="btn btn-primary w-50">Modifica</button>
                      </div>
                    </div>
                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- FINE CONTENUTO PAGINA -->
    <!-- INIZIO FOOTER -->
    <footer class="footer">
      <div class="row no-gutters">
        <div class="col-12 col-md-6">
          <p>
            <span>&#64;</span>2020 Dhillon-Schifano<span>&trade;</span>
          </p>
        </div>
        <div class="col-12 col-md-6">
          <div class="text-right">
            <a href="">prova</a> <a href="">prova</a> <a href="">prova</a>
          </div>
        </div>
      </div>
    </footer>
    <!-- FINE FOOTER -->
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <!-- JS Input password -->
    <script src="Assets/Btn.js"></script>
    <% if(ErroreVecchiaPassword || ErroreNuovaPassword){ %>
      <script type="text/javascript">
        $('#ModalPassword').modal('toggle')
      </script>
    <% }//endif %>
  </body>
</html>