package com.dinossauroProductions.crud.manager;

import java.util.ArrayList;
import java.util.Scanner;

public class CrudMain {

	private static final ArrayList<User> usuarios = new ArrayList<User>();



	public static Scanner in;

	private static FileManager fileManager;

	public static CrudMain crud;

	public static void main(String[] args) {

		// create , read , update, delete

		crud = new CrudMain();

	}

	public CrudMain() {

		//
		// SETUP
		//

		// criar o Scanner para leitura de inputs

		if (in == null)
			in = new Scanner(System.in);

		// criando a lista de usuarios

		fileManager = new FileManager();

		//usuarios = new ArrayList<User>();

		// TODO: Arrumar o sistema de save / load e carregar os dados do arquivo antes
		// de executar

		// pré-criando usuarios para teste

		testUsers();

		// inicializar o looping principal

		loop();

	}

	private void testUsers(){

		// usuario 1

		User user = new User();

		user.setCpf("13123132389");
		user.setNome("Carlos");
		user.setIdade("25");
		user.setGenero("Masculino");
		user.setEndereco("Rua do cu");

		usuarios.add(user);

		// usuario 2

		// usuarios.add(EntradaDados.criarUsuarioInput());
	}

	public void loop() {
		// main loop

		String entrada;

		// do while que gerencia o funcionamento do programa e interage com o usuario

		do {

			entrada = "";

			System.out.println("O que você deseja fazer?\n\n" + "C = Criar usuario novo\n"
					+ "R = Ler um usuario existente\n" + "U = Atualizar um usuario existente\n"
					+ "D = Deletar um usuario existente\n" + "Q = Salvar e sair do programa\n");

			entrada = in.nextLine();

			entrada = validarInput1Char(entrada);

			// System.out.println(entrada);

			switch (entrada.charAt(0)) {

			case 'C':

				// Create / Criar

				usuarios.add(UsersIO.criarUsuarioConsole());

				break;
			case 'R':

				// Read / Ler

				exibirUsuariosConsole();

				break;
			case 'U':

				// Update / Atualizar

				break;
			case 'D':

				// Delete / Apagar

				break;
			case 'Q':

				// Sair do programa

				fileManager.saveUsersData(usuarios);

				System.out.println("Obrigado e até mais!");

				break;

			}
		} while (!(entrada.charAt(0) == 'Q'));

	}

	private String validarInput1Char(String entrada) {

		entrada = entrada.trim();
		entrada = entrada.toUpperCase();

		if (entrada == null || entrada.length() != 1) {

			// Input não legível

			System.out.println(
					"A entrada dada foi invalida, por favor insira apenas um dígito de acordo com a ação desejada.");
		}
		return entrada;
	}

	public static void consultarUsuario(User user) {

		System.out.println(user.getUserJson());

		// System.out.println("O usuario de nome " + user.getNome() + " e salario "+
		// user.getHashValue("salario") + " esta matriculado no sistema.\n");

	}

	private void consultarUsuarioIdentificador(long identificador) {

		// Consultar um usuario da lista com base no identificador interno (CPF) e
		// Exibir suas informações na tela

		User user = getUserFromIdentifier(identificador);
		consultarUsuario(user);

	}

	private void consultarUsuarioNome(String nome) {

		// Consultar um usuario da lista com base em nome e Exibir suas informações na
		// tela

		User usr = getUserFromName(nome);

		if (usr == null) {
			return;
		}

		consultarUsuarioIdentificador(usr.getIdentificador());

	}

	public User getUserFromIdentifier(long identificador) {

		// Consulta um usuario da lista com base no identificador interno e o retorna

		if (listFailSafe(usuarios)) {
			System.out.println(
					"ERRO: A lista de usuarios " + usuarios + " possui tamanho invalido (" + usuarios.size() + ").");
			return null;
		}

		for (User usuario : usuarios) {
			if (usuario.getCpf() == identificador) {
				return usuario;
			}
		}

		System.out.println("ERRO: O usuario de identificador " + identificador + " não foi encontrado.");
		return null;

	}

	public User getUserFromName(String nome) {

		// Consulta um usuario da lista com base no nome e o retorna

		if (listFailSafe(usuarios)) {
			System.out.println(
					"ERRO: A lista de usuarios " + usuarios + " possui tamanho invalido (" + usuarios.size() + ").");
			return null;
		}

		for (User usuario : usuarios) {
			if (usuario.getNome().equals(nome)) {

				// O usuario de index 'i' é o de mesmo nome do desejado.

				return usuario;
			}
		}

		System.out.println("ERRO: Na função getUserFromName(), pois o nome passado não corresponde a nenhum usuario.");

		return null;
	}

	public static ArrayList<User> getUsers() {
		return usuarios;
	}

	public boolean hasRepeatedName(String name) {

		for (User usuario : usuarios) {
			Object nome = usuario.getNome();

			if (nome.equals(name)) {
				System.out.println(
						"O usuario de nome \"" + nome + "\" possui o mesmo nome do usuario de nome \"" + name + ".");
				return true;
			}

		}
		return false;
	}

	public boolean hasRepeatedCPF(int cpf) {

		for (User usuario : usuarios) {

			if ((usuario.getCpf()) == cpf) {
				return true;
			}

		}
		return false;
	}

	public static boolean listFailSafe(ArrayList<?> arr) {
		return arr.size() == 0;
	}

	private void exibirUsuariosConsole() {

		// lê a lista de usuarios e a exibe na tela.

		// verifica se a lista é valida

		if (listFailSafe(usuarios)) {
			System.out.println("\nERRO: Não foi possível ler a lista de usuarios, pois a mesma é nula.");
			return;
		}

		// caso a lista tenha pelo menos 1 usuario, segue com a execução:

		else {

			// recebe e valida o input do usuario no contexto

			System.out.println("Você deseja ver um usuario ou toda a lista?\n\n" + "U = Usuario único\n"
					+ "T = Todos os usuarios\n");

			String entrada = in.nextLine();
			entrada = validarInput1Char(entrada);

			// com o input do usuario preparado, segue em frente

			switch (entrada.charAt(0)) {

			case 'U':

				// Um único usuario para ser consultado, identifica-lo

				System.out.println("\nDigite o nome do usuario:");
				entrada = in.nextLine();

				consultarUsuarioNome(entrada);

				break;
			case 'T':

				// Todos os usuarios a serem consultados

				for (User usuario : usuarios) {

					consultarUsuario(usuario);

				}

				break;
			}

		}
	}

	public static void addUsuarios(ArrayList<User> usuariosEntrada){
		usuarios.addAll(usuariosEntrada);
	}

}
