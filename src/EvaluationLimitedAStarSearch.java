class EvaluationLimitedAStarSearch extends TreeSearch{

    private final int threshold;
    private int nextThreshold;
    public EvaluationLimitedAStarSearch(int threshold){
        super(new FrontierQueue());
        this.threshold = threshold;
        this.nextThreshold = Integer.MAX_VALUE;

    }
    public boolean shouldPruneThisNode(SearchNode n){
        int evaluation = n.evaluate();
        if (evaluation>threshold){
            if (evaluation<nextThreshold){
                nextThreshold = evaluation;
            }
            return true;
        }
        return false;
    }

    public String toString(){
        return "DepthLimitedAStarSearch";
    }

    public int getNextThreshold(){
        return nextThreshold;
    }
}
