package main.io.ouput;


/**
 * Centralise System.out.print for easier further expansion
 */
public class Console extends Output {

    public void update(String txt) {
        System.out.print(txt);
    }

}
