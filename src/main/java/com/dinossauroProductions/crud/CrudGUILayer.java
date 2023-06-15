package com.dinossauroProductions.crud;

import com.dinossauroProductions.crud.manager.FileManager;
import com.dinossauroProductions.crud.manager.User;
import com.dinossauroProductions.crud.manager.CrudMain;
import com.dinossauroProductions.crud.manager.UsersIO;
import com.dinossauroProductions.renderer.ImGuiLayer;
import imgui.ImGui;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.*;

public class CrudGUILayer extends ImGuiLayer {

    private ImString nome = new ImString();
    private boolean sexo = false;
    private ImString endereco = new ImString();
    private ImString cargo = new ImString();
    private ImInt idade = new ImInt(0);
    private ImFloat salario = new ImFloat(0);
    private ImLong cpf = new ImLong(0);
    private ImString cpfString = new ImString();

    private boolean criarUsuario = true, exibirUsuarios = true;


    //TODO: criar um menu que configura as funções do crud como um todo
    //TODO: implementar as outras funções do CRUD: update e delete

    public CrudGUILayer(long windowPtr) {
        super(windowPtr);
    }

    @Override
    public void imguiImplementation() {



        //janela que gerencia a presença das outras janelas
        imguiMainOptionsWindow();

        //janela que permite criar novos usuários ao sistema
        if(criarUsuario){
            imguiCreateUserWindow();
        }
        //janela que permite exibir os usuários que estão no sistema
        if(exibirUsuarios){
            //imguiShowUsersWindow();
            imguiShowUsersSelectWindow();
        }

        //janela de testes do imgui que permite
        ImGui.showDemoWindow();

    }

    private void imguiMainOptionsWindow(){

        ImGui.begin("Main");


        if(ImGui.checkbox("Criar Usuarios: ", criarUsuario)){
            criarUsuario = !criarUsuario;
        }

        if(ImGui.checkbox("Exibir Usuarios: ", exibirUsuarios)){
            exibirUsuarios = !exibirUsuarios;
        }

        if(ImGui.button("Salvar usuários")){
            FileManager.saveUsersData(CrudMain.getUsers());
        }




        ImGui.end();

    }

    public User[] imguiShowUsersSelectWindow(){

        ImGui.begin("Selecionar Usuarios");

        // Iniciar a tabela
        ImGui.beginTable("Tabela de Usuários", 4);

        // Definir os cabeçalhos das colunas
        ImGui.tableSetupColumn("Nome");
        ImGui.tableSetupColumn("Idade");
        ImGui.tableSetupColumn("Sexo");
        ImGui.tableSetupColumn("Endereço");
        ImGui.tableHeadersRow();

        // Array para rastrear a seleção dos usuários

        // Exibir os usuários na tabela com funcionalidade de seleção
        for (int i = 0; i < CrudMain.getUsers().size(); i++) {
            ImGui.tableNextRow();
            ImGui.tableNextColumn();
            if (ImGui.selectable(CrudMain.getUsers().get(i).getNome(), CrudMain.getUsers().get(i).getSelected())) {
                CrudMain.getUsers().get(i).toggleSelect(); // Inverter o estado de seleção
                System.out.println("sexo");
            }
            User usuario = CrudMain.getUsers().get(i);
            ImGui.tableNextColumn();
            ImGui.text(String.valueOf(usuario.getIdade()));
            ImGui.tableNextColumn();
            ImGui.text(usuario.getGeneroStr());
            ImGui.tableNextColumn();
            ImGui.text(usuario.getEndereco());

            //System.out.print(CrudMain.getUsers().get(i).getSelected());
        }
        System.out.println();

        ImGui.endTable();

        //ImGui.checkbox("Selecionado", CrudMain.getUsers().get(0).getSelected());

        ImGui.end();

        return null;
    }

    private void imguiShowUsersWindow(){

        //Função exibe uma janela com a lista de todos os usuários atualmente carregados no sistema

        //Campos do usuário: nome, cpf, genero, idade, endereço, salario, cargo

        ImGui.begin("Lista de Usuários:", new ImBoolean(true), ImGuiWindowFlags.HorizontalScrollbar);

        ImGui.columns(7, "UserColumns", true);

        ImGui.text("Nome");
        ImGui.nextColumn();

        ImGui.text("CPF");
        ImGui.nextColumn();

        ImGui.text("Sexo");
        ImGui.nextColumn();

        ImGui.text("Idade");
        ImGui.nextColumn();

        ImGui.text("Endereço");
        ImGui.nextColumn();

        ImGui.text("Salario");
        ImGui.nextColumn();

        ImGui.text("Cargo");
        ImGui.nextColumn();

        ImGui.separator();



        for (User user : CrudMain.getUsers()) {
            //nome
            ImGui.text(user.getNome());
            ImGui.nextColumn();

            //cpf
            ImGui.text(String.valueOf((user.getCpf())));
            ImGui.nextColumn();

            //genero
            if(user.getGenero())
                ImGui.text("Masculino");
            else
                ImGui.text("Feminino");
            ImGui.nextColumn();

            //idade
            ImGui.text(String.valueOf((user.getIdade())));
            ImGui.nextColumn();

            //endereço
            ImGui.text(user.getEndereco());
            ImGui.nextColumn();

            //salario
            ImGui.text(user.getComponent(User.salario));
            ImGui.nextColumn();

            //cargo
            ImGui.text(user.getComponent(User.cargo));
            ImGui.nextColumn();
        }

        ImGui.columns(2);

        ImGui.end();
    }


    private void imguiCreateUserWindow(){



        ImGui.begin("Criar Usuário");

        // Campos de entrada de texto
        ImGui.inputText("Nome", nome);

        ImGui.text("Sexo:");
        ImGui.sameLine();
        if(ImGui.checkbox("Masculino", sexo))
            sexo = true;
        ImGui.sameLine();
        if(ImGui.checkbox("Feminino", !sexo))
            sexo = false;

        ImGui.inputText("Cargo", cargo);

        ImGui.inputInt("Idade", idade);

        ImGui.inputText("CPF", cpfString);

        ImGui.inputText("Endereço", endereco);

        ImGui.inputFloat("Salário", salario);

        // Botão para enviar os dados
        if (ImGui.button("Enviar")) {

            User usuario = new User();

            try {
                cpf = new ImLong(Long.parseLong(cpfString.get()));
                usuario.setCpf(cpf.get());
            } catch (NumberFormatException e) {
                System.out.println("CPF inválido!");
            }
            usuario.setIdentificador(cpf.get());
            usuario.setIdade(idade.get());
            usuario.setNome(nome.get());
            usuario.setGenero(sexo);
            usuario.setEndereco(endereco.get());
            usuario.setCargo(cargo.get());
            usuario.setSalario(salario.get());

            CrudMain.consultarUsuario(usuario);

            CrudMain.getUsers().add(usuario);

            //Após criar o usuário novo, salva os dados atuais
            FileManager.saveUsersData(CrudMain.getUsers());



        }

        ImGui.end();
    }


    public void imgui(){


        //setupDockspace();
        imguiImplementation();
        //ImGui.showDemoWindow();

        //ImGui.




    }

}
