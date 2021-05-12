package Beans;
import java.util.ArrayList;
/***************************/
public class MateriaBean{
/***************************/
//Attributi
/***************************/
	private ArrayList<QuestionarioBean> Questionario;
	private String Materia;
	private String Colore;
/***************************/
//Costruttore
/***************************/
	public MateriaBean(){
		Questionario = new ArrayList<QuestionarioBean>();
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
	public String getMateria() {
		return Materia;
	}
	public void setMateria(String materia) {
		Materia = materia;
	}
	public String getColore() {
    return Colore;
  }
  public void setColore(String colore) {
    Colore = colore;
  }
/***************************/
}
