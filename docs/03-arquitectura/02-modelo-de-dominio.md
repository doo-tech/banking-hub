# Modelo de Domínio

> Nomes de classes, tabelas, campos e variáveis de processo seguem o glossário (`docs/02-bpm/01-glossario.md`) em português. Traduzir para inglês na camada técnica reintroduz a ambiguidade que o glossário eliminou.

---

## 1. Contextos delimitados

| Contexto | Módulo | Responsabilidade | Agregado raiz |
|---|---|---|---|
| Pedido | `bh-orchestration` | Ciclo de vida e estado do Pedido de Abertura | `PedidoAbertura` |
| Cliente | `bh-customer` | Ficha de Cliente, partes relacionadas, elegibilidade | `FichaCliente` |
| Documento | `bh-document` | Catálogo, checklist, submissão e verificação | `ChecklistDocumental` |
| Diligência | `bh-kyc` | Identidade, PEP, sanções, risco, beneficiário efectivo | `ProcessoDiligencia` |
| Contrato | `bh-contract` | Condições, FTI, disponibilização, assinatura, celebração | `ContratoAberturaConta` |
| Fundos | `bh-funding` | Entrega inicial, ordenante, instituição de origem | `EntregaInicialFundos` |
| Conta | `bh-account` | Conta, IBAN, titularidade, activação, produtos | `ContaBancaria` |
| Arquivo | `bh-archive` | Dossiê, evidências, retenção, índice | `DossieAbertura` |
| Auditoria | `bh-audit` | Cadeia de eventos encadeada por hash | `RegistoAuditoria` |

**Relações entre contextos**

| Origem → Destino | Padrão | Nota |
|---|---|---|
| `bh-orchestration` → todos | Orquestrador / cliente | Coordena; não contém regras de domínio |
| `bh-customer` → `bh-document` | Fornecedor / consumidor | Perfil determina checklist |
| `bh-kyc` → `bh-customer` | Consumidor | Lê partes; escreve resultado de diligência |
| `bh-contract` → `bh-customer` | Consumidor | Ficha é peça do contrato |
| `bh-account` → Core bancário | Adaptador | Traduz domínio ↔ core |
| Todos → `bh-archive` | Fornecedor | Emitem evidências |
| Todos → `bh-audit` | Fornecedor | Emitem eventos auditáveis |

## 2. Agregado `PedidoAbertura` — raiz do processo

```
PedidoAbertura                                   « agregado raiz »
├── pedidoId              : PedidoId             « identidade »
├── tenantId              : TenantId             « instituição — obrigatório »
├── canalOrigem           : CanalOrigem          { PRESENCIAL, REMOTO, TERCEIRO_MANDATADO }
├── perfilCliente         : PerfilCliente
├── estado                : EstadoPedido         « máquina de estados fechada »
├── dataInicio            : Instant
├── dataEstadoTerminal    : Instant?
├── versaoProcesso        : VersaoRecurso        « BPMN — persistida »
├── versaoRegras          : VersaoRecurso        « DMN — persistida »
├── fichaClienteRef       : Referencia<FichaCliente>
├── checklistRef          : Referencia<ChecklistDocumental>
├── diligenciaRef         : Referencia<ProcessoDiligencia>
├── contratoRef           : Referencia<ContratoAberturaConta>
├── entregaFundosRef      : Referencia<EntregaInicialFundos>?
├── contaRef              : Referencia<ContaBancaria>?
├── motivoTerminal        : MotivoTerminal?
└── temporizadorActivo    : Temporizador?        « INV-10 »
```

**`EstadoPedido`** — conjunto fechado, transições validadas pelo agregado:

`INICIADO` · `EM_ELEGIBILIDADE` · `EM_VERIFICACAO_IDENTIDADE` · `EM_RECOLHA_DOCUMENTAL` · `AGUARDA_CLIENTE` · `EM_ANALISE_RISCO` · `EM_DILIGENCIA_REFORCADA` · `EM_CELEBRACAO` · `AGUARDA_FUNDOS_INICIAIS` · `EM_VERIFICACAO_FUNDOS` · **`ACTIVA`** ● · **`NAO_ELEGIVEL`** ● · **`RECUSADO_IDENTIDADE_NAO_COMPROVADA`** ● · **`RECUSADO_RISCO`** ● · **`RECUSADO_FUNDOS`** ● · **`DESISTIDO`** ● · **`EXPIRADO`** ●

## 3. Agregado `FichaCliente`

