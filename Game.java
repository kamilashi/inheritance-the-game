/**
 * The Inheritance Problem: The Game
 */
import java.util.ArrayList;

public class Game
{
    public Player player;
    public String commandString = null;
    private Parser parser;
    private boolean isFinished = false;
    GameStateTracker stateTracker;
    GameStage currentStage;
    private ArrayList<Room> rooms;
     
    /**
     * Create the Game and initialise its internal map.
     */
    public Game() 
    {   
        initialize();
        this.parser = new Parser();
        
    }
    
    /**
    *  Main play routine. 
    */
    public void startGame()
    {   
        printWelcome();
        stateTracker = new GameStateTracker();
        currentStage = new GameStage(player, rooms);
        stateTracker.recordGameState(currentStage);
        
        player.currentRoom.printDescription();
        player.currentRoom.printShortDescription();
        
    }
    
    public void gameLoop(String userInput)
    {
        Command command = parser.getCommand(userInput);
        isFinished = processCommand(command);
        currentStage.reSetPlayer(player);
        currentStage.reSetRooms(rooms);
        stateTracker.recordGameState(currentStage);
        
        if(isFinished){
        if(player.gameCompleted()){
            printEnding();
        } else {
            System.out.println("Thank you for playing. We hope you actually finish the game next time!");
        }
        }
    } 
    
    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the Game, false otherwise.
    */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;
        
        CommandWord commandWord = command.getCommandWord();
        
        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                return wantToQuit;
                
            case BACK:
                reSet(stateTracker.getLastGameStage());
                break;
                
            case HELP:
                printHelp();
                break;
                
            case GO:
                player.goToRoom(command);
                break;
                
            case TAKE:
                player.pickUp(command);
                break;
                
            case DROP:
                player.drop(command);
                break;
                
            case INSPECT:
                player.inspect(command);
                break;
                
            case LEAVE:
                return wantToQuit = player.leave(command);
                
