# ADR-0010 — Idempotência universal e compensação Saga

**Estado:** Aceite · **Data:** 2026-08-19 · **Decisor:** Arquitecto de Software

## Contexto

O motor de processos garante entrega **ao menos uma vez**. Uma tarefa de serviço pode ser executada mais de uma vez: por retentativa após tempo esgotado, por reinício de trabalhador, por reprocessamento de mensagem.

No domínio bancário isto tem consequências concretas: `bh-account.criarConta` executado duas vezes cria duas contas com dois IBAN para o mesmo cliente (`R-18`); `bh-notification` executado duas vezes envia duas mensagens; `bh-funding` executado duas vezes contabiliza duas entregas.

Existe um segundo problema, independente. O processo atravessa fronteiras transaccionais: a conta é criada no core bancário na fase F1, mas só é activada em F11, após a verificação da entrega de fundos. Se algo falhar entre F1 e F11 — verificação recusada, expiração do prazo, desistência — existe uma conta criada no core que nunca deveria ter existido (`R-19`). Uma transacção distribuída não é opção: o core bancário não participa numa transacção nossa.

## Decisão

### Idempotência universal

Toda tarefa de serviço e todo endpoint de escrita aceitam e exigem chave de idempotência, derivada deterministicamente de `(pedidoId, nomeTarefa, tentativaLogica)`.

O módulo registra a chave com o resultado. Chave já vista devolve o resultado registado sem reexecutar o efeito.

### Compensação Saga

Cada passo com efeito externo declara a sua acção compensatória. Não há transacção distribuída; há reversão explícita e modelada.

| Passo | Efeito externo | Compensação |
|---|---|---|
| F1 `criarConta` | Conta criada no core em `ABERTA_NAO_ACTIVA` | Encerrar conta no core, com motivo registado |
| F11 `activarConta` | Conta `ACTIVA` | Nenhuma — é o último passo do caminho de sucesso |
| E11 `celebrarContrato` | Contrato celebrado, hash selado | Anular contrato com registo de anulação (nunca eliminar) |
| `bh-funding` fundos recebidos e recusados | Fundos na conta interna | Devolver à conta de origem (`BR-FUN-05`) |
| `bh-notification` enviada | Mensagem no cliente | Não compensável — mitigado por notificação de correcção |

A compensação é modelada no BPMN como *compensation handler*, e não como código de tratamento de erro dispersado.

**Salvaguarda de desenho:** a conta permanece em `ABERTA_NAO_ACTIVA` até à verificação dos fundos (`BR-FUN-04`). O efeito externo mais consequente — uma conta operacional — só ocorre quando já não há passos que possam falhar. Isto reduz a compensação a um caso raro em vez de um caminho corrente.

## Alternativas consideradas

| Alternativa | Porque foi rejeitada |
|---|---|
| Transacção distribuída (two-phase commit) | O core bancário não participa. Tecnicamente indisponível, não apenas indesejável |
| Confiar em entrega exactamente-uma-vez | Não existe. Garantia de entrega ao menos uma vez é a propriedade real do motor |
| Idempotência só onde parece necessário | O julgamento sobre o que "parece necessário" falha exactamente nos casos em que importa. Universal é mais simples de verificar do que selectiva |
| Criar a conta no core apenas no fim, após verificação de fundos | O cliente precisa do IBAN **para** transferir os fundos iniciais. A ordem é imposta pelo negócio, não pela arquitectura |

## Consequências

**Positivas** — retentativa é segura em todo o processo; nenhuma conta órfã activa; reversão é modelada e visível no diagrama em vez de escondida em tratamento de erro; comportamento previsível sob falha de rede e reinício de trabalhador.

**Negativas aceites** — registo de chaves de idempotência a manter e expurgar; cada passo com efeito externo obriga a pensar a compensação, o que é trabalho adicional de desenho; a compensação de `bh-notification` não existe, e é uma limitação aceite conscientemente.

## Verificação

- Teste que executa cada *job worker* duas vezes com a mesma chave e verifica efeito único.
- Teste de processo que injecta falha entre F1 e F11 e verifica que a conta não permanece no core.
- Teste de arquitectura: todo endpoint de escrita declara chave de idempotência.
