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
public class File extends Node {

    public File(String name, Folder parent) {
        super(name, parent);
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public boolean isFile() {
        return true;
    }
    
}
