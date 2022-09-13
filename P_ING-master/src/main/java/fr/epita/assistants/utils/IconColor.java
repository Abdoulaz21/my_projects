package fr.epita.assistants.utils;

import fr.epita.assistants.myide.domain.views.MainFrame;

import java.awt.*;

public class IconColor {
    public static Color getIconColor() {
       if (MainFrame.getInstance().isDarkTheme())
           return new Color(255, 255, 255);
       else
           return new Color(0, 0, 0);
    }
}
