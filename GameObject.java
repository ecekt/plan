import java.util.ArrayList;

public class GameObject {

	private String name = "";
	
	private boolean isFree;
	
	private boolean isGrabbed;
	
	private boolean canBeGrabbed;
	
	private boolean isVisible;
	
	private boolean isObstacle;
	
	private String state = "neutral";
	
	ArrayList <GameObject> touchList = new ArrayList <GameObject>();

	public GameObject(){
		
		this.name = null;
		this.isFree = false;
		this.isGrabbed = false;
		this.canBeGrabbed = false;
		this.isObstacle = false;
	}
	
	public GameObject(String name, boolean isFree, boolean isGrabbed, boolean canBeGrabbed, boolean isVisible, boolean isObstacle){
	
		this.name = name;
		this.isFree = isFree;
		this.isGrabbed = isGrabbed;
		this.canBeGrabbed = canBeGrabbed;
		this.isVisible = isVisible;
		this.isObstacle = isObstacle;
	}

	public void setName(String name){
		
		this.name = name;
		
	}

	public void setIsFree(boolean isFree){
		
		this.isFree = isFree;
	}

	public void setIsGrabbed(boolean isGrabbed){
		
		this.isGrabbed = isGrabbed;
	}

	public void setIsVisible(boolean isVisible){
		
		this.isVisible = isVisible;
	}
	
	public void setCanBeGrabbed(boolean canBeGrabbed){
		
		this.canBeGrabbed = canBeGrabbed;
	}

	public void setIsObstacle(boolean isObstacle){
		
		this.isObstacle = isObstacle;
	}

	public void setState(String state){
		
		this.state = state;
	}
	
	public String getName(){
		
		return this.name;		
	}

	public boolean getIsFree(){
		
		return this.isFree;
	}
	
	public boolean getIsGrabbed(){
		
		return this.isGrabbed;
	}

	public boolean getCanBeGrabbed(){
		
		return this.canBeGrabbed;
	}

	public boolean getIsVisible(){
		
		return this.isVisible;
	}

	public boolean getIsObstacle(){
		
		return this.isObstacle;
	}
	
	public String getState(){
		
		return this.state;
	}
}