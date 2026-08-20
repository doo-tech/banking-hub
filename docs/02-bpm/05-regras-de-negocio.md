# Repositório de Regras de Negócio — `BR-*`

> **Princípio (ABPMP CBOK).** Regras de negócio são declarativas e vivem separadas do processo que as invoca. Uma regra enterrada numa condição de *gateway* ou num `if` de código é uma regra que ninguém consegue auditar, alterar com segurança, nem provar a um regulador.
>
> **Formato.** Cada regra tem: identificador, enunciado declarativo, fonte, tipo, implementação e teste. **Toda regra tem de ser falsificável** — se não se consegue escrever o teste que a viola, o enunciado é vago.

**Tipos:** `RESTRICAO` (proíbe) · `DERIVACAO` (calcula) · `EXISTENCIA` (exige presença) · `PROCESSO` (impõe ordem ou prazo).

**Parametrização por instituição:** `F` = fixa (imposta por norma, não parametrizável) · `R` = parametrizável **apenas em sentido mais restritivo** · `L` = livremente parametrizável pela IFB.

---

## Elegibilidade e titularidade

| ID | Enunciado | Tipo | Fonte | Param. | Implementação |
|---|---|---|---|---|---|
| `BR-ELE-01` | Podem ser titulares de conta pessoas singulares e colectivas, residentes e não residentes, em moeda nacional e/ou estrangeira. | RESTRICAO | `REG-ABR-01` | F | `dmn-elegibilidade` |
| `BR-ELE-02` | Um pedido cujo titular seja menor só é elegível se estiver identificado pelo menos um representante legal com identidade verificada. | EXISTENCIA | `REG-ABR-02` | F | `dmn-elegibilidade` + `INV` |
| `BR-ELE-03` | Toda conta cujo titular seja não residente é marcada com `residenteFlag = false` em atributo estruturado, independentemente da moeda de denominação. | RESTRICAO | `REG-ABR-03` | F | Contrato de integração com o core |
| `BR-ELE-04` | O regime de titularidade pertence ao conjunto fechado {`SINGULAR`, `COLECTIVA_SOLIDARIA`, `COLECTIVA_CONJUNTA`, `COLECTIVA_MISTA`}. | RESTRICAO | `REG-ABR-08` | F | Enum de domínio |
| `BR-ELE-05` | Um regime de titularidade colectivo exige dois ou mais titulares; um regime singular exige exactamente um. | RESTRICAO | `REG-ABR-08` | F | Invariante do agregado |
| `BR-ELE-06` | Um pedido no canal `TERCEIRO_MANDATADO` só é elegível com prova registada da atribuição legal ou contratual da competência. | EXISTENCIA | `REG-ABR-06` | F | `dmn-elegibilidade` |

## Ficha de Cliente e campos mínimos

