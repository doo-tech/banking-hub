package ao.bankinghub.common.domain.model;

import java.util.Objects;

/**
 * Chave de idempotencia de uma operacao de escrita.
 *
 * <p>O motor de processos garante entrega <i>ao menos uma vez</i>: uma tarefa de
 * servico pode ser executada mais de uma vez por retentativa, reinicio de
 * trabalhador ou reprocessamento de mensagem. No dominio bancario isto tem
 * consequencia concreta — {@code account.criarConta} executado duas vezes cria
 * duas contas com dois IBAN para o mesmo cliente.
 *
 * <p>A chave e derivada deterministicamente de
 * {@code (pedidoId, nomeTarefa, tentativaLogica)}, para que a mesma operacao
 * logica produza sempre a mesma chave, independentemente de quantas vezes seja
 * entregue. Ver ADR-0010.
 */
public record ChaveIdempotencia(String valor) {

    public ChaveIdempotencia {
        Objects.requireNonNull(valor, "chave de idempotencia nao pode ser nula");
        if (valor.isBlank()) {
            throw new IllegalArgumentException("chave de idempotencia nao pode ser vazia");
        }
    }

    /**
     * Deriva a chave canonica de uma tarefa.
     *
     * @param pedidoId        identidade do Pedido de Abertura
     * @param nomeTarefa      nome da tarefa, na convencao {@code <modulo>.<accao>}
     * @param tentativaLogica tentativa de negocio — nao a tentativa tecnica.
     *                        Uma retentativa por falha de rede mantem o mesmo
     *                        valor; um novo pedido do cliente apos rejeicao
     *                        documental incrementa-o.
     */
    public static ChaveIdempotencia de(PedidoId pedidoId, String nomeTarefa, int tentativaLogica) {
        Objects.requireNonNull(pedidoId, "pedidoId e obrigatorio");
        Objects.requireNonNull(nomeTarefa, "nomeTarefa e obrigatorio");
        if (tentativaLogica < 0) {
            throw new IllegalArgumentException("tentativaLogica nao pode ser negativa");
        }
        return new ChaveIdempotencia(pedidoId + ":" + nomeTarefa + ":" + tentativaLogica);
    }

    @Override
    public String toString() {
        return valor;
    }
}
