public class MineRunner
{
    public static void main( String args[] )
    {
        boolean game = true;
        boolean addedMines = false;
        boolean debug = false;
        boolean revealed = false;
        char action = 'c';
        String error = "";


        Minefield m = new Minefield(10, .20 );
        Display d = new Display(m);
        // Game g = new Game(m,d);
        Player p = new Player(d);

        while(game){
            if(!debug){
                // System.out.println(m.getTilesLeft());
                // Coord1, Coord2, Action
                int[] coord = p.getCoordinate(m, error); error = "";
                action = (char)coord[2];
                coord = new int[]{coord[0], coord[1]};

                // Enable debug
                if(action == 'd'){
                    debug = true; 
                    continue;
                }

                if(action == 0)
                    action = p.getAction();

                if(action == 'c'){
                    if(!addedMines){
                        m.addMines(coord);
                        addedMines = true;
                    }
                    if(m.isFlagged(coord)){
                        error = "That tile is flagged!";
                    }
                    if(m.isRevealed(coord)){
                        if(!m.revealAdjacent(coord)){
                            m.revealMines();
                            d.printGame("MINE --> GAME OVER");
                            game = false;
                            continue;
                        }
                    }
                    // System.out.println("CHECLED");
                    if(!m.checkTile(coord)){
                        m.revealMines();
                        d.printGame("MINE --> GAME OVER");
                        game = false;
                        continue;
                    }
                }

                else if(action == 'f'){
                    if(m.isRevealed(coord)){
                        error = "That tile is already revealed!";
                    }
                    else
                        m.toggleFlag(coord);
                }

                else if(action == 'e'){
                    m.revealMines();
                    d.printGame("EXITED --> GAME OVER");

                    game = false;
                    continue;
                }
                
                if(m.getTilesLeft() == 0){
                    m.revealMines();
                    d.printGame("ALL TILES CLEARED! BIG WIN!");
    
                    game = false;
                    continue;
                }
            }

            // Debug mode
            else{
                action = p.getActionDebug(error);
                error = "";

                if(action == 'r'){
                    if(revealed){
                        m.concealMines();
                        revealed = false;
                    }
                    else{
                        m.revealMines();
                        revealed = true;
                    }
                }

                if(action == 't'){
                    m.toggleMine(p.getCoordinate(m, "Toggle Mine"));
                    if(revealed)
                        m.revealMines();
                }

                if(action == 's'){
                    error = "tilesLeft: " + m.getTilesLeft() + "\n" +
                            "mines: " + m.getMines() + "\n" + 
                            "size: " + m.getSize();
                }

                if(action == 'b'){
                    debug = false; 
                    continue;
                }
            }
        }
    }
}