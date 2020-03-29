package com.faresa.inventaris_ontrucks.pojo.divisi.create;

import com.google.gson.annotations.SerializedName;

public class DivisiCreateResponse{

	@SerializedName("status_code")
	private String statusCode;

	@SerializedName("divisiCreate")
	private DataCreate divisiCreate;

	@SerializedName("message")
	private String message;

	public void setStatusCode(String statusCode){
		this.statusCode = statusCode;
	}

	public String getStatusCode(){
		return statusCode;
	}

	public void setDivisiCreate(DataCreate divisiCreate){
		this.divisiCreate = divisiCreate;
	}

	public DataCreate getDivisiCreate(){
		return divisiCreate;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"DivisiCreateResponse{" + 
			"status_code = '" + statusCode + '\'' + 
			",divisiCreate = '" + divisiCreate + '\'' +
			",message = '" + message + '\'' + 
			"}";
		}
}