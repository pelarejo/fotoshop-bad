package main.gui;


public class ConsoleView implements IView {

    @Override
    public void updateView(String text) {
        System.out.print(text);
    }
    
}
