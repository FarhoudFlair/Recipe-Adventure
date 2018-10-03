import java.util.List;
import java.io.FileReader;
import java.io.BufferedReader;

public class World{
  protected Room[][] rooms;
  protected Location entrance;
  protected Thing goal;
  protected int[] size;
 protected int rows;
 protected int cols;
 protected List<Player> players;
  
  //TODO: move more stuff to constructor
  public World(){
  }
 
 public List<Player> getPlayers(){
  return players;
 }
  
  public void readWorldFromFile(String filename, Boolean isGoal){
   String line = null;
   
   try{
  FileReader file = new FileReader(filename);
  BufferedReader reader = new BufferedReader(file);
  
  //read the first line of the file (number of rooms)... hardcoded-ish for shape
  line = reader.readLine();
 int numRooms = Integer.parseInt(line);
 
 if(numRooms==6){ //2 rows 3 cols
  rows = 2;
  cols = 3;
  size = new int[]{2,3};
 }
 else if(numRooms==2){ //1 row 2 cols
  rows = 1;
  cols = 2;
  size = new int[]{1,2};
 }
 
  /*
  try{
   numRooms = Integer.parseInt(line);
  } catch (java.lang.NumberFormatException e){
   System.out.println(filename + " line 1: " + line + " is not an integer");
  }
  */
  //Make the generic rooms & add them to the world
  rooms = new Room[rows][cols];
  for(int row=0; row<rows; row++){
   for(int col=0; col<cols; col++){
    //System.out.println("making room " + row + " " + col); //debugging
    rooms[row][col] = new Room("temp", new Location(this,row,col), 
                      new java.util.ArrayList<Location>(),
                      new java.util.ArrayList<Player>(),
                      new java.util.ArrayList<Thing>());
   }
  }
 
 //initialize the list of players
  players = new java.util.ArrayList<Player>();
  
  //read the file, 5 lines at a time to get the room details
  //TODO: add a big try/catch??
  for(int row=0; row<rows; row++){
   for(int col=0; col<cols; col++){
    
    line = null;
    String rmName = "";
    int rmNum = 0;
    Room currRm = rooms[row][col];
    
    //line 2: Room #
    line = reader.readLine();
    rmNum = Integer.parseInt(line); //TODO: add a try/catch??
  if(numRooms==6){
   switch (rmNum){
    case 1:
    currRm.getAdjacentRooms().add(rooms[0][1].getLocation());
    currRm.getAdjacentRooms().add(rooms[1][0].getLocation());
    break;
    case 2:
    currRm.getAdjacentRooms().add(rooms[0][0].getLocation());
    currRm.getAdjacentRooms().add(rooms[0][2].getLocation());
    currRm.getAdjacentRooms().add(rooms[1][1].getLocation());
    break;
    case 3:
    currRm.getAdjacentRooms().add(rooms[0][1].getLocation());
    currRm.getAdjacentRooms().add(rooms[1][2].getLocation());
    break;
    case 4:
    currRm.getAdjacentRooms().add(rooms[0][0].getLocation());
    currRm.getAdjacentRooms().add(rooms[1][1].getLocation());
    break;
    case 5:
    currRm.getAdjacentRooms().add(rooms[0][1].getLocation());
    currRm.getAdjacentRooms().add(rooms[1][0].getLocation());
    currRm.getAdjacentRooms().add(rooms[1][2].getLocation());
    break;
    case 6:
    currRm.getAdjacentRooms().add(rooms[0][2].getLocation());
    currRm.getAdjacentRooms().add(rooms[1][1].getLocation());
    break; 
   }
  }
  else if(numRooms==2){
   switch (rmNum){
    case 1:
    currRm.getAdjacentRooms().add(rooms[0][1].getLocation());
    break;
    case 2:
    currRm.getAdjacentRooms().add(rooms[0][0].getLocation());
    break;
   }
  }
    
    
    //line 3: Room Name
    rmName = reader.readLine();
    currRm.setDescription(rmName);
    
    //line 4: Connected Rooms
    //parse the string
    line = reader.readLine();
    String delims = "[,]";
    String[] connDirs = line.split(delims);
    
    //System.out.print("doors at " + rmNum + ": "); //debugging
    for(int i=0; i<connDirs.length; i++){
     //System.out.print(" " + connDirs[i]); //debugging
     int connDir = Integer.parseInt(connDirs[i]);
     currRm.setDoor(connDir);
    }
    //System.out.println(); //debugging
    
    //line 5: players
    line = reader.readLine(); //same
  delims = "[,]"; //same
  String[] playersArr = line.split(delims);
  //create the AI players
  if(playersArr[0].equals("1")){ //(1,0,0,0) someone
    //create waiter
    Waiter newWaiter = new Waiter(this, currRm.getLocation(), new java.util.ArrayList<Thing>());
    currRm.addPlayer(newWaiter);
    players.add(newWaiter);

  }
  else if(playersArr[1].equals("1")){ //(0,1,0,0) indicates chef
   //create chef
   //System.out.println("Putting Chef in " + currRm.getDescription()); //debugging
   java.util.ArrayList<Thing> foodstuff = new java.util.ArrayList<Thing>();
   foodstuff.add(new Food("Steak", currRm.getLocation(), 100));
   foodstuff.add(new Food("Roast beef", currRm.getLocation(), 50));
   Chef newChef = new Chef(this, currRm.getLocation(), foodstuff);
   currRm.addPlayer(newChef);
   players.add(newChef);
  }
  else if(playersArr[2].equals("1")){ //(0,0,1,0) indicates guard
   //create guard
   //System.out.println("Putting Guard in " + currRm.getDescription()); //debugging
   SecurityGuard newGuard = new SecurityGuard(this, currRm.getLocation(), new java.util.ArrayList<Thing>(), null ); 
   currRm.addPlayer(newGuard);
   players.add(newGuard);
  }
  else if(playersArr[3].equals("1")){ //(0,0,0,1) someone
    //create custodian
    Custodian newCust = new Custodian(this, "Mr. Flack", currRm.getLocation(), 100, new java.util.ArrayList<Thing>(), null);
    currRm.addPlayer(newCust);
    players.add(newCust);
    newCust.getThings().add(new Key("Office keys", currRm.getLocation(), 0));
  }
    
    //line 6: things on the ground
  line = reader.readLine(); //same
  delims = "[,]"; //same
  String[] thingsArr = line.split(delims); //everything like above
  if(thingsArr[0].equals("1")){ //(1,0,0,0)
   ID newID = new ID(currRm.getLocation());
   currRm.addThing(newID);
  }
  else if(thingsArr[1].equals("1")){ //(0,1,0,0)
   Food newFood = new Food("Pizza Slice", currRm.getLocation(), 10);
   currRm.addThing(newFood);
  }
  else if(thingsArr[2].equals("1")){ //(0,0,1,0)
   //Key newKey = new Key("Key", currRm.getLocation(), 10);
   //currRm.addThing(newKey);
    WaiterUniform uniform = new WaiterUniform();
    currRm.addThing(uniform);
  }
  else if(thingsArr[3].equals("1")){ //(0,0,0,1)
   Mop newMop = new Mop("Mop", currRm.getLocation(), 10);
   currRm.addThing(newMop);
  }
  
   }
  }
  
  reader.close();
  
  entrance = rooms[0][0].getLocation();
 if(isGoal){
  goal = new Thing("Secret Recipe", rooms[1][0].getLocation(), 1, 7);
  rooms[1][0].addThing(goal);
 } else {
  goal = new Thing("MacGuffin", rooms[0][0].getLocation(), 0, 0);
  rooms[0][0].addThing(goal);
 }
  
  } catch (java.io.FileNotFoundException e){
   System.err.println(filename + " not found!");
  } catch (java.io.IOException e){
   System.err.println("IOException: " + e);
  }
 }
  
