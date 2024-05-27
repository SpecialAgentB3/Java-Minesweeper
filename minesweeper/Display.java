public class Display
{
    private Minefield minefield;
    // private final int[][] adjacentCells = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1, 1}};

    public Display(Minefield minefield){
        this.minefield = minefield;
    }

    public void addWhiteSpace(){
        System.out.println("\n\n\n\n\n\n\n\n\n");
    }

    public void printArray(boolean[][] row){
        for(boolean[] col : row){
            for(boolean val : col){
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }
    public void printArray(int[][] row){
        for(int[] col : row){
            for(int val : col){
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }
    public void printArray(String[][] row){
        for(String[] col : row){
            for(String val : col){
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }
    public void printArray(Tile[][] row){
        int i = -1;
        for(Tile[] col : row){
            i = (i+1)%10;
            System.out.print(i + " ");
            for(Tile val : col){
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }
    // public void printArray(Tile[][] arr, int[] coord){
    //     for(int i = 0; i < arr.length; i++){
    //         for(int j = 0; j < arr[0].length; j++){
    //             String out = arr[i][j] + "";
    //             // If current coord is the selected one
    //             if(i==coord[0] || (j+1==coord[1] || j==coord[1])){
    //                 out += "-";
    //             }
    //             else
    //                 out += " ";
    //             System.out.print(out);
    //         }
    //         System.out.println();
    //     }
    // }

    public void printGame(String message){
        addWhiteSpace();
        System.out.print(message + "\n  ");

        for(int i = 0; i < minefield.getSize(); i++){
            System.out.print(i%10 + " ");
        }
        System.out.println();

        printArray(minefield.getBoard());
    }
    public void printGame(String message, String error){
        printGame(error + "\n" + message);
    }
    // public void printGame(String message, int[] coord){
    //     System.out.println(message);
    //     addWhiteSpace();
    //     printArray(minefield.getBoard(), coord);
    // }

}