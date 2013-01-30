/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graficos;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleInsets;

/**
 *
 * @author ALDAJO
 */
public class ChartData2 extends JPanel {
    private TimeSeries dataArray[];
    private TimeSeriesCollection dataset;
    private XYItemRenderer renderer;
    private Color[] colors = {Color.BLACK, Color.GRAY, Color.BLUE, Color.RED};
    
    public ChartData2(int maxAge, int cant) {
        super(new BorderLayout());
        
        dataArray = new TimeSeries[cant];
        dataset = new TimeSeriesCollection();
        
        for(int i=0;i<dataArray.length; i++){
            dataArray[i] = new TimeSeries("Line"+(i+1), Millisecond.class);
            dataArray[i].setMaximumItemAge(maxAge);
            dataset.addSeries(dataArray[i]);
        }
        
        DateAxis domain = new DateAxis("Time");
        NumberAxis values = new NumberAxis("Values");
        values.setAutoRangeIncludesZero(false);
        //values.setAutoRangeStickyZero(false);
        //whit domain and values i can modify the properties of this axes
        
        renderer = new XYLineAndShapeRenderer(true, false);
        for(int i=0; i<dataArray.length; i++){
            renderer.setSeriesPaint(i, colors[i]);
            renderer.setSeriesStroke(i, new BasicStroke(1f));
        }
        
        XYPlot plot = new XYPlot(dataset, domain, values, renderer);
        plot.setBackgroundPaint(Color.GREEN);
        plot.setDomainGridlinePaint(Color.GRAY);
        plot.setRangeGridlinePaint(Color.GRAY);
        plot.setAxisOffset(new RectangleInsets(5, 5, 5, 5));
        domain.setAutoRange(true);
        
        JFreeChart chart = new JFreeChart(plot);
        chart.setBackgroundPaint(Color.LIGHT_GRAY);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createCompoundBorder(
                null, BorderFactory.createLineBorder(Color.BLACK)));
        add(chartPanel);
    }
    
    public void addData(float y, int line){
        if(line<dataArray.length){
            dataArray[line].add(new Millisecond(), y);
        }
    }
}