 public World(String worldFileName){
    // create world described in file worldFileName 
  }
  
  public Location getEntrance(){
    return entrance;
  }
  
  public Thing getGoal(){ return goal;}
  
  /** returns room of spcified Player */
  public Room getRoom(Player p){
    int r = p.getLocation().getRow();
    int c = p.getLocation().getCol();
    return rooms[r][c];
  }
  /** returns room of specified location 
    * 
    * @return the room that this is at this location in this world. 
    *         Returns null if there is no such room.
    */
  public Room getRoom(Location location){
    return rooms[location.getRow()][location.getCol()];
  }
  
  public int[] getSize(){
    return size;
  }
  
  public char[] roomPlayers(int row){
    char[] roomPlayers = new char[size[1]];
    
    for(int i = 0; i < size[1]; i++){
      Room currRoom = rooms[row][i];
      java.util.List<Player> players = currRoom.getPlayers();
      
      if(players.size() == 1){
        roomPlayers[i] = players.get(0).getClass().getName().charAt(0);
        if(players.get(0) instanceof Human){
          roomPlayers[i] = 'P';
        }
      } else if(players.size() > 1){
        roomPlayers[i] = '8';     
        for(Player currPlayer : players){
          if(currPlayer instanceof Human){
            roomPlayers[i] = 'P';
            break;
          }
        }
      } else {
        roomPlayers[i] = ' ';
      }
    }
    return roomPlayers;
  }
    
  
  public String printRow(int row){
    String rowString = "| ";
    String wallSpacer = " | ";
    String wallDoor = " : ";
    String floorString = "+";
    String floorSpacer = "---+";
    String floorDoor = "- -+";
    char[] players = roomPlayers(row);
    
    for(int i = 0; i < size[1]; i++){
      Room currRoom = rooms[row][i];
      rowString += players[i];
      if(i < size[1]-1){
        if(currRoom.getDoors()[1] != null){
          rowString += wallDoor;
        } else {
          rowString += wallSpacer;
        }
      } else {
        rowString += " |\n";
      }
      
      if(row < size[0]){
        if(currRoom.getDoors()[2] != null){
          floorString += floorDoor;
        } else {
          floorString += floorSpacer;
        }
      }
    }
//  floorString += "\n";
    return rowString + floorString;
  }
  
  public void printWorld(){
    String topLine = "\n+";
    for(int i = 0; i < size[1]; i ++){
      topLine += "---+";
    }
//    topLine += "\n";
    System.out.println(topLine);
    for(int i = 0; i < size[0]; i++){
      System.out.println(printRow(i));
    }
  } 
}