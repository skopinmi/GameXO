package ru.geekbrains.skopin.gamexo;

import javax.swing.*;
import java.awt.*;

public class MassageWindow extends JFrame{

    private static MassageWindow massageWindow;
    private static JTextPane textPane;

    private MassageWindow(String text) {
        setTitle("Результат игры.");
        setSize( 200, 100);
        setLocation(100, 200);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        textPane = new JTextPane();
        textPane.setText(text);
        add(textPane, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void createMassageWindow (String text) {
        if (massageWindow == null) {
            massageWindow = new MassageWindow(text);
        }
        textPane.setText(text);
        massageWindow.setVisible(true);
    }
}
