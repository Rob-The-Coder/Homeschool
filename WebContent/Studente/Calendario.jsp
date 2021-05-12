<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="DB.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
ArrayList<String> InfoUtente=new ArrayList<String>();
if (request.getSession().getAttribute("InfoUtente") != null) {
  InfoUtente=(ArrayList<String>) request.getSession().getAttribute("InfoUtente");
} //endif
ArrayList<String> idQuestionari=new ArrayList<String>();
if (request.getSession().getAttribute("idQuestionari") != null) {
  idQuestionari=(ArrayList<String>) request.getSession().getAttribute("idQuestionari");
} //endif
Map<String, String> InfoQuestionario = new HashMap<String, String>();
DBManagement DB = new DBManagement();
%>
<!doctype html>
<html>
<head>
	<title>Calendario</title>
	<meta charset="ISO-8859-1">
	<!-- Required meta tags -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<!-- Bootstrap CSS -->
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
	<!-- Dashboard CSS -->
	<link rel="stylesheet" href="../Assets/CSS/StileBase.css" type="text/css">
	<link rel="stylesheet" href="../Assets/CSS/Animazioni.css" type="text/css">
	<link rel="stylesheet" href="Assets/StileImpostazioni.css" type="text/css">
	<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" />
	<!-- FullCalendar CSS -->
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/fullcalendar@5.6.0/main.min.css">
	<!-- FullCalendar JS -->
	<script src="https://cdn.jsdelivr.net/npm/fullcalendar@5.6.0/main.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/fullcalendar@5.6.0/locales-all.min.js"></script>
	<script>
		document.addEventListener('DOMContentLoaded', function() {
			var calendarEl = document.getElementById('calendar');
			var calendar = new FullCalendar.Calendar(calendarEl, {
			  locale: 'it', // the initial locale. of not specified, uses the first one
				themeSystem: 'bootstrap',
				initialView: 'dayGridMonth', 
				editable: false,
				events: [
					<% for(int i=0; i<idQuestionari.size(); i=i+1){ %>
					 <% InfoQuestionario=DB.selectInfoQuestionario(idQuestionari.get(i)); %>
					 {
						 id: "<%= idQuestionari.get(i) %>",
						 title: "<%= InfoQuestionario.get("NomeQuestionario") %>",
						 start: "<%= InfoQuestionario.get("DataQuestionario") %>",
						 end: "<%= InfoQuestionario.get("DataQuestionario") %>",
						 className: [
						   "bg-<%= InfoQuestionario.get("NomeMateria").substring(0, 3).toUpperCase() %>",
						   "bg-<%= InfoQuestionario.get("NomeMateria").substring(0, 3).toUpperCase() %>-border",
						   "<%= idQuestionari.get(i) %>"
						 ],
						 extendedProps: [
							 "<%= InfoQuestionario.get("DataQuestionario") %>",
							 "<%= InfoQuestionario.get("OrarioMinimoInizio") %>",
							 "<%= InfoQuestionario.get("TempoMassimo") %>"
						 ]
					 },
					<% }//endfor %>
				],
				eventClick: function(info){
					var Data=new Date().toJSON().slice(0,10);
					Time=new Date();
					Time=Time.toTimeString();
					Time=Time.split(' ')[0];
					OrarioMassimoInizio=addTimes(info.event.extendedProps[1], info.event.extendedProps[2]);
					if(info.event.extendedProps[0]===Data && (Time>info.event.extendedProps[1] && Time<OrarioMassimoInizio)){
						//trigger modal
						document.getElementById("btn").click();
					}else{
						var el=document.getElementsByClassName(info.event.classNames[2])[0];
						el.setAttribute("id", info.event.id);
						$('#'+info.event.id+'').popover({
							content: "Ci dispiace! Avevi fino alle "+OrarioMassimoInizio+" per svolgere il questionario.",
							placement: "right",
							title: info.event.title,
							trigger: "click"
						});
					}//endif
				}
			});
			calendar.render();
		});
	</script>
</head>
<body>
	<!-- Dashboard JS -->
	<script type="text/javascript" src="../Assets/JS/FunzioniBase.js"></script>
	<script type="text/javascript" src="../Assets/JS/Carte.js"></script>
	<script type="text/javascript" src="Assets/Funzioni.js"></script>
	<!-- NAVBAR -->
	<nav class="navbar" id="navbar">
		<!-- LOGO HOMESCHOOL -->
		<a class="navbar-brand" href=""><img id="LOGO"></a>
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
								<button class="btn" id="BottoneTema" onclick=CambiaTema()></button>
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
	<!-- INIZIO NAVBAR ELEMENTI -->
  <nav class="navbar" id="Elements-navbar">
    <ul class="nav align-items-center">
      <li class="nav-items">
        <a class="btn nav-link" href="Calendario.jsp">
          <p>Calendario</p>
        </a>
      </li>
      <li class="nav-items">
        <a class="btn nav-link" href="Dashboard.jsp">
          <p>Voti</p>
        </a>
      </li>
    </ul>
  </nav>
  <!-- FINE NAVBAR ELEMENTI -->
	<!-- CONTENUTO PAGINA -->
	<div class="container-fluid ContenutoPagina">
	  <div class="row justify-content-center">
		  <div class="card p-3 mb-3 w-75" style="border-radius: 25px">
	      <div id='calendar'></div>
	    </div>
	  </div>
	  <button id="btn" type="button" class="d-none" data-toggle="modal" data-target="#ModalQ"></button>
	</div>
	<!-- Modal -->
  <div class="modal fade show" id="ModalQ" tabindex="-1" role="dialog" aria-labelledby="ModalQCenterTitle" aria-modal="true">
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
                    <p class="font-weight-bold h6">Immetti il codice di accesso per partecipare al questionario.</p>
                  </div>
                </div>
                <form action="/Homeschool/DashboardStudenti" method="POST">
                  <div class="row py-2">
                    <div class="col">
                      <input type="text" class="form-control w-75" name="idQuestionario" placeholder="Codice di accesso ...">
                    </div>
                  </div>
                  <div class="row pt-2 pb-5">
                    <div class="col">
                      <input type="hidden" name="TipoPost" value="SvolgiQuestionario">
                      <button type="submit" class="btn btn-primary w-50">Invia</button>
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
  <script type="text/javascript" src="Assets/Btn.js"></script>
</body>
</html>