| ID | Enunciado | Tipo | Fonte | Param. | Implementação |
|---|---|---|---|---|---|
| `BR-FIC-01` | A Ficha de Cliente de pessoa singular exige os 15 campos mínimos do Anexo I, I.1. Nenhum pode ficar vazio, salvo `entidadePatronal` e `moradaAlternativa` quando declaradamente inaplicáveis. | EXISTENCIA | `REG-KYC-01` | R | Schema + validação |
| `BR-FIC-02` | A morada habitual exige **ponto de referência** preenchido. O ponto de referência é campo distinto da morada, não parte dela. | EXISTENCIA | `REG-KYC-01` | F | Schema |
| `BR-FIC-03` | O campo `motivoAbertura` é obrigatório em todos os perfis. | EXISTENCIA | `REG-KYC-02` | F | Schema |
| `BR-FIC-04` | O documento de identificação apresentado não pode estar expirado à data da submissão. | RESTRICAO | `REG-KYC-10` | F | Validação em B3 |
| `BR-FIC-05` | Pessoa singular residente identifica-se por bilhete de identidade ou cartão de residente; não residente por passaporte, excepto não residente de nacionalidade angolana, que pode usar bilhete de identidade. | RESTRICAO | `REG-KYC-10` | F | `dmn-requisitos-documentais` |
| `BR-FIC-06` | Menor que, em razão da idade, não possua os documentos de `BR-FIC-05` identifica-se por cédula pessoal (residente) ou documento público equivalente (não residente), apresentado por quem demonstre legitimidade como representante legal, cuja identidade é verificada no início da relação de negócio. | RESTRICAO | `REG-KYC-10` | F | `dmn-requisitos-documentais` |
| `BR-FIC-07` | Comerciante em nome individual exige, além dos campos de pessoa singular, os 6 campos do Anexo I, I.1.1. | EXISTENCIA | `REG-KYC-01` | R | Schema condicional |
| `BR-FIC-08` | Pessoa colectiva exige os 7 campos do Anexo I, I.2. Condomínios em propriedade horizontal e patrimónios autónomos seguem o mesmo conjunto. | EXISTENCIA | `REG-KYC-01` | R | Schema condicional |
| `BR-FIC-09` | Organização sem fins lucrativos exige, além dos campos de pessoa colectiva, os 4 campos do Anexo I, I.2.1. | EXISTENCIA | `REG-KYC-01` | R | Schema condicional |
| `BR-FIC-10` | Instituição de caridade sem personalidade jurídica, órgão de igreja ou local de culto exige os 5 campos do Anexo I, I.3, incluindo documento comprovativo da legalização pelas autoridades estatais. | EXISTENCIA | `REG-KYC-01` | R | Schema condicional |
| `BR-FIC-11` | O NIF é obrigatório e comprovado por cartão de identificação fiscal ou equivalente emitido pela AGT, para cliente, pessoa colectiva e beneficiário efectivo. | EXISTENCIA | `REG-KYC-10` | F | `dmn-requisitos-documentais` |
| `BR-FIC-12` | Pessoa colectiva não residente exige documento de registo **certificado** pelas entidades competentes do país de residência **e** **autenticado** pela representação consular de Angola no país de origem. As duas condições são cumulativas. | EXISTENCIA | `REG-KYC-12` | F | `dmn-requisitos-documentais` |

## Assinatura

| ID | Enunciado | Tipo | Fonte | Param. | Implementação |
|---|---|---|---|---|---|
| `BR-ASS-01` | A Ficha de Cliente tem de estar assinada por todos os titulares e, quando aplicável, pelos respectivos representantes legais, antes da celebração do contrato. | EXISTENCIA | `REG-KYC-03` | F | `INV-01` |
| `BR-ASS-02` | Quando o cliente declara não saber ou não poder assinar, a função de assinatura é servida por meio biométrico. Este é o caminho normativo, não uma excepção. | DERIVACAO | `REG-KYC-04` | F | E7 → E9 |
| `BR-ASS-03` | Toda assinatura, manuscrita ou biométrica, é vinculada ao hash do documento assinado no momento da assinatura. | RESTRICAO | `REG-KYC-03` | F | Serviço de assinatura |

## Beneficiário efectivo e estrutura societária

| ID | Enunciado | Tipo | Fonte | Param. | Implementação |
|---|---|---|---|---|---|
| `BR-UBO-01` | Em pessoa colectiva, é obrigatória a identificação de todos os titulares de participações no capital ou nos direitos de voto de valor **igual ou superior a 20%**. | EXISTENCIA | `REG-KYC-06` | R | `dmn-ubo-threshold` |
| `BR-UBO-02` | A identificação de titulares de participação ≥ 20% só está completa com o preenchimento da secção de beneficiário efectivo correspondente. | PROCESSO | Anexo I, nota 3 | F | D2 → D3 |
| `BR-UBO-03` | O beneficiário efectivo exige os 13 campos do Anexo I, I.4. | EXISTENCIA | `REG-KYC-07` | R | Schema |
| `BR-UBO-04` | A soma das participações declaradas não pode exceder 100%. Divergência gera excepção para Compliance, não rejeição automática. | RESTRICAO | `REG-KYC-06` | F | Validação + excepção |
| `BR-UBO-05` | Ausência de titular com participação ≥ 20% não dispensa a identificação de beneficiário efectivo: o controlo pode ser exercido por outros meios. | EXISTENCIA | `REG-KYC-07` | F | `dmn-ubo-threshold` |
| `BR-UBO-06` | Os procuradores da pessoa colectiva e o respectivo mandato são identificados e comprovados por declaração escrita emitida pela própria pessoa colectiva. | EXISTENCIA | `REG-KYC-10` | F | `dmn-requisitos-documentais` |

