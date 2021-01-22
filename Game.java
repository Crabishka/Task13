import java.util.Random;

public class Game {

    private final Random rnd = new Random();
    public int[][] field = null;   // сделано public, чтобы проще было писать тест
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
        for (int j = 0; j < getColCount(); j++) {
            for (int i = 0; i < getRowCount(); i++)  // клетку которую мы берем и относительно которой рассматриваем дальше
            {
                for (int k = i + 1; k < getRowCount(); k++) {
                    if (field[k][j] != 0)  // 0 клетки не трогаем (логично)
                    {
                        if (field[i][j] == 0)  //  передвигаем если следующая 0
                        {
                            field[i][j] = field[k][j];
                            field[k][j] = 0;
                            wasAnyMoving = true;
                        } else {
                            if (field[i][j] == field[k][j]) {   // передвигаем если равно
                                field[i][j] += field[k][j];
                                field[k][j] = 0;
                                wasAnyMoving = true;
                            }
                            break;
                        }
                    }
                }
            }
        }
        if (wasAnyMoving) addRandomCell();
        return wasAnyMoving;
    }

    public boolean downButtonPress() {
        boolean wasAnyMoving = false;
        for (int j = 0; j < getColCount(); j++) {
            for (int i = getRowCount() - 1; i >= 0; i--)
            {
                for (int k = i - 1; k >= 0; k--) {
                    if (field[k][j] != 0)
                    {
                        if (field[i][j] == 0)
                        {
                            field[i][j] = field[k][j];
                            field[k][j] = 0;
                            wasAnyMoving = true;
                        } else {
                            if (field[i][j] == field[k][j]) {
                                field[i][j] += field[k][j];
                                field[k][j] = 0;
                                wasAnyMoving = true;
                            }
                            break;
                        }
                    }
                }
            }
        }
        if (wasAnyMoving) addRandomCell();
        return wasAnyMoving;
    }

    public boolean leftButtonPress() { // переделать
        boolean wasAnyMoving = false;
        for (int j = 0; j < getRowCount(); j++) {
            for (int i = 0; i < getColCount(); i++)
            {
                for (int k = i + 1; k < getColCount(); k++) {
                    if (field[j][k] != 0)
                    {
                        if (field[j][i] == 0)
                        {
                            field[j][i] = field[j][k];
                            field[j][k] = 0;
                            wasAnyMoving = true;
                        } else {
                            if (field[j][k] == field[j][i]) {
                                field[j][i] += field[j][k];
                                field[j][k] = 0;
                                wasAnyMoving = true;
                            }
                            break;
                        }
                    }
                }
            }
        }
        if (wasAnyMoving) addRandomCell();
        return wasAnyMoving;
    }

    public boolean rightButtonPress() {
        boolean wasAnyMoving = false;
        for (int j = 0; j < getRowCount(); j++) {
            for (int i = getColCount() - 1; i >= 0; i--)
            {
                for (int k = i - 1; k >= 0; k--) {
                    if (field[j][k] != 0)
                    {
                        if (field[j][i] == 0)
                        {
                            field[j][i] = field[j][k];
                            field[j][k] = 0;
                            wasAnyMoving = true;
                        } else {
                            if (field[j][k] == field[j][i]) {
                                field[j][i] += field[j][k];
                                field[j][k] = 0;
                                wasAnyMoving = true;
                            }
                            break;
                        }
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
