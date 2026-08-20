# Roteiro e Fases

> Estruturado sobre o **ciclo de vida BPM do ABPMP CBOK**: Planeamento e Estratégia → Análise → Desenho → Implementação → Monitorização e Controlo → Refinamento. O ciclo é contínuo: a Fase 5 realimenta a Fase 1.

---

## Visão geral

| Fase | Etapa do ciclo BPM | Marco | Estado |
|---|---|---|---|
| **Fase 0** | Planeamento e Estratégia + Análise + Desenho | M0 | **Em curso** |
| ~~**Fase 1**~~ | ~~Análise (validação)~~ | ~~M1, M2~~ | **Reformulada pela emenda E-01** — ver Fase 1-P |
| **Fase 1-P** | Desenho executável (Modo Produto) | M2, M3 | **Em curso** |
| **Fase 2** | Implementação — fatia vertical | M3 | Por iniciar |
| **Fase 3** | Implementação — Escopo 1 completo | M4, M5, M6 | Por iniciar |
| **Fase 4** | Escopo 2 — manutenção e movimentação | — | Planeado |
| **Fase 5** | Escopo 3 — dormência, eventos de vida, encerramento | — | Planeado |
| **Contínua** | Monitorização, Controlo e Refinamento | — | A partir de M5 |

---

## Fase 0 — Conceptualização

**Objectivo:** estabelecer base regulatória, vocabulário, processos e arquitectura antes de escrever lógica de negócio.

| # | Entregável | Estado |
|---|---|---|
| 0.1 | Transcrição de referência do Aviso n.º 1/23 | Concluído |
| 0.2 | Anexo I — campos da Ficha de Cliente e catálogo documental | Concluído |
| 0.3 | Matriz de requisitos regulatórios `REG-*` (53 requisitos) | Concluído |
| 0.4 | Glossário e Linguagem Ubíqua | Concluído |
| 0.5 | Arquitectura de processos | Concluído |
| 0.6 | AS-IS de referência com análise | Concluído |
| 0.7 | TO-BE com máquina de estados e invariantes | Concluído |
| 0.8 | Repositório de regras de negócio `BR-*` (75 regras) | Concluído |
| 0.9 | KPIs, SLA e plano de medição | Concluído |
| 0.10 | Matriz de rastreabilidade | Concluído |
| 0.11 | Decisões de arquitectura (ADR-0001 a ADR-0010) | Concluído |
| 0.12 | Modelo de domínio e mapa de módulos | Concluído |
| 0.13 | Estrutura base do projecto e ambiente em contentores | Concluído |
| 0.14 | Esqueletos BPMN e DMN com anotações `REG-*` | Concluído |

**Saída de fase:** M0 — aprovação do conjunto conceptual pelo Dono do Processo, Compliance (KYC e BC/FT) e Arquitecto.

## ~~Fase 1 — Validação e congelamento~~ → reformulada

**Estado: não executável no Modo Produto.** As actividades `1.1` a `1.3` e `1.6` a `1.7` exigem uma instituição de acolhimento, que não existe (emenda E-01 do Termo de Abertura).

O conteúdo desta fase **não é descartado**: passa a ser pré-requisito de instalação em cada instituição, e está preservado como tal.

| Actividade original | Destino |
|---|---|
| 1.1 Nomear Dono do Processo | Cláusula do compromisso de instalação |
| 1.2 Validar o AS-IS (protocolo de 6 técnicas) | Pré-requisito de instalação, por instituição |
| 1.3 Oficinas de TO-BE com quem executa | Pré-requisito de instalação, por instituição |
| 1.4 Minutas de Condições Gerais | Fase 1-P, com minuta de referência construída a partir dos 13 temas do Art. 5.º n.º 2 |
| 1.5 Fornecedor de verificação de identidade | Fase 1-P, com implementação simulada primeiro e prova de conceito real depois |
| 1.6 Contrato de integração com o core | Fase 1-P, com adaptador simulado e contrato de referência publicado |
| 1.7 Registo de instituições que aplicam diligência | Pré-requisito de instalação, por instituição |
| 1.8 Modelar BPMN e DMN executáveis | **Fase 1-P, agora** |
| 1.9 Parametrizar risco e diligência | **Fase 1-P, agora** |

