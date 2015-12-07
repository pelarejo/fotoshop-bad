package main.io;

import main.io.input.Input;
import main.io.ouput.Dialog;
import main.io.ouput.StatusBar;

public class IoGui extends IoHelper<Input, StatusBar, Dialog, Dialog> {
    public IoGui() {
        super(null, new StatusBar(), new Dialog(), new Dialog());
    }
}
