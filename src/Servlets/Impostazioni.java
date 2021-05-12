package Servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.DBManagement;

/**
 * Servlet implementation class Impostazioni
 */
@WebServlet("/Impostazioni")
public class Impostazioni extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Impostazioni() {
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
	  String TipoUtente="";
	  if (request.getSession().getAttribute("TipoUtente") != null) {
	    TipoUtente=(String)request.getSession().getAttribute("TipoUtente");
	  } //endif
	  String email="";
    if (request.getSession().getAttribute("email") != null) {
      email = (String) request.getSession().getAttribute("email");
    } //endif
    String password="";
    if (request.getSession().getAttribute("password") != null) {
      password = (String) request.getSession().getAttribute("password");
    } //endif
	  DBManagement DB = new DBManagement();
    
	  try{
	    String VecchiaPassword="";
	    String NuovaPassword="";
	    String ConfermaPassword="";
	    if (request.getSession().getAttribute("password") != null) {
	      password = (String) request.getSession().getAttribute("password");
	    } //endif
	    if(request.getParameter("Tipo").equals("Password")){
	      //modifica password
	      VecchiaPassword=request.getParameter("VecchiaPassword");
	      
	      if(!VecchiaPassword.equals(password)){
	        //Errore, la vecchia password e la password attuale non coincidono
	        request.getSession().removeAttribute("ErroreVecchiaPassword");
          request.getSession().setAttribute("ErroreVecchiaPassword", true);
          
          response.sendRedirect("Impostazioni/Impostazioni.jsp");
	      }else{
	        NuovaPassword=request.getParameter("NuovaPassword");
	        ConfermaPassword=request.getParameter("ConfermaNuovaPassword");
	        if(NuovaPassword.equals(ConfermaPassword)){
	          //modifica password
	          DB.updatePassword(TipoUtente, email, password, NuovaPassword);
	          
	          ArrayList<String> InfoUtente=new ArrayList<String>();
	          InfoUtente=DB.selectInfoUtente("Insegnante", email, NuovaPassword);
	          request.getSession().removeAttribute("InfoUtente");
	          request.getSession().setAttribute("InfoUtente", InfoUtente);
	          
	          request.getSession().removeAttribute("password");
	          request.getSession().setAttribute("password", NuovaPassword);
	          
	          if(TipoUtente.equals("Studente")){
	            response.sendRedirect("Studente/Dashboard.jsp");
	          }else{
	            response.sendRedirect("Insegnante/DashboardClassi.jsp");
	          }//endif
	        }else{
	          //Errore, le nuove password non coincidono
	          request.getSession().removeAttribute("ErroreNuovaPassword");
	          request.getSession().setAttribute("ErroreNuovaPassword", true);
	          
	          response.sendRedirect("Impostazioni/Impostazioni.jsp");
	        }//endif
	      }//endif
	    }else{
	      //modifica immagine
	      String Immagine=request.getParameter("Immagine");
	      DB.updateAvatar(TipoUtente, email, password, Immagine);
	      
	      ArrayList<String> InfoUtente=new ArrayList<String>();
        InfoUtente=DB.selectInfoUtente("Insegnante", email, password);
        request.getSession().removeAttribute("InfoUtente");
        request.getSession().setAttribute("InfoUtente", InfoUtente);
	      
	      if(TipoUtente.equals("Studente")){
	        response.sendRedirect("Studente/Dashboard.jsp");
	      }else{
	        response.sendRedirect("Insegnante/DashboardClassi.jsp");
	      }//endif
	    }//endif

    }catch (SQLException e){
      System.out.println("ERROR:" + e.getErrorCode() + ":" + e.getMessage());
      e.printStackTrace();
      request.getSession().removeAttribute("COMPANY");
      //RequestDispatcher rd = sc.getRequestDispatcher("/error.jsp");
      //rd.forward(request, response);
      //response.sendRedirect("/error.jsp");
    }//endtry
	}

}
