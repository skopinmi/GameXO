
package ru.geekbrains.skopin.gamexo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class GameWindow extends JFrame {

    private static final int SIZE = 3;
    private static final GameWindow gameWindow = new GameWindow();

    private static final ImageIcon xo = new ImageIcon(GameWindow.class.getResource("/ru/geekbrains/skopin/gamexo/х.png"));
    private static final ImageIcon ox = new ImageIcon(GameWindow.class.getResource("/ru/geekbrains/skopin/gamexo/о.png"));
    private static JButton [] jButtons;
    private static JTextArea textArea;

    private static int x;
    private static int y;

    private static ImageIcon human = xo;

    private GameWindow () {

        setTitle("X/O и swing! GUI v0.2");
        setBounds(100, 100, 350, 400);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

/*
        текстовая панелька внизу окна
 */
        textArea = new JTextArea();
        add(textArea, BorderLayout.SOUTH);

/*
        панелька с кнопочками - игровое поле
 */
        JPanel center = new JPanel(new GridLayout(SIZE, SIZE));
        jButtons = new JButton[SIZE * SIZE];

/*
        Заморочился с созданием менюшки
        кода много, а сложного нет совсем
 */

        JPanel top = new JPanel(new FlowLayout());
        JMenuBar menu = new JMenuBar();
        JMenuItem newGame = new JMenuItem("Новая игра");
        menu.add(newGame);
        JMenu options = new JMenu("Настройки");
        JMenu difficulty = new JMenu("Сложность");
        JMenuItem light = new JMenuItem("Легкая");
        JMenuItem medium = new JMenuItem("Средняя");
        difficulty.add(light);
        difficulty.add(medium);
        JMenu dots = new JMenu("Выбор фишки");
        JMenuItem dotX = new JMenuItem("Крестики");
        JMenuItem dotO = new JMenuItem("Нолики");
        dots.add(dotX);
        dots.add(dotO);
        options.add(difficulty);
        options.add(dots);
        menu.add(options);
        JMenuItem help = new JMenuItem("Помощь");
        menu.add(help);
        top.add(menu);
/*
        парочка функций
        newGame / новая игра - очистка игрового поля
        exit / выход - выход из программы
        difficulty / сложность - выбор из 2-х вариантов (рандомный ход и "осмысленный")
        dots / выбол фишки - крестик/нолик изменяется надпись при нажатии
        help / помощь - выбрасывается дополнительное окно с текстом
 */

        dotX.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Main.setDotHuman("X");
                Main.setDotComp("O");
                human = xo;
                Main.newGameField(SIZE);
                cleanJButtons();
            }
        });
        dotO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Main.setDotHuman("O");
                Main.setDotComp("X");
                human = ox;
                Main.newGameField(SIZE);
                cleanJButtons();
            }
        });
        light.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Main.setDifficulty(true);
            }
        });
        medium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Main.setDifficulty(false);
            }
        });
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cleanJButtons();
            }
        });
        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                HelpWindow.createHelp();
            }
        });
/*
        Кнопочки игрового поля с действием
        изменяется надпись на кнопочке после нажатия
        в соответствии с выбором фишки
        в текстовой строке номер кнопки отображается
 */

        for (int i = 0; i < SIZE * SIZE; i++) {
            jButtons[i] = new JButton();
            center.add(jButtons[i]);
            int a = i;
            jButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    calculateXY(a);
                    String w = Main.getGameField(x, y);
                    if (w.equals("*")) {
                        jButtons[a].setIcon(human);
                        Main.putInGameField(x, y);
                        Main.setMyMove(false);
                    }
                    x = -1;
                    y = -1;
                }
            });
        }
/*
        все созданное разместил в окне
 */
        add(center, BorderLayout.CENTER);
//        add(down, BorderLayout.SOUTH);
        add(top, BorderLayout.NORTH);         // центральное расположение меню менюбар добавлено в панель
//        add(menu, BorderLayout.NORTH);     // обычное расположение меню без панели
    }
    public static GameWindow createGameWindow () {
        return gameWindow;
    }

    private static void calculateXY (int a) {
        if (a < SIZE ) {
            y = 0;
            x = a;
        } else if (a < SIZE * 2) {
            y = SIZE - 2;
            x = a - SIZE;
        } else {
            y = SIZE - 1;
            x = a - (SIZE * 2);
        }
    }

    public static void setJButton (int x, int y) {
        int a = -1;
        if (y == 0) {
            a = x;
        } else if (y == 1 ) {
            a = SIZE + x;
        } else {
            a = SIZE * 2 + x;
        }
        if(Main.getDotComp().equals("X")) {
            jButtons[a].setIcon(xo);
        } else {
            jButtons[a].setIcon(ox);
        }
    }

    private static  void cleanJButtons () {
        for (int i = 0; i < SIZE * SIZE; i++) {
            jButtons[i].setIcon(null);
            Main.newGameField(SIZE);
        }
    }

    public static void sayResult () {
        textArea.setText("Счет игры. Победы : " + Main.countOfHumanWin + " Поражения : "
                + Main.countOfAIWin + " Ничья : " + Main.countOfDraw);
    }
}
