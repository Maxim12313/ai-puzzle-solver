import java.util.List;

class Solution{
    public State startState;
    public State finalState;
    public List<SearchNode> path;

    public Solution(State startState, State finalState, List<SearchNode> path){
        this.startState = startState;
        this.finalState = finalState;
        this.path = path;
    }
    public void displayFinalState(){
        finalState.display();
    }

    public void displayPath(){
        for (int i=1;i<path.size();i++){
            SearchNode node = path.get(i);
            System.out.println(node.prevAction);
        }
        double length = getPathLength();
        System.out.println("length: "+length);
    }

    public double getPathLength(){
        return path.size()-1; //do i count just num of actions, so number of states-1?
    }
}