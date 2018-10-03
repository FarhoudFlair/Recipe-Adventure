public class Food extends Thing{

  public Food(String name, Location location, int value){
    super(name, location, value);
  }
  
  public Food(){
    this("Rostbiff", null, 100);
  }
  
  @Override
  public void interact(Player p){
    p.setHealth(p.getHealth()+this.value);
    String s = "You eat the " + this.name + " and gain " + this.value + " health!\n";
    s += "You now have " + p.getHealth() + " health.";
    System.out.println(s);
    Room currRoom = this.getLocation().getWorld().getRoom(this.getLocation());
    currRoom.removeThing(this);
  }
  
}