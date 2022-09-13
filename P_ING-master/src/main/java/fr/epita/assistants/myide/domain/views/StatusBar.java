package fr.epita.assistants.myide.domain.views;

import fr.epita.assistants.myide.domain.entity.Aspect;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import org.eclipse.jgit.api.Git;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.simpleicons.SimpleIcons;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class StatusBar extends JPanel {
    private final Color bg = new Color(2, 62, 125);
    private final Color fg = new Color(151, 157, 172);

    private final JLabel notificationsContainer;
    private JLabel gitActualBranchContainer;

    public StatusBar() {
        this.setBackground(bg);
        this.setLayout(new BorderLayout());

        FontIcon notificationsIcon = FontIcon.of(BootstrapIcons.BOOKMARKS, 16, fg);
        notificationsContainer = new JLabel();
        notificationsContainer.setIcon(notificationsIcon);
        notificationsContainer.setForeground(fg);

        gitActualBranchContainer = new JLabel();
      
        this.add(gitActualBranchContainer, BorderLayout.EAST);
        this.add(notificationsContainer, BorderLayout.WEST);
    }

    public void setGitActualBranch()
    {
        boolean isGitProject = false;
        if (MainFrame.getInstance().isProjectLoad())
        {
            for (var aspect : MainFrame.getInstance().getLoadedProject().getAspects())
                if (aspect.getType().equals(Mandatory.Aspects.GIT))
                {
                    isGitProject = true;
                    break;
                }
        }
        if (isGitProject) {
            FontIcon gitActualBranchIcon = FontIcon.of(SimpleIcons.GIT, 16, fg);
            gitActualBranchContainer.setIcon(gitActualBranchIcon);
            gitActualBranchContainer.setForeground(fg);
            Git git;
            try {
                git = Git.open(new File(MainFrame.getInstance().getLoadedProject().getRootNode().getPath().toString() + "/.git"));
                gitActualBranchContainer.setText(git.getRepository().getBranch());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.add(gitActualBranchContainer, BorderLayout.EAST);
        }
    }

    public void setNotificationMessage(String message) {
        this.notificationsContainer.setText(message);

        Timer timer = new Timer(4000,
                e -> MainFrame.getInstance().getStatusBar().notificationsContainer.setText(null));
        timer.setRepeats(false);
        timer.start();
    }
}
