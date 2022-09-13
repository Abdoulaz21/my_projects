package fr.epita.assistants;

import fr.epita.assistants.myide.domain.service.NodeServiceClass;
import fr.epita.assistants.myide.domain.service.ProjectService;
import fr.epita.assistants.myide.domain.service.ProjectServiceClass;
import fr.epita.assistants.myide.domain.views.MainFrame;
import fr.epita.assistants.myide.domain.views.MainMenu;
import fr.epita.assistants.myide.domain.views.filemenu.SettingsMenu;
import fr.epita.assistants.utils.Given;

import java.nio.file.Path;

/**
 * Starter class, we will use this class and the init method to get a
 * configured instance of {@link ProjectService}.
 */
@Given(overridden = false)
public class MyIde {

    /**
     * Init method. It must return a fully functional implementation of {@link ProjectService}.
     *
     * @return An implementation of {@link ProjectService}.
     */
    public static ProjectService init(final Configuration configuration) {
        return new ProjectServiceClass(new NodeServiceClass());
    }

    /**
     * Record to specify where the configuration of your IDE
     * must be stored. Might be useful for the search feature.
     */
    public static record Configuration(Path indexFile,
                                       Path tempFolder) {}
}
