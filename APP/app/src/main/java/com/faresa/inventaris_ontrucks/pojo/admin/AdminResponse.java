package com.faresa.inventaris_ontrucks.pojo.admin;

import com.google.gson.annotations.SerializedName;

public class AdminResponse {

	@SerializedName("user")
	private User user;

	public void setUser(User user){
		this.user = user;
	}

	public User getUser(){
		return user;
	}

	@Override
 	public String toString(){
		return
                "AdminResponse{" +
			"user = '" + user + '\'' + 
			"}";
		}
}