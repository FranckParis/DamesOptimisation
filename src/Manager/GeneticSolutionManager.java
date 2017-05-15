package Manager;

import object.Solution;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by franck on 5/15/17.
 */
public class GeneticSolutionManager {

    public SolutionManager solutionManager;
    public Random random;

    public GeneticSolutionManager(){
        this.solutionManager = new SolutionManager();
        this.random = new Random();
    }

    public ArrayList<Solution> crossing (ArrayList<Solution> inputSolutions, float crossingProba){

        //Variables
        ArrayList<Solution> outputSolutions = new ArrayList<>();

        ArrayList<Integer> geneSolution11 = new ArrayList();
        ArrayList<Integer> geneSolution12 = new ArrayList<>();
        ArrayList<Integer> geneSolution21 = new ArrayList();
        ArrayList<Integer> geneSolution22 = new ArrayList<>();

        //Crossing
        if(random.nextFloat() <= crossingProba){
            int crossingPos = random.nextInt(inputSolutions.get(0).getSize()-1);

            //Debug displays
            System.out.println("\n");
            System.out.println("CrossingPos = " + crossingPos);
            System.out.println("Input A : " + inputSolutions.get(0).getState());
            System.out.println("Input B : " + inputSolutions.get(1).getState());
            System.out.println("---------------------------");

            //First input solution
            for(int i = 0; i<=inputSolutions.get(0).getSize()-1; i++) {
                if(i <= crossingPos){
                    geneSolution11.add(inputSolutions.get(0).getIState(i));
                }
                else{
                    geneSolution12.add(inputSolutions.get(0).getIState(i));
                }
            }

            //Second input solution
            for(int i = 0; i<=inputSolutions.get(1).getSize()-1; i++) {
                if(i <= crossingPos){
                    geneSolution21.add(inputSolutions.get(1).getIState(i));
                }
                else{
                    geneSolution22.add(inputSolutions.get(1).getIState(i));
                }
            }

            ArrayList<Integer> firstSol = new ArrayList<>();
            firstSol.addAll(geneSolution11);
            firstSol.addAll(geneSolution22);

            ArrayList<Integer> secondSol = new ArrayList<>();
            secondSol.addAll(geneSolution21);
            secondSol.addAll(geneSolution12);

            System.out.println("Output A' : " + firstSol);
            System.out.println("Output B' : " + secondSol);

            outputSolutions.add(new Solution(firstSol));
            outputSolutions.add(new Solution(secondSol));
            //System.out.println(outputSolutions);
            return outputSolutions;
        }

        else{
            System.out.println("Crossing not happening");
            return null;
        }
    }

    public void mutation (Solution solution, float mutationProba){
        if(random.nextFloat() <= mutationProba) {
            System.out.println("---------------------------");
            System.out.println("Mutation happening on : " + solution.getState());
            int mutationPos = random.nextInt(solution.getSize()-1);
            int mutationValue = 1 + random.nextInt(solution.getSize());
            solution.getState().set(mutationPos, mutationValue);
            System.out.println("Mutated solution : " + solution.getState());
        }
    }

    public Solution geneticResolution(ArrayList<Solution> inputSolutions, float crossingProba, float mutationProba){

        // Init
        ArrayList<Solution> outputSolutions = inputSolutions;

        //Crossing
        outputSolutions = crossing(inputSolutions, crossingProba);
        if(outputSolutions == null || outputSolutions.isEmpty()){
            outputSolutions = inputSolutions;
        }

        //Mutation
        for (Solution sol : outputSolutions) {
            mutation(sol, mutationProba);
        }

        //Output Variables
        int min = outputSolutions.get(0).getNbConflicts();
        Solution bestSol = outputSolutions.get(0);

        //Selection
        for (Solution sol : outputSolutions) {
            if(sol.getNbConflicts() < min){
                 bestSol = sol;
                 min = sol.getNbConflicts();
            }
        }
        System.out.println("\n Best solution found : " + bestSol.getState() + " | Nb Conflicts : " + min);
        return bestSol;
    }
}
