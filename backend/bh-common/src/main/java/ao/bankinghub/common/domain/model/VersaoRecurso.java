package ao.bankinghub.common.domain.model;

import java.time.Instant;
import java.util.Objects;

/**
 * Versao de um recurso versionado: modelo BPMN, decisao DMN ou minuta contratual.
 *
 * <p>Persistida na instancia do Pedido de Abertura. Sem isto e impossivel
 * responder a uma autoridade competente sobre uma abertura de ha sete anos:
 * a pergunta "que regra aplicaram a este pedido?" tem de ter resposta
 * consultavel, e nao reconstruida por inferencia.
 *
 * <p>Sustenta o invariante {@code INV-09} (toda decisao automatica referencia a
 * versao da decisao DMN que a produziu) e a regra {@code BR-INF-05} (a versao da
 * minuta contratual nao e alterada por publicacoes posteriores).
 */
@Regulatorio(
        value = {"REG-RET-02"},
        fonte = "Aviso n.o 1/23, Anexo I, I.1 ponto 3; Art. 16.o",
        regra = {"BR-RSK-02", "BR-INF-05", "BR-RET-05"},
        invariante = {"INV-09"})
public record VersaoRecurso(
        TipoRecurso tipo,
        String identificador,
        String versao,
        Instant publicadaEm) {

    public enum TipoRecurso {
        /** Modelo de processo BPMN 2.0. */
        PROCESSO_BPMN,
        /** Decisao DMN. */
        DECISAO_DMN,
        /** Minuta de Condicoes Gerais, Particulares ou Ficha Tecnica Informativa. */
        MINUTA_CONTRATUAL
    }

    public VersaoRecurso {
        Objects.requireNonNull(tipo, "tipo de recurso e obrigatorio");
        Objects.requireNonNull(identificador, "identificador e obrigatorio");
        Objects.requireNonNull(versao, "versao e obrigatoria");
        Objects.requireNonNull(publicadaEm, "data de publicacao e obrigatoria");
    }

    @Override
    public String toString() {
        return tipo + "/" + identificador + "@" + versao;
    }
}
