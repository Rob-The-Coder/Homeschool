package Servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Beans.StudenteBean;
import DB.DBManagement;

/**
 * Servlet implementation class DashboardClassi
 */
@WebServlet("/DashboardClassi")
public class DashboardClassi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardClassi() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
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
	  String Classe = request.getParameter("classe");
    String Materia=request.getParameter("materia");
	  ArrayList<StudenteBean> Studenti=new ArrayList<StudenteBean>();
	  ArrayList<ArrayList<Integer>> VotiStudente=new ArrayList<ArrayList<Integer>>();
	  ArrayList<Integer> App;
	  try{
	    DBManagement DB = new DBManagement();
	    Studenti=DB.selectStudenti(Classe);
	    
	    //Per ogni studente ottengo tutti i suoi voti relativi alla materia
	    for(int i=0; i<Studenti.size(); i=i+1){
	      App=new ArrayList<Integer>();
	      String email=Studenti.get(i).getEmail();
	      String password=Studenti.get(i).getPassword();
	      ArrayList<Integer> Id=DB.selectIdQuestionari(email, password, Materia, "%", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	      for(int j=0; j<Id.size(); j=j+1){
	        App.add(DB.selectVotoQuestionario(email, password, Id.get(j), Materia, "%", new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
	      }//endfor
	      VotiStudente.add(App);
	    }//endfor
	    
	    //Aggiunta degli attributi alla sessione
      request.getSession().removeAttribute("Classe");
      request.getSession().setAttribute("Classe", Classe);
      request.getSession().removeAttribute("VetStudenti");
      request.getSession().setAttribute("VetStudenti", Studenti);
      request.getSession().removeAttribute("VotiStudente");
      request.getSession().setAttribute("VotiStudente", VotiStudente);
      
      response.sendRedirect("Insegnante/Classe.jsp");
    }catch (SQLException e){
      System.out.println("ERROR:" + e.getErrorCode() + ":" + e.getMessage());
      e.printStackTrace();
      request.getSession().removeAttribute("COMPANY");
      //RequestDispatcher rd = sc.getRequestDispatcher("/error.jsp");
      //rd.forward(request, response);
      //response.sendRedirect("/error.jsp");
    }
	}

}