## PEP, sanções e risco

| ID | Enunciado | Tipo | Fonte | Param. | Implementação |
|---|---|---|---|---|---|
| `BR-PEP-01` | A triagem PEP incide sobre **todas** as partes do pedido: cliente, cada representante legal, cada procurador e cada beneficiário efectivo. | EXISTENCIA | `REG-KYC-08` | F | D4 multi-instância, `INV-03` |
| `BR-PEP-02` | A qualificação como PEP abrange titulares de altos cargos políticos ou públicos (`PEP_I_01`–`PEP_I_11`), membros próximos da família (`PEP_II_01`–`PEP_II_02`) e pessoas com relações societárias ou comerciais reconhecidas (`PEP_III_01`–`PEP_III_02`). | DERIVACAO | Anexo I, nota 2 | F | `dmn-pep-categoria` |
| `BR-PEP-03` | Toda parte qualificada como PEP em qualquer categoria implica nível de diligência `REFORCADA`. | DERIVACAO | Lei n.º 5/20 | R | `dmn-nivel-diligencia` |
| `BR-PEP-04` | A diligência reforçada exige aprovação humana registada de perfil `CHEFE_COMPLIANCE` ou superior, com fundamento textual. Não é automatizável. | RESTRICAO | Lei n.º 5/20 | F | D9, `INV-08` |
| `BR-PEP-05` | A qualificação PEP mantém-se registada com a data de aferição e a categoria aplicada; alterações posteriores geram nova aferição, não sobrescrevem a anterior. | RESTRICAO | `REG-RET-01` | F | Modelo temporal |
| `BR-RSK-01` | A pontuação de risco BC/FT/FPADM é calculada por decisão DMN versionada, cujas entradas mínimas são: perfil de cliente, residência, estado PEP, resultado do rastreio de sanções, natureza e montante do rendimento, motivo da abertura, canal, produto e moeda. | DERIVACAO | `REG-KYC-05` | L | `dmn-risco-bcft` |
| `BR-RSK-02` | Toda decisão automática de risco registra a versão da decisão DMN que a produziu. | RESTRICAO | Art. 16.º | F | `INV-09` |
| `BR-RSK-03` | Diligência simplificada reduz a profundidade da verificação, mas **nunca** dispensa nenhum campo mínimo do Anexo I. | RESTRICAO | Anexo I, I.1 | F | Schema independente do nível |
| `BR-RSK-04` | Organização sem fins lucrativos e instituição de caridade sem personalidade jurídica têm risco base não inferior a `MEDIO`. | DERIVACAO | Lei n.º 5/20 | R | `dmn-risco-bcft` |

## Identidade — regra de recusa

