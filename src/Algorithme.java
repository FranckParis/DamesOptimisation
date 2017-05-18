import Manager.GeneticSolutionManager;
import Manager.SolutionManager;
import javafx.application.Application;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import object.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Quentin on 18/05/2017.
 */
public class Algorithme extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Echiquier");
        dialog.setHeaderText("Veuillez choisir la taille de l'échiquier");
        dialog.setContentText("Taille de l'échiquier :");
        int n = 0;
        // Traditional way to get the response value.
        while(n == 0){
            Optional<String> result2 = dialog.showAndWait();
            if (!result2.isPresent()){
                return;
            }
            String str = result2.get();

            try
            {
                n = Integer.parseInt(str);
            }
            catch(NumberFormatException nfe)
            {
                n = 0;
                dialog.setContentText("Taille de l'échiquier :\nChoisissez un nombre");
            }
        }


        SolutionManager sm = new SolutionManager();
        String algo;
        List<String> choices = new ArrayList<>();
        String choice1 = "Genetique";
        String choice2 = "Recuit";
        String choice3 = "Tabou";
        choices.add(choice1);
        choices.add(choice2);
        choices.add(choice3);
        String errorMessage = "";
        ChoiceDialog<String> dialog1 = new ChoiceDialog<>(choice1, choices);

        dialog1.setTitle("Choix de type de connexion");
        dialog1.setContentText(errorMessage + "\n Choisissez votre moyen de connexion :");
        Optional<String> result = dialog1.showAndWait();
        if(result.isPresent()){
            algo = result.get();
            switch (algo){
                case "Genetique":
                    GeneticSolutionManager gsm = new GeneticSolutionManager(n);
                    TextInputDialog dialog2 = new TextInputDialog("");
                    dialog2.setTitle("Population");
                    dialog2.setHeaderText("Veuillez choisir la taille de la population");
                    dialog2.setContentText("Taille de la population :");
                    int pop = 0;
                    // Traditional way to get the response value.
                    while(pop == 0){
                        Optional<String> result2 = dialog2.showAndWait();
                        if (!result2.isPresent()){
                            return;
                        }
                        String str = result2.get();

                        try
                        {
                            pop = Integer.parseInt(str);
                        }
                        catch(NumberFormatException nfe)
                        {
                            pop = 0;
                            dialog.setContentText("Taille de la population :\nChoisissez un nombre");
                        }
                    }

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
                    System.out.println("\n Best Solution : "+ bestSol.getState() + " \n NbConflicts : "+ bestSol.getNbConflicts()+" \n Fitness : " + (n*(n-1)-bestSol.getNbConflicts())); break;
                case "Recuit":
                    Solution solRecuit = sm.recuitSimulte(n);
                    System.out.println(solRecuit);
                    System.out.println(solRecuit.getNbConflicts());
                    System.out.println(solRecuit.getState());
                    break;
                case "Tabou":
                    Solution solTabou = sm.tabou(n,5);
                    System.out.println(solTabou);
                    System.out.println(solTabou.getNbConflicts());
                    System.out.println(solTabou.getState());
                    break;
            }
        }
        else{
            System.out.println("Vous avez quitté");
            return;
        }
    }
    public void run() {
        String [] empty = new String[0];
        Application.launch(empty);
    }
}
