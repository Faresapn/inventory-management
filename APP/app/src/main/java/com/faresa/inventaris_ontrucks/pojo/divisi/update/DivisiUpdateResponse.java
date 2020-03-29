package com.faresa.inventaris_ontrucks.pojo.divisi.update;

import com.google.gson.annotations.SerializedName;

public class DivisiUpdateResponse{

	@SerializedName("status_code")
	private String statusCode;

	@SerializedName("data")
	private DataUpdate data;

	@SerializedName("message")
	private String message;

	public void setStatusCode(String statusCode){
		this.statusCode = statusCode;
	}

	public String getStatusCode(){
		return statusCode;
	}

	public void setData(DataUpdate data){
		this.data = data;
	}

	public DataUpdate getData(){
		return data;
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
			"DivisiUpdateResponse{" + 
			"status_code = '" + statusCode + '\'' + 
			",data = '" + data + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}