| ID | Enunciado | Tipo | Fonte | Param. | Implementação |
|---|---|---|---|---|---|
| `BR-KYC-09` | Havendo dúvida sobre a verdadeira identidade do cliente, de representante legal ou de beneficiário efectivo, que não seja resolvida de forma satisfatória dentro do prazo máximo de revisão, o pedido transita obrigatoriamente para `RECUSADO_IDENTIDADE_NAO_COMPROVADA` e nenhuma operação é realizada. | RESTRICAO | `REG-KYC-09` | F | B8, `INV-06` |
| `BR-KYC-10` | Não existe estado de espera indefinido para dúvida de identidade. Todo pedido em revisão manual de identidade tem temporizador activo. | PROCESSO | `REG-KYC-09` | F | `INV-10` |
| `BR-KYC-11` | Toda informação declarada que seja verificável tem de ter documento associado no catálogo de tipos documentais. Campo verificável sem documento é defeito de configuração, não estado admissível. | EXISTENCIA | `REG-KYC-10` | F | Matriz documento × informação |
| `BR-KYC-12` | Para morada habitual, profissão e entidade patronal admite-se qualquer documento, meio ou diligência considerado válido, idóneo e suficiente, desde que a fundamentação de idoneidade fique registada com autor identificado. | EXISTENCIA | `REG-KYC-11` | L | Catálogo extensível + registo |

## Informação pré-contratual e celebração

| ID | Enunciado | Tipo | Fonte | Param. | Implementação |
|---|---|---|---|---|---|
| `BR-INF-01` | As Condições Gerais, as Condições Particulares e a Ficha Técnica Informativa são disponibilizadas ao cliente **antes** da celebração do contrato. A verificação é temporal: `timestampDisponibilizacao < timestampCelebracao`. | PROCESSO | `REG-INF-01` | F | E10, `INV-02` |
| `BR-INF-02` | É arquivada evidência imutável da disponibilização, contendo versão do documento, canal, timestamp e hash. | EXISTENCIA | `REG-INF-02` | F | E5 |
| `BR-INF-03` | A minuta de Condições Gerais em uso tem de cobrir os 13 temas `CG-A` a `CG-M`. Falha de cobertura suspende a instância e gera incidente para a Direcção Jurídica; não é contornável por operador. | RESTRICAO | `REG-INF-03` | F | E3, `dmn-cobertura-cg` |
| `BR-INF-04` | O Contrato de Abertura de Conta só se considera celebrado com as três peças referenciadas: Ficha de Cliente, Condições Gerais e, quando aplicável, Condições Particulares. | EXISTENCIA | `REG-ABR-07` | F | `INV-01` |
| `BR-INF-05` | A versão da minuta contratual sob a qual cada pedido foi celebrado é persistida na instância e não é alterada por publicações posteriores. | RESTRICAO | `REG-RET-01` | F | Versionamento |

## Entrega inicial de fundos

| ID | Enunciado | Tipo | Fonte | Param. | Implementação |
|---|---|---|---|---|---|
| `BR-FUN-01` | No canal `REMOTO`, a entrega inicial de fundos tem de ser efectuada por transferência bancária que permita a identificação do ordenante. Numerário e meios sem identificação do ordenante são rejeitados. | RESTRICAO | `REG-FUN-01` | F | `dmn-entrega-fundos` |
| `BR-FUN-02` | A conta de origem dos fundos tem de estar aberta junto de instituição constante do registo de instituições sobre as quais existe aferição documentada de que aplicam medidas de identificação e diligência. | RESTRICAO | `REG-FUN-02` | F | F6, `INV-05` |
| `BR-FUN-03` | Se o ordenante da entrega inicial não coincidir com o titular, é exigida justificação credível documentada e avaliada por decisão humana registada. | EXISTENCIA | `REG-FUN-03` | F | F7–F9 |
| `BR-FUN-04` | A conta permanece em `ABERTA_NAO_ACTIVA` até a entrega inicial de fundos estar verificada. | PROCESSO | `REG-ABR-07` | F | F1, F11, `INV-05` |
| `BR-FUN-05` | Fundos recebidos cuja verificação seja recusada são devolvidos à conta de origem, e o facto é registado no dossiê. | PROCESSO | `REG-FUN-03` | F | F10 |

## Contas de menores

