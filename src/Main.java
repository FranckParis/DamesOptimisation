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
//        long startTime = System.currentTimeMillis();
//        Algorithme algorithme = new Algorithme();
//        algorithme.run();
//        System.out.println("Cette solution a été trouvé en "+ (System.currentTimeMillis()-startTime) +"ms");


        // tabou
        SolutionManager sm = new SolutionManager();
      int nbConflits;
      int nbSimulations = 1;
      int n = 100;

        for(int allowed=1; allowed<20;allowed++){
            nbConflits=0;
            long startTime, totalTime = 0;
            for(int i=0; i<nbSimulations; i++){
                startTime = System.currentTimeMillis();
                Solution s = sm.recuitSimulte(n);
                //Solution s = new Solution(n);
                //System.out.println(s);
                nbConflits += s.getNbConflicts();
                //System.out.println(s.getNbConflicts());
                //System.out.println(s.getState());
                totalTime += System.currentTimeMillis()-startTime;
            }
            double averageConclifcts = (double)nbConflits / (double)nbSimulations;
            System.out.println("Size:"+allowed+" average total time:" + totalTime/nbSimulations + " with an average score of " + averageConclifcts +"/"+nbConflits );
        }

//        SolutionManager sm = new SolutionManager();
//        int n = 20;
//        GeneticSolutionManager geneticSolutionManager = new GeneticSolutionManager(n);
//      int nbConflits;
//      int nbSimulations = 2;
//
//        for(int popSize = 1; popSize< 10;popSize++) {
//            for (int crossingProba = 1; crossingProba < 10; crossingProba++) {
//                nbConflits = 0;
//                long startTime, totalTime = 0;
//                for (int i = 0; i < nbSimulations; i++) {
//                    startTime = System.currentTimeMillis();
//                    Solution s = geneticSolutionManager.callGeneticResolution((float)crossingProba/10, (float)0.01,2,100*popSize);
//                    //Solution s = new Solution(n);
//                    //System.out.println(s);
//                    nbConflits += s.getNbConflicts();
//                    //System.out.println(s.getNbConflicts());
//                    //System.out.println(s.getState());
//                    totalTime += System.currentTimeMillis() - startTime;
//                }
//                double averageConclifcts = (double) nbConflits / (double) nbSimulations;
//                System.out.println("popSize/crossing:" + popSize + "/"+ crossingProba+ " average total time:" + totalTime / nbSimulations + " with an average score of " + averageConclifcts + "/" + nbConflits);
//            }
//        }
    }
}
