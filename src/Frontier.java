interface Frontier
{
    // removes all elements from the Frontier
    public void clear();

    // returns true if the Frontier has no elements
    public boolean isEmpty();

    // adds a new node to the Frontier
    public void insert(SearchNode node);

    // removes and returns the next node in the Frontier
    public SearchNode removeNext();
}