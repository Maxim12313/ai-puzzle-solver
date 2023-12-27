import java.util.List;

public class SearchNode implements Comparable<Object>{

    public State currentState;
    public SearchNode parentNode;
    public int depth;
    public Action prevAction;
    public int cost;
    public SearchNode(State currentState, SearchNode parentNode,int cost,int depth, Action prevAction){
        this.currentState = currentState;
        this.parentNode = parentNode;
        this.cost = cost;
        this.depth = depth;
        this.prevAction = prevAction;
    }

    public SearchNode[] getNextNodes(){
        List<Action> actions = currentState.listActions();
        SearchNode[] nextNodes = new SearchNode[actions.size()];
        for (int i=0;i<actions.size();i++){
            State nextState = currentState.duplicate();
            Action action = actions.get(i);
            nextState.performAction(action);
            SearchNode nextNode = new SearchNode(nextState,this,depth+1,cost+action.getCost(),action);
            nextNodes[i] = nextNode;
        }
        return nextNodes;
    }

    public int evaluate(){
        return currentState.heuristic()+cost;
    }

    @Override
    public int compareTo(Object otherNode) {
        int thisValue = evaluate();
        int otherValue = ((SearchNode)otherNode).evaluate();

        if (thisValue<otherValue) return -1;
        else if (thisValue==otherValue) return 0;
        else return 1;
    }
}
