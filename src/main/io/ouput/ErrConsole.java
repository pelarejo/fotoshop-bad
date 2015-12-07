package main.io.ouput;

public class ErrConsole extends Output {
    @Override
    public void update(String txt) {
        System.out.print("(x) " + txt);
    }
}
