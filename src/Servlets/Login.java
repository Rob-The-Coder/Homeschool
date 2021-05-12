package Servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Beans.MateriaBean;
import Beans.QuestionarioBean;
import Beans.StudenteBean;
import DB.DBManagement;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Login() {
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
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		DBManagement login = new DBManagement();
		ArrayList<Integer> Media=new ArrayList<Integer>();
		ArrayList<String> VotiQuadrimestre;
		ArrayList<MateriaBean> ListaMaterie=new ArrayList<MateriaBean>();
		ArrayList<ArrayList<String>> MediaPrimoPeriodo=new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> MediaSecondoPeriodo=new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> DettaglioQuestionario=new ArrayList<ArrayList<String>>();
		ArrayList<String> Dettaglio=new ArrayList<String>();
		ArrayList<ArrayList<String>> ClasseMateria=new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<StudenteBean>> Studenti=new ArrayList<ArrayList<StudenteBean>>();
		ArrayList<QuestionarioBean> Questionari=new ArrayList<QuestionarioBean>();
		ArrayList<String> InfoUtente=new ArrayList<String>();
		Boolean Login=false;
		int AppMedia=0;
		
		try{
			int Loggato = login.selectLogin(email,password);
			if(Loggato==2){
				//Insegnante
			  
			  //Classe e materia dell'insegnante
			  ClasseMateria=login.selectClasseMateriaInsegnante(email, password);
			  for(int i=0; i<ClasseMateria.size(); i=i+1) {
			    Studenti.add(login.selectStudenti(ClasseMateria.get(i).get(0)));
			  }//endfor
			  //Media primo quadrimestre
        for(int i=0; i<ClasseMateria.size(); i=i+1){
          VotiQuadrimestre=new ArrayList<String>();
          for(int j=0; j<Studenti.get(i).size(); j=j+1){
            String EmailStudente=Studenti.get(i).get(j).getEmail();
            String PasswordStudente=Studenti.get(i).get(j).getPassword();
            ArrayList<Integer> Id=login.selectIdQuestionari(EmailStudente, PasswordStudente, ClasseMateria.get(i).get(1), "%",  "2021-02-05");
            AppMedia=0;
            for(int k=0; k<Id.size(); k=k+1){
              AppMedia=AppMedia+login.selectVotoQuestionario(EmailStudente, PasswordStudente, Id.get(k), ClasseMateria.get(i).get(1), "%",  "2021-02-05");
            }//endfor
            if(Id.size()!=0) {
              AppMedia=AppMedia/Id.size();  
            }//endif
            VotiQuadrimestre.add(Integer.toString(AppMedia));
          }//endfor
          MediaPrimoPeriodo.add(VotiQuadrimestre);
        }//endfor
			  //Media secondo quadrimestre
			  for(int i=0; i<ClasseMateria.size(); i=i+1){
			    VotiQuadrimestre=new ArrayList<String>();
          for(int j=0; j<Studenti.get(i).size(); j=j+1){
            String EmailStudente=Studenti.get(i).get(j).getEmail();
            String PasswordStudente=Studenti.get(i).get(j).getPassword();
            ArrayList<Integer> Id=login.selectIdQuestionari(EmailStudente, PasswordStudente, ClasseMateria.get(i).get(1), "2021-02-05",  new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            AppMedia=0;
            for(int k=0; k<Id.size(); k=k+1){
              AppMedia=AppMedia+login.selectVotoQuestionario(EmailStudente, PasswordStudente, Id.get(k), ClasseMateria.get(i).get(1), "2021-02-05",  new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            }//endfor
            if(Id.size()!=0) {
              AppMedia=AppMedia/Id.size();  
            }//endif
            VotiQuadrimestre.add(Integer.toString(AppMedia));
          }//endfor
          MediaSecondoPeriodo.add(VotiQuadrimestre);
        }//endfor
			  Questionari=login.selectQuestionari(email, password);
			  InfoUtente=login.selectInfoUtente("Insegnante", email, password);
			  ListaMaterie=login.selectMaterie();
			  
			  Login=true;
			  
			  //Aggiunta degli attributi alla sessione
        request.getSession().removeAttribute("ClasseMateria");
        request.getSession().setAttribute("ClasseMateria", ClasseMateria);
        request.getSession().removeAttribute("ListaMaterie");
        request.getSession().setAttribute("ListaMaterie", ListaMaterie);
        request.getSession().removeAttribute("Studenti");
        request.getSession().setAttribute("Studenti", Studenti);
        request.getSession().removeAttribute("MediaPrimoPeriodo");
        request.getSession().setAttribute("MediaPrimoPeriodo", MediaPrimoPeriodo);
        request.getSession().removeAttribute("MediaSecondoPeriodo");
        request.getSession().setAttribute("MediaSecondoPeriodo", MediaSecondoPeriodo);
        request.getSession().removeAttribute("email");
        request.getSession().setAttribute("email", email);
        request.getSession().removeAttribute("password");
        request.getSession().setAttribute("password", password);
        request.getSession().removeAttribute("Questionari");
        request.getSession().setAttribute("Questionari", Questionari);
        request.getSession().removeAttribute("TipoUtente");
        request.getSession().setAttribute("TipoUtente", "Insegnante");
        request.getSession().removeAttribute("InfoUtente");
        request.getSession().setAttribute("InfoUtente", InfoUtente);
        request.getSession().removeAttribute("Login");
        request.getSession().setAttribute("Login", Login);
          
				response.sendRedirect("Insegnante/DashboardClassi.jsp");
			}else if(Loggato==1){
				//Studente
				
			  ArrayList<MateriaBean> Materie=login.selectMaterieStudente(email, password);
				//Calcolo media generale e per materia dello studente
				for(int i=0; i<Materie.size(); i=i+1){
					ArrayList<Integer> Id=login.selectIdQuestionari(email, password, Materie.get(i).getMateria(), "%",  new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
					int App=0;
					for(int j=0; j<Id.size(); j=j+1){
						App=App+login.selectVotoQuestionario(email, password, Id.get(j), Materie.get(i).getMateria(), "%",  new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
					}//endfor
					if(Id.size()!=0) {
						App=App/Id.size();	
					}//endif
					Media.add(App);
				}//endfor
				
				ArrayList<Integer> Id=login.selectIdQuestionari(email, password, "%", "%", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				for(int i=0; i<Id.size(); i=i+1){
					Dettaglio=login.selectDettagliQuestionario(email, password, Id.get(i));
					Dettaglio.add(Integer.toString(login.selectVotoQuestionario(email, password, Id.get(i), "%", "%",  new SimpleDateFormat("yyyy-MM-dd").format(new Date()))));
					DettaglioQuestionario.add(Dettaglio);
				}//endfor
			  InfoUtente=login.selectInfoUtente("Studente", email, password);
			  Login=true;
			  
			  //Media generale di tutte le materie
			  int MediaGenerale=Media.get(0);
			  Media.remove(0);
			  
			  //Id per il calendario dei questionari
			  ArrayList<String> idQuestionariCalendario=new ArrayList<String>();
			  idQuestionariCalendario=login.selectIdSchedule(email, password);
			  
				System.out.println("ID DEI QUESTIONARI: " + Id.toString());
				System.out.println("NUMERO DEI QUESTIONARI: " + DettaglioQuestionario.size());
				
				//Aggiunta degli attributi alla sessione
				request.getSession().removeAttribute("Materie");
        request.getSession().setAttribute("Materie", Materie);
				request.getSession().removeAttribute("MediaGenerale");
        request.getSession().setAttribute("MediaGenerale", MediaGenerale);
				request.getSession().removeAttribute("Media");
				request.getSession().setAttribute("Media", Media);
				request.getSession().removeAttribute("DettaglioQuestionario");
				request.getSession().setAttribute("DettaglioQuestionario", DettaglioQuestionario);
				request.getSession().removeAttribute("IdQuestionari");
				request.getSession().setAttribute("IdQuestionari", Id);		
        request.getSession().removeAttribute("email");
        request.getSession().setAttribute("email", email);
        request.getSession().removeAttribute("password");
        request.getSession().setAttribute("password", password);
        request.getSession().removeAttribute("TipoUtente");
        request.getSession().setAttribute("TipoUtente", "Studente");
        request.getSession().removeAttribute("InfoUtente");
        request.getSession().setAttribute("InfoUtente", InfoUtente);
        request.getSession().removeAttribute("idQuestionari");
        request.getSession().setAttribute("idQuestionari", idQuestionariCalendario);
        request.getSession().removeAttribute("Login");
        request.getSession().setAttribute("Login", Login);
        
				response.sendRedirect("Studente/Dashboard.jsp");
			}else{
				//Errore, utente non trovato
			  request.getSession().removeAttribute("ErroreLogin");
        request.getSession().setAttribute("ErroreLogin", true);
        
				response.sendRedirect("Login/Login.jsp");
			}//endif
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
