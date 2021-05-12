package Servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Beans.QuestionarioBean;
import DB.DBManagement;

/**
 * Servlet implementation class CreaQuestionari
 */
@WebServlet("/CreaQuestionari")
public class CreaQuestionari extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreaQuestionari() {
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
	  ArrayList<String> Domande=new ArrayList<String>();
	  ArrayList<ArrayList<String>> Risposte=new ArrayList<ArrayList<String>>();
	  ArrayList<ArrayList<String>> Punteggi=new ArrayList<ArrayList<String>>();
	  ArrayList<ArrayList<String>> RisposteGiuste=new ArrayList<ArrayList<String>>();
	  ArrayList<String> AppRisposte=new ArrayList<String>();
	  ArrayList<String> AppPunteggi=new ArrayList<String>();
	  ArrayList<String> AppRisposteGiuste=new ArrayList<String>();
	  ArrayList<String> TipoDomande=new ArrayList<String>();
	  ArrayList<String> ImmaginiDomande=new ArrayList<String>();
	  String NomeQuestionario=request.getParameter("NomeQuestionario");
	  String Descrizione=request.getParameter("Descrizione");
	  String DataCreazione=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	  String TempoMassimo=request.getParameter("TempoMassimo");
	  String Sufficienza=request.getParameter("Sufficienza");
	  String DataQuestionario="";
	  String OrarioMinimoInizio="";
	  String ImgQ=request.getParameter("ImgQ");

	  String App=request.getParameter("DataOrario");
	  String[] split = App.split("T");
	  DataQuestionario=split[0];
	  OrarioMinimoInizio=split[1];
	  
	  //Oltre ai parametri già noti ricavo gli altri
	  Boolean TrovatoDomanda=false;
	  int i=1;
    while(!TrovatoDomanda){
      AppPunteggi=new ArrayList<String>();
      String Domanda=request.getParameter("Domanda"+i);
      if(Domanda!=null){
        Domande.add(Domanda);
        TipoDomande.add(request.getParameter("Menu"+i));
        AppPunteggi.add(request.getParameter("PunteggioGiusto"+i));
        AppPunteggi.add(request.getParameter("PunteggioSbagliato"+i));
        AppPunteggi.add(request.getParameter("PunteggioVuoto"+i));
        ImmaginiDomande.add(request.getParameter("ImmagineDomanda"+i));
        //Risposte
        AppRisposteGiuste=new ArrayList<String>();
        Boolean TrovatoRisposta=false;
        int j=1;
        if(request.getParameter("Menu"+i).equals("Radio")){
          while(!TrovatoRisposta){
            //Radio
            String Radio=request.getParameter("Risposta"+i+"."+j);
            if(Radio!=null){
              AppRisposte.add(Radio);
              j=j+1;
            }else{
              //finito le risposte
              TrovatoRisposta=true;
              Risposte.add(AppRisposte);
              AppRisposte=new ArrayList<String>();
            }//endif
          }//endwhile
          AppRisposteGiuste.add(request.getParameter(""+i));
        }else if(request.getParameter("Menu"+i).equals("Checkbox")){
          while(!TrovatoRisposta){
            //Checkbox
            String[] Checkbox=request.getParameterValues("Risposta"+i+"."+j);
            if(Checkbox!=null){
              AppRisposte.add(Checkbox[Checkbox.length-1]);
              if(Checkbox.length>1){
                AppRisposteGiuste.add(Checkbox[0]);
              }//endif
              j=j+1;
            }else{
              //finito le risposte
              TrovatoRisposta=true;
              Risposte.add(AppRisposte);
              AppRisposte=new ArrayList<String>();
            }//endif
          }//endwhile
        }else{
          AppRisposteGiuste.add("1");
          AppRisposte.add(request.getParameter("Risposta"+i+".1"));
          Risposte.add(AppRisposte);
          TrovatoRisposta=true;
          AppRisposte=new ArrayList<String>();
        }//endif
        Punteggi.add(AppPunteggi);
        RisposteGiuste.add(AppRisposteGiuste);
        i=i+1;
      }else{
        //finito le domande
        TrovatoDomanda=true;
      }//endif
    }//endwhile
    try{
      String IdInsegnante=DB.selectIdInsegnante(email, password);
      String IdMateria=DB.selectIdMateria(request.getParameter("Materia"));
      String idQuestionario=DB.selectIdInserimentoQuestionario();
      String CodiceAccesso="";
      
      char[] chars="abcdefghijklmnopqrstuvwxyz".toCharArray();
      StringBuffer Code=new StringBuffer();
      String subcode="";
      for(int j=0; j<2; j=j+1) {
        subcode="";
        do{
          int max=10000000;
          int random=(int) (Math.random()*max);
          StringBuffer sb=new StringBuffer();
          while (random>0) {
              sb.append(chars[random % chars.length]);
              random /= chars.length;
          }//endwhile
          subcode=sb.toString();
        }while(subcode.length()!=5);
        Code.append(subcode);
      }//endfor
      Code.insert(3, "-");
      Code.insert(8, "-");
      CodiceAccesso=Code.toString();
      
      //Pre primo inserimento questionario
      DB.insertQuestionario(idQuestionario, IdInsegnante, IdMateria, NomeQuestionario, Descrizione, DataCreazione, TempoMassimo, Sufficienza, CodiceAccesso, DataQuestionario, OrarioMinimoInizio, ImgQ);
      
      //Poi inserimento domande e relative risposte
      for(i=0; i<Domande.size(); i=i+1){
        String idDomanda=DB.selectIdInserimentoDomanda();
        DB.insertDomanda(idDomanda, idQuestionario, Punteggi.get(i).get(0), Punteggi.get(i).get(1), Punteggi.get(i).get(2), Domande.get(i), TipoDomande.get(i), ImmaginiDomande.get(i));
        for(int j=0; j<Risposte.get(i).size(); j=j+1){
          if(RisposteGiuste.get(i).indexOf(String.valueOf(j+1))!=-1){
            DB.insertRisposta(idDomanda, Risposte.get(i).get(j), 1);
          }else{
            DB.insertRisposta(idDomanda, Risposte.get(i).get(j), 0);
          }//endif
        }//endfor
      }//endfor
      
      ArrayList<QuestionarioBean> Questionari=new ArrayList<QuestionarioBean>();
      Questionari=DB.selectQuestionari(email, password);
      
      request.getSession().removeAttribute("Questionari");
      request.getSession().setAttribute("Questionari", Questionari);
      
      response.sendRedirect("Insegnante/DashboardQuestionari.jsp");
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
