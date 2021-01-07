import java.util.Random;

public class Game {

    private final Random rnd = new Random();
    private int[][] field = null;
    private int colorCount = 0;


    public Game() {
    }

    public void newGame(int rowCount, int colCount, int colorCount) {
        // создаем поле
        field = new int[rowCount][colCount];
        this.colorCount = colorCount;
    }

// каждая функция меняет поле в зависимости от нажатой клавиши
// 1. Передвигает все клеточки
// 2. Проверяет возможность добавления новой клеточки
// если да - добавляет
// если нет - игра проиграна
    // выглядит ужасно
    public boolean upButtonPress() {
        boolean wasAnyMoving = false;
        boolean wasSumInIteration; // чтобы 2 2 4 превращалось в 4 4, а не в 8
        for (int i = 0; i < getColCount(); i++) {
            wasSumInIteration = false;
            for (int j = 0; j < getRowCount(); j++) { // j - индекс взятой клетки

                for (int k = j; k > 0; k--) { // двигаем j раз
                    if (field[k - 1][i] == 0) { // если пусто - передвигаем
                        field[k - 1][i] = field[k][i];
                        field[k][i] = 0;
                        if (field[k - 1][i] != 0 || field[k][i] != 0) wasAnyMoving = true; // чтобы не двигать нули
                    }
                    if (field[k - 1][i] == field[k][i] && field[k - 1][i] != 0 && !wasSumInIteration) { // равно - складываем
                        field[k - 1][i] = field[k - 1][i] * 2; // чтобы не двигать нули
                        field[k][i] = 0;
                        wasSumInIteration = true;
                        wasAnyMoving = true;
                        break;

                    }
                }

            }
        }
        if (wasAnyMoving) addRandomCell();
        return wasAnyMoving;
    }

    public boolean downButtonPress() { //
        boolean wasAnyMoving = false;
        boolean wasSumInIteration;
        for (int i = 0; i < getColCount(); i++) {
            wasSumInIteration = false;
            for (int j = getRowCount() - 1; j >= 0; j--) {
                for (int k = j; k < getRowCount() - 1; k++) {
                    if (field[k + 1][i] == 0) {
                        field[k + 1][i] = field[k][i];
                        field[k][i] = 0;
                        if (field[k + 1][i] != 0 || field[k][i] != 0) wasAnyMoving = true;
                    }
                    if (field[k + 1][i] == field[k][i] && field[k + 1][i] != 0 && !wasSumInIteration) {
                        field[k + 1][i] = field[k + 1][i] * 2;
                        field[k][i] = 0;
                        wasAnyMoving = true;
                        wasSumInIteration = true;
                        break;
                    }
                }

            }
        }
        if (wasAnyMoving) addRandomCell();
        return wasAnyMoving;
    }

    public boolean rightButtonPress() { //
        boolean wasAnyMoving = false;
        boolean wasSumInIteration;
        for (int i = 0; i < getRowCount(); i++) {
            wasSumInIteration = false;
            for (int j = getColCount() - 1; j >= 0; j--) {
                for (int k = j; k < getRowCount() - 1; k++) {
                    if (field[i][k + 1] == 0) {
                        field[i][k + 1] = field[i][k];
                        field[i][k] = 0;
                        if (field[i][k + 1] != 0 || field[i][k] != 0) wasAnyMoving = true;
                    }
                    if (field[i][k + 1] == field[i][k] && field[i][k] != 0 && !wasSumInIteration) {
                        field[i][k + 1] = field[i][k + 1] * 2;
                        field[i][k] = 0;
                        wasAnyMoving = true;
                        wasSumInIteration = true;
                        break;
                    }
                }

            }
        }
        if (wasAnyMoving) addRandomCell();
        return wasAnyMoving;
    }

    public boolean leftButtonPress() { //
        boolean wasAnyMoving = false;
        boolean wasSumInIteration;
        for (int i = 0; i < getRowCount(); i++) {
            wasSumInIteration = false;
            for (int j = 0; j < getColCount(); j++) {
                for (int k = j; k > 0; k--) {
                    if (field[i][k - 1] == 0) {
                        field[i][k - 1] = field[i][k];
                        field[i][k] = 0;
                        if (field[i][k - 1] != 0 || field[i][k] != 0) wasAnyMoving = true;
                    }
                    if (field[i][k - 1] == field[i][k] && field[i][k] != 0 && !wasSumInIteration) {
                        field[i][k - 1] = field[i][k - 1] * 2;
                        field[i][k] = 0;
                        wasAnyMoving = true;
                        wasSumInIteration = true;
                        break;
                    }
                }

            }
        }
        if (wasAnyMoving) addRandomCell();
        return wasAnyMoving;
    }


    public boolean addRandomCell() { // возвращает false, если нельзя добавить
        int countOfZeroElement = 0;
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getColCount(); j++) {
                if (field[i][j] == 0) countOfZeroElement++;
            }
        }
        if (countOfZeroElement == 0) return false;
        int index = rnd.nextInt(countOfZeroElement);
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getColCount(); j++) {
                if (field[i][j] == 0) {
                    if (index == 0) {
                        field[i][j] = 2;
                        return true;
                    } else index--;
                }

            }
        }
        return true;

    }

    public int getRowCount() {
        return field == null ? 0 : field.length;
    }

    public int getColCount() {
        return field == null ? 0 : field[0].length;
    }

    public int getColorCount() {
        return colorCount;
    }

    public int getCell(int row, int col) {
        return (row < 0 || row >= getRowCount() || col < 0 || col >= getColCount()) ? 0 : field[row][col];
    }

}
