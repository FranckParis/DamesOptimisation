package Manager;

import object.Solution;

import java.util.ArrayList;
import java.util.Collection;
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

    public double randomSolutionAverage(int n){
        double somme=0;
        double nbSolutions = 10;
        for (int i=0; i<nbSolutions; i++){
            Solution solution = new Solution(n);
            somme+=solution.getNbConflicts();
        }
        return somme/nbSolutions;
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
        double proba = 0.8;
        double t = -randomSolutionAverage(n)/Math.log(proba);
        double mu = 0.9;
        double n1 = (Math.log(-randomSolutionAverage(n)/t*Math.log(proba)))/Math.log(mu);

        for (int indBoucle1 = 0; indBoucle1 < Math.ceil(n1); indBoucle1++){
            for(int i=0;i<Math.ceil(n1*n1);i++){
                Solution otherSolution = this.getOtherSolution(xi); // On prend une solution au hasard
                int delta = otherSolution.getNbConflicts() - xi.getNbConflicts();
                if(delta >= 0 ){
                    xi = otherSolution;
                    int fx = otherSolution.getNbConflicts();
                    if(fx < fmin){
                        fmin = fx;
                        xmin = new Solution(otherSolution.getState());
                        if(fmin == 0){
                            return xmin;
                        }
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
            t *= mu;
        }
        return xmin;
    }

    public Solution tabou(int n){
        Solution xi = new Solution(n);
        Solution xmin = xi;
        int fmin = xmin.getNbConflicts();
        int fi = fmin;
        Solution xVoisin;
        int fVoisin;
        int yVoisin;
        List<Solution> voisins = new ArrayList<Solution>();
        List<Integer> forbiddenMoves = new ArrayList<Integer>();
        int i= 0;
        int nbIterations = 500;
        do{
            voisins = this.getTabouVoisins(xi, forbiddenMoves);
            if(!voisins.isEmpty()){
                xVoisin = this.getBestSolution(voisins);
                fVoisin = xVoisin.getNbConflicts();
                if(fVoisin >= fi){
                    forbiddenMoves.addAll(this.getPreviousMove(xVoisin, xi));
                }
                if(fVoisin < fmin){
                    xmin = new Solution(xVoisin.getState());
                    fmin = fVoisin;
                }
            }
            i++;
        }while (!voisins.isEmpty() && i<nbIterations);
        return xmin;
    }

    private List<Solution> getTabouVoisins(Solution solution, List<Integer> forbiddenMoves) {
        ArrayList<Solution> listSolutions = new ArrayList<Solution>();
        for(int i=0;i<solution.getSize()-1;i++){
            for(int j=i+1;j<solution.getSize();j++){
                for(int forbidden = 0; forbidden<forbiddenMoves.size();forbidden++){
                    if (forbiddenMoves.get(forbidden) == i){
                        if(i%2 == 0){
                            if(forbiddenMoves.get(forbidden + 1) == j){
                                continue;
                            }
                        }else{
                            if(forbiddenMoves.get(forbidden - 1) == j){
                                continue;
                            }
                        }
                    }
                }
                listSolutions.add(new Solution(solution.change(solution.getState(),i,j)));
            }
        }
        return listSolutions;
    }

    private ArrayList<Integer> indexOfAll(Object obj, ArrayList list){
        ArrayList<Integer> indexList = new ArrayList<Integer>();
        for (int i = 0; i < list.size(); i++)
            if(obj.equals(list.get(i)))
                indexList.add(i);
        return indexList;
    }

    private ArrayList<Integer> getPreviousMove(Solution xVoisin, Solution xi) {
        ArrayList<Integer> move = new ArrayList<>();
        for(int i=0; i<xVoisin.getSize();i++){
            if(xVoisin.getIState(i) != xi.getIState(i)){
                move.add(i);
            }
        }
        return move;
    }

    private Solution getBestSolution(List<Solution> voisins) {
        Solution sBest = null;
        int fBest = Integer.MAX_VALUE;
        int i=0;
        int f;
        for (Solution solution : voisins){
            f= solution.getNbConflicts();
            if(f<fBest){
                fBest = f;
                sBest = solution;
            }
            i++;
        }
        return sBest;
    }

}
