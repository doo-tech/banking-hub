# Termo de Abertura do Projecto — Banking Hub

| Campo | Valor |
|---|---|
| Projecto | **Banking Hub** — Plataforma de Onboarding Bancário para Angola |
| Versão | 1.0 |
| Data | 19 de Agosto de 2026 |
| Patrocinador | *A nomear pela instituição de acolhimento* |
| Gestor de Projecto | *A nomear* |
| Dono do Processo (`PRC-01`) | *A nomear — pré-requisito para arranque da Fase 1* |
| Base regulatória | Aviso n.º 1/23 do BNA, de 30 de Janeiro de 2023 |
| Estado | Fase 0 — Conceptualização |

---

## 1. Justificação

A abertura de conta bancária em Angola é hoje, na generalidade da banca de retalho, um processo presencial, em papel e integralmente manual. O Aviso n.º 1/23 do BNA, em vigor desde 31 de Janeiro de 2023, permite expressamente a abertura de conta **sem presença física do cliente**, mediante uso exclusivo de meios de comunicação à distância (Art. 3.º n.º 4 e n.º 5). Simultaneamente, endurece as exigências de identificação, diligência, informação pré-contratual e retenção de registos.

Coexistem, portanto, duas forças: uma **permissão** que ainda não é aproveitada e uma **exigência** que o processo em papel cumpre em substância mas não consegue provar de forma eficiente.

A análise AS-IS (`docs/02-bpm/03-as-is-abertura-de-conta.md`) quantifica o problema: eficiência de ciclo de 2 a 5%, taxa de abandono de 15 a 30%, rendimento acumulado de aproximadamente 31%, zero processamento sem intervenção humana, e doze riscos de conformidade identificados — três deles críticos.

## 2. Problema

> O processo de abertura de conta consome 3 a 5 horas de trabalho humano qualificado por conta, demora 3 a 30 dias úteis, perde entre 15% e 30% dos clientes pelo caminho, e não produz evidência estruturada e pesquisável do cumprimento das obrigações do Aviso n.º 1/23 — nomeadamente da disponibilização **prévia** das Condições Gerais, da triagem PEP de todas as partes e da disponibilização atempada de registos à autoridade competente.

## 3. Objectivos

| # | Objectivo | Indicador | Alvo |
|---|---|---|---|
| O1 | Viabilizar a abertura de conta integralmente remota, ao abrigo do Art. 3.º n.º 5 | `KPI-12` distribuição por canal | > 70% remoto ao 12.º mês |
| O2 | Reduzir o tempo de ciclo para pessoa singular de risco baixo | `KPI-01` | < 15 min ponta-a-ponta |
| O3 | Eliminar intervenção humana em pedidos de risco baixo | `KPI-04` STP | > 60% |
| O4 | Garantir e provar conformidade integral com o Aviso n.º 1/23 | `KPC-01`–`KPC-11` | 100%, sem excepção |
| O5 | Tornar a resposta a pedido de autoridade competente imediata | `KPI-08` | < 5 min (p95) por dossiê |
| O6 | Reduzir o custo unitário de abertura | `KPI-10` | Redução ≥ 70% |
| O7 | Entregar produto instalável em qualquer IFB sediada em Angola | Instituições em produção | ≥ 1 no Escopo 1; multi-instituição desde o desenho |

## 4. Escopo

### Dentro do escopo (Escopo 1 — MVP)

- Processo `PRC-01` — Abertura de Conta de Depósito, ponta-a-ponta, até `ACTIVA` e dossiê retido.
- Canais `REMOTO` (app Android nativa) e `PRESENCIAL` (app do gestor de balcão), sobre o **mesmo** modelo de processo.
- Perfis de cliente: pessoa singular residente e não residente, menor, comerciante em nome individual, pessoa colectiva, organização sem fins lucrativos, instituição de caridade sem personalidade jurídica, condomínio e património autónomo.
- Verificação remota de identidade: OCR do documento, correspondência facial e prova de vida.
- Triagem PEP com a taxonomia integral do Anexo I, rastreio de sanções, pontuação de risco e nível de diligência.
- Identificação de titulares de participação ≥ 20% e de beneficiário efectivo.
- Geração, disponibilização evidenciada e celebração do Contrato de Abertura de Conta com as três peças.
- Verificação da entrega inicial de fundos, incluindo o caso de ordenante diferente do titular.
- Integração com core bancário para criação, marcação e activação de conta.
- Arquivo digital com retenção ≥ 10 anos, índice pesquisável e registo de auditoria encadeado.
- Orquestração em Camunda 8, com BPMN e DMN versionados.
- Empacotamento integralmente em contentores.

### Fora do escopo do Escopo 1

- Movimentação de conta, extractos e meios de pagamento (Escopo 2).
- Gestão de contas dormentes, eventos de vida e encerramento (Escopo 3) — **modelados** no domínio e no mapa de processos, não implementados.
- iOS e canal web para o cliente final.
- Substituição do core bancário. O Banking Hub integra-se com o core existente; não o substitui.
- Motor de decisão de crédito.

