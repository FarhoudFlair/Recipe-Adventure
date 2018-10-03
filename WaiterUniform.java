public class WaiterUniform extends Thing{

	private static String NAME = "WaiterUniform";
	private static int VALUE = 5000;
	
	public WaiterUniform(){
		super(NAME, null, VALUE);
	}

	@Override
	public void interact(Player p){
		p.setHealth(VALUE);
		System.out.println("WOW! This uniform makes me feel INVINCIBLE!");
		System.out.println("Player health: " + p.getHealth());
	}
}