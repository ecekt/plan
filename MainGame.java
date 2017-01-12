import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Ece K. Takmaz
 *
 */
public class MainGame {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		Game g1 = new Game(1);
		//Game g2 = new Game(2);
		
		g1.setGoal("paper", "cut");
		
		String path = "RoomInitial.pks";
		
		String initialPath = "RoomInitialTemplate.pks";
		createInitialPlan(initialPath, g1, path);
		
		while(!g1.goalAchieved){
			
			//System.out.println("while start");
			
			executePKS(path, "planInitial.txt", "-d");
			
			ArrayList <String> operationSequence = new ArrayList <String>();
	
			readPlan("planInitial.txt", operationSequence);
			
			System.out.println("ops M" + operationSequence.size());
			
			if(operationSequence.size() > 0){
				
				performPlan(path, g1, operationSequence, path);
				
			}
			
			exploreAndUpdate(path, g1, path);
		
		}
		
		
		executePKS(path, "planInitial.txt", "-d");
		
		ArrayList <String> operationSequence = new ArrayList <String>();
	
		readPlan("planInitial.txt", operationSequence);
									
	}
	

	
	public static void createInitialPlan(String path, Game g, String writePath) throws IOException{
		    
		File f = new File(path);
	    FileInputStream in = new FileInputStream(f);
	    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	    
	    ArrayList <String> originalLines = new ArrayList <String>(); 
	    
	    String line = "";
	
	   // read all words
	    while ((line = reader.readLine()) != null ) {
	   	 
	    	originalLines.add(line);
	    	
	    }
	    
	/*    for(int l = 0; l < originalLines.size(); l++){
	    	System.out.println(originalLines.get(l));
	    }
	    
	    reader.close();
	
	*/
	    
	    for(int i = 0; i<originalLines.size(); i++){
	    	
	    	if(originalLines.get(i).contains("<!--constants-->")){
	    		
	    		String constants = "<!--constants-->hand, uncut, cut, broken, unlocked";

	    		for(int o = 0; o < g.getObjectList().size(); o++){
	    			
	    			if(g.getObjectList().get(o).getIsVisible()){
	    				
	    				constants = constants + ", " + g.getObjectList().get(o).getName();
	    			}
	    		}
	    		
	    		originalLines.set(i, constants);     
	    	}  
	    	
	    	if(originalLines.get(i).contains("<!--unlock-->")){
	    		
	    		String unlock = "<!--unlock-->";
	    			
	    		for(int o = 0; o < g.getObjectList().size(); o++){
	    			
	    			if(g.getObjectList().get(o).getName().equals("key")){
	    				
	    				unlock = unlock + "K(key = ?x) ^ K(woodendoor = ?y) => del(Kf, is_obstacle(woodendoor)) ^ add(Kf, state(woodendoor) = unlocked); "
	    						+ "K(key = ?x) ^ K(glassdoor = ?y) => del(Kf, is_obstacle(glassdoor)) ^ add(Kf, state(glassdoor) = unlocked);";
	    				
	    			}
	    		}

	    		originalLines.set(i, unlock);     
	    	}
	    	
	    	if(originalLines.get(i).contains("<!--cut-->")){
	    		
	    		String cut = "<!--cut-->";
	    			
	    		for(int o = 0; o < g.getObjectList().size(); o++){
	    			
	    			if(g.getObjectList().get(o).getName().equals("scissors")){
	    				
	    				cut = cut + "K(scissors = ?x) ^ K(paper = ?y) => add(Kf, state(paper) = cut); ";
	    				
	    			}
	    		}

	    		originalLines.set(i, cut);     
	    		
	    	}
	    	
	    	
	    	if(originalLines.get(i).contains("<!--is_object-->")){
	    		
	    		String isobj = "<!--is_object-->";
	    			
	    		for(int o = 0; o < g.getObjectList().size(); o++){
	    			
	    			if(g.getObjectList().get(o).getIsVisible()){
	    				
	    				isobj = isobj + "add(Kf, is_object(" + g.getObjectList().get(o).getName() + ")); ";
	    				
	    			}
	    				
	    		}
	    		
	    		originalLines.set(i, isobj);     
	    		
	    	}
	    	
	    	
	    	if(originalLines.get(i).contains("<!--can_be_grabbed-->")){
	    		
	    		String cbgrabbed = "<!--can_be_grabbed-->";
	    			
	    		for(int o = 0; o < g.getObjectList().size(); o++){
	    			
	    			if(g.getObjectList().get(o).getIsVisible() && g.getObjectList().get(o).getCanBeGrabbed()){
	    				
	    				cbgrabbed = cbgrabbed + "add(Kf, can_be_grabbed(" + g.getObjectList().get(o).getName() + ")); ";
	    				
	    			}
	    				
	    		}

	    		
	    		originalLines.set(i, cbgrabbed);     
	    		
	    	}


	    	
	    	
	    	if(originalLines.get(i).contains("<!--is_free-->")){
	    		
	    		String isfree = "<!--is_free-->";
	    			
	    		for(int o = 0; o < g.getObjectList().size(); o++){
	    			
	    			if(g.getObjectList().get(o).getIsVisible() && g.getObjectList().get(o).getIsFree()){
	    				
	    				isfree = isfree + "add(Kf, is_free(" + g.getObjectList().get(o).getName() + ")); ";
	    				
	    			}
	    				
	    		}
	    		
	    		originalLines.set(i, isfree);     
	    		
	    	}
	    	
	    	

	    	if(originalLines.get(i).contains("<!--is_obstacle-->")){
	    		
	    		String isobs = "<!--is_obstacle-->";
	    			
	    		for(int o = 0; o < g.getObjectList().size(); o++){
	    			
	    			if(g.getObjectList().get(o).getIsVisible() && g.getObjectList().get(o).getIsObstacle()){
	    				
	    				isobs = isobs + "add(Kf, is_obstacle(" + g.getObjectList().get(o).getName() + ")); ";
	    				
	    			}
	    				
	    		}
	    		
	    		originalLines.set(i, isobs);     
	    		
	    	}
	    	
	    	if(originalLines.get(i).contains("<!--goal-->")){
	    		
	    		String goal = "<!--goal-->";
	    			
	    		goal = goal + "K(state(" + g.goalObject + ")= " + g.goalObjectState + ")";
	    		
	    		for(int o = 0; o < g.getObjectList().size(); o++){
	    			
	    			if(g.getObjectList().get(o).getIsVisible() && g.getObjectList().get(o).getCanBeGrabbed()){
	    				
	    				goal = goal + "^ K(is_free(" + g.getObjectList().get(o).getName() + "))";
	    				
	    			}
	    				
	    		}
	    		
	    		originalLines.set(i, goal);     
	    		
	    	}
	    	
	    
	    
	    }
	    
	    File fout = new File(writePath);
		FileOutputStream out = new FileOutputStream(fout);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
		
	    for(int i = 0; i < originalLines.size(); i++){
	    
	    	writer.write(originalLines.get(i));
	    	writer.write("\n");
	    }
		writer.close();

	
		
	}
	
	public static void createInitialSubplan(String subpath, Game g, String originalpath) throws IOException{
	    
		File f = new File(originalpath);
	    FileInputStream in = new FileInputStream(f);
	    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	    
	    ArrayList <String> originalLines = new ArrayList <String>(); 
	    
	    String line = "";
	
	   // read all words
	    while ((line = reader.readLine()) != null ) {
	   	 
	    	originalLines.add(line);
	    	
	    }
	

	    for(int i = 0; i<originalLines.size(); i++){
	    	
    	if(originalLines.get(i).contains("<!--goal-->")){
    		
    		String goal = "<!--goal-->";

    		if(g.subGoalObject.equals("glassdoor")){
    			
    			goal = goal + "<!--goal-->K(state(glassdoor)= unlocked)";
    			
    		}else if(g.subGoalObject.equals("woodendoor")){
    			

    			goal = goal + "<!--goal-->K(state(woodendoor)= unlocked)";
    			
    			
    		}else if(g.subGoalObject.equals("glassbox")){
    			

    			goal = goal + "<!--goal-->K(state(glassbox)= broken)";
    			
    			
    		}
    			
    		for(int o = 0; o < g.getObjectList().size(); o++){
    			
    			if(g.getObjectList().get(o).getIsVisible() && g.getObjectList().get(o).getCanBeGrabbed()){
    				
    				goal = goal + "^ K(is_free(" + g.getObjectList().get(o).getName() + "))";
    				
    			}
    				
    		}
    		
    		originalLines.set(i, goal);     
    		
    	}
    	
        }
	    
	    File fout = new File(subpath);
		FileOutputStream out = new FileOutputStream(fout);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
		
	    for(int i = 0; i < originalLines.size(); i++){
	    
	    	writer.write(originalLines.get(i));
	    	writer.write("\n");
	    }
		writer.close();
	
	
		
	}



	public static void readPlan(String path, ArrayList<String> opSequence) throws IOException{
		
		//System.out.println("read");
		//open txt and start reading the plan
				File f = new File(path);
		        FileInputStream in = new FileInputStream(f);
		        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		        
		       String line = reader.readLine();

		       // read all lines
		        while (line != null && !line.contains("<init>") ) {
		       	 
		       	     	//System.out.println(line);
		       	     	line = reader.readLine();
		        }
		        
		        //at <init> now, continue to get the plan steps
		        
		        line = reader.readLine();
		        
		        while (line != null && !line.isEmpty())  {
		          	 
		        	opSequence.add(line.trim());
		        	
		            line = reader.readLine();
		   	     	
		        }
		        reader.close();
		        
		        
		       for(int l = 0; l < opSequence.size(); l++){
		    	   
		    	   System.out.println(opSequence.get(l));
		    	   
		       }

		
	}

	
	public static boolean performPlan(String path, Game g, ArrayList<String> opSequence, String writePath) throws IOException{
		
		System.out.println("perform");
		boolean performed = true;
		
		for(int o = 0; o < opSequence.size(); o++){
			
			if(opSequence.get(o).indexOf(",") == -1){
				
				//only one parameter, grab, drop
				String action = opSequence.get(o).substring(0, opSequence.get(o).indexOf("("));
	
				String obj1 = opSequence.get(o).substring(opSequence.get(o).indexOf("(") + 1, opSequence.get(o).indexOf(")"));
				
				for(int a = 0; a < g.getObjectList().size(); a++){
					
					if(g.getObjectList().get(a).getName().equals(obj1)){
						
						if(action.equals("grab")){
							
							g.grab(g.getObjectList().get(a));
							
						}else if(action.equals("drop")){
							
							g.drop(g.getObjectList().get(a));
							
						}
					}
				}
			}else{
	
				//two parameters,cut,break,unlock				
				String action = opSequence.get(o).substring(0, opSequence.get(o).indexOf("("));
				
				String obj1 = opSequence.get(o).substring(opSequence.get(o).indexOf("(") + 1, opSequence.get(o).indexOf(","));
				
				String obj2 = opSequence.get(o).substring(opSequence.get(o).indexOf(",") + 1, opSequence.get(o).indexOf(")"));
				
				
				for(int a = 0; a < g.getObjectList().size(); a++){
					
					if(g.getObjectList().get(a).getName().equals(obj1)){
						
						for(int b = 0; b < g.getObjectList().size(); b++){
							
							if(g.getObjectList().get(b).getName().equals(obj2)){
								
								if(action.equals("break")){
									
									boolean breakPerformed = g.breakObject(g.getObjectList().get(a), g.getObjectList().get(b));
									
									if(!breakPerformed){
										
										//change effects in the plan
										System.out.println("no break performed");
										
										removeEffect2(path, "break", g.getObjectList().get(a), g.getObjectList().get(b), writePath);
										
										performed = false;
										
									}else{
										
										//add object isfree to goal
										
										
									}
									
								}else if(action.equals("cut")){
									
									boolean cutPerformed = g.cut(g.getObjectList().get(a), g.getObjectList().get(b));
									
									if(!cutPerformed){
										
										//change effects in the plan
										System.out.println("no cut performed");
																		
										removeEffect2(path, "cut", g.getObjectList().get(a), g.getObjectList().get(b), writePath);
										
										performed = false;
									}
									
								}else if(action.equals("unlock")){
									
									boolean unlockPerformed = g.unlock(g.getObjectList().get(a), g.getObjectList().get(b));
									
									if(!unlockPerformed){
										
										//change effects in the plan
										System.out.println("no unlock performed");
										
										removeEffect2(path, "unlock", g.getObjectList().get(a), g.getObjectList().get(b), writePath);
										
										performed = false;
									}
								}
								

								g.getObjectList().get(a).touchList.add(g.getObjectList().get(b));

								g.getObjectList().get(b).touchList.add(g.getObjectList().get(a));
								
								ArrayList <GameObject> pair = new ArrayList <GameObject>();
								pair.add(g.getObjectList().get(a));
								pair.add(g.getObjectList().get(b));
								
								if(!g.checkPair(g.getObjectList().get(a), g.getObjectList().get(b))){
									g.objPair.add(pair);

									g.pairCount++;
								}
								g.printPairs();
								
							}
						}
					}
				
				}
				
			}
			
						
		}
		
		return performed;
	}



