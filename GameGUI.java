import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import java.util.List;      //not used
import java.util.ArrayList;     //not used 
import java.util.Iterator;      //not used ?
import java.util.HashMap;
import java.io.*;

//transp scroll
//fix char display 
//needs refactoring obv
//switch room to separate method
//more buttons
//contentPane.add doesnt work with ++componentNumber for some reason

public class GameGUI 
{
    private JFrame window;
    private JLabel hall,library,workshop,study;
    private JTextField input;
    private JTextArea output;
    private JLayeredPane contentPane;
    private JPanel rightPanel;
    private PrintStream standardOut;
    private String userInput;
    private JScrollPane scrollPane;
    private HashMap <String, JLabel> rooms;
    private Game game;
    private int componentNumber = 1,verticalScrollBarMaximumValue;
    private String currentRoomString;
    
    public GameGUI () 
    {
        game = new Game();
        initGUI();
        game.startGame();
    }
    
    void initGUI()
    {
        window = new JFrame("Inheritance: the Game");
        userInput = new String("go left");
        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        contentPane = new JLayeredPane();
        rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBounds(500,100,450,300);
        rightPanel.setBackground(Color.black);
        rightPanel.setForeground(Color.black);
        rightPanel.setOpaque(false);
        
        initRooms();
        
        contentPane.setBackground(Color.black);
        contentPane.setLayout(null);
        contentPane.setOpaque(true);
        int wPref = 1017;
        int hPref = 587;
        
        JLabel canvas = new JLabel(new ImageIcon("canvas1kx563.jpg"));
        canvas.setSize(wPref, hPref);
        canvas.setLocation(0,0);
        
        //JLabel firstRoom = new JLabel(new ImageIcon("hall.png"));
        currentRoomString = new String("hall");
        hall.setVisible(true);
        
        contentPane.add(canvas,1,0);
        contentPane.add(rightPanel,7,0);
        
        initInput();
        initOutput();
        updateOutput();
        
        window.getContentPane().add(contentPane);
        //window.getContentPane().add(rightPanel);
        window.setResizable(false);
        window.setSize(wPref+10, hPref+30);

        window.setVisible(true);
        
        //System.out.println("components count:" + componentNumber);
    }
    
    void initRooms()
    {   
         hall = new JLabel(new ImageIcon("hall.png"));
         library = new JLabel(new ImageIcon("library.png"));
         workshop = new JLabel(new ImageIcon("workshop.png"));
         study = new JLabel(new ImageIcon("study.png"));
        
        hall.setBounds(90,100,303,383);
        library.setBounds(90,100,303,383);
        study.setBounds(90,100,303,383);
        workshop.setBounds(90,100,303,383);
        
        contentPane.add(hall,6,0);
        contentPane.add(library,6,1);
        contentPane.add(workshop,6,2);
        contentPane.add(study,6,3);
        
        HashMap rooms = new HashMap<>();
        rooms.put("hall",hall);
        rooms.put("library",library);       //not used
        rooms.put("workshop",workshop);
        rooms.put("study",study);
        
        resetRooms();
    }
    //not used
    void setPicture(JLabel pic)
    {
        contentPane.add(pic,++componentNumber,0);
        pic.setBounds(90,100,303,383);
    }
    
    void initOutput()
    {   
        int prefW = 450;
        int prefH = 300;
        
        output = new JTextArea() ;
        
        output.setOpaque(false);
        output.setEditable(false);
        output.setBackground(Color.BLACK);
        output.setForeground(Color.WHITE);
        //output.setBounds(500,100,450,300);
        //output.setLocation(500,100);
        output.setVisible(true);
        //output.setPreferredSize(new Dimension(450,300));
        
        scrollPane = new JScrollPane(output);
        scrollPane.setBackground(Color.BLACK);
        scrollPane.setForeground(Color.BLACK);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollDown(scrollPane);
        scrollPane.setBorder(null);
        rightPanel.add(scrollPane);
        
    }
    void scrollDown(JScrollPane sPane)
    {
        verticalScrollBarMaximumValue = sPane.getVerticalScrollBar().getMaximum();
        sPane.getVerticalScrollBar().addAdjustmentListener(
            e -> {
                if ((verticalScrollBarMaximumValue - e.getAdjustable().getMaximum()) == 0)
                    return;
                e.getAdjustable().setValue(e.getAdjustable().getMaximum());
                verticalScrollBarMaximumValue = sPane.getVerticalScrollBar().getMaximum();
            });
    }
    void initInput()
    {
        
        String inputString = null;
        input = new JTextField();
        //textField.setColumns(50);

        input.setBackground(Color.BLACK);
        input.setForeground(Color.WHITE);
        //textField.setFont(Font.getFont(Font.SERIF));
        input.setToolTipText("enter your command");
        input.setPreferredSize( new Dimension( 200, 24 ) );
        input.setMaximumSize(input.getPreferredSize());
        input.setVisible(true);
        input.setBounds(600, 500, 200, 24);
        input.setBorder(null);
        //textField.setOpaque(false);
        input.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){

                userInput = input.getText();
                game.gameLoop(userInput);
                currentRoomString = game.player.currentRoom.getName();
                //System.out.print(currentRoomString);
                resetRooms(); 
                switch(currentRoomString){
                case "hall":
                    hall.setVisible(true);
                    break;
                case "library":
                    library.setVisible(true);
                    break;
                case "workshop":                    //needs relocation to another method
                    workshop.setVisible(true);
                    break;
                case "study":
                    study.setVisible(true);
                    break;
                }
            }
        });
        
        contentPane.add(input,5,0);
        
    }
    void updateOutput()
     {
         PrintStream printStream = new PrintStream(new CustomOutputStream(output));
         output.setCaretPosition(output.getText().length());
         standardOut = System.out;
         // re-assigns standard output stream and error output stream
         System.setOut(printStream);
         System.setErr(printStream);
    
     }
    
    private void resetRooms()
    {   
        hall.setVisible(false);
        library.setVisible(false);
        workshop.setVisible(false);
        study.setVisible(false);
    }
}