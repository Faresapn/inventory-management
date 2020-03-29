package com.faresa.inventaris_ontrucks.pojo;

import com.google.gson.annotations.SerializedName;

public class TokenResponse {

	@SerializedName("token")
	private String token;

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}

	@Override
 	public String toString(){
		return 
			"TokenResponse{" +
			"token = '" + token + '\'' + 
			"}";
		}
}