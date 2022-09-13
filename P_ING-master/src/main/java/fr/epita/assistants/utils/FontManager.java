package fr.epita.assistants.utils;

import fr.epita.java.log.Logged;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class FontManager implements Logged {

    private Font comicSansMS;
    private Font arial;
    private Font joker;

    public FontManager() {
        create_comic();
        create_arial();
        create_joker();
    }

    public Font getComicSansMS() {
        return comicSansMS;
    }

    public Font getArial() {
        return arial;
    }

    public Font getJoker() {
        return joker;
    }

    public void create_joker() {
        try {
            InputStream stream = getClass().getClassLoader().getResourceAsStream("Police/Jokerman.ttf");
            joker = Font.createFont(Font.TRUETYPE_FONT, stream);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(joker);
            joker = joker.deriveFont(Font.PLAIN, 16);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    public void create_arial() {
        try {
            InputStream stream = getClass().getClassLoader().getResourceAsStream("Police/arial.ttf");
            arial = Font.createFont(Font.TRUETYPE_FONT, stream);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(arial);
            arial = arial.deriveFont(Font.PLAIN, 16);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    public void create_comic() {
        try {
            InputStream stream = getClass().getClassLoader().getResourceAsStream("Police/comic-sans-ms.ttf");
            comicSansMS = Font.createFont(Font.TRUETYPE_FONT, stream);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(comicSansMS);
            comicSansMS = comicSansMS.deriveFont(Font.PLAIN, 16);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    public Font getFontFromIndex(int index) {
        return switch (index) {
            case 0 -> comicSansMS;
            case 1 -> arial;
            case 2 -> joker;
            default -> throw new RuntimeException("Invalid Font index.");
        };
    }
}
