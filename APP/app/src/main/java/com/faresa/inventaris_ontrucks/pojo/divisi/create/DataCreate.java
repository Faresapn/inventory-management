package com.faresa.inventaris_ontrucks.pojo.divisi.create;

import com.google.gson.annotations.SerializedName;

public class DataCreate {

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("divisi_id")
	private int divisiId;

	@SerializedName("name")
	private String name;

	@SerializedName("created_at")
	private String createdAt;

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setDivisiId(int divisiId){
		this.divisiId = divisiId;
	}

	public int getDivisiId(){
		return divisiId;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	@Override
 	public String toString(){
		return 
			"DataCreate{" +
			"updated_at = '" + updatedAt + '\'' + 
			",divisi_id = '" + divisiId + '\'' + 
			",name = '" + name + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			"}";
		}
}