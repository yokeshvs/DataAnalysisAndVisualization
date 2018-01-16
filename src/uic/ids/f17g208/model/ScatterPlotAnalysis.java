package uic.ids.f17g208.model;

import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.function.LineFunction2D;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.statistics.Regression;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeriesCollection;

public class ScatterPlotAnalysis {

	static double beta0;
	static double beta1;

	public static JFreeChart generateScatterPlot(XYSeriesCollection dataset, String col1, String col2, double xmin,
			double xmax, double ymin, double ymax) {
		JFreeChart chart = ChartFactory.createScatterPlot("Scatter Plot for " + col1 + " vs " + col2, col1, col2,
				dataset, PlotOrientation.VERTICAL, true, true, false);
		XYPlot plot = chart.getXYPlot();
		plot.getRenderer().setSeriesPaint(0, Color.blue);
		generateRegressionLine(chart, dataset, col1, col2, xmin, xmax);
		return chart;
	}

	private static void generateRegressionLine(JFreeChart chart, XYSeriesCollection dataset, String col1, String col2,
			double xmin, double xmax) {
		double regressionParameters[] = Regression.getOLSRegression(dataset, 0);
		regressionParameters[0] = beta0;
		regressionParameters[1] = beta1;
		LineFunction2D linefunction2d = new LineFunction2D(regressionParameters[0], regressionParameters[1]);
		XYDataset dataset1 = DatasetUtilities.sampleFunction2D(linefunction2d, xmin, xmax, 3, "Fitted Regression Line");
		XYPlot xyPlot = chart.getXYPlot();
		xyPlot.setDataset(1, dataset1);
		XYLineAndShapeRenderer xylineAndShapeRenderer = new XYLineAndShapeRenderer(true, false);
		xylineAndShapeRenderer.setSeriesPaint(0, Color.RED);
		xyPlot.setRenderer(1, xylineAndShapeRenderer);
	}

	public static void generateRegressionEquation(double sumX, double sumY, int n, double[] xNew, double[] yNew) {
		double[] x = xNew;
		double[] y = yNew;
		double xBar = sumX / n;
		double yBar = sumY / n;
		double xxBar = 0.0, xyBar = 0.0;
		for (int i = 0; i < n; i++) {
			xxBar += (x[i] - xBar) * (x[i] - xBar);
			xyBar += (x[i] - xBar) * (y[i] - yBar);
		}
		beta1 = xyBar / xxBar;
		beta0 = yBar - beta1 * xBar;

	}

}
