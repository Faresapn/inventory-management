package com.faresa.inventaris_ontrucks.pojo.divisi.update;

import com.google.gson.annotations.SerializedName;


public class DataUpdate {

	@SerializedName("divisi_id")
	private int divisiId;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("name")
	private String name;

	@SerializedName("created_at")
	private String createdAt;

	public void setDivisiId(int divisiId){
		this.divisiId = divisiId;
	}

	public int getDivisiId(){
		return divisiId;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
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
			"divisi_id = '" + divisiId + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",name = '" + name + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			"}";
		}
}