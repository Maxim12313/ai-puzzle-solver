class DepthFirstSearch extends TreeSearch{

    public DepthFirstSearch(){
        super(new FrontierStack());

    }
    public Solution search(State startState) {
        return super.search(startState);
    }
    public String toString(){
        return "DepthFirstSearch";
    }

}