import javafx.scene.paint.Color;
import java.util.Random;

/** Klasa Simulation zawiera symulację zmainy kolorów pól programu. */
public class Simulation {

    private final double probability;
    private final int speed;

    /** Konstruktor klasy Simulation.
     * @param probability prawdopodobieństwo zmiany koloru na losowy
     * @param speed szybkość symulacji
     */
    public Simulation(double probability, int speed) {
        this.probability = probability;
        this.speed = speed;
    }

    /** Metoda generuje opóźnienie na podstawie szybkości symulacji.
     * @return opóźnienie równe losowo wybranej liczbie milisekund z przedziału [0.5*speed, 1.5*speed]
     */
    public int generateDelay() {
        Random random = new Random();
        double number = random.nextDouble() + 0.5; //losowa liczba z przedziału [0.5, 1.5]
        return (int) (number * speed);
    }

    /** Metoda rozpoczyna symulację dla określonego wiersza i kolumny. 
     * Na podstawie kolorów aktywnych pól sąsiadujących, oblicza średnią ich kolorów.
     * W zależności od prawdopodobieństwa zmienia kolor pola na obliczony lub losowy.
     * @param column numer kolumny pola
     * @param row numer wiersza pola
     */
    public void startSimulation(int row, int column) {
        System.out.println("Start: " + row + "," + column);
        Field field = SimulationGUI.allFields[row][column];
        Color[] neighborsColors = field.getNeighborsColors();

        double red = 0.0;
        double green = 0.0;
        double blue = 0.0;
        int nonNull = 0;

        for (int i = 0; i < 4; i++) {
            Color color = neighborsColors[i];
            red += color.getRed();
            green += color.getGreen();
            blue += color.getBlue();

            if (neighborsColors[i] != null) {
                nonNull++;
            }
        }

        if (nonNull > 0) {
            red /= nonNull;
            green /= nonNull;
            blue /= nonNull;
        }

        if (Math.random() >= probability) {
            field.setFill(Color.color(red, green, blue));
        } else {
            field.setFill(field.generateRandomColor());
        }

        System.out.println("End: " + row + "," + column);
    }
}