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
public abstract class Node {
    private String name;
    private Folder parent;

    Node(String name, Folder parent)
    {
        this.name = name;
        this.parent = parent;
    }
    
    public String getName() {
        return name;
    }

    public Folder getParent() {
        return parent;
    }

    public abstract boolean isDirectory();
    
    public abstract boolean isFile();
    
 
}
