import java.util.List;

public class Custodian extends Player {
  private boolean interacted = false;
  
  public Custodian(World w, String name, Location location, int health,
                   List<Thing>  things, Thing goal) {
    super(w,name,location,health,things,goal);
    
  }
  
  @Override
  public void interact() {
    System.err.print("Mr. Flack: ");
    System.out.println("Mopping, mopping never changes."); 
  }
  
  
  
  public void interact(Player p) {
    
    
    
    for(int i=0; i<p.getThings().size(); i++){
      Thing currThing = p.getThings().get(i);
      if (currThing instanceof Mop) {
        p.getThings().remove(i);
        p.getThings().add(this.things.get(0));
        this.things.add(currThing);
        System.out.println("Mr. Flack: Oh thank you so much for the mop! Here's a key the boss told me to hold on to.");
        this.interacted = true;
        return;
      }
    }
    System.out.println("Mr. Flack: Hey, you there! " + p.getName() + ", that's your name, right? Don't ask me how I know.\n If you can find me a brand new mop so I can clean these floors I'll give you the key to the bosses office!");
  }
  
  @Override
  public void play() {
    /*   int randomMove;
     boolean validMove = false;
     do {
     try {
     randomMove = (int)(Math.random()*6) + 1;
     switch (randomMove) {
     case 1: 
     this.move(this.getLocation().north());
     validMove = true;
     break;
     case 2:
     this.move(this.getLocation().east());
     validMove = true;
     break;
     case 3: 
     this.move(this.getLocation().south());
     validMove = true;
     break;
     case 4:
     this.move(this.getLocation().west());
     validMove = true;
     break;
     default: 
     System.err.println("Something went wrong with Custodian play method");
     break;
     }
     }  catch (BadLocationException e) {      
     ///System.err.println(e); 
     }
     }while(!validMove);
     }*/
    Room currRoom = this.getWorld().getRoom(this.getLocation());
    int[] worldSize = this.getWorld().getSize();
    if(worldSize[0] * worldSize[1] < 4){
      Door[] moves = currRoom.getDoors();
      java.util.Random wander = new java.util.Random();
      if(wander.nextInt(10) < 10){ //for adjusting the chances of chef wandering
        int direction = wander.nextInt(4);
        if(moves[direction] != null){
          move(moves[direction].getConnection().getLocation());
          this.getWorld().printWorld();
          List<Player> roomPlayers = this.getWorld().getRoom(this).getPlayers();
          if (roomPlayers.size() > 1) {
            for(Player currPlayer : roomPlayers){
              if(!currPlayer.equals(this)) {
                this.interact();
              }
            }
            // currRoom = this.getWorld().getRoom(this.getLocation());
          }
        }
      }
    }
  }
  
}


