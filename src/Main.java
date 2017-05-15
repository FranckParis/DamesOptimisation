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
        int n = 10;
        SolutionManager sm = new SolutionManager();

        /*Solution s = sm.recuitSimulte(n);
        System.out.println(s);
        System.out.println(s.getNbConflicts());
        System.out.println(s.getState());*/

        /*Solution s = sm.tabou(n);
        System.out.println(s);
        System.out.println(s.getNbConflicts());
        System.out.println(s.getState());*/

        GeneticSolutionManager gsm = new GeneticSolutionManager();

        Solution s1 = new Solution(4);
        Solution s2 = new Solution(4);

        ArrayList<Solution> solutions = new ArrayList<>();
        solutions.add(s1);
        solutions.add(s2);

        gsm.geneticResolution(solutions, 0.7f, 0.8f);

    }
}
