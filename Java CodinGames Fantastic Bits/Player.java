import java.util.*;                                                                                                                                                                           
import java.io.*;                                                                                                                                                                             
import java.math.*;                                                                                                                                                                           

class Player {                                                                                                                                                                                

	public static void main(String args[]) {                                                                                                                                                  
		Scanner in = new Scanner(System.in);                                                                                                                                                  
		int myTeamId = in.nextInt(); // if 0 you need to score on the right of the map, if 1 you need to score on the left                                                                                                                                                                                                         

		Goal goalTeam1 = new Goal(16000, 3750, 1);                                                                                                                                            
		Goal goalTeam0 = new Goal(0, 3750, 0);                                                                                                                                                

		while (true) {                                                                                                                                                                        
			ArrayList<Entity> myTeam = new ArrayList<Entity>();                                                                                                                               
			ArrayList<Entity> theirTeam = new ArrayList<Entity>();                                                                                                                            
			ArrayList<Entity> snaffles = new ArrayList<Entity>();                                                                                                                             
			ArrayList<Entity> bludgers = new ArrayList<Entity>();                                                                                                                             
			Goal targetGoal, defendGoal;                                                                                                                                                      
			int minProxY = 2050;                                                                                                                                                              
			int maxProxY = 5450;                                                                                                                                                              
			if(myTeamId == 0) {                                                                                                                                                               
				targetGoal = goalTeam1;                                                                                                                                                       
				defendGoal = goalTeam0;                                                                                                                                                       
			}                                                                                                                                                                                 
			else {                                                                                                                                                                            
				targetGoal = goalTeam0;                                                                                                                                                       
				defendGoal = goalTeam1;                                                                                                                                                       
			}                                                                                                                                                                                 

			int myScore = in.nextInt();                                                                                                                                                       
			int myMagic = in.nextInt();                                                                                                                                                       
			int opponentScore = in.nextInt();                                                                                                                                                 
			int opponentMagic = in.nextInt();                                                                                                                                                 
			int entities = in.nextInt(); // number of entities still in game                                                                                                                  
			for (int i = 0; i < entities; i++) {                                                                                                                                              
				int entityId = in.nextInt(); // entity identifier                                                                                                                             
				String entityType = in.next(); // "WIZARD", "OPPONENT_WIZARD" or "SNAFFLE" (or "BLUDGER" after first league)                                                                  
				int x = in.nextInt(); // position                                                                                                                                             
				int y = in.nextInt(); // position                                                                                                                                             
				int vx = in.nextInt(); // velocity                                                                                                                                            
				int vy = in.nextInt(); // velocity                                                                                                                                            
				int state = in.nextInt(); // 1 if the wizard is holding a Snaffle, 0 otherwise                                                                                                
				if(entityType.equals("WIZARD")) {                                                                                                                                             
					Wizard w = new Wizard(entityId, x, y, vx, vy, state);                                                                                                                     
					myTeam.add(w);                                                                                                                                                            
				}                                                                                                                                                                             
				else if(entityType.equals("OPPONENT_WIZARD")) {                                                                                                                               
					Wizard ow = new Wizard(entityId, x, y, vx, vy, state);                                                                                                    
					theirTeam.add(ow);                                                                                                                                                        
				}                                                                                                                                                                             
				else if(entityType.equals("SNAFFLE")) {                                                                                                                                       
					Snaffle snaff = new Snaffle(entityId, x, y, vx, vy, state);                                                                                                               
					snaffles.add(snaff);                                                                                                                                                      
				}                                                                                                                                                                             
				else if(entityType.equals("BLUDGER")) {                                                                                                                                       
					Bludger bludge = new Bludger(entityId, x, y, vx, vy, state);                                                                                                              
					bludgers.add(bludge);                                                                                                                                                     
				}                                                                                                                                                                             
			}                                                                                                                                                                                 
			for (int i = 0; i < 2; i++) {                                                                                                                                                     
				Wizard w = (Wizard)myTeam.get(i); 
				Wizard ow = (Wizard)theirTeam.get(i);
				String action = "";                                                                                                                                                           
				int power = 500;                                                                                                                                                              
				int thrust = 150;                                                                                                                                                             
				Snaffle closestSnaffle = (Snaffle)getClosestEntity(w, snaffles);
				w.setTargetID(closestSnaffle.entID);
				//System.err.println("Wizard " + w.entID + " target: " + w.targetID);
				if (snaffles.size() > 1) {
					snaffles.remove(closestSnaffle);
				}
				Snaffle oppForSnaffle = (Snaffle)getClosestEntity(ow, snaffles);
				//Bludger closestBludger = (Bludger)getClosestEntity(w, bludgers);                                                                                                              
				//Snaffle closeToGoal = (Snaffle)closestToGoal(defendGoal, snaffles);

				//Flipendo calculations
				boolean castFlipendo = false;
				double fDistX = 0;
				boolean targetAligned = castFlipendo(w, closestSnaffle, targetGoal);
				if(targetAligned) {
					fDistX = closestSnaffle.x - w.x;
					if(myTeamId == 0 && fDistX > 0) {
						castFlipendo = true;
					}
					else if(myTeamId == 1 && fDistX < 0) {
						castFlipendo = true;
					}
				}

				//Accio Logic
				double distX =  (closestSnaffle.x + closestSnaffle.vx) - (w.x + w.vx);
				boolean isBehindWizard = false;
				if(distX < 0 && myTeamId == 0) {
					isBehindWizard = true;
				}
				else if(distX > 0 && myTeamId == 1) {
					isBehindWizard = true;
				}

				if(w.state == 1) {                                                                                                                                                            
					action = "THROW " + (targetGoal.x - closestSnaffle.vx) + " " + (targetGoal.y - closestSnaffle.vy) + " " + power;                                                                                                      
				}
				else {
					if(myMagic >= 20 && castFlipendo) {	                                                                                      
						action = "FLIPENDO " + closestSnaffle.entID;                                                                                                                              
						myMagic -= 20;                                                                                                                                                            
					}                                                                                                                                                                             
					else if(myMagic >= 15) {	                                                                          
						if(ow.state == 1) {
							action = "ACCIO " + oppForSnaffle.entID;                                                                                                                                 
							myMagic -= 15; 
						}
						else if(isBehindWizard && (distX < -300 || distX > 300)) {
							action = "ACCIO " + closestSnaffle.entID;
							myMagic -=15;
						}
					}
					/*
					else if(myMagic >= 10 && (closeToGoal.x - defendGoal.x < 1500) && (closeToGoal.y < maxProxY || closeToGoal.y > minProxY)) {                                                  
						action = "PETRIFICUS " + closeToGoal.entID;                                                                                                                               
						myMagic -= 10;                                                                                                                                                            
				}
					 */
					else {                                                                                                                                                                        
						action = "MOVE " + (closestSnaffle.x + closestSnaffle.vx) + " " + (closestSnaffle.y + closestSnaffle.vy) + " " + thrust;                                                                                              
					}
				}
				System.out.println(action);                                                                                                                                                   
			}                                                                                                                                                                                 
		}                                                                                                                                                                                     
	}                                                                                                                                                                                         

