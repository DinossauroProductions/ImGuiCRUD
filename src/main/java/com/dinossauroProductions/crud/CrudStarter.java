package com.dinossauroProductions.crud;

import com.dinossauroProductions.renderer.Window;

public class CrudStarter {

    public static Window window;

    public static void main(String[] args){
        window = new Window();
        window.setScene(new CrudScene(new CrudGUILayer(window.getId())));
        window.init();
        window.run();
        window.destroy();
    }

}