```
FichaCliente                                     « agregado raiz »
├── fichaId, tenantId, pedidoId
├── perfilCliente         : PerfilCliente
├── motivoAbertura        : MotivoAbertura        « REG-KYC-02 — obrigatório »
├── titulares             : List<Titular>         « ≥ 1; ≥ 2 se regime colectivo »
├── representantesLegais  : List<RepresentanteLegal>
├── procuradores          : List<Procurador>
├── assinaturas           : List<Assinatura>
└── estado                : { RASCUNHO, SUBMETIDA, ASSINADA }

Titular                                          « entidade »
├── papel                 : { TITULAR, CO_TITULAR }
├── parte                 : Parte                 « polimórfico »
└── percentagemParticipacao : Percentagem?        « pessoa colectiva »

Parte                                            « hierarquia »
├── PessoaSingular
├── ComercianteNomeIndividual  : PessoaSingular + DadosComerciais
├── PessoaColectiva
├── OrganizacaoSemFinsLucrativos : PessoaColectiva + DadosOsfl
└── InstituicaoCaridadeSemPersonalidade
```

### `PessoaSingular` — 15 campos mínimos do Anexo I, I.1

```
PessoaSingular
├── nomeCompleto              : String            « I.1.1 »
├── dataNascimento            : LocalDate         « I.1.3 »
├── nacionalidade             : CodigoPais        « I.1.4 »
├── naturalidade              : String            « I.1.5 »
├── moradaHabitual            : Morada            « I.1.6 »
├── moradaAlternativa         : Morada?           « I.1.7 »
├── profissao                 : String            « I.1.8 »
├── entidadePatronal          : String?           « I.1.8 — "se aplicável" »
├── docIdentificacao          : DocumentoIdentificacao  « I.1.9–12 »
├── rendimento                : Rendimento        « I.1.13 »
├── nif                       : Nif               « I.1.14 »
├── pep                       : QualificacaoPep   « I.1.15 »
├── residenciaFiscal          : Residencia
├── residenciaCambial         : Residencia        « Lei n.º 5/97 — distinta »
└── menor                     : boolean           « derivado de dataNascimento »

Morada                                           « objecto de valor »
├── enderecoCompleto          : String
├── pontoReferencia           : String            « campo distinto e obrigatório — BR-FIC-02 »
├── municipio, provincia, pais

DocumentoIdentificacao                           « objecto de valor »
├── tipo                      : TipoDocIdentificacao  { BILHETE_IDENTIDADE, CARTAO_RESIDENTE,
│                                                       PASSAPORTE, CEDULA_PESSOAL,
│                                                       DOC_PUBLICO_EQUIVALENTE }
├── numero, dataExpiracao, entidadeEmissora
└── valido()                  : boolean           « BR-FIC-04 — não expirado »

Rendimento                                       « objecto de valor »
├── natureza                  : NaturezaRendimento
└── montante                  : Dinheiro

QualificacaoPep                                  « objecto de valor »
├── estado                    : { NAO_PEP, PEP, PEP_RELACIONADO }
├── categoria                 : CategoriaPep?     « PEP_I_01..11, PEP_II_01..02, PEP_III_01..02 »
├── dataAfericao              : Instant
└── fonte                     : FontePep          « BR-PEP-05 — histórico, não sobrescreve »
```

### `PessoaColectiva` — 7 campos do Anexo I, I.2

```
PessoaColectiva
├── denominacaoSocial, objectoSocial, finalidadeNegocio
├── enderecoSede              : Morada
├── nif                       : Nif
├── matriculaRegistoComercial : String
├── titularesParticipacao     : List<TitularParticipacao>   « BR-UBO-01 — ≥ 20% »
├── procuradores              : List<Procurador>
├── residencia                : Residencia
├── entidadeExportadora       : boolean           « REG-FX-03 »
└── subtipo                   : { SOCIEDADE, OSFL, CONDOMINIO, PATRIMONIO_AUTONOMO }
```

## 4. Agregado `ProcessoDiligencia`

