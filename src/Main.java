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
        int n = 5;
        SolutionManager sm = new SolutionManager();

        /*Solution s = sm.recuitSimulte(n);
        System.out.println(s);
        System.out.println(s.getNbConflicts());
        System.out.println(s.getState());*/

        /*Solution s = sm.tabou(n);
        System.out.println(s);
        System.out.println(s.getNbConflicts());
        System.out.println(s.getState());*/

        GeneticSolutionManager gsm = new GeneticSolutionManager(n);

        Solution s1 = new Solution(n);
        Solution s2 = new Solution(n);
        Solution s3 = new Solution(n);
        Solution s4 = new Solution(n);
        Solution s5 = new Solution(n);
        Solution s6 = new Solution(n);
        Solution s7 = new Solution(n);
        Solution s8 = new Solution(n);
        Solution s9 = new Solution(n);
        Solution s10 = new Solution(n);

        ArrayList<Solution> solutions = new ArrayList<>();
        solutions.add(s1);
        solutions.add(s2);
        solutions.add(s3);
        solutions.add(s4);
        solutions.add(s5);
        solutions.add(s6);
        solutions.add(s7);
        solutions.add(s8);
        solutions.add(s9);
        solutions.add(s10);

        for (Solution sol : solutions) {
            System.out.println("Solution : " + sol.getState() + " | NbConflicts : "+ sol.getNbConflicts()+ " | Fitness : " + (n*(n-1)-sol.getNbConflicts()));
        }

        Solution bestSol = gsm.geneticResolution(solutions, 0.7f, 0.8f, 2, 10);
        System.out.println("\n Best Solution : "+ bestSol.getState() + " | Fitness : " + (n*(n-1)-bestSol.getNbConflicts()));
        //gsm.reproduction(solutions,2, 10);
    }
}
