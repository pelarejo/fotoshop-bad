package Gui;


public class ConsoleController extends AGuiController {

    // TO REMOVE
    
    // Create a model for this that fetch depending of language
    private static final String SPLASH_MSG = "\n"
            + "Welcome to Fotoshop\n"
            + "Fotoshop is an amazing new, image editing tool.\n"
            + "Type 'help' if you need help\n"
            + "\n";
    
    //
    
    public ATextView mConsoleView;
    
    public ConsoleController() {
        mConsoleView = new ConsoleView();
        mConsoleView.updateView(SPLASH_MSG);
    }
    
    @Override
    public void updateView() {  
    }
  

}
