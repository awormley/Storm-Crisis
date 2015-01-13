
public class Native extends Plant{
	boolean spawnsButterflies = false;
	boolean spawnsAnimals = false;
	public Native(int x, int y){
		super(x,y);
		inttype = 2;
		//plant type
		//scarlet oak = 0, spicebush = 1,
		//butterfly weed = 2, christmas fern = 3
	}
	public void setSpawns(boolean a, boolean b){
		spawnsButterflies = b;
		spawnsAnimals = a;
	}
	public int getStrength(){
		return strength+size;
	}
	
}
