# ADR-0006 — Arquivo WORM para retenção de 10 anos

**Estado:** Aceite · **Data:** 2026-08-19 · **Decisor:** Arquitecto + Chefe de Compliance KYC

## Contexto

O Anexo I impõe dois requisitos cumulativos e frequentemente confundidos:

1. **Conservar** todos os registos de clientes por período **mínimo de 10 anos** (I.1 ponto 3).
2. **Garantir que estão disponíveis atempadamente** para consulta pela autoridade competente (I.1 ponto 3).

O segundo requisito é o que o arquivo em papel falha: no AS-IS, localizar um processo demora de 2 horas a 5 dias (`RAI-07`).

A secção II do Anexo admite expressamente conservação "na forma de documentos físicos **e qualquer processo tecnológico** nos termos a estabelecer pelo Banco Nacional da Angola". Esta é a base legal do arquivo digital.

## Decisão

Arquivo digital com três propriedades:

1. **WORM** — armazenamento de objectos com bloqueio de retenção. Um objecto selado não é alterável nem eliminável durante o período de retenção, por configuração do armazenamento e não por permissão de aplicação.
2. **Manifesto com hash** — o `DossieAbertura` selado contém o hash de cada artefacto e um hash do conjunto. Alteração posterior é detectável.
3. **Índice de recuperação** — pesquisável por cliente, NIF, número de conta, data e estado, com alvo de recuperação de dossiê completo em menos de 5 minutos no percentil 95 (`KPI-08`).

**Regras associadas:** a contagem da retenção inicia-se na **data do estado terminal** do pedido, não na data de início (`BR-RET-02`). **Todo** estado terminal produz dossiê selado, incluindo recusa, desistência, não elegibilidade e expiração (`BR-RET-03`).

## Alternativas consideradas

| Alternativa | Porque foi rejeitada |
|---|---|
| Arquivo em base de dados relacional com coluna de retenção | Retenção aplicada por lógica de aplicação é retenção que um erro operacional ou um acesso privilegiado contorna. `R-08` |
| Sistema de gestão documental genérico | Sem bloqueio de retenção verificável nem manifesto de integridade; obriga a construir as garantias por cima |
| Manter arquivo físico como registo autoritativo | Preserva o problema de disponibilização atempada, que é metade do requisito |
| Armazenamento de objectos sem bloqueio de retenção | Depende de política de acesso; um erro de configuração permite expurgo antecipado |

## Consequências

**Positivas** — expurgo antes do prazo é rejeitado pelo armazenamento, não desencorajado por procedimento (`BR-RET-01`); integridade demonstrável por hash; resposta a pedido de autoridade em minutos; conformidade com `REG-RET-01`, `REG-RET-02` e `REG-RET-03` verificável por teste.

**Negativas aceites** — custo de armazenamento por dez anos, incluindo dossiês de pedidos recusados; correcção de erro num dossiê selado exige registo de correcção adicional, nunca alteração do original; migração futura de tecnologia de armazenamento tem de preservar a garantia WORM.

## Risco residual

A premissa `A7` — o BNA aceitar conservação exclusivamente por processo tecnológico — não está confirmada por escrito. A arquitectura permite arquivo físico paralelo sem alteração estrutural, caso a confirmação não venha (`R-04`).
