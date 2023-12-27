import java.util.List;
interface State
{
    // public constructor (maybe with inputs) that starts at a legal initial state

    // returns a list of the legal actions possible from this state
    public List<Action> listActions();

    // returns true if this state is a Goal State, and false otherwise
    public boolean isGoalState();

    // displays a human-readable form of this state
    public void display();

    // returns a new state that exactly duplicates the current one
    public State duplicate();

    // applies the given action to the current state
    public void performAction(Action action);

    // rolls back the given action from the current state
    // commented out for now, but documented in case we need it later
    // public void undoAction(Action action);

    // returns true if the otherState is the same as this one
    //   it is safe to cast otherState to your state class
    public boolean equals(Object otherState);

    public int heuristic();
}


    