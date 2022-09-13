import fr.epita.assistants.myide.domain.entity.AspectClass;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AspectTest {
    @Test
    void basicAspectTest(){
        AspectClass aspect = new AspectClass(Mandatory.Aspects.ANY);
        List<Feature> features = aspect.getFeatureList();

        assert (features.size() == 3);
    }
}
