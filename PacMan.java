class MyPacman extends Pacman
{
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
    
    // Add and initialise Pacman's variables here
    
    public final int PacmanSpeed = 10;
    public final int count = 0;
    public boolean movePacman = false;
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
        //Make Pacman do things here
        if (newLevel)
        {
            // funtion that will play the Pacman introplayPacmanIntro();
            playPacmanIntro();
            newLevel = false;
        }

        // condition to check if into is still playing and if the game is not over
        if ( !isPacmanIntroStillPlaying() && !isGameOver )
        {
            //condition to check if Pacman is not dead then detect which key board key was pressed
            if ( !isPacmanDead() )
            {
                whichKeyWasPressed();
            }
            
            // condition to check if Pacman is moving is true and use the selected speed
            if (movePacman)
            {
                move(PacmanSpeed);
            }
            
            // animates Pacman's movement
            animatePacmanMovement();

            // enables Pacman to move from one side of the world to the other 
            moveAroundWorld();

            // prevents Pacman from colliding with trees and ghost wall
            PacmanCantCollide();

            // Pacman needs to eat all the leaves and when he does eating leaf sound plays
            eatingAllLeaves();

            // conditoin to check if all the leaves have been eaten then Pacman can move to the next level
            if ( allLeavesEaten() )
            {
                advanceToLevel(LEVEL_2);
                newLevel = true;
            }

            
            // Pacman will eat mushrooms and gain super powers
            eatingMushrooms();

        }
    
    
    //Give Pacman functions here

    }

    void whichKeyWasPressed()
    {
        if ( Keyboard.isKeyDown(UP) )
        {
            setDirection(UP);
            movePacman = true;
        }

        else if ( Keyboard.isKeyDown(DOWN) )
        {
            setDirection(DOWN);
            movePacman = true;
        }

        else if ( Keyboard.isKeyDown(RIGHT) )
        {
            setDirection(RIGHT); 
            movePacman = true;
        }

        else if ( Keyboard.isKeyDown(LEFT) )
        {
            setDirection(LEFT); 
            movePacman = true;
        }
    }

    void animatePacmanMovement()
    {
        //Pacman will animate after every 5 frames
        if (animateCounter == 5)
        {
            animate();
            animateCounter=0;
        }
        else { animateCounter++;}
    }

    void moveAroundWorld()
    {
        //method to make Pacman move from one side of the world to the other
        if ( !treeFront() && treeLeft() && treeRight() && treeAbove() && treeBelow() )  
        {
            wrapAroundWorld();
        }
    }

    //Pacman can't Collide with tree's and the Ghost wall
    void PacmanCantCollide()
    {
        if ( !ghostWallFront() ) 
        {
            movePacman = false; 
        }
        if ( !treeFront() )
        {
            movePacman = true;
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