## Fase 1-P — Desenho executável (Modo Produto)

**Objectivo:** produzir os modelos executáveis e o que os torna demonstráveis, para que exista algo a mostrar a uma instituição.

| # | Actividade | Estado |
|---|---|---|
| 1P.1 | Modelo BPMN executável do processo de abertura de conta | Em curso |
| 1P.2 | As 9 decisões DMN, com os limiares do Aviso parametrizados | Em curso |
| 1P.3 | Formulários das tarefas de utilizador | Por iniciar |
| 1P.4 | Minuta de referência de Condições Gerais, cobrindo os 13 temas | Por iniciar |
| 1P.5 | Contrato de referência de integração com o core, e adaptador simulado | Por iniciar |
| 1P.6 | Implementação simulada da verificação de identidade, com a mesma interface do fornecedor real | Por iniciar |

**Saída de fase:** modelos executáveis que arrancam uma instância e a levam até estado terminal.

**Porta de qualidade:** o AS-IS mantém-se marcado como não validado em todo lugar onde é citado. Nenhum número de estimativa é apresentado como medição.

## Fase 2 — Fatia vertical

**Objectivo:** provar a arquitectura ponta-a-ponta no caminho mais simples. Uma fatia fina e completa vale mais do que várias camadas incompletas.

**Âmbito:** pessoa singular residente, canal remoto, risco baixo, documentação completa, sem PEP, ordenante igual ao titular.

| # | Entregável |
|---|---|
| 2.1 | Ambiente em contentores completo (backend, Camunda, base de dados, arquivo, observabilidade) |
| 2.2 | `bh-onboarding-bff` com contrato de API para mobile |
| 2.3 | `bh-customer` — Ficha de Cliente de pessoa singular, campos do Anexo I I.1 |
| 2.4 | `bh-document` — submissão, verificação e catálogo documental |
| 2.5 | `bh-kyc` — OCR, correspondência facial, prova de vida, triagem PEP, risco |
| 2.6 | `bh-contract` — geração, disponibilização evidenciada, assinatura, celebração |
| 2.7 | `bh-funding` — verificação da entrega inicial |
| 2.8 | `bh-account` — integração com core (criação, marcação, activação) |
| 2.9 | `bh-archive` + `bh-audit` — selagem, retenção, índice, cadeia de hash |
| 2.10 | `bh-orchestration` — BPMN executável e *job workers* |
| 2.11 | App Android — percurso completo do fluxo base |
| 2.12 | Testes de processo cobrindo todos os ramos do fluxo base |
| 2.13 | Testes dos invariantes `INV-01` a `INV-10` |
| 2.14 | `tools/trace-check` — implementado **agora e não antes**, porque só tem o que verificar quando existir código e testes a verificar |

**Saída de fase:** M3 — fluxo base a correr ponta-a-ponta em ambiente de integração, com os 10 invariantes verificados.

**Porta de qualidade:** `KPC-01` a `KPC-11` a 100% no âmbito da fatia. Abaixo disso, não avança.

## Fase 3 — Escopo 1 completo

| # | Entregável |
|---|---|
| 3.1 | Perfil menor, com `dmn-limites-menor` e termo de responsabilidade |
| 3.2 | Perfil comerciante em nome individual |
| 3.3 | Perfil pessoa colectiva, com titulares ≥ 20%, procuradores e beneficiário efectivo |
| 3.4 | Perfil pessoa colectiva não residente, com dupla validação |
| 3.5 | Perfis organização sem fins lucrativos, instituição de caridade sem personalidade jurídica, condomínio e património autónomo |
| 3.6 | Percurso de diligência reforçada e aprovação de PEP |
| 3.7 | Caminho de ordenante diferente do titular, com justificação credível |
| 3.8 | Assinatura biométrica |
| 3.9 | Canal presencial (app do gestor de balcão) |
| 3.10 | Canal de entidade terceira mandatada |
| 3.11 | Painéis operacional, de conformidade, de processo e executivo |
| 3.12 | Parametrização multi-instituição e manual de instalação |
| 3.13 | Auditoria interna de rastreabilidade |
| 3.14 | Piloto controlado em produção |

**Saídas de fase:** M4 (conformidade certificada), M5 (piloto com 100 contas), M6 (Escopo 1 concluído).

## Fase 4 — Escopo 2

| # | Entregável | `REG-*` |
|---|---|---|
| 4.1 | `PRC-02` — manutenção de dados de cliente e reavaliação periódica de risco | `REG-KYC-05` |
| 4.2 | `PRC-03` — gestão de meios de movimentação | `REG-MOV-01`, `REG-MEN-01` |
| 4.3 | Emissão e disponibilização de extractos | `REG-INF-05` |
| 4.4 | Regras de movimentação de contas em moeda estrangeira | `REG-FX-01`–`REG-FX-03` |

## Fase 5 — Escopo 3

| # | Entregável | `REG-*` |
|---|---|---|
| 5.1 | `PRC-04` — gestão de contas dormentes (24 meses) | `REG-DOR-01`, `REG-DOR-02` |
| 5.2 | `PRC-05` — eventos de vida: óbito, falência e insolvência | `REG-EVE-01`–`REG-EVE-05` |
| 5.3 | `PRC-06` — encerramento de conta, incluindo os 60 dias, os 15 anos, editais e reversão ao Estado | `REG-ENC-01`–`REG-ENC-09` |

## Fase contínua — Monitorização e Refinamento

| Cadência | Actividade | Responsável |
|---|---|---|
| Contínua | Painéis operacional e de conformidade | Operações, Compliance |
| Diária | Revisão de indicadores `KPC-*`; qualquer valor < 100% é incidente | Compliance KYC |
| Semanal | Revisão de filas, excepções e SLA em risco | Chefe de Operações |
| Mensal | Revisão de desempenho do processo com contra-indicadores | Dono do Processo |
| Mensal | Auditoria de amostra de 30 instâncias contra a matriz `REG-*` | Auditoria Interna |
| Trimestral | Revisão de limiares parametrizáveis e confirmação de que nenhum limiar fixo foi alterado | Compliance BC/FT |
| Por acto normativo | Actualização da matriz `REG-*` e nova versão das decisões DMN afectadas | Compliance KYC |

## Sequenciamento e caminho crítico

Reformulado pela emenda E-01. O caminho já não passa por uma instituição.

```
   [1P.1 BPMN executável] ──► [1P.2 DMN] ──► [Fase 2 fatia vertical] ──► [Apresentação a 3 interlocutores]
                                    ▲                    ▲
   [1P.6 Identidade simulada] ──────┘                    │
   [1P.5 Adaptador de core simulado] ───────────────────┬─┘
   [1P.4 Minuta de referência das Condições Gerais] ────┘
```

**Caminho crítico:** `1P.1 → 1P.2 → Fase 2 → apresentação`.

**O que mudou de natureza:** as três dependências que antes eram de risco alto — API do core, fornecedor de identidade, minutas jurídicas — deixam de bloquear, porque passam a ter implementação simulada atrás de uma interface estável. O risco não desaparece: desloca-se para o momento da substituição do simulado pelo real, e é aí que `A2`, `A3` e `A4` voltam a ser testadas.

**O que passou a ser o verdadeiro portão:** a apresentação a interlocutores. Sem interesse de nenhuma instituição, o problema pode estar mal caracterizado — e essa é informação sobre o diagnóstico, não uma falha de venda. Registado como `S5`.
