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
        Algorithme algorithme = new Algorithme();
        algorithme.run();


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
