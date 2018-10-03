import java.util.List;

public class Door{
  
  private Room connection;
  private boolean locked;
  
  public Door(Room connection, boolean locked){
    this.connection = connection;
    this.locked = locked;
  }
  
  public Door(){
    this(null, false);
  }
               
  
  public boolean isLocked(){
    return locked;
  }
  
  public boolean setLocked(boolean lock){
    locked = lock;
    return locked;
  }
  
  public Room getConnection(){
    return connection;
  }
  
  public Room setConnection(Room r1){
    this.connection = r1;
    return connection;
  }
  
  public boolean isConnected(Room r1, Room r2){
    //Fill in some code here: check the doors of r1 for r2 connection.
    return false;
  }
  
//Requires one connected room as input, outputs the other room.
//Returns null if Door is not connected to input room.
  /*
  public Room connectedRoom(Room entrance){
    int entranceIndex = connections.indexOf(entrance);
    if(entranceIndex != -1){
      return connections.get(1-entranceIndex);
    }
    String s = "The door is not connected to input room.";
    System.out.println(s);
    return null;
  }
    */
}