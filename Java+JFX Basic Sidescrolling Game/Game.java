//Jacob Labelle COSC 111 L04 Assignment 10
public class Game
{
    private Grid grid;
    private int userRow;
    private int userCol;
    private int msElapsed;
    private int sauceBombTime;
    private int sauceBombCount = 0;
    private int theSauce;
    private int tendyCount;
    private int brocAttack;
    private int spawnLevel = 1;

    public Game()
    {
        grid = new Grid(10, 20);
        userRow = grid.getNumRows()/2;
        userCol = 0;
        msElapsed = 0;
        tendyCount = 0;
        brocAttack = 0;
        theSauce = 0;
        updateTitle();
        grid.setImage(new Location(userRow, 0), "user.png");
    }

    public void play()
    {
        populateGround();
        while (!isGameOver())
        {
            grid.pause(100);
            handleKeyPress();
            if (msElapsed % 300 == 0)
            {
                scrollLeft();
                populateRightEdge();
            }
            updateTitle();
            msElapsed += 100;
            if(timeSinceSauce() > 900) {
                sauceCleanup();
            }
        }
        updateTitle();
    }

    public void handleKeyPress()
    {
        int key = grid.checkLastKeyPressed();
        grid.setImage(new Location(userRow, userCol), null);
        //Move Up
        if(key == 38) {
            if(userRow > 0) {
                userRow--;
                Location userLoc = new Location(userRow, userCol);
                String loc = grid.getImage(userLoc);
                if(loc != null && loc != "ground.png") {
                    handleCollision(userLoc);
                }
            }
        }
        //Move Down
        if(key == 40) {
            if(userRow < grid.getNumRows() - 2) {
                userRow++;
                Location userLoc = new Location(userRow, userCol);
                String loc = grid.getImage(userLoc);
                if(loc != null && loc != "ground.png") {
                    handleCollision(userLoc);
                }
            }
        }
        //Move Right
        if(key == 39) {
            if(userCol < grid.getNumCols() - 2) {
                userCol++;
                Location userLoc = new Location(userRow, userCol);
                String loc = grid.getImage(userLoc);
                if(loc != null) {
                    handleCollision(userLoc);
                } 
            }
        }
        //Move Left
        if(key == 37) {
            if(userCol > 0) {
                userCol--;
                Location userLoc = new Location(userRow, userCol);
                String loc = grid.getImage(userLoc);
                if(loc != null) {
                    handleCollision(userLoc);
                } 
            }
        }
        //Deploy the Sauce Bomb
        if(key == 32 && theSauce >= 10) {
            if(timeSinceSauce() > 1000) {
                sauceBombTime = msElapsed;
                sauceBomb();
            }
        }
        grid.setImage(new Location(userRow, userCol), "user.png");
    }
    //Make the things appear at the right edge
    public void populateRightEdge()
    {
        if(msElapsed % 600 == 0) {
            grid.setImage(new Location((int)(Math.random()*(grid.getNumRows()-1)),grid.getNumCols() - 1), "tenders.png");
        }
        if(msElapsed >= 3000 && msElapsed % 3000 == 0) {
            grid.setImage(new Location((int)(Math.random()*(grid.getNumRows()-1)),grid.getNumCols() - 1), "bbq.png");
        }
        if(msElapsed % 900 == 0) {
            if(msElapsed % 2000 == 0) {
                repeaterAdjust();
            }
            if(sauceBombCount > 0 && timeSinceSauce() > 900) {
                for(int count = 0; count <= spawnLevel; count++) {
                    grid.setImage(new Location ((int)(Math.random()*(grid.getNumRows()-1)),grid.getNumCols() - 1), "the_broc.png");
                }
            }
            else if(sauceBombCount == 0) {
                for(int count = 0; count <= spawnLevel; count++) {
                    grid.setImage(new Location ((int)(Math.random()*(grid.getNumRows()-1)),grid.getNumCols() - 1), "the_broc.png");
                }
            }
        }
        //Ensure the ground keeps generating
        grid.setImage(new Location(grid.getNumRows()-1, grid.getNumCols()-1), "ground.png");
    }
    //Mutate Repeater for Spawning Objects
    public void repeaterAdjust() {
        if(spawnLevel < 8) {
            spawnLevel++;
        }
    }
    //Generate Ground at the beginning
    public void populateGround() 
    {
        for(int col = 0; col < grid.getNumCols(); col++) {
            grid.setImage(new Location(grid.getNumRows()-1, col), "ground.png");
        }
    }
    //DEPLOY THE SAUCE
    public void sauceBomb() {
        for(int row = 0; row < grid.getNumRows(); row++) {
            for(int col = 0; col < grid.getNumCols(); col++) {
                Location loc = new Location(row, col);
                grid.setColor(loc, new Color (104, 19, 19));
                String img = grid.getImage(new Location(row, col)); 
                if(img == "the_broc.png") {
                    grid.setImage(new Location(row, col), null);
                }                
            }
        }
        theSauce-=10;
        sauceBombCount++;
    }
    //TIME SINCE LAST SAUCING
    public int timeSinceSauce() {
        return msElapsed - sauceBombTime;
    }
    //SAUCE CLEANUP
    public void sauceCleanup() {
        for(int row = 0; row < grid.getNumRows(); row++) {
            for(int col = 0; col < grid.getNumCols(); col++) {
                Location loc = new Location(row, col);
                grid.setColor(loc, new Color (0, 0, 0));              
            }
        }
    }

