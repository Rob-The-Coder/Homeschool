package Servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
import Beans.RispostaBean;
import DB.DBManagement;

/**
 * Servlet implementation class DashboardStudenti
 */
@WebServlet("/DashboardStudenti")
public class DashboardStudenti extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardStudenti() {
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
		DBManagement DBManager = new DBManagement();
		
		String TipoPost = request.getParameter("TipoPost");
		String email = (String) request.getSession().getAttribute("email");
		String password = (String) request.getSession().getAttribute("password");
		
		
		if(TipoPost.compareTo("SvolgiQuestionario")==0) {
		
			String CodiceQuestionario = request.getParameter("idQuestionario");
			ArrayList<DomandaBean> DomandeQuestionario = new ArrayList<DomandaBean>();
			ArrayList<ArrayList<RispostaBean>> RisposteDomande = new ArrayList<ArrayList<RispostaBean>>();
			Map <String,String> InfoQuestionario = new HashMap<String,String>();
			ArrayList<Integer> nRisposteGiuste = new ArrayList<Integer>();
						

			try {
				
				DomandeQuestionario = DBManager.selectDomandeQuestionario(CodiceQuestionario);
				Collections.shuffle(DomandeQuestionario);
				System.out.println(CodiceQuestionario);
				
				
				for(int i=0;i<DomandeQuestionario.size();i++) {
					System.out.println("Domanda numero "+i+":");
					System.out.println(DomandeQuestionario.get(i).getTestoDomanda()+" ");
					System.out.println(DomandeQuestionario.get(i).getPunteggioGiusto()+" ");
					System.out.println(DomandeQuestionario.get(i).getPunteggioSbagliato()+" ");
					System.out.println(DomandeQuestionario.get(i).getPunteggioVuoto()+" ");
					System.out.println(DomandeQuestionario.get(i).getTipoDomanda()+" ");
					
					System.out.println("Queste sono le sue risposte :");
					
					RisposteDomande.add(DBManager.selectRisposteDomanda(DomandeQuestionario.get(i).getTestoDomanda()));
					
					int appNumRis = 0;
									
					for(int j=0;j<RisposteDomande.get(i).size();j++) {
								
						if(RisposteDomande.get(i).get(j).getRispostaGiusta()){
							
							appNumRis++;
							
						}//endif

						System.out.println("Testo: "+ RisposteDomande.get(i).get(j).getTestoRisposta() +" Giusta: "
						+ RisposteDomande.get(i).get(j).getRispostaGiusta());
						System.out.println();
							
					}//endfor
					
					nRisposteGiuste.add(appNumRis);
					
				}//endfor
				
				System.out.println("NUMERO DI RISPOSTE GIUSTE: " + nRisposteGiuste.toString());
				
				InfoQuestionario = DBManager.selectInfoQuestionario(CodiceQuestionario);
				System.out.println(InfoQuestionario.toString());
					
				
				request.getSession().removeAttribute("DomandeQuestionario");
				request.getSession().setAttribute("DomandeQuestionario", DomandeQuestionario);
				
				request.getSession().removeAttribute("RisposteDomande");
				request.getSession().setAttribute("RisposteDomande", RisposteDomande);
				
				request.getSession().removeAttribute("InfoQuestionario");
				request.getSession().setAttribute("InfoQuestionario", InfoQuestionario);
				
				request.getSession().removeAttribute("nRisposteGiuste");
				request.getSession().setAttribute("nRisposteGiuste", nRisposteGiuste);
				
				response.sendRedirect("Studente/SvolgiQuestionario.jsp");	
				
			} catch(SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("ERROR:" + e.getErrorCode() + ":" + e.getMessage());
				e.printStackTrace();
			}//endtry
			
			
		}else if (TipoPost.compareTo("VediQuestionario")==0) {
			
			int idQuestionario = Integer.valueOf(request.getParameter("IdQuestionario"));
			ArrayList<ArrayList<Boolean>> RisposteSelezionate = new ArrayList<ArrayList<Boolean>>();
			boolean appSelezionata;
			
			try {
				
				String CodiceQuestionario = DBManager.selectCodiceQuestionario(idQuestionario);
				System.out.println("CODICE QUESTIONARIO " + CodiceQuestionario);
				
				int VotoQuestionario = DBManager.selectVotoQuestionario(email, password, idQuestionario, "%", "%", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				

				ArrayList<DomandaBean> DomandeQuestionario = new ArrayList<DomandaBean>();
				ArrayList<ArrayList<RispostaBean>> RisposteDomande = new ArrayList<ArrayList<RispostaBean>>();
				Map<String, String> InfoQuestionario = new HashMap<String, String>();

				DomandeQuestionario = DBManager.selectDomandeQuestionario(CodiceQuestionario);
				System.out.println(CodiceQuestionario);

				
				
				for (int i = 0; i < DomandeQuestionario.size(); i++) {
					System.out.println("Domanda numero " + i + ":");
					System.out.println(DomandeQuestionario.get(i).getTestoDomanda() + " ");
					System.out.println(DomandeQuestionario.get(i).getPunteggioGiusto() + " ");
					System.out.println(DomandeQuestionario.get(i).getPunteggioSbagliato() + " ");
					System.out.println(DomandeQuestionario.get(i).getPunteggioVuoto() + " ");
					System.out.println(DomandeQuestionario.get(i).getTipoDomanda() + " ");

					System.out.println("Queste sono le sue risposte :");

					RisposteDomande.add(DBManager.selectRisposteDomanda(DomandeQuestionario.get(i).getTestoDomanda()));
					
					ArrayList<Boolean> arraySelezionata = new ArrayList<Boolean>();

					for (int j = 0; j < RisposteDomande.get(i).size(); j++) {

						System.out.println("Testo: " + RisposteDomande.get(i).get(j).getTestoRisposta() + " Giusta: "
								+ RisposteDomande.get(i).get(j).getRispostaGiusta());
						System.out.println();

						arraySelezionata.add(DBManager.selectDettaglioRisposta(CodiceQuestionario, DomandeQuestionario.get(i).getTestoDomanda(), 
								RisposteDomande.get(i).get(j).getTestoRisposta(), email));
						
						
					} // endfor
					
					System.out.println(arraySelezionata.toString());
					RisposteSelezionate.add(arraySelezionata);

				} // endfor

				InfoQuestionario = DBManager.selectInfoQuestionario(CodiceQuestionario);
				System.out.println(InfoQuestionario.toString());

				request.getSession().removeAttribute("DomandeQuestionario");
				request.getSession().setAttribute("DomandeQuestionario", DomandeQuestionario);

				request.getSession().removeAttribute("RisposteDomande");
				request.getSession().setAttribute("RisposteDomande", RisposteDomande);

				request.getSession().removeAttribute("InfoQuestionario");
				request.getSession().setAttribute("InfoQuestionario", InfoQuestionario);
				
				request.getSession().removeAttribute("VotoQuestionario");
				request.getSession().setAttribute("VotoQuestionario", VotoQuestionario);
				
				request.getSession().removeAttribute("RisposteSelezionate");
				request.getSession().setAttribute("RisposteSelezionate", RisposteSelezionate);					
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//endtry
			
			
			response.sendRedirect("Studente/Questionario.jsp");	
			
			
		}//endif
		

		
			
	}

}
