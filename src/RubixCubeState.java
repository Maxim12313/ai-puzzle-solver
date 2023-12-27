import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RubixCubeState implements State{
    //0=top back left, 1=top back right, 2=top front right, 3=top front left
    //4=bottom back left, 5=bottom back right, 6=bottom front right, 7=bottom front left

    //0=facing top bottom
    //1=facing left right
    //2=facing front back

    //orientationUnaffected%2

    public RubixCubeBlock[] cube;
//    public RubixCubeState(RubixCubeBlock[] cube){
//        this.cube = cube;
//    }

    public RubixCubeState(RubixCubeState state){
        cube = new RubixCubeBlock[8];
        RubixCubeBlock[] otherCube = state.cube;
        for (int i=0;i<otherCube.length;i++){
            cube[i] = otherCube[i].duplicate();
        }
    }

    public RubixCubeState(int moves){
        cube = new RubixCubeBlock[8];
        for (int i=0;i<8;i++){
            cube[i] = new RubixCubeBlock(i,0);
        }

//        display();

        //issue, will not accept it if solved with white top and yellow bottom


        SearchNode node = new SearchNode(this,null,0,0,null);

        node.currentState.display();
        System.out.println();

        for (int i=0;i<moves;i++){
            SearchNode[] nextNodes = node.getNextNodes();
            int index = (int)(Math.random()*nextNodes.length);
            node = nextNodes[index];
            node.prevAction.display();
        }

        RubixCubeState state = (RubixCubeState)node.currentState;
        this.cube = state.cube;
        System.out.println("length: "+moves+" (this is not optimized, so real length is actually <=)");
        System.out.println("-----------------");
    }

    public static void main(String[] args) {

        RubixCubeState startState = new RubixCubeState(20);
        Search searcher = new IterativeDeepeningDepthFirstSearch();
        Solution solution = searcher.search(startState);
        solution.displayPath();
        //more if they're all the same

    }
    @Override
    public List<Action> listActions() {
        List<Action> actions = Arrays.asList(
                RubixCubeAction.U,
                RubixCubeAction.U_,
                RubixCubeAction.F,
                RubixCubeAction.F_,
                RubixCubeAction.R,
                RubixCubeAction.R_);
        return actions;
    }

    @Override
    public boolean isGoalState() {
        for (int i=0;i<cube.length;i++){
            RubixCubeBlock block = cube[i];
            if (!block.correctlyPlaced(i)){
                return false;
            }
        }
        return true;
    }

//    @Override
    public void display() {
        for (int i=0;i<4;i++){
            System.out.print("|i: "+i+"    ");
            cube[i].printData();
        }
        System.out.println();
        for (int i=4;i<8;i++){
            System.out.print("|i: "+i+"    ");
            cube[i].printData();
        }
        System.out.println();
    }

    @Override
    public State duplicate() {
        return new RubixCubeState(this);
    }

    @Override
    public void performAction(Action action) {
        RubixCubeAction rubixCubeAction = (RubixCubeAction)action;
        int orientationUnaffected = rubixCubeAction.orientationUnaffected;
        boolean isPrime = rubixCubeAction.isPrime;
        int[] rotateIndexValues;

        if (orientationUnaffected==0){ //U
            //index 0 1 2 3 clockwise
            rotateIndexValues = new int[]{0,1,2,3};
        }
        else if (orientationUnaffected==1){//F
            //index 0 3 7 4 clockwise
            rotateIndexValues = new int[]{0,3,7,4};
        }
        else{//R
            //index 3 2 6 7 clockwise
            rotateIndexValues = new int[]{3,2,6,7};
        }

        if (isPrime){
            rotateForward(rotateIndexValues, orientationUnaffected);
        }
        else{
            rotateBackward(rotateIndexValues, orientationUnaffected);
        }
    }


    public void rotateForward(int[] rotateIndexValues, int orientationUnaffected){
        //current becomes previous
        //0 1 2 3 -> 3 0 1 2


        //rotate location
        RubixCubeBlock prevValue = cube[rotateIndexValues[0]];
        for (int i=1;i<rotateIndexValues.length;i++){
            int index = rotateIndexValues[i];
            RubixCubeBlock thisValue = cube[index];
            cube[index] = prevValue;
            prevValue = thisValue;
        }
        cube[rotateIndexValues[0]] = prevValue;

        //rotate orientation
        for (int i=0;i<rotateIndexValues.length;i++){
            int index = rotateIndexValues[i];
            RubixCubeBlock block = cube[index];
            int currentOrientation = block.orientation;
//            System.out.println("current: "+currentOrientation+"    unafffeected: "+orientationUnaffected);
            if (currentOrientation!=orientationUnaffected){
                int nextOrientation = 0;
                while (nextOrientation==currentOrientation || nextOrientation==orientationUnaffected){
                    nextOrientation++;
                }
                //should not be possible for it to be >3
                //0 1 2, will switch between 2 of those
                block.orientation = nextOrientation;
            }
        }
    }

    public void rotateBackward(int[] indexes, int orientationUnaffected){
        int[] reverseIndexes = new int[indexes.length];

        //reverse
        for (int i=0;i<indexes.length;i++){
            reverseIndexes[(reverseIndexes.length-1)-i] = indexes[i];
        }

        rotateForward(reverseIndexes, orientationUnaffected);
    }

    @Override
    public int heuristic() {
        return manhattanDistance();
    }

    public int manhattanDistance(){ //manhattanish.

        //Is this optimistic?

        int totalDimensionsDifferent = 0;
        for (int i=0;i<8;i++){
            RubixCubeBlock block = cube[i];
            int correctPosition = block.correctPosition;
            int[] correctTriplet = getTriplet(correctPosition);
            int[] currentTriplet = getTriplet(i);

            int dimensionsDifferent = 0;
            for (int j=0;j<3;j++){
                if (correctTriplet[j]!=currentTriplet[j]){
                    dimensionsDifferent++;
                }
            }
            totalDimensionsDifferent += dimensionsDifferent;
        }
        return totalDimensionsDifferent;
    }

    public int[] getTriplet(int pos){
        int y = pos/4; //up down (0 top, 1 bottom)
        int flatIndex = pos%4;
        int x= flatIndex%2; //left right (0 left, 1 right)
        int z = flatIndex/2; // back front (0 back, 1 front)
        return new int[]{x,y,z};
    }

    @Override
    public boolean equals(Object otherState){
        RubixCubeState other = (RubixCubeState)otherState;
        for (int i=0;i<cube.length;i++){
            if (!other.cube[i].equals(cube[i])){
                return false;
            }
        }
        return true;
    }
}
