package com.faresa.inventaris_ontrucks.pojo.divisi.delete;

import com.google.gson.annotations.SerializedName;


public class DivisiDeleteResponse{

	@SerializedName("status_code")
	private String statusCode;

	@SerializedName("message")
	private String message;

	public void setStatusCode(String statusCode){
		this.statusCode = statusCode;
	}

	public String getStatusCode(){
		return statusCode;
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
			"DivisiDeleteResponse{" + 
			"status_code = '" + statusCode + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}