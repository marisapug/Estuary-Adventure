package model;

import java.util.List;

class Shore {
  List<Barrier> shore;
  
 void addBarr(Barrier b){
  shore.add(b);
 }
 
 void remBarr(Barrier b){ //update to make sure every "dead" barrier is being removed from the list
  shore.remove(b);
 }
 
 }
