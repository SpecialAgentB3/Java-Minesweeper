public class Tile {
    private boolean isMine;
    private boolean isFlagged;
    private boolean isRevealed;
    private int adjacentMines;

    public Tile(){
        isMine = false;
        isFlagged = false;
        isRevealed = false;
        adjacentMines = 0;
    }
    public Tile(int adj){
        isMine = false;
        isFlagged = false;
        isRevealed = false;
        adjacentMines = adj;
    }

    public void setMine(boolean m){isMine = m;}
    public void setFlag(boolean f){isFlagged = f;}
    public void setRevealed(boolean r){isRevealed = r;}
    public void setAdjacent(int a){adjacentMines = a;}

    public boolean getMine(){return isMine;}
    public boolean getFlag(){return isFlagged;}
    public boolean getRevealed(){return isRevealed;}
    public int getAdjacent(){return adjacentMines;}

    public String toString(){
        if(isRevealed){
            if(isMine)
                return "x";
            if(adjacentMines == 0)
                return " ";
            return adjacentMines + "";
        }

        if(isFlagged)
            return "F";

        // if(isMine)
        //     return "x";
        // if(!isRevealed)
        return "■";
    }
}