	public static Entity getClosestEntity(Wizard w, ArrayList<Entity> entities) {                                                                                                             
		Entity closestEntity = null;                                                                                                                                                          
		double minDistance = 0;                                                                                                                                                                  
		for(int index = 0; index < entities.size(); index++) {                                                                                                                                
			Entity temp = entities.get(index);                                                                                                                                                
			double xDiff = (temp.x + temp.vx) - (w.x + w.vx);
			double xDist = Math.pow(xDiff, 2);
			double yDiff = (temp.y + temp.vy) - (w.y + w.vy);
			double yDist = Math.pow(yDiff, 2);
			double distance = Math.sqrt(xDist + yDist); 
			//System.err.println(w.entID + " - " + temp.entID + ": " + distance);
			if(closestEntity == null || distance < minDistance) {                                                                                                                             
				closestEntity = temp;                                                                                                                                                         
				minDistance = distance;                                                                                                                                                       
			}                                                                                                                                                                                 
		}
		return closestEntity;                                                                                                                                                                 
	}

	public static boolean castFlipendo(Wizard w, Snaffle closestSnaffle, Goal targetGoal) {
		boolean targetInBounds = false;
		final int GOAL_WIDTH = 4000, POLE_RADIUS = 300; 
		//This calculation is for deciding to cast Flipendo, Codingames provided the suggested algorithm                                                                              
		int distX = closestSnaffle.x - w.x;                                                                                                                                           
		int distY = closestSnaffle.y - w.y;                                                                                                                                           
		if(distX != 0) {                                                                                                                                                              
			int slope = distY / distX;                                                                                                                                                
			int targetY = closestSnaffle.y + (targetGoal.x - closestSnaffle.x) * slope;                                                                                               
			targetInBounds = (Math.abs(targetY - targetGoal.y)) < (GOAL_WIDTH / 2 + POLE_RADIUS); 
		}                                                                                                                                                                             
		//End Section provided by Codingames 
		return targetInBounds;
	}

