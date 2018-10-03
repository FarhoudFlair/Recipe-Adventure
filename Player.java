import java.util.List;

/** Players in the game 
  * 
  * Each member of your team should implement their own
  * unique Player subtype. Your group should also have a human player.
  */

public abstract class Player{
 protected World        w;    // world that player lives in
 protected String       name;
 protected Location     location;
 protected int          health = 100;
 protected List<Thing>  things;
 protected Thing        goal;
 
 /** Creates a player in the game
  * 
  * @param w is the world that the player lives in
  * @param name is the name of the player
  * @param location is where in the world the player is
  * @param health is the health of the player (which may or may not be relevant in your game)
  * @param things is a list of Thing objects that the player initially possesses
  * @param goal is the Thing that the human player is trying to retrieve in the game
  */
 public Player(World w, String name, Location location, int health,
               List<Thing>  things, Thing goal)
 {
  this.w = w;
  this.name = name;
  this.location = location;
  this.health = health;
  this.things = things;
  this.goal = goal;
 }
 
 /** Getter for a player's world */
 public World        getWorld(){ return w; }

 /** Getter for a player's name */
 public String       getName(){ return name; }
 
 /** Getter for a player's location */
 public Location     getLocation(){ return location; }
 
 /** Getter for a player's health */
 public int          getHealth(){ return health; }
 
 /** Getter for a player's list of things */
 public List<Thing>  getThings(){ return things; }
 
 /** Getter for a player's goal */
 public Thing        getGoal(){ return goal; }
 
 /** Plays a turn for this player
   * 
   * For computer players will have the AI for that player.
   * For human player querie user for input and then react 
   * appropriately for the input.
   */
 public void play(){}
 
 
 /** Moves a player from one location to a new location
   * 
   * @param newLocation is the new location that the player will be moved to
   * @return true if the move was successful and false otherwise (e.g. when trying to move from one 
   *         location to another that are not connected)
   */
 public boolean move(Location newLocation){
   // move from current location to new location
   // should only be allowed to move if the locations are connected
   // (with a door that can opened)
   Location playerLocation = this.getLocation();
   Room playerRoom = w.getRoom(playerLocation);
   Room newRoom = w.getRoom(newLocation);
   Door[] dArray = playerRoom.getDoors();
   for(int i = 0; i < 4; i++){
     if(dArray[i] != null){
       if(dArray[i].getConnection().equals(newRoom)){
         if(!dArray[i].isLocked()){
           playerRoom.removePlayer(this);
           this.setLocation(newLocation);
           newRoom.addPlayer(this);
           return true;
         } else {
      List<Thing> playerThings = this.getThings();
      boolean keyUsed = false;
           for (Thing neededKey: playerThings) {
      if (neededKey instanceof Key) {
        System.out.println("You used the key to unlock the office door! Try entering now.");
        this.removeThing(neededKey);
        dArray[i].setLocked(false);
        keyUsed = true;    
      }
      if (keyUsed) { break; }
           }
    
           if (dArray[i].isLocked()) {
           System.out.println("The door is locked.");
           }
           return false;
      }

         }
       }
     }
   System.out.println("You cannot go that way.");
   return false;
 }
 
 /** sets a player's current location
  * 
  * @param location is the Location to set for the player
  */
 public void setLocation(Location location){
  this.location = location;
 }

 /** Setter for a player's health
   * 
   * @param h is the new health of the player
   */
 public void setHealth(int h){
  this.health = h;
 }
 
 /** Adds a thing to the player's list of things
   * 
   * @param t isa thing to add to the player's list of things
   */
 public void addThing(Thing t){
  this.things.add(t);
 }
 
 /** Remove a thing from a player's list of things
   * 
   * @param t is a thing to be removed from the player's list of things
   * @return true if remove was successful and false otherwise (i.e., if the player 
   *              does not have this thing in their list).
   */
 public boolean removeThing(Thing t){
  return this.things.remove(t);
 }
 
 /** Change the player's goal in the game (setter for goal)
   * 
   * @param g is the new goal for this player
   * @return the previous goal of the player
   */ 
 public Thing newGoal(Thing g){
  Thing old = this.goal;
  this.goal = goal;
  return old;
 }

 
 /** Allows for interaction with this player and another player
   * 
   * @param p is a player that is interacting with this player
   */
 public void interact(Player p){
   // allows for some interaction with a player
 }
 
 /** Allows for interaction with this player and some thing
   * 
   * @param t is a thing that this player is interacting with
   */
 public void interact(Thing t){
   // allows for some interaction with a player
 }

 
 /** Allows for interaction with this player and the room it is in
   * 
   */
 public void interact(){
   // allows for some interaction with anything in the room
 }
 
 
 @Override
 public String toString(){
   return name;
 }
 
 /** Two players are the same if they have the same name, location and health. */
 @Override
 public boolean equals(Object o){
   if( o instanceof Player){
     return this.name.equals( ((Player)o).name )  
            && this.location.equals( ((Player)o).location ) 
            && this.health == ((Player)o).health;
                                  
   }else{
     return false;
   }
 }

 
}