public static boolean performSubplan(String subpath, Game g, ArrayList<String> opSequence, String mainPath) throws IOException{
		
		System.out.println("perform");
		boolean performed = true;
		
		for(int o = 0; o < opSequence.size(); o++){
			
			if(opSequence.get(o).indexOf(",") == -1){
				
				//only one parameter, grab, drop
				String action = opSequence.get(o).substring(0, opSequence.get(o).indexOf("("));
	
				String obj1 = opSequence.get(o).substring(opSequence.get(o).indexOf("(") + 1, opSequence.get(o).indexOf(")"));
				
				for(int a = 0; a < g.getObjectList().size(); a++){
					
					if(g.getObjectList().get(a).getName().equals(obj1)){
						
						if(action.equals("grab")){
							
							g.grab(g.getObjectList().get(a));
							
						}else if(action.equals("drop")){
							
							g.drop(g.getObjectList().get(a));
							
						}
					}
				}
			}else{
	
				//two parameters,cut,break,unlock				
				String action = opSequence.get(o).substring(0, opSequence.get(o).indexOf("("));
				
				String obj1 = opSequence.get(o).substring(opSequence.get(o).indexOf("(") + 1, opSequence.get(o).indexOf(","));
				
				String obj2 = opSequence.get(o).substring(opSequence.get(o).indexOf(",") + 1, opSequence.get(o).indexOf(")"));
				
				
				for(int a = 0; a < g.getObjectList().size(); a++){
					
					if(g.getObjectList().get(a).getName().equals(obj1)){
						
						for(int b = 0; b < g.getObjectList().size(); b++){
							
							if(g.getObjectList().get(b).getName().equals(obj2)){
								
								if(action.equals("break")){
									
									boolean breakPerformed = g.breakObject(g.getObjectList().get(a), g.getObjectList().get(b));
									
									if(!breakPerformed){
										
										//change effects in the plan
										System.out.println("no break performed");
										
										removeEffect2(subpath, "break", g.getObjectList().get(a), g.getObjectList().get(b), subpath);
										removeEffect2(mainPath, "break", g.getObjectList().get(a), g.getObjectList().get(b), mainPath);
										
										performed = false;
										
									}			else{
										
										//add object isfree to goal
										
									
									}						
									
								}else if(action.equals("cut")){
									
									boolean cutPerformed = g.cut(g.getObjectList().get(a), g.getObjectList().get(b));
									
									if(!cutPerformed){
										
										//change effects in the plan
										System.out.println("no cut performed");
																		
										removeEffect2(subpath, "cut", g.getObjectList().get(a), g.getObjectList().get(b), subpath);
										removeEffect2(mainPath, "cut", g.getObjectList().get(a), g.getObjectList().get(b), mainPath);
										
										performed = false;
									}
									
								}else if(action.equals("unlock")){
									
									boolean unlockPerformed = g.unlock(g.getObjectList().get(a), g.getObjectList().get(b));
									
									if(!unlockPerformed){
										
										//change effects in the plan
										System.out.println("no unlock performed");
										
										removeEffect2(subpath, "unlock", g.getObjectList().get(a), g.getObjectList().get(b), subpath);
										removeEffect2(mainPath, "unlock", g.getObjectList().get(a), g.getObjectList().get(b), mainPath);
										
										performed = false;
									}
								}
								

								g.getObjectList().get(a).touchList.add(g.getObjectList().get(b));

								g.getObjectList().get(b).touchList.add(g.getObjectList().get(a));
								
								ArrayList <GameObject> pair = new ArrayList <GameObject>();
								pair.add(g.getObjectList().get(a));
								pair.add(g.getObjectList().get(b));
								if(!g.checkPair(g.getObjectList().get(a), g.getObjectList().get(b))){
									g.objPair.add(pair);

									g.pairCount++;
								}
								g.printPairs();
								
							}
						}
					}
				
				}
				
			}
			
						
		}
		
		return performed;
	}

	public static void executePKS(String pksfile, String outputfile, String searchtype) throws IOException, InterruptedException	{
		
		// String command= "/usr/bin/gnome-terminal"; 
	//	System.out.println("exec");
         Runtime rt = Runtime.getRuntime(); 
         Process pr = rt.exec("./pks " + pksfile + " -o " + outputfile);
     
         Thread.sleep(500);
         
        /*String line=null;
         
	    BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream())); 
	    	
	     while( (line=input.readLine())!=null) {
		                        
		    	   System.out.println(line);
		       }*/
	}
	
	public static void removeEffect2(String path, String action, GameObject go1, GameObject go2, String writePath) throws IOException{
		
		System.out.println("removing " + action + " " + go1.getName() + " " + go2.getName());
		
		File f = new File(path);
	    FileInputStream in = new FileInputStream(f);
	    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	    
	    ArrayList <String> originalLines = new ArrayList <String>(); 
	    
	    String line = "";
	
	   // read all words
	    while ((line = reader.readLine()) != null ) {
	   	 
	    	originalLines.add(line);
	    	
	    }
	    
	    /*
	    for(int l = 0; l < originalLines.size(); l++){
	    	System.out.println(originalLines.get(l));
	    }
	    */
	    
	    for(int i = 0; i<originalLines.size(); i++){
	    	
	    	if(originalLines.get(i).contains("<!--" + action + "-->")){
	    		
	    		String removed = "";
	    		
	    		int startIndexOfEffect = originalLines.get(i).indexOf("K(" + go1.getName() + " = ?x) ^ K(" + go2.getName() + " = ?y)");

	    		int indOfSemiColon = originalLines.get(i).indexOf(";", startIndexOfEffect);
	    		
	    		System.out.println("rem " + originalLines.get(i));
	    		removed = originalLines.get(i).substring(0, startIndexOfEffect) + originalLines.get(i).substring(indOfSemiColon + 1);
	    		
	    		//System.out.println("rem " + removed);
	    		originalLines.set(i, removed);
	    		
	    	}
	    		    	
	    }
	    
	    File fout = new File(writePath);
		FileOutputStream out = new FileOutputStream(fout);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
		

	    for(int i = 0; i < originalLines.size(); i++){
	    
	    	writer.write(originalLines.get(i));
	    	writer.write("\n");
	    }
		writer.close();
	}
	
	public static void addEffect2(String path, String action, GameObject go1, GameObject go2, String writePath) throws IOException{
		
		System.out.println("adding " + action + " " + go1.getName() + " " + go2.getName());
		
		File f = new File(path);
	    FileInputStream in = new FileInputStream(f);
	    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	    
	    ArrayList <String> originalLines = new ArrayList <String>(); 
	    
	    String line = "";
	
	   // read all words
	    while ((line = reader.readLine()) != null ) {
	   	 
	    	originalLines.add(line);
	    	
	    }
	    
	    for(int i = 0; i<originalLines.size(); i++){
	    	
	    	if(originalLines.get(i).contains("<!--" + action + "-->")){
	    		
	    		if(action.equals("cut")){
	    			
	    			originalLines.set(i, originalLines.get(i) 
	    					+ "K(" + go1.getName() + " = ?x) ^ K(" + go2.getName() + " = ?y) => add(Kf, state(" + go2.getName() + ") = cut);");
	    			
	    		}else if(action.equals("unlock")){
	    			
	    			originalLines.set(i, originalLines.get(i) + "K(" + go1.getName() + " = ?x) ^ K(" + go2.getName() + " = ?y) => del(Kw, is_obstacle(" + go2.getName()
	    					+ ")) ^ add(Kf, is_object(glassbox)) ^ add(Kf, is_free(glassbox)) ^ add(Kf, is_obstacle(glassbox)) ^ add(Kf, state(woodendoor) = unlocked);");
	    					
	    			
	    		}else if(action.equals("break") && go2.getName().equals("glassdoor")){
	    			
	    			originalLines.set(i, originalLines.get(i) + "K(" + go1.getName() + " = ?x) ^ K(" + go2.getName() + " = ?y) => del(Kw, is_obstacle(" + go2.getName() + "))^ add(Kf, is_object(pliers)) ^ add(Kf, is_free(pliers)) ^ add(Kf, can_be_grabbed(pliers))^ add(Kf, state(glassdoor) = broken);");
	    													  
	    		}else if(action.equals("break") && go2.getName().equals("glassbox")){
	    			
	    			originalLines.set(i, originalLines.get(i) + "K(" + go1.getName() + " = ?x) ^ K(" + go2.getName() + " = ?y) => add(Kf, is_object(knife)) ^ add(Kf, is_free(knife)) ^ add(Kf, can_be_grabbed(knife)) ^ add(Kf, state(glassbox) = broken);");

	    		}    		
	    		
	    	}
	    		    	
	    }
	    
	    File fout = new File(writePath);
		FileOutputStream out = new FileOutputStream(fout);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
		

	    for(int i = 0; i < originalLines.size(); i++){
	    
	    	writer.write(originalLines.get(i));
	    	writer.write("\n");
	    }
		writer.close();
	}
		
	
	public static void exploreAndUpdate(String path, Game g, String writePath) throws IOException, InterruptedException{
		
		boolean explored = false;
		
		//if there are objects that we can utilize immediately on the goal-related object try them
		//otherwise try to eliminate obstacles
		
		for(int i = 0; i < g.objects.size(); i++){
			
			if(g.objects.get(i).getIsVisible() && g.objects.get(i).getIsFree() && g.objects.get(i).getCanBeGrabbed() && !g.objects.get(i).getIsGrabbed()){
	
				for(int o = 0; o < g.objects.size(); o++){
					
					if(g.objects.get(o).getName().equals(g.getGoalObject())){
						
						if(!g.checkPair(g.objects.get(i), g.objects.get(o))){
							
							System.out.println("explore here " + g.objects.get(i).getName() + g.objects.get(o).getName());
							
							g.grab(g.objects.get(i));
							
							//System.out.println("here " + g.objects.get(i).getName());
							
						String action = g.touch(g.objects.get(i), g.objects.get(o));

						System.out.println(action + " " + g.objects.get(i).getName() + " " +  g.objects.get(o).getName());
						explored = true;

						g.drop(g.objects.get(i));
						
						if(!action.equals("")){
														
							addEffect2(path, action, g.objects.get(i), g.objects.get(o), writePath);
						//	addObjFree(path, g.getObjectList().get(g.getObjectList().size()-1), writePath);
							System.out.println("adding main " +  g.getObjectList().get(g.getObjectList().size()-1).getName());
							//removeEffect2(path, action, g.objects.get(i), g.objects.get(o), writePath);
							
						}else{
							

							}
						
						g.getObjectList().get(i).touchList.add(g.getObjectList().get(o));

						g.getObjectList().get(o).touchList.add(g.getObjectList().get(i));
						
						ArrayList <GameObject> pair = new ArrayList <GameObject>();
						pair.add(g.getObjectList().get(i));
						pair.add(g.getObjectList().get(o));
						if(!g.checkPair(g.getObjectList().get(i), g.getObjectList().get(o))){
							g.objPair.add(pair);

							g.pairCount++;
						}
						g.printPairs();
						return;
						
						}
					}
				}
			}
		}
						


		//no visible graspable free objects found, or the pair already existed
		//try to eliminate obstacles here
		
		
		for(int i = 0; i < g.objects.size(); i++){
			
			if(g.objects.get(i).getIsVisible() && g.objects.get(i).getIsObstacle()){
		
				System.out.println("subplan");
				//sub-goal is to eliminate obs.
				g.subGoalObject = g.objects.get(i).getName();
				
				String subpath = "subplan.pks";

				createInitialSubplan(subpath, g, path);
					
				while(g.objects.get(i).getIsObstacle()){
				//while(g.subGoalObject)
				executePKS(subpath, "subPlanInitial.txt", "-d");
					
				ArrayList <String> operationSequence = new ArrayList <String>();
				
				readPlan("subPlanInitial.txt", operationSequence);
				
				System.out.println("ops S" + operationSequence.size());
				
				if(operationSequence.size() > 0){
					
					performSubplan(subpath, g, operationSequence, path);
				
					
				}
				
				exploreAndUpdateSubplan(subpath, g, path);
								
				}
			}
			}
	}
	