```
ProcessoDiligencia                               « agregado raiz »
├── diligenciaId, tenantId, pedidoId
├── verificacoesIdentidade    : List<VerificacaoIdentidade>
├── triagensPep               : List<TriagemPep>          « uma por parte — BR-PEP-01 »
├── rastreiosSancoes          : List<RastreioSancoes>
├── beneficiariosEfectivos    : List<BeneficiarioEfectivo>
├── pontuacaoRisco            : PontuacaoRisco
├── nivelDiligencia           : NivelDiligencia   { SIMPLIFICADA, NORMAL, REFORCADA }
├── aprovacaoDiligenciaReforcada : Aprovacao?     « INV-08 »
└── estado                    : { EM_CURSO, CONCLUIDA, RECUSADA }

VerificacaoIdentidade                            « entidade »
├── parteId
├── extraccaoDocumento        : ResultadoOcr
├── validacaoDocumento        : ResultadoValidacao
├── provaVida                 : ResultadoProvaVida?       « canal REMOTO »
├── correspondenciaFacial     : ResultadoCorrespondencia?
├── revisaoManual             : RevisaoManual?
└── desfecho                  : { COMPROVADA, NAO_COMPROVADA }   « BR-KYC-09 »

PontuacaoRisco                                   « objecto de valor »
├── valor, nivel              : { BAIXO, MEDIO, ALTO }
├── factores                  : List<FactorRisco>         « explicabilidade »
└── versaoDecisao             : VersaoRecurso     « INV-09 — obrigatório »

Aprovacao                                        « objecto de valor »
├── autor                     : UtilizadorId      « perfil CHEFE_COMPLIANCE »
├── perfil, decisao, fundamento : String          « obrigatório e não vazio »
└── timestamp
```

## 5. Agregado `ContratoAberturaConta`

O invariante central do domínio (`REG-ABR-07`): as três peças ou não há contrato.

```
ContratoAberturaConta                            « agregado raiz »
├── contratoId, tenantId, pedidoId
├── fichaClienteRef           : Referencia<FichaCliente>       « peça 1 — obrigatória »
├── condicoesGerais           : CondicoesGerais                « peça 2 — obrigatória »
├── condicoesParticulares     : CondicoesParticulares?         « peça 3 — quando aplicável »
├── fichaTecnicaInformativa   : FichaTecnicaInformativa        « REG-INF-04 »
├── disponibilizacao          : EvidenciaDisponibilizacao      « REG-INF-02 »
├── confirmacaoLeitura        : ConfirmacaoLeitura
├── assinaturas               : List<Assinatura>
├── timestampCelebracao       : Instant?
├── hashConjunto              : Hash?
└── estado                    : { EM_PREPARACAO, DISPONIBILIZADO, ASSINADO, CELEBRADO }

  invariante INV-01: celebrar() exige fichaClienteRef ∧ condicoesGerais
                     ∧ (condicoesParticulares se aplicável) ∧ assinaturas completas
  invariante INV-02: celebrar() exige
                     disponibilizacao.timestamp < timestampCelebracao

CondicoesGerais                                  « objecto de valor »
├── versaoMinuta              : VersaoRecurso     « BR-INF-05 — congelada por pedido »
├── temasCobertos             : Set<TemaCondicoesGerais>
└── conforme()                : boolean           « 13 temas CG_A..CG_M — BR-INF-03 »

EvidenciaDisponibilizacao                        « objecto de valor imutável »
├── timestamp, canal, versaoDocumento, hashDocumento, destinatario
```

## 6. Agregado `EntregaInicialFundos`

```
EntregaInicialFundos                             « agregado raiz »
├── entregaId, tenantId, pedidoId
├── meioEntrega               : MeioEntrega       { TRANSFERENCIA_BANCARIA, NUMERARIO, OUTRO }
├── ordenante                 : Ordenante
├── instituicaoOrigem         : InstituicaoOrigem
├── montante                  : Dinheiro
├── ordenanteCoincideComTitular : boolean         « BR-FUN-03 »
├── justificacaoCredivel      : JustificacaoCredivel?
├── avaliacaoJustificacao     : Aprovacao?        « decisão humana obrigatória »
└── desfecho                  : { ACEITE, RECUSADA, PENDENTE }

InstituicaoOrigem                                « objecto de valor »
├── nome, codigoInstituicao
├── aplicaDiligencia          : boolean           « BR-FUN-02 »
└── evidenciaAfericao         : Referencia<Evidencia>   « obrigatória se aplicaDiligencia »
```

## 7. Agregado `ContaBancaria`

```
ContaBancaria                                    « agregado raiz »
├── contaId, tenantId, pedidoId
├── numeroConta, iban
├── moeda                     : Moeda
├── regimeTitularidade        : RegimeTitularidade   « BR-ELE-04 »
├── regimeMovimentacao        : RegimeMovimentacao?  « SOLIDARIA|CONJUNTA|MISTA se colectiva »
├── titulares                 : List<Referencia<Parte>>   « BR-ELE-05 — cardinalidade »
├── residenteFlag             : boolean           « BR-ELE-03 — estruturado, não texto »
├── titularMenor              : boolean
├── limiteDiarioCartaoMenor   : Dinheiro?         « BR-MEN-03 »
├── entidadeExportadora       : boolean
├── preferenciaExtracto       : CanalExtracto
├── estado                    : { ABERTA_NAO_ACTIVA, ACTIVA, BLOQUEADA_DEBITO,
│                                DORMENTE, ENCERRADA }
└── dataUltimoMovimentoDebito : Instant?          « base de BR-DOR-01 »
```

