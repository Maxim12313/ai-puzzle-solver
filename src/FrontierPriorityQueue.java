import java.util.LinkedList;
import java.util.PriorityQueue;

public class FrontierPriorityQueue implements Frontier{
    private PriorityQueue<SearchNode> priorityQueue;

    public FrontierPriorityQueue(){
        priorityQueue = new PriorityQueue<>();
    }

    @Override
    public void clear() {
        priorityQueue = new PriorityQueue<>();
    }

    @Override
    public boolean isEmpty() {
        return priorityQueue.isEmpty();
    }

    @Override
    public void insert(SearchNode node) {
        priorityQueue.add(node);
    }

    @Override
    public SearchNode removeNext() {
        return priorityQueue.poll();
    }
}
