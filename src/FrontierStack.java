import java.util.Stack;

class FrontierStack implements Frontier{

    private Stack<SearchNode> stack;

    public FrontierStack(){
        stack = new Stack<>();
    }
    @Override
    public void clear() {
        stack = new Stack<>();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public void insert(SearchNode node) {
        stack.add(node);
    }

    @Override
    public SearchNode removeNext() {
        return stack.pop();
    }
}
