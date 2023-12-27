import java.util.ArrayList;
import java.util.List;

public class SillyPuzzle implements State {

    public static void main(String[] args) {
        SillyPuzzle startState = new SillyPuzzle(36);
        Search searcher = new BreadthFirstSearch();
        Solution solution = searcher.search(startState);
        solution.displayPath();  // or whatever means you want to display the solution
    }

    private int n;

    public SillyPuzzle(int n) {
        this.n = n;
    }

    public SillyPuzzle(SillyPuzzle oldPuzzle) {
        this.n = oldPuzzle.n;
    }

    @Override
    public List<Action> listActions() {
        ArrayList<Action> actions = new ArrayList<>();
        actions.add(new SillyPuzzleAction(-1));
        actions.add(new SillyPuzzleAction(+1));
        return actions;
    }

    @Override
    public boolean isGoalState() {
        return (n == 42);
    }

    @Override
    public void display() {
        System.out.println("the current number is " + n);
    }

    @Override
    public void performAction(Action action) {
        n = n + ((SillyPuzzleAction)action).delta;
    }

    @Override
    public State duplicate() {
        return new SillyPuzzle(this);
    }

    @Override
    public boolean equals(Object otherState) {
        SillyPuzzle otherSillyPuzzle = (SillyPuzzle) otherState;
        return (this.n == otherSillyPuzzle.n);
    }

    @Override
    public int heuristic() {
        return 0;
    }

    //@Override
    //public int heuristic() {
    //  return Math.abs(42 - n);
    //}

}