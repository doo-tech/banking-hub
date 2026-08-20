# TO-BE — `PRC-01` Abertura de Conta de Depósito

> **Aviso metodológico.** O TO-BE não é o AS-IS informatizado. Segundo o CBOK, o desenho TO-BE parte das **causas-raiz** identificadas na análise e escolhe deliberadamente que trabalho deixa de existir, que trabalho passa a ser executado por sistema e que trabalho permanece humano por exigir julgamento.
>
> **Restrição inviolável.** Nenhum controlo imposto pelo Aviso n.º 1/23 é removido. Todos os `REG-*` classificados `S` são executados **e evidenciados** por sistema.

---

## 1. Das causas-raiz às alavancas de transformação

| Causa-raiz (AS-IS) | Alavanca TO-BE | Elimina |
|---|---|---|
| A informação é recolhida em papel e transcrita para sistema | Captura digital na origem, uma única vez, com validação no momento do preenchimento | `8b`, `H3`, `P4` |
| Os requisitos documentais são conhecidos apenas pelo gestor, e revelados ao cliente por tentativa | **Checklist documental gerada por decisão DMN** a partir do perfil, residência e canal, apresentada antes de qualquer submissão | Espera de 1–15 dias no passo 4; `H2` |
| Não há coordenador do processo: cada participante conhece só a sua parte | **Orquestração explícita** com estado persistido, prazos e visibilidade ponta-a-ponta | `H1`, `H4`, `H5`, `P2`, `P3` |
| A verificação de identidade exige presença física porque não existe alternativa técnica | Verificação remota: OCR do documento, correspondência facial e prova de vida | Dependência de balcão; viabiliza o Art. 3.º n.º 5 |
| Todo pedido é conferido por humano, independentemente do risco | **Diligência proporcional ao risco**: percurso automático para risco baixo, humano apenas para risco elevado, PEP e excepções | `8c` para a maioria; libera Compliance |
| A prova de conformidade é reconstituída *a posteriori* a partir de papel | **Evidência gerada no acto**, imutável, encadeada por hash, indexada e pesquisável | `RAI-01`, `RAI-07`, `RAI-11`; `H6` |
| A triagem PEP depende de conhecimento pessoal | Triagem sistemática de todas as partes contra listas de referência, com categoria da taxonomia registada | `RAI-02` |
| Dúvida de identidade resulta em pendente indefinido | Estado terminal `RECUSADO_IDENTIDADE_NAO_COMPROVADA`, com prazo máximo de resolução | `RAI-06` |

## 2. Princípios de desenho

1. **Recolha uma vez, use sempre.** Nenhum dado é pedido duas vezes ao cliente na mesma instância.
2. **Falhe cedo.** Elegibilidade e checklist documental são resolvidas **antes** de o cliente investir tempo a preencher a ficha. O abandono, quando ocorre, ocorre no minuto 2 e não no dia 12.
3. **Conformidade por desenho, não por conferência.** Se o Art. 5.º n.º 1 exige disponibilização prévia, o modelo torna a celebração tecnicamente impossível sem evidência anterior — não confia na disciplina do operador.
4. **Regra fora do código e fora do diagrama.** Elegibilidade, requisitos documentais, risco e diligência são decisões DMN versionadas. Alterar um limiar regulatório é publicar uma nova versão de decisão, não recompilar a aplicação.
5. **Humano onde há julgamento.** Aprovação de PEP, avaliação de justificação credível de fundos de terceiro e resolução de excepções são e permanecem humanas. Conferir se um campo está preenchido não é julgamento.
6. **Estado explícito e único.** O Pedido de Abertura tem uma máquina de estados fechada. Não existe "em análise" como estado de conveniência.
7. **Assíncrono por omissão.** O cliente nunca fica bloqueado à espera de um sistema externo; é notificado quando há progresso ou quando precisa de agir.
8. **Idempotência em todos os passos.** Toda tarefa de serviço e todo endpoint de escrita aceitam chave de idempotência.
9. **Prazo em tudo.** Toda espera por terceiro tem temporizador e caminho de escalonamento. Nenhuma instância pode ficar parada sem consequência.
10. **Multi-instituição desde o primeiro dia.** Regras, minutas, catálogos e limiares são parametrizáveis por IFB. O produto é para qualquer banco em Angola, não para um.

## 3. Fluxo TO-BE — canal `REMOTO` (mobile Android), pessoa singular residente

