<%@ page import="java.util.ArrayList"%>
<%@ page import="DB.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	DBManagement DB=new DBManagement();
	ArrayList<String> Materie=new ArrayList<String>();
	ArrayList<String> Classi=new ArrayList<String>();

	Materie=DB.selectListaMaterie();
	Classi=DB.selectListaClassi();
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.0.0-beta1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="Registrazione.css">
    <title>Registrazione</title>
  </head>
  <body>
    <div class="container-fluid bg">
      <div class="row">
        <div class="col-5 position-absolute top-50 start-50 translate-middle">
          <div class="card">
            <div class="card-header">
              <p class="mb-0 h5">Registrazione</p>
            </div>
            <div class="card-body">
              <form action="/Homeschool/Registrazione" method="POST">
                <div class="row py-2">
                  <div class="col-4 d-flex align-self-center">
                    Selezionare il tipo di utente: 
                  </div>
                  <div class="col-5">
                    <select class="form-select" aria-label="Select utente" name="TipoUtente" onchange="CambiaUtente()">
                      <option value="Studente" selected>Studente</option>
                      <option value="Insegnante">Insegnante</option>
                    </select>
                  </div>
                </div>
                <div class="row py-2">
                  <div class="col-6">
                    <label class="form-label">Nome</label>
                    <input type="text" class="form-control" name="Nome" placeholder="Nome...">
                  </div>
                  <div class="col-6">
                    <label class="form-label">Cognome</label>
                    <input type="text" class="form-control" name="Cognome" placeholder="Cognome...">
                  </div>
                </div>
                <div class="row py-2">
                  <div class="col-6">
                    <label class="form-label">Password</label>
                    <input type="password" class="form-control" name="Password" placeholder="Password...">
                  </div>
                  <div class="col-6">
                    <label class="form-label">Conferma password</label>
                    <input type="password" class="form-control" name="ConfermaPassword" placeholder="Password...">
                  </div>
                </div>
                <div class="row py-2">
                  <div class="col-4">
                    <label class="form-label">Email</label>
                    <input type="email" class="form-control" name="Email" placeholder="nome@dominio.it...">
                  </div>
                  <div class="col-4">
                    <label class="form-label">Data di nascita</label>
                    <input type="date" class="form-control" name="Data" placeholder="Data...">
                  </div>
                  <div class="col-4">
                    <label class="form-label">Città</label>
                    <input type="text" class="form-control" name="Città" placeholder="Città ...">
                  </div>
                </div>
                <div id="WrapClassi">
                  <div class="row py-2">
                    <div class="col-4">
                      <label class="form-label">Classe</label>
                      <select class="form-select" aria-label="Classe" name="Classe">
                        <option selected>Scegli la tua classe</option>
                        <% for(int i=0; i<Classi.size(); i=i+1){ %>
                        	<option value="<%= Classi.get(i) %>"><%= Classi.get(i) %></option>
                        <% }//endfor %>
                      </select>
                    </div>
                  </div>
                </div>
                <div class="row py-2">
                  <div class="col-12">
                    <button type="submit" class="btn btn-primary">Registrati</button>
                  </div>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
    <script type="text/javascript">
    	var Materie=[];
    	var Classi=[];
    	<% for(int i=0; i<Materie.size(); i=i+1){ %>
    		Materie.push("<%= Materie.get(i) %>");
    	<% }//endfor %>
    	<% for(int i=0; i<Classi.size(); i=i+1){ %>
			Classi.push("<%= Classi.get(i) %>");
		<% }//endfor %>
    </script>   
    <script type="text/javascript" src="Registrazione.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/2.5.4/umd/popper.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.0.0-beta1/js/bootstrap.min.js"></script>
  </body>
</html>