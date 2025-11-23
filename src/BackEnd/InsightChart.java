package BackEnd;

import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.Color;
import java.util.Map;
import javax.swing.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class InsightChart {

    public enum ChartType {
        STUDENT_PERFORMANCE,
        QUIZ_AVERAGE,
        COURSE_COMPLETION
    }

    private Map<String, Double> data;
    private ChartType type;

    public InsightChart(Map<String, Double> data, ChartType type) {
        this.data = data;
        this.type = type;
    }

    public void showChart() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(type.name().replace("_", " "));
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            String seriesName = switch (type) {
                case STUDENT_PERFORMANCE -> "Score";
                case QUIZ_AVERAGE -> "Quiz Avg";
                case COURSE_COMPLETION -> "Completion %";
            };

            for (Map.Entry<String, Double> entry : data.entrySet()) {
                dataset.addValue(entry.getValue(), seriesName, entry.getKey());
            }

            String title = switch (type) {
                case STUDENT_PERFORMANCE -> "Student Performance";
                case QUIZ_AVERAGE -> "Quiz Average per Lesson";
                case COURSE_COMPLETION -> "Course Completion Percentage";
            };

            JFreeChart chart = ChartFactory.createBarChart(
                    title,
                    type == ChartType.COURSE_COMPLETION ? "Course" : "Lesson/Quiz",
                    seriesName,
                    dataset
            );

            // Dark theme styling
            chart.setBackgroundPaint(new Color(43, 43, 43));
            CategoryPlot plot = chart.getCategoryPlot();
            plot.setBackgroundPaint(new Color(60, 63, 65));
            plot.setOutlinePaint(new Color(100, 100, 100));
            plot.setDomainGridlinePaint(new Color(120, 120, 120));
            plot.setRangeGridlinePaint(new Color(120, 120, 120));

            chart.getTitle().setPaint(Color.WHITE);
            if (chart.getLegend() != null) {
                chart.getLegend().setBackgroundPaint(new Color(43, 43, 43));
                chart.getLegend().setItemPaint(Color.WHITE);
            }

            plot.getDomainAxis().setTickLabelPaint(Color.WHITE);
            plot.getDomainAxis().setLabelPaint(Color.WHITE);
            plot.getRangeAxis().setTickLabelPaint(Color.WHITE);
            plot.getRangeAxis().setLabelPaint(Color.WHITE);

            // Bar styling
            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            renderer.setSeriesPaint(0, new Color(155, 135, 245));
            renderer.setMaximumBarWidth(0.15);
            renderer.setItemMargin(0.2);

            frame.add(new ChartPanel(chart));
            frame.setSize(600, 500);
            frame.setVisible(true);
        });
    }

    // TEST MAIN
    public static void main(String[] args) {
        try {
            FlatDarkLaf.setup();
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf");
        }

        // STUDENT PERFORMANCE
        Map<String, Double> studentScores = Map.of(
                "Quiz 1", 85.0,
                "Quiz 2", 70.0,
                "Quiz 3", 90.0
        );
        new InsightChart(studentScores, ChartType.STUDENT_PERFORMANCE).showChart();

        // QUIZ AVERAGE PER LESSON
        Map<String, Double> lessonAvg = Map.of(
                "Lesson 1", 80.0,
                "Lesson 2", 65.0,
                "Lesson 3", 90.0
        );
        new InsightChart(lessonAvg, ChartType.QUIZ_AVERAGE).showChart();

        // COURSE COMPLETION
        Map<String, Double> completion = Map.of(
                "Course A", 75.0,
                "Course B", 50.0,
                "Course C", 95.0
        );
        new InsightChart(completion, ChartType.COURSE_COMPLETION).showChart();
    }
}