Modelo: `bpmn/to-be/PRC-01-abertura-conta.bpmn`

### Fase A — Captação e Elegibilidade (`PRC-01.1`)

| # | Elemento | Tipo BPMN | Pista | `REG-*` | Notas |
|---|---|---|---|---|---|
| A0 | Pedido de abertura iniciado | Evento de início (mensagem) | L2 | `REG-ABR-04` | Cria `pedidoId`; registra canal, instituição, versões de processo e regras |
| A1 | Consentimento de tratamento de dados pessoais | Tarefa de utilizador (app) | L1 | `SUP-02` | Evidência com timestamp e versão da política |
| A2 | Declarar perfil, residência e moeda pretendida | Tarefa de utilizador (app) | L1 | `REG-ABR-01` `REG-ABR-02` | Perguntas mínimas: 5 |
| A3 | **Avaliar elegibilidade** | Tarefa de regra de negócio (DMN `dmn-elegibilidade`) | L4 | `REG-ABR-01` `REG-ABR-02` `REG-ABR-03` `REG-MEN-04` | Decide perfil e produtos admissíveis |
| A4 | Elegível? | *Gateway* exclusivo | L4 | — | Não elegível → A5 |
| A5 | Comunicar não elegibilidade fundamentada | Tarefa de serviço | L4 | — | Estado terminal `NAO_ELEGIVEL`, com dossiê retido |
| A6 | **Gerar checklist documental** | Tarefa de regra de negócio (DMN `dmn-requisitos-documentais`) | L4 | `REG-KYC-10` `REG-KYC-11` `REG-KYC-12` | Deriva do perfil × residência × canal; apresentada ao cliente antes de A7 |
| A7 | Apresentar checklist e estimativa de prazo | Tarefa de serviço | L2 | — | Ponto de abandono deliberadamente antecipado |

### Fase B — Recolha e Verificação de Identidade (`PRC-01.2`)

| # | Elemento | Tipo BPMN | Pista | `REG-*` | Notas |
|---|---|---|---|---|---|
| B1 | Capturar documento de identificação (frente e verso) | Tarefa de utilizador (app) | L1 | `REG-KYC-10` | Qualidade validada no dispositivo antes do envio |
| B2 | **Extrair e estruturar dados do documento** (OCR/MRZ) | Tarefa de serviço | L5 | `REG-KYC-01` | Pré-preenche a ficha; o cliente confirma, não redigita |
| B3 | **Validar autenticidade e validade do documento** | Tarefa de serviço | L5 | `REG-KYC-10` | Verifica tipo, entidade emissora e `dataExpiracao` não vencida |
| B4 | Capturar selfie com **prova de vida** | Tarefa de utilizador (app) | L1 | `REG-ABR-05` | Requisito técnico do canal remoto |
| B5 | **Correspondência facial** documento × selfie | Tarefa de serviço | L5 | `REG-KYC-10` | Pontuação registada como evidência |
| B6 | Identidade comprovada? | *Gateway* exclusivo | L4 | `REG-KYC-09` | Abaixo do limiar → B7 |
| B7 | Revisão manual de identidade | Tarefa de utilizador | L7 | `REG-KYC-09` | Prazo máximo definido; sem resolução → estado terminal de recusa |
| B8 | Dúvida persiste? | *Gateway* exclusivo | L4 | `REG-KYC-09` | Sim → **`RECUSADO_IDENTIDADE_NAO_COMPROVADA`** (terminal, obrigatório) |

### Fase C — Ficha de Cliente e Documentação (`PRC-01.3`)

| # | Elemento | Tipo BPMN | Pista | `REG-*` | Notas |
|---|---|---|---|---|---|
| C1 | Confirmar e completar Ficha de Cliente | Tarefa de utilizador (app) | L1 | `REG-KYC-01` | Todos os campos do Anexo I I.1; validação campo a campo |
| C2 | Declarar **motivo da abertura** | Tarefa de utilizador (app) | L1 | `REG-KYC-02` | Vocabulário controlado + texto livre |
| C3 | Declarar natureza e montante do rendimento | Tarefa de utilizador (app) | L1 | `REG-KYC-01` | Entrada do *scoring* de risco |
| C4 | Submeter documentos da checklist | Tarefa de utilizador (subprocesso multi-instância) | L1 | `REG-KYC-10` `REG-KYC-11` | Uma instância por `RequisitoDocumental`; paralelo |
| C5 | **Verificar cada documento** | Tarefa de serviço + utilizador em excepção | L5 / L6 | `REG-KYC-10` | Verificação automática; rejeição abre C6 |
| C6 | Pedido de Informação Adicional (RFI) | Tarefa de utilizador + temporizador | L1 | — | *Stop-the-clock* no SLA interno; expira e encerra como `EXPIRADO` |
| C7 | Checklist completa? | *Gateway* exclusivo | L4 | `REG-KYC-10` | Não → volta a C4 |

