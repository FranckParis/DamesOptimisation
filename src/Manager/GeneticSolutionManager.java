package Manager;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import object.Solution;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by franck on 5/15/17.
 */
public class GeneticSolutionManager {

    public SolutionManager solutionManager;
    public Random random;
    public int dimension;

    public GeneticSolutionManager(int n){
        this.solutionManager = new SolutionManager();
        this.random = new Random();
        this.dimension = n;
    }

    public Solution findBestSolution (ArrayList<Solution> solutions){

        Solution bestSol = new Solution(this.dimension);
        int max = this.dimension*(dimension-1) - solutions.get(0).getNbConflicts();

        //Finding initial best solution
        for (Solution sol : solutions) {
            if(this.dimension*(dimension-1) - sol.getNbConflicts() > max){
                bestSol = sol;
                max = sol.getNbConflicts();
            }
        }
        return bestSol;
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
            /*System.out.println("\n");
            System.out.println("CrossingPos = " + crossingPos);
            System.out.println("Input A : " + inputSolutions.get(0).getState());
            System.out.println("Input B : " + inputSolutions.get(1).getState());
            System.out.println("---------------------------");*/

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

            //System.out.println("Output A' : " + firstSol);
            //System.out.println("Output B' : " + secondSol);

            outputSolutions.add(new Solution(firstSol));
            outputSolutions.add(new Solution(secondSol));
            //System.out.println(outputSolutions);
            return outputSolutions;
        }

        else{
            //System.out.println("Crossing not happening");
            return inputSolutions;
        }
    }

    public void mutation (Solution solution, float mutationProba){
        if(random.nextFloat() <= mutationProba) {
            //System.out.println("---------------------------");
            //System.out.println("Mutation happening on : " + solution.getState());
            int mutationPos = random.nextInt(solution.getSize()-1);
            int mutationValue = 1 + random.nextInt(solution.getSize());
            solution.getState().set(mutationPos, mutationValue);
            //System.out.println("Mutated solution : " + solution.getState());
        }
    }


    public ArrayList<ArrayList<Solution>> reproduction(ArrayList<Solution> inputSolutions, int reprodSize, int initialPopSize){

        //Init
        ArrayList<ArrayList<Solution>> outputSolutions = new ArrayList<>();
        ArrayList<Solution> initialAdd = new ArrayList<>();

        Solution bestSol = new Solution(this.dimension);
        int bestSolPos = 0;
        int cptBestSol = 0;
        int min = inputSolutions.get(0).getNbConflicts();

        //Finding initial best solution
        for (Solution sol : inputSolutions) {
            if(sol.getNbConflicts() < min){
                bestSol = sol;
                min = sol.getNbConflicts();
                bestSolPos = cptBestSol;
            }
            cptBestSol++;
        }

        //Adding best solution to output
        initialAdd.add(bestSol);
        inputSolutions.remove(bestSolPos);

        //Biaised Roulette - Fitness
        ArrayList<Solution> probabilizedSolutions = new ArrayList<>();

        //Probabilized List
        for (Solution sol : inputSolutions) {
            int fitnessFraction = (this.dimension * (this.dimension - 1) - sol.getNbConflicts() + 1 )/inputSolutions.size();
            for(int i=0; i<=fitnessFraction; i++){
                probabilizedSolutions.add(sol);
            }
        }

        //Selection in list
        int cpt = 1;
        int cptDimension = 1;

        Solution selectedSolution = probabilizedSolutions.get(random.nextInt(probabilizedSolutions.size()));
        initialAdd.add(selectedSolution);
        outputSolutions.add(initialAdd);

        while(cptDimension < initialPopSize/2){
            //System.out.println(cptDimension);
            ArrayList<Solution> couple = new ArrayList<>();
            cpt = 0;

            while(cpt < reprodSize){
                //System.out.println("********  CPT = "+ cpt);
                selectedSolution = probabilizedSolutions.get(random.nextInt(probabilizedSolutions.size()));
                //System.out.println("Trying solution : " + selectedSolution.getState());
                boolean addable = true;

                for (Solution sol : couple) {
                    //System.out.println("Comparing to output sol : " + sol.getState());
                    int cptCommon = 0;
                    for(int i=0; i<sol.getState().size(); i++){
                        if(sol.getState().get(i) == selectedSolution.getState().get(i)){
                            cptCommon++;
                        }
                    }
                    if(cptCommon == sol.getState().size()){
                        addable = false;
                    }
                }

                if(addable){
                    couple.add(selectedSolution);
                    //System.out.println("Addable solution : " + selectedSolution.getState() + "\n");
                    cpt++;
                }
                else{
                    //System.out.println("Non addable solution \n");
                }
            }
            outputSolutions.add(couple);
            cptDimension++;
        }

        //System.out.println("\n Output solutions after reproduction : ");
        for (ArrayList<Solution> couple : outputSolutions) {
            for(Solution sol : couple){
                //System.out.println(sol.getState() + " | Fitness : "+ (this.dimension*(this.dimension-1) - sol.getNbConflicts()));
            }
            //System.out.println("-------------------------------");
        }

        return outputSolutions;
    }

    public Solution geneticResolution(ArrayList<Solution> inputSolutions, float crossingProba, float mutationProba, int reprodSize, int initialPopSize){

        // Init
        ArrayList<ArrayList<Solution>> outputSolutions = new ArrayList<>();
        ArrayList<ArrayList<Solution>> progessList;
        ArrayList<Solution> collect = new ArrayList();
        Solution bestSolution = new Solution(this.dimension);

        /*System.out.println("\n");
        System.out.println("ReprodSize : " +  reprodSize);
        System.out.println("InitialPopSize : " + initialPopSize);*/

        if(initialPopSize > 1){

            //System.out.println("----------------------- Iteration for size : " + initialPopSize);

            //Reproduction
            progessList = reproduction(inputSolutions, reprodSize, initialPopSize);

            //Crossing
            for(ArrayList<Solution> couple : progessList){
                outputSolutions.add(crossing(couple, crossingProba));
            }

            //Mutation
            for (ArrayList<Solution> couple : outputSolutions) {
                for(Solution sol : couple){
                    mutation(sol, mutationProba);
                }
            }

            for (ArrayList<Solution> couple : outputSolutions) {
                for (Solution sol : couple) {
                    //System.out.println("Output Solution : " + sol.getState() + " | Fitness : " + (dimension*(dimension-1) - sol.getNbConflicts()));
                    collect.add(sol);
                }
                //System.out.println("----------------------------------------");
            }

            /*System.out.println("\n Collect :");
            for (Solution sol : collect) {
                System.out.println("Output Solution : " + sol.getState() + " | Fitness : " + (dimension*(dimension-1) - sol.getNbConflicts()));
            }*/

            if((int)(initialPopSize/2) == 2){
                geneticResolution(collect, crossingProba, mutationProba, 1, 2);
            }
            else{
                geneticResolution(collect, crossingProba, mutationProba, reprodSize, initialPopSize/2);
            }
        }
        if(!collect.isEmpty()){
            bestSolution = findBestSolution(collect);
        }
        return bestSolution;
    }
}
