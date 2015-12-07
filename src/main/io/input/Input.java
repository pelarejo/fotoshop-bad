package main.io.input;

import main.command.CommandFactory;

public abstract class Input {
    public enum PARSED_STATE {
        VALID,
        INVALID,
        INVALID_ARG,
        IGNORE
    }


    public class ParsedInput {
        public PARSED_STATE eState;
        public CommandFactory.Command cmd;
        public String what;

        protected ParsedInput(PARSED_STATE eState) {
            this.eState = eState;
        }

        protected ParsedInput(PARSED_STATE eState, String what) {
            this.eState = eState;
            this.what = what;
        }

        protected ParsedInput(PARSED_STATE eState, String what, CommandFactory.Command cmd) {
            this.eState = eState;
            this.cmd = cmd;
            this.what = what;
        }
    }

    public abstract ParsedInput getCommand();
}
