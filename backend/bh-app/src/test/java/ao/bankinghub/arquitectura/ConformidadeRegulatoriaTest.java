package ao.bankinghub.arquitectura;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

/**
 * Verifica os principios de arquitectura que sustentam directamente requisitos
 * do Aviso n.o 1/23 e as decisoes ADR-0008, ADR-0009 e ADR-0010.
 *
 * <p>A verificacao de integridade da matriz de rastreabilidade propriamente dita
 * (norma -> regra -> processo -> modulo -> teste) e executada por
 * {@code tools/trace-check}, que le a documentacao e falha o build quando
 * existe requisito de escopo S sem teste nomeado.
 */
@DisplayName("Conformidade regulatoria — principios verificaveis em codigo")
class ConformidadeRegulatoriaTest {

    private static JavaClasses classes;

    @BeforeAll
    static void importar() {
        classes = new ClassFileImporter().importPackages("ao.bankinghub");
    }

    // ADR-0008 — isolamento multi-instituicao

    @Test
    @DisplayName("Nenhum recurso REST aceita tenantId como parametro de entrada")
    void tenantIdNuncaVemDoCliente() {
        noClasses()
                .that().resideInAPackage("..api.rest..")
                .should().haveSimpleNameContaining("TenantParam")
                .because("o tenantId deriva do contexto de autenticacao e nunca da entrada "
                        + "da API, para que o isolamento entre instituicoes nao dependa do cliente "
                        + "(ADR-0008)")
                .check(classes);
    }

    // ADR-0009 — dados pessoais fora das variaveis de processo

    @Test
    @DisplayName("Job workers nao dependem de agregados de dominio de outros modulos")
    void workersUsamReferenciasNaoAgregados() {
        noClasses()
                .that().resideInAPackage("..orchestration.infrastructure.client..")
                .should().dependOnClassesThat().resideInAPackage("..domain.model..")
                .because("as variaveis de processo transportam referencias e identificadores, "
                        + "nunca dados pessoais (ADR-0009)")
                .check(classes);
    }

    // ADR-0010 — idempotencia universal

    @Test
    @DisplayName("Casos de uso de comando residem no pacote application.command")
    void comandosNoPacoteCorrecto() {
        classes()
                .that().haveSimpleNameEndingWith("Command")
                .should().resideInAPackage("..application.command..")
                .because("todo comando e fronteira transaccional e exige chave de "
                        + "idempotencia (ADR-0010); concentra-los torna a regra verificavel")
                .check(classes);
    }

    // Principio 6 — evidencia como cidada de primeira classe

    @Test
    @DisplayName("Objectos de valor de evidencia sao imutaveis")
    void evidenciasSaoImutaveis() {
        classes()
                .that().haveSimpleNameStartingWith("Evidencia")
                .should().haveOnlyFinalFields()
                .because("evidencia alteravel nao prova nada (REG-INF-02, REG-RET-02)")
                .check(classes);
    }
}
