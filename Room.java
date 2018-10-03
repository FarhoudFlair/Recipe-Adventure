import java.util.List;

public class Room{
  protected String         description;
  protected Location       location;
  protected List<Location> adjacent;
  protected List<Player>   people;
  protected List<Thing>    things;
  //New attribute - array of Doors. Ordered [N,E,S,W], null is no door.
  protected Door[]         doors;
  
    public Room(String description, Location location, List<Location> adjacent, 
              List<Player> people, List<Thing> things, Door[] doors)
  {
    this.description = description;
    this.location = location;
    this.adjacent = adjacent;
    this.people = people;
    this.things = things;
    this.doors = doors;
  }
  
  public Room(String description, Location location, List<Location> adjacent, 
              List<Player> people, List<Thing> things)
  {
    this(description, location, adjacent, people, things, new Door[] {null, null, null, null});
  }
  
  /* getters */
  public Location       getLocation(){ return location; }
  public List<Location> getAdjacentRooms(){ return adjacent; }
  public List<Player>   getPlayers(){ return people; }
  public List<Thing>    getThings(){ return things; }
  public Door[]         getDoors() { return doors; }
  
  public void setDoor(int direction){
    doors[direction] = new Door();
    Room other = null;
    World w = this.getLocation().getWorld();
    try{
      if(direction == 0){
        other = w.getRoom(this.getLocation().north());
      } else if (direction == 1){
        other = w.getRoom(this.getLocation().east());
      } else if (direction == 2){
        other = w.getRoom(this.getLocation().south());
      } else if (direction == 3){
        other = w.getRoom(this.getLocation().west());
      }   
      doors[direction].setConnection(other);    
    
  } catch(BadLocationException e){
    System.err.println(e);
  } finally {
    return;
  }
  }
  
  public String look(){
    // return a string describing the room 
    // (what is in it, what exits you have, etc)
    return this.toString();
  }

  public void setDescription(String description){
    this.description = description;
  }
	
	public String getDescription(){
		return this.description;
	}
  
  public void addPlayer(Player p){
    this.people.add(p);
  }
  
  public void removePlayer(Player p){
    this.people.remove(p);
  }
  
  /** add a thing t to the current room */
  public void addThing(Thing t){
    this.things.add(t);
  }
  
  public void removeThing(Thing t){
    this.things.remove(t);
  }
  
  @Override
  public String toString(){
    return description;
  }
}