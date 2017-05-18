import Manager.GeneticSolutionManager;
import Manager.SolutionManager;
import object.Solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static java.lang.System.currentTimeMillis;

/**
 * Created by Quentin on 15/03/2017.
 */
public class Main {
    public static void main (String[] args) {
        int n = 100;
        int pop = 1000;

        SolutionManager sm = new SolutionManager();
        GeneticSolutionManager gsm = new GeneticSolutionManager(n);

        /*Solution s = sm.recuitSimulte(n);
        System.out.println(s);
        System.out.println(s.getNbConflicts());
        System.out.println(s.getState());*/

        /*Solution s = sm.tabou(n);
        System.out.println(s);
        System.out.println(s.getNbConflicts());
        System.out.println(s.getState());*/


        ArrayList<Solution> solutions = new ArrayList<>();
        for(int i=0; i<pop; i++){
            solutions.add(new Solution(n));
        }

        System.out.println("Generating solutions");
        for (Solution sol : solutions) {
            System.out.println("Solution | NbConflicts : "+ sol.getNbConflicts()+ " | Fitness : " + (n*(n-1)-sol.getNbConflicts()));
        }
        System.out.println("Starting resolution. Please wait.");

        Solution bestSol = gsm.geneticResolution(solutions, 0.1f, 0.15f, 2, pop);
        System.out.println("\n Best Solution : "+ bestSol.getState() + " | NbConflicts : "+ bestSol.getNbConflicts()+" | Fitness : " + (n*(n-1)-bestSol.getNbConflicts()));


        // tabou
//      int nbConflits;
//      int nbSimulations = 20;
//
//        for(int allowed=1; allowed<20;allowed++){
//            nbConflits=0;
//            long startTime, totalTime = 0;
//            for(int i=0; i<nbSimulations; i++){
//                startTime = System.currentTimeMillis();
//                Solution s = sm.tabou(n,allowed);
//                //System.out.println(s);
//                nbConflits += s.getNbConflicts();
//                //System.out.println(s.getNbConflicts());
//                //System.out.println(s.getState());
//                totalTime += System.currentTimeMillis()-startTime;
//            }
//            double averageConclifcts = (double)nbConflits / (double)nbSimulations;
//            System.out.println("Size:"+allowed+" average total time:" + totalTime/nbSimulations + " with an average score of " + averageConclifcts +"/"+nbConflits );
//        }
    }
}
