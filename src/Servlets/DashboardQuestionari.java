package Servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Beans.DomandaBean;
import Beans.QuestionarioBean;
import Beans.RispostaBean;
import DB.DBManagement;

/**
 * Servlet implementation class DashboardQuestionari
 */
@WebServlet("/DashboardQuestionari")
public class DashboardQuestionari extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardQuestionari() {
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
	  String email="";
    if (request.getSession().getAttribute("email") != null) {
      email = (String) request.getSession().getAttribute("email");
    } //endif
    String password="";
    if (request.getSession().getAttribute("password") != null) {
      password = (String) request.getSession().getAttribute("password");
    } //endif
	  DBManagement DB = new DBManagement();
    Map<String, String> InfoQuestionario = new HashMap<String, String>();
    ArrayList<DomandaBean> Domande=new ArrayList<DomandaBean>();
    ArrayList<ArrayList<RispostaBean>> Risposte=new ArrayList<ArrayList<RispostaBean>>();
    String idModificaQuestionario="";
    String Azione=request.getParameter("Azione");
    String CodiceAccesso=request.getParameter("CodiceAccesso");
    
    try{
      if(Azione!=null){
        //Eliminazione
        InfoQuestionario=DB.selectInfoQuestionario(CodiceAccesso);
        if(new SimpleDateFormat("yyyy-MM-dd").parse(InfoQuestionario.get("DataQuestionario")).before(new Date())){
          //errore, non puoi eliminare un questionario già svolto
          request.getSession().removeAttribute("ErroreData");
          request.getSession().setAttribute("ErroreData", true);
          
          response.sendRedirect("Insegnante/DashboardQuestionari.jsp");
        }else{
          DB.deleteQuestionario(CodiceAccesso);
          ArrayList<QuestionarioBean> Questionari=new ArrayList<QuestionarioBean>();
          Questionari=DB.selectQuestionari(email, password);
          
          request.getSession().removeAttribute("Questionari");
          request.getSession().setAttribute("Questionari", Questionari);
          
          response.sendRedirect("Insegnante/DashboardQuestionari.jsp"); 
        }//endif 
      }else{
        //Mofifica
        InfoQuestionario=DB.selectInfoQuestionario(CodiceAccesso);
        if(new SimpleDateFormat("yyyy-MM-dd").parse(InfoQuestionario.get("DataQuestionario")).before(new Date())){
          //errore, non puoi modificare un questionario già svolto
          request.getSession().removeAttribute("ErroreData");
          request.getSession().setAttribute("ErroreData", true);
          
          response.sendRedirect("Insegnante/DashboardQuestionari.jsp");
        }else{
          idModificaQuestionario=DB.selectIdQuestionario(CodiceAccesso);
          Domande=DB.selectDomandeQuestionario(CodiceAccesso);
          for(int i=0; i<Domande.size(); i=i+1){
            Risposte.add(DB.selectRisposteDomanda(Domande.get(i).getTestoDomanda()));
          }//endfor
          
          //Aggiunta degli attributi alla sessione
          request.getSession().removeAttribute("InfoQuestionario");
          request.getSession().setAttribute("InfoQuestionario", InfoQuestionario);
          request.getSession().removeAttribute("Domande");
          request.getSession().setAttribute("Domande", Domande);
          request.getSession().removeAttribute("Risposte");
          request.getSession().setAttribute("Risposte", Risposte);
          request.getSession().removeAttribute("idModificaQuestionario");
          request.getSession().setAttribute("idModificaQuestionario", idModificaQuestionario);
          request.getSession().removeAttribute("CodiceAccesso");
          request.getSession().setAttribute("CodiceAccesso", CodiceAccesso);
          
          response.sendRedirect("Insegnante/ModificaQuestionario.jsp");  
        }//endif
      }//endif   
    }catch (SQLException | ParseException e){
      System.out.println("ERROR:" + ((SQLException) e).getErrorCode() + ":" + e.getMessage());
      e.printStackTrace();
      request.getSession().removeAttribute("COMPANY");
      //RequestDispatcher rd = sc.getRequestDispatcher("/error.jsp");
      //rd.forward(request, response);
      //response.sendRedirect("/error.jsp");
    }//endtry
	}

}