### Fase D — Partes Relacionadas, PEP e Risco (`PRC-01.4`, `PRC-01.5`)

| # | Elemento | Tipo BPMN | Pista | `REG-*` | Notas |
|---|---|---|---|---|---|
| D1 | Identificar representantes legais e procuradores | Subprocesso (condicional) | L1 | `REG-ABR-02` | Obrigatório se titular menor ou pessoa colectiva |
| D2 | Identificar titulares de participação ≥ 20% | Subprocesso (condicional) | L1 | `REG-KYC-06` | Só pessoa colectiva; soma de participações validada |
| D3 | Identificar **beneficiário efectivo** | Subprocesso (condicional) | L1 | `REG-KYC-07` | Campos completos do Anexo I I.4 |
| D4 | **Triagem PEP de todas as partes** | Tarefa de serviço (multi-instância paralela) | L5 | `REG-KYC-08` | Cliente + representantes + BE; categoria `PEP_I/II/III` registada |
| D5 | **Rastreio de sanções** | Tarefa de serviço (multi-instância paralela) | L5 | Lei n.º 5/20 | Complementa, não substitui, D4 |
| D6 | **Calcular pontuação de risco BC/FT/FPADM** | Tarefa de regra de negócio (DMN `dmn-risco-bcft`) | L4 | `REG-KYC-05` | Entradas: perfil, residência, PEP, sanções, rendimento, motivo, canal, produto |
| D7 | **Determinar nível de diligência** | Tarefa de regra de negócio (DMN `dmn-nivel-diligencia`) | L4 | Lei n.º 5/20 | `SIMPLIFICADA` / `NORMAL` / `REFORCADA` |
| D8 | Nível de diligência | *Gateway* exclusivo | L4 | — | `REFORCADA` → D9 |
| D9 | **Diligência reforçada e aprovação de nível superior** | Tarefa de utilizador | L8 | `REG-KYC-08` | Obrigatoriamente humana; fundamento registado |
| D10 | Aprovado? | *Gateway* exclusivo | L4 | — | Não → `RECUSADO_RISCO` (terminal, fundamentado) |

### Fase E — Informação Pré-Contratual e Celebração (`PRC-01.6`)

| # | Elemento | Tipo BPMN | Pista | `REG-*` | Notas |
|---|---|---|---|---|---|
| E1 | Selecionar regime de titularidade e movimentação | Tarefa de utilizador (app) | L1 | `REG-ABR-08` | Singular / solidária / conjunta / mista |
| E2 | **Gerar Condições Gerais, Particulares e Ficha Técnica Informativa** | Tarefa de serviço | L4 | `REG-INF-03` `REG-INF-04` | A partir de minuta versionada da instituição |
| E3 | **Validar cobertura dos 13 temas** | Tarefa de regra de negócio (DMN `dmn-cobertura-cg`) | L4 | `REG-INF-03` | 13 asserções `CG-A`…`CG-M`; falha bloqueia o processo |
| E4 | **Disponibilizar CG, CP e FTI ao cliente** | Tarefa de serviço | L2 | `REG-INF-01` `REG-INF-04` | Registra `timestampDisponibilizacao` |
| E5 | **Arquivar evidência de disponibilização** | Tarefa de serviço | L10 | `REG-INF-02` | Versão, canal, timestamp, hash — imutável |
| E6 | Cliente confirma leitura | Tarefa de utilizador (app) | L1 | `REG-INF-01` | |
| E7 | Sabe e pode assinar? | *Gateway* exclusivo | L4 | `REG-KYC-04` | Não → E9 |
| E8 | Assinar Ficha de Cliente e contrato | Tarefa de utilizador (app) | L1 | `REG-KYC-03` | Assinatura vinculada ao hash do documento |
| E9 | **Assinatura por meio biométrico** | Tarefa de utilizador (app) | L1 | `REG-KYC-04` | Caminho normativo, não excepção |
| E10 | **Verificar anterioridade da disponibilização** | *Gateway* exclusivo + regra | L4 | `REG-INF-01` | `timestampDisponibilizacao < timestampCelebracao`; falso → excepção bloqueante |
| E11 | **Celebrar Contrato de Abertura de Conta** | Tarefa de serviço | L4 | `REG-ABR-07` | Junção das três peças; hash do conjunto selado |

