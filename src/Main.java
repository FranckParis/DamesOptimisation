import Manager.SolutionManager;
import object.Solution;

import java.util.ArrayList;

import static java.lang.System.currentTimeMillis;

/**
 * Created by Quentin on 15/03/2017.
 */
public class Main {
    public static void main (String[] args) {
        int n = 8;
        SolutionManager sm = new SolutionManager();

        /*Solution s = sm.recuitSimulte(n);
        System.out.println(s);
        System.out.println(s.getNbConflicts());
        System.out.println(s.getState());*/

        Solution s = sm.tabou(n);
        System.out.println(s);
        System.out.println(s.getNbConflicts());
        System.out.println(s.getState());

    }
}
