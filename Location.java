/** Represents a place in a world. */
public class Location{
 protected World world;  // the world that the location lives in
 protected int row=0;
 protected int col=0;
 protected BadLocationException e = new BadLocationException("Invalid location: Please try someplace else");
 
 /** Creates a new location in the world at the specified row and column */
 public Location(World w,int row, int col){
   this.world = w;
   this.row = row;
   this.col = col;
 }
  
 public World getWorld(){ return world;}
 public int getRow(){return row;}
 public int getCol(){return col;}
 
 /** Checks if another location is adjacent (connected with a door)
   * to this current location.
   * 
   * @param other is another location
   * @return true if this location and the other location are adjacent to each other (connected with a door)
   */
 
 public boolean isConnected(Location other){
   // using the information in the world check is this location
   // and other are connected.
   if(!this.getWorld().equals(other.getWorld())){
     return false;
   }
   int rowDifference = this.getRow() - other.getRow();
   int absRowDiff = rowDifference;
   if(rowDifference < 0) { absRowDiff = -absRowDiff; }
   
   int colDifference = this.getCol() - other.getCol();
   int absColDiff = colDifference;
   if(colDifference < 0) { absColDiff = -absColDiff; }
   
   if(absRowDiff + absColDiff == 1){
     Room r1 = this.getWorld().getRoom(this);
     Room r2 = other.getWorld().getRoom(other);
     Room rConnected = null;
     Door[] doors = r1.getDoors();
         
     if(rowDifference == 1) {
       if(doors[0] != null){
         rConnected = doors[0].getConnection();
       }
     } else if(colDifference == 1) {
       if(doors[1] != null){
         rConnected = doors[1].getConnection();
       }
     } else if(rowDifference == -1) {
       if(doors[2] != null){
         rConnected = doors[2].getConnection();
       }
     } else if(colDifference == -1) {
       if(doors[3] != null){
         rConnected = doors[3].getConnection();
       } 
     }
     if(r2.equals(rConnected)){
       return true;
     }     
   }   
   return false;  
 }
 
 
 /** Returns location to the west (left) of the current location
   * 
   * TODO: This will throw an ArrayIndexOutOfBoundsException
   *       You need to deal with this in some way.
   */
 public Location west() throws BadLocationException{
   if(this.getCol() == 0){
     throw e;
   }
   return new Location(world, row, col-1);
 }
 
 /** Returns location to the east (right) of the current location
   * 
   * TODO: This will throw an ArrayIndexOutOfBoundsException
   *       You need to deal with this in some way.
   */
 public Location east() throws BadLocationException{
   if(this.getCol() == world.getSize()[1]-1){
     throw e;
   }
   return new Location(world, row, col+1);
 }
 
 /** Returns location to the north (up) of the current location
   * 
   * TODO: This will throw an ArrayIndexOutOfBoundsException
   *       You need to deal with this in some way.
   */
 public Location north() throws BadLocationException{
   if(this.getRow() == 0){
     throw e;
   }
   return new Location(world, row-1, col);
 }
 
 /** Returns location to the south (down) of the current location
   * 
   * TODO: This will throw an ArrayIndexOutOfBoundsException
   *       You need to deal with this in some way.
   */
 public Location south() throws BadLocationException{
   if(this.getRow() == world.getSize()[0]-1){
     throw e;
   }
   return new Location(world, row+1, col);
 }
 
 @Override
 public String toString(){
   return "("+row+","+col+")";
 }
 
 /** Two locations are equal to each other if they are in the 
   * same world, have the same row and column.
   */
 @Override
 public boolean equals(Object o){
   if( o instanceof Location){
     return this.row == ((Location)o).row && 
            this.col == ((Location)o).col && 
            this.world.equals(((Location)o).world);
   }else{
     return false;
   }
 }
 
}