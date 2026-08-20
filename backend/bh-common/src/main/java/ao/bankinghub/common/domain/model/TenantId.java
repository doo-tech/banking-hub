package ao.bankinghub.common.domain.model;

import java.util.Objects;

/**
 * Identificador da Instituicao Financeira Bancaria — o <i>tenant</i>.
 *
 * <p>Obrigatorio em todo agregado, todo esquema e toda mensagem, mesmo em
 * instalacao dedicada a uma unica instituicao (ADR-0008). Deriva sempre do
 * contexto de autenticacao e nunca e aceito como parametro de entrada da API:
 * campo opcional e campo esquecido, e o isolamento entre instituicoes tem de
 * ser estrutural desde a primeira linha.
 */
public record TenantId(String valor) {

    public TenantId {
        Objects.requireNonNull(valor, "tenantId nao pode ser nulo");
        if (valor.isBlank()) {
            throw new IllegalArgumentException("tenantId nao pode ser vazio");
        }
    }

    @Override
    public String toString() {
        return valor;
    }
}
