import java.util.*;
import java.io.*;

public class GameStage implements Serializable  
{
    private Player player;
    private ArrayList<Room> rooms;    
    
    public GameStage(Player player,ArrayList<Room> rooms)
    {
        this.rooms = rooms;
        this.player = player;
    }
    
    public void reSetRooms(ArrayList<Room> rooms)
    {
        this.rooms = rooms;
    }
    
    public void reSetPlayer(Player player)
    {
        this.player = player;
    }
    
    public ArrayList<Room> getRooms()
    {
        return rooms;
    }
    
    public Player getPlayer()
    {
        return player;
    }
    
    public GameStage deepCopy(GameStage gameStage) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream outputStrm = new ObjectOutputStream(outputStream);
            outputStrm.writeObject(gameStage);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            ObjectInputStream objInputStream = new ObjectInputStream(inputStream);
            return (GameStage) objInputStream.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
     }
    
}
