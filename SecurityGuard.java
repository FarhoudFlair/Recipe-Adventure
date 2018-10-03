import java.util.List;

public class SecurityGuard extends Player{
	
	private static final int SG_HEALTH = 100; //not using this
	private static final String SG_NAME = "Mr. Security Guard";
	private boolean hasCheckedYou;
	
	public SecurityGuard(World w, Location location, List<Thing>  things, Thing goal){
		super(w, SG_NAME, location, SG_HEALTH, things, goal);
		hasCheckedYou = false;
	}
	
	// talks to the human if the human is in his room & hasnt previously shown ID
	public void play(){
		//get the list of players in the current room
		List<Player> roomPlayers = this.w.getRoom(this.location).getPlayers();
		for(int i=0; i<roomPlayers.size(); i++){
			Player currPlayer = roomPlayers.get(i);
			if((currPlayer.getClass().getName()=="Human")&&(hasCheckedYou==false)){
				System.out.println(this.name + ": " + "Do you work here? Let me see some ID...");
			}
		}
	}
	
	// if the human doesn't have an id - sends him back to entrance
	public void interact(Player p){
		List<Thing> humanThings = p.getThings();
		//check the human for ID
		boolean hasID = false;
		for(int j=0; j<humanThings.size(); j++){
			Thing currThing = humanThings.get(j);
			if(currThing.getClass().getName()=="ID"){
				hasID = true;
			}
		}
			
		if(hasID==true){
			System.out.println(this.name + ": " + "Is that your ID? Nice pic, heh.");
			hasCheckedYou=true;
		}
		else{
			System.out.println(this.name + ": " + "Get out of here, we're closed! Front door's that way.");
			//kick the human out
			this.w.getRoom(this.location).removePlayer(p);
			//put the human in the lobby
			this.w.getRoom(this.w.getEntrance()).addPlayer(p);
			p.setLocation(this.w.getEntrance());
		}
	}
}