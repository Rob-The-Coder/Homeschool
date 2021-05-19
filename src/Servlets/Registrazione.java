package Servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.DBManagement;

/**
 * Servlet implementation class Registrazione
 */
@WebServlet("/Registrazione")
public class Registrazione extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Registrazione() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	 DBManagement DB=new DBManagement();
	 String TipoUtente=request.getParameter("TipoUtente");
		String Nome=request.getParameter("Nome");
  String Cognome=request.getParameter("Cognome");
  String Password=request.getParameter("Password");
  String ConfermaPassword=request.getParameter("ConfermaPassword");
  String Email=request.getParameter("Email");
  String Data=request.getParameter("Data");
  String Città=request.getParameter("Città");
  
  try{
   if(TipoUtente.equals("Studente")){
     String Classe=request.getParameter("Classe");
     
     DB.insertStudente(Classe, Nome, Cognome, Email, Password, Città, Data);
   }else{
     ArrayList<String> Classi=new ArrayList<String>();
     ArrayList<String> Materie=new ArrayList<String>();
     
     String App=request.getParameter("Classe1");
     int i=2;
     while(!App.equals("Classe")){
       Classi.add(App);
       Materie.add(request.getParameter("Materia"+(i-1)));
       App=request.getParameter("Classe"+i);
       i=i+1;
     }//endwhile
     
     DB.insertInsegnante(Classi, Materie, Nome, Cognome, Email, Password, Città, Data);
   }//endif
  }catch(SQLException e){
   System.out.println("ERROR:" + e.getErrorCode() + ":" + e.getMessage());
   e.printStackTrace();
   request.getSession().removeAttribute("COMPANY");
   //RequestDispatcher rd = sc.getRequestDispatcher("/error.jsp");
   //rd.forward(request, response);
   //response.sendRedirect("/error.jsp");
  }//endtry
  response.sendRedirect("Login/Login.jsp");
	}
}
