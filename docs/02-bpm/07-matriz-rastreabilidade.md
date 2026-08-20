# Matriz de Rastreabilidade — Norma → Regra → Processo → Módulo → Teste

> **Para que serve.** Quando o BNA, a auditoria interna ou um auditor externo perguntar *"como provam que cumprem o n.º 1 do artigo 5.º?"*, a resposta é uma linha desta tabela — e essa linha leva a um teste automatizado que falha se o controlo for removido.
>
> **Regra de integridade.** Não pode existir requisito `REG-*` de escopo `S` sem regra, sem elemento de processo, sem módulo responsável e sem teste. A verificação desta integridade é executada na integração contínua.

**Legenda de módulos:** ver `docs/03-arquitectura/03-mapa-de-modulos.md`.

---

## Abertura e elegibilidade

| `REG-*` | Fonte | `BR-*` | Elemento TO-BE | Módulo | Teste |
|---|---|---|---|---|---|
| `REG-ABR-01` | Art. 3.º n.º 1 | `BR-ELE-01` | A3 `dmn-elegibilidade` | `bh-customer` | `ElegibilidadeDmnTest#titularidadePorTipoResidenciaMoeda` |
| `REG-ABR-02` | Art. 3.º n.º 2 | `BR-ELE-02`, `BR-MEN-01` | A3, D1 | `bh-customer` | `MenorTest#exigeRepresentanteLegalVerificado` |
| `REG-ABR-03` | Art. 3.º n.º 3 | `BR-ELE-03` | F2 | `bh-account` | `NaoResidenteTest#marcacaoEstruturadaPropagadaAoCore` |
| `REG-ABR-04` | Art. 3.º n.º 4 | — | A0 | `bh-orchestration` | `CanalTest#presencialERemotoNoMesmoProcesso` |
| `REG-ABR-05` | Art. 3.º n.º 5 | — | Fase B completa | `bh-kyc` | `CanalRemotoTest#nenhumPassoExigePresencaFisica` |
| `REG-ABR-06` | Art. 3.º n.º 6 | `BR-ELE-06` | A2 | `bh-customer` | `TerceiroMandatadoTest#exigeProvaDeMandato` |
| `REG-ABR-07` | Art. 2.º g) | `BR-INF-04`, `BR-FUN-04` | E11, F1, F11 | `bh-contract`, `bh-account` | `ContratoTest#celebracaoExigeTresPecas` · `INV-01` |
| `REG-ABR-08` | Art. 2.º e), f) | `BR-ELE-04`, `BR-ELE-05` | E1 | `bh-account` | `RegimeTitularidadeTest#cardinalidadePorRegime` |

## Identificação e diligência

| `REG-*` | Fonte | `BR-*` | Elemento TO-BE | Módulo | Teste |
|---|---|---|---|---|---|
| `REG-KYC-01` | Art. 4.º n.º 1; Anexo I | `BR-FIC-01`, `BR-FIC-02`, `BR-FIC-07`–`BR-FIC-10` | B2, C1, C3 | `bh-customer` | `FichaClienteTest#camposMinimosPorPerfil` (7 perfis) |
| `REG-KYC-02` | Art. 4.º n.º 1 | `BR-FIC-03` | C2 | `bh-customer` | `FichaClienteTest#motivoAberturaObrigatorio` |
| `REG-KYC-03` | Art. 4.º n.º 2 | `BR-ASS-01`, `BR-ASS-03` | E8 | `bh-contract` | `AssinaturaTest#vinculadaAoHashDoDocumento` |
| `REG-KYC-04` | Art. 4.º n.º 3 | `BR-ASS-02` | E7, E9 | `bh-contract` | `AssinaturaBiometricaTest#caminhoNormativoQuandoNaoPodeAssinar` |
| `REG-KYC-05` | Anexo I, I.1 | `BR-RSK-01` | D6 | `bh-kyc` | `RiscoBcFtDmnTest#entradasMinimas` |
| `REG-KYC-06` | Anexo I, I.2 | `BR-UBO-01`, `BR-UBO-04` | D2 `dmn-ubo-threshold` | `bh-kyc` | `UboTest#limiar20PorCento` · `UboTest#somaParticipacoesInvalidaGeraExcepcao` |
| `REG-KYC-07` | Anexo I, I.4 | `BR-UBO-02`, `BR-UBO-03`, `BR-UBO-05` | D3 | `bh-kyc` | `BeneficiarioEfectivoTest#trezeCamposObrigatorios` |
| `REG-KYC-08` | Anexo I, nota 2 | `BR-PEP-01`–`BR-PEP-05` | D4, D9 | `bh-kyc` | `TriagemPepTest#todasAsPartes` · `PepTest#taxonomiaCompleta` · `INV-03`, `INV-08` |
| `REG-KYC-09` | Anexo I, I.1 ponto 4 | `BR-KYC-09`, `BR-KYC-10` | B6, B7, B8 | `bh-kyc` | `IdentidadeTest#duvidaNaoResolvidaConduzARecusa` · `INV-06` |
| `REG-KYC-10` | Anexo I, II | `BR-FIC-04`–`BR-FIC-06`, `BR-FIC-11`, `BR-KYC-11`, `BR-UBO-06` | A6, C4, C5 | `bh-document` | `RequisitosDocumentaisDmnTest#matrizPorPerfilResidenciaCanal` · `INV-04` |
| `REG-KYC-11` | Anexo I, II.1/II.3 | `BR-KYC-12` | C5 excepção | `bh-document` | `MeioIdoneoTest#exigeFundamentacaoComAutor` |
| `REG-KYC-12` | Anexo I, II.2 | `BR-FIC-12` | A6, C5 | `bh-document` | `PessoaColectivaNaoResidenteTest#certificacaoEAutenticacaoCumulativas` |

