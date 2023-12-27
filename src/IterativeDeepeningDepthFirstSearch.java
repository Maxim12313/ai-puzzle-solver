class IterativeDeepeningDepthFirstSearch implements Search{

    public Solution search(State startState) {
        Solution solution = null;
        for (int i=1;solution==null;i++){
            DepthLimitedDepthFirstSearch searcher = new DepthLimitedDepthFirstSearch(i);
            solution = searcher.search(startState);
        }
        return solution;
    }

    public String toString(){
        return "IterativeDeepeningDepthFirstSearch";
    }

}