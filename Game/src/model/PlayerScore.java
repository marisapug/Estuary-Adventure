package model;

import java.io.Serializable;

public class PlayerScore implements Serializable{
	private String name;
	private double score;
	
	public PlayerScore(String n, double s){
		name = n;
		score = s;
	}
	
	//GETTERS
	public String getName(){
		return name;
	}
	public double getScore(){
		return score;
	}
	
	//SETTERS
	public void setName(String n){
		name = n;
	}
	public void setScore(double s){
		score = s;
	}
	

	
}
