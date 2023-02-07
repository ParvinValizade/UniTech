package com.unitech.unitech.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServiceRules {

    private JavaClasses importedClasses;

    @BeforeEach
    public void setUp(){
        importedClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("com.springboot.testing.archunit");
    }


    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        noClasses()
                .that().resideInAnyPackage("com.unitech.unitech.service..")
                .or().resideInAnyPackage("com.unitech.unitech.repository..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("com.unitech.unitech.controller..")
                .because("Services and repositories should not depend on web layer")
                .check(importedClasses);
    }
}
