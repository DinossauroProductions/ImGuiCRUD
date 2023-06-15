package com.dinossauroProductions.crud.manager;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UsersIO {
	
	public static User criarUsuarioConsole() {
		
		User user = new User();
		
		String value;

		value = askFieldInput("nome");
		user.setNome(value);
		
		value = askFieldInput("cpf");
		user.setCpf(value);
		
		value = askFieldInput("idade");
		user.setIdade(value);
		
		value = askFieldInput("genero");
		user.setGenero(value);
		
		value = askFieldInput("endereco");
		user.setEndereco(value);
		
		
		//valores extras de hashmap
		
		value = askFieldInput("cargo");
		user.addComponent("cargo", value);
		
		value = askFieldInput("salario");
		user.addComponent("salario", value);
		
		
		
		System.out.println("\nUsuario criado com sucesso!\n");
		
		return user;

	}



	private static String askFieldInput(String field) {
		
		//Pedir ao usuario que entre com um valor de Field
		
		System.out.println("Insira o campo "+field+": ");

		//Caso seja um String, não entrara nos ifs, e retornara normalmente.
		
		return CrudMain.in.nextLine();
	}

}
