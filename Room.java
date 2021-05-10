import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Date;
import java.io.*;

/**
 * Class Room - a room in an adventure game.
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 */

public class Room implements Serializable
{
    private String description, shortDescription;
    private String name;
    private HashMap<String, Room> exits; // stores exits of this room.
    private ArrayList<Item> items;

    /**
     * Create a room described "description".
     * @param description The room's description.
     */
    public Room(String name, String description, String shortDescription) 
    {   
        this.name = name;
        this.description = String.format(description);
        this.shortDescription = String.format(shortDescription);
        exits = new HashMap<>();
        items = new ArrayList<>();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setItem(Item item) 
    {
        items.add(item);
    }
    
    /**
     * Print first part of description of the room 
     */
    public void printDescription()
    {
        if(description.contains("<currentTime>")){ 
            Date time = new Date();
            System.out.printf(description.replace("<currentTime>", "%tR") + "%n", time);
        } else {
            System.out.printf("%s %n",description);
        }
    }
    
    /**
     * Print short description of the room 
     */
    public void printShortDescription()
    {
        System.out.printf("%s %n", shortDescription);
    }

    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys){
            returnString += " " + exit;
        }
        return returnString;
    }

    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    public ArrayList<Item> getItems() 
    {
        return items;
    }
    
    public Item getItem(String itemName) 
    {   
        for(Item item : items){
           if (item.getName().contains(itemName)){
               return item;
           }
        }
        return null;
    }
    
    public void removeItem(Item item) 
    {   
        items.remove(item);
    }
}