public static void exploreAndUpdateSubplan(String path, Game g, String writePath) throws IOException, InterruptedException{
	
		boolean explored = false;
		
		//if there are objects that we can utilize immediately on the goal-related object try them
		//otherwise try to eliminate obstacles
		
		for(int i = 0; i < g.objects.size(); i++){
			
			if(g.objects.get(i).getIsVisible() && g.objects.get(i).getIsFree() && g.objects.get(i).getCanBeGrabbed() && !g.objects.get(i).getIsGrabbed()){
	
				for(int o = 0; o < g.objects.size(); o++){
					
					if(g.objects.get(o).getName().equals(g.subGoalObject) && g.objects.get(o).getIsObstacle()){
						
						if(!g.checkPair(g.objects.get(i), g.objects.get(o))){
							
							System.out.println("explore here Sub " + g.objects.get(i).getName() + g.objects.get(o).getName());
							
							g.grab(g.objects.get(i));
							
						System.out.println("here to grab " + g.objects.get(i).getName());
							
						String action = g.touch(g.objects.get(i), g.objects.get(o));

						System.out.println(action + " " + g.objects.get(i).getName() + " " +  g.objects.get(o).getName());
						explored = true;
						g.drop(g.objects.get(i));
						if(!action.equals("")){
							
							addEffect2(path, action, g.objects.get(i), g.objects.get(o), path);

							addEffect2(writePath, action, g.objects.get(i), g.objects.get(o), writePath);
							addObjFree(path, g.getObjectList().get(g.getObjectList().size()-1), path);
							System.out.println("adding SUBP " +  g.getObjectList().get(g.getObjectList().size()-1).getName());
							

							addObjFree(writePath, g.getObjectList().get(g.getObjectList().size()-1), writePath);
							System.out.println("adding SUBPM " +  g.getObjectList().get(g.getObjectList().size()-1).getName());
							
						
							//removeEffect2(path, action, g.objects.get(i), g.objects.get(o), writePath);
							
						}else{
							

							}
						
						g.getObjectList().get(i).touchList.add(g.getObjectList().get(o));

						g.getObjectList().get(o).touchList.add(g.getObjectList().get(i));
						
						ArrayList <GameObject> pair = new ArrayList <GameObject>();
						pair.add(g.getObjectList().get(i));
						pair.add(g.getObjectList().get(o));
						if(!g.checkPair(g.getObjectList().get(i), g.getObjectList().get(o))){
							g.objPair.add(pair);

							g.pairCount++;
						}
						g.printPairs();
						//return;
						
						}
					}
				}
			}
		
			
		/*	if(g.objects.get(i).getName().equals("scissors")){
				
				System.out.println("isvisible " + g.objects.get(i).getIsVisible() 
						+ " isfree " + g.objects.get(i).getIsFree() 
						+ " canbegr " + g.objects.get(i).getCanBeGrabbed() 
						+ " isgrabbed " + g.objects.get(i).getIsGrabbed());
			
			} */
		}
					
	}

	public static void addObjFree(String path, GameObject go, String writepath) throws IOException{
		File f = new File(path);
	    FileInputStream in = new FileInputStream(f);
	    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	    
	    ArrayList <String> originalLines = new ArrayList <String>(); 
	    
	    String line = "";
	
	   // read all words
	    while ((line = reader.readLine()) != null ) {
	   	 
	    	originalLines.add(line);
	    	
	    }


	    for(int i = 0; i<originalLines.size(); i++){
	    	
    	if(originalLines.get(i).contains("<!--constants-->")){
    		
    		String cons = originalLines.get(i);
    			
    		cons = cons + "," + go.getName();
    	
    		originalLines.set(i, cons);     
    		
    	}
    	
    	if(originalLines.get(i).contains("<!--goal-->")){
    		
    		String goal = originalLines.get(i);
    			
    		goal = goal + "^ K(is_free(" + go.getName() + "))";
    	
    		originalLines.set(i, goal);     
    		
    	}
	    }    
	    File fout = new File(writepath);
		FileOutputStream out = new FileOutputStream(fout);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
		
	    for(int i = 0; i < originalLines.size(); i++){
	    
	    	writer.write(originalLines.get(i));
	    	writer.write("\n");
	    }
		writer.close();
	
		
	}
	
}