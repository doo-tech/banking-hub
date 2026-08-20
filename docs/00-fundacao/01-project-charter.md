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
| ~~A1~~ | ~~A instituição de acolhimento nomeia Dono do Processo com autoridade efectiva~~ | **FALSIFICADA em 20/08/2026** — não existe instituição de acolhimento. Ver emenda E-01 |
| A2 | O core bancário expõe, ou pode passar a expor, API de criação e activação de conta | Necessário adaptador com integração por ficheiro; degrada o alvo de tempo de ciclo |
| A3 | Existem fornecedores de verificação de identidade utilizáveis com documentos de identificação angolanos | Verificação remota degrada para revisão manual; O2 e O3 ficam em risco |
| A4 | A Direcção Jurídica fornece minutas de Condições Gerais que cobrem os 13 temas | Bloqueia a Fase E; é dependência crítica |
| ~~A5~~ | ~~O AS-IS de referência é validado na instituição antes do congelamento do TO-BE~~ | **FALSIFICADA em 20/08/2026** — sem instituição, não há agência para observar. Substituída por A8. Ver emenda E-01 |
| A8 | O AS-IS de referência do sector é base suficiente para desenhar um produto, sendo a validação feita por instituição no momento da instalação | Se um AS-IS real divergir substancialmente do de referência, o produto exige adaptação e não apenas parametrização |
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

Divididos em dois conjuntos pela emenda E-01, porque os critérios originais pressupunham uma instituição em produção e, sem ela, seriam permanentemente inalcançáveis.

### Conjunto A — verificáveis sem instituição de acolhimento

| # | Critério | Como se verifica |
|---|---|---|
| A1 | Os invariantes `INV-01` a `INV-10` são verificados por teste automatizado, e cada um falha quando o controlo é removido | Suite de testes |
| A2 | Todo requisito `REG-*` de escopo MVP tem regra, elemento de processo, módulo e teste nomeado, verificado automaticamente | `tools/trace-check` no *build* |
| A3 | O processo corre ponta-a-ponta em ambiente local, para pessoa singular residente de risco baixo, sem intervenção humana | Instância concluída em Camunda |
| A4 | `KPI-01` inferior a 15 minutos medido em ambiente controlado, com verificação de identidade simulada | Medição sobre 30 instâncias sintéticas |
| A5 | A instalação de uma segunda instituição exige apenas parametrização, sem alteração de código | Segundo `tenantId` configurado do zero |
| A6 | Um profissional de Compliance consegue ler e aprovar as regras sem assistência técnica | Revisão das tabelas de decisão por terceiro |

### Conjunto B — exigem instituição em produção

Mantidos como alvo, reconhecidamente indisponíveis no modo actual:

| # | Critério |
|---|---|
| B1 | `KPC-01` a `KPC-11` a 100% durante três meses consecutivos em produção |
| B2 | `KPI-04` (processamento sem intervenção humana) acima de 60% em volume real |
| B3 | `KPI-08` (recuperação de dossiê) abaixo de 5 minutos no percentil 95, com arquivo real |
| B4 | Auditoria interna independente confirma a rastreabilidade sem constatação crítica |

## 10. Critérios de insucesso — quando parar

Explicitados deliberadamente, para que a decisão de parar seja tomada por critério e não por desgaste:

| # | Condição | Acção |
|---|---|---|
| ~~S1~~ | ~~Não é possível nomear Dono do Processo com autoridade após dois ciclos de escalonamento~~ | Substituído por S5 pela emenda E-01 |
| S5 | A fatia vertical conclui e, mesmo assim, nenhuma instituição manifesta interesse após apresentação a três interlocutores | Reavaliar: o problema pode ser real e a proposta não ser a certa, ou o canal comercial ser inadequado |
| S2 | Nenhum fornecedor de verificação de identidade atinge fiabilidade utilizável com documentos angolanos | Redesenhar o Escopo 1 para canal presencial digitalizado; renegociar O1 |
| S3 | O core bancário não permite integração programática nem por ficheiro em prazo aceitável | Renegociar escopo e alvos de tempo de ciclo |
| S4 | O BNA comunica não aceitar conservação exclusivamente por processo tecnológico | Adicionar arquivo físico paralelo; recalcular caso de negócio |

---

## 11. Emenda E-01 — Modo Produto

**Data:** 20 de Agosto de 2026. **Motivo:** confirmação de que não existe instituição de acolhimento.

### O que mudou

As premissas `A1` e `A5` estão falsificadas. Não há Dono do Processo para nomear nem agência para observar. Isto invalida a Fase 1 tal como estava desenhada, e invalidaria também os critérios de sucesso originais, todos dependentes de produção.

### A decisão

O projecto passa a executar em **Modo Produto**: constrói-se uma implementação de referência sobre o AS-IS do sector, e a validação por instituição desloca-se para o momento da instalação.

Esta é uma reformulação do portão de qualidade, não a sua supressão. A distinção importa e fica registada:

| Suprimir o portão seria | Reformulá-lo é |
|---|---|
| Avançar para implementação sem dizer que o AS-IS não foi validado | Marcar o AS-IS como não validado em todo lugar onde é citado, e manter o protocolo de validação como pré-requisito de cada instalação |
| Apresentar as estimativas como medições | Manter a marca de estimativa em todos os tempos |
| Retirar a exigência de Dono do Processo | Manter a exigência, deslocada para o contrato de instalação |

### Consequências operacionais

1. **A validação do AS-IS passa a ser pré-requisito de instalação**, não de desenho. Nenhuma instituição entra em produção sem executar o protocolo de 6 técnicas na sua realidade.
2. **A nomeação do Dono do Processo passa a ser cláusula do compromisso de instalação.** Continua a ser condição — muda apenas o momento em que é exigida.
3. **O TO-BE passa a ser hipótese de produto**, não desenho validado. Está desenhado a partir da norma, que é a mesma para todas as instituições, e a partir de um AS-IS de referência, que pode divergir.
4. **A parametrização por instituição deixa de ser conveniência e passa a ser o mecanismo de adaptação.** É o que absorve a divergência entre o AS-IS de referência e o AS-IS real de cada banco. Reforça a prioridade de `ADR-0008`.
5. **A fatia vertical deixa de ser apenas prova técnica** e passa a ser também o argumento comercial. É o que se mostra a um banco.

### Risco que esta emenda cria

Registado como `R-29` no registo de riscos: construir sobre um AS-IS não validado pode produzir um produto que resolve um problema mal caracterizado. A mitigação não é técnica — é comercial: apresentar a três interlocutores antes de investir na segunda fatia, e tratar a ausência de interesse como informação sobre o diagnóstico, não como falha de venda.