### Fase F — Entrega Inicial de Fundos e Activação (`PRC-01.7`)

| # | Elemento | Tipo BPMN | Pista | `REG-*` | Notas |
|---|---|---|---|---|---|
| F1 | **Criar conta no core bancário** (estado `ABERTA_NAO_ACTIVA`) | Tarefa de serviço | L9 | `REG-ABR-07` | Idempotente; devolve número de conta e IBAN |
| F2 | Marcar conta de não residente | Tarefa de serviço | L9 | `REG-ABR-03` | Atributo estruturado, não texto livre |
| F3 | Comunicar IBAN e instruções de entrega inicial | Tarefa de serviço | L2 | `REG-FUN-01` | |
| F4 | Aguardar entrega inicial de fundos | Evento intermédio de mensagem + temporizador | L4 | `REG-FUN-01` | Expiração → `EXPIRADO` com dossiê retido |
| F5 | **Verificar meio de entrega** | Tarefa de regra de negócio (DMN `dmn-entrega-fundos`) | L4 | `REG-FUN-01` | Canal remoto: só transferência com identificação do ordenante |
| F6 | **Verificar elegibilidade da instituição de origem** | Tarefa de serviço | L4 | `REG-FUN-02` | Consulta ao registo de instituições com aferição documentada |
| F7 | Ordenante = titular? | *Gateway* exclusivo | L4 | `REG-FUN-03` | Não → F8 |
| F8 | Recolher **justificação credível** | Tarefa de utilizador (app) | L1 | `REG-FUN-03` | Documento `DOC_JUSTIFICACAO_FUNDOS_TERCEIRO` |
| F9 | **Avaliar justificação credível** | Tarefa de utilizador | L7 | `REG-FUN-03` | Decisão humana obrigatória, com fundamento registado |
| F10 | Justificação aceite? | *Gateway* exclusivo | L4 | `REG-FUN-03` | Não → `RECUSADO_FUNDOS` e devolução dos fundos |
| F11 | **Activar conta** | Tarefa de serviço | L9 | `REG-ABR-07` | Estado `ACTIVA` |
| F12 | Registar preferências de extracto e canal | Tarefa de serviço | L9 | `REG-INF-05` | |
| F13 | Notificar cliente da activação | Tarefa de serviço | L2 | — | |

### Fase G — Arquivo e Retenção (`PRC-01.8`)

| # | Elemento | Tipo BPMN | Pista | `REG-*` | Notas |
|---|---|---|---|---|---|
| G1 | **Selar Dossiê de Abertura** | Tarefa de serviço | L10 | `REG-RET-01` `REG-RET-03` | Manifesto com hash de todos os artefactos |
| G2 | Aplicar política de retenção ≥ 10 anos | Tarefa de serviço | L10 | `REG-RET-01` | WORM; expurgo bloqueado antes do prazo |
| G3 | Indexar para recuperação atempada | Tarefa de serviço | L10 | `REG-RET-02` | Índice pesquisável por cliente, NIF, conta, data, estado |
| G4 | Fechar registo de auditoria da instância | Tarefa de serviço | L10 | `REG-RET-02` | Cadeia de hash encadeada |

> **G1–G4 executam em todos os estados terminais**, incluindo recusa, desistência e expiração. Um pedido recusado sem dossiê retido é uma instância incompleta.

## 4. Máquina de estados do Pedido de Abertura

