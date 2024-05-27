public class Coordinate {
    private int row;
    private int col;

    public Coordinate(int row, int col){
        this.row = row;
        this.col = col;
    }

    public int[] getCoordinate(){
        return new int[]{row,col};
    }

    public int getRow(){
        return row;
    }
    
    public int getCol(){
        return col;
    }
}