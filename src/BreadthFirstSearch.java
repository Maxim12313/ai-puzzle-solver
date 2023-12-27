class BreadthFirstSearch extends TreeSearch{

    public BreadthFirstSearch(){
        super(new FrontierQueue());
    }
    public Solution search(State startState) {
        return super.search(startState);
    }
    public String toString(){
        return "BreadthFirstSearch";
    }
}