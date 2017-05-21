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
                "mu","Temps d'éxécution (ms)",
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
        int n = 100;

        for(int mu=1; mu<10;mu++){
            double trueMu = (double) mu/10;
            System.out.println("Current:" +trueMu);
            nbConflits=0;
            long startTime, totalTime = 0;
            for(int i=0; i<nbSimulations; i++){
                startTime = System.currentTimeMillis();
                Solution s = sm.recuitSimulte(n,trueMu);
                nbConflits += s.getNbConflicts();

                totalTime += System.currentTimeMillis()-startTime;
            }
            double averageConclicts = (double)nbConflits / (double)nbSimulations;
            dataset.addValue(totalTime/nbSimulations,"time", String.valueOf(trueMu));
        }
        return dataset;
    }

    public static void main( String[ ] args ) {
        LineChart_AWT chart = new LineChart_AWT(
                "Temps moyen sur 10 essais" ,
                "Temps d'éxécution selon mu(n=100)");

        chart.pack( );
        RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible( true );
    }
}