## Informação pré-contratual

| `REG-*` | Fonte | `BR-*` | Elemento TO-BE | Módulo | Teste |
|---|---|---|---|---|---|
| `REG-INF-01` | Art. 5.º n.º 1 | `BR-INF-01` | E4, E6, E10 | `bh-contract` | `AnterioridadeTest#disponibilizacaoAntesDaCelebracao` · `INV-02` |
| `REG-INF-02` | Art. 5.º n.º 1 | `BR-INF-02` | E5 | `bh-archive` | `EvidenciaTest#versaoCanalTimestampHashImutaveis` |
| `REG-INF-03` | Art. 5.º n.º 2 | `BR-INF-03` | E2, E3 | `bh-contract` | `CoberturaCondicoesGeraisTest#trezeTemas` (13 asserções `CG-A`…`CG-M`) |
| `REG-INF-04` | Art. 5.º n.º 3 | `BR-INF-02` | E2, E4 | `bh-contract` | `FichaTecnicaInformativaTest#geradaEEvidenciada` |
| `REG-INF-05` | Art. 6.º | — | F12 | `bh-account` | `PreferenciaExtractoTest#canalCapturadoNaAbertura` |

## Entrega inicial de fundos

| `REG-*` | Fonte | `BR-*` | Elemento TO-BE | Módulo | Teste |
|---|---|---|---|---|---|
| `REG-FUN-01` | Art. 4.º n.º 4 | `BR-FUN-01`, `BR-FUN-04` | F3, F4, F5 | `bh-funding` | `EntregaFundosDmnTest#remotoExigeTransferenciaComOrdenante` · `INV-05` |
| `REG-FUN-02` | Art. 4.º n.º 4 | `BR-FUN-02` | F6 | `bh-funding` | `InstituicaoOrigemTest#exigeAfericaoDocumentada` |
| `REG-FUN-03` | Art. 3.º n.º 7 | `BR-FUN-03`, `BR-FUN-05` | F7, F8, F9, F10 | `bh-funding` | `FundosTerceiroTest#exigeJustificacaoEDecisaoHumana` |

## Menores

| `REG-*` | Fonte | `BR-*` | Elemento TO-BE | Módulo | Teste |
|---|---|---|---|---|---|
| `REG-MEN-01` | Art. 9.º n.º 1 | `BR-MEN-01` | D1 | `bh-account` | `MenorTest#movimentacaoPorRepresentante` |
| `REG-MEN-02` | Art. 9.º n.º 2 | `BR-MEN-02` | `dmn-limites-menor` | `bh-account` | `LimitesMenorDmnTest#cartaoApenasDesde14AnosComTermo` |
| `REG-MEN-03` | Art. 9.º n.º 3 | `BR-MEN-03` | `dmn-limites-menor` | `bh-account` | `LimitesMenorDmnTest#limiteDiarioPersistidoETransmitido` |
| `REG-MEN-04` | Art. 9.º n.º 4 | `BR-MEN-04` | A3 | `bh-account` | `CatalogoProdutoTest#creditoVedadoEmContaDeMenor` |

## Retenção e auditoria

| `REG-*` | Fonte | `BR-*` | Elemento TO-BE | Módulo | Teste |
|---|---|---|---|---|---|
| `REG-RET-01` | Anexo I, I.1 p.3 | `BR-RET-01`–`BR-RET-03` | G1, G2 | `bh-archive` | `RetencaoTest#expurgoAntesDe10AnosRejeitado` · `RetencaoTest#todoEstadoTerminalSela` · `INV-07` |
| `REG-RET-02` | Anexo I, I.1 p.3 | `BR-RET-04`, `BR-RET-05` | G3, G4 | `bh-archive`, `bh-audit` | `IndiceTest#recuperacaoPorClienteNifContaDataEstado` · `AuditoriaTest#cadeiaDeHashIntegra` · `INV-09` |
| `REG-RET-03` | Anexo I, II | `BR-RET-01` | G1, G2 | `bh-archive` | `WormTest#objectoNaoAlteravelDuranteRetencao` |

## Requisitos modelados, fora do MVP (escopo `N`)

Sem teste de implementação nesta fase; têm teste de **modelo** (o elemento existe no BPMN/DMN e o domínio suporta o conceito).

| `REG-*` | `BR-*` | Modelo | Escopo previsto |
|---|---|---|---|
| `REG-MOV-01` | — | `PRC-03` | 2 |
| `REG-FX-01`–`REG-FX-03` | `BR-FX-01`–`BR-FX-03` | `dmn-fx-transferencias` | 3 |
| `REG-DOR-01`, `REG-DOR-02` | `BR-DOR-01`, `BR-DOR-02` | `PRC-04` | 3 |
| `REG-EVE-01`–`REG-EVE-05` | `BR-EVE-01`–`BR-EVE-03` | `PRC-05` | 3 |
| `REG-ENC-01`–`REG-ENC-09` | `BR-ENC-01`–`BR-ENC-08` | `PRC-06` | 3 |

## Verificação de integridade da matriz

Executada na integração contínua. Falha o *build* quando:

| # | Verificação |
|---|---|
| 1 | Existe `REG-*` de escopo `S` sem `BR-*` associada |
| 2 | Existe `REG-*` de escopo `S` sem elemento TO-BE identificado |
| 3 | Existe `REG-*` de escopo `S` sem módulo responsável |
| 4 | Existe `REG-*` de escopo `S` sem teste nomeado |
| 5 | Existe teste nomeado que não existe no código |
| 6 | Existe `BR-*` no repositório de regras que não aparece nesta matriz |
| 7 | Existe elemento BPMN com anotação `REG-*` inexistente na matriz de requisitos |
| 8 | Existe invariante `INV-*` do TO-BE sem teste correspondente |