## 8. Agregado `DossieAbertura`

```
DossieAbertura                                   « agregado raiz »
├── dossieId, tenantId, pedidoId
├── estadoTerminalPedido      : EstadoPedido      « BR-RET-03 — todo terminal sela »
├── dataSelagem               : Instant
├── artefactos                : List<ArtefactoArquivado>
├── manifesto                 : Manifesto         « hash de todos os artefactos »
├── politicaRetencao          : PoliticaRetencao
└── indice                    : IndiceRecuperacao

PoliticaRetencao                                 « objecto de valor »
├── anosRetencao              : int               « ≥ 10 — BR-RET-01 »
├── dataInicioContagem        : Instant           « = dataEstadoTerminal — BR-RET-02 »
├── dataExpurgoElegivel       : Instant           « derivada »
└── expurgoPermitido()        : boolean           « falso antes da data »

IndiceRecuperacao                                « objecto de valor »
└── chaves: clienteId, nif, numeroConta, dataInicio, dataTerminal, estado   « BR-RET-04 »
```

## 9. Eventos de domínio

Emitidos pelos módulos; consumidos por `bh-orchestration`, `bh-audit` e `bh-archive`.

| Evento | Emissor | Consequência |
|---|---|---|
| `PedidoAberturaIniciado` | `bh-orchestration` | Arranca a instância |
| `ElegibilidadeAvaliada` | `bh-customer` | Prossegue ou termina em `NAO_ELEGIVEL` |
| `ChecklistDocumentalGerada` | `bh-document` | Apresentada ao cliente |
| `DocumentoSubmetido` / `DocumentoVerificado` / `DocumentoRejeitado` | `bh-document` | Progresso ou RFI |
| `IdentidadeComprovada` / `IdentidadeNaoComprovada` | `bh-kyc` | Prossegue ou recusa terminal |
| `TriagemPepConcluida` | `bh-kyc` | Entrada do cálculo de risco |
| `RiscoCalculado` / `NivelDiligenciaDeterminado` | `bh-kyc` | Encaminha para EDD ou prossegue |
| `DiligenciaReforcadaAprovada` / `Recusada` | `bh-kyc` | Prossegue ou `RECUSADO_RISCO` |
| `CondicoesDisponibilizadas` | `bh-contract` | Evidência arquivada |
| `ContratoCelebrado` | `bh-contract` | Habilita criação de conta |
| `ContaCriada` / `ContaActivada` | `bh-account` | Progresso |
| `EntregaFundosRecebida` / `Verificada` / `Recusada` | `bh-funding` | Activação ou recusa |
| `PedidoAberturaConcluido` | `bh-orchestration` | Dispara selagem do dossiê |
| `DossieSelado` | `bh-archive` | Instância encerra |

**Todo evento transporta:** `tenantId`, `pedidoId`, `timestamp`, `versaoProcesso`, `versaoRegras`, `chaveIdempotencia`. **Nenhum evento transporta dados pessoais** — apenas referências (princípio 8 da arquitectura).

## 10. Invariantes verificados no domínio

Os invariantes `INV-01` a `INV-10` do TO-BE são implementados nos agregados, não na camada de orquestração. Motivo: um agregado que aceita estado inválido é um defeito de domínio, e o motor de processos não é o guardião das regras de negócio.

| Invariante | Local de verificação |
|---|---|
| `INV-01` | `ContratoAberturaConta.celebrar()` |
| `INV-02` | `ContratoAberturaConta.celebrar()` |
| `INV-03` | `ProcessoDiligencia.concluir()` |
| `INV-04` | `ChecklistDocumental.completa()` |
| `INV-05` | `ContaBancaria.activar()` |
| `INV-06` | `VerificacaoIdentidade.desfecho` |
| `INV-07` | `DossieAbertura.selar()` |
| `INV-08` | `ProcessoDiligencia.aprovarDiligenciaReforcada()` |
| `INV-09` | `PontuacaoRisco` — construtor exige `versaoDecisao` |
| `INV-10` | `PedidoAbertura.transitarPara()` |
