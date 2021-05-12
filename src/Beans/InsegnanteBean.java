package Beans;
import java.util.ArrayList;
/***************************/
public class InsegnanteBean {
/***************************/
//Attributi
/***************************/
  private ArrayList<QuestionarioBean> Questionario;
  private String Nome;
  private String Cognome;
  private String DataNascita;
  private String Città;
  private String Email;
  private String Password;
  private String PercorsoImmagineInsegnante;
/***************************/
//Costruttore
/***************************/
  public InsegnanteBean() {
	  Questionario = new ArrayList<QuestionarioBean> ();
  }
/***************************/
//Getter/Setter
/***************************/
  public ArrayList<QuestionarioBean> getQuestionario() {
    return Questionario;
  }
  public void setQuestionario(ArrayList<QuestionarioBean> questionario) {
    Questionario = questionario;
  }
  public String getNome() {
    return Nome;
  }
  public void setNome(String nome) {
    Nome = nome;
  }
  public String getCognome() {
    return Cognome;
  }
  public void setCognome(String cognome) {
    Cognome = cognome;
  }
  public String getDataNascita() {
    return DataNascita;
  }
  public void setDataNascita(String dataNascita) {
    DataNascita = dataNascita;
  }
  public String getCittà() {
    return Città;
  }
  public void setCittà(String città) {
    Città = città;
  }
  public String getEmail() {
    return Email;
  }
  public void setEmail(String email) {
    Email = email;
  }
  public String getPassword() {
    return Password;
  }
  public void setPassword(String password) {
    Password = password;
  }
  public String getPercorsoImmagineInsegnante() {
    return PercorsoImmagineInsegnante;
  }
  public void setPercorsoImmagineInsegnante(String percorsoImmagineInsegnante) {
    PercorsoImmagineInsegnante = percorsoImmagineInsegnante;
  }
/***************************/
}
