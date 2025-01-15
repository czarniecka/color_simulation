## Autor
Aleksandra Czarniecka

## Color Simulation

Color Simulation to symulacja oparta na wątkach, napisana w Javie z wykorzystaniem JavaFX. Symulacja odbywa się na planszy o wymiarach `n x m`, gdzie każde pole reprezentuje kwadrat o losowym kolorze. Każdy z kwadratów jest niezależnym wątkiem, który co jakiś losowy czas zmienia swój kolor według określonych zasad.

## Funkcjonalności

	- Każde pole zmienia swój kolor z pewnym prawdopodobieństwem `p` na losowy.
	- Z prawdopodobieństwem `1-p` kolor pola jest obliczany jako średnia kolorów jego sąsiadów.
	- Możliwość zatrzymywania poszczególnych wątków.
	- Interfejs graficzny (GUI) oparty na JavaFX umożliwia obserwowanie przebiegu symulacji w czasie rzeczywistym.

## Wymagania

	- JDK
	- JavaFX

## Instalacja i uruchomienie

Kompilacja:
	```bash
	javac --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml -d . SimulationGUI.java
	```
Uruchomienie:
	```bash
	java --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml SimulationGUI n m p v
	```
	   - `n` - liczba wierszy na planszy.
	   - `m` - liczba kolumn na planszy.
	   - `p` - prawdopodobieństwo zmiany koloru na losowy.
	   - `v` - szybkość.

