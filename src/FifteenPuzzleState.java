import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FifteenPuzzleState implements State{

    public long board;
    public int gapIndex;
    HashMap<String,String> bitDecimalReverse;

    public static void main(String[] args) {
        int moves=50;
        FifteenPuzzleState startState = new FifteenPuzzleState(moves);
        Search searcher = new IterativeDeepeningAStarSearch();
        Solution solution = searcher.search(startState);
        solution.displayPath();  // or whatever means you want to display the solution
        //add something that compares steps backwards
    }

    public FifteenPuzzleState(FifteenPuzzleState lastState){

        board = lastState.board;
        gapIndex = lastState.gapIndex;
    }

    public FifteenPuzzleState(long board){
        this.board = board;
        for (int i=0;i<16;i++){
            int value = getCellValue(i);
            if (value==0){
                this.gapIndex = i;
                break;
            }
        }
    }

    public FifteenPuzzleState(int moves){

        board = 0;
        gapIndex = 0;
        for (int i=0;i<16;i++){
            setCellValue(i,i);
        }

        SearchNode node = new SearchNode(this,null,0,0,null);

//        node.currentState.display();

        for (int i=0;i<moves;i++){



            SearchNode[] nextNodes = node.getNextNodes();
            node = nextNodes[(int)(Math.random()*nextNodes.length)];

            if (i>1){ //third move
                //while the node state boardm = grandparents state board
                long thisBoard = ((FifteenPuzzleState)node.currentState).board;
                long grandParentBoard = ((FifteenPuzzleState)node.parentNode.parentNode.currentState).board;
                while(thisBoard==grandParentBoard){
                    node = nextNodes[(int)(Math.random()*nextNodes.length)];
                    thisBoard = ((FifteenPuzzleState)node.currentState).board;
                    grandParentBoard = ((FifteenPuzzleState)node.parentNode.parentNode.currentState).board;
                }
            }

//            node.currentState.display();
            node.prevAction.display();
        }

        FifteenPuzzleState state = (FifteenPuzzleState)node.currentState;
        board = state.board;
        gapIndex = state.gapIndex;
        System.out.println("length: "+moves+" (this is not optimized, so real length is actually <=)");
        System.out.println("-----------------");

    }

    public int heuristic(){
        int manhattanDistance = manhattanDistance();
        return manhattanDistance;
    }



    public int manhattanDistance(){
        int totalDist = 0;
        boolean fourMisplaced = false;
        boolean oneMisplaced = false;

        for (int i=0;i<16;i++){
            int value = getCellValue(i);

            if (value==0) continue; //skip empty tile

            int desiredRow = value/4;
            int desiredCol = value%4;

            int currentRow = i/4;
            int currentCol = i%4;

            if (value==4 && currentRow!=0){
                fourMisplaced=true;
            }
            else if (value==2 && currentCol!=0){
                oneMisplaced=true;
            }

            int colDist = Math.abs(desiredCol-currentCol);
            int rowDist = Math.abs(desiredRow-currentRow);

            totalDist+= colDist+rowDist;
        }

//        if (fourMisplaced && oneMisplaced)totalDist+=2; //last move rule

        return totalDist;
    }

    //I never tested any of these extra rules

//    public int cornerTilesRule(){
//        int[] cornerIndexes = new int[]{
//                0,3,12,15
//        };
//
//        int extraMoves = 0;
//        for (int index:cornerIndexes){
//            int cellValue = getCellValue(index);
//            if (cellValue==index && cellValue==0) continue;
//
//            int r = index/4;
//            int c = index%4;
//
//            if (r+1<4){
//                int otherIndex = (r+1)*4+c;
//                if (correctlyPlaced(otherIndex))extraMoves+=2;
//            }
//            if (r-1>=0){
//                int otherIndex = (r-1)*4+c;
//                if (correctlyPlaced(otherIndex))extraMoves+=2;
//            }
//            if (c+1<4){
//                int otherIndex = r*4+c+1;
//                if (correctlyPlaced(otherIndex))extraMoves+=2;
//            }
//            if (c-1>=0){
//                int otherIndex = r*4+c-1;
//                if (correctlyPlaced(otherIndex))extraMoves+=2;
//            }
//        }
//        return extraMoves;
//    }
//
//    public boolean correctlyPlaced(int otherIndex){
//        int otherCellValue = getCellValue(otherIndex);
//        return otherIndex==otherCellValue;
//
//    }
//
//    public int linearConflictRule(){
//        int extraMoves = 0;
//        for (int r=1;r<4;r++){
//            for (int c=0;c<4;c++){
//                int thisIndex = r*4+c;
//                int thisValue = getCellValue(thisIndex);
//
//                int aboveIndex = thisIndex-4;
//                int aboveValue = getCellValue(aboveIndex);
//
//                if (aboveValue+1==thisValue || thisValue+1==aboveValue){
//                    extraMoves+=2;
//                }
//            }
//        }
//
//        for (int r=0;r<4;r++){
//            for (int c=1;c<4;c++){
//                int thisIndex = r*4+c;
//                int thisValue = getCellValue(thisIndex);
//
//                int leftIndex = thisIndex-1;
//                int leftValue = getCellValue(leftIndex);
//
//                if (leftValue+1==thisValue || thisValue+1==leftValue){
//                    extraMoves+=2;
//                }
//            }
//        }
//        return extraMoves;
//    }

    public int getCellValue(int index){
        index*=4;
        return (int)((board>>index) & 0b1111);
    }

    public void setCellValue(int index,long value) {
        index *= 4;
        board = board | (value << index);
    }

    public void setCellZero(int index){
        index*=4;
        long value = (long)getCellValue(index/4)<<index; //multiplied by 4 in getCellValue
        board = board ^ value;
    }


    @Override
    public List<Action> listActions() {
        ArrayList<Action> actions = new ArrayList<>();

        int rowMovement = 4;
        int colMovement = 1;
        int gapRow = gapIndex/rowMovement;
        int gapCol = (gapIndex%rowMovement)/colMovement;

//        System.out.println("gapIndex: "+gapIndex+"    gapRow: "+gapRow+"    gapCol: "+gapCol);

        if (gapRow>0){ //move up possible
            actions.add(new FifteenPuzzleAction(gapIndex-rowMovement,"Up"));
        }
        if (gapRow < 3) { //move down possible
            actions.add(new FifteenPuzzleAction(gapIndex+rowMovement,"Down"));
        }
        if (gapCol>0){ //move left possible
            actions.add(new FifteenPuzzleAction(gapIndex-colMovement,"Left"));
        }
        if (gapCol<3){ //move right possible
            actions.add(new FifteenPuzzleAction(gapIndex+colMovement,"Right"));
        }

        return actions;
    }
    @Override
    public boolean isGoalState() {
        return board==0xfedcba9876543210L; //I got this from testing
    }

    @Override
    public void display() {
        for (int r=0;r<4;r++){
            String value = "";
            for (int c=0;c<4;c++){
                int index = r*4+c;
                value+=getCellValue(index)+"    ";
            }
            System.out.println(value);
        }
    }

    @Override
    public State duplicate() {
        return new FifteenPuzzleState(this);
    }

    @Override
    public void performAction(Action action) {
        FifteenPuzzleAction usableAction = (FifteenPuzzleAction)action;
        int swapCellIndex = usableAction.swapCellIndex;
        setCellValue(gapIndex,getCellValue(swapCellIndex));
        setCellZero(swapCellIndex);
        gapIndex = swapCellIndex;
    }

    @Override
    public boolean equals(Object otherState){
        FifteenPuzzleState other = (FifteenPuzzleState)otherState;
        return board==other.board;
    }
}
