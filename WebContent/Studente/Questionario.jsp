<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="Beans.*"%>
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

ArrayList<String> InfoUtente=new ArrayList<String>();
if (request.getSession().getAttribute("InfoUtente") != null) {
  InfoUtente=(ArrayList<String>) request.getSession().getAttribute("InfoUtente");
} //endif

Map<String, String> InfoQuestionario = new HashMap<String, String>();
if(request.getSession().getAttribute("InfoQuestionario") != null) {
	InfoQuestionario = (Map<String, String>) request.getSession().getAttribute("InfoQuestionario");
}//endif

ArrayList<DomandaBean> DomandeQuestionario = new ArrayList<DomandaBean>();
if(request.getSession().getAttribute("DomandeQuestionario") != null) {
	DomandeQuestionario = (ArrayList<DomandaBean>) request.getSession().getAttribute("DomandeQuestionario");
}//endif

ArrayList<ArrayList<RispostaBean>> RisposteDomande = new ArrayList<ArrayList<RispostaBean>>();
if(request.getSession().getAttribute("RisposteDomande") != null) {
	RisposteDomande = (ArrayList<ArrayList<RispostaBean>>) request.getSession().getAttribute("RisposteDomande");
}//endif

int VotoQuestionario = 0;
if (request.getSession().getAttribute("VotoQuestionario")!=null){
	VotoQuestionario = (Integer) request.getSession().getAttribute("VotoQuestionario");
}//endif

ArrayList<ArrayList<Boolean>> RisposteSelezionate = new ArrayList<ArrayList<Boolean>>();
if (request.getSession().getAttribute("RisposteSelezionate")!=null){
	RisposteSelezionate = (ArrayList<ArrayList<Boolean>>) request.getSession().getAttribute("RisposteSelezionate");
}//endif

String App=InfoQuestionario.get("TempoMassimo");
String[] split = App.split(":");
int Ora=Integer.parseInt(split[0]);
int Minuti=Integer.parseInt(split[1]);
String AppTempo=new SimpleDateFormat("HH:mm").format(new Date());
split = AppTempo.split(":");
int Ora2=Integer.parseInt(split[0]);
int Minuti2=Integer.parseInt(split[1]);

Ora+=Ora2;
Minuti+=Minuti2;
if(Minuti>=60){
  Minuti-=60;
  Ora+=1;
}//endif

String OraFinale=Ora+":"+Minuti;
%>
<!doctype html>
<html>
<head>
	<title>Visualizza questionario</title>
	<meta charset="ISO-8859-1">
	<!-- Required meta tags -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<!-- Bootstrap CSS -->
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
	<!-- Dashboard CSS -->
	<link rel="stylesheet" href="../Assets/CSS/StileBase.css" type="text/css">
	<link rel="stylesheet" href="../Assets/CSS/Animazioni.css" type="text/css">
	<link rel="stylesheet" href="Assets/StileInsegnanti.css" type="text/css">
	<!-- LoadingBar CSS -->
	<link rel="stylesheet" href="../Assets/LoadingBar/loading-bar.css" type="text/css">
