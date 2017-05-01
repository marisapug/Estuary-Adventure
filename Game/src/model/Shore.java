package model;

import java.util.ArrayList;

public class Shore {
	ArrayList<Barrier> shore;

	public void addBarr(Barrier b){
		shore.add(b);
	}

	void remBarr(Barrier b){ //update to make sure every "dead" barrier is being removed from the list
		shore.remove(b);
	}

	public ArrayList<Barrier> getShore() {
		return shore;
	}

}