    public void scrollLeft()
    {
        Location userLoc = new Location(userRow, userCol);
        scroller();
        handleCollision(userLoc);
    }
    //Scroll Helper
    public void scroller() {
        for(int row = 0; row < grid.getNumRows(); row++) {
            for(int col = 0; col < grid.getNumCols(); col++) {
                Location locEval = new Location(row, col);
                String img = grid.getImage(new Location(row, col));
                if(img != null && img == "the_broc.png"){
                    if(grid.getImage(locEval).equals("the_broc.png")) {
                        if(col > 0) {
                            grid.setImage(locEval, null);
                            grid.setImage(new Location(row, col-1), "the_broc.png");
                        }
                        else {
                            grid.setImage(locEval, null);
                        }
                    }
                }
                if(img != null && img == "bbq.png"){
                    if(grid.getImage(locEval).equals("bbq.png")) {
                        if(col > 0) {
                            grid.setImage(locEval, null);
                            grid.setImage(new Location(row, col-1), "bbq.png");
                        }
                        else {
                            grid.setImage(locEval, null);
                        }
                    }
                }
                if(img != null && img == "tenders.png") {
                    if(grid.getImage(locEval).equals("tenders.png")) {
                        if(col > 0) {
                            grid.setImage(locEval, null);
                            grid.setImage(new Location(row, col-1), "tenders.png");
                        }
                        else {
                            grid.setImage(locEval, null);
                        }
                    }
                }
                if(img != null && img == "ground.png"){
                    if(grid.getImage(locEval).equals("ground.png")) {
                        if(col > 0) {
                            grid.setImage(locEval, null);
                            grid.setImage(new Location(row, col-1), "ground.png");
                        }
                        else {
                            grid.setImage(locEval, null);
                        }
                    }
                }
            }
        }
    }
    //Collision Helper
    public void handleCollision(Location userLoc)
    {
        if(grid.getImage(userLoc).equals("the_broc.png")) {
            if(brocAttack > 1) {
                tendyCount--;
            }
            brocAttack++;
            grid.setImage(userLoc, "user.png");
        }
        if(grid.getImage(userLoc).equals("tenders.png")) {
            tendyCount++;
            grid.setImage(userLoc, "user.png");
        }
        if(grid.getImage(userLoc).equals("bbq.png")) {
            if(theSauce <= 20) {
                theSauce+=3;
            }
            grid.setImage(userLoc, "user.png");
        }
    }

    public int getScore()
    {
        int userScore = tendyCount;
        return userScore;
    }

    public void updateTitle()
    {
        grid.setTitle("TENDY QUEST: " + getScore() + " CHICKEN TENDIES. EAT THOSE TENDIES.");
        if(theSauce >= 10 ) {
            grid.setTitle("THE SAUCE IS READY. PRESS SPACEBAR TO PURGE THE BROCCOLI.");
        }
        if(isGameOver() == true) {
            if(brocAttack > 20) {
                grid.setTitle("GAME OVER. THIS IS NOT BROCCOLI QUEST. TENDIES EATEN: " + getScore());
            }
            if(tendyCount < 0) {
                grid.setTitle("YOU LOST ALL THE TENDIES. HOW COULD YOU. YOU ARE A MONSTER.");
            }
            if(tendyCount > 50 || msElapsed >= 100000) {
                grid.setTitle("YOU ATE A LOT OF TENDIES. " + getScore() + " TENDIES WERE FEASTED UPON.");
            }
        }
    }

    public boolean isGameOver()
    {
        if(brocAttack > 20 || tendyCount < 0) {
            return true; 
        }
        if(tendyCount > 50 || msElapsed >= 100000) {
            return true;
        }
        return false;
    }

    public static void test()
    {
        Game game = new Game();
        game.play();
    }

    public static void main(String[] args)
    {
        test();
    }
}