            case QUIT:
                return wantToQuit = player.quit(command);
        }
        
        // else command not recognised.
        return wantToQuit;
    }
    
    
    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {   
        System.out.println();
        System.out.println("You say the magic word. Nothing happens at first, then");
        System.out.println("a striking realization hits you: ");
        System.out.println("You are on a mission of retrieving your inheritance");
        System.out.println("in a creepy mansion full of all sorts of uncanny relics");
        System.out.println("and ancient artifacts. A totally normal situation ");
        System.out.println("to find yourself in.");
        System.out.println();
        System.out.println("All the next possible steps become very clear to you:");
        
        parser.showCommands();
    }
    
    /**
     * Create all the rooms, items and player.
     */
    private void reSet(GameStage prevStage)
    {   
        if(prevStage!=null){
            rooms = prevStage.getRooms();
            player = prevStage.getPlayer();
        
            System.out.printf("%nYou are in the "+player.currentRoom.getName());
            System.out.println();
            player.currentRoom.printShortDescription();
        }
        else{
        System.out.printf("%nYou just satred the game, nothing to go back to!%n");
        }
    }
    private void initialize()
    {
        Room hall, secondFloor, study, library, diningRoom, workshop, basement, bedroom;
        Item casketItemClasp,casketItemKey,casketItemCharm,casketItemGemstone;
        Item diningRoomSword, diningRoomBrooch, diningRoomCabinet, libraryBook, libraryTable;
        Item workshopHomunculus,workshopHatch,workshopApparatus, basementSarcophagus, basementPanel;
        Item studyTelescope, studyBoard, studyDesk;
        Casket diningRoomCasket, studyCasket, workshopBox, basementChest;
        
        // create the rooms
        hall = new Room("hall","%nYou stand in a hall of the collector's apartment. Of all paintings that %ndecorate the walls, the one that sticks out to you the most is the portrait of %nwho you assume is the owner of the house. You realize what's so unnerving %nabout the painting - the eyes seem to follow you wherever you go.","%nYou look around.There is a staircase that leads to the second floor, %na door to your right and a door to your left.");
        diningRoom = new Room("dining Room","%nYou enter a dining room. The only thing that indicates it's a dining room %nis an ornate table set in the middle of it. A staggering amount of display cases with treasures, %ndecorative urns and reliquaries make it look like a museum more than anything else.","%nOne of the cabinets immediately catches your eye.%nThere is a small sword, a brooch in the form of a star and %na jewelry casket, all decorated with diamonds.");
        library = new Room("library","%nYou walk into a room that is obviously a library, and quite an impressive one. %nYou cannot see the walls - it's all covered by the endless rows of bookshelves.","%nThere is a pile of books on a table in the right corner of the room, %none of them still open. You also notice another door next to it.");
        workshop = new Room("workshop","%nYou enter what you could best describe as a workshop, or a lab of some sort. %nStrange jars with liquids hanging from the ceiling, dozens of %nvial racks and the most peculiar looking workbenches %nmake you question whether it was worth coming here at all.","%nA big red-glowing vial on the desk captures you attention. Beside it lies %na fancy little box. You almost overlook a hatch on the floor, near %nwhat could be a distillation apparatus. You wonder where it leads.");
        secondFloor = new Room("second Floor","%nYou come up the stairs and reach the second-floor hall. %nThe clock on the wall shows <currentTime>.","%nYou see two doors - one to your right and another one to your left.");
        study = new Room("study","%nYou walk into a room that could be a study, or a personal office.%nMuch like the previous one, it is full of unusual items of all sorts.","%nAside from ancient tablets and armillary spheres that are beyond your understanding,%nyou see a telescope looking through an open window, a drawing board and a desk next to it.%nYou approach the desk. Among various schematics and blueprints %nthere is another casket that you could try to unlock.");
        basement = new Room("basement", "%nYou come down into the basement. Just when you thought it couldn't get any more %ndisturbing, what lies before you makes want to flee away from this wicked house. %nHowever, you find a way to curb your fear, and proceed further %ninto what looks like a chamber for some twisted experiments. ","%nIn the middle of the room you see a sarcophagus, surrounded by %nclanking machines with cogs and gears. You also notice a small ornate chest, %nnext to some sort of a control panel");
        bedroom = new Room("bedroom","%nYou shouldn't be here","%nJust as you reach for the doorknob you hear a clicking sound coming %nfrom the inside. You lean forward and peek through the keyhole. You are %nsurprised to see a totally normal looking room. There is a lone person sitting in front of a %ncomputer screen; luckily, too carried away to notice anything. You decide that %nit would be wise, not to disclose your presence and carefully step back.");
        
        // initialise room exits
        hall.setExit("right", library);
        hall.setExit("up", secondFloor);
        hall.setExit("left", diningRoom);
        
        diningRoom.setExit("right", hall);

        library.setExit("left", hall);
        library.setExit("right", workshop);
        
        workshop.setExit("left", library);
        workshop.setExit("down", basement);  //basement here
        
        basement.setExit("up", workshop);

        secondFloor.setExit("down", hall);
        secondFloor.setExit("left", bedroom);
        secondFloor.setExit("right", study);

        study.setExit("left", secondFloor);
        
        
        //initializing items:
        casketItemClasp = new Item("clasp", "Presumably a hat-clasp. Made of diamonds, of course.",200,10,false);
        diningRoomCabinet = new Item("cabinet","You inspect the display cabinet. Its contents remind you of the royals of the 19th century",100,2000,false);
        diningRoomCasket = new Casket("casket","A small casket, decorated with diamonds",20,100,true,casketItemClasp);
        diningRoomSword = new Item("sword","A small sword, made of silver and gold, with a diamond-studded hilt",100,50,false);
        diningRoomBrooch = new Item("brooch","A star-shaped diamond brooch",150,10,false);
        
        libraryBook = new Item("book", "A rather heavy tome on waveform manipulation. What is this guy up to?",10, 50, false);
        libraryTable = new Item("table", "A small round table with a pile of books on it, %none of them open. Someone might have used it recently.",1,1500,false);
        
        casketItemKey = new Item("key", "If only you knew what it unlocks", 5, 5, false);
        workshopHatch = new Item("hatch", "There is a heavy lock on it. Unfortunately, your hacking tool %nis not suitable for this particular kind of locks",0,2000,false);
        workshopHomunculus = new Item("vial","You try to see what's inside the vial. For a second there you think %nyou saw a humanoid-like creature with disproportionally large head and limbs, %njust before the cloudy liquid conceals it again.",5,300,false);
        workshopApparatus = new Item("apparatus","A heavy apparatus for strange purposes",3,1200,false);
        workshopBox = new Casket("box", "A small wooden box",5,100,true,casketItemKey);
        
        casketItemGemstone = new Item("gemstone","A hazy gemstone. Small, yet heavy.",100,10,false);
        basementSarcophagus = new Item("sarcophagus", "A stone sarcophagus in the middle of the room. The sight of thick wires that come out of the %nnearest machine and slide under the lid of the sarcophagus gives you anxiety. %nYou don't want to come anywhere near that thing. How did it end up down here in the first place?",10,4000,false);
        basementPanel = new Item("panel","You come closer and inspect the panel. You see a bunch of buttons and %nlevers; probably best not to mess with those.",5,4000,false);
        basementChest = new Casket("chest","A small ornate chest. You could try to hack it, or take it with you",5,100,true,casketItemGemstone);
        
        casketItemCharm = new Item("charm","A small charm crafted of metal you don't recognize.%nIt's cold, yet it feels comforting to the touch. Something tells you, %nyou've found your aunt's parting gift!",1500,5,false);
        studyTelescope = new Item("telescope","You look into the telescope but see no stars. Perhaps now is not %nthe right time for astronomical observations",100,1200,false);
        studyBoard = new Item("board","The board is filled with very detailed drawings of celestial bodies",1,1500,false);
        studyDesk = new Item("desk","You take a closer look at the desk. Lots of drafts scattered %nacross it and the casket is still there.",1,3000,false);
        studyCasket = new Casket("casket","Another small casket, with ostentatious carvings upon it. %nProbably to hold something of great value.",20,100,true,casketItemCharm);
        
        //filling rooms with items:
        diningRoom.setItem(diningRoomCasket);
        diningRoom.setItem(diningRoomSword);
        diningRoom.setItem(diningRoomBrooch);
        diningRoom.setItem(diningRoomCabinet);
        library.setItem(libraryBook);
        library.setItem(libraryTable);
        workshop.setItem(workshopHomunculus);
        workshop.setItem(workshopApparatus);
        workshop.setItem(workshopHatch);
        workshop.setItem(workshopBox);
        study.setItem(studyCasket);
        study.setItem(studyTelescope);
        study.setItem(studyBoard);
        study.setItem(studyDesk);
        basement.setItem(basementSarcophagus);
        basement.setItem(basementPanel);
        basement.setItem(basementChest);
        
        rooms = new ArrayList<>();
        rooms.add(hall);
        rooms.add(diningRoom);
        rooms.add(library);
        rooms.add(workshop);
        rooms.add(study);
        rooms.add(basement);
        player = new Player(hall);  // start Game hall
        
    }
    
    /**
     * Print out the opening message for the Game.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.printf("You've just sneaked into a house with an enormous private collection %nof ancient relics and valuables to retrieve your family jewels.%n");
        System.out.println("You recall that when in doubt, saying the magic word 'help' may be worth a try.");
        System.out.println("You also recall that you can 'leave' this house anytime you want.");
        
    }
    
    private void printEnding()
    {
        if(player.hasWinningCondition()) {   
            System.out.println("You found what you were looking for.");
            if(player.getScore() > 2000) {        //the best ending
                System.out.println("You also gathered enough resources to try to unveil");
                System.out.println("the mysteries of the strange house and with the power granted");
                System.out.println("by your aunt's charm, you know you will succeed.");
              } else {                            //an OK ending
                System.out.println("You made it home in one peace.");
                System.out.println("A few days later, you survived an attempted murder by sheer luck.");
                System.out.println("You have every reason to believe that it was your aunt's charm that kept you safe");
            }
        } else {                                   //RIP
            System.out.println("You couldn't find what you were looking for.");
            System.out.println("Unfortunately, a few days later you die under mysterious circumstances");
        }
    }
}