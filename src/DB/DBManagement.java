package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Beans.*;

public class DBManagement {
	/****************************************************************/
	// ATTRIBUTI
	/****************************************************************/
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/homeschool";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "";
	/****************************************************************/
	// METODI
	/****************************************************************/
	private static Connection getDBConnection() throws Exception {
		System.out.println("-------MYSQL JDBC Connection----------");
		Connection dbConnection = null;
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR: MySQL JDBC Driver not found!!!");
			throw new Exception(e.getMessage());
		}
		try {
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			System.out.println("SQL Connection to homeschool database established!");
		} catch (SQLException e) {
			System.out.println("Connection to homeschool database failed");
			throw new SQLException(e.getErrorCode() + ":" + e.getMessage());
		} // endtry
		return dbConnection;
	}
	/****************************************************************/
	// QUERY
	/****************************************************************/
	/****************************************************************/
	// SELECT LOGIN, HA IL COMPITO PRINCIPALE DI EFFETTUARE IL LOGIN
	// DI STUDENTI ED INSEGNANTI
	// PER PRIMA COSA TENTA IL LOGIN DEGLI STUDENTI, SE DOVESSE ANDARE
	// A BUON FINE -> return 1
	// IN CASO CONTRARIO, TENTA IL LOGIN NEGLI INSEGNANTI, SE DOVESSE
	// ANDARE A BUON FINE -> return 2
	// IN ULTIMA TESI, RITORNA ALLA PAGINA DI LOGIN  
	/****************************************************************/
	public int selectLogin(String Email, String Password) throws SQLException {
		Statement stmt = null;
		Connection conn = null;
		int Log = 0;
		try {
			conn = getDBConnection();
			stmt = conn.createStatement();
			String select = "SELECT COUNT(idStudente) AS Log FROM Studente WHERE Email LIKE '" + Email
					+ "' AND Password LIKE '" + Password + "'";
			ResultSet Risultati = stmt.executeQuery(select);
			while (Risultati.next()) {
				Log = Risultati.getInt("Log");
			} // endwhile
			if (Log == 1) {
				// è uno studente
				return Log;
			} else {
				select = "SELECT COUNT(idInsegnante) AS Log FROM Insegnante WHERE Email LIKE '" + Email
						+ "' AND Password LIKE '" + Password + "'";
				Risultati = stmt.executeQuery(select);
				while (Risultati.next()) {
					Log = Risultati.getInt("Log");
				} // endwhile
				if (Log == 1) {
					// è un'insegnante
					Log = 2;
					return Log;
				} else {
					// Non è nessuno
					return Log;
				} // endif
			} // endif
		} catch (SQLException sqle) {
			System.out.println("SELECT ERROR");
			throw new SQLException(sqle.getErrorCode() + ":" + sqle.getMessage());
		} catch (Exception err) {
			System.out.println("GENERIC ERROR");
			throw new SQLException(err.getMessage());
		} finally {
			if (stmt != null) {
				stmt.close();
			} // endif
			if (conn != null) {
				conn.close();
			} // endif
		} // endfinally
	}
	/****************************************************************/
	// SELECT Voto Questionario, HA IL COMPITO DI FORNIRE IL VOTO
	// RELATIVO AD UN QUESTIONARIO DI UNO STUDENTE SPECIFICO.
	// IL FUNZIONAMENTO DI QUESTA QUERY È IL SEGUENTE:
	// OTTIENE TUTTI GLI ID RELATIVI ALLE DOMANDE DEL QUESTIONARIO.
	// PER OGNI DOMANDA, CALCOLA SE L'UTENTE HA RISPOSTO ALLA DOMANDA
	// SE NON HA RISPOSTO VIENE SALVATO IL VALORE "PunteggioVuoto" DELLA
	// DOMANDA SPECIFICA.
	// AL TERMINE DEL CICLO DELLE DOMANDE VIENE ESEGUITA UNA QUERY
	// ANNIDATA PER CALCOLARE IL RESTO DEL PUNTEGGIO DEL QUESTIONARIO
	// INFINE, IL VOTO FINALE VIENE CALCOLATO CON LA FORMULA:
	// Voto=PunteggioOttenuto*60/(PunteggioMassimo*Sufficienza/100)  
	/****************************************************************/
	public int selectVotoQuestionario(String Email, String Password, int idQuestionario, String Materia, String DataInizio, String DataFine) throws SQLException {
		Statement stmt = null;
		Connection conn = null;
		ArrayList<Integer> idDomanda=new ArrayList<Integer>();
		float Voto=0;
		try{
			conn=getDBConnection();
			stmt=conn.createStatement();
			String select="SELECT DISTINCT idDomanda FROM questionario\r\n"
					+ "INNER JOIN domanda ON idfQuestionario=idQuestionario\r\n"
					+ "INNER JOIN risposta ON idDomanda=idfDomanda\r\n"
					+ "INNER JOIN dettagliorisposta ON idRisposta=idfRisposta\r\n"
					+ "WHERE idQuestionario='" + idQuestionario + "'";
			ResultSet Ris=stmt.executeQuery(select);
			while(Ris.next()){
				idDomanda.add(Ris.getInt("idDomanda"));
			} // endwhile
			
			for(int i=0; i<idDomanda.size(); i=i+1){
				int App=0;
				select="SELECT SUM(RispostaSelezionata) AS App FROM Studente\r\n"
						+ "INNER JOIN dettagliorisposta ON idStudente=idfStudente\r\n"
						+ "INNER JOIN risposta ON idfRisposta=idRisposta\r\n"
						+ "INNER JOIN Domanda ON idDomanda=idfDomanda\r\n"
						+ "INNER JOIN questionario ON idfQuestionario=idQuestionario\r\n"
						+ "INNER JOIN materia ON idMateria=idfMateria\r\n"
						+ "WHERE idDomanda='" + idDomanda.get(i) + "'\r\n"
						+ "AND studente.Email='" + Email + "'\r\n"
						+ "AND studente.Password='" + Password + "'\r\n"
						+ "AND NomeMateria LIKE '" + Materia + "'\r\n"
						+ "AND DataQuestionario BETWEEN '" + DataInizio + "' AND '" + DataFine + "'";
				Ris=stmt.executeQuery(select);
				while(Ris.next()){
					App=Ris.getInt("App");
				} // endwhile

			  if(App==0){
			    int PV=0;
			  	select="SELECT PunteggioVuoto as PV FROM questionario\r\n"
			    		+ "	INNER JOIN domanda ON idfQuestionario=idQuestionario\r\n"
			    		+ " WHERE idDomanda='" + idDomanda.get(i) + "'"
			    		+ " AND domanda.TipoDomanda NOT LIKE 'Textarea'";
			    Ris=stmt.executeQuery(select);
					while(Ris.next()){
						PV=Ris.getInt("PV");
					} // endwhile
			    Voto=Voto+PV;
			  }//endif
			}//endfor
			
			System.out.println(idQuestionario+"   "+Materia +"   "+Voto);
			
			select="SELECT COALESCE((SELECT SUM(VotoTot.Voto) FROM(\r\n"
					+ "    SELECT SUM(PunteggioGiusto) AS Voto, idfQuestionario FROM Studente\r\n"
					+ "    INNER JOIN DettaglioRisposta ON idStudente=idfStudente\r\n"
					+ "    INNER JOIN Risposta ON idfRisposta=idRisposta\r\n"
					+ "    INNER JOIN Domanda ON idfDomanda=idDomanda\r\n"
					+ "    INNER JOIN Questionario ON idfQuestionario=idQuestionario\r\n"
					+ "    INNER JOIN Materia ON idfMateria=idMateria\r\n"
					+ "    WHERE DettaglioRisposta.RispostaSelezionata=1\r\n"
					+ "    AND RispostaGiusta=1\r\n"
					+ "    AND Studente.email LIKE '" + Email + "'\r\n"
					+ "    AND Studente.password LIKE '" + Password + "'\r\n"
					+ "    AND idQuestionario='" + idQuestionario + "'\r\n"
					+ "    AND NomeMateria LIKE '" + Materia + "'\r\n"
					+ "    AND DataQuestionario BETWEEN '" + DataInizio + "' AND '" + DataFine + "'\r\n"
					+ "    union\r\n"
					+ "    SELECT SUM(PunteggioSbagliato) AS Voto, idfQuestionario FROM Studente\r\n"
					+ "    INNER JOIN DettaglioRisposta ON idStudente=idfStudente\r\n"
					+ "    INNER JOIN Risposta ON idfRisposta=idRisposta\r\n"
					+ "    INNER JOIN Domanda ON idfDomanda=idDomanda\r\n"
					+ "    INNER JOIN Questionario ON idfQuestionario=idQuestionario\r\n"
					+ "    INNER JOIN Materia ON idfMateria=idMateria\r\n"
					+ "    WHERE DettaglioRisposta.RispostaSelezionata=1\r\n"
					+ "    AND RispostaGiusta=0\r\n"
					+ "    AND Studente.email LIKE '" + Email + "'\r\n"
					+ "    AND Studente.password LIKE '" + Password + "'\r\n"
					+ "    AND idQuestionario='" + idQuestionario + "'\r\n"
					+ "    AND NomeMateria LIKE '" + Materia + "'\r\n"
					+ "    AND DataQuestionario BETWEEN '" + DataInizio + "' AND '" + DataFine + "'\r\n"
					+ ") AS VotoTot), 0) AS Punteggio";
			Ris=stmt.executeQuery(select);
			while(Ris.next()){
				Voto=Voto+Ris.getInt("Punteggio");
			} // endwhile

			//Punteggio massimo questionario, per ogni domanda se il tipo è checkbox somma il punteggio domanda per ogni
			//RispostaGiusta
			select="SELECT idDomanda, PunteggioGiusto, TipoDomanda, TestoDomanda FROM questionario INNER JOIN domanda ON idfQuestionario=idQuestionario INNER JOIN tipodomanda ON idfTipoDomanda=idTIpoDomanda WHERE idQuestionario='" + idQuestionario + "'";
			float PMax=0;
      Ris=stmt.executeQuery(select);
      while(Ris.next()){
        String TestoDomanda=Ris.getString("TestoDomanda");
        if(Ris.getString("TipoDomanda").equals("Checkbox")){
          ArrayList<RispostaBean> Risposte=new ArrayList<RispostaBean>();
          Risposte=selectRisposteDomanda(TestoDomanda);
          for(int i=0; i<Risposte.size(); i=i+1){
            if(Risposte.get(i).getRispostaGiusta()){
              PMax+=Ris.getFloat("PunteggioGiusto");
            }//endif
          }//endfor
        }else{
          PMax+=Ris.getFloat("PunteggioGiusto");
        }//endif
      }// endwhile
			
			select="SELECT Questionario.Sufficienza AS Sufficienza FROM Questionario WHERE idQuestionario='"+idQuestionario+"'";
			float Sufficienza=0;
			Ris=stmt.executeQuery(select);
			while(Ris.next()){
				Sufficienza=Ris.getFloat("Sufficienza");
			} // endwhile
			
			Voto=Math.round((Voto*60)/((PMax*Sufficienza)/100));
			return (int)Voto;
		} catch (SQLException sqle) {
			System.out.println("SELECT ERROR");
			throw new SQLException(sqle.getErrorCode() + ":" + sqle.getMessage());
		} catch (Exception err) {
			System.out.println("GENERIC ERROR");
			throw new SQLException(err.getMessage());
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}
	/****************************************************************/
	// SELECT Id Questionari, HA IL COMPITO PRINCIPALE DI FORNIRE
	// TUTTA LA LISTA DI ID RELATIVI AI QUESTIONARI SVOLTI DA UNO STUDENTE
	// SPECIFICO.
	/****************************************************************/
	public ArrayList<Integer> selectIdQuestionari(String Email, String Password, String Materia, String DataInizio, String DataFine) throws SQLException {
		Statement stmt = null;
		Connection conn = null;
		ArrayList<Integer> IdQuestionari=new ArrayList<Integer>();
		try {
			conn = getDBConnection();
			stmt = conn.createStatement();
			String select = "SELECT DISTINCT idQuestionario FROM studente\r\n"
					+ "INNER JOIN dettagliorisposta ON idStudente=idfStudente\r\n"
					+ "INNER JOIN risposta ON idfRisposta=idRisposta\r\n"
					+ "INNER JOIN domanda ON idDomanda=idfDomanda\r\n"
					+ "INNER JOIN Questionario ON idfQuestionario=idQuestionario\r\n"
					+ "INNER JOIN Materia ON idfMateria=idMateria\r\n"
					+ "WHERE studente.Email LIKE '" + Email + "'\r\n"
					+ "AND studente.Password LIKE '" + Password + "'"
					+ "AND NomeMateria LIKE '" + Materia + "'\r\n"
					+ "AND DataQuestionario BETWEEN '" + DataInizio + "' AND '" + DataFine + "'";
			ResultSet Ris = stmt.executeQuery(select);
			while (Ris.next()) {
				IdQuestionari.add(Ris.getInt("idQuestionario"));
			} // endwhile
			return IdQuestionari;
		} catch (SQLException sqle) {
			System.out.println("SELECT ERROR");
			throw new SQLException(sqle.getErrorCode() + ":" + sqle.getMessage());
		} catch (Exception err) {
			System.out.println("GENERIC ERROR");
			throw new SQLException(err.getMessage());
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}
	/****************************************************************/
  // SELECT MaterieStudente, HA IL COMPITO PRINCIPALE DI FORNIRE
  // TUTTA LA LISTA DI MATERIE DI CUO UNO STUDENTE HA SVOLTO
	// ALMENO UN QUESTIONARIO
  /****************************************************************/
  public ArrayList<MateriaBean> selectMaterieStudente(String email, String password) throws SQLException {
    Statement stmt = null;
    Connection conn = null;
    ArrayList<MateriaBean> Materie=new ArrayList<MateriaBean>();
    MateriaBean Materia=new MateriaBean();
    try {
      conn = getDBConnection();
      stmt = conn.createStatement();
      String select = "SELECT NomeMateria, Colore FROM studente\r\n"
          + "INNER JOIN classe ON Studente.idfClasse=Classe.idClasse\r\n"
          + "INNER JOIN dettaglioinsegnante ON Classe.idClasse=dettaglioinsegnante.idfClasse\r\n"
          + "INNER JOIN Materia ON idfMateria=idmateria\r\n"
          + "WHERE Studente.Email LIKE '"+email+"'\r\n"
          + "AND Studente.Password LIKE '"+password+"'";
      ResultSet Ris = stmt.executeQuery(select);
      Materia.setMateria("%");
      Materie.add(Materia);
      while (Ris.next()){
        Materia=new MateriaBean();
        Materia.setMateria(Ris.getString("NomeMateria"));
        Materia.setColore(Ris.getString("Colore"));
        Materie.add(Materia);
      } // endwhile
      return Materie;
    } catch (SQLException sqle) {
      System.out.println("SELECT ERROR");
      throw new SQLException(sqle.getErrorCode() + ":" + sqle.getMessage());
    } catch (Exception err) {
      System.out.println("GENERIC ERROR");
      throw new SQLException(err.getMessage());
    } finally {
      if (stmt != null) {
        stmt.close();
      }
      if (conn != null) {
        conn.close();
      }
    }
  }
  /****************************************************************/
  // SELECT Materie, Restituisce la lista delle materie
  /****************************************************************/
  public ArrayList<MateriaBean> selectMaterieInsegnante(String email, String password) throws SQLException {
    Statement stmt = null;
    Connection conn = null;
    ArrayList<MateriaBean> Materie=new ArrayList<MateriaBean>();
    MateriaBean Materia=new MateriaBean();
    try {
      conn = getDBConnection();
      stmt = conn.createStatement();
      String select = "SELECT DISTINCT NomeMateria FROM Materia\r\n"
          + "INNER JOIN dettaglioinsegnante ON idfMateria=idMateria\r\n"
          + "INNER JOIN insegnante ON idfInsegnante=idInsegnante\r\n"
          + "WHERE Insegnante.Email LIKE '" + email + "' AND Insegnante.Password LIKE '" + password + "'";
      ResultSet Ris = stmt.executeQuery(select);
      while (Ris.next()){
        Materia=new MateriaBean();
        Materia.setMateria(Ris.getString("NomeMateria"));
        Materie.add(Materia);
      } // endwhile
      return Materie;
    } catch (SQLException sqle) {
      System.out.println("SELECT ERROR");
      throw new SQLException(sqle.getErrorCode() + ":" + sqle.getMessage());
    } catch (Exception err) {
      System.out.println("GENERIC ERROR");
      throw new SQLException(err.getMessage());
    } finally {
      if (stmt != null) {
        stmt.close();
      }
      if (conn != null) {
        conn.close();
      }
    }
  }
	/****************************************************************/
  // SELECT IdSchedule, restituisce la lista di Id relativi ai
	// questionari svolti e ancora da svolgere riguardanti uno studente
  /****************************************************************/
  public ArrayList<String> selectIdSchedule(String Email, String Password) throws SQLException {
    Statement stmt = null;
    Connection conn = null;
    ArrayList<String> IdQuestionari=new ArrayList<String>();
    try {
      conn = getDBConnection();
      stmt = conn.createStatement();
      String select = "SELECT CodiceAccesso FROM insegnante\r\n"
          + "INNER JOIN questionario ON idfInsegnante=idInsegnante\r\n"
          + "WHERE idInsegnante IN (SELECT DISTINCT idInsegnante FROM insegnante\r\n"
          + "                      INNER JOIN questionario ON idInsegnante=idfInsegnante\r\n"
          + "                      INNER JOIN domanda ON idQuestionario=idfQuestionario\r\n"
          + "                      INNER JOIN risposta ON idDomanda=idfDomanda\r\n"
          + "                      INNER JOIN dettagliorisposta ON idRisposta=idfRisposta\r\n"
          + "                      INNER JOIN Studente ON idfStudente=idStudente\r\n"
          + "                      WHERE studente.Email LIKE '" + Email + "'\r\n"
          + "                      AND studente.Password LIKE '" + Password + "')";
      ResultSet Ris = stmt.executeQuery(select);
      while (Ris.next()) {
        IdQuestionari.add(Ris.getString("CodiceAccesso"));
      } // endwhile
      return IdQuestionari;
    } catch (SQLException sqle) {
      System.out.println("SELECT ERROR");
      throw new SQLException(sqle.getErrorCode() + ":" + sqle.getMessage());
    } catch (Exception err) {
      System.out.println("GENERIC ERROR");
      throw new SQLException(err.getMessage());
    } finally {
      if (stmt != null) {
        stmt.close();
      }
      if (conn != null) {
        conn.close();
      }
    }
  }
	/****************************************************************/
	// SELECT Dettagli Questionario, FORNISCE Nome, Cognome e Materia
	// DEL DOCENTE CHE HA CREATO IL QUESTIONARIO.
	// INOLTRE, RESISTUISCE ANCHE LA CLASSE DELLO STUDENTE E LA DATA
	// IN CUI IL QUESTIONARIO SI È SVOLTO.
	/****************************************************************/
	public ArrayList<String> selectDettagliQuestionario(String email, String password, int idQuestionario) throws SQLException {
		Statement stmt = null;
		Connection conn = null;
		ArrayList<String> Dettagli = new ArrayList<String>();

		try {
			conn = getDBConnection();
			stmt = conn.createStatement();
			String select = "SELECT DISTINCT insegnante.Nome, insegnante.Cognome, DataQuestionario, NomeMateria, NomeClasse FROM Classe\r\n"
	    + "INNER JOIN studente ON idfClasse=idClasse\r\n"
	    + "INNER JOIN dettagliorisposta ON idfStudente=idStudente\r\n"
	    + "INNER JOIN risposta ON idfRisposta=IdRisposta\r\n"
	    + "INNER JOIN domanda ON idfDomanda=idDomanda\r\n"
	    + "INNER JOIN questionario ON idfQuestionario=idQuestionario\r\n"
	    + "INNER JOIN Materia ON idfMateria=idMateria\r\n"
	    + "INNER JOIN insegnante ON idfInsegnante=idInsegnante\r\n"
			  + "WHERE Studente.Email LIKE '" + email + "'\r\n"
					+ "AND Studente.Password LIKE '" + password + "'"
					+ "AND idQuestionario='" + idQuestionario + "'";
			ResultSet Ris = stmt.executeQuery(select);
			while (Ris.next()) {
				Dettagli.add(Ris.getString("Nome"));
				Dettagli.add(Ris.getString("Cognome"));
				Dettagli.add(Ris.getString("NomeClasse"));
				Dettagli.add(Ris.getString("NomeMateria"));
				Dettagli.add(Ris.getString("DataQuestionario"));
			}//endwhile
			return Dettagli;
		} catch (SQLException sqle) {
			System.out.println("SELECT ERROR");
			throw new SQLException(sqle.getErrorCode() + ":" + sqle.getMessage());
		} catch (Exception err) {
			System.out.println("GENERIC ERROR");
			throw new SQLException(err.getMessage());
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}
	/****************************************************************/
	//selectClasseMateriaInsegnante, DATE IN INPUT LE CREDENZIALI
	// DELL'INSEGNANTE, RESTITUISCE TUTTE LE CLASSE E LE RISPETTIVE
	// MATERIE INSEGATE
	/****************************************************************/
	public ArrayList<ArrayList<String>> selectClasseMateriaInsegnante(String email, String password) throws SQLException {
		Statement stmt = null;
		Connection conn = null;
		ArrayList<ArrayList<String>> Risultato=new ArrayList<ArrayList<String>>();
		ArrayList<String> Parziale=new ArrayList<String>();

		try {
			conn = getDBConnection();
			stmt = conn.createStatement();
			String select = "SELECT DISTINCT NomeClasse, NomeMateria FROM Materia\r\n"
	    + "INNER JOIN dettaglioinsegnante ON idfMateria=idMateria\r\n"
	    + "INNER JOIN classe ON idfClasse=idClasse\r\n"
	    + "INNER JOIN insegnante ON idfInsegnante=idInsegnante\r\n"
					+ "WHERE Insegnante.Email LIKE '" + email + "' AND Insegnante.Password LIKE '" + password + "'";
			ResultSet Ris = stmt.executeQuery(select);
			while (Ris.next()) {
			  Parziale=new ArrayList<String>();
				Parziale.add(Ris.getString("NomeClasse"));
				Parziale.add(Ris.getString("NomeMateria"));
				Risultato.add(Parziale);
			}//endwhile
			return Risultato;
		} catch (SQLException sqle) {
			System.out.println("SELECT ERROR");
			throw new SQLException(sqle.getErrorCode() + ":" + sqle.getMessage());
		} catch (Exception err) {
			System.out.println("GENERIC ERROR");
			throw new SQLException(err.getMessage());
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}
	/****************************************************************/
  // SELECT Questionari, HA IL COMPITO PRINCIPALE DI FORNIRE
  // LA LISTA DI QUESTIONARI RELATIVI AD UN INSEGNANTE.  
  /****************************************************************/
  public ArrayList<QuestionarioBean> selectQuestionari(String email, String password) throws SQLException {
    Statement stmt = null;
    Connection conn = null;
    ArrayList<QuestionarioBean> Questionari=new ArrayList<QuestionarioBean>();
    try {
      conn = getDBConnection();
      stmt = conn.createStatement();
      String select = "SELECT CodiceAccesso, ImmagineQuestionario, NomeQuestionario, DataCreazione FROM insegnante \r\n"
          + "INNER JOIN questionario ON idfInsegnante=idInsegnante\r\n"
          + "WHERE insegnante.Email LIKE '" + email + "'\r\n"
          + "AND insegnante.Password LIKE '" + password + "'";
      ResultSet Ris = stmt.executeQuery(select);
      while (Ris.next()) {
        QuestionarioBean Questionario=new QuestionarioBean();
        Questionario.setCodiceAccesso(Ris.getString("CodiceAccesso"));
        Questionario.setImmagineQuestionario(Ris.getString("ImmagineQuestionario"));
        Questionario.setNomeQuestionario(Ris.getString("NomeQuestionario"));
        Questionario.setDataCreazione(Ris.getString("DataCreazione"));
        
        Questionari.add(Questionario);
      } // endwhile
      return Questionari;
    } catch (SQLException sqle) {
      System.out.println("SELECT ERROR");
      throw new SQLException(sqle.getErrorCode() + ":" + sqle.getMessage());
    } catch (Exception err) {
      System.out.println("GENERIC ERROR");
      throw new SQLException(err.getMessage());
    } finally {
      if (stmt != null) {
        stmt.close();
      }
      if (conn != null) {
        conn.close();
      }
    }
  }
  /****************************************************************/
  // SELECT idQuestionario, restituisce l'id di un questionario
  // dato il codice di accesso
  /****************************************************************/
  public String selectIdQuestionario(String CodiceAccesso) throws SQLException {
    Statement stmt = null;
    Connection conn = null;
    String id="";
    try {
      conn = getDBConnection();
      stmt = conn.createStatement();
      String select = "SELECT idQuestionario FROM questionario \r\n"
          + "WHERE questionario.CodiceAccesso LIKE '" + CodiceAccesso + "'\r\n";
      ResultSet Ris = stmt.executeQuery(select);
      while (Ris.next()) {
        id=Ris.getString("idQuestionario");
      } // endwhile
      return id;
    } catch (SQLException sqle) {
      System.out.println("SELECT ERROR");
      throw new SQLException(sqle.getErrorCode() + ":" + sqle.getMessage());
    } catch (Exception err) {
      System.out.println("GENERIC ERROR");
      throw new SQLException(err.getMessage());
    } finally {
      if (stmt != null) {
        stmt.close();
      }
      if (conn != null) {
        conn.close();
      }
    }
  }
	/****************************************************************/
  // SELECT IdMateria
	// RESTITUISCE L'IDMATERIA DATA LA MATERIA IN QUESTIONE
  /****************************************************************/
  public String selectIdMateria(String Materia) throws SQLException {
    Statement stmt = null;
    Connection conn = null;
    String id="";
    try{
      conn = getDBConnection();
      stmt = conn.createStatement();
      String select = "SELECT idMateria FROM materia\r\n"
      + "WHERE NomeMateria LIKE '" + Materia + "'";
      ResultSet Ris = stmt.executeQuery(select);
      while (Ris.next()) { 
        id=Ris.getString("idMateria");
      } // endwhile
      return id;
    } catch (SQLException sqle) {
      System.out.println("SELECT ERROR");
      throw new SQLException(sqle.getErrorCode() + ":" + sqle.getMessage());
    } catch (Exception err) {
      System.out.println("GENERIC ERROR");
      throw new SQLException(err.getMessage());
    } finally {
      if (stmt != null) {
        stmt.close();
      }
      if (conn != null) {
        conn.close();
      }
    }
  }
  /****************************************************************/
  // SELECT IdInsegnante
  // RESTITUISCE L'IDINSEGANTE DATE LA CREDENZIALI DI ACCESSO
  /****************************************************************/
  public String selectIdInsegnante(String email, String password) throws SQLException {
    Statement stmt = null;
    Connection conn = null;
    String id="";
    try{
      conn = getDBConnection();
      stmt = conn.createStatement();
      String select = "SELECT idInsegnante FROM Insegnante\r\n"
      + "WHERE Email LIKE '" + email + "' AND Password LIKE '" + password + "'";
      ResultSet Ris = stmt.executeQuery(select);
      while (Ris.next()) { 
        id=Ris.getString("idInsegnante");
      } // endwhile
      return id;
    } catch (SQLException sqle) {
      System.out.println("SELECT ERROR");
      throw new SQLException(sqle.getErrorCode() + ":" + sqle.getMessage());
    } catch (Exception err) {
      System.out.println("GENERIC ERROR");
      throw new SQLException(err.getMessage());
    } finally {
      if (stmt != null) {
        stmt.close();
      }
      if (conn != null) {
        conn.close();
      }
    }
  }
	/****************************************************************/
  // SELECT Studenti, HA IL COMPITO PRINCIPALE DI FORNIRE
  // UNA LISTA DI STUDENTI APPARTENENTI AD UNA CLASSE.  
  /****************************************************************/
  public ArrayList<StudenteBean> selectStudenti(String Classe) throws SQLException {
    Statement stmt = null;
    Connection conn = null;
    ArrayList<StudenteBean> Studenti=new ArrayList<StudenteBean>();
    try {
      conn = getDBConnection();
      stmt = conn.createStatement();
      String select = "SELECT DISTINCT studente.Cognome as Cognome, studente.nome AS Nome, studente.Email as Email, studente.Password as Password FROM Studente\r\n"
          + "INNER JOIN classe ON idclasse=idfClasse\r\n"
          + "AND Nomeclasse LIKE '" + Classe + "'\r\n"
          + "ORDER BY Cognome";
      ResultSet Ris = stmt.executeQuery(select);
      while (Ris.next()) {
        StudenteBean Studente=new StudenteBean();
        Studente.setCognome(Ris.getString("Cognome"));
        Studente.setNome(Ris.getString("Nome"));
        Studente.setEmail(Ris.getString("Email"));
        Studente.setPassword(Ris.getString("Password"));
        
        Studenti.add(Studente);
      } // endwhile
      return Studenti;
    } catch (SQLException sqle) {
      System.out.println("SELECT ERROR");
      throw new SQLException(sqle.getErrorCode() + ":" + sqle.getMessage());
    } catch (Exception err) {
      System.out.println("GENERIC ERROR");
      throw new SQLException(err.getMessage());
    } finally {
      if (stmt != null) {
        stmt.close();
      }
      if (conn != null) {
        conn.close();
      }
    }
  }
	/****************************************************************/
	// SELECT Domande Questionario, HA IL COMPITO PRINCIPALE DI FORNIRE
	// LA LISTA DELLE DOMANDE RELATIVE AD UN QUESTIONARIO SPECIFICATO
	// DAL CODICE DI ACCESSO.  
	/****************************************************************/
	public ArrayList<DomandaBean> selectDomandeQuestionario(String CodiceAccesso) throws SQLException {
		Statement stmt = null;
		Connection conn = null;
		ArrayList<DomandaBean> Domande = new ArrayList<DomandaBean>();
		
		try {
			conn = getDBConnection();
			stmt = conn.createStatement();
			String select = "SELECT TipoDomanda, PunteggioSbagliato, PunteggioGiusto, PunteggioVuoto,"
					+ " TestoDomanda, ImmagineDomanda \r\n" + "FROM Questionario \r\n"
					+ "INNER JOIN Domanda ON Questionario.idQuestionario = Domanda.idfQuestionario\r\n"
					+ "INNER JOIN TipoDomanda ON idfTipoDomanda=idTipoDomanda\r\n"
					+ "WHERE Questionario.CodiceAccesso LIKE '" + CodiceAccesso + "'";
			ResultSet Ris = stmt.executeQuery(select);
			

			while (Ris.next()) {
				DomandaBean app = new DomandaBean();
				app.setTipoDomanda(Ris.getString("TipoDomanda"));
				app.setPunteggioSbagliato(Ris.getInt("PunteggioSbagliato"));
				app.setPunteggioGiusto(Ris.getInt("PunteggioGiusto"));
				app.setPunteggioVuoto(Ris.getInt("PunteggioVuoto"));	
				app.setTestoDomanda(Ris.getString("TestoDomanda"));
				app.setImmagineDomanda(Ris.getString("ImmagineDomanda"));
				System.out.println(app.getTestoDomanda());
				Domande.add(app);
			} // endwhile
			return Domande;
		} catch (SQLException sqle) {
			System.out.println("SELECT ERROR");
			throw new SQLException(sqle.getErrorCode() + ":" + sqle.getMessage());
		} catch (Exception err) {
			System.out.println("GENERIC ERROR");
			throw new SQLException(err.getMessage());
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}
	/****************************************************************/
	// SELECT Risposte Domande, HA IL COMPITO PRINCIPALE DI FORNIRE
	// TUTTE LE RISPOSTE RELATIVE AD UNA DOMANDA SPECIFICATA DAL SUO TESTO
	/****************************************************************/
	public ArrayList<RispostaBean> selectRisposteDomanda(String TestoDomanda) throws SQLException {
		Statement stmt = null;
		Connection conn = null;
		ArrayList<RispostaBean> Risposte = new ArrayList<RispostaBean>();
		
		try {
			conn = getDBConnection();
			stmt = conn.createStatement();
			String select = "\r\n" + "SELECT TestoRisposta, RispostaGiusta \r\n" + "FROM Risposta\r\n"
					+ "INNER JOIN Domanda ON Risposta.idfDomanda = Domanda.idDomanda\r\n" + "WHERE Domanda.TestoDomanda = '"
					+ TestoDomanda+"'";
			ResultSet Ris = stmt.executeQuery(select);
			while (Ris.next()) {

				RispostaBean app = new RispostaBean();	
				
				app.setTestoRisposta(Ris.getString("TestoRisposta"));
				app.setRispostaGiusta(Ris.getBoolean("RispostaGiusta"));
				
				Risposte.add(app);
			} // endwhile
			return Risposte;
		} catch (SQLException sqle) {
			System.out.println("SELECT ERROR");
			throw new SQLException(sqle.getErrorCode() + ":" + sqle.getMessage());
		} catch (Exception err) {
			System.out.println("GENERIC ERROR");
			throw new SQLException(err.getMessage());
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}
	/****************************************************************/
	// SELECT Info Questionario, HA IL COMPITO PRINCIPALE DI FORNIRE
	// TUTTE LE INFORMAZIONI RELATIVE AD UN QUESTIONARIO IN BASE AL SUO CODICE
	/****************************************************************/
	public Map <String, String> selectInfoQuestionario(String Codice) throws SQLException {
		Statement stmt = null;
		Connection conn = null;
		
		Map <String, String> InfoQuestionario = new HashMap <String, String>( );
		
		try {
			conn = getDBConnection();
			stmt = conn.createStatement();
			String select = "\r\n" + "SELECT NomeQuestionario, Descrizione, DataCreazione, TempoMassimo, Sufficienza, "
							+ "DataQuestionario, OrarioMinimoInizio, NomeMateria  \r\n"
							+ "FROM Questionario "
							+ "INNER JOIN Materia ON idfMateria = idMateria\r\n"
							+ "WHERE CodiceAccesso = '" + Codice+"'";
			ResultSet Ris = stmt.executeQuery(select);
			while (Ris.next()) {

				InfoQuestionario.put("NomeQuestionario",Ris.getString("NomeQuestionario"));
				InfoQuestionario.put("Descrizione", Ris.getString("Descrizione"));
				InfoQuestionario.put("CodiceAccesso",Codice);
				InfoQuestionario.put("DataCreazione",Ris.getDate("DataCreazione").toString());
				InfoQuestionario.put("DataQuestionario",Ris.getDate("DataQuestionario").toString());
				InfoQuestionario.put("Sufficienza", String.valueOf(Ris.getFloat("Sufficienza")));
				InfoQuestionario.put("OrarioMinimoInizio", Ris.getTime("OrarioMinimoInizio").toString());
				InfoQuestionario.put("TempoMassimo",Ris.getTime("TempoMassimo").toString());
				InfoQuestionario.put("NomeMateria",Ris.getString("NomeMateria"));
				
				
			} // endwhile
				
			return InfoQuestionario;
			
		} catch (SQLException sqle) {
			System.out.println("SELECT ERROR");
			throw new SQLException(sqle.getErrorCode() + ":" + sqle.getMessage());
		} catch (Exception err) {
			System.out.println("GENERIC ERROR");
			throw new SQLException(err.getMessage());
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}
	/****************************************************************/
	// SELECT InfoUtente, Restituisce l'immagine ed il nome dell'utente
	/****************************************************************/
	public ArrayList<String> selectInfoUtente(String TipoUtente, String email, String password) throws SQLException {
		Statement stmt = null;
		Connection conn = null;
		ArrayList<String> InfoUtente=new ArrayList<String>();
		try{
			conn = getDBConnection();
			stmt = conn.createStatement();
			String select="SELECT Immagine"+TipoUtente+" as img, Nome FROM "+TipoUtente.toLowerCase()+"\r\n"
					+ "WHERE "+TipoUtente+".Email LIKE '"+email+"'\r\n"
					+ "AND "+TipoUtente+".Password LIKE '"+password+"'";
			ResultSet Ris = stmt.executeQuery(select);
			while (Ris.next()) {
				InfoUtente.add(Ris.getString("img"));
				InfoUtente.add(Ris.getString("Nome"));
			}// endwhile
			return InfoUtente;
		} catch (SQLException sqle) {
			System.out.println("SELECT ERROR");
			throw new SQLException(sqle.getErrorCode() + ":" + sqle.getMessage());
		} catch (Exception err) {
			System.out.println("GENERIC ERROR");
			throw new SQLException(err.getMessage());
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}
	/****************************************************************/
  // SELECT IdInserimentoQuestionario
  // Restituisce l'id da utilizzare per un nuovo inserimento
  /****************************************************************/
  public String selectIdInserimentoQuestionario() throws SQLException {
    Statement stmt = null;
    Connection conn = null;
    int id=0;
    try{
      conn = getDBConnection();
      stmt = conn.createStatement();
      String select = "SELECT MAX(idQuestionario) AS MAX FROM Questionario";
      ResultSet Ris = stmt.executeQuery(select);
      while (Ris.next()) { 
        id=Ris.getInt("MAX");
      } // endwhile
      id=id+1;
      return String.valueOf(id);
    } catch (SQLException sqle) {
      System.out.println("SELECT ERROR");
      throw new SQLException(sqle.getErrorCode() + ":" + sqle.getMessage());
    } catch (Exception err) {
      System.out.println("GENERIC ERROR");
      throw new SQLException(err.getMessage());
    } finally {
      if (stmt != null) {
        stmt.close();
      }
      if (conn != null) {
        conn.close();
      }
    }
  }
  /****************************************************************/
  // SELECT IdInserimentoDomanda
  // Restituisce l'id da utilizzare per un nuovo inserimento
  /****************************************************************/
  public String selectIdInserimentoDomanda() throws SQLException {
    Statement stmt = null;
    Connection conn = null;
    int id=0;
    try{
      conn = getDBConnection();
      stmt = conn.createStatement();
      String select = "SELECT MAX(idDomanda) AS MAX FROM Domanda";
      ResultSet Ris = stmt.executeQuery(select);
      while (Ris.next()) { 
        id=Ris.getInt("MAX");
      } // endwhile
      id=id+1;
      return String.valueOf(id);
    } catch (SQLException sqle) {
      System.out.println("SELECT ERROR");
      throw new SQLException(sqle.getErrorCode() + ":" + sqle.getMessage());
    } catch (Exception err) {
      System.out.println("GENERIC ERROR");
      throw new SQLException(err.getMessage());
    } finally {
      if (stmt != null) {
        stmt.close();
      }
      if (conn != null) {
        conn.close();
      }
    }
  }
	/****************************************************************/
  //INSERT Questionario, permette l'inserimento nella tabella
	// "Questionario"
  /****************************************************************/
	public void insertQuestionario(String idQ, String idIns, String idM, String Nq, String Des, String Dc, String Tm, String Suff, String Cacc, String Dq, String Omi, String ImgQ) throws SQLException{
	  Statement stmt = null;
    Connection conn = null;
    
    try{
      conn = getDBConnection();
      conn.setAutoCommit(false);
      stmt = conn.createStatement();
      
      String insert = "INSERT INTO questionario(idQuestionario, idfInsegnante, idfMateria, NomeQuestionario, Descrizione, DataCreazione, TempoMassimo, Sufficienza, CodiceAccesso, DataQuestionario, OrarioMinimoInizio, ImmagineQuestionario)\r\n"
      + "VALUES('"+idQ+"', '"+idIns+"', '"+idM+"', '"+Nq+"', '"+Des+"', '"+Dc+"', '"+Tm+"', '"+Suff+"', '"+Cacc+"', '"+Dq+"', '"+Omi+"', '"+ImgQ+"')";
      
      stmt.executeUpdate(insert);
      conn.commit();
    }catch(SQLException sqle){
      if (conn != null) {
        conn.rollback();
      }//endif
      System.out.println("INSERT ERROR: Transaction is being rolled back");
      throw new SQLException(sqle.getErrorCode() + ":" + sqle.getMessage());
    }catch(Exception err){
      if (conn != null){
        conn.rollback();
      }//endif
      System.out.println("GENERIC ERROR: Transaction is being rolled back");
      throw new SQLException(err.getMessage());
    }finally{
      if (stmt != null){
        stmt.close();
      }//endif
      if (conn != null){
        conn.close();
      }//endif
    }//endtry
	}
	/****************************************************************/
  //INSERT Domanda, permette l'inserimento nella tabella
  //"Domanda"
  /****************************************************************/
  public void insertDomanda(String idDomanda, String idfQuestionario, String PG, String PS, String PV, String TestoDomanda, String TipoDomanda, String ImmagineDomanda) throws SQLException{
    Statement stmt = null;
    Connection conn = null;
    
    try{
      conn = getDBConnection();
      conn.setAutoCommit(false);
      stmt = conn.createStatement();
      
      String insert = "INSERT INTO domanda(idDomanda, idfQuestionario, PunteggioGiusto, PunteggioSbagliato, PunteggioVuoto, TestoDomanda, TipoDomanda, ImmagineDomanda)\r\n"
      + "VALUES('"+idDomanda+"', '"+idfQuestionario+"', '"+PG+"', '"+PS+"', '"+PV+"', '"+TestoDomanda+"', '"+TipoDomanda+"', '"+ImmagineDomanda+"')";
      
      stmt.executeUpdate(insert);
      conn.commit();
    }catch(SQLException sqle){
      if (conn != null) {
        conn.rollback();
      }//endif
      System.out.println("INSERT ERROR: Transaction is being rolled back");
      throw new SQLException(sqle.getErrorCode() + ":" + sqle.getMessage());
    }catch(Exception err){
      if (conn != null){
        conn.rollback();
      }//endif
      System.out.println("GENERIC ERROR: Transaction is being rolled back");
      throw new SQLException(err.getMessage());
    }finally{
      if (stmt != null){
        stmt.close();
      }//endif
      if (conn != null){
        conn.close();
      }//endif
    }//endtry
  }
  /****************************************************************/
  //INSERT Domanda, permette l'inserimento nella tabella
  //"Risposta"
  /****************************************************************/
  public void insertRisposta(String idfDomanda, String TestoRisposta, int RispostaGiusta) throws SQLException{
	    Statement stmt = null;
	    Connection conn = null;
	    
	    try{
	      conn = getDBConnection();
	      conn.setAutoCommit(false);
	      stmt = conn.createStatement();
	      
	      String insert = "INSERT INTO risposta(idfDomanda, TestoRisposta, RispostaGiusta)\r\n"
	      + "VALUES('"+idfDomanda+"', '"+TestoRisposta+"', '"+RispostaGiusta+"')";
	      
	      stmt.executeUpdate(insert);
	      conn.commit();
	    }catch(SQLException sqle){
	      if (conn != null) {
	        conn.rollback();
	      }//endif
	      System.out.println("INSERT ERROR: Transaction is being rolled back");
	      throw new SQLException(sqle.getErrorCode() + ":" + sqle.getMessage());
	    }catch(Exception err){
	      if (conn != null){
	        conn.rollback();
	      }//endif
	      System.out.println("GENERIC ERROR: Transaction is being rolled back");
	      throw new SQLException(err.getMessage());
	    }finally{
	      if (stmt != null){
	        stmt.close();
	      }//endif
	      if (conn != null){
	        conn.close();
	      }//endif
	    }//endtry
	  }

	/****************************************************************/
	// SELECT idStudente
	// RESTITUISCE L'IDSTUDENTE DATE LE SUE CREDENZIALI
	/****************************************************************/
	public int selectIdStudente(String Email, String Password) throws SQLException {
		Statement stmt = null;
		Connection conn = null;
		int id = 0;
		try {
			conn = getDBConnection();
			stmt = conn.createStatement();
			String select = "SELECT idStudente FROM Studente\r\n" + "WHERE Email = '" + Email +
							"' AND Password = '"+ Password +"'";
			ResultSet Ris = stmt.executeQuery(select);
			while (Ris.next()) {
				id = Ris.getInt("idStudente");
			} // endwhile
			return id;
			
		} catch (SQLException sqle) {
			System.out.println("SELECT ERROR");
			throw new SQLException(sqle.getErrorCode() + ":" + sqle.getMessage());
		} catch (Exception err) {
			System.out.println("GENERIC ERROR");
			throw new SQLException(err.getMessage());
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}
	/****************************************************************/
	// SELECT idRisposta
	// RESTITUISCE L'IDRISPOSTA DI UNA RISPOSTA DATO IL SUO TESTO E DATO IL TESTO DELLA SUA DOMANDA
	/****************************************************************/
	public int selectIdRisposta(String TestoRisposta, String TestoDomanda) throws SQLException {
		Statement stmt = null;
		Connection conn = null;
		int id = 0;
		try {
			conn = getDBConnection();
			stmt = conn.createStatement();
			String select = "SELECT idRisposta FROM Risposta INNER JOIN Domanda ON idDomanda = idfDomanda"
							+ "\r\n" + "WHERE TestoRisposta = '" + TestoRisposta + "' AND TestoDomanda = '"+TestoDomanda+"'";
			ResultSet Ris = stmt.executeQuery(select);
			while (Ris.next()) {
				id = Ris.getInt("idRisposta");
			} // endwhile
			return id;
			
		} catch (SQLException sqle) {
			System.out.println("SELECT ERROR");
			throw new SQLException(sqle.getErrorCode() + ":" + sqle.getMessage());
		} catch (Exception err) {
			System.out.println("GENERIC ERROR");
			throw new SQLException(err.getMessage());
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}
	/****************************************************************/
  	//INSERT Dettaglio Risposta, permette l'inserimento nella tabella
	//"DettaglioRisposta"
	/****************************************************************/
  	public void insertDettaglioRisposta(int idfRisposta, int idfStudente, String RispostaSelezionata) throws SQLException{
  		
		Statement stmt = null;
		Connection conn = null;

		try {

			conn = getDBConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();

			String insert = "INSERT INTO dettagliorisposta(idfRisposta, idfStudente, RispostaSelezionata)\r\n" + "VALUES("
					+ idfRisposta + ", " + idfStudente + ", " + RispostaSelezionata + ")";

			stmt.executeUpdate(insert);
			conn.commit();

		} catch (SQLException sqle) {
			if (conn != null) {
				conn.rollback();
			} // endif
			System.out.println("INSERT ERROR: Transaction is being rolled back");
			throw new SQLException(sqle.getErrorCode() + ":" + sqle.getMessage());
		} catch (Exception err) {
			if (conn != null) {
				conn.rollback();
			} // endif
			System.out.println("GENERIC ERROR: Transaction is being rolled back");
			throw new SQLException(err.getMessage());
		} finally {
			if (stmt != null) {
				stmt.close();
			} // endif
			if (conn != null) {
				conn.close();
			} // endif
		} // endtry
	}
	/****************************************************************/
  //DELETE questionario, elimina tutti i dati relativi ad un
  // questionario
  /****************************************************************/
  public void deleteQuestionario(String CodiceAccesso) throws SQLException{
    Statement stmt = null;
    Connection conn = null;
    try {
      conn = getDBConnection();
      stmt = conn.createStatement();
      String delete = "DELETE FROM questionario WHERE CodiceAccesso='"+CodiceAccesso+"'";
      stmt.executeUpdate(delete);
    } catch (SQLException sqle) {
      if (conn != null) {
        conn.rollback();
      } // endif
      System.out.println("INSERT ERROR: Transaction is being rolled back");
      throw new SQLException(sqle.getErrorCode() + ":" + sqle.getMessage());
    } catch (Exception err) {
      if (conn != null) {
        conn.rollback();
      } // endif
      System.out.println("GENERIC ERROR: Transaction is being rolled back");
      throw new SQLException(err.getMessage());
    } finally {
      if (stmt != null) {
        stmt.close();
      } // endif
      if (conn != null) {
        conn.close();
      } // endif
    } // endtry
  }
  /****************************************************************/
  //Update avatar, aggiorna l'immagine dell'utente
  /****************************************************************/
  public void updateAvatar(String TipoUtente, String email, String password, String Immagine)throws SQLException{
    Statement stmt = null;
    Connection conn = null;
    try{
      conn=getDBConnection();
      stmt=conn.createStatement();
      String update="UPDATE "+TipoUtente+" SET Immagine"+TipoUtente+"='"+Immagine+"'"
          + "WHERE "+TipoUtente+".Email LIKE '"+email+"'"
          + "AND "+TipoUtente+".Password LIKE '"+password+"'";
      stmt.executeUpdate(update);
    }catch(SQLException sqle){
      System.out.println("UPDATE ERROR");
      throw new SQLException(sqle.getErrorCode() + ":" + sqle.getMessage());
    }catch(Exception err){
      System.out.println("GENERIC ERROR");
      throw new SQLException(err.getMessage());
    }finally{
      if (stmt != null){
        stmt.close();
      }
      if (conn != null){
        conn.close();
      }
    }//endtry
  }
  /****************************************************************/
  //Update password, aggiorna la password dell'utente
  /****************************************************************/
  public void updatePassword(String TipoUtente, String email, String VecchiaPassword, String NuovaPassword)throws SQLException{
    Statement stmt = null;
    Connection conn = null;
    try{
      conn=getDBConnection();
      stmt=conn.createStatement();
      String update="UPDATE "+TipoUtente+" SET Password='"+NuovaPassword+"'"
          + "WHERE "+TipoUtente+".Email LIKE '"+email+"'"
          + "AND "+TipoUtente+".Password LIKE '"+VecchiaPassword+"'";
      stmt.executeUpdate(update);
    }catch(SQLException sqle){
      System.out.println("UPDATE ERROR");
      throw new SQLException(sqle.getErrorCode() + ":" + sqle.getMessage());
    }catch(Exception err){
      System.out.println("GENERIC ERROR");
      throw new SQLException(err.getMessage());
    }finally{
      if (stmt != null){
        stmt.close();
      }
      if (conn != null){
        conn.close();
      }
    }//endtry
  }
	/****************************************************************/
	//Select CodiceQuestionario, seleziona il codice di un questionario in base al suo id
	/****************************************************************/
	public String selectCodiceQuestionario(int idQuestionario) throws SQLException{

		Statement stmt = null;
		Connection conn = null;
		String Codice = "";
		try {
			conn = getDBConnection();
			stmt = conn.createStatement();
			String select = "SELECT CodiceAccesso FROM Questionario\r\n" + "WHERE idQuestionario = " + idQuestionario;
			System.out.println("Query selectCodiceAccesso: " + select);
			ResultSet Ris = stmt.executeQuery(select);
			while (Ris.next()) {
				Codice = Ris.getString("CodiceAccesso");
			} // endwhile
			return Codice;
			
		} catch (SQLException sqle) {
			System.out.println("SELECT ERROR");
			throw new SQLException(sqle.getErrorCode() + ":" + sqle.getMessage());
		} catch (Exception err) {
			System.out.println("GENERIC ERROR");
			throw new SQLException(err.getMessage());
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}//endtry

	}
	/****************************************************************/
	//Select DettaglioRisposta, seleziona un dettaglioRisposta in base al codice del questionario in cui è,
	//in base al testo della domanda, in base a quello della risposta e in base all'email dello studente
	/****************************************************************/
	public boolean selectDettaglioRisposta(String CodiceQuestionario, String TestoDomanda, String TestoRisposta, String EmailStudente) throws SQLException{

		Statement stmt = null;
		Connection conn = null;
		boolean Selezionata = false;
		try {
			conn = getDBConnection();
			stmt = conn.createStatement();
			String select = "SELECT RispostaSelezionata FROM DettaglioRisposta "
					+ " INNER JOIN Studente ON idfStudente = idStudente"
					+ " INNER JOIN Risposta ON idfRisposta = idRisposta"
					+ " INNER JOIN Domanda ON idfDomanda = idDomanda"
					+ " INNER JOIN Questionario ON idfQuestionario = idQuestionario"
					+ " WHERE Studente.Email = '" + EmailStudente + "' AND Risposta.TestoRisposta = '"+ TestoRisposta + 
					"' AND Domanda.TestoDomanda = '"+TestoDomanda+"' AND Questionario.CodiceAccesso='" + CodiceQuestionario + "'";
			System.out.println("Query selectDettaglioRisposta: " + select);
			ResultSet Ris = stmt.executeQuery(select);
			while (Ris.next()) {
				Selezionata = Ris.getBoolean("RispostaSelezionata");
			} // endwhile
			return Selezionata;
			
		} catch (SQLException sqle) {
			System.out.println("SELECT ERROR");
			throw new SQLException(sqle.getErrorCode() + ":" + sqle.getMessage());
		} catch (Exception err) {
			System.out.println("GENERIC ERROR");
			throw new SQLException(err.getMessage());
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}//endtry

	}
	/****************************************************************/
}
