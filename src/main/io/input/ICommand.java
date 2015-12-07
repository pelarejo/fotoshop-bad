package main.io.input;

import main.command.CommandFactory;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Workbench input waits for commands to proceed to its execution
 */
public class ICommand extends Input {
    private LinkedBlockingQueue<CommandFactory.Command> cmdQueue;

    public ICommand() {
        cmdQueue = new LinkedBlockingQueue<>();
    }

    /**
     * Add a command to the queue
     *
     * @param input Command to add to the queue
     * @return true if the queue was added. False otherwise.
     */
    public boolean setCommand(CommandFactory.Command input) {
        return cmdQueue.offer(input);
    }

    @Override
    public ParsedInput getCommand() {
        try {
            CommandFactory.Command cmd = cmdQueue.take();
            return new ParsedInput(PARSED_STATE.VALID, cmd.getTag(), cmd);
        } catch (InterruptedException e) {
            // TODO: Should log exception
            return new ParsedInput(PARSED_STATE.IGNORE);
        }
    }
}