```
                    INICIADO
                       │
                       ▼
                 EM_ELEGIBILIDADE ──────────────► NAO_ELEGIVEL ●
                       │
                       ▼
              EM_VERIFICACAO_IDENTIDADE ─────────► RECUSADO_IDENTIDADE_NAO_COMPROVADA ●
                       │
                       ▼
                EM_RECOLHA_DOCUMENTAL ◄──┐
                       │                 │ RFI
                       ▼                 │
              AGUARDA_CLIENTE ───────────┘
                       │  (temporizador expira)
                       ├──────────────────────────► EXPIRADO ●
                       ▼
                 EM_ANALISE_RISCO ────────────────► RECUSADO_RISCO ●
                       │
                       ▼
              EM_DILIGENCIA_REFORCADA ────────────► RECUSADO_RISCO ●
                       │
                       ▼
                 EM_CELEBRACAO
                       │
                       ▼
              AGUARDA_FUNDOS_INICIAIS
                       │  (temporizador expira → EXPIRADO ●)
                       ▼
              EM_VERIFICACAO_FUNDOS ──────────────► RECUSADO_FUNDOS ●
                       │
                       ▼
                     ACTIVA ●

   Transição disponível em qualquer estado não terminal:
                       └──────────────────────────► DESISTIDO ●

   ● = estado terminal. Todo estado terminal executa G1–G4 (selar e retar dossiê).
```

**Invariantes verificados por teste:**

| # | Invariante |
|---|---|
| `INV-01` | Nenhuma transição para `ACTIVA` sem `ContratoAberturaConta` com as três peças referenciadas (`REG-ABR-07`) |
| `INV-02` | Nenhuma transição para `EM_CELEBRACAO` sem evidência de disponibilização de CG/CP com timestamp anterior (`REG-INF-01`) |
| `INV-03` | Nenhuma transição para `ACTIVA` sem triagem PEP concluída para **todas** as partes (`REG-KYC-08`) |
| `INV-04` | Nenhuma transição para `ACTIVA` sem checklist documental integralmente `VERIFICADO` (`REG-KYC-10`) |
| `INV-05` | Nenhuma transição para `ACTIVA` sem entrega inicial de fundos verificada (`REG-FUN-01`, `REG-FUN-02`) |
| `INV-06` | Dúvida de identidade não resolvida conduz obrigatoriamente a `RECUSADO_IDENTIDADE_NAO_COMPROVADA`, nunca a estado de espera indefinido (`REG-KYC-09`) |
| `INV-07` | Todo estado terminal tem dossiê selado com política de retenção ≥ 10 anos aplicada (`REG-RET-01`) |
| `INV-08` | Nível de diligência `REFORCADA` exige aprovação humana registada de perfil `CHEFE_COMPLIANCE` |
| `INV-09` | Toda decisão automática referencia a versão da decisão DMN que a produziu |
| `INV-10` | Nenhuma instância permanece em estado não terminal sem temporizador activo |

## 5. Decisões DMN

| Decisão | Ficheiro | Entradas | Saída | Política de acerto |
|---|---|---|---|---|
| `dmn-elegibilidade` | `bpmn/dmn/elegibilidade.dmn` | tipo de pessoa, idade, residência, residência cambial, moeda, produto, canal | elegível, perfil de cliente, produtos admissíveis, motivo de recusa | *Unique* |
| `dmn-requisitos-documentais` | `bpmn/dmn/requisitos-documentais.dmn` | perfil de cliente, residência, nacionalidade, menoridade, canal | lista de `RequisitoDocumental` | *Collect* |
| `dmn-ubo-threshold` | `bpmn/dmn/ubo-threshold.dmn` | percentagem de participação, direitos de voto, cadeia de controlo | identificar como titular ≥ 20%, identificar como BE | *Collect* |
| `dmn-pep-categoria` | `bpmn/dmn/pep-categoria.dmn` | cargo declarado, relação familiar, relação societária | categoria `PEP_I/II/III`, nível de exposição | *First* |
| `dmn-risco-bcft` | `bpmn/dmn/risco-bcft.dmn` | perfil, residência, PEP, sanções, natureza e montante do rendimento, motivo, canal, produto, moeda | pontuação, nível de risco | *Collect (sum)* |
| `dmn-nivel-diligencia` | `bpmn/dmn/nivel-diligencia.dmn` | nível de risco, PEP, produto, canal | nível de diligência, aprovações exigidas | *Priority* |
| `dmn-entrega-fundos` | `bpmn/dmn/entrega-fundos.dmn` | canal, meio de entrega, ordenante identificado, instituição de origem elegível, ordenante = titular | aceitar, exigir justificação, rejeitar | *Unique* |
| `dmn-cobertura-cg` | `bpmn/dmn/cobertura-condicoes-gerais.dmn` | versão da minuta, temas presentes | conforme, temas em falta | *Unique* |
| `dmn-limites-menor` | `bpmn/dmn/limites-menor.dmn` | idade, produto solicitado, termo de responsabilidade presente | cartão permitido, limite diário máximo, produtos vedados | *Unique* |

