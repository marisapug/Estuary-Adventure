package model;

import java.io.Serializable;

public class PlayerStory implements Serializable {
	private String name;
	private String story;
	
	public PlayerStory(String n, String s){
		name = n;
		story = s;
	}
}
