# ADR-0001 — Camunda 8 como motor de orquestração

**Estado:** Aceite · **Data:** 2026-08-19 · **Decisor:** Arquitecto de Software

## Contexto

O processo de abertura de conta é de execução longa (minutos a dias), atravessa nove módulos e três sistemas externos, tem esperas por acção do cliente com prazo, caminhos de excepção com intervenção humana e obrigação de reconstituir, anos depois, o que aconteceu em cada instância.

Uma máquina de estados codificada à mão consegue executar isto. O que não consegue é apresentar o processo a Compliance de forma inspeccionável, nem responder a "mostre-me o que correu neste pedido de 2027" sem trabalho de arqueologia.

O uso de Camunda é uma restrição declarada do projecto (`C6`). Esta ADR registra a fundamentação, para que a restrição seja compreendida e não apenas obedecida.

## Decisão

Camunda 8 (Zeebe) como motor de orquestração, com BPMN 2.0 e DMN como notações únicas de processo e decisão. Módulos de domínio expõem tarefas via *job workers* em `bh-orchestration`.

## Alternativas consideradas

| Alternativa | Porque foi rejeitada |
|---|---|
| Máquina de estados em código (Spring StateMachine) | O modelo executável não é o mesmo artefacto que Compliance revê. Divergência entre diagrama documental e comportamento real é inevitável, e é precisamente o que a auditoria procura |
| Orquestração por eventos sem coordenador | Sem estado central explícito, reconstituir o percurso de uma instância exige correlacionar tópicos. Prazos, escalonamentos e tarefas humanas ficam dispersos |
| Camunda 7 | Fim de vida anunciado; sem sentido iniciar um produto novo sobre plataforma em transição |
| BPMS proprietário de fornecedor de core bancário | Aprisionamento a um fornecedor, contra o objectivo O7 de instalar em qualquer IFB |

## Consequências

**Positivas**

- O diagrama que Compliance aprova **é** o artefacto executado. Não há divergência possível.
- Histórico de instância nativo: quem, quando, que caminho, que decisão — base directa de `REG-RET-02`.
- Prazos e escalonamentos são elementos de primeira classe (temporizadores), sustentando `INV-10`.
- Tarefas humanas com atribuição por papel via Tasklist.
- Decisões DMN versionadas e auditáveis, sustentando `INV-09`.

**Negativas aceites**

- Componente de infra-estrutura adicional a operar, com curva de aprendizagem.
- Tentação de colocar lógica de negócio em expressões de *gateway*. Mitigado por `ADR-0003` e por revisão de modelo.
- Variáveis de processo tentam a transportar dados pessoais. Mitigado por `ADR-0009`.

## Verificação

- Todo elemento de processo com origem normativa tem anotação `REG-*` no BPMN.
- Testes de processo (Camunda Process Test) cobrem 100% dos ramos e verificam `INV-01` a `INV-10`.
