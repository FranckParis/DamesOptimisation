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

            outputSolutions.add(new Solution(firstSol));
            outputSolutions.add(new Solution(secondSol));
            return outputSolutions;
        }

        else{
            return inputSolutions;
        }
    }

    public Solution mutation (Solution solution, float mutationProba){
        Solution mutatedSol = solution;
        if(random.nextFloat() <= mutationProba) {
            int mutationPos = random.nextInt(solution.getSize()-1);
            int mutationValue = 1 + random.nextInt(solution.getSize());
            mutatedSol.getState().set(mutationPos, mutationValue);
        }
        return mutatedSol;
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
        int cpt;
        int cptDimension = 1;

        Solution selectedSolution = probabilizedSolutions.get(random.nextInt(probabilizedSolutions.size()));
        initialAdd.add(selectedSolution);
        outputSolutions.add(initialAdd);

        while(cptDimension < initialPopSize){
            ArrayList<Solution> couple = new ArrayList<>();
            cpt = 0;

            while(cpt < reprodSize){
                selectedSolution = probabilizedSolutions.get(random.nextInt(probabilizedSolutions.size()));
                boolean addable = true;

                for (Solution sol : couple) {
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
                    cpt++;
                }
            }
            outputSolutions.add(couple);
            cptDimension++;
        }

        return outputSolutions;
    }

    public Solution callGeneticResolution(float crossingProba, float mutationProba, int reprodSize, int initialPopSize){

        ArrayList<Solution> inputSolutions = new ArrayList<>();
        for(int i=0; i<initialPopSize; i++){
            inputSolutions.add(new Solution(dimension));
        }
        return geneticResolution(inputSolutions, crossingProba, mutationProba, reprodSize, initialPopSize);
    }

    public Solution geneticResolution(ArrayList<Solution> inputSolutions,float crossingProba, float mutationProba, int reprodSize, int initialPopSize){


        // Init
        ArrayList<ArrayList<Solution>> outputSolutions = new ArrayList<>();
        ArrayList<ArrayList<Solution>> progessList;
        ArrayList<Solution> collect = new ArrayList();
        Solution bestSolution = new Solution(this.dimension);

        if(initialPopSize > 1){

            //Reproduction
            progessList = reproduction(inputSolutions, reprodSize, initialPopSize);

            //Crossing
            for(ArrayList<Solution> couple : progessList){
                outputSolutions.add(crossing(couple, crossingProba));
            }

            //Mutation
            Solution temp;
            for (ArrayList<Solution> couple : outputSolutions) {
                for(Solution sol : couple){
                    collect.add(mutation(sol, mutationProba));
                }
            }
            geneticResolution(collect, crossingProba, mutationProba, reprodSize, initialPopSize-1);
        }
        if(!collect.isEmpty()){
            bestSolution = findBestSolution(collect);
        }
        return bestSolution;
    }
}
