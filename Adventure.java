import java.util.List;

public class Adventure{
  
  public static void main(String[] args){
    World world = new World();
    world.readWorldFromFile("map.txt", true);
  
  List<Thing> humanStartItems = new java.util.ArrayList<Thing>();
  Player human = new Human(world, "Player", world.getEntrance(), 100,humanStartItems, world.getGoal() );
  world.getRoom(world.getEntrance()).addPlayer(human);
  world.getRoom(new Location(world,1,1)).getDoors()[3].setLocked(true);
 
    while(!checkWin(human, world)){
   //System.out.println("\n---New Turn---\n"); //debugging
      world.printWorld();
   //go thru the array of non-playable chars
   for (Player player : world.getPlayers()){ //for players that take their turn before the human
    if(player.getClass().getName()=="SecurityGuard"){
     //System.out.println("SecurityGuard's turn"); //debugging
     player.play();
    }
   }
   
   human.play();
   
   //go thru the array of non-playable chars
   for (Player player : world.getPlayers()){ //for players that take their turn before the human
    if(player.getClass().getName()=="Chef"){
     //System.out.println("Chef's turn"); //debugging
     player.play();
    }
   }
   for (Player player : world.getPlayers()){ //for players that take their turn before the human
    if(player.getClass().getName()=="Custodian"){
     //System.out.println("Chef's turn"); //debugging
     player.play();
    }
   }
    }
    System.out.println("\n\nCongratulations, you win!");
  }
  
  private static boolean checkWin(Player human, World w){
    boolean hasMacGuffin = human.getThings().contains(w.getGoal());
    boolean isInEntrance = human.getLocation().equals(w.getEntrance());
    return (hasMacGuffin && isInEntrance);
  }
}
