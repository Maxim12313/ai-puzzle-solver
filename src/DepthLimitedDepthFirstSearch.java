class DepthLimitedDepthFirstSearch extends TreeSearch{

    private int depthLimit;
    public DepthLimitedDepthFirstSearch(int depthLimit){
        super(new FrontierStack());
        this.depthLimit = depthLimit;

    }
    public Solution search(State startState) {
        return super.search(startState);
    }

    public boolean shouldPruneThisNode(SearchNode n){
        return n.depth>depthLimit;
    }

    public String toString(){
        return "DepthLimitedDepthFirstSearch";
    }
}
