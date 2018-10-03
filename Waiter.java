import java.util.List;
import java.util.Random;

public class Waiter extends Player{

	private static String name = "Big Ol' Waiter";
	private static int waiterHealth = 100;
	private static Thing goal = null;

	public Waiter(World w, String name, Location location, int health, List<Thing> things, Thing goal){
		super(w, name, location, health, things, goal);
		WaiterUniform waiterUniform = new WaiterUniform();
		this.addThing(waiterUniform);
	}

	public Waiter(World w, Location location, List<Thing> things){
		this(w, name, location, waiterHealth, things, goal);
		WaiterUniform waiterUniform = new WaiterUniform();
		this.addThing(waiterUniform);
	}

	@Override
	public void play(){
		Room currRoom = this.getWorld().getRoom(this.getLocation());
   		int[] worldSize = this.getWorld().getSize();
   		if(worldSize[0] * worldSize[1] < 5){
     		Door[] moves = currRoom.getDoors();
     		java.util.Random wander = new java.util.Random();
     	
	     	if(wander.nextInt(10) < 10){
	       		int direction = wander.nextInt(4);
	       	
		      	if(moves[direction] != null){
		         	move(moves[direction].getConnection().getLocation());
		       	}
		       	currRoom = this.getWorld().getRoom(this.getLocation());
		    }
		}
       	
	}

 	@Override
	public void interact(Player p){
   // allows for some interaction with a player
		WaiterUniform waiterUniform = (WaiterUniform)this.getThings().get(0);
		System.out.println("Oh you want my ID? You're going to have to fight me for it.");
		while (p.getHealth() > 0 && this.getHealth() > 0){
			p.setHealth(p.getHealth() - 20);
			this.setHealth(this.getHealth() - 40);
		}
		if (p.getHealth() > 0){
			System.out.println(p.getName() + " overpowered the " + this.name + "!");
          	waiterUniform.setLocation(this.getLocation());
          	Room currRoom = waiterUniform.getLocation().getWorld().getRoom(waiterUniform.getLocation());
          	currRoom.addThing(waiterUniform);
          	this.removeThing(waiterUniform);
		}else{
			System.out.println("The Big Ol' Waiter defeated you...");
			p.setHealth(0);
		}
 	}

}