/**
 * MyClara
 * 
 * Available functions (see Assignment document for explanations on what each function does):
 * treeFront, ghostWallFront,
 * getDirection, setDirection,
 * move,
 * makeScared, isScared,
 * animate, animateDead, 
 * onLeaf, removeLeaf, 
 * onMushroom, removeMushroom,
 * allLeavesEaten, 
 * isClaraDead,
 * playClaraDieSound, isClaraDieSoundStillPlaying,
 * playLeafEatenSound,
 * playPacmanIntro, isPacmanIntroStillPlaying,
 * wrapAroundWorld,
 * getCurrentLevelNumber, advanceToLevel
 */
class MyClara extends Clara
{
    // Please leave this first level here,
    // until after you've completed \"Part 12 -
    // Making and Adding Levels\"
    public final char[][] LEVEL_1 = {
            {'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'},
            {'#','$','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','$','#'},
            {'#','.','#','#','.','#','.','#','#','#','#','#','.','#','.','#','#','.','#'},
            {'#','.','.','.','.','#','.','.','.','.','.','.','.','#','.','.','.','.','#'},
            {'#','#','#','#','.','#',' ','#','#','|','#','#',' ','#','.','#','#','#','#'},
            {' ',' ',' ',' ','.',' ',' ','#','%','?','%','#',' ',' ','.',' ',' ',' ',' '},
            {'#','#','#','#','.','#',' ','#','#','#','#','#',' ','#','.','#','#','#','#'},
            {'#','.','.','.','.','.','.','.','.','#','.','.','.','.','.','.','.','.','#'},
            {'#','.','#','#','.','#','#','#','.','#','.','#','#','#','.','#','#','.','#'},
            {'#','$','.','#','.','.','.','.','.','@','.','.','.','.','.','.','.','$','#'},
            {'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'}
        };

    public final char [][] LEVEL_2 = {

            {'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'},
            {'#','.','.','.','.','.','.','.','.','.','.','.','.','.','#','%','|','%','#'},
            {'#','.','#','.','.','#','.','.','#','#','#','#','.','#','#','.','?','.','#'},
            {'#','.','.','$','.','#','.','$','.','@','.','.','.','.','#','.','.','.','#'},
            {'#','#','#','#','.','#','.','.','#','.','.','#','.','#','#','.','#','#','#'},
            {'.','.','.','.','.','.','.','.','.','.','.','#','.','.','.','.','.','.','.'},
            {'#','#','#','#','.','#','.','#','#','#','#','.','.','#','.','#','#','#','#'},
            {'#','.','.','.','.','.','.','.','.','#','.','.','.','.','.','.','.','$','#'},
            {'#','.','#','#','.','#','#','#','.','#','.','#','#','#','.','#','#','.','#'},
            {'#','.','.','#','$','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
            {'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'}
    };
        
    // Movement constants
    public final String  UP = "up";    
    public final String  DOWN = "down";    
    public final String  LEFT = "left";    
    public final String  RIGHT = "right";        
    
    // Add and initialise Clara's variables here
    
    public final int ClaraSpeed = 10;
    public final int count = 0;
    public boolean moveClara = false;
    public boolean newLevel = true;
    boolean isGameOver = false;
    int levelCount = 2;
    public int animateCounter = 0;

    /**
     * Act method
     * 
     * Runs of every frame
     */
    public void act()
    {
        //Make Clara do things here
        if (newLevel)
        {
            // funtion that will play the Pacman introplayPacmanIntro();
            playPacmanIntro();
            newLevel = false;
        }

        // condition to check if into is still playing and if the game is not over
        if ( !isPacmanIntroStillPlaying() && !isGameOver )
        {
            //condition to check if Clara is not dead then detect which key board key was pressed
            if ( !isClaraDead() )
            {
                whichKeyWasPressed();
            }
            
            // condition to check if clara is moving is true and use the selected speed
            if (moveClara)
            {
                move(ClaraSpeed);
            }
            
            // animates clara's movement
            animateClaraMovement();

            // enables clara to move from one side of the world to the other 
            moveAroundWorld();

            // prevents clara from colliding with trees and ghost wall
            ClaraCantCollide();

            // Clara needs to eat all the leaves and when she does eating leaf sound plays
            eatingAllLeaves();

            // conditoin to check if all the leaves have been eaten then clara can move to the next level
            if ( allLeavesEaten() )
            {
                advanceToLevel(LEVEL_2);
                newLevel = true;
            }

            
            // Clara will eat mushrroms and gain super powers
            eatingMushrooms();

        }
    
    
    //Give Clara functions here

    }

    void whichKeyWasPressed()
    {
        if ( Keyboard.isKeyDown(UP) )
        {
            setDirection(UP);
            moveClara = true;
        }

        else if ( Keyboard.isKeyDown(DOWN) )
        {
            setDirection(DOWN);
            moveClara = true;
        }

        else if ( Keyboard.isKeyDown(RIGHT) )
        {
            setDirection(RIGHT); 
            moveClara = true;
        }

        else if ( Keyboard.isKeyDown(LEFT) )
        {
            setDirection(LEFT); 
            moveClara = true;
        }
    }

    void animateClaraMovement()
    {
        //clara will animate after every 5 frames
        if (animateCounter == 5)
        {
            animate();
            animateCounter=0;
        }
        else { animateCounter++;}
    }

    void moveAroundWorld()
    {
        //method to make clara move from one side of the world to the other
        if ( !treeFront() && treeLeft() && treeRight() && treeAbove() && treeBelow() )  
        {
            wrapAroundWorld();
        }
    }

    //Clara can't Collide with tree's and the Ghost wall
    void ClaraCantCollide()
    {
        if ( !ghostWallFront() ) 
        {
            moveClara = false; 
        }
        if ( !treeFront() )
        {
            moveClara = true;
        }
    }

    void eatingAllLeaves()
    {
        if ( onLeaf() )
        {
            removeLeaf();
            playLeafEatenSound();
        }
    }

    void eatingMushrooms()
    {
        if ( onMushroom() )
        {
            removeMushroom();
            makeScared();
        }
    }
}