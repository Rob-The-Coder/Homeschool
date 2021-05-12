<%@ page import="java.util.ArrayList"%>
<%@ page import="Beans.QuestionarioBean" %>
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
ArrayList<QuestionarioBean> Questionari = new ArrayList<QuestionarioBean>();
if (request.getSession().getAttribute("Questionari") != null) {
  Questionari = (ArrayList<QuestionarioBean>) request.getSession().getAttribute("Questionari");
} //endif
ArrayList<String> InfoUtente=new ArrayList<String>();
if (request.getSession().getAttribute("InfoUtente") != null) {
  InfoUtente=(ArrayList<String>) request.getSession().getAttribute("InfoUtente");
} //endif
Boolean ErroreData=false;
if(request.getSession().getAttribute("ErroreData") != null){
  ErroreData=(Boolean) request.getSession().getAttribute("ErroreData");
}//endif
%>
<!DOCTYPE html>
<html>
	<head>
		<title>Dashboard questionari</title>
		<meta charset="ISO-8859-1">
		<!-- Required meta tags -->
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<!-- Bootstrap CSS -->
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
		<!-- Dashboard CSS -->
		<link rel="stylesheet" type="text/css" href="../Assets/CSS/StileBase.css">
		<link rel="stylesheet" type="text/css" href="../Assets/CSS/Animazioni.css">
		<!-- Dashboard Insegnante CSS -->
		<link rel="stylesheet" type="text/css" href="Assets/StileInsegnanti.css">
		<!-- CSS Icone -->
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" />  
	</head>
	<body>
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
          <p class="TestoNormale font-weight-bold">Crea un nuovo questionario</p>
        </div>
      </div>
      <div class="row pb-3">
        <div class="col-12 col-xl-2">
	        <div class="card preview">
	          <div class="card-body">
	            <div class="row">
	              <div class="col-12 d-flex justify-content-center">
	                <img id="ImmagineAdd" width="150vw" height="150vh">
	              </div>
	            </div>
	          </div>
	          <a href="CreaQuestionari.jsp" class="stretched-link"></a>
	        </div>
        </div>
        <% for(int i=Questionari.size()-1; (i>Questionari.size()-6 && i>0); i=i-1){ %>
          <div class="col-12 col-xl-2">
	          <div class="card preview">
	            <div class="card-body">
	              <div class="row">
	                <div class="col-12 d-flex justify-content-center">
	                  <img src="<%= Questionari.get(i).getImmagineQuestionario() %>" width="150vw" height="auto">
	                </div>
	              </div>
	            </div>
	            <div class="card-footer">
	              <div class="row">
	                <div class="col">
	                   <div class="row">
	                     <p class="TestoNormale font-weight-bold"><%= Questionari.get(i).getNomeQuestionario() %></p>
	                   </div>
	                   <div class="row">
	                     <p class="TestoNormale"><%= Questionari.get(i).getDataCreazione() %></p>
	                   </div>
	                 </div>
	              </div>      
	            </div>
	          </div>
          </div>
        <% }//endfor %>
      </div>
      <div class="row mb-3">
        <div class="col">
          <p class="TestoNormale font-weight-bold">Nuovo</p>
        </div>
      </div>
      <div class="row mt-5 mb-1">
        <div class="col">
          <p class="TestoNormale font-weight-bold">I tuoi questionari</p>          
        </div>
      </div>
      <% if(ErroreData){ %>
        <div class="row mt-3">
          <div class="col-12">
	          <div class="alert alert-danger alert-dismissible fade show p-3" role="alert">
	            Attenzione!! Non è possibile modificare o eliminare un questionario già svolto dagli studenti.
	          </div>
          </div>
        </div>
      <% }//endif %>
      <% 
          request.getSession().removeAttribute("ErroreData");
          request.getSession().setAttribute("ErroreData", false); 
      %>
      <form action="/Homeschool/DashboardQuestionari" method="POST" id="Form">
	      <div class="row pb-3">
	      <% for(int i=0; i<Questionari.size(); i=i+1){ %>
		      <% if(i % 4 == 0){ %>
		       </div>
		       <div class="row pb-3">
		      <% } //endif %>
		      <div class="col-12 col-xl-3">
		        <div class="card">
		          <div class="card-body" onclick="document.getElementById('<%= i %>').setAttribute('name', 'CodiceAccesso'); document.getElementById('Form').submit()" style="cursor:pointer">
		            <div class="row">
		              <div class="col-12 d-flex justify-content-center">
		                <img src="<%= Questionari.get(i).getImmagineQuestionario() %>" width="100%" height="auto">
		              </div>
		            </div>
		          </div>
		          <div class="card-footer">
			          <div class="row">
				          <div class="col">
			               <div class="row">
			                 <p class="TestoNormale font-weight-bold"><%= Questionari.get(i).getNomeQuestionario() %></p>
			               </div>
			               <div class="row">
                       <p class="TestoNormale"><%= Questionari.get(i).getCodiceAccesso() %></p>
                     </div>
			               <div class="row">
			                 <p class="TestoNormale"><%= Questionari.get(i).getDataCreazione() %></p>
			               </div>
			             </div>
			             <div class="col">
			               <div class="row d-flex justify-content-end">
			                 <button type="button" class="btn btn-link" onclick="document.getElementById('<%= i %>.<%= i %>').setAttribute('name', 'Azione'); document.getElementById('<%= i %>').setAttribute('name', 'CodiceAccesso'); document.getElementById('Form').submit()">
			                   <span style="font-size: 1.5rem"><i class="fa fa-trash"></i></span>
			                 </button>
			               </div>
			             </div>
			          </div>      
		          </div>
		          <input type="hidden" id="<%= i %>" value="<%= Questionari.get(i).getCodiceAccesso() %>">
		          <input type="hidden" id="<%= i %>.<%= i %>" value="Eliminazione">
		        </div>
	        </div>
	        
	      <% }//endfor %>
	      </div>
      </form>
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
		<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
	</body>
</html>