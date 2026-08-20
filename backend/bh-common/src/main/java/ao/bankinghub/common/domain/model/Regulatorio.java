package ao.bankinghub.common.domain.model;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marca um tipo, metodo ou teste como existindo por imposicao normativa,
 * identificando o requisito da matriz {@code REG-*}.
 *
 * <p>Esta anotacao e o elo entre o codigo e
 * {@code docs/01-regulatorio/03-matriz-requisitos-regulatorios.md}. A ferramenta
 * {@code tools/trace-check} le estas anotacoes e falha o build quando existe
 * requisito de escopo {@code S} sem implementacao nem teste nomeado, ou quando
 * uma anotacao referencia um identificador inexistente.
 *
 * <p>Regra de governacao da arquitectura de processos: actividade sem
 * {@code REG-*} nem justificacao de valor e candidata a eliminacao.
 *
 * <p>Exemplo:
 * <pre>{@code
 * @Regulatorio(
 *     value = "REG-INF-01",
 *     fonte = "Aviso n.o 1/23, Art. 5.o n.o 1",
 *     regra = "BR-INF-01")
 * public void celebrar(Instant timestampCelebracao) { ... }
 * }</pre>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
public @interface Regulatorio {

    /** Identificadores da matriz de requisitos, ex. {@code "REG-INF-01"}. */
    String[] value();

    /** Disposicao normativa de origem, ex. {@code "Aviso n.o 1/23, Art. 5.o n.o 1"}. */
    String fonte() default "";

    /** Identificadores do repositorio de regras, ex. {@code "BR-INF-01"}. */
    String[] regra() default {};

    /** Identificadores de invariante do TO-BE, ex. {@code "INV-02"}. */
    String[] invariante() default {};
}
