package com.epita.assistants.yakamon.arch;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

/**
 * Arch tests.
 *
 * @author dumeig_a (antoine.dumeige@epita.fr)
 * @since 1.0
 */
@AnalyzeClasses(packages = "com.epita")
public class ModelTest {

    @ArchTest
    public static final ArchRule test_000_modelPackageName =
            classes().that().areAnnotatedWith(Model.class).should()
                    .resideInAPackage("..model..");
    @ArchTest
    public static final ArchRule test_001_modelClassSuffix =
            classes().that().areAnnotatedWith(Model.class).should()
                    .haveSimpleNameEndingWith("Model");
    @ArchTest
    public static final ArchRule test_002_nonModelClassSuffix =
            noClasses().that().areNotAnnotatedWith(Model.class).and()
                    .areNotAssignableTo(Model.class).should()
                    .haveSimpleNameEndingWith("Model");

    @ArchTest
    public static final ArchRule test_003_noReferencesToEntities =
            noClasses().that().areAnnotatedWith(Model.class).should()
                    .dependOnClassesThat().areAnnotatedWith(Entity.class);

    @ArchTest
    public static final ArchRule test_004_noReferencesToDtos = noClasses()
            .that().areAnnotatedWith(Model.class).should().dependOnClassesThat()
            .areAnnotatedWith(DtoRequest.class).orShould().dependOnClassesThat()
            .areAnnotatedWith(DtoResponse.class);

}
