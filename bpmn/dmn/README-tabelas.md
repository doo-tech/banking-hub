# Especificação das Tabelas de Decisão

Conteúdo acordado na Fase 0. Implementado em DMN na actividade `1.8` do roteiro.

---

## `dmn-elegibilidade` — Unique

**Entradas:** `tipoPessoa`, `idade`, `residenciaFiscal`, `residenciaCambial`, `moeda`, `produto`, `canalOrigem`
**Saídas:** `elegivel`, `perfilCliente`, `produtosAdmissiveis`, `motivoRecusa`

| # | Regra | `REG-*` |
|---|---|---|
| 1 | Pessoa singular ou colectiva, residente ou não residente, moeda nacional ou estrangeira → elegível | `REG-ABR-01` |
| 2 | `idade < 18` → perfil `MENOR`; exige representante legal verificado | `REG-ABR-02` |
| 3 | Perfil `MENOR` → produtos de crédito e instrumentos de pagamento fora do Art. 9.º n.º 2 removidos de `produtosAdmissiveis` | `REG-MEN-04` |
| 4 | `canalOrigem = TERCEIRO_MANDATADO` sem prova de mandato → não elegível | `REG-ABR-06` |
| 5 | `residenciaFiscal = NAO_RESIDENTE` → `residenteFlag = false` propagado à conta | `REG-ABR-03` |

## `dmn-requisitos-documentais` — Collect

**Entradas:** `perfilCliente`, `residencia`, `nacionalidade`, `menor`, `canalOrigem`
**Saída:** colecção de `RequisitoDocumental`

| # | Condição | Documento exigido | `REG-*` |
|---|---|---|---|
| 1 | PS residente | `DOC_BI` ou `DOC_CARTAO_RESIDENTE` | `REG-KYC-10` |
| 2 | PS não residente, nacionalidade ≠ angolana | `DOC_PASSAPORTE` | `REG-KYC-10` |
| 3 | PS não residente, nacionalidade angolana | `DOC_BI` ou `DOC_PASSAPORTE` | `REG-KYC-10` |
| 4 | Menor residente sem documento próprio | `DOC_CEDULA_PESSOAL` | `REG-KYC-10` |
| 5 | Menor não residente sem documento próprio | `DOC_PUBLICO_EQUIV_MENOR` + identidade do representante legal | `REG-KYC-10` |
| 6 | Todos | `DOC_CARTAO_NIF` | `REG-KYC-10` |
| 7 | Todos | `DOC_COMPROV_MORADA`, `DOC_COMPROV_PROFISSAO` | `REG-KYC-11` |
| 8 | PC residente | `DOC_CERTIDAO_REG_COMERCIAL` ou `DOC_DR_ESTATUTOS` ou `DOC_CERTIDAO_NOTARIAL` | `REG-KYC-10` |
| 9 | PC não residente | `DOC_REG_COMERCIAL_ESTRANGEIRO` — certificado **e** autenticado consularmente (cumulativo) | `REG-KYC-12` |
| 10 | PC | `DOC_ACTA_AG_CONSTITUINTE`, `DOC_ACTA_ALTERACAO_SOCIETARIA`, `DOC_DECLARACAO_ORGAOS_GESTAO` | `REG-KYC-10` |
| 11 | Comerciante em nome individual | `DOC_RELATORIO_SUSTENTABILIDADE` | `REG-KYC-01` |
| 12 | Instituição de caridade sem personalidade jurídica, igreja, local de culto | `DOC_LEGALIZACAO_ESTATAL` | `REG-KYC-01` |
| 13 | Beneficiário efectivo aplicável | `DOC_ACORDO_FIDUCIARIO` | `REG-KYC-07` |
| 14 | `canalOrigem = REMOTO` | `DOC_COMPROV_TRANSFERENCIA_INICIAL` | `REG-FUN-01` |
| 15 | Todos | `DOC_EVIDENCIA_CG_CP`, `DOC_FICHA_TECNICA_INFORMATIVA` | `REG-INF-02`, `REG-INF-04` |
| 16 | Menor ≥ 14 anos com pedido de cartão | `DOC_TERMO_RESPONSABILIDADE_CARTAO` | `REG-MEN-02` |

## `dmn-ubo-threshold` — Collect

**Entradas:** `percentagemCapital`, `percentagemDireitosVoto`, `cadeiaControlo`
**Saídas:** `identificarComoTitularParticipacao`, `identificarComoBeneficiarioEfectivo`

| # | Regra | `REG-*` |
|---|---|---|
| 1 | `percentagemCapital >= 20` ou `percentagemDireitosVoto >= 20` → identificar como titular de participação | `REG-KYC-06` |
| 2 | Identificado como titular de participação → **também** identificar como beneficiário efectivo | Anexo I, nota 3 |
| 3 | Controlo por outros meios, independentemente da percentagem → identificar como beneficiário efectivo | `REG-KYC-07` |
| 4 | Nenhum titular ≥ 20% → **não** dispensa beneficiário efectivo | `BR-UBO-05` |

