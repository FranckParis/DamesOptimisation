import Manager.GeneticSolutionManager;
import Manager.SolutionManager;
import javafx.application.Application;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import object.Solution;

import java.awt.*;
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
                if(n < 4){
                    n = 0;
                    dialog.setContentText("Taille de l'échiquier :\nChoisissez un nombre supérieur à 4");
                }
            }
            catch(NumberFormatException nfe) {
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

        dialog1.setTitle("Choix Algorithme");
        dialog1.setContentText(errorMessage + "\n Choisissez un algorithme :");
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
                    TextInputDialog dialog3 = new TextInputDialog("");
                    dialog3.setTitle("Nombre d'itérations");
                    dialog3.setHeaderText("Veuillez choisir le nombre d'itérations à effectuer");
                    dialog3.setContentText("Nombre d'itérations :");
                    int iterNumber = 0;
                    // Traditional way to get the response value.
                    while(iterNumber == 0){
                        Optional<String> result3 = dialog3.showAndWait();
                        if (!result3.isPresent()){
                            return;
                        }
                        String str = result3.get();

                        try
                        {
                            iterNumber = Integer.parseInt(str);
                        }
                        catch(NumberFormatException nfe)
                        {
                            iterNumber = 0;
                            dialog.setContentText("Nombre d'itérations :\nChoisissez un nombre");
                        }
                    }
                    System.out.println("Calculating Solution");
                    long startTime = System.currentTimeMillis();
                    Solution bestSol = gsm.callGeneticResolution(0.01f, 0.01f, 2, pop, iterNumber);
                    System.out.println("Processing done in : " + (System.currentTimeMillis() - startTime) + " ms");
                    System.out.println("\n Best Solution : "+ bestSol.getState() + " \n NbConflicts : "+ bestSol.getNbConflicts()+" \n Fitness : " + (n*(n-1)-bestSol.getNbConflicts())); break;
                case "Recuit":
                    System.out.println("calculating Solution");
                    Solution solRecuit = sm.recuitSimulte(n);
                    System.out.println(solRecuit);
                    System.out.println(solRecuit.getNbConflicts());
                    System.out.println(solRecuit.getState());
                    break;
                case "Tabou":
                    System.out.println("calculating Solution");
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
