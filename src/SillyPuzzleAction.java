public class SillyPuzzleAction implements Action {

    final int delta;

    public SillyPuzzleAction(int delta) {
        this.delta = delta;
    }

    @Override
    public void display() {
        System.out.println("change by " + delta);
    }

    @Override
    public int getCost() {
        return 1;
    }

}