	public static boolean castAccio(Wizard w, Snaffle closest) {

		return false;
	}

	public static Entity closestToGoal(Goal defendGoal, ArrayList<Entity> snaffles) {                                                                                                         
		Entity closestSnaffle = null;                                                                                                                                                         
		double minDistance = 0;                                                                                                                                                                  
		for(int index = 0; index < snaffles.size(); index++) {                                                                                                                                
			Entity temp = snaffles.get(index);                                                                                                                                                
			double xDiff = (temp.x) - (defendGoal.x);
			double xDist = Math.pow(xDiff, 2);
			double yDiff = (temp.y) - (defendGoal.y);
			double yDist = Math.pow(yDiff, 2);
			double distance = Math.sqrt(xDist + yDist); 
			if(closestSnaffle == null || distance < minDistance) {                                                                                                                            
				closestSnaffle = temp;                                                                                                                                                        
				minDistance = distance;                                                                                                                                                       
			}                                                                                                                                                                                 
		}                                                                                                                                                                                     
		return closestSnaffle;                                                                                                                                                                
	}                                                                                                                                                                                         
}                                                                                                                                                                                             

class Goal {                                                                                                                                                                                  
	protected int x;                                                                                                                                                                          
	protected int y;                                                                                                                                                                          
	protected int teamID;                                                                                                                                                                     

	public Goal(int x, int y, int teamID) {                                                                                                                                                   
		this.x = x;                                                                                                                                                                           
		this.y = y;                                                                                                                                                                           
		this.teamID = teamID;                                                                                                                                                                 
	}                                                                                                                                                                                         
}                                                                                                                                                                                             

class Entity {                                                                                                                                                                                
	protected int entID;                                                                                                                                                                      
	protected int x;                                                                                                                                                                          
	protected int y;                                                                                                                                                                          
	protected int vx;                                                                                                                                                                         
	protected int vy;                                                                                                                                                                         
	protected int state;                                                                                                                                                                      

	public Entity(int entID, int x, int y, int vx, int vy, int state) {                                                                                                                       
		this.entID = entID;                                                                                                                                                                   
		this.x = x;                                                                                                                                                                           
		this.y = y;                                                                                                                                                                           
		this.vx = vx;                                                                                                                                                                         
		this.vy = vy;                                                                                                                                                                         
		this.state = state;                                                                                                                                                                   
	}                                                                                                                                                                                         
}                                                                                                                                                                                             

class Wizard extends Entity { 
	protected int targetID = -1;

	public Wizard(int entID, int x, int y, int vx, int vy, int state) {                                                                                                                       
		super(entID, x, y, vx, vy, state);                                                                                                                                                    
	}  

	public void setTargetID(int targetID) {
		this.targetID = targetID;
	}
}                                                                                                                                                                                             

class Snaffle extends Entity {                                                                                                                                                                
	public Snaffle(int entID, int x, int y, int vx, int vy, int state) {                                                                                                                      
		super(entID, x, y, vx, vy, state);                                                                                                                                                    
	}                                                                                                                                                                                         
}                                                                                                                                                                                             

class Bludger extends Entity {                                                                                                                                                                
	public Bludger(int entID, int x, int y, int vx, int vy, int state)  {                                                                                                                     
		super(entID, x, y, vx, vy, state);                                                                                                                                                    
	}                                                                                                                                                                                         
}                                                                                                                                                                                             