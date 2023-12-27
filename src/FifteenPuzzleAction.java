public class FifteenPuzzleAction implements Action{
    public int swapCellIndex;
    public String name;
    public FifteenPuzzleAction(int swapCellIndex, String name){ //swap index is the index you swap the gap index with to move
        this.swapCellIndex = swapCellIndex;
        this.name = name;
    }

    @Override
    public void display() {
//        int rowMovement = 4;
//        int colMovement = 1;
//        int swapCellRow = swapCellIndex/rowMovement;
//        int swapCellCol = (swapCellIndex%rowMovement)/colMovement;
//        System.out.println("swapCellRow: "+swapCellRow+"    swapCellCol: "+swapCellCol);
        System.out.println(name);
    }

    @Override
    public String toString(){
        return name;
    }

    @Override
    public int getCost() {
        return 1;
    }
}
