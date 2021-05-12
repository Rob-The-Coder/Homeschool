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

/**
 * Servlet implementation class SvolgiQuestionario
 */
@WebServlet("/SvolgiQuestionario")
public class SvolgiQuestionario extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SvolgiQuestionario() {
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		DBManagement DBManager = new DBManagement();
		
		//Salva i valori di sessione in variabili
		
		Map<String, String> InfoQuestionario = new HashMap<String, String>();
		InfoQuestionario = (Map<String, String>) request.getSession().getAttribute("InfoQuestionario");

		ArrayList<DomandaBean> DomandeQuestionario = new ArrayList<DomandaBean>();
		DomandeQuestionario = (ArrayList<DomandaBean>) request.getSession().getAttribute("DomandeQuestionario");

		ArrayList<ArrayList<RispostaBean>> RisposteDomande = new ArrayList<ArrayList<RispostaBean>>();
		RisposteDomande = (ArrayList<ArrayList<RispostaBean>>) request.getSession().getAttribute("RisposteDomande");
		
		ArrayList<ArrayList<String>> RisposteSelezionate = new ArrayList<ArrayList<String>>(); 
		
		
		System.out.println(DomandeQuestionario.size());
		
		int NumeroRisposta = 0;

		//recupera l'id dello studente
		String email = (String) request.getSession().getAttribute("email");
		String password = (String) request.getSession().getAttribute("password");
		System.out.println("email: "+email);
		System.out.println("password: "+password);
		try {
			int idStudente = DBManager.selectIdStudente(email, password);
			System.out.println("IDSTUDENTE: "+idStudente);
			
			for(int i=0;i<DomandeQuestionario.size();i++) {
				
				System.out.print(DomandeQuestionario.get(i).getTipoDomanda()+" ");
				
				//Determina il tipo di domanda
				if(DomandeQuestionario.get(i).getTipoDomanda().compareTo("Radio")==0) { //Scelta multipla con una risposta giusta
					 
					String nomeParametro = "Domanda"+i;
					System.out.print(nomeParametro + ": ");
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
							
						}else {

							if (RisposteDomande.get(i).get(j).getTestoRisposta().compareTo(RisSelezionata) == 0) {// risposta selezionata
								
								DBManager.insertDettaglioRisposta(idRisposta, idStudente, "TRUE");

							} else {// non è la risposta selezionata

								DBManager.insertDettaglioRisposta(idRisposta, idStudente, "FALSE");

							} // endif

						}//endif
												
						System.out.println("IDRISPOSTA: " +"");
						
					}//endfor
					NumeroRisposta+=RisposteDomande.get(i).size();
					
				}else if (DomandeQuestionario.get(i).getTipoDomanda().compareTo("Textarea")==0) { //Domanda aperta
					
					String nomeParametro = "Risposta"+NumeroRisposta;
					System.out.print(nomeParametro + ": ");
					ArrayList<String> appSelezionate= new ArrayList<String>();
					
					String RisInserita = request.getParameter(nomeParametro).trim();
					
					appSelezionate.add(RisInserita);
					System.out.print(appSelezionate +" \n");
									
					RisposteSelezionate.add(appSelezionate);
					
					int idRisposta = DBManager.selectIdRisposta(RisposteDomande.get(i).get(0).getTestoRisposta(),
							DomandeQuestionario.get(i).getTestoDomanda());
					
					if(RisInserita.compareTo("")==0) {//l'utente non ha inserito nulla
						
						DBManager.insertDettaglioRisposta(idRisposta, idStudente, "FALSE");
						
					}else {
						
						if (RisposteDomande.get(i).get(0).getTestoRisposta().compareTo(RisInserita) == 0) {// risposta selezionata
							
							DBManager.insertDettaglioRisposta(idRisposta, idStudente, "TRUE");

						} else {// non è la risposta selezionata

							DBManager.insertDettaglioRisposta(idRisposta, idStudente, "FALSE");

						} // endif
						
					}//endif
					
					NumeroRisposta+=RisposteDomande.get(i).size();
					
					
					
					
				}else if (DomandeQuestionario.get(i).getTipoDomanda().compareTo("Checkbox")==0) { //Scelta multipla con più di una risposta giusta
					
					
					System.out.println(RisposteDomande.get(i).size());
					ArrayList<String> appSelezionate= new ArrayList<String>();
					
					for (int j = 0; j < RisposteDomande.get(i).size(); j++) {
						
						String nomeParametro = "Risposta"+NumeroRisposta;
						System.out.print(nomeParametro + ": ");
						String app = request.getParameter(nomeParametro);
						System.out.print(request.getParameter(nomeParametro) +" \n");

						int idRisposta = DBManager.selectIdRisposta(RisposteDomande.get(i).get(j).getTestoRisposta(),
																	DomandeQuestionario.get(i).getTestoDomanda());
						
						if(request.getParameter(nomeParametro) == null) {//risposta non selezionata
							
							
							DBManager.insertDettaglioRisposta(idRisposta, idStudente, "FALSE");
							
						}else{//risposta selezionata
							
							
							appSelezionate.add(request.getParameter(nomeParametro));
							DBManager.insertDettaglioRisposta(idRisposta, idStudente, "TRUE");
							
						}//endif
						
						NumeroRisposta++;
						
					}//endfor
					
					//l'utente non ha selezionato alcuna risposta, ma per evitare che l'array risulti vuoto ci aggiungiamo un 'null'
					if(appSelezionate.size()==0) {
						
						appSelezionate.add(null);
						
					}//endif

					RisposteSelezionate.add(appSelezionate);
					

				}//endif
				
				
			}//enfor
			
			System.out.println(RisposteSelezionate.toString());
			
			request.getSession().removeAttribute("RisposteSelezionate");
			request.getSession().setAttribute("RisposteSelezionate", RisposteSelezionate);
			
			response.sendRedirect("Studente/Dashboard.jsp");	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		


		
		
	}


}
