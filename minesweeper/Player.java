import java.util.Scanner;

public class Player {
    Display display;

    public Player(Display d){
        display = d;
    }

    public String getInput(){        
        Scanner keyboard = new Scanner(System.in);
        String input = keyboard.nextLine();

        return input;
    }
    
    // 1345: true   13g5: false
    public boolean isNumber(String str){
        for(int i = 0; i < str.length(); i++){
            if(!Character.isDigit(str.charAt(i)))
                return false;
        }
        return true;
    }

    public int[] getCoordinate(Minefield m, String error){
        display.printGame("Enter Coordinate", error);
        String str = getInput().trim(); if(str.isEmpty()){str = " ";}
        int fieldSize = m.getSize();
        String[] strArray = str.split(" ");
        int[] coordinate = new int[]{0,0,0};
        
        // Checks for special actions
        if(str.equals("debug") || str.equals("d"))
            return new int[]{-1,-1, 'd'};
        if(str.equals("end") || str.equals("exit") || str.equals("e"))
            return new int[]{-1,-1, 'e'};
        
        
        
        // Returns the first two integers seperated by a space
        for(int i = 0; (i < strArray.length) && (i < 2); i++){
            if(!isNumber(strArray[i]))
            return getCoordinate(m, "Invalid input: not a number");
            
            coordinate[i] = Integer.decode(strArray[i]);
            
            if(coordinate[i] > fieldSize-1)
            return getCoordinate(m, "Invalid input: out of bounds");
        }
        if(strArray.length < 2)
        return getCoordinate(m, "Invalid input: not a coordinate");

        // Add action at end of coordinate
        if(strArray.length >= 3){
            if(strArray[2].equals("c") || strArray[2].equals("check"))
                coordinate[2] = 'c';
            if(strArray[2].equals("flag") || strArray[2].equals("f"))
                coordinate[2] = 'f';
        }
        
        return coordinate;
    }
    
    public char getAction(){
        display.printGame("Enter Action (flag, check, back)");
        String str = getInput().trim().toLowerCase();
        
        if(str.equals("flag") || str.equals("f") || str.equals("o")){
            return 'f';
        }
        if(str.equals("back") || str.equals("b")){
            return 'b';
        }
        if(str.equals("end") || str.equals("exit") || str.equals("e")){
            return 'e';
        }
        if(str.isEmpty() || str.equals("check") || str.equals("c")){
            return 'c';
        }

        return 'c';
    }

    public char getActionDebug(String error){
        display.printGame("Enter Action (back, reveal, toggle, stats)", error);
        String str = getInput().trim().toLowerCase();

        if(str.equals("back") || str.equals("b") || str.equals("exit") || str.equals("e")){
            return 'b';
        }
        if(str.equals("reveal") || str.equals("r")){
            return 'r';
        }
        if(str.equals("toggle") || str.equals("t")){
            return 't';
        }
        if(str.equals("stats") || str.equals("s")){
            return 's';
        }

        return 'b';
    }

}
