import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/** Klasa SimulationGUI jest rozszerzeniem klasy Application.
 * Tworzy graficzny interfejs użytkownika (GUI) do symulacji kolorów.
 */
public class SimulationGUI extends Application {

    public static int allRows;
    public static int allColumns;
    public static Field[][] allFields;

    public static final int fieldSize = 40;

    private static double probability;
    private static int speed;

    /** Metoda tworzy GridPane i dodaje do niego obiekty Field 
     * na podstawie określonej liczby kolumn i wierszy.
     * @param gridPane obiekt klasy GridPane, do którego dodawane są obiekty Field */
    public void doGridPane(GridPane gridPane) {
        for (int row = 0; row < allRows; row++) {
            for (int column = 0; column < allColumns; column++) {
                Field field = new Field(row, column, fieldSize, new Simulation(probability, speed));
                allFields[row][column] = field;
                gridPane.add(field, column, row);
            }
        }
    }

    /** Metoda start uruchamiana przez metodę main.
     * Tworzy GridPane i ustawia scenę.
     * Zawiera pętlę, która tworzy i uruchamia wątki dla każdego pola.
     * @param stage obiekt klasy Stage, na którym wyświetlana jest aplikacja
    */
    @Override
    public void start(Stage stage) {
        GridPane gridPane = new GridPane();
        doGridPane(gridPane);

        Scene scene = new Scene(gridPane, allColumns * fieldSize, allRows * fieldSize);
        stage.setScene(scene);
        stage.setTitle("Color simulation.");
        stage.show();
        stage.setResizable(false);

        for (int row = 0; row < allRows; row++) {
            for (int column = 0; column < allColumns; column++) {
                Field field = allFields[row][column];
                Thread thread = new Thread(field);
                thread.setDaemon(true);
                thread.start();
            }
        }
    }

    /** Główna metoda programu. 
    * Pobiera argumenty po kolei: ilość kolumn, ilość wierszy, prawdopodobieństwo, szybkość.
    * Inicjalizuje pola i uruchamia program.
    * @param args argumenty wiersza poleceń przekazywane do programu 
    */
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Nieprawidłowa ilość parametrów.");
            System.exit(1);
        } else {
            allColumns = Integer.parseInt(args[0]);
            allRows = Integer.parseInt(args[1]);
            probability = Float.parseFloat(args[2]);
            speed = Integer.parseInt(args[3]);

            allFields = new Field[allRows][allColumns];
        }
        
        launch(args);
    }
}