> Limiar de 20% parametrizável **apenas** para valores inferiores (mais restritivos). Ver `BR-UBO-01`.

## `dmn-pep-categoria` — First

**Entradas:** `cargoDeclarado`, `relacaoFamiliar`, `relacaoSocietaria`
**Saídas:** `categoriaPep`, `nivelExposicao`

Cobre integralmente a taxonomia da nota 2 do Anexo I: `PEP_I_01`–`PEP_I_11` (altos cargos), `PEP_II_01`–`PEP_II_02` (família próxima), `PEP_III_01`–`PEP_III_02` (relações societárias ou comerciais). Ver `docs/01-regulatorio/02-anexo-i-ficha-e-documentos.md`.

## `dmn-risco-bcft` — Collect (sum)

**Entradas:** `perfilCliente`, `residencia`, `estadoPep`, `resultadoSancoes`, `naturezaRendimento`, `montanteRendimento`, `motivoAbertura`, `canalOrigem`, `produto`, `moeda`
**Saídas:** `pontuacao`, `nivelRisco`, `factores`

| # | Regra | Origem |
|---|---|---|
| 1 | Qualquer estado PEP → contribuição que garante `nivelRisco = ALTO` | `BR-PEP-03` |
| 2 | Correspondência em lista de sanções → `nivelRisco = ALTO` | Lei n.º 5/20 |
| 3 | OSFL ou instituição de caridade sem personalidade jurídica → risco base ≥ `MEDIO` | `BR-RSK-04` |
| 4 | Não residente → contribuição positiva | Lei n.º 5/20 |
| 5 | Incoerência entre montante do rendimento e produto solicitado → contribuição positiva | Lei n.º 5/20 |

Pesos parametrizáveis por instituição. `factores` é obrigatório: uma pontuação sem factores não é explicável, e decisão automática inexplicável é inauditável (`BR-RSK-02`).

## `dmn-nivel-diligencia` — Priority

**Entradas:** `nivelRisco`, `estadoPep`, `produto`, `canalOrigem`
**Saídas:** `nivelDiligencia`, `aprovacoesExigidas`

| Prioridade | Condição | Nível | Aprovação |
|---|---|---|---|
| 1 | `estadoPep ≠ NAO_PEP` | `REFORCADA` | `CHEFE_COMPLIANCE` |
| 2 | `nivelRisco = ALTO` | `REFORCADA` | `CHEFE_COMPLIANCE` |
| 3 | `nivelRisco = MEDIO` | `NORMAL` | — |
| 4 | `nivelRisco = BAIXO` | `SIMPLIFICADA` | — |

> `SIMPLIFICADA` reduz a **profundidade** da verificação; **nunca** dispensa campos mínimos do Anexo I (`BR-RSK-03`).

## `dmn-entrega-fundos` — Unique

**Entradas:** `canalOrigem`, `meioEntrega`, `ordenanteIdentificado`, `instituicaoOrigemElegivel`, `ordenanteCoincideComTitular`
**Saídas:** `decisao` (`ACEITAR` / `EXIGIR_JUSTIFICACAO` / `REJEITAR`), `motivo`

| # | Regra | `REG-*` |
|---|---|---|
| 1 | `REMOTO` e `meioEntrega ≠ TRANSFERENCIA_BANCARIA` → `REJEITAR` | `REG-FUN-01` |
| 2 | `REMOTO` e `ordenanteIdentificado = false` → `REJEITAR` | `REG-FUN-01` |
| 3 | `REMOTO` e `instituicaoOrigemElegivel = false` → `REJEITAR` | `REG-FUN-02` |
| 4 | `ordenanteCoincideComTitular = false` → `EXIGIR_JUSTIFICACAO` (**todos** os canais) | `REG-FUN-03`, `ADR-0007` |
| 5 | Restantes casos → `ACEITAR` | — |

> A regra 4 aplica-se a todos os canais por decisão interpretativa registada em `ADR-0007`.

## `dmn-cobertura-condicoes-gerais` — Unique

**Entradas:** `versaoMinuta`, `temasPresentes`
**Saídas:** `conforme`, `temasEmFalta`

Verifica os 13 temas `CG-A` a `CG-M` do Art. 5.º n.º 2. Não conforme **suspende a instância** e gera incidente para a Direcção Jurídica; não é contornável por operador (`BR-INF-03`).

## `dmn-limites-menor` — Unique

**Entradas:** `idade`, `produtoSolicitado`, `termoResponsabilidadePresente`
**Saídas:** `cartaoPermitido`, `limiteDiarioMaximo`, `produtosVedados`

| # | Regra | `REG-*` |
|---|---|---|
| 1 | `idade < 14` → `cartaoPermitido = false` | `REG-MEN-02` |
| 2 | `idade >= 14` e `termoResponsabilidadePresente = false` → `cartaoPermitido = false` | `REG-MEN-02` |
| 3 | `idade >= 14` e termo presente → `cartaoPermitido = true`, com limite diário acordado | `REG-MEN-02`, `REG-MEN-03` |
| 4 | Produtos de crédito → sempre vedados | `REG-MEN-04` |
