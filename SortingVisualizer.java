import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class SortingVisualizer extends JPanel {
    private int[] arr;
    private final int WIDTH = 800, HEIGHT = 400;
    private final int BAR_WIDTH = 5;
    private Timer timer;
    private boolean isSorting = false;git
    private String algorithm = "BubbleSort";

    public SortingVisualizer() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        arr = new int[WIDTH / BAR_WIDTH];
        generateArray();

        // Start Timer for visualization
        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isSorting) {
                    if (algorithm.equals("BubbleSort")) {
                        bubbleSortStep();
                    } else if (algorithm.equals("SelectionSort")) {
                        selectionSortStep();
                    } else if (algorithm.equals("InsertionSort")) {
                        insertionSortStep();
                    }
                }
                repaint();
            }
        });
        timer.start();
    }

    // Generate random array for visualization
    private void generateArray() {
        Random rand = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rand.nextInt(HEIGHT);
        }
    }

    // Visualize sorting process in Bubble Sort
    private int bubbleSortIndex = 0;
    private void bubbleSortStep() {
        if (bubbleSortIndex < arr.length - 1) {
            if (arr[bubbleSortIndex] > arr[bubbleSortIndex + 1]) {
                int temp = arr[bubbleSortIndex];
                arr[bubbleSortIndex] = arr[bubbleSortIndex + 1];
                arr[bubbleSortIndex + 1] = temp;
            }
            bubbleSortIndex++;
        } else {
            isSorting = false; // Stop after sorting is complete
        }
    }

    // Visualize sorting process in Selection Sort
    private int selectionSortIndex = 0, selectionSortMinIndex = 0;
    private void selectionSortStep() {
        if (selectionSortIndex < arr.length) {
            selectionSortMinIndex = selectionSortIndex;
            for (int i = selectionSortIndex + 1; i < arr.length; i++) {
                if (arr[i] < arr[selectionSortMinIndex]) {
                    selectionSortMinIndex = i;
                }
            }
            if (selectionSortMinIndex != selectionSortIndex) {
                int temp = arr[selectionSortIndex];
                arr[selectionSortIndex] = arr[selectionSortMinIndex];
                arr[selectionSortMinIndex] = temp;
            }
            selectionSortIndex++;
        } else {
            isSorting = false; // Stop after sorting is complete
        }
    }

    // Visualize sorting process in Insertion Sort
    private int insertionSortIndex = 1;
    private void insertionSortStep() {
        if (insertionSortIndex < arr.length) {
            int key = arr[insertionSortIndex];
            int j = insertionSortIndex - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
            insertionSortIndex++;
        } else {
            isSorting = false; // Stop after sorting is complete
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the array as bars
        for (int i = 0; i < arr.length; i++) {
            g.setColor(Color.RED);
            g.fillRect(i * BAR_WIDTH, HEIGHT - arr[i], BAR_WIDTH, arr[i]);
        }
    }

    // Set the sorting algorithm
    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
        generateArray();
        isSorting = true;
    }

    // Main function to run the application
    public static void main(String[] args) {
        JFrame frame = new JFrame("Sorting Algorithm Visualizer");
        SortingVisualizer visualizer = new SortingVisualizer();

        JPanel controlPanel = new JPanel();
        JButton bubbleSortButton = new JButton("Bubble Sort");
        JButton selectionSortButton = new JButton("Selection Sort");
        JButton insertionSortButton = new JButton("Insertion Sort");

        // Set button actions
        bubbleSortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visualizer.setAlgorithm("BubbleSort");
            }
        });

        selectionSortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visualizer.setAlgorithm("SelectionSort");
            }
        });

        insertionSortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visualizer.setAlgorithm("InsertionSort");
            }
        });

        // Add buttons to control panel
        controlPanel.add(bubbleSortButton);
        controlPanel.add(selectionSortButton);
        controlPanel.add(insertionSortButton);

        // Set up the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.CENTER, visualizer);
        frame.getContentPane().add(BorderLayout.SOUTH, controlPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
