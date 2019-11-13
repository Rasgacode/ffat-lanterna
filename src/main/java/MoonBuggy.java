import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import javax.swing.*;
import java.util.*;
import java.io.IOException;

public class MoonBuggy {
    public static void main(String[] args) throws IOException, InterruptedException {
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);


        TextGraphics tg = screen.newTextGraphics();
        screen.startScreen();
        //Ground
        printFirstLayer(tg);
        printSecondLayer(tg, screen);


        screen.refresh();
        screen.readInput();
        screen.stopScreen();
    }

    private static void printFirstLayer(TextGraphics tg) {
        for (int i = 0; i < 80; i++) {
            tg.putString(i, 20, "#");
        }
    }



    private static void printSecondLayer(TextGraphics tg, Screen screen) throws IOException, InterruptedException {
        /*Random r = new Random();
        String[] ground = new String[1500];
        int randomCounter = r.nextInt((17 - 7) + 1) + 7;

        for (int i = 0; i < ground.length; i++) {
            if (i == randomCounter) {
                ground[i] = " ";
                randomCounter += r.nextInt((17 - 7) + 1) + 7;
            } else {
                ground[i] = "#";
            }
        }
        String[] map = new String[80];
        for (int i = 0; i < map.length; i++) {
            if (i == randomCounter && i > 20) {
                map[i] = " ";
                randomCounter += r.nextInt((17 - 7) + 1) + 7;
            } else {
                map[i] = "#";
            }
        }
        int counter = 0;
        while (counter < 10) {
            for (int i = 0; i < ground.length - (map.length - 1); i++) {
                for (int j = map.length - 1; j > 0; j--) {
                    map[j] = map[j - 1];
                }
                map[0] = ground[i];

                for (int k = 0; k < map.length; k++) {
                    tg.putString(k, 19, map[k]);
                }
                Thread.sleep(100);
            }
            counter++;
        }*/
        Random r = new Random();
        String[] ground = new String[1500];
        int randomCounter = r.nextInt((17 - 8) + 1) + 8;

        for (int i = 0; i < ground.length; i++) {
            if (i == randomCounter) {
                ground[i] = " ";
                randomCounter += r.nextInt((17 - 8) + 1) + 8;
            } else {
                ground[i] = "#";
            }
        }
        String[] map = new String[80];
        for (int i = 0; i < map.length; i++) {
            if (i == randomCounter && i > 20) {
                map[i] = " ";
                randomCounter += r.nextInt((17 - 8) + 1) + 8;
            } else {
                map[i] = "#";
            }
        }
        for (int i = 0; i < ground.length - 81; i++) {
            for (int j = map.length - 1; j > 0; j--) {
                map[j] = map[j - 1];
            }
            map[0] = ground[i];
            int k = 0;
            for (String mapElement : map) {
                tg.putString(k, 19, mapElement);
                k++;
            }
            screen.refresh();
            Thread.sleep(200);

        }

    }
}
