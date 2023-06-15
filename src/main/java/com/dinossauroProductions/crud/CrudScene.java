package com.dinossauroProductions.crud;

import com.dinossauroProductions.crud.manager.CrudMain;
import com.dinossauroProductions.crud.manager.FileManager;
import com.dinossauroProductions.crud.manager.User;
import com.dinossauroProductions.crud.manager.UsersIO;
import com.dinossauroProductions.renderer.Scene;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;

public class CrudScene extends Scene {

    public CrudGUILayer gui;



    public CrudScene(CrudGUILayer gui){

        //addTestUser();

        CrudMain.addUsuarios(FileManager.loadUserData());

        //System.out.println(CrudMain.getUsers());


        //UsersIO.createUserJSON(jsonStr);

        this.gui = gui;
    }

    @Override
    public void renderScene() {
        gui.imgui();
    }

    private void addTestUser(){

        User user = new User();

        user.setCpf("13123132389");
        user.setNome("Carlos");
        user.setIdade("25");
        user.setGenero("Masculino");
        user.setEndereco("Rua do cu");
        user.setCargo("Coisador");
        user.setSalario(1312);

        CrudMain.getUsers().add(user);
    }
}