**Por que DMN e não código.** Os limiares deste domínio são regulatórios e mudam por acto normativo: os 20% do Anexo I, os 24 meses do Art. 7.º, os 14 anos do Art. 9.º, os 60 dias do Art. 13.º n.º 4, os 15 anos do Art. 13.º n.º 8, os 10 anos do Anexo I. Cada um deles deve ser alterável por publicação de nova versão de decisão, com registo de quem alterou, quando e sob que fundamento — sem ciclo de desenvolvimento e sem recompilação. Ver `ADR-0003`.

## 6. Variantes de processo

O mesmo modelo BPMN suporta todas as variantes; a diferença está nas decisões DMN e nos subprocessos condicionais activados. Um modelo por variante seria ingovernável.

| Variante | Diferenças face ao fluxo base | Fases activadas adicionalmente |
|---|---|---|
| **Pessoa singular residente, remoto** | Fluxo base | — |
| **Pessoa singular não residente** | Passaporte obrigatório (excepto nacional angolano com BI); marcação de não residente | `REG-ABR-03` reforçado |
| **Menor** | Representante legal obrigatório; catálogo de produtos restrito; cartão só ≥ 14 anos com termo de responsabilidade; limite diário | D1, `dmn-limites-menor` |
| **Comerciante em nome individual** | Campos de I.1.1; relatório de sustentabilidade | Campos adicionais em C1 |
| **Pessoa colectiva** | Certidão de registo comercial; titulares ≥ 20%; procuradores e mandato; BE obrigatório | D1, D2, D3 |
| **Pessoa colectiva não residente** | Registo certificado no país de residência **e** autenticado consularmente | D2, D3 + dupla validação |
| **Organização sem fins lucrativos** | Campos de I.2.1; risco base elevado | D1, D2, D3 + `dmn-risco-bcft` |
| **Instituição de caridade sem personalidade jurídica / igreja / local de culto** | Campos de I.3; documento de legalização estatal; gestores e classes de beneficiários | D1, D3 |
| **Condomínio / património autónomo** | Tratado como pessoa colectiva (Anexo I I.2 aplica expressamente) | D1, D2, D3 |
| **Canal presencial (balcão)** | Verificação de identidade presencial substitui B4–B5; captura na app do gestor | Sem prova de vida remota |
| **Canal entidade terceira mandatada** | Prova de mandato legal ou contratual obrigatória antes de A2 | Validação de mandato |
| **Cliente que não sabe ou não pode assinar** | E9 em vez de E8 | Assinatura biométrica |
| **Cliente PEP** | Diligência reforçada obrigatória; aprovação de Chefe de Compliance | D9 |

## 7. Tratamento de excepções

| Tipo | Natureza BPMN | Tratamento |
|---|---|---|
| Serviço externo indisponível (OCR, listas, core) | Falha técnica | Retentativa com espera exponencial; incidente após N tentativas; instância permanece consistente |
| Documento ilegível ou rejeitado | Erro de negócio | RFI ao cliente com prazo; *stop-the-clock* |
| Correspondência facial abaixo do limiar | Erro de negócio | Revisão manual (B7); se persistir, recusa (`INV-06`) |
| Soma de participações inconsistente | Erro de negócio | Tarefa de utilizador em Compliance |
| Cobertura dos 13 temas falha | Erro bloqueante | Instância suspensa e incidente para a Direcção Jurídica — nunca contornável |
| Timestamp de disponibilização posterior à celebração | Erro bloqueante | Impede celebração; incidente de conformidade |
| Cliente inactivo | Temporizador | Recordatório → segundo recordatório → `EXPIRADO` |
| Fundos não recebidos no prazo | Temporizador | Recordatório → `EXPIRADO`; conta `ABERTA_NAO_ACTIVA` encerrada |
| Falha após criação da conta no core | Compensação (Saga) | Reversão do estado no core; conta nunca fica activa órfã |

## 8. Desempenho alvo

