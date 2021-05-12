<%@ page import="java.util.ArrayList"%>
<%@ page import="Beans.StudenteBean" %>
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
ArrayList<ArrayList<String>> ClasseMateria = new ArrayList<ArrayList<String>>();
if (request.getSession().getAttribute("ClasseMateria") != null) {
  ClasseMateria = (ArrayList<ArrayList<String>>) request.getSession().getAttribute("ClasseMateria");
} //endif
ArrayList<ArrayList<StudenteBean>> Studenti = new ArrayList<ArrayList<StudenteBean>>();
if (request.getSession().getAttribute("Studenti") != null) {
  Studenti = (ArrayList<ArrayList<StudenteBean>>) request.getSession().getAttribute("Studenti");
} //endif
ArrayList<ArrayList<String>> Cognomi=new ArrayList<ArrayList<String>>();
ArrayList<String> App;
for(int i=0; i<Studenti.size(); i=i+1){
  App=new ArrayList<String>();
  for(int j=0; j<Studenti.get(i).size(); j=j+1){
    App.add(Studenti.get(i).get(j).getCognome());
  }//endfor
  Cognomi.add(App);
}//endfor
ArrayList<ArrayList<String>> MediaPrimoPeriodo = new ArrayList<ArrayList<String>>();
if (request.getSession().getAttribute("MediaPrimoPeriodo") != null) {
  MediaPrimoPeriodo = (ArrayList<ArrayList<String>>) request.getSession().getAttribute("MediaPrimoPeriodo");
} //endif
ArrayList<ArrayList<String>> MediaSecondoPeriodo = new ArrayList<ArrayList<String>>();
if (request.getSession().getAttribute("MediaSecondoPeriodo") != null) {
  MediaSecondoPeriodo = (ArrayList<ArrayList<String>>) request.getSession().getAttribute("MediaSecondoPeriodo");
} //endif
ArrayList<String> InfoUtente=new ArrayList<String>();
if (request.getSession().getAttribute("InfoUtente") != null) {
  InfoUtente=(ArrayList<String>) request.getSession().getAttribute("InfoUtente");
} //endif
%>
<!DOCTYPE html>
<html>
	<head>
		<title>Dashboard classi</title>
		<meta charset="ISO-8859-1">
		<!-- Required meta tags -->
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<!-- Bootstrap CSS -->
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
		<!-- Loading bar CSS -->
		<link rel="stylesheet" type="text/css" href="../Assets/LoadingBar/loading-bar.css">
		<!-- Dashboard CSS -->
		<link rel="stylesheet" type="text/css" href="../Assets/CSS/StileBase.css">
		<link rel="stylesheet" type="text/css" href="../Assets/CSS/Animazioni.css">
		<!-- Dashboard Insegnante CSS -->
		<link rel="stylesheet" type="text/css" href="Assets/StileInsegnanti.css">
		<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" />
	</head>
	<body>
		<!-- Loading bar JS -->
		<script type="text/javascript" src="../Assets/LoadingBar/loading-bar.js"></script>
		<!-- Chartjs JS -->
		<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.bundle.min.js" integrity="sha512-SuxO9djzjML6b9w9/I07IWnLnQhgyYVSpHZx0JV97kGBfTIsUYlWflyuW4ypnvhBrslz1yJ3R+S14fdCWmSmSA==" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.min.js" integrity="sha512-d9xgZrVZpmmQlfonhQUvTR7lMPtO7NkZMkA0ABN3PHCbKA5nqylQ/yWlFAyY6hYgdF1Qh6nYiuADWwKB4C2WSw==" crossorigin="anonymous"></script>
		<!-- Dashboard JS -->
		<script type="text/javascript" src="../Assets/JS/FunzioniBase.js"></script>
		<script type="text/javascript" src="../Assets/JS/Carte.js"></script>
		<script type="text/javascript" src="Assets/FunzioniInsegnanti.js"></script>
		<!-- NAVBAR -->
    <nav class="navbar" id="navbar">
      <!-- LOGO HOMESCHOOL -->
      <a class="navbar-brand" href=""><img id="LOGO"></a>
      <!-- COMPONENTI NAVBAR -->
      <ul class="nav align-items-center">
        <li class="nav-item">
          <div class="dropdown">
            <button class="btn dropdown" type="button" data-toggle="dropdown" aria-expanded="false" id="DropdownButton" onclick="AggiungiClasse()">
              <svg id="rotella" xmlns="http://www.w3.org/2000/svg" width="17" height="17" fill="currentColor" class="bi bi-gear" viewBox="0 0 17 17">
                <path d="M8 4.754a3.246 3.246 0 1 0 0 6.492 3.246 3.246 0 0 0 0-6.492zM5.754 8a2.246 2.246 0 1 1 4.492 0 2.246 2.246 0 0 1-4.492 0z"/>
                <path d="M9.796 1.343c-.527-1.79-3.065-1.79-3.592 0l-.094.319a.873.873 0 0 1-1.255.52l-.292-.16c-1.64-.892-3.433.902-2.54 2.541l.159.292a.873.873 0 0 1-.52 1.255l-.319.094c-1.79.527-1.79 3.065 0 3.592l.319.094a.873.873 0 0 1 .52 1.255l-.16.292c-.892 1.64.901 3.434 2.541 2.54l.292-.159a.873.873 0 0 1 1.255.52l.094.319c.527 1.79 3.065 1.79 3.592 0l.094-.319a.873.873 0 0 1 1.255-.52l.292.16c1.64.893 3.434-.902 2.54-2.541l-.159-.292a.873.873 0 0 1 .52-1.255l.319-.094c1.79-.527 1.79-3.065 0-3.592l-.319-.094a.873.873 0 0 1-.52-1.255l.16-.292c.893-1.64-.902-3.433-2.541-2.54l-.292.159a.873.873 0 0 1-1.255-.52l-.094-.319zm-2.633.283c.246-.835 1.428-.835 1.674 0l.094.319a1.873 1.873 0 0 0 2.693 1.115l.291-.16c.764-.415 1.6.42 1.184 1.185l-.159.292a1.873 1.873 0 0 0 1.116 2.692l.318.094c.835.246.835 1.428 0 1.674l-.319.094a1.873 1.873 0 0 0-1.115 2.693l.16.291c.415.764-.42 1.6-1.185 1.184l-.291-.159a1.873 1.873 0 0 0-2.693 1.116l-.094.318c-.246.835-1.428.835-1.674 0l-.094-.319a1.873 1.873 0 0 0-2.692-1.115l-.292.16c-.764.415-1.6-.42-1.184-1.185l.159-.291A1.873 1.873 0 0 0 1.945 8.93l-.319-.094c-.835-.246-.835-1.428 0-1.674l.319-.094A1.873 1.873 0 0 0 3.06 4.377l-.16-.292c-.415-.764.42-1.6 1.185-1.184l.292.159a1.873 1.873 0 0 0 2.692-1.115l.094-.319z"/>
              </svg>
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
					<a class="btn nav-link" href="DashboardClassi.jsp">
						<p>Classi</p>
					</a>
				</li>
				<li class="nav-items">
					<a class="btn nav-link" href="DashboardQuestionari.jsp">
						<p>Questionari</p>
					</a>
				</li>
			</ul>
		</nav>
		<!-- FINE NAVBAR ELEMENTI -->
		<!-- INIZIO CONTENUTO PAGINA -->
		<div class="container-fluid ContenutoPagina">
			<div class="row mb-3">
				<div class="col">
					<p class="TestoNormale font-weight-bold">CLASSI</p>
				</div>
			</div>
			<!-- CARTA PER LA ClASSE -->
			<% for(int i=0; i<ClasseMateria.size(); i=i+1){ %>
			  <div class="card mb-3" id="CARD">
	        <div class="card-body">
	          <div class="row">
	            <div class="col">
	              <p class="TestoNormale font-weight-bold h3"><%= ClasseMateria.get(i).get(0) %> <%= ClasseMateria.get(i).get(1).toUpperCase() %></p>               
	            </div>
	          </div>
	          <div class="row">
	            <div class="col">
	              <canvas id="GraficoStudenti<%= i+1 %>" width="auto" height="100vh"></canvas>           
	            </div>
	          </div>
	          <div class="row">
	            <div class="col">
	              <form action="/Homeschool/DashboardClassi" method="POST" class="w-100 d-flex justify-content-end">
	                 <input type="hidden" name="classe" value=<%= ClasseMateria.get(i).get(0)  %>>
	                 <input type="hidden" name="materia" value=<%= ClasseMateria.get(i).get(1)  %>>
	                 <button type="submit" class="btn btn-outline-danger w-25">Vai alla classe</button>
	              </form>
	            </div>
	          </div>      
	        </div>
	      </div>
			<% } %>
		</div>
		<!-- FINE CONTENUTO PAGINA -->
		<!-- INIZIO FOOTER -->
		<footer class="footer">
      <div class="row no-gutters">
        <div class="col-12 col-md-6">
          <p><span>&#64;</span>2020 Dhillon-Schifano<span>&trade;</span></p>
        </div>
        <div class="col-12 col-md-6">
          <div class="text-right">
            <a href="">prova</a>
            <a href="">prova</a>
            <a href="">prova</a>
          </div>
        </div>
      </div>
    </footer>
		<!-- FINE FOOTER -->
		<!-- Optional JavaScript -->
		<!-- jQuery first, then Popper.js, then Bootstrap JS -->
		<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
		<script type="text/javascript">
      <% for(int i=0; i<ClasseMateria.size(); i=i+1){ %>
        var Alunni=[];
        <% for(int j=0; j<Cognomi.get(i).size(); j=j+1){ %>
          Alunni.push("<%= Cognomi.get(i).get(j) %>");
        <% } %>
	      var ctx=document.getElementById("GraficoStudenti<%= i+1 %>").getContext('2d');
	      var myChart=new Chart(ctx, {
	          type: 'bar',
	          data: {
	            labels: Alunni,
	            datasets: [{
	              label: 'media primo quadrimestre',
	              data: <%= MediaPrimoPeriodo.get(i) %>,
	              backgroundColor: 'rgba(255, 99, 132, 0.2)',
	              borderColor: 'rgba(255, 99, 132, 1)',
	              borderWidth: 1
	            }, {
	              label: 'media secondo quadrimestre',
	              data: <%= MediaSecondoPeriodo.get(i) %>,
	              backgroundColor: 'rgba(255, 193, 7, 0.2)',
	              borderColor: 'rgba(255, 193, 7, 1)',
	              borderWidth: 1
	            }]
	          },
	          options: {
	            scales: {
	              yAxes: [{
	                display: true,
	                ticks: {
	                  suggestedMin: 0,
	                  suggestedMax: 100,
	                  beginAtZero: true
	                }
	              }]
	            },
	            animation: {
	              duration: 1000,
	              animation: true,
	              easing: 'linear'
	            }
	          }
	      });
      <% } %>
    </script>
	</body>
</html>