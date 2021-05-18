package Servlets;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Beans.*;
import DB.DBManagement;

@WebServlet("/SvolgiQuestionario")
public class SvolgiQuestionario extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SvolgiQuestionario() {
		super();
		
	}

	
	public void init(ServletConfig config) throws ServletException {
		
	}

	
	public void destroy() {
		
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DBManagement DBManager = new DBManagement();
		//Salva i valori di sessione in variabili
		Map<String, String> InfoQuestionario = new HashMap<String, String>();
		InfoQuestionario = (Map<String, String>) request.getSession().getAttribute("InfoQuestionario");
		ArrayList<DomandaBean> DomandeQuestionario = new ArrayList<DomandaBean>();
		DomandeQuestionario = (ArrayList<DomandaBean>) request.getSession().getAttribute("DomandeQuestionario");
		ArrayList<ArrayList<RispostaBean>> RisposteDomande = new ArrayList<ArrayList<RispostaBean>>();
		RisposteDomande = (ArrayList<ArrayList<RispostaBean>>) request.getSession().getAttribute("RisposteDomande");
		ArrayList<ArrayList<String>> RisposteSelezionate = new ArrayList<ArrayList<String>>(); 
		int NumeroRisposta = 0;

		//recupera l'id dello studente
		String email = (String) request.getSession().getAttribute("email");
		String password = (String) request.getSession().getAttribute("password");
		try{
			int idStudente = DBManager.selectIdStudente(email, password);			
			for(int i=0;i<DomandeQuestionario.size();i++) {
				//Determina il tipo di domanda
				if(DomandeQuestionario.get(i).getTipoDomanda().compareTo("Radio")==0) { //Scelta multipla con una risposta giusta 
					String nomeParametro = "Domanda"+i;
					ArrayList<String> appSelezionate= new ArrayList<String>();
					
					String RisSelezionata = request.getParameter(nomeParametro);
					appSelezionate.add(RisSelezionata);
					System.out.print(appSelezionate +" \n");
									
					RisposteSelezionate.add(appSelezionate);
				 for(int j = 0; j < RisposteDomande.get(i).size();j++) {
						int idRisposta = DBManager.selectIdRisposta(RisposteDomande.get(i).get(j).getTestoRisposta(), 
						    DomandeQuestionario.get(i).getTestoDomanda());
						if (RisSelezionata == null) {//compareTo(null) da errore quindi è necessario questo if
							DBManager.insertDettaglioRisposta(idRisposta, idStudente, "FALSE");	
						}else{
							if (RisposteDomande.get(i).get(j).getTestoRisposta().compareTo(RisSelezionata) == 0) {// risposta selezionata
								DBManager.insertDettaglioRisposta(idRisposta, idStudente, "TRUE");
							}else{// non è la risposta selezionata
								DBManager.insertDettaglioRisposta(idRisposta, idStudente, "FALSE");
							} // endif
						}//endif
					}//endfor
					NumeroRisposta+=RisposteDomande.get(i).size();
				}else if(DomandeQuestionario.get(i).getTipoDomanda().compareTo("Textarea")==0){ //Domanda aperta	
					String nomeParametro = "Risposta"+NumeroRisposta;
					ArrayList<String> appSelezionate= new ArrayList<String>();		
					String RisInserita = request.getParameter(nomeParametro).trim();		
					appSelezionate.add(RisInserita);
									
					RisposteSelezionate.add(appSelezionate);		
					int idRisposta = DBManager.selectIdRisposta(RisposteDomande.get(i).get(0).getTestoRisposta(),
							DomandeQuestionario.get(i).getTestoDomanda());
			  if(RisInserita.compareTo("")==0) {//l'utente non ha inserito nulla
						DBManager.insertDettaglioRisposta(idRisposta, idStudente, "FALSE");	
					}else{
						if(RisposteDomande.get(i).get(0).getTestoRisposta().compareTo(RisInserita) == 0) {// risposta selezionat		
							DBManager.insertDettaglioRisposta(idRisposta, idStudente, "TRUE");
						}else{// non è la risposta selezionata
							DBManager.insertDettaglioRisposta(idRisposta, idStudente, "FALSE");
						}//endif
					}//endif
					NumeroRisposta+=RisposteDomande.get(i).size();
				}else if(DomandeQuestionario.get(i).getTipoDomanda().compareTo("Checkbox")==0) { //Scelta multipla con più di una risposta giusta
				  ArrayList<String> appSelezionate= new ArrayList<String>();
					 for(int j = 0; j < RisposteDomande.get(i).size(); j++){
 						
 						String nomeParametro = "Risposta"+NumeroRisposta;
 						String app = request.getParameter(nomeParametro);
 				
 						int idRisposta = DBManager.selectIdRisposta(RisposteDomande.get(i).get(j).getTestoRisposta(),
 																	DomandeQuestionario.get(i).getTestoDomanda());
 						
						if(request.getParameter(nomeParametro) == null){//risposta non selezionata
							DBManager.insertDettaglioRisposta(idRisposta, idStudente, "FALSE");
						}else{//risposta selezionata
						appSelezionate.add(request.getParameter(nomeParametro));
							DBManager.insertDettaglioRisposta(idRisposta, idStudente, "TRUE");
						}//endif		
						NumeroRisposta++;		
					}//endfor	
					//l'utente non ha selezionato alcuna risposta, ma per evitare che l'array risulti vuoto ci aggiungiamo un 'null'
					if(appSelezionate.size()==0){	
						appSelezionate.add(null);					
					}//endif
					RisposteSelezionate.add(appSelezionate);
				}//endif
			}//enfor
			request.getSession().removeAttribute("RisposteSelezionate");
			request.getSession().setAttribute("RisposteSelezionate", RisposteSelezionate);
			
			response.sendRedirect("Studente/Dashboard.jsp");	
		}catch(SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