</head>
<body>
	<!-- LoadingBar JS -->
	<script type="text/javascript" src="../Assets/LoadingBar/loading-bar.js"></script>
	<!-- Chartjs JS -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.bundle.min.js" integrity="sha512-SuxO9djzjML6b9w9/I07IWnLnQhgyYVSpHZx0JV97kGBfTIsUYlWflyuW4ypnvhBrslz1yJ3R+S14fdCWmSmSA==" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.min.js" integrity="sha512-d9xgZrVZpmmQlfonhQUvTR7lMPtO7NkZMkA0ABN3PHCbKA5nqylQ/yWlFAyY6hYgdF1Qh6nYiuADWwKB4C2WSw==" crossorigin="anonymous"></script>
	<!-- Dashboard JS -->
	<script type="text/javascript" src="../Assets/JS/FunzioniBase.js"></script>
	<script type="text/javascript" src="../Assets/JS/Carte.js"></script>
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
							<div
								class="col-2 d-flex justify-content-center align-self-center">
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
	<!-- CONTENUTO PAGINA -->
	<div class="container-fluid ContenutoPagina-Q">
		<!-- CARTA QUESTIONARIO -->
		<div class="card mb-3" id="CARD">
			<div class="card-header bg-info pt-0"></div>
			<div class="card-body py-2">
				<div class="row mx-2 py-2 align-items-center">
					<div class="col-12">
						<p style="font-size: 40px"><%= InfoQuestionario.get("NomeQuestionario") %></p>
					</div>
				</div>
				<!-- DESCRIZIONE -->
				<div class="row mx-2 py-2 align-items-center">
					<div class="col-12">
						<p style="font-size: 20px"><%= InfoQuestionario.get("Descrizione") %></p>
					</div>
				</div>
				<!-- ORARIO D'INIZIO MATERIA E SUFFICIENZA-->
				<div class="row mx-2 py-2 align-items-center">
					<div class="col-12 col-xl-3">
						<p class="font-weight-bold"><%=InfoQuestionario.get("NomeMateria")%></p>
					</div>
					<div class="col-12 col-xl-3">
						<p><%=InfoQuestionario.get("DataQuestionario")%></p>
					</div>
					<div class="col-12 col-xl-3">
						<p>
							Sufficienza:
							<%=InfoQuestionario.get("Sufficienza")%>%
						</p>
					</div>
					<div class="col-12 col-xl-3">
						<p>
							Valutazione:
							<%=VotoQuestionario%>%
						</p>
					</div>
				</div>
			</div>
		</div>
		<form action="/Homeschool/SvolgiQuestionario" method="POST">
			<% int NumeroRisposta=0; %>
			<% for (int i = 0; i < DomandeQuestionario.size(); i++) { %>
				<!-- INIZIO DOMANDE -->
				<!-- NUMERO DELLA DOMANDA, N.B.: I TAG SONO UGUALI A QUELLI DELLA SECONDA DOMANDA, DA GENERARE DINAMICAMENTE IN JAVA  -->
				<div class="row my-3">
					<div class="col">
						<p>Domanda numero <%= i+1 %>:</p>
					</div>
				</div>
				<% if(DomandeQuestionario.get(i).getTipoDomanda().equals("Checkbox")){ %>
					<!-- ESEMPIO DI DOMANDA CON PIU' DI UNA RISPOSTA -->
					<div class="card mb-3 DOMANDA" id="CARD">
						<div class="card-body py-2">
							<!--TESTO DELLA DOMANDA-->
							<div class="row mx-2 my-2">
								<div class="col">
									<p name="TestoDomanda<%= i %>" class="main-content-header font-weight-bold"><%= DomandeQuestionario.get(i).getTestoDomanda() %></p>
								</div>
							</div>
							<!--IMMAGINE DOMANDA-->
              <div class="row mx-2 my-2">
                <% if(DomandeQuestionario.get(i).getImmagineDomanda()!=null){ %>
                  <div class="row py-1 d-flex justify-content-center">
                    <img width="50%" src=<%= DomandeQuestionario.get(i).getImmagineDomanda() %> >
                  </div>
                <% }//endif %>
              </div>
							<!--RISPOSTE-->
							<div class="row mx-2 py-2 align-items-center">
								<div class="col-12 col-xl-12">
									<% for(int j=0; j < RisposteDomande.get(i).size(); j=j+1){ 
									
										//fai l'if e vedi se la risposta è stata selezionata e vedi se è la risposta giusta	
										
										System.out.println(RisposteSelezionate.get(i).get(j));
										
											if (RisposteSelezionate.get(i).get(j)){ //risposta selezionata
										%>
												
												<input type="checkbox" id="Risposta<%= NumeroRisposta %>"	name="Risposta<%= NumeroRisposta %>" value="<%= RisposteDomande.get(i).get(j).getTestoRisposta() %>"  checked onclick="return false;">
												
												<%if(RisposteDomande.get(i).get(j).getRispostaGiusta()){ //risposta selezionata giusta%>
												
													<label style="color:green!important;" for="Risposta<%= NumeroRisposta %>" >  <%= RisposteDomande.get(i).get(j).getTestoRisposta() %></label><br>
												
												<%}else{ //risposta selezionata sbagliata%>
												
													<label style="color:red!important;" for="Risposta<%= NumeroRisposta %>" >  <%= RisposteDomande.get(i).get(j).getTestoRisposta() %></label><br>
												
												<%}//endif %>
												
										<%		
											}else{ //risposta NON selezionata
										
										%>
										
												<input type="checkbox" id="Risposta<%= NumeroRisposta %>"	name="Risposta<%= NumeroRisposta %>" value="<%= RisposteDomande.get(i).get(j).getTestoRisposta() %>" onclick="return false;">
												
												<%if(RisposteDomande.get(i).get(j).getRispostaGiusta()){ //risposta non selezionata giusta%>
												
													<label style="color:green!important;" for="Risposta<%= NumeroRisposta %>" >  <%= RisposteDomande.get(i).get(j).getTestoRisposta() %></label><br>
												
												<%}else{ //risposta non selezionata sbagliata%>
												
													<label for="Risposta<%= NumeroRisposta %>" >  <%= RisposteDomande.get(i).get(j).getTestoRisposta() %></label><br>
												
												<%}//endif %>
												
											<%}//endif %>
											
									<% NumeroRisposta++; %>
									<% }//endfor %>
									</div>
							</div>
						</div>
					</div>
				<% }else if(DomandeQuestionario.get(i).getTipoDomanda().equals("Radio")){ %>
					<!-- ESEMPIO DI DOMANDA CON SOLAMENTE UNA POSSIBILE RISPOSTA -->
					<div class="card mb-3 DOMANDA" id="CARD">
						<div class="card-body py-2">
							<!--TESTO DELLA DOMANDA-->
							<div class="row mx-2 my-2">
								<div class="col">
									<p name="TestoDomanda<%= i %>" 	class="main-content-header font-weight-bold"><%= DomandeQuestionario.get(i).getTestoDomanda() %></p>
								</div>
							</div>
							<!--IMMAGINE DOMANDA-->
              <div class="row mx-2 my-2">
               <% if(DomandeQuestionario.get(i).getImmagineDomanda()!=null){ %>
                 <div class="row py-1 d-flex justify-content-center">
                   <img width="50%" src=<%= DomandeQuestionario.get(i).getImmagineDomanda() %> >
                 </div>
               <% }//endif %>
              </div>
							<!--RISPOSTE-->
							<div class="row mx-2 py-2 align-items-center">
								<div class="col-12 col-xl-12">
							<%
								for (int j = 0; j < RisposteDomande.get(i).size(); j = j + 1) {

									if (RisposteSelezionate.get(i).get(j)) {//risposta selezionata
							%>
									<input type="radio" id="Risposta<%=NumeroRisposta%>" name="Domanda<%=i%>" value="<%=RisposteDomande.get(i).get(j).getTestoRisposta()%>" checked onclick="return false;">
							<%
										if (RisposteDomande.get(i).get(j).getRispostaGiusta()) {//risposta selezionata giusta
							%>
											<label style="color:green!important" for="Risposta<%= NumeroRisposta %>"><%= RisposteDomande.get(i).get(j).getTestoRisposta() %></label>
							<%
										} else {//risposta selezionata sbagliata
							%>
											<label style="color:red!important" for="Risposta<%= NumeroRisposta %>"><%= RisposteDomande.get(i).get(j).getTestoRisposta() %></label>
							<%
										} //endif
							%>


							<%
									} else {//risposta NON selezionata
							%>
										<input type="radio" id="Risposta<%=NumeroRisposta%>" name="Domanda<%=i%>" value="<%=RisposteDomande.get(i).get(j).getTestoRisposta()%>" onclick="return false;">
							<%
										if (RisposteDomande.get(i).get(j).getRispostaGiusta()) {//risposta non selezionata giusta
							%>
											<label style="color:green!important" for="Risposta<%= NumeroRisposta %>"><%= RisposteDomande.get(i).get(j).getTestoRisposta() %></label>
							<%
										} else {//risposta non selezionata sbagliata
							%>
											<label for="Risposta<%= NumeroRisposta %>"><%= RisposteDomande.get(i).get(j).getTestoRisposta() %></label>
							<%
										} //endif
							%>

							<%
									} //endif
							%>


										
										
										<br>
									<% NumeroRisposta++; %>
									<% }//endfor %>
								</div>
							</div>
						</div>
					</div>
				<% }else if(DomandeQuestionario.get(i).getTipoDomanda().equals("Textarea")){ %>
				<!-- ESEMPIO DI DOMANDA CON RISPOSTA TESTUALE -->
				<div class="card mb-3 DOMANDA" id="CARD">
					<div class="card-body py-2">
						<!--TESTO DELLA DOMANDA-->
						<div class="row mx-2 my-2">
							<div class="col">
								<p name="TestoDomanda<%=i%>" class="main-content-header font-weight-bold"><%=DomandeQuestionario.get(i).getTestoDomanda()%></p>
							</div>
						</div>
						<!--IMMAGINE DOMANDA-->
              <div class="row mx-2 my-2">
                <% if(DomandeQuestionario.get(i).getImmagineDomanda()!=null){ %>
                  <div class="row py-1 d-flex justify-content-center">
                    <img width="50%" src=<%= DomandeQuestionario.get(i).getImmagineDomanda() %> >
                  </div>
                <% }//endif %>
              </div>
						<!--RISPOSTE-->
						<div class="row mx-2 py-1 align-items-center">
							<div class="col-12 col-xl-12">
							
								<%if(RisposteSelezionate.get(i).get(0)){//si ha scritto la risposta giusta%>
									
									<p>La risposta data è <span style="color:green!important"> corretta </span></p>
									<input style="color:green!important" value="<%=RisposteDomande.get(i).get(0).getTestoRisposta()%>" type="text" name="Risposta<%=NumeroRisposta%>" id="Risposta<%=NumeroRisposta%>" readonly>
								
								<%}else{%>
								
									<p>La risposta data è <span style="color:red!important"> sbagliata </span></p>
									<input style="color:green!important" value="<%=RisposteDomande.get(i).get(0).getTestoRisposta()%>" type="text" name="Risposta<%=NumeroRisposta%>" id="Risposta<%=NumeroRisposta%>" readonly>
								
								<%}//endif %>
								
								<% NumeroRisposta++; %>
							</div>
						</div>
					</div>
				</div>
				<% }//endif %>
			<% }//endfor %>

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
</body>
</html>