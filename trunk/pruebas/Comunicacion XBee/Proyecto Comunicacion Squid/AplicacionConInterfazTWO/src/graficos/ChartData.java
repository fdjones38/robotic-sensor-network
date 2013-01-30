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
public class ChartData extends JPanel {
    private TimeSeries data;
    private TimeSeriesCollection dataset;
    private XYItemRenderer renderer;
    
    public ChartData(int maxAge) {
        super(new BorderLayout());
        data = new TimeSeries("Line1", Millisecond.class);
        data.setMaximumItemAge(maxAge);
        
        dataset = new TimeSeriesCollection();
        dataset.addSeries(data);
        
        DateAxis domain = new DateAxis("Time");
        NumberAxis values = new NumberAxis("Values");
        //whit domain and values i can modify the properties of this axes
        
        renderer = new XYLineAndShapeRenderer(true, false);
        renderer.setSeriesPaint(0, Color.BLACK);
        renderer.setSeriesStroke(0, new BasicStroke(1f));
        
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
    
    public void addData(double y){
        data.add(new Millisecond(), y);
    }
}
