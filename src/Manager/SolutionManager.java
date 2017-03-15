package Manager;

import object.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Quentin on 15/03/2017.
 */
public class SolutionManager {

    public List<Solution> getVoisins(Solution solution){

        ArrayList<Solution> listSolutions = new ArrayList<Solution>();
        for(int i=0;i<solution.getSize()-1;i++){
            for(int j=i+1;j<solution.getSize();j++){
                listSolutions.add(new Solution(solution.change(solution.getState(),i,j)));
            }
        }
        return listSolutions;
    }

    public Solution getOtherSolution(Solution solution){
        Random r = new Random();
        int random =r.nextInt(solution.getSize());
        int random2= random;
        while (random2 == random){
            random2 = r.nextInt(solution.getSize());
        }
        Solution otherSolution = new Solution(solution.change(solution.getState(),random,random2));
        return otherSolution;
    }

    public Solution recuitSimulte(int n){
        //intitialize
        Solution xi = new Solution(n);
        Solution xmin = xi;
        int fmin = xmin.getNbConflicts();
        int fcourant =fmin;
        //Initialize
        double t = 2000;
        while(t>56){
            for(int i=0;i<50;i++){
                Solution otherSolution = this.getOtherSolution(xmin); // On prend une solution au hasard
                int delta = otherSolution.getNbConflicts() - fmin;
                if(delta <= 0 ){
                    xi = otherSolution;
                    int fx = otherSolution.getNbConflicts();
                    if(fx < fmin){
                        fmin = fx;
                        xmin = otherSolution;
                    }
                }
                else{
                    Random random = new Random();
                    double p = random.nextDouble();
                    if( p<= Math.exp(-delta/t)){
                        xi = otherSolution;
                    }
                }
            }
            t *= 0.7;
        }
        return xmin;
    }


}