### Fora do escopo, definitivamente

- Alteração de qualquer controlo imposto pelo Aviso n.º 1/23. O projecto automatiza controlos; não os flexibiliza.

## 5. Premissas

| # | Premissa | Consequência se falsa |
|---|---|---|
| A1 | A instituição de acolhimento nomeia Dono do Processo com autoridade efectiva | Sem decisor, o desenho paralisa em impasses funcionais |
| A2 | O core bancário expõe, ou pode passar a expor, API de criação e activação de conta | Necessário adaptador com integração por ficheiro; degrada o alvo de tempo de ciclo |
| A3 | Existem fornecedores de verificação de identidade utilizáveis com documentos de identificação angolanos | Verificação remota degrada para revisão manual; O2 e O3 ficam em risco |
| A4 | A Direcção Jurídica fornece minutas de Condições Gerais que cobrem os 13 temas | Bloqueia a Fase E; é dependência crítica |
| A5 | O AS-IS de referência é validado na instituição antes do congelamento do TO-BE | O TO-BE resolve problemas hipotéticos |
| A6 | Existe registo, ou é possível constituí-lo, das instituições que comprovadamente aplicam diligência | `REG-FUN-02` fica sem base de verificação |
| A7 | A conservação por processo tecnológico é aceite pelo BNA nos termos do Anexo I, secção II | Obriga a arquivo físico paralelo |

## 6. Restrições

| # | Restrição |
|---|---|
| C1 | Aviso n.º 1/23 do BNA — integralmente vinculativo, sem margem de flexibilização |
| C2 | Lei n.º 5/20 (BC/FT/FPADM), Lei n.º 14/21 (RGIF), Lei n.º 5/97 (Cambial), Lei n.º 22/11 (Protecção de Dados Pessoais) |
| C3 | Aviso n.º 14/20 do BNA — prevenção de branqueamento de capitais e financiamento do terrorismo |
| C4 | Documentação de negócio e Linguagem Ubíqua em português de Angola; terminologia normativa preservada tal como publicada |
| C5 | Toda a plataforma empacotada em contentores |
| C6 | Orquestração de processos em Camunda 8; BPMN 2.0 e DMN como notações únicas |
| C7 | Mobile Android nativo em Kotlin; backend em Java com Spring |
| C8 | Multi-instituição desde o desenho — o produto é para qualquer banco em Angola |

## 7. Partes interessadas

Ver `02-stakeholders-raci.md`.

## 8. Marcos

| Marco | Entregável | Critério de aceitação |
|---|---|---|
| M0 | Fase conceptual concluída | Glossário, arquitectura de processos, AS-IS, TO-BE, regras de negócio, KPIs, matriz de rastreabilidade, decisões de arquitectura e estrutura base aprovados |
| M1 | AS-IS validado na instituição | Protocolo da secção 8 do AS-IS executado; valores `†` substituídos por medição real |
| M2 | TO-BE congelado | BPMN e DMN aprovados pelo Dono do Processo e por Compliance |
| M3 | Fatia vertical funcional | Pessoa singular residente, remoto, risco baixo, ponta-a-ponta em ambiente de integração |
| M4 | Conformidade certificada | `KPC-01`–`KPC-11` a 100%; auditoria interna sem constatação crítica |
| M5 | Piloto em produção | 100 contas abertas com `KPI-01` < 1 dia útil |
| M6 | Escopo 1 concluído | Todos os perfis de cliente e ambos os canais em produção |

## 9. Critérios de sucesso do projecto

O projecto é considerado bem-sucedido quando, cumulativamente:

1. `KPC-01` a `KPC-11` registam 100% durante três meses consecutivos em produção.
2. `KPI-01` para pessoa singular de risco baixo em canal remoto é inferior a 15 minutos no percentil 95.
3. `KPI-04` (STP) excede 60% no universo de risco baixo.
4. `KPI-08` (recuperação de dossiê) é inferior a 5 minutos no percentil 95.
5. Uma auditoria interna independente confirma a rastreabilidade integral da matriz `REG-*` sem constatação crítica.
6. A instalação numa segunda instituição não exige alteração de código — apenas parametrização.

## 10. Critérios de insucesso — quando parar

Explicitados deliberadamente, para que a decisão de parar seja tomada por critério e não por desgaste:

| # | Condição | Acção |
|---|---|---|
| S1 | Não é possível nomear Dono do Processo com autoridade após dois ciclos de escalonamento | Suspender; sem Dono não há BPM |
| S2 | Nenhum fornecedor de verificação de identidade atinge fiabilidade utilizável com documentos angolanos | Redesenhar o Escopo 1 para canal presencial digitalizado; renegociar O1 |
| S3 | O core bancário não permite integração programática nem por ficheiro em prazo aceitável | Renegociar escopo e alvos de tempo de ciclo |
| S4 | O BNA comunica não aceitar conservação exclusivamente por processo tecnológico | Adicionar arquivo físico paralelo; recalcular caso de negócio |
