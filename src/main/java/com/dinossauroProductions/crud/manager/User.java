package com.dinossauroProductions.crud.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

public class User {

	// tipos de valores possíveis que são aceitos no Component

	private long identificador;

	public static final String salario = "salario", cargo = "cargo";
	private HashMap<String, String> componentes;
	
	private String nome, endereco;
	private int idade;
	private boolean genero;
	private long cpf;

	private transient boolean selecionado = false;

	//nome, cpf, genero, idade, endereço, salario, cargo

	public User(HashMap<String, String> components) {

		this.componentes = components;

	}

	public User() {

		componentes = new HashMap<String, String>();
	}

	public void addComponent(String key, String comp) {
		componentes.put(key, comp);
	}

	public String getComponent(String key){
		return componentes.get(key) == null ? "" : componentes.get(key);
	}

	
	public void lerUsuario() {
		for (int i = 0; i < componentes.size(); i++) {
			//componentes.get(i).printarComponente();
			//TODO: printação
		}
	}

	public void setIdentificador(long identificador) {
		this.identificador = identificador;
	}

	public long getIdentificador() {
		return identificador;
	}
	
	public String getNome() {
		return nome;
	}

	public long getCpf() {
		return cpf;
	}

	public int getIdade() {
		return idade;
	}

	public boolean getGenero() {
		return genero;
	}

	public String getGeneroStr(){
		return genero ? "Masculino" : "Feminino";
	}

	public String getEndereco(){
		return endereco;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCpf(String cpf) {
		this.cpf = Long.parseLong(cpf);
		this.identificador = this.cpf;
	}

	public void setCpf(long cpf){
		this.cpf = cpf;
	}

	public void setIdade(String idade) {
		this.idade = Integer.parseInt(idade);
	}

	public void setIdade(int idade){
		this.idade = idade;
	}

	public void setGenero(String genero) {
		
		if(genero.toLowerCase().equals("masculino")) {
			this.genero = true;
		}
		else {
			this.genero = false;
		}
	}

	public void setGenero(boolean genero){
		this.genero = genero;

	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
		
	}

	public void setCargo(String cargo){
		if(!componentes.containsKey("cargo")){
			addComponent("cargo", cargo);
		}
	}

	public void setSalario(float salario){
		if(!componentes.containsKey("salario")){
			addComponent("salario", Float.toString(salario));
		}
	}


	public Object getHashValue(String nome) {
		return componentes.containsKey(nome);
	}
	
	public String getUserJson() {
		Gson gson = new GsonBuilder()
			      .setPrettyPrinting()
			      .create();

		return gson.toJson(this);
	}

	public void toggleSelect(){
		selecionado = !selecionado;
	}

	public boolean getSelected(){
		return selecionado;
	}

	public void setSelected(boolean select){
		selecionado = select;
	}
	

}
