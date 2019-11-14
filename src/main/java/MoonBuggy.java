import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.AbstractTextGUI;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.util.concurrent.CompletableFuture;
import java.util.*;
import java.io.IOException;

public class MoonBuggy {
    static boolean keepRunning = true;
    static boolean jumpStatus = false;

    public static void main(String[] args) throws IOException, InterruptedException {
        String wheelStatus = "/";

        String[][] originalPos = new String[][] {{"(", "69", "18"}, {wheelStatus, "70", "18"},{")", "71", "18"},
                {"-", "72", "18"}, {"(", "73", "18"}, {wheelStatus, "74", "18"}, {")", "75", "18"},
                {"O", "72", "17"}, {"m", "73", "17"}, {"m", "74", "17"}};

        String[][] jumpOne = new String[][] {{"(", "69", "17"}, {wheelStatus, "70", "17"}, {")", "71", "17"},
                {"-", "72", "17"}, {"(", "73", "17"}, {wheelStatus, "74", "17"}, {")", "75", "17"},
                {"O", "72", "16"}, {"m", "73", "16"}, {"m", "74", "16"}};

        String[][] jumpTwo = new String[][] {{"(", "69", "16"}, {wheelStatus, "70", "16"}, {")", "71", "16"},
                {"-", "72", "16"}, {"(", "73", "16"}, {wheelStatus, "74", "16"}, {")", "75", "16"},
                {"O", "72", "15"}, {"m", "73", "15"}, {"m", "74", "15"}};

        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);

        TextGraphics tg = screen.newTextGraphics();
        screen.startScreen();

        // Car (jump)
        CompletableFuture.runAsync(() -> {
            MoonBuggy.clear(tg);
            MoonBuggy.carPos(tg, originalPos);
            while (MoonBuggy.keepRunning) {
                try {
                    KeyStroke keyPressed = terminal.readInput();
                    switch (keyPressed.getKeyType()) {
                        case Escape:
                            keepRunning = false;
                            break;
                        case ArrowUp:
                            jumpStatus = true;
                            jump(tg, screen, originalPos, jumpOne, jumpTwo);
                            jumpStatus = false;
                        }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

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
            if (!jumpStatus) {
                if (screen.getFrontCharacter(70, 18).getCharacter() == '/') {
                    tg.putString(70, 18, "|");
                    tg.putString(74, 18, "|");
                } else if (screen.getFrontCharacter(70, 18).getCharacter() == '|'){
                    tg.putString(70, 18, "\\");
                    tg.putString(74, 18, "\\");
                } else if (screen.getFrontCharacter(70, 18).getCharacter() == '\\') {
                    tg.putString(70, 18, "-");
                    tg.putString(74, 18, "-");
                } else if (screen.getFrontCharacter(70, 18).getCharacter() == '-') {
                    tg.putString(70, 18, "/");
                    tg.putString(74, 18, "/");
                }
            }
            screen.refresh();
            Thread.sleep(100);

        }

    }

    private static void carPos(TextGraphics tg, String[][] pos){
        for (String[] part: pos
             ) {
            tg.putString(Integer.parseInt(part[1]), Integer.parseInt(part[2]), part[0]);
        }
    }
    private static void clear(TextGraphics tg) {
        for (int i = 15; i < 19; i++) {
            for (int j = 69; j < 76; j++) {
                tg.putString(j, i, " ");
            }
        }
    }
    private static void jump(TextGraphics tg, Screen screen, String[][] oPos, String[][] JOPos, String[][] JTPos
                             ) throws IOException, InterruptedException{
        MoonBuggy.carPos(tg, oPos);
        screen.refresh();
        Thread.sleep(200);
        MoonBuggy.clear(tg);
        MoonBuggy.carPos(tg, JOPos);
        screen.refresh();
        Thread.sleep(200);
        MoonBuggy.clear(tg);
        MoonBuggy.carPos(tg, JTPos);
        screen.refresh();
        Thread.sleep(400);
        MoonBuggy.clear(tg);
        MoonBuggy.carPos(tg, JOPos);
        screen.refresh();
        Thread.sleep(200);
        MoonBuggy.clear(tg);
        MoonBuggy.carPos(tg, oPos);
        screen.refresh();
        Thread.sleep(100);
    }
}
