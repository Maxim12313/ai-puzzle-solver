public class AStarSearch extends TreeSearch{

    public AStarSearch() {
        super(new FrontierPriorityQueue());
    }

    public Solution search(State startState){
        return super.search(startState);
    }

    public String toString(){
        return "AStarSearch";
    }


}
