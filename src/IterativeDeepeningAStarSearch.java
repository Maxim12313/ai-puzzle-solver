

public class IterativeDeepeningAStarSearch implements Search {

    public Solution search(State startState){
        Solution solution = null;
        int threshold = startState.heuristic();
        while (solution==null){
            EvaluationLimitedAStarSearch searcher = new EvaluationLimitedAStarSearch(threshold);
            solution = searcher.search(startState);
            threshold = searcher.getNextThreshold();
        }
        return solution;
    }

    public String toString(){
        return "IterativeDeepeningAStarSearch";
    }
}
