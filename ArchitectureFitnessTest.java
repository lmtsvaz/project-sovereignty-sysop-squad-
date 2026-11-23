package com.sovereignty.sysop.architecture;

import com.sovereignty.sysop.domain.ports.ILegacyInventoryService;
import com.sovereignty.sysop.domain.states.TicketState;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "com.sovereignty.sysop", importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchitectureFitnessTest {

    @ArchTest
    static final ArchRule dominio_nao_deve_depender_de_infraestrutura_ou_frameworks =
        noClasses()
            .that().resideInAPackage("..domain..")
            .should().dependOnClassesThat()
            .resideInAnyPackage("..infrastructure..", "org.springframework..", "io.github.resilience4j..");

    @ArchTest
    static final ArchRule adaptadores_acl_devem_implementar_porta_de_dominio =
        classes()
            .that().resideInAPackage("..infrastructure.acl..")
            .and().haveSimpleNameEndingWith("Adapter")
            .should().implement(ILegacyInventoryService.class);

    @ArchTest
    static final ArchRule classes_de_estado_devem_implementar_ticket_state =
        classes()
            .that().resideInAPackage("..domain.states..")
            .and().areNotInterfaces()
            .should().implement(TicketState.class)
            .andShould().haveSimpleNameEndingWith("State");
}
