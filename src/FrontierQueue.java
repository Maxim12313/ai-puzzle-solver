import java.util.LinkedList;
import java.util.Queue;

class FrontierQueue implements Frontier{

    private Queue<SearchNode> queue;
    public FrontierQueue(){
        queue = new LinkedList<>();
    }

    @Override
    public void clear() {
        queue = new LinkedList<>();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public void insert(SearchNode node) {
        queue.add(node);
    }

    @Override
    public SearchNode removeNext() {
        return queue.remove();
    }
}