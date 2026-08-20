package ao.bankinghub.arquitectura;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.library.dependencies.SliceRule;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

/**
 * Verifica a matriz de dependencias permitidas de
 * {@code docs/03-arquitectura/03-mapa-de-modulos.md} e os principios de
 * arquitectura de {@code docs/03-arquitectura/01-visao-arquitectura.md}.
 *
 * <p>Estes testes existem porque, sem verificacao automatica, a modularidade
 * degrada-se em acoplamento mutuo em poucos meses e perde-se a opcao de
 * extrair um modulo mais tarde (ADR-0002). Convencao nao verificada e
 * convencao violada.
 */
@DisplayName("Arquitectura modular — matriz de dependencias e pureza do dominio")
class ArquitecturaModularTest {

    private static JavaClasses classes;

    /** Modulos de dominio. Nenhum deles pode depender de outro (regra estrutural 1). */
    private static final String[] MODULOS_DOMINIO = {
            "customer", "document", "kyc", "contract", "funding", "account"
    };

    @BeforeAll
    static void importar() {
        classes = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("ao.bankinghub");
    }

    // ------------------------------------------------------------------
    // Regra estrutural 1 — nenhum modulo de dominio depende de outro
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Nenhum modulo de dominio depende de outro modulo de dominio")
    void modulosDeDominioNaoSeConhecem() {
        for (String origem : MODULOS_DOMINIO) {
            for (String destino : MODULOS_DOMINIO) {
                if (origem.equals(destino)) {
                    continue;
                }
                noClasses()
                        .that().resideInAPackage("ao.bankinghub." + origem + "..")
                        .should().dependOnClassesThat()
                        .resideInAPackage("ao.bankinghub." + destino + "..")
                        .because("bh-" + origem + " nao pode depender de bh-" + destino
                                + ": modulos de dominio comunicam pelo orquestrador ou por evento, "
                                + "para que as fronteiras sejam reais e nao decorativas")
                        .check(classes);
            }
        }
    }

    // ------------------------------------------------------------------
    // Regra estrutural 3 — archive e audit sao destinos, nunca origens
    // ------------------------------------------------------------------

    @Test
    @DisplayName("bh-archive nao invoca modulos de dominio")
    void arquivoNaoInvocaDominio() {
        for (String modulo : MODULOS_DOMINIO) {
            noClasses()
                    .that().resideInAPackage("ao.bankinghub.archive..")
                    .should().dependOnClassesThat()
                    .resideInAPackage("ao.bankinghub." + modulo + "..")
                    .because("bh-archive recebe evidencias; nao invoca dominio")
                    .check(classes);
        }
    }

    @Test
    @DisplayName("bh-audit nao depende de nenhum modulo alem de common e tenant")
    void auditoriaNaoDependeDeDominio() {
        noClasses()
                .that().resideInAPackage("ao.bankinghub.audit..")
                .should().dependOnClassesThat()
                .resideInAnyPackage(
                        "ao.bankinghub.customer..", "ao.bankinghub.document..",
                        "ao.bankinghub.kyc..", "ao.bankinghub.contract..",
                        "ao.bankinghub.funding..", "ao.bankinghub.account..",
                        "ao.bankinghub.archive..", "ao.bankinghub.orchestration..")
                .because("bh-audit e destino terminal: recebe eventos e nada invoca")
                .check(classes);
    }

    // ------------------------------------------------------------------
    // Regra estrutural 4 — bh-common sem logica de negocio
    // ------------------------------------------------------------------

    @Test
    @DisplayName("bh-common nao depende de nenhum modulo do Banking Hub")
    void commonNaoDependeDeNinguem() {
        noClasses()
                .that().resideInAPackage("ao.bankinghub.common..")
                .should().dependOnClassesThat()
                .resideInAnyPackage(
                        "ao.bankinghub.tenant..", "ao.bankinghub.customer..",
                        "ao.bankinghub.document..", "ao.bankinghub.kyc..",
                        "ao.bankinghub.contract..", "ao.bankinghub.funding..",
                        "ao.bankinghub.account..", "ao.bankinghub.archive..",
                        "ao.bankinghub.audit..", "ao.bankinghub.notification..",
                        "ao.bankinghub.orchestration..", "ao.bankinghub.bff..")
                .because("bh-common e a base: se depende de um modulo, o tipo esta no lugar errado")
                .check(classes);
    }

    // ------------------------------------------------------------------
    // Principio 4 — dominio puro
    // ------------------------------------------------------------------

    @Test
    @DisplayName("O pacote domain nao depende de Spring")
    void dominioNaoDependeDeSpring() {
        noClasses()
                .that().resideInAPackage("..domain..")
                .should().dependOnClassesThat().resideInAPackage("org.springframework..")
                .because("o modelo de dominio e independente de infra-estrutura (principio 4)")
                .check(classes);
    }

    @Test
    @DisplayName("O pacote domain nao depende de JPA nem de Jakarta Persistence")
    void dominioNaoDependeDeJpa() {
        noClasses()
                .that().resideInAPackage("..domain..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("jakarta.persistence..", "javax.persistence..", "org.hibernate..")
                .because("persistencia e detalhe de infra-estrutura, nao de dominio")
                .check(classes);
    }

    // ------------------------------------------------------------------
    // Regra de dependencia interna: api -> application -> domain
    // ------------------------------------------------------------------

    @Test
    @DisplayName("O dominio nao depende de application, api nem infrastructure")
    void dominioNaoDependeDasCamadasExteriores() {
        noClasses()
                .that().resideInAPackage("..domain..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("..application..", "..api..", "..infrastructure..")
                .because("a direccao da dependencia e api -> application -> domain")
                .check(classes);
    }

    @Test
    @DisplayName("A camada application nao depende de api nem de infrastructure")
    void applicationNaoDependeDeApiNemInfra() {
        noClasses()
                .that().resideInAPackage("..application..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("..api..", "..infrastructure..")
                .because("application define portos; infrastructure implementa-os")
                .check(classes);
    }

    @Test
    @DisplayName("Anotacoes de configuracao Spring vivem apenas em infrastructure.config")
    void configuracaoApenasEmInfrastructureConfig() {
        classes()
                .that().areAnnotatedWith("org.springframework.context.annotation.Configuration")
                .should().resideInAnyPackage("..infrastructure.config..", "ao.bankinghub.app..")
                .because("configuracao concentrada torna a composicao inspeccionavel")
                .check(classes);
    }

    // ------------------------------------------------------------------
    // Ausencia de ciclos
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Nao existem dependencias circulares entre modulos")
    void semCiclosEntreModulos() {
        SliceRule regra = SlicesRuleDefinition.slices()
                .matching("ao.bankinghub.(*)..")
                .should().beFreeOfCycles();
        regra.because("ciclo entre modulos elimina a possibilidade de extraccao futura")
             .check(classes);
    }

    @Test
    @DisplayName("Nao existem dependencias circulares entre camadas de um modulo")
    void semCiclosEntreCamadas() {
        SlicesRuleDefinition.slices()
                .matching("ao.bankinghub.(*).(*)..")
                .should().beFreeOfCycles()
                .check(classes);
    }
}
