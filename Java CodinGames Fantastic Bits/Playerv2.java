// Jacob Labelle 300306856 Assignment 5 COSC 121
// Codingames username: SMF_Alpharius
// Level reached: GOLD
import java.util.*;                                                                                                                                                                           
import java.io.*;                                                                                                                                                                             
import java.math.*;                                                                                                                                                                           

class Player {                                                                                                                                                                                

	public static void main(String args[]) {                                                                                                                                                  
		Scanner in = new Scanner(System.in);                                                                                                                                                  
		int myTeamId = in.nextInt(); // if 0 you need to score on the right of the map, if 1 you need to score on the left                                                                                                                                                                                                         

		Goal goalTeam1 = new Goal(16000, 3750, 1);                                                                                                                                            
		Goal goalTeam0 = new Goal(0, 3750, 0); 
		final int GOAL_WIDTH = 4000;
		final int POLE_RADIUS = 300; 

		while (true) {                                                                                                                                                                        
			ArrayList<Entity> myTeam = new ArrayList<Entity>();                                                                                                                               
			ArrayList<Entity> theirTeam = new ArrayList<Entity>();                                                                                                                            
			ArrayList<Entity> snaffles = new ArrayList<Entity>();                                                                                                                             
			ArrayList<Entity> bludgers = new ArrayList<Entity>();                                                                                                                             
			Goal targetGoal;                                                                                                                                                                                                                                                                                                                    
			if(myTeamId == 0) {                                                                                                                                                               
				targetGoal = goalTeam1;                                                                                                                                                                                                                                                                                                              
			}                                                                                                                                                                                 
			else {                                                                                                                                                                            
				targetGoal = goalTeam0;                                                                                                                                                                                                                                                                                                             
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
				// Find closest Snaffle to My Wizard
				Snaffle closestSnaffle = (Snaffle)getClosestEntity(w, snaffles);
				w.setTargetID(closestSnaffle.entID);
				// Estimate target for Opponent Wizard
				Snaffle closestOppSnaffle = (Snaffle)getClosestEntity(ow, snaffles);
				ow.setTargetID(closestOppSnaffle.entID);
				// Accio Logic and Methods
				boolean behindWizard = isBehindWizard(w, closestSnaffle, myTeamId);
				boolean worthCastingAccio = false;
				if(behindWizard) {
					worthCastingAccio = isWorthCastingAccioSnaffle(w, closestSnaffle, myTeamId);
				}
				boolean oppWizardBehind = isBehindWizard(w, ow, myTeamId);
				// End Accio Logic
				// Flipendo Logic and Methods
				// Calculations for flipendo were adapted from code recommended by Codingames
				boolean targetAligned = false;
				int distX = (closestSnaffle.x + closestSnaffle.vx) - (w.x + w.vx);                                                                                                                                           
				int distY = (closestSnaffle.y + closestSnaffle.vy) - (w.y + w.vy);                                                                                                                                           	                                                                                                                                               
				if(distX != 0) {                                                                                                                                                              
					int slope = distY / distX;                                                                                                                                                
					int targetY = (closestSnaffle.y + closestSnaffle.vy) + (targetGoal.x - (closestSnaffle.x + closestSnaffle.vx)) * slope;                                                                                               
					targetAligned = (Math.abs(targetY - targetGoal.y)) < (GOAL_WIDTH / 2 + POLE_RADIUS);
				}
				// End Flipendo Logic and Methods
				// End attributed section
				// Actions
				if(w.state == 1) {                                                                                                                                                            
					action = "THROW " + (targetGoal.x - closestSnaffle.vx) + " " + (targetGoal.y - closestSnaffle.vy) + " " + power;
				}
				else if(myMagic >= 20 && !behindWizard && targetAligned) {
					action = "FLIPENDO " + closestSnaffle.entID + " YEET";
					myMagic -= 20;
				}
				else if(myMagic >= 15 && ((behindWizard && worthCastingAccio) || (ow.state == 1 && oppWizardBehind))) {
					if(behindWizard && worthCastingAccio) {
						action = "ACCIO " + closestSnaffle.entID;
						myMagic -= 15;
					}
					else if(ow.state == 1 && oppWizardBehind) {
						action = "ACCIO " + closestOppSnaffle.entID + " YOINK";
						myMagic -= 15;
					}
				}
				else {                                                                                                                                                                        
					action = "MOVE " + (closestSnaffle.x + closestSnaffle.vx) + " " + (closestSnaffle.y + closestSnaffle.vy) + " " + thrust;
				}
				// Mitigate risk of chasing same target
				if (snaffles.size() > 1) {
					snaffles.remove(closestSnaffle);
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


	public static boolean isBehindWizard(Wizard w, Entity entity, int myTeamId) {
		if(myTeamId == 0 && (w.x + w.vx) > (entity.x + entity.vx)) {
			return true;
		}
		else if(myTeamId == 1 && (w.x + w.vx) < (entity.x + entity.vx)) {
			return true;
		}
		return false;
	}

	public static boolean isWorthCastingAccioSnaffle(Wizard w, Snaffle cS, int myTeamId) {
		double distance = (w.x + w.vx) - (cS.x + cS.vx);
		if(myTeamId == 0 && (distance > 600 && distance < 2000)) {
			return true;
		}
		else if(myTeamId == 1 && (distance < -600 && distance > -2000)) {
			return true;
		}
		return false;
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