| ID | Enunciado | Tipo | Fonte | Param. | Implementação |
|---|---|---|---|---|---|
| `BR-MEN-01` | Conta de menor é aberta pelo representante legal e movimentada por este. | RESTRICAO | `REG-MEN-01` | F | `dmn-elegibilidade` |
| `BR-MEN-02` | Cartão de débito sobre conta de menor só é atribuível a partir dos **14 anos**, mediante solicitação do representante legal e termo de responsabilidade assinado por este. | RESTRICAO | `REG-MEN-02` | R | `dmn-limites-menor` |
| `BR-MEN-03` | Os movimentos a débito com cartão de menor estão limitados a valor máximo diário acordado no acto de solicitação, persistido e transmitido ao emissor. | RESTRICAO | `REG-MEN-03` | L | `dmn-limites-menor` |
| `BR-MEN-04` | Em conta de menor não é permitida contratação de crédito nem disponibilização de instrumentos de pagamento que não sejam o cartão de débito nas condições de `BR-MEN-02`. | RESTRICAO | `REG-MEN-04` | F | Catálogo filtrado |

## Moeda estrangeira

| ID | Enunciado | Tipo | Fonte | Param. | Implementação |
|---|---|---|---|---|---|
| `BR-FX-01` | Transferência a débito de conta em moeda estrangeira a favor de entidade residente em território nacional só é executável em moeda estrangeira se: (a) ambas as partes forem pessoas colectivas em relação de grupo; (b) ambas forem pessoas singulares em relação de parentesco; ou (c) ordenante e beneficiário forem a mesma pessoa. | RESTRICAO | `REG-FX-02` | F | Escopo 3 |
| `BR-FX-02` | Não é permitida concessão de crédito em conta denominada em moeda estrangeira a favor de entidade não exportadora. | RESTRICAO | `REG-FX-03` | F | Escopo 3 |
| `BR-FX-03` | Na abertura de conta em moeda estrangeira é capturado o atributo `entidadeExportadora`. | EXISTENCIA | `REG-FX-03` | F | Schema |

## Manutenção, dormência e eventos de vida

| ID | Enunciado | Tipo | Fonte | Param. | Implementação |
|---|---|---|---|---|---|
| `BR-DOR-01` | Uma conta é classificada `DORMENTE` quando não registe movimento a débito por período igual ou superior a **24 meses**. | DERIVACAO | `REG-DOR-01` | R | Escopo 3 |
| `BR-DOR-02` | Conta classificada `DORMENTE` fica sujeita a restrição de movimentação a débito. | RESTRICAO | `REG-DOR-02` | F | Escopo 3 |
| `BR-EVE-01` | Ao tomar conhecimento do falecimento de titular, todas as contas por ele tituladas são bloqueadas a débito. | PROCESSO | `REG-EVE-01` | F | Escopo 3 |
| `BR-EVE-02` | A movimentação de conta de titular falecido só é autorizada a herdeiros que apresentem certidão de óbito e certidão de habilitação de herdeiros ou documento equiparado. | RESTRICAO | `REG-EVE-02` | F | Escopo 3 |
| `BR-EVE-03` | Ao tomar conhecimento de processo de falência ou insolvência de titular, a conta é bloqueada a débito, seja singular ou colectiva, e actua-se nos termos instruídos pelas autoridades judiciais competentes. | PROCESSO | `REG-EVE-05` | F | Escopo 3 |

## Encerramento

| ID | Enunciado | Tipo | Fonte | Param. | Implementação |
|---|---|---|---|---|---|
| `BR-ENC-01` | Encerramento a pedido do cliente exige instrução de **todos** os titulares ou representantes legais, com indicação do destino do saldo. | EXISTENCIA | `REG-ENC-02` | F | Escopo 3 |
| `BR-ENC-02` | O encerramento pode ser recusado se existir saldo devedor a favor da instituição, ordens ou operações pendentes, responsabilidades por liquidar, imposição judicial ou impossibilidade legal. | RESTRICAO | `REG-ENC-03` | F | Escopo 3 |
| `BR-ENC-03` | Encerramento por iniciativa da instituição exige notificação ao cliente com antecedência mínima de **60 dias** face à data definida. | PROCESSO | `REG-ENC-04` | R | Escopo 3 |
| `BR-ENC-04` | O encerramento com efeitos imediatos só é admissível nas situações das alíneas a) a f) do n.º 5 do Art. 13.º, com o fundamento concreto registado. | RESTRICAO | `REG-ENC-05` | F | Escopo 3 |
| `BR-ENC-05` | O encerramento implica cancelamento imediato de todos os meios de movimentação entregues ao cliente. | PROCESSO | `REG-ENC-06` | F | Escopo 3 |
| `BR-ENC-06` | Não havendo instrução do cliente sobre o saldo à data do encerramento, os fundos podem ser transferidos para conta contabilística interna até recepção de instrução. | PROCESSO | `REG-ENC-07` | F | Escopo 3 |
| `BR-ENC-07` | Contas sem movimentos a débito ou a crédito por **15 anos** são encerradas, precedendo diligências legais de contacto do titular ou herdeiros, incluindo publicação de editais no jornal de maior circulação do País. | PROCESSO | `REG-ENC-08` | F | Escopo 3 |
| `BR-ENC-08` | Não havendo oposição às diligências de `BR-ENC-07`, o valor em depósito reverte ao Estado nos termos do Decreto-Lei n.º 187/70. | PROCESSO | `REG-ENC-09` | F | Escopo 3 |

## Retenção, arquivo e auditoria

| ID | Enunciado | Tipo | Fonte | Param. | Implementação |
|---|---|---|---|---|---|
| `BR-RET-01` | Todos os registos relativos a clientes são conservados por período **mínimo de 10 anos**. Tentativa de expurgo antes do prazo é rejeitada pelo sistema, não apenas desencorajada por procedimento. | RESTRICAO | `REG-RET-01` | R | Política WORM, `INV-07` |
| `BR-RET-02` | A contagem do prazo de retenção de um dossiê inicia-se na data do estado terminal do pedido, não na data de início. | DERIVACAO | `REG-RET-01` | F | Política de retenção |
| `BR-RET-03` | Todo estado terminal — incluindo recusa, desistência, não elegibilidade e expiração — produz Dossiê de Abertura selado e retido. | PROCESSO | `REG-RET-01` | F | G1–G4, `INV-07` |
| `BR-RET-04` | O Dossiê de Abertura é indexado para recuperação por cliente, NIF, número de conta, data e estado, com objectivo de disponibilização atempada à autoridade competente. | EXISTENCIA | `REG-RET-02` | F | G3 |
| `BR-RET-05` | Todo evento relevante de um pedido é registado em cadeia encadeada por hash, com autor, timestamp e versões de processo e regras aplicadas. Registo de auditoria não é log de aplicação. | EXISTENCIA | `REG-RET-02` | F | G4, `INV-09` |
| `BR-RET-06` | A recolha de dados além dos campos mínimos do Anexo I exige finalidade declarada e fundamentação de risco registada. | RESTRICAO | Lei n.º 22/11 | L | Minimização de dados |

---

## Contagem e cobertura

| Domínio | Regras |
|---|---|
| Elegibilidade e titularidade | 6 |
| Ficha de Cliente | 12 |
| Assinatura | 3 |
| Beneficiário efectivo | 6 |
| PEP, sanções e risco | 8 |
| Identidade e recusa | 4 |
| Informação pré-contratual | 5 |
| Entrega inicial de fundos | 5 |
| Contas de menores | 4 |
| Moeda estrangeira | 3 |
| Manutenção e eventos de vida | 5 |
| Encerramento | 8 |
| Retenção e auditoria | 6 |
| **Total** | **75** |

**Regra de manutenção deste repositório.** Nenhuma regra é adicionada ao código sem entrada aqui, e nenhuma entrada aqui existe sem teste automatizado que a viole quando removida. Uma regra sem teste é uma intenção.
