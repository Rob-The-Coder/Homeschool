package Beans;
/***************************/
public class CalendarBean{
/***************************/
//Attributi
/***************************/
  public String id;
  public String title;
  public String start;
  public String end;
/***************************/
//Costruttore
/***************************/
  public CalendarBean() {
    
  }
/***************************/
//Getter/Setter
/***************************/
  public String getId() {
      return id;
  }
  public void setId(String id) {
      this.id = id;
  }
  public String getTitle() {
      return title;
  }
  public void setTitle(String title) {
      this.title = title;
  }
  public String getStart() {
      return start;
  }
  public void setStart(String start) {
      this.start = start;
  }
  public String getEnd() {
      return end;
  }
  public void setEnd(String end) {
      this.end = end;
  }
/***************************/
}
