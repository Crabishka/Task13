import util.DrawUtils;
import util.JTableUtils;
import util.SwingUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainForm extends JFrame {
    private JPanel MainPanel;
    private JTable GameField;
    private JLabel GameStatus;
    private JScrollPane JMainPanel;

    private static final int DEFAULT_COL_COUNT = 4;
    private static final int DEFAULT_ROW_COUNT = 4;
    private static final int DEFAULT_COLOR_COUNT = 10;

    private static final int DEFAULT_GAP = 8;
    private static final int DEFAULT_CELL_SIZE = 200;
    // цвета клеточек
    private static final Color[] COLORS = {
            Color.BLUE,
            Color.RED,
            Color.YELLOW,
            Color.GREEN,
            Color.MAGENTA,
            Color.CYAN,
            Color.ORANGE,
            Color.PINK,
            Color.WHITE,
            Color.GRAY
    };

    private GameParams params = new GameParams(DEFAULT_ROW_COUNT, DEFAULT_COL_COUNT, DEFAULT_COLOR_COUNT);
    private Game game = new Game();
    // таймер
    private int time = 0;
    private Timer timer = new Timer(1000, e -> {
        time++;
        this.GameStatus.setText("Прошло времени (секунд): " + time);
    });

    public MainForm() {
        this.setTitle("2048");
        this.setContentPane(MainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        this.pack();
   //     GameField.setFocusable(true);


        SwingUtils.setShowMessageDefaultErrorHandler();

        GameField.setRowHeight(DEFAULT_CELL_SIZE);
        JTableUtils.initJTableForArray(GameField, DEFAULT_CELL_SIZE, false, false, false, false);
        GameField.setIntercellSpacing(new Dimension(0, 0));
        GameField.setEnabled(false);

        GameField.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            final class DrawComponent extends Component {
                private int row = 0, column = 0;

                @Override
                public void paint(Graphics gr) {
                    Graphics2D g2d = (Graphics2D) gr;
                    int width = getWidth() - 2;
                    int height = getHeight() - 2;
                    paintCell(row, column, g2d, width, height);
                }
            }


            DrawComponent comp = new DrawComponent();

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                comp.row = row;
                comp.column = column;
                return comp;
            }
        });

        newGame();
        updateWindowSize();
        updateView();

        MainPanel.setFocusable(true);
        MainPanel.requestFocusInWindow(); // без этого не работает

        MainPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_UP) {
                    game.upButtonPress();
                    updateView();
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    game.downButtonPress();
                    updateView();
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    game.leftButtonPress();
                    updateView();
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    game.rightButtonPress();
                    updateView();
                }

            }
        });


    }


    private void updateWindowSize() {
        int menuSize = this.getJMenuBar() != null ? this.getJMenuBar().getHeight() : 0;
        SwingUtils.setFixedSize(
                this,
                GameField.getWidth() + 2 * DEFAULT_GAP + 60,
                GameField.getHeight() + MainPanel.getY() + GameStatus.getHeight() +
                        menuSize + DEFAULT_GAP + 2 * DEFAULT_GAP + 60
        );
        this.setMaximumSize(null);
        this.setMinimumSize(null);
    }

    private Font font = null;
    private Font getFont(int size) {
        if (font == null || font.getSize() != size) {
            font = new Font("Comic Sans MS", Font.BOLD, size);
        }
        return font;
    }
    // рисуем клеточку
    private void paintCell(int row, int column, Graphics2D g2d, int cellWidth, int cellHeight) {
        int cellValue = game.getCell(row, column);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (cellValue <= 0) {
            return;
        }
        Color color = COLORS[(int)((Math.log(cellValue+1) / Math.log(2.0))) % 10];

        int size = Math.min(cellWidth, cellHeight);
        int bound = (int) Math.round(size * 0.1);

        g2d.setColor(color);
        g2d.fillRoundRect(bound, bound, size - 2 * bound, size - 2 * bound, bound * 3, bound * 3);
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawRoundRect(bound, bound, size - 2 * bound, size - 2 * bound, bound * 3, bound * 3);

        g2d.setFont(getFont(size - 8 * bound));
        g2d.setColor(DrawUtils.getContrastColor(color));
        DrawUtils.drawStringInCenter(g2d, font, "" + cellValue, 0, 0, cellWidth, (int) Math.round(cellHeight * 0.95));

    }

    private void updateView() {
        GameField.repaint();
    }

    private void newGame() {
        game.newGame(params.getRowCount(), params.getColCount(), params.getColorCount());
        JTableUtils.resizeJTable(GameField,
                game.getRowCount(), game.getColCount(),
                GameField.getRowHeight(), GameField.getRowHeight()
        );
        time = 0;
        timer.start();
        updateView();
        game.addRandomCell();
        game.addRandomCell();
    }
}
