import java.util.List;

public class SmallAdventure{
  
  public static void main(String[] args){
    World world = new World();
    world.readWorldFromFile("minimap.txt", false);
   
  Player human = new Human(world, "Player", world.getEntrance(), 100,new java.util.ArrayList<Thing>(), world.getGoal());

  world.getRoom(world.getEntrance()).addPlayer(human);
  
  while(true){
   world.printWorld();
   //human turn
   human.play();

   //go thru the array of non-playable chars
   for (Player player : world.getPlayers()){

    player.play();
   }
  }
  }
}
