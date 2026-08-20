# ADR-0007 — Interpretação do Art. 3.º n.º 7 sobre fundos de terceiro

**Estado:** Aceite · **Data:** 2026-08-19 · **Decisor:** Chefe de Compliance KYC (com Arquitecto)

## Contexto

O Art. 3.º n.º 7 do Aviso n.º 1/23 dispõe:

> "A entrega inicial de fundos prevista **no número anterior**, com origem em conta titulada por pessoa diferente do cliente, apenas deve ser aceite mediante a apresentação de uma justificação credível."

O "número anterior" é o n.º 6, que trata de abertura de conta por entidades terceiras mandatadas — e **não** de entrega inicial de fundos. A entrega inicial de fundos está regulada no Art. 4.º n.º 4, por remissão expressa ao n.º 5 do Art. 3.º (abertura sem presença física).

Existe, portanto, uma remissão que não fecha. É preciso decidir como implementar, e registar a decisão, porque a escolha tem consequência prática directa.

## Interpretações possíveis

| # | Leitura | Âmbito de aplicação |
|---|---|---|
| A | Literal — aplica-se apenas a fundos de contas abertas por entidade terceira mandatada | Muito estreito; deixaria a maioria dos casos de fundos de terceiro sem exigência de justificação |
| B | Remissão pretendida ao n.º 5 — aplica-se apenas ao canal não presencial | Coerente com o Art. 4.º n.º 4, mas deixaria o canal presencial sem a exigência |
| C | Princípio geral — aplica-se a **qualquer** entrega inicial de fundos com origem em conta de terceiro, em qualquer canal | Mais abrangente; alinhado com o propósito declarado do Aviso em matéria de BC/FT |

## Decisão

Adopta-se a **interpretação C**.

Fundamentação: os considerandos do Aviso declaram expressamente como motivação "as novas exigências legais e regulamentares, mormente, sobre a prevenção e combate ao branqueamento de capitais, do financiamento do terrorismo e da proliferação de armas de destruição em massa". Uma leitura que dispensasse justificação credível para fundos de terceiro no canal presencial — onde o risco é o mesmo — contrariaria o propósito da norma.

Adicionalmente, a interpretação C é a **mais restritiva**. Em caso de divergência interpretativa com o supervisor, o risco de ter exigido mais do que o mínimo é imaterial; o risco de ter exigido menos é sancionatório.

## Implementação

| Regra | Comportamento |
|---|---|
| `BR-FUN-03` | Se `ordenante ≠ titular`, exige-se documento `DOC_JUSTIFICACAO_FUNDOS_TERCEIRO` **e** avaliação humana registada, em **todos** os canais |
| `BR-FUN-01` | O requisito de transferência com identificação do ordenante aplica-se especificamente ao canal `REMOTO`, conforme Art. 4.º n.º 4 |
| `BR-FUN-02` | A exigência de instituição de origem que comprovadamente aplique diligência aplica-se conforme Art. 4.º n.º 4 |

A distinção mantém-se explícita: `BR-FUN-01` e `BR-FUN-02` são requisitos do canal remoto; `BR-FUN-03` é requisito geral.

## Consequências

**Positivas** — posição defensável perante o supervisor; cobertura de risco de BC/FT uniforme entre canais; a decisão e a sua fundamentação ficam registadas, o que é em si mesmo um elemento de defesa.

**Negativas aceites** — atrito adicional no canal presencial em casos que uma leitura literal dispensaria; `F9` (avaliação humana) gera carga em Compliance também no presencial.

## Acção pendente

Submeter esta interpretação a **consulta formal ao BNA**, ao abrigo do Art. 16.º, que atribui ao BNA a resolução de dúvidas e omissões. A resposta, quando vier, actualiza esta ADR — não a substitui, para que o histórico da decisão permaneça.
