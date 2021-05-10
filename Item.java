/**
 * Item is the main class for collectable things of the game.
 *
 */
import java.io.*;

public class Item implements Serializable
{
    private String description;
    private int score;
    private int weight;
    private String name;
    boolean isCasket;

    /**
     * Constructor for objects of class Item
     */
    public Item(String name, String description, int score, int weight, boolean isCasket) 
    {
        this.name = name;
        this.description = String.format(description);
        this.score = score;
        this.weight = weight;
        this.isCasket = isCasket;
    }
    /**
     * methods:
     */
    public String getShortDescription()
    {
        return description;
    }
    public String getName()
    {
        return name;
    }
    public int getScore()
    {
        return score;
    }
    public int getWeight()
    {
        return weight;
    }
    public boolean getIsCasket()
    {
        return isCasket;
    }
    public Item getContainedItem()
    {   
        return null;
    }
    public void printDescription()
    {
        System.out.printf("%n%s %n",description);
    }
}
