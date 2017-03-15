package object;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Quentin on 15/03/2017.
 */
public class Solution {
    ArrayList<Integer> state;
    int size;

    public Solution(ArrayList<Integer> state){
        size = state.size();
    }

    public Solution(int n){
        size = n;
        state = new ArrayList<Integer>();
        for (int i=0; i<n; i++) {
            state.add(new Integer(i));
        }
        Collections.shuffle(state);
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
                if(state.get(colonneDiagonale) == indLigne){
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
                    stb.append('R');
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
}
