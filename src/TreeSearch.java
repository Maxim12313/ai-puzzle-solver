import java.util.ArrayList;

public abstract class TreeSearch implements Search {

    private Frontier frontier;
    private int max;

    public TreeSearch(Frontier frontier) {
        this.frontier = frontier;
    }


    boolean shouldGrandparentPrune(SearchNode node){
        //node.parentNode is only ever null at the root, and nextNode can't be that, so only need to check grandparent
        if (node.parentNode.parentNode==null){
            return false;
        }

        State thisState = node.currentState;
        State grandparentState = node.parentNode.parentNode.currentState;
        return thisState.equals(grandparentState);
    }

    boolean shouldPruneThisNode(SearchNode node){ //should this be a function of a node? Looking at parent and child
        return false;
    }
    public Solution search(State startState){
        frontier.clear();
        SearchNode start = new SearchNode(startState,null,0,0,null);
        frontier.insert(start);

        while (!frontier.isEmpty()){
            SearchNode node = frontier.removeNext();
            if (node.currentState.isGoalState()){
                ArrayList<SearchNode> path = findPath(node);
                return new Solution(startState,node.currentState,path);
            }
            else{
                addNextNodes(node);
            }
        }
        return null;
    }


    public void addNextNodes(SearchNode node){
        SearchNode[] nextNodes = node.getNextNodes();
        for (SearchNode nextNode: nextNodes){
            if (!shouldPruneThisNode(nextNode) && !shouldGrandparentPrune(nextNode)){
                frontier.insert(nextNode);
            }
        }
    }

    public ArrayList<SearchNode> findPath(SearchNode node){
        ArrayList<SearchNode> path = new ArrayList<>();
        do {
            path.add(0,node);
            node = node.parentNode;
        }while (node!=null);
        return path;
    }


}
