package main.gui;

import main.locale.LocaleManager;

public class ConsoleController extends AGuiController {

    // TO REMOVE
    
    // Create a model for this that fetch depending of language
    private static final String SPLASH_MSG = LocaleManager.getInstance().getString("splash.screen");
    
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
