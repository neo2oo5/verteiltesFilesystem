/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package substructure;

/**
 *
 * @author xoxoxo
 */
public class fileSystemException extends Exception {

    /**
     * Creates a new instance of <code>fileSystemException</code> without detail
     * message.
     */
    public fileSystemException() {
    }

    /**
     * Constructs an instance of <code>fileSystemException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public fileSystemException(String msg) {
        super(msg);
    }
}
