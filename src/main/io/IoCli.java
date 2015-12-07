package main.io;

import main.io.input.Parser;
import main.io.ouput.Console;
import main.io.ouput.ErrConsole;

public class IoCli extends IoHelper<Parser, Console, ErrConsole, Console> {
    public IoCli() {
        super(new Parser("> "), new Console(), new ErrConsole(), new Console());
    }
}
