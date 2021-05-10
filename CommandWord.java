public enum CommandWord
{
    GO("go"), 
    QUIT("quit"),
    HELP("help"), 
    TAKE("take"), 
    DROP("drop"), 
    INSPECT("inspect"), 
    LEAVE("leave"),
    UNKNOWN("?"),
    BACK("back");
    
    private String commandString;
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }
    
    /**
     * @return The command word as a string.
     */
    public String toString()
    {
        return commandString;
    }
}