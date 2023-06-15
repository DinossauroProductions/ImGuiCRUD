package com.dinossauroProductions.main;

import com.dinossauroProductions.renderer.Scene;

public class TestScene extends Scene {

    // A cena pode armazenar mais de um gui, bem como outras coisas.
    // É o que será passado para a classe Window, ao invés de um ImGuiLayer diretamente.

    public TestGUILayer gui;

    public TestScene(TestGUILayer gui){
        this.gui = gui;
    }

    @Override
    public void renderScene() {
        gui.imgui();
    }
}
