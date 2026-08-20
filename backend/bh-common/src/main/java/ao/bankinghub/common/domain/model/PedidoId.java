package ao.bankinghub.common.domain.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Identidade do Pedido de Abertura.
 *
 * <p>E a raiz de agregacao de todo o dossie e a chave de correlacao unica ao
 * longo de todo o processo: rastreio distribuido, registo de auditoria,
 * indice de recuperacao do arquivo e chave de idempotencia derivam deste valor.
 */
public record PedidoId(UUID valor) {

    public PedidoId {
        Objects.requireNonNull(valor, "pedidoId nao pode ser nulo");
    }

    public static PedidoId novo() {
        return new PedidoId(UUID.randomUUID());
    }

    public static PedidoId de(String texto) {
        return new PedidoId(UUID.fromString(texto));
    }

    @Override
    public String toString() {
        return valor.toString();
    }
}
