public enum RubixCubeAction implements Action{
    U(0,false,"U"),
    U_(0,true,"U_"),
    F(1,false,"F"),
    F_(1,true,"F_"),
    R(2,false,"R"),
    R_(2,true,"R_");


    public final int orientationUnaffected;
    public final boolean isPrime;
    private final String name;

    RubixCubeAction(int orientationUnaffected, boolean isPrime, String name){
        this.orientationUnaffected = orientationUnaffected;
        this.isPrime = isPrime;
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }

    @Override
    public void display() {
        System.out.println(name);
    }

    @Override
    public int getCost() {
        return 1;
    }
}
