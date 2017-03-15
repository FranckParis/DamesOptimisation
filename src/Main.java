import Manager.SolutionManager;
import object.Solution;

/**
 * Created by Quentin on 15/03/2017.
 */
public class Main {
    public static void main (String[] args) {
        int n = 6;
        SolutionManager sm = new SolutionManager();
        Solution s =sm.recuitSimulte(n);
        System.out.println(s);
    }
}
