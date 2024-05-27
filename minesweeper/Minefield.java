import java.util.ArrayList;

public class Minefield
{
    // ■ □
    private Tile board[][];
    private boolean revealed[][];
    // private String[][] board;
    private int mines;
    private int size;
    private int tilesLeft;

    private final int[][] adjacentCells = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1, 1}};
    // private final int[][] adjacentCells2 = {{-1,0},{1,0},{0,-1},{0,1}};

    public Minefield(int size){
        setMinefield(size, getMineDensity(size, .25));
    }
    public Minefield(int size, double density){
        setMinefield(size, getMineDensity(size, density));
    }
    public Minefield(int size, int mines){
        setMinefield(size, mines);
    }
    private void setMinefield(int size, int mines){
        // System.out.println("SDHFB DSUHFB SDFBSD FKHSDB FK   "  + mines);
        this.size = size;
        this.mines = mines;
        this.revealed = new boolean[size][size];
        // Init board
        board = new Tile[size][size];
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                board[i][j] = new Tile(0);
            }
        }
    }

    public void addMine(int row, int col){
        board[row][col].setMine(true);
    }

    public void addMines(int[] coord){
        tilesLeft = size*size;
        // Cycles i over all possible squares; selects random squares to add mines to
        ArrayList<Coordinate> availableTiles = new ArrayList<Coordinate>();
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                boolean pass = false;
                // Checks if current tile is an adjacent or selected tile- enables "pass" if so
                for(int[] place : adjacentCells){
                    if(isntNull(i+place[0],j+place[1]) && (((i==place[0]+coord[0]) && (j==place[1]+coord[1])) || (i==coord[0] && j==coord[1]))){
                        pass = true; break;
                }}
                if(!pass){
                    availableTiles.add(new Coordinate(i,j));
                }
            }
        }

        int count = mines;
        while(count > 0){
            int cell = (int)(Math.random()*availableTiles.size());
            // System.out.println(count + ", " + availableTiles.get(cell).getRow() + ", " + availableTiles.get(cell).getCol());
            // System.out.println(tilesLeft);
            
            int row = availableTiles.get(cell).getRow();
            int col = availableTiles.get(cell).getCol();
            availableTiles.remove(cell);

            addMine(row, col);
            tilesLeft--;
            count--;

            if(availableTiles.size() == 0)
                count = 0;
        }

        calculateAdjacent();
    }
    public void addMines(){
        int[] loc = {-100, -100};
        addMines(loc);
    }
    
    public void calculateAdjacent(){
        int total = 0;
        // Cycle over all tiles
        for(int i=0; i < size; i++){
            for(int j=0; j < size; j++){
                // Tile isn't a mine
                if(!board[i][j].getMine()){
                    total = 0;
                    // Cycle over 8 adjacent cells
                    for(int[] place : adjacentCells){
                        // If current adjacent tile is a mine and isn't out of the array
                        if((i+place[0] > -1 && j+place[1] > -1) && (i+place[0] < size && j+place[1] < size) && (board[i+place[0]][j+place[1]].getMine()))
                            total++;
                        else
                            continue;
                    }
                    board[i][j].setAdjacent(total);
                }
                // Tile is a Mine
                else
                    board[i][j].setAdjacent(9);
            }
        }
    }

    // Returns false if the point isn't wthin bounds of "board"
    public boolean isntNull(int row, int col){
        if((row < -1 || col < -1) || (row > size || col > size))
            return false;
        return true;
    }
    
    public boolean isMine(int row, int col){
        return board[row][col].getMine();
    }
    
    public void revealTile(int row, int col){
        board[row][col].setRevealed(true);
        revealed[row][col] = true;
        if(!board[row][col].getMine())
            tilesLeft--;
        // System.out.println("REVEAL" + tilesLeft);
    }

    // Recursively loops over all hidden + zero-adjacent tiles and reveals them
    private void revealConnecting(int row, int col){
        revealTile(row, col);
        for(int[] place : adjacentCells){
            if((row+place[0] > -1 && col+place[1] > -1) && 
              (row+place[0] < size && col+place[1] < size) &&
              (board[row][col].getAdjacent() == 0 || board[row+place[0]][col+place[1]].getAdjacent() == 0) &&
              !board[row+place[0]][col+place[1]].getRevealed() &&
              !board[row+place[0]][col+place[1]].getFlag())
            {
                if(board[row+place[0]][col+place[1]].getAdjacent() == 0)
                    revealConnecting(row + place[0], col + place[1]);
                else
                    revealTile(row + place[0], col + place[1]);
            }
        }
    }
    
    // Returns false if a mine is hit
    public boolean checkTile(int row, int col){
        if(isMine(row, col)){
            revealTile(row, col);
            return false;
        }

        revealConnecting(row, col);
        return true;
    }
    public boolean checkTile(int[] coord){return checkTile(coord[0], coord[1]);}
    
    // Checks all 8 adjacent squares of a revealed tile if flag conditions are met
    public boolean revealAdjacent(int row, int col){
        int total = 0;
        // revealTile(row, col);
        for(int[] place : adjacentCells){
            if((row+place[0] > -1 && col+place[1] > -1) && 
              (row+place[0] < size && col+place[1] < size))
            {
                System.out.println(row+place[0] + " " + col+place[1]);
                if(board[row+place[0]][col+place[1]].getFlag())
                    total++;
            }
        }
        System.out.println("total: " + total + " adjacent: " + board[row][col].getAdjacent() + "\n" + row + ", " + col);
        if(total == board[row][col].getAdjacent()){
            for(int[] place1 : adjacentCells){
                if((row+place1[0] > -1 && col+place1[1] > -1) && 
                (row+place1[0] < size && col+place1[1] < size))
                {
                    if(!board[row+place1[0]][col+place1[1]].getFlag())
                        if(!checkTile(row+place1[0], col+place1[1]))
                            return false;
                }
            }
        }
        return true;
    }
    public boolean revealAdjacent(int[] coord){return revealAdjacent(coord[0], coord[1]);}
    
    public void toggleFlag(int row, int col){
        if(board[row][col].getFlag())
            board[row][col].setFlag(false);
        else
            board[row][col].setFlag(true);
    }
    public void toggleFlag(int[] coord){toggleFlag(coord[0], coord[1]);}
    
    public void toggleMine(int row, int col){
        if(board[row][col].getMine()){
            board[row][col].setMine(false);
            mines--;
            tilesLeft++;
        }
        else{
            board[row][col].setMine(true);
            mines++;
            tilesLeft--;
        }
        board[row][col].setRevealed(false);
        calculateAdjacent();
    }
    public void toggleMine(int[] coord){toggleMine(coord[0], coord[1]);}
    
    public void revealMines(){
        for(int i=0; i < size; i++){
            for(int j=0; j < size; j++){
                // Tile isn't a mine
                if(board[i][j].getMine()){
                    revealTile(i, j);
                }
            }
        }
    }
    
    public void concealMines(){
        for(int i=0; i < size; i++){
            for(int j=0; j < size; j++){
                // Tile isn't a mine
                if(board[i][j].getMine()){
                    board[i][j].setRevealed(false);
                    revealed[i][j] = false;
                }
            }
        }
    }



    public int getMineDensity(int size, double density){
        return (int)(density * size*size);
    }
    

    public boolean isFlagged(int[] coord){
        return board[coord[0]][coord[1]].getFlag();
    }
    public boolean isRevealed(int[] coord){
        return board[coord[0]][coord[1]].getRevealed();
    }

    public int getMines(){return mines;}
    public int getTilesLeft(){return tilesLeft;}
    public Tile[][] getBoard(){return board;}
    public int getSize(){return size;}
}