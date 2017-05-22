import Manager.SolutionManager;
import object.Solution;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class LineChart_AWT extends ApplicationFrame {

    public LineChart_AWT( String applicationTitle , String chartTitle ) {
        super(applicationTitle);
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "taille de la liste","Temps d'éxécution (ms)",
                createDataset(),
                PlotOrientation.VERTICAL,
                true,true,false);

        ChartPanel chartPanel = new ChartPanel( lineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        setContentPane( chartPanel );
    }

    private DefaultCategoryDataset createDataset( ) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        SolutionManager sm = new SolutionManager();
        int nbConflits;
        int nbSimulations = 10;
        int n = 40;

        for(int listeTabou=1; listeTabou<20;listeTabou++){

            System.out.println("listSize:" +listeTabou);
            nbConflits=0;
            long startTime, totalTime = 0;
            for(int i=0; i<nbSimulations; i++){
                startTime = System.currentTimeMillis();
                Solution s = sm.tabou(n,listeTabou);
                nbConflits += s.getNbConflicts();

                totalTime += System.currentTimeMillis()-startTime;
            }
            double averageConclicts = (double)nbConflits / (double)nbSimulations;
            dataset.addValue(totalTime/nbSimulations,"time", String.valueOf(listeTabou));
        }
        return dataset;
    }

    public static void main( String[ ] args ) {
        LineChart_AWT chart = new LineChart_AWT(
                "Temps moyen sur 10 essais" ,
                "Temps d'éxécution selon la taille de la liste(n=40)");

        chart.pack( );
        RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible( true );
    }
}