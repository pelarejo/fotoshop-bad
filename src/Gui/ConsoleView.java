package Gui;


public class ConsoleView extends ATextView {

    @Override
    public void updateView(String text) {
        System.out.print(text);
    }
    
}
