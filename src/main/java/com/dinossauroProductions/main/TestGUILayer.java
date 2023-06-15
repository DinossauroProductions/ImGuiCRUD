package com.dinossauroProductions.main;

import com.dinossauroProductions.renderer.ImGuiLayer;
import imgui.ImGui;

public class TestGUILayer extends ImGuiLayer {

    private boolean showText;

    public TestGUILayer(long window) {
        super(window);
    }

    @Override
    public void imguiImplementation() {

        ImGui.begin("Window");

        if(ImGui.button("I am a button", 200, 20)){
            showText = true;
        }

        if(showText){
            ImGui.text("You clicked in the button hahdada");
            ImGui.sameLine();
            if(ImGui.button("Stop showing text")){
                showText = false;
            }
        }

        ImGui.end();

    }

    @Override
    public void imgui(){
        //ImGui.begin(name);

        //setupDockspace();
        imguiImplementation();
        //ImGui.showDemoWindow();

        //ImGui.
        //ImGui.end();



    }
}
