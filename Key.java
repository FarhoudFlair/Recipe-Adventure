import java.util.List;

public class Key extends Thing {
  
  public Key(String name, Location location, int value){
    super(name, location, value);
  }
  
  public Key(String name, int value) {
    this(name, null, value);
 }
}