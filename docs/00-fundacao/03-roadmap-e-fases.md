# Roteiro e Fases

> Estruturado sobre o **ciclo de vida BPM do ABPMP CBOK**: Planeamento e Estratégia → Análise → Desenho → Implementação → Monitorização e Controlo → Refinamento. O ciclo é contínuo: a Fase 5 realimenta a Fase 1.

---

## Visão geral

| Fase | Etapa do ciclo BPM | Marco | Estado |
|---|---|---|---|
| **Fase 0** | Planeamento e Estratégia + Análise + Desenho | M0 | **Em curso** |
| **Fase 1** | Análise (validação) | M1, M2 | Por iniciar |
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

## Fase 1 — Validação e congelamento

**Objectivo:** substituir hipóteses por factos. Nenhuma linha de lógica de negócio antes disto.

| # | Actividade | Saída | Duração |
|---|---|---|---|
| 1.1 | Nomear Dono do Processo | Nomeação formal | Pré-requisito |
| 1.2 | Executar o protocolo de validação do AS-IS (6 técnicas) | AS-IS validado; valores `†` substituídos | 3 semanas |
| 1.3 | Oficinas de desenho TO-BE com quem executa | TO-BE ajustado | 2 semanas |
| 1.4 | Recolher minutas de Condições Gerais e verificar os 13 temas | Minuta conforme, versionada | 2 semanas |
| 1.5 | Selecionar fornecedor de verificação de identidade (prova de conceito com documentos angolanos) | Fornecedor selecionado com métricas de fiabilidade | 4 semanas |
| 1.6 | Especificar contrato de integração com o core bancário | Contrato acordado com a Direcção de Sistemas | 3 semanas |
| 1.7 | Constituir o registo de instituições que aplicam diligência | Registo inicial com critério de aferição | 2 semanas |
| 1.8 | Modelar BPMN e DMN executáveis | Modelos validados por Compliance | 3 semanas |
| 1.9 | Parametrizar risco, diligência e limiares | Tabelas DMN preenchidas e aprovadas | 2 semanas |

**Saída de fase:** M1 (AS-IS validado) e M2 (TO-BE congelado).

**Porta de qualidade:** o TO-BE não congela sem assinatura do Dono do Processo, do Chefe de Compliance KYC e do Chefe de Compliance BC/FT.

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

```
   [1.1 Dono do Processo]  ─────────────┐
                                        ▼
   [1.2 Validar AS-IS] ──► [1.3 TO-BE] ──► [1.8 BPMN/DMN] ──► [Fase 2] ──► [Fase 3]
                                        ▲          ▲
   [1.5 Fornecedor identidade] ─────────┘          │
   [1.4 Minutas jurídicas] ────────────────────────┤
   [1.6 Contrato core] ────────────────────────────┘
```

**Caminho crítico:** `1.1 → 1.2 → 1.3 → 1.8 → Fase 2 → Fase 3`.

**Dependências que podem deslocar o caminho crítico:** `1.5` (fornecedor de identidade), `1.4` (minutas) e `1.6` (core). São as três premissas de risco alto — `A2`, `A3` e `A4` — e devem arrancar em paralelo com `1.2`, não depois.
