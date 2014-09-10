/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fileSystem;

/**
 *
 * @author Kevin Bonner <kevin.bonner@gmx.de>
 */
public class Folder extends Node {

    public Folder(String name, Folder parent) {
        super(name, parent);
    }
    
    @Override
    public boolean isDirectory() {
        return true;
    }

    @Override
    public boolean isFile() {
        return false;
    }
}
