/**
 * @author ece
 *
 */

import java.util.ArrayList;
import java.util.Random;

public class Game {

	boolean goalAchieved = false;

	ArrayList <GameObject> objects = new ArrayList <GameObject>();
	boolean handsFree = true;
		
	String changeEffect = "";
	
	ArrayList<ArrayList<GameObject>> objPair = new ArrayList<ArrayList<GameObject>>();
	
	String goalObject = "";
	String goalObjectState = "";
 
	String subGoalObject = "";
	String subGoalObjectState = "";
	
	int pairCount = 0;
	
	public Game(int experiment){
		
		GameObject o1 = new GameObject("paper", false, false, false, true, false);
		GameObject o2 = new GameObject("scissors", true, false, true, true, false);
		GameObject o3 = new GameObject("key", true, false, true, true,  false);
		GameObject o4 = new GameObject("glassdoor", false, false, false, true,  true);
		GameObject o5 = new GameObject("woodendoor", false, false, false, true, true);
		
		objects.add(o1);
		objects.add(o2);
		objects.add(o3);
		objects.add(o4);
		objects.add(o5);

	}
	
	public void setObjectList(ArrayList <GameObject> go){
		
		this.objects = go;
		
		//g01.clone?
	}
	
	public ArrayList <GameObject> getObjectList(){
		
		return this.objects;
	}
	
	
	public boolean grab(GameObject obj){

		System.out.println("grab contents "  + obj.getName() + handsFree + obj.getCanBeGrabbed() 
				+ obj.getIsFree()  + obj.getIsObstacle());
		
		boolean performed = false;
		if(handsFree && obj.getCanBeGrabbed() && obj.getIsFree() && !obj.getIsObstacle()){
			
				obj.setIsFree(false);
				obj.setIsGrabbed(true);
				this.handsFree = false;
				
				performed = true;
		}
		
		return performed;
		
	}
	

	public boolean drop(GameObject obj){

		boolean performed = false;
		
		if(obj.getIsGrabbed()){
			
			obj.setIsFree(true);
			obj.setIsGrabbed(false);
			this.handsFree = true;
			
			performed = true;

	}
		return performed;
	}
	
	public String touch(GameObject obj1, GameObject obj2){
		
		String action = "";
		if(obj1.getIsGrabbed() && !obj1.equals(obj2)){
			
			obj1.touchList.add(obj2);
			obj2.touchList.add(obj1);
			
			if(obj1.getName().equals("scissors") && obj2.getName().equals("glassdoor") || 
					obj1.getName().equals("scissors") && obj2.getName().equals("glassbox") ){
				
				this.breakObject(obj1, obj2);
				action = "break";
				
			}else if(obj1.getName().equals("pliers") && obj2.getName().equals("woodendoor")){
				this.unlock(obj1, obj2);
				action = "unlock";
				
			}else if(obj1.getName().equals("knife") && obj2.getName().equals("paper")){
				
				this.cut(obj1, obj2);
				action = "cut";
			}
								
		}
		
		return action;
	}
	
	public boolean breakObject(GameObject obj1, GameObject obj2){
		
			boolean performed = false;
			
			if(obj1.getName().equals("scissors") && obj2.getName().equals("glassdoor")){
				
				obj2.setIsObstacle(false);
				obj2.setState("unlocked");
				
				GameObject o6 = new GameObject("pliers", true, false, true, true, false);
				
				this.objects.add(o6);
			
				performed = true;
				
			}else if(obj1.getName().equals("scissors") && obj2.getName().equals("glassbox")){
				
				obj2.setIsObstacle(false);
				obj2.setState("broken");

				GameObject o8 = new GameObject("knife", true, false, true, true, false);

				this.objects.add(o8);
				
				performed = true;
			}else{
				
				performed = false;
			}
			
			if(performed){

				/*this.drop(obj1);
				this.drop(obj2);*/
				
				if(this.goalObject.equals(obj2.getName()) && this.goalObjectState.equals("broken"))
				{
					this.goalAchieved = true;
				}
						
						
			}
			
			return performed;
		
	}
	

	public boolean unlock(GameObject obj1, GameObject obj2){
		
		boolean performed = false;
		if(obj1.getName().equals("pliers") && obj2.getName().equals("woodendoor")){
			
				obj2.setState("unlocked");
				obj2.setIsObstacle(false);

				GameObject o7 = new GameObject("glassbox", true, false, true, true, true);
				
				this.objects.add(o7);
				
				performed = true;
				
			}
		
		if(performed){

		/*	this.drop(obj1);
			this.drop(obj2);*/
			
			if(this.goalObject.equals(obj2.getName()) && this.goalObjectState.equals("unlocked"))
			{
				this.goalAchieved = true;
			}
					
					
		}
		return performed;
				
	}
	
	public boolean cut(GameObject obj1, GameObject obj2){
		
			boolean performed = false;
			
			if(obj1.getName().equals("knife") && obj2.getName().equals("paper")){
				
				obj2.setState("cut");
								
				performed = true;
			}
			
			if(performed){

				/*this.drop(obj1);
				this.drop(obj2);*/
				
				if(this.goalObject.equals(obj2.getName()) && this.goalObjectState.equals("cut"))
				{
					this.goalAchieved = true;
				}
						
						
			}
			
			return performed;
	
	}	
	
	public boolean isGoalAchieved(){
		
		return this.goalAchieved;
	}
	
	public void printPairs(){
		
		for(int i = 0; i < this.objPair.size(); i++){
			
			System.out.println("Pair " + i + " " + this.objPair.get(i).get(0).getName() + " " + this.objPair.get(i).get(1).getName());
		}
	}
	
	public boolean checkPair(GameObject o1, GameObject o2){
		
		boolean pairExists = false;
		
		for(int i = 0; i < this.pairCount; i++){
			
			if((this.objPair.get(i).get(0).getName().equals(o1.getName()) && this.objPair.get(i).get(1).getName().equals(o2.getName()))
					|| this.objPair.get(i).get(0).getName().equals(o2.getName()) && this.objPair.get(i).get(1).getName().equals(o1.getName())){
				
				pairExists = true;
			}
		}
		
		return pairExists;
	}
	
	public void setGoal(String name, String state){
		
		this.goalObject = name;
		this.goalObjectState = state;
		
	}
	
	public String getGoalObject(){
		
		return this.goalObject;
	}
	

	public String getGoalObjectState(){
		
		return this.goalObjectState;
	}
		
}
