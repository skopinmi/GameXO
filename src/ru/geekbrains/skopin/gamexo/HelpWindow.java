
package ru.geekbrains.skopin.gamexo;

import javax.swing.*;

public class HelpWindow extends JFrame {

    private static HelpWindow helpWindow;
/*
    окно помощи с текстом (он редактируется. но не сохраняется)
 */
    private HelpWindow() {
        setTitle("Помощь. GUI version 0.1");
        setBounds(10, 10, 400, 400);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JTextPane textPane = new JTextPane();
        textPane.setText(text());
        add(textPane);
        setVisible(true);
    }

    public static void createHelp () {
        if (helpWindow == null) {
            helpWindow = new HelpWindow();
        }
        helpWindow.setVisible(true);
    }

    private static String text () {
        String text = "Google Вам в помощь!";
        return text;
    }
}
