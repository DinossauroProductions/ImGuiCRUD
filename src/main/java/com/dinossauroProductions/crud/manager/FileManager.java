package com.dinossauroProductions.crud.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileManager {

    public static FileManager fileManager;
    private Gson gson;
    //private FileWriter writer;

    public FileManager() {

        //Gson gson = new GsonBuilder();

        //tenta criar o arquivo para salvar os dados


    }
    private static final String fileName = "save.json";
    private static final String pathBase = "src/main/resources/";      // /src/main/resources/
    public static final String path = pathBase + fileName;

    public static void saveUsersData(ArrayList<User> users) {

        //verifica se a lista passada é passível de ser processada

        if (CrudMain.listFailSafe(users)) {

            System.out.println("ERRO: na função saveUsersData() ao salvar uma lista nula de usuarios");
            return;

        }

        //salvar os valores em um arquivo formato json

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String jsonString = gson.toJson(users);

        try {
            FileWriter writer = new FileWriter(path);
            writer.write(jsonString);
            writer.close();

        } catch (IOException e) {
            System.out.println("ERRO: na função saveUsersData, falha ao procurar arquivo :" + path);
            e.printStackTrace();
        }

        //System.out.println(jsonString);

    }

    public static ArrayList<User> loadUserData() {

        ArrayList<User> users = new ArrayList<User>();

        String json = lerArquivo("src/main/resources/save.json");

        Gson gsonConverter = new Gson();

        User[] usersArr = gsonConverter.fromJson(json, User[].class);

        for(int i = 0; i < usersArr.length; i++){
            users.add(usersArr[i]);
        }

        return users;
    }

    public static String lerArquivo(String caminhoArquivo) {
        try {
            return new String(Files.readAllBytes(Paths.get(caminhoArquivo)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }




}
