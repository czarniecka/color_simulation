import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import java.util.Random;

/** Klasa Field dziedzicząca po Rectangle i implementująca Runnable.
 * Klasa reprezentująca pojedyncze pole w symulacji.
 */
public class Field extends Rectangle implements Runnable {

    private final int row;
    private final int column;
    private final Simulation simulation;
    private final Random random = new Random();
    private boolean suspended = false;

    /** Konstruktor klasy Field
     * Wywołuje konstruktor klasy nadrzędnej (Rectangle).
     * @param row numer wiersza pola
     * @param column numer kolumny pola
     * @param size rozmiar pola
     * @param simulation obiekt symulacji
     */
    public Field(int row, int column, int size, Simulation simulation) {
        super(size, size);
        this.row = row;
        this.column = column;
        this.simulation = simulation;

        setFill(generateRandomColor());
        setStrokeType(StrokeType.INSIDE);
        setStroke(Color.TRANSPARENT);

        // Obsługa zdarzenia kliknięcia myszą na wątek, w celu zatrzymania go.
        setOnMouseClicked(e -> {
            setStroke(Color.BLACK);
            synchronized (this) {
                suspended = !suspended;
                if (!suspended) {
                    notify();
                    setStroke(Color.TRANSPARENT);
                }
            }
        });
    }

    /** Metoda sprawdza, czy pole jest zawieszone.
     * @return true (jeśli pole jest zawieszone); false (jeśli pole nie jest zawieszone)
     */
    private boolean isSuspended() {
        return suspended;
    }

    /** Metoda zwraca pole na podstawie kolumny i wiersza.
     * @param column numer kolumny pola
     * @param row    numer wiersza pola
     * @return konkretne pole
     */
    private Field getField(int row, int column) {
        return SimulationGUI.allFields[row][column];
    }

    /** Metoda generuje losowy kolor na podstawie składowych.
     * @return wygenerowany losowy kolor
     */
    public Color generateRandomColor() {
        return Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    /** Metoda do pobierania koloru określonego pola.
     * @param row numer wiersza pola
     * @param column numer kolumny pola
     * @return kolor pola
     */
    private Color getColor(int row, int column) {
        // Działania modulo sprawiają, że pola na przeciwnych krawędziach sąsiadują ze sobą.
        int row2 = (row + SimulationGUI.allRows) % SimulationGUI.allRows;
        int column2 = (column + SimulationGUI.allColumns) % SimulationGUI.allColumns;

        return (Color) getField(row2, column2).getFill();
    }

    /** Metoda zwraca kolory pól sąsiadujących.
     * @return tablica kolorów pól sąsiadujących
     */
    public Color[] getNeighborsColors() {
        Color[] neighborsColors = new Color[4];
        neighborsColors[0] = getColor(row + 1, column); // Góra
        neighborsColors[1] = getColor(row - 1, column); // Dół
        neighborsColors[2] = getColor(row, column + 1); // Prawo
        neighborsColors[3] = getColor(row, column - 1); // Lewo

        // Jeśli pole sąsiadujące jest zawieszone, to jego kolor ustawia się na null.
        for (int i = 0; i < 4; i++) {
            if (getField(row, column).isSuspended()) {
                neighborsColors[i] = null;
            }
        }

        return neighborsColors;
    }

    /** Metoda, która uruchamia się, gdy wątek jest uruchomiony.
     * Sprawdza, czy pole jest zawieszone, i jeśli nie, to uruchamia symulację
     * i czeka na określone opóźnienie.
     */
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(simulation.generateDelay());
                synchronized (this) {
                    while (suspended) {
                        wait();
                    }
                    if (!suspended) {
                        simulation.startSimulation(row, column);
                    }
                }
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}