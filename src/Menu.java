import java.awt.Image;

public class Menu{
	
	public int numTools = 5;
	public Tool[] Tool_Box = new Tool[numTools];
	public String[] Tool_Desc; 
	public Image Background; 
	public Image hide_Tab; 
	public int Hide_Delay;
	int cooldowns[] = {0,0,0,0,0};
	int cdtimes[] = {0,0,0,0,0};
	
	
	public Tool[] getToolBox(){
		return Tool_Box;
	}
	
	public int getNumTools(){
		return numTools;
	}
	public int getBaseCooldownFor(int toolnum){
	//	System.out.println("Base CD for " + toolnum + " is " + cdtimes[toolnum]);
		return cdtimes[toolnum];
		
	}
	public int getRemCooldownFor(int toolnum){
	//	System.out.println("Current CD for " + toolnum + " is " + cooldowns[toolnum]);
		return cooldowns[toolnum];
	}
	public void setUsed(int toolnum){
	//	System.out.println("Cooldown for " + toolnum + " set to " + cdtimes[toolnum]);
		cooldowns[toolnum] = cdtimes[toolnum];
	}
	public void decrementAllCooldowns(){
		//System.out.println("All CDs decremented");
		for(int i = 0; i < 5; i++){
			if(cooldowns[i] > 0)
				cooldowns[i]--;
		}
		
	}
	public void Pause(){}
	
	public void Use_Element(){}
	
	public void checkHide(){}
	
	public Menu(){
		//add tools using to array tool box
		Tool_Box[1] = new CommonMilkweedTool();
		Tool_Box[0] = new SasafrasTool();
		Tool_Box[2] = new JapaneseMapleTool();
		Tool_Box[3] = new ButterflyBushTool();
		Tool_Box[4] = new Lawnmower();
		for(int i = 0; i < 5;i++){
			cdtimes[i] = Tool_Box[i].getCooldown();
		}
	}
	
}
	


