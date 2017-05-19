package object;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Quentin on 15/03/2017.
 */
public class Solution {
    ArrayList<Integer> state;
    int size;

    public Solution(ArrayList<Integer> state){

        size = state.size();
        this.state = new ArrayList<>(state);
    }

    public Solution(int n) {
        size = n;
        state = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            state.add(new Integer(i));
        }
        Collections.shuffle(state);
    }

    public int getIState(int i){
        if (i<0 || i> state.size()){
            return 0;
        }
        return this.state.get(i);
    }
    public int getSize(){
        return size;
    }

    public int getNbConflicts(){
        int nbCollisions = 0;
        for (int indColonne=0;indColonne<size;indColonne++){// Pour chaque reine
            int indLigne = state.get(indColonne);
            int colonneDiagonale = indColonne;
            while (indLigne>=0 && colonneDiagonale<size-1){ //parcours diagonale 1
                colonneDiagonale++;
                indLigne --;
                if(state.get(colonneDiagonale) == indLigne || state.get(colonneDiagonale) == state.get(indColonne)){
                    nbCollisions++;
                }
            }
            colonneDiagonale = indColonne;
            indLigne = state.get(indColonne);
            while (indLigne< size-1 && colonneDiagonale<size-1){//parcours diagonale 2
                colonneDiagonale++;
                indLigne ++;
                if(state.get(colonneDiagonale) == indLigne){
                    nbCollisions++;
                }
            }
        }
        return nbCollisions;
    }

    public String toString(){

        StringBuilder stb = new StringBuilder();
        for (int i=0; i<state.size();i++) {
            int posQueen = state.get(i);
            stb.append('|');
            for(int j=0; j<state.size();j++){
                if (j==posQueen){
                    stb.append('Q');
                }
                else{
                    stb.append(' ');
                }
                stb.append('|');
            }
            stb.append("\n");
        }
        return stb.toString();
    }

    public ArrayList<Integer> getState(){
        return state;
    }

    public ArrayList<Integer> change(ArrayList<Integer> list, int a, int b){

        int buffer = list.get(a);
        list.set(a,list.get(b));
        list.set(b,buffer);

        return list;
    }

    public void setState(ArrayList<Integer> state) {
        this.state = state;
    }
}
