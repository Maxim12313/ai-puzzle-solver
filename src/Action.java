interface Action
{
    // displays a human-readable form of this action
    public void display();

    // returns the cost of taking this action (often 1)
    public int getCost();
}