<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<% 
Boolean ErroreLogin=false;
if(request.getSession().getAttribute("ErroreLogin") != null){
  ErroreLogin=(Boolean) request.getSession().getAttribute("ErroreLogin");
}//endif
%>
<!doctype html>
<html lang="en">
  <head>
    <title>Login</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <!-- Login CSS -->
    <link rel="stylesheet" type="text/css" href="Login.css">
  </head>
  <body>
    <!-- INIZIO LOGIN -->
    <div class="container-fluid bg">
      <div class="row">
        <div class="col-6 d-none d-xl-block"></div>
        <div class="col-10 col-xl-2" id="log">
          <div class="card" id="CARD">
            <div class="card-body">
              <h4 class="card-title">Login</h4>
              <form action="/Homeschool/Login" method="post">
                <div class="form-group">
                  <input type="text" class="form-control mb-3 p-3" placeholder="Username..." name="email">
                </div>
                <div class="form-group">
                  <div class="input-group">
                    <input class="form-control" type="password" placeholder="Password..." data-toggle="password" name="password">
                    <div class="input-group-append">
                      <div class="input-group-text"><i class="fa fa-eye"></i></div>
                    </div>
                  </div>
                </div>
                <div class="form-group">
                  <button type="submit" class="btn btn-primary w-100">Accedi</button>
                </div>
              </form>
              <button type="button" class="btn btn-link p-0" onclick="window.location.href='../Registrazione/Registrazione.jsp'">Non sei registrato? Clicca qui!</button>
              <% if(ErroreLogin){ %>
                <div class="alert alert-danger alert-dismissible fade show p-2 mb-0" role="alert">
                  Utente non trovato! Controllare le credenziali inserite
                </div>    
            <% }//endif %>
            <% 
              request.getSession().removeAttribute("ErroreLogin");
              request.getSession().setAttribute("ErroreLogin", false); 
            %>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- FINE LOGIN -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <!-- Login js -->
    <script src="Login.js"></script>
  </body>
</html>