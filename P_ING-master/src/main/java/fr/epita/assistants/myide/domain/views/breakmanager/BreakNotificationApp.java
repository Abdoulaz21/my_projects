package fr.epita.assistants.myide.domain.views.breakmanager;

import dorkbox.notify.Notify;
import fr.epita.java.log.Logged;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class BreakNotificationApp implements Logged {
    public void initOutside() {
        URL pathToFile = getClass().getClassLoader().getResource("notifications/sun.png");
        Image image = new ImageIcon(pathToFile).getImage();
        Notify.create()
                .title("Break Notification")
                .text("It's time for a little walk outside")
                .darkStyle()
                .hideAfter(5000)
                .image(image)
                .show();
    }

    public void initInside() {
        URL pathToFile = getClass().getClassLoader().getResource("notifications/coffee.png");
        Image image = new ImageIcon(pathToFile).getImage();
        Notify.create()
                .title("Break Notification")
                .text("It's time for a little coffee with your teammates at the cafeteria")
                .darkStyle()
                .hideAfter(5000)
                .image(image)
                .show();
    }
}
