package com.dinossauroProductions.main;

import com.dinossauroProductions.renderer.Window;

public class Starter {

    public static Window window;

    public static void main(String[] args){
        window = new Window();
        window.setScene(new TestScene(new TestGUILayer(window.getId())));
        window.init();
        window.run();
        window.destroy();
    }

}
