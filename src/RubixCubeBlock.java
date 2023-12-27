public class RubixCubeBlock {

    public final int correctPosition;
    public int orientation;
    public RubixCubeBlock(int correctPosition, int orientation){
        this.correctPosition = correctPosition;
        this.orientation = orientation;
    }


    public boolean equals(RubixCubeBlock other){
        return correctPosition==other.correctPosition && orientation==other.orientation;
    }

    public RubixCubeBlock duplicate(){
        return new RubixCubeBlock(correctPosition,orientation);
    }

    public boolean correctlyPlaced(int position){
        if (orientation==0 && position==correctPosition){
            return true;
        }
        return false;
    }

    public void printData(){
        System.out.print("correctPosition: "+correctPosition+"     orientation: "+orientation+"    ");
    }
}
