package Beans;
import java.util.ArrayList;
/***************************/
public class QuestionarioBean{
/***************************/
//Attributi
/***************************/
  private InsegnanteBean Insegnante;
  private MateriaBean Materia;
  private ArrayList<DomandaBean> Domanda;
  private String NomeQuestionario;
  private String Descrizione ;
  private String DataCreazione;
  private String TempoMassimo;
  private float Sufficienza;
  private String CodiceAccesso;
  private String DataQuestionario;
  private String OrarioMinimoInizio;
  private String ImmagineQuestionario;
/***************************/
//Costruttore
/***************************/
  public QuestionarioBean(){
	  Insegnante = new InsegnanteBean();
	  Materia = new MateriaBean();
	  Domanda = new ArrayList<DomandaBean>();
  }
/***************************/
//Getter/Setter
/***************************/
  public InsegnanteBean getInsegnante() {
    return Insegnante;
  }
  public void setInsegnante(InsegnanteBean insegnante) {
    Insegnante = insegnante;
  }
  public MateriaBean getMateria() {
    return Materia;
  }
  public void setMateria(MateriaBean materia) {
    Materia = materia;
  }
  public ArrayList<DomandaBean> getDomanda() {
    return Domanda;
  }
  public void setDomanda(ArrayList<DomandaBean> domanda) {
    Domanda = domanda;
  }
  public String getDescrizione() {
    return Descrizione;
  }
  public void setDescrizione(String descrizione) {
    Descrizione = descrizione;
  }
  public String getDataCreazione() {
    return DataCreazione;
  }
  public void setDataCreazione(String dataCreazione) {
    DataCreazione = dataCreazione;
  }
  public String getTempoMassimo() {
    return TempoMassimo;
  }
  public void setTempoMassimo(String tempoMassimo) {
    TempoMassimo = tempoMassimo;
  }
  public float getSufficienza() {
    return Sufficienza;
  }
  public void setSufficienza(float sufficienza) {
    Sufficienza = sufficienza;
  }
  public String getCodiceAccesso() {
    return CodiceAccesso;
  }
  public void setCodiceAccesso(String codiceAccesso) {
    CodiceAccesso = codiceAccesso;
  }
  public String getDataQuestionario() {
    return DataQuestionario;
  }
  public void setDataQuestionario(String dataQuestionario) {
    DataQuestionario = dataQuestionario;
  }
  public String getOrarioMinimoInizio() {
    return OrarioMinimoInizio;
  }
  public void setOrarioMinimoInizio(String orarioMinimoInizio) {
    OrarioMinimoInizio = orarioMinimoInizio;
  }
  public String getImmagineQuestionario() {
    return ImmagineQuestionario;
  }
  public void setImmagineQuestionario(String immagineQuestionario) {
    ImmagineQuestionario = immagineQuestionario;
  }
  public String getNomeQuestionario() {
  	return NomeQuestionario;
  }
  public void setNomeQuestionario(String nomeQuestionario) {
  	NomeQuestionario = nomeQuestionario;
  }
/***************************/
}
