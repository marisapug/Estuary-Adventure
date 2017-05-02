package model;

import java.util.ArrayList;

public class Shore {
	ArrayList<Grass> grassList = new ArrayList<Grass>();
	ArrayList<Seawall> wallList = new ArrayList<Seawall>();
	ArrayList<OysterGabion> gabionList = new ArrayList<OysterGabion>();

	void remGrass(Grass g){ //update to make sure every "dead" barrier is being removed from the list
		grassList.remove(g);
	}
	
	void remWall(Seawall g){ //update to make sure every "dead" barrier is being removed from the list
		grassList.remove(g);
	}
	
	void remOyster(OysterGabion g){ //update to make sure every "dead" barrier is being removed from the list
		grassList.remove(g);
	}

	public ArrayList<Grass> getGrass() {
		return grassList;
	}
	
	public ArrayList<Seawall> getSeawall() {
		return wallList;
	}
	
	public ArrayList<OysterGabion> getGab() {
		return gabionList;
	}
	
	public void addGrass(int x, int y){
		Grass g1 = new Grass(x,y);
		grassList.add(g1);
	}
	
	public void addWall(int x, int y){
		Seawall s1 = new Seawall(x,y);
		wallList.add(s1);
	}

}
