class Ghost extends Character
{

    public final String RIGHT = "right";
    public final String LEFT = "left";
    public final String UP = "up";
    public final String DOWN = "down";

    public final int ghostsSpeed = 4;
    public int animatGhosteCounter = 0;

    public boolean isTheGameOver = false;
    public boolean rebirthingOrigin = true;
    public boolean areTheGhostsDead = false;
    
    /**
     * Act method, runs on every frame
     */
    public void act()
    {
        // condition to check if the intro is stil playing and game is not over
        // condition to check if the ghosts are not scared and are not dead
        // ghosts will chase and kill Pacman
        if ( !isPacmanIntroStillPlaying() && !isTheGameOver )
        {
            if ( !isScared() && !areTheGhostsDead) 
            {
                timeToChaseAndKillPacman();
            }

            // condition to check if ghosts are scared and are still alive, once Pacman
            // eats a mushroom they will run away
            if (isScared() && !areTheGhostsDead)
            {
                ghostAreScaredAndNeedToRunAway();
            }

            if (areTheGhostsDead)
            {
                ghostsAreDeadAndNeedToHeal();
            }
        }

    //Give the Ghost functions here
    
    }

    //method to chase and kill Pacman
    void timeToChaseAndKillPacman()
    {
        leaveHomeSearchForPacman();
        moveAcrossTheWorld();
        animateGhostsMovement();

        if ( intersects(getPacman()) )
        {
            makePacmanDead();
            isTheGameOver = true;
        }
    }

    // method for when ghosts leave their home
    void leaveHomeSearchForPacman()
    {
        if ( rebirthingOrigin )
        {
            moveAroundTheMap();
        }

        if ( !rebirthingOrigin )
        {
            if ( !ghostWallFront() )
            {
                move(ghostsSpeed);
            }

            if ( ghostsAtRowIntersection() || ghostsAtColumnIntersection() )
            {
                directionsToChasePacman();
            }
        }
    }

    //method for moving across the the world
    void moveAcrossTheWorld()
    {
        if ( !treeFront() && treeLeft() && treeRight() )
        {
            wrapAroundWorld();
            move(ghostsSpeed);
        }
    }

    //method for animating ghosts
    void animateGhostsMovement()
    {
        if ( animatGhosteCounter == 5 )
        {
            animate();
            animatGhosteCounter=0;
        } else {
            animatGhosteCounter++;
        }
    }

    // method to let ghosts know which way to move
    void moveAroundTheMap()
    {
        move(ghostsSpeed);

        if ( treeToRight() )
        {
            setDirection(LEFT);
        }

        else if ( treeToLeft() )
        {
            setDirection(RIGHT);
        }

        if ( !treeAbove() )
        {
            setDirection(UP);
            move(ghostsSpeed);
        }

        if ( checkIfOnGhostWall() )
        {
            rebirthingOrigin = false;
        }
    }

    // ghosts will check if they are at a row intersection
    boolean ghostsAtRowIntersection()
    {
        if ((getDirection() == "right") || (getDirection() == "left"))
        {
            if (!treeAbove() || !treeBelow() || treeFront())
            {
                return true;
            }

            else
                {
                    return false;
                }
        }

        else
            {
                return false;
            }
    }

    // ghosts will check if they are at a column intersection
    boolean ghostsAtColumnIntersection()
    {
        if ((getDirection() == "up") || (getDirection() == "down"))
        {
            if (!treeRight() || !treeLeft() || treeFront())
            {
                return true;
            }

            else
                {
                    return false;
                }
        }
            
        else 
            {
                return false;
            }
    }


    // the ghosts will decide which way is the best way to chase Pacman
    void directionsToChasePacman()
    {
    if ( !treeToLeft() )
    {
        setDirection(LEFT);
    }

    else if ( !treeToRight() )
    {
        setDirection(RIGHT);
    }

    if ( !treeBelow() )
    {
        setDirection(DOWN);
    }
    
    else if ( !treeAbove() )
    {
        setDirection(UP);
    }
    }
    
    // method for ghosts to act scared and run away when Pacman eats a mushroom
    void ghostAreScaredAndNeedToRunAway()
    {
        animateScared();
        isScared();
        moveAroundTheMap();

        // condition to check if ghosts and Pacman intersect while ghosts are scared
        // ghosts will die and return to the healer position
        if ( intersects(getPacman()) )
        {
            playGhostEatenSound();
            areTheGhostsDead = true;
            ghostsAreDeadAndNeedToHeal();
        }
    }

    // method to check if ghosts are dead 
    void ghostsAreDeadAndNeedToHeal()
    {
        directionsToHealer();
        animateDead();

        if ( intersects(getGhostHealer()) )
        {
            areTheGhostsDead = true;
            rebirthingOrigin = true;
        }
    }

    // method for ghosts to return to healer
    void directionsToHealer()
    {

    }

    
    boolean ghostWallFront()
    {
        return true;
    }

    boolean checkIfOnGhostWall()
    {
        return true;
    }

}
