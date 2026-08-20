# `trace-check` — Verificação de Integridade da Rastreabilidade

Ferramenta de integração contínua. Lê a documentação e o código, e **falha o *build*** quando a cadeia norma → regra → processo → módulo → teste se rompe.

## Porque existe

O risco `R-20` — "regras de negócio migram silenciosamente para código, tornando-se inauditáveis" — tem exposição 16, a segunda mais alta do registo. A mitigação declarada é esta verificação. Sem ela, a matriz de rastreabilidade degrada-se em documentação desactualizada em poucos meses, e a resposta a "como provam que cumprem o artigo 5.º?" volta a ser trabalho de arqueologia.

## Verificações

Conforme `docs/02-bpm/07-matriz-rastreabilidade.md`, secção final:

| # | Falha o *build* quando |
|---|---|
| 1 | Existe `REG-*` de escopo `S` sem `BR-*` associada |
| 2 | Existe `REG-*` de escopo `S` sem elemento TO-BE identificado |
| 3 | Existe `REG-*` de escopo `S` sem módulo responsável |
| 4 | Existe `REG-*` de escopo `S` sem teste nomeado |
| 5 | Existe teste nomeado na matriz que não existe no código |
| 6 | Existe `BR-*` no repositório de regras que não aparece na matriz |
| 7 | Existe elemento BPMN com anotação `REG-*` inexistente na matriz de requisitos |
| 8 | Existe invariante `INV-*` do TO-BE sem teste correspondente |
| 9 | Existe anotação `@Regulatorio` no código a referenciar `REG-*` inexistente |

## Fontes lidas

| Fonte | Extrai |
|---|---|
| `docs/01-regulatorio/03-matriz-requisitos-regulatorios.md` | Identificadores `REG-*` e escopo (`S`/`P`/`N`) |
| `docs/02-bpm/05-regras-de-negocio.md` | Identificadores `BR-*` e `REG-*` de origem |
| `docs/02-bpm/04-to-be-abertura-de-conta.md` | Elementos de processo e invariantes `INV-*` |
| `docs/02-bpm/07-matriz-rastreabilidade.md` | Ligações declaradas e nomes de teste |
| `backend/**/*.java` | Anotações `@Regulatorio` e nomes de método de teste |
| `bpmn/**/*.bpmn`, `bpmn/**/*.dmn` | Anotações `REG-*` nos elementos |

## Estado — Fase 0

Especificado. Implementado na Fase 2, em conjunto com a primeira fatia vertical — a ferramenta só tem o que verificar quando existir código a verificar. Até lá, a integridade da matriz é confirmada por revisão na saída da Fase 0.
