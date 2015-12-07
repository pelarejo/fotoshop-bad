package main.io;

import main.io.input.Input;
import main.io.ouput.Output;

/**
 * Designed to help access IOs.
 * TODO: Implement a level of output rather than more variables.
 * @param <I> Input
 * @param <O> Output
 * @param <E> Error Output
 * @param <A> Alert Output
 */
public class IoHelper<I extends Input, O extends Output, E extends Output, A extends Output> {

    public final I in;
    public final O out;
    public final E err;
    public final A alrt;

    public IoHelper(I in, O out, E err, A alrt) {
        this.in = in;
        this.out = out;
        this.err = err;
        this.alrt = alrt;
    }
}
