import java.util.*;

public class GameStateTracker
{
    private Stack<GameStage> stateTrackList;
    private GameStage lastGameStage;
    
    
    public GameStateTracker()
    {
        // initialise instance variables
        this.stateTrackList = new Stack<>(); 
        
    }
    
    public GameStage getLastGameStage()
    {   
        lastGameStage = null;
        
        for(int i=0; i<2; i++)
        {   
            if(!stateTrackList.empty()){
                lastGameStage = stateTrackList.pop();           //if you are at the start of the game you stay where you are - nowhere to go back to
            }                                                    
        }
        
        return lastGameStage;
    }
    
     public void recordGameState(GameStage currentGameStage)  
    {
       lastGameStage = currentGameStage.deepCopy(currentGameStage);
       stateTrackList.push(lastGameStage);
    }
}