| Indicador | AS-IS† | Alvo TO-BE | Como se atinge |
|---|---|---|---|
| Tempo de Ciclo — PS residente, risco baixo, remoto | 3–5 dias úteis | **< 15 minutos** ponta-a-ponta (exclui espera pela transferência de fundos) | STP completo |
| Tempo de Ciclo — PS, risco elevado / PEP | 20–45 dias | **< 2 dias úteis** | Fila priorizada com prazo |
| Tempo de Ciclo — pessoa colectiva | 15–30 dias | **< 5 dias úteis** | Checklist antecipada + verificação paralela |
| Tempo de Processamento humano por conta (risco baixo) | 3–5 h | **< 5 min** (zero, no percurso STP) | Automatização de BVA |
| **Eficiência do Ciclo** | 2–5% | **> 60%** | Eliminação de esperas por passagem |
| **STP** | 0% | **> 60%** das instâncias de risco baixo | Diligência proporcional ao risco |
| **FTR** | 40–60% | **> 90%** | Checklist antecipada + validação na origem |
| Taxa de abandono | 15–30% | **< 10%**, e concentrada na fase A | Falhar cedo |
| Retrabalho | 20–35% | **< 5%** | Recolha única e validada |
| Tempo de resposta a pedido de autoridade competente | 2 h – 5 dias | **< 5 minutos** por dossiê | Índice pesquisável |
| Cobertura de evidência de conformidade | Parcial, em papel | **100% dos `REG-*` de escopo `S`**, verificável por teste | Conformidade por desenho |

## 9. Conformidade por desenho — mapa de controlos

Como cada risco do AS-IS é neutralizado por um elemento concreto do TO-BE:

| Risco AS-IS | Controlo TO-BE | Elemento | Falha do controlo |
|---|---|---|---|
| `RAI-01` Anterioridade das CG não provável | Timestamp e hash em E4/E5; *gateway* de anterioridade em E10 | E4, E5, E10, `INV-02` | Bloqueia a celebração |
| `RAI-02` Triagem PEP dependente de pessoa | Triagem sistemática de todas as partes com categoria registada | D4, `dmn-pep-categoria`, `INV-03` | Bloqueia activação |
| `RAI-03` Titulares ≥ 20% e BE por leitura de acta | Cálculo de participações e decisão explícita | D2, D3, `dmn-ubo-threshold` | Excepção para Compliance |
| `RAI-04` Elegibilidade da instituição de origem não registada | Registo de instituições com aferição documentada | F6, `INV-05` | Bloqueia activação |
| `RAI-05` Divergência ordenante × titular por inspecção visual | Comparação automática + decisão humana registada | F7, F8, F9 | Bloqueia activação |
| `RAI-06` Pendente indefinido em dúvida de identidade | Estado terminal obrigatório | B8, `INV-06` | Estrutural: o estado não existe |
| `RAI-07` Retenção em papel sem índice | Dossiê selado, WORM, índice pesquisável | G1–G4, `INV-07` | Instância não fecha |
| `RAI-08` Cobertura dos 13 temas não verificada | Validação por instância | E3, `dmn-cobertura-cg` | Suspende a instância |
| `RAI-09` Não residente em campo de texto livre | Atributo estruturado propagado ao core | F2 | Contrato de integração rejeita |
| `RAI-10` Assinatura biométrica como excepção informal | Caminho normativo modelado | E7, E9 | — |
| `RAI-11` Sem trilho de auditoria unificado | Registo encadeado por hash de toda a instância | G4, `INV-09` | Instância não fecha |
| `RAI-12` Abertura remota não praticada | Canal `REMOTO` como fluxo primário | Fase B completa | — |

## 10. O que permanece humano, e porquê

Automatizar julgamento é o modo mais rápido de criar risco regulatório. Estas actividades permanecem humanas por decisão de desenho, não por limitação técnica:

| Actividade | Porquê |
|---|---|
| Aprovação de diligência reforçada de PEP (D9) | A Lei n.º 5/20 exige decisão de nível hierárquico superior; é responsabilidade pessoal, não resultado de algoritmo |
| Avaliação de justificação credível de fundos de terceiro (F9) | "Credível" é um juízo de razoabilidade sobre a narrativa apresentada; não é reduzível a regra |
| Revisão manual de identidade duvidosa (B7) | O Anexo I ponto 4 impõe recusa quando a dúvida não é resolúvel — determinar se é resolúvel é julgamento |
| Aceitação de meio ou diligência "idóneo e suficiente" para morada e profissão (C5, excepção) | A norma delega expressamente na instituição; a fundamentação da idoneidade tem de ter autor |
| Resolução de inconsistências societárias (D2, excepção) | Estruturas de controlo reais raramente encaixam em tabela |
