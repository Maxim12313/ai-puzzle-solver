public interface Search {
    public Solution search(State startState);
    String toString();  // returns name of search algorithm, like "BFS", "DFS", etc.
}
