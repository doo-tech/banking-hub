# Glossário Corporativo — Banking Hub

> **Propósito.** Estabelecer vocabulário único e não ambíguo entre Negócio, Compliance, Arquitectura e Engenharia. Segundo o **ABPMP BPM CBOK**, o glossário é artefacto obrigatório da fase de *Análise de Processos*: sem linguagem comum, modelos de processo divergem da realidade e regras de negócio tornam-se indiscutíveis.
>
> **Regra de ouro.** Os termos aqui definidos são a **Linguagem Ubíqua** (*Ubiquitous Language*) do sistema. Nome de classe, tabela, endpoint, variável de processo BPMN e ecrã de app usam **estes** termos, em português, sem tradução para inglês e sem sinónimos locais.
>
> **Colunas:** *Termo* · *Definição* · *Fonte* · *Identificador técnico* (nome canónico em código).

---

## 1. Termos de domínio com definição legal (Aviso n.º 1/23)

Estes termos têm definição normativa. **Não podem ser redefinidos** pelo projecto.

| Termo | Definição | Fonte | Identificador técnico |
|---|---|---|---|
| **Abertura de Conta Bancária** | Processo mediante o qual ocorre a celebração do contrato de abertura de conta entre o cliente e a Instituição Financeira Bancária. | Art. 2.º a) | `AberturaConta` |
| **Cliente** | Pessoa singular ou colectiva, nacional ou estrangeira, residente ou não residente, pública ou privada, que celebra um contrato de abertura de conta com uma IFB a quem esta coloca à disposição produtos e serviços financeiros. | Art. 2.º b) | `Cliente` |
| **Condições Gerais** | Condições que regem o relacionamento entre a IFB e o cliente quanto à abertura, manutenção, movimentação e encerramento da conta. Devem cobrir, no mínimo, 13 temas. | Art. 2.º c); Art. 5.º n.º 2 | `CondicoesGerais` |
| **Condições Particulares** | Condições acordadas entre o cliente e a IFB para a movimentação da conta. | Art. 2.º d) | `CondicoesParticulares` |
| **Contas Colectivas** | Contas tituladas por mais de uma pessoa. Subdividem-se em Solidárias, Conjuntas e Mistas. | Art. 2.º e) | `RegimeTitularidade.COLECTIVA` |
| **Conta Solidária** | Conta colectiva movimentável por qualquer um dos titulares, de forma autónoma. | Art. 2.º e) i. | `RegimeMovimentacao.SOLIDARIA` |
| **Conta Conjunta** | Conta colectiva movimentável apenas mediante assinatura simultânea de todos os titulares. | Art. 2.º e) ii. | `RegimeMovimentacao.CONJUNTA` |
| **Conta Mista** | Conta colectiva com diferentes possibilidades de movimentação, nos termos definidos pelos titulares com a IFB. | Art. 2.º e) iii. | `RegimeMovimentacao.MISTA` |
| **Conta Singular** | Conta com um único titular. | Art. 2.º f) | `RegimeTitularidade.SINGULAR` |
| **Contrato de Abertura de Conta** | Constituído pela Ficha de Cliente, pelas Condições Gerais e pelas Condições Particulares, quando aplicável. **É a junção das três peças que forma o contrato** — nenhuma isoladamente o constitui. | Art. 2.º g) | `ContratoAberturaConta` |
| **Encerramento de Conta** | Processo por via do qual o vínculo contratual entre o cliente e a IFB é extinto. | Art. 2.º h) | `EncerramentoConta` |
| **Ficha de Cliente** | Formulário de abertura de conta onde são preenchidos os dados de identificação e caracterização do cliente e dos respectivos representantes legais, quando aplicável. | Art. 2.º i); Art. 4.º n.º 1; Anexo I | `FichaCliente` |
| **Meio de Comunicação à Distância** | Qualquer meio telefónico, electrónico, telemático ou de natureza análoga que permita estabelecer relações de negócio ou transmitir instruções sem a presença física e simultânea do cliente na IFB. | Art. 2.º j) | `MeioComunicacaoDistancia` |
| **Movimentação de Conta** | A conta é movimentada através de depósitos, levantamentos e transferências. | Art. 2.º k) | `MovimentacaoConta` |
| **Representante Legal** | Pessoa com poderes de representação legal na relação entre o titular da conta e a IFB. | Art. 2.º l) | `RepresentanteLegal` |
| **Conta Dormente** | Conta sem movimento a débito por período igual ou superior a 24 meses. | Art. 7.º n.º 1 | `EstadoConta.DORMENTE` |
| **Instituição Financeira Bancária (IFB)** | Entidade sujeita ao Aviso, sediada em Angola. No Banking Hub é o *tenant* — a instituição que instala e opera o produto. | Art. 1.º; Lei n.º 14/21 | `Instituicao` (tenant) |

---

## 2. Termos de domínio com definição em legislação conexa

| Termo | Definição | Fonte | Identificador técnico |
|---|---|---|---|
| **Beneficiário Efectivo (BE)** | Pessoa singular que, em última instância, detém a propriedade ou o controlo do cliente, ou em nome de quem a operação é realizada. No Aviso 1/23 é secção própria da Ficha de Cliente e o seu preenchimento é obrigatório para completar a identificação de titulares de participações ≥ 20%. | Anexo I, I.4 e nota 3; Lei n.º 5/20 | `BeneficiarioEfectivo` |
| **Pessoa Politicamente Exposta (PEP)** | Indivíduo nacional ou estrangeiro que desempenha, ou desempenhou, funções públicas proeminentes em Angola, em qualquer outro país ou jurisdição, ou em qualquer organização internacional. Abrange ainda membros próximos da família e pessoas com relações societárias ou comerciais reconhecidas. | Anexo I, nota 2; art. 3.º n.º 31 da Lei n.º 5/20 | `PepStatus`, `PepCategoria` |
| **BC/FT/FPADM** | Branqueamento de Capitais / Financiamento do Terrorismo / Financiamento da Proliferação de Armas de Destruição em Massa. Designação usada pela Lei n.º 5/20 e pelo Aviso n.º 14/20. | Lei n.º 5/20 | `RiscoBcFt` |
| **Diligência Devida (CDD — *Customer Due Diligence*)** | Conjunto de medidas de identificação, verificação e caracterização do cliente, representantes e beneficiários efectivos. No Aviso 1/23 aparece como "dever de identificação e diligência". | Art. 4.º; Lei n.º 5/20 | `Diligencia` |
| **Diligência Reforçada (EDD — *Enhanced Due Diligence*)** | Medidas adicionais e aprovação de nível superior exigidas em situações de risco elevado, designadamente PEP. | Lei n.º 5/20 | `Diligencia.REFORCADA` |
| **Diligência Simplificada** | Medidas reduzidas admissíveis em situações comprovadamente de risco baixo. Nunca dispensa os campos mínimos do Anexo I. | Lei n.º 5/20 | `Diligencia.SIMPLIFICADA` |
| **Residência Cambial** | Qualificação de residente/não residente para efeitos da Lei Cambial (Lei n.º 5/97), distinta de residência fiscal e de domicílio. O Anexo I usa expressamente "não residentes cambiais". | Lei n.º 5/97; Anexo I, II.3 | `residenciaCambial` |
| **NIF** | Número de Identificação Fiscal, comprovado por cartão de identificação fiscal ou equivalente emitido pela Administração Geral Tributária (AGT). | Anexo I, II | `nif` |
| **Comerciante em Nome Individual (CNI)** | Pessoa singular que exerce actividade comercial em nome próprio; exige, além dos campos de pessoa singular, denominação social, morada da sede, NIF empresarial, objecto social, relatório de sustentabilidade e montante do rendimento. | Anexo I, I.1.1 | `TipoCliente.COMERCIANTE_NOME_INDIVIDUAL` |
| **Organização Sem Fins Lucrativos (OSFL)** | Pessoa colectiva com exigências informativas adicionais: localização geográfica, estrutura organizacional, natureza das doações e voluntariado, natureza dos fundos e gastos incluindo beneficiários. | Anexo I, I.2.1 | `TipoCliente.ORGANIZACAO_SEM_FINS_LUCRATIVOS` |
| **Ficha Técnica Informativa (FTI)** | Documento padronizado de informação pré-contratual sobre o depósito, exigido pela regulamentação dos Deveres de Informação. | Art. 5.º n.º 3 | `FichaTecnicaInformativa` |
| **Serviços Mínimos Bancários** | Regime que sustenta a gratuidade da primeira via do extracto. | Art. 6.º n.º 2 | — |

---

## 3. Termos do processo Banking Hub (definição do projecto)

Termos criados pelo projecto. Onde possível, ancorados em conceitos legais.

| Termo | Definição | Identificador técnico |
|---|---|---|
| **Pedido de Abertura** | Instância única e rastreável do processo de abertura de conta, desde a manifestação de interesse até um estado terminal (`ACTIVA`, `RECUSADO`, `DESISTIDO`, `EXPIRADO`). É a raiz de agregação de todo o dossiê. | `PedidoAbertura`, `pedidoId` |
| **Dossiê de Abertura** | Conjunto completo e imutável de dados, documentos, decisões, evidências e registos de auditoria de um Pedido de Abertura. É a unidade de retenção de 10 anos e a unidade de resposta a pedido da autoridade competente. | `DossieAbertura` |
| **Canal de Origem** | Meio pelo qual o pedido entrou: `PRESENCIAL` (balcão), `REMOTO` (mobile/web, ao abrigo do Art. 3.º n.º 5), `TERCEIRO_MANDATADO` (Art. 3.º n.º 6). Determina o subconjunto de regras aplicáveis. | `CanalOrigem` |
| **Perfil de Cliente** | Classificação estrutural que determina o conjunto de campos e documentos exigidos: `PESSOA_SINGULAR`, `MENOR`, `COMERCIANTE_NOME_INDIVIDUAL`, `PESSOA_COLECTIVA`, `ORGANIZACAO_SEM_FINS_LUCRATIVOS`, `INSTITUICAO_CARIDADE_SEM_PERSONALIDADE`, `CONDOMINIO_PATRIMONIO_AUTONOMO`. | `PerfilCliente` |
| **Requisito Documental** | Par (informação a verificar, tipo de documento aceite) derivado do Anexo I secção II. Gerado dinamicamente a partir do Perfil de Cliente, residência e canal. | `RequisitoDocumental` |
| **Checklist Documental** | Conjunto de Requisitos Documentais instanciado para um Pedido concreto, com estado por item (`PENDENTE`, `SUBMETIDO`, `VERIFICADO`, `REJEITADO`). | `ChecklistDocumental` |
| **Evidência** | Registo imutável, com timestamp e hash, que prova que um acto regulatoriamente exigido ocorreu (ex.: disponibilização das Condições Gerais, assinatura, consentimento). Evidência ≠ documento: a evidência prova o **acto**, o documento é o **conteúdo**. | `Evidencia` |
| **Triagem PEP** | Verificação do enquadramento como PEP do cliente, de cada representante legal e de cada beneficiário efectivo, com atribuição de categoria da taxonomia `PEP_I/II/III`. | `TriagemPep` |
| **Rastreio de Sanções** | Verificação das partes contra listas de sanções e de restrições aplicáveis. Complementa a Triagem PEP; não a substitui. | `RastreioSancoes` |
| **Pontuação de Risco** | Resultado numérico e categórico (`BAIXO`, `MEDIO`, `ALTO`) da avaliação de risco BC/FT/FPADM do pedido, calculado por decisão DMN versionada e auditável. Determina o nível de diligência. | `PontuacaoRisco`, `NivelRisco` |
| **Nível de Diligência** | `SIMPLIFICADA`, `NORMAL` ou `REFORCADA`. Deriva da Pontuação de Risco e da Triagem PEP. Determina que subprocessos de aprovação são activados. | `NivelDiligencia` |
| **Decisão de Abertura** | Acto de aprovação ou recusa do Pedido, com autor (humano ou automático), fundamento, regras aplicadas e timestamp. Toda decisão automática tem de ser explicável pela versão da regra que a produziu. | `DecisaoAbertura` |
| **Entrega Inicial de Fundos** | Primeiro crédito na conta, sujeito aos requisitos dos Art. 4.º n.º 4 e Art. 3.º n.º 7. | `EntregaInicialFundos` |
| **Ordenante** | Titular da conta de origem da Entrega Inicial de Fundos. Se divergir do Cliente, exige justificação credível. | `Ordenante` |
| **Instituição de Origem Elegível** | IFB de onde provêm os fundos iniciais, sobre a qual existe aferição registada de que aplica medidas de identificação e diligência. | `InstituicaoOrigemElegivel` |
| **Prova de Vida** (*liveness*) | Verificação de que a captura biométrica corresponde a pessoa presente e viva, e não a fotografia, vídeo ou máscara. Requisito técnico do canal `REMOTO`. | `ProvaVida` |
| **Correspondência Facial** (*face match*) | Comparação entre a face capturada em vivo e a fotografia do documento de identificação. | `CorrespondenciaFacial` |
| **Assinatura Biométrica** | Meio biométrico usado para servir a função de assinatura quando o cliente não sabe ou não pode assinar (Art. 4.º n.º 3). Caminho normativo, não excepção técnica. | `AssinaturaBiometrica` |
| **Excepção de Processo** | Desvio identificado que impede a continuação automática e requer intervenção humana qualificada (Tarefa de Utilizador). Toda excepção tem tipo, responsável, prazo e desfecho registados. | `ExcepcaoProcesso` |
| **Pedido de Informação Adicional** (*RFI*) | Solicitação ao cliente de dado ou documento em falta ou rejeitado, com prazo. Suspende a contagem do SLA interno (*stop-the-clock*). | `PedidoInformacaoAdicional` |
| **Recusa por Identidade Não Comprovada** | Estado terminal obrigatório quando persistem dúvidas sobre a verdadeira identidade do cliente, representante ou beneficiário efectivo (Anexo I, I.1 ponto 4). Não existe "pendente indefinido" nesta situação. | `EstadoPedido.RECUSADO_IDENTIDADE_NAO_COMPROVADA` |

---

## 4. Termos de BPM (ABPMP CBOK)

| Termo | Definição | Uso no Banking Hub |
|---|---|---|
| **BPM** (*Business Process Management*) | Disciplina de gestão que trata processos de negócio como activos organizacionais, geridos ponta-a-ponta ao longo de um ciclo de vida. Não é software nem projecto pontual. | Disciplina que estrutura toda a documentação `docs/02-bpm/` |
| **Ciclo de vida BPM** | Planeamento e Estratégia → Análise → Desenho → Implementação → Monitorização e Controlo → Refinamento. | Estrutura do roteiro do projecto |
| **Processo de Negócio** | Conjunto de actividades inter-relacionadas, ponta-a-ponta, que transforma entradas em saídas de valor para um cliente. Atravessa funções e sistemas. | `PRC-01` a `PRC-06` |
| **Arquitectura de Processos** | Representação hierárquica dos processos da organização (cadeia de valor → grupos → processos → subprocessos → actividades → tarefas). | `02-arquitectura-de-processos.md` |
| **AS-IS** | Modelo do processo **como é hoje**, incluindo os seus defeitos. Descrever o AS-IS idealizado é o erro mais comum e invalida a análise. | `03-as-is-abertura-de-conta.md` |
| **TO-BE** | Modelo do processo **como deve passar a ser**, desenhado para resolver as causas-raiz identificadas na análise do AS-IS. | `05-to-be-abertura-de-conta.md` |
| **Dono do Processo** (*Process Owner*) | Papel único, nomeado, com autoridade e responsabilidade sobre o desempenho ponta-a-ponta do processo, transversalmente às funções. Sem Dono do Processo nomeado, não há BPM. | `docs/00-fundacao/02-stakeholders-raci.md` |
| **SIPOC** | *Suppliers, Inputs, Process, Outputs, Customers* — enquadramento de fronteira do processo antes de modelar. | Secção de cada processo |
| **Análise de Valor Agregado** | Classificação de cada actividade como VA (agrega valor para o cliente), BVA (necessária ao negócio ou à conformidade) ou NVA (desperdício, a eliminar). Em banca regulada, **BVA não é desperdício**. | `04-analise-as-is.md` |
| **Análise de Passagens** (*Handoff Analysis*) | Identificação de cada transferência de responsabilidade entre pessoas, equipas ou sistemas. Passagens são o principal local de atraso, perda de informação e erro. | `04-analise-as-is.md` |
| **Tempo de Ciclo** | Tempo total decorrido entre início e fim de uma instância do processo, incluindo esperas. | `KPI-01` |
| **Tempo de Processamento** | Soma do tempo de trabalho efectivo. A diferença face ao Tempo de Ciclo é o tempo de espera. | `KPI-02` |
| **Eficiência do Ciclo** | Tempo de Processamento ÷ Tempo de Ciclo. Revela quanto do prazo é espera pura. | `KPI-03` |
| **FTR** (*First Time Right*) | Percentagem de instâncias concluídas sem retrabalho nem pedido de informação adicional. | `KPI-05` |
| **RTY** (*Rolled Throughput Yield*) | Produto dos rendimentos de cada etapa: probabilidade de uma instância atravessar todo o processo sem defeito. Expõe o efeito multiplicativo de falhas pequenas. | `KPI-06` |
| **STP** (*Straight-Through Processing*) | Percentagem de instâncias que concluem sem qualquer intervenção humana. | `KPI-04` |
| **Regra de Negócio** | Afirmação que define ou restringe um aspecto do negócio, expressa de forma declarativa e independente do processo que a invoca. Regras vivem em repositório próprio (`06-regras-de-negocio.md`) e, quando decisionais, em DMN. | `BR-*` |
| **BPMN 2.0** | *Business Process Model and Notation* — notação padrão OMG para modelação de processos. Notação única e obrigatória do projecto. | `bpmn/` |
| **DMN** | *Decision Model and Notation* — notação padrão OMG para modelação de decisões. Usada para elegibilidade, requisitos documentais, risco e diligência. | `bpmn/` |
| **Orquestração** | Coordenação central e explícita da sequência de actividades por um motor de processos, com estado persistido. | Camunda 8 |
| **Coreografia** | Colaboração entre participantes por troca de mensagens, sem coordenador central. | Eventos de integração |
| **Tarefa de Serviço** (*Service Task*) | Actividade automatizada executada por sistema. | `Service Task` + *job worker* |
| **Tarefa de Utilizador** (*User Task*) | Actividade que requer decisão ou acção humana, atribuída por papel. | `User Task` + Tasklist |
| **Compensação** | Reversão de efeitos de actividades já concluídas quando o processo falha adiante. Em vez de transacção distribuída, define-se a acção compensatória de cada passo. | Padrão Saga |

---

## 5. Termos técnicos e de arquitectura

| Termo | Definição | Uso no Banking Hub |
|---|---|---|
| **Módulo** | Unidade de encapsulamento de um subdomínio de negócio, com fronteira explícita, modelo próprio e contrato de integração. | `bh-*` |
| **Contexto Delimitado** (*Bounded Context*) | Fronteira dentro da qual um modelo de domínio e a sua linguagem são consistentes. Módulo e contexto coincidem por desenho. | Ver `docs/03-arquitectura/02-modelo-de-dominio.md` |
| **BFF** (*Backend for Frontend*) | Camada de agregação e adaptação dedicada a um cliente específico — aqui, a app Android. Isola o mobile da granularidade dos módulos internos. | `bh-onboarding-bff` |
| **Job Worker** | Componente que subscreve e executa tarefas de serviço publicadas pelo motor de processos, devolvendo resultado, falha ou erro de negócio. | `bh-orchestration` |
| **Erro de Negócio** (*BPMN Error*) | Falha esperada e modelada no diagrama, tratada por um caminho alternativo (ex.: identidade não comprovada). Distingue-se de falha técnica, que é retentada. | `bh-orchestration` |
| **Idempotência** | Propriedade de uma operação que, repetida com a mesma chave, produz o mesmo efeito de uma única execução. Obrigatória em todos os *workers* e endpoints de escrita. | `chaveIdempotencia` |
| **Multi-Instituição** (*multi-tenant*) | Capacidade de servir várias IFB na mesma instalação, com isolamento de dados, parametrização e regras por instituição. | `tenantId` |
| **WORM** (*Write Once, Read Many*) | Armazenamento que impede alteração ou eliminação de objectos durante o período de retenção. Sustenta a retenção de 10 anos. | Ver `ADR-0006` |
| **Registo de Auditoria** | Sequência imutável e encadeada por hash de todos os eventos relevantes de um Pedido. Não é log de aplicação. | `RegistoAuditoria` |
| **Minimização de Dados** | Recolher apenas o necessário à finalidade declarada. Em Angola, os campos mínimos são os do Anexo I; recolha adicional exige fundamentação de risco registada. | Lei n.º 22/11 (Protecção de Dados Pessoais) |

---

## 6. Antónimos e distinções que geram erro

Distinções que, na experiência de projectos de *onboarding*, produzem defeitos recorrentes quando colapsadas:

| Não confundir | Com | Distinção |
|---|---|---|
| **Cliente** | **Titular** | Cliente é a parte contratual; Titular é o papel relativamente a uma conta. Uma conta colectiva tem vários Titulares e vários Clientes. |
| **Titular** | **Representante Legal** | O Titular é o dono da conta; o Representante actua em nome dele. Numa conta de menor, o menor é Titular e o pai/mãe é Representante. |
| **Representante Legal** | **Procurador** | O Representante deriva de lei ou estatuto; o Procurador deriva de mandato voluntário com âmbito delimitado. O Anexo I trata-os em campos distintos. |
| **Beneficiário Efectivo** | **Titular de participação ≥ 20%** | Nem todo titular de ≥ 20% é beneficiário efectivo, e o beneficiário efectivo pode deter controlo abaixo de 20% por outros meios. O Anexo I exige a identificação de ambos. |
| **Beneficiário Efectivo** | **Beneficiário da conta** | O primeiro é conceito de controlo (BC/FT); o segundo é destinatário de fundos. |
| **Documento** | **Evidência** | O documento é conteúdo apresentado pelo cliente; a evidência é o registo probatório de que um acto ocorreu (ex.: disponibilização das Condições Gerais). |
| **Verificação** | **Validação** | Verificação confirma que a informação corresponde ao documento; validação confirma que o documento é autêntico e idóneo. |
| **Conta Aberta** | **Conta Activa** | A abertura é a celebração do contrato; a activação é o estado operacional que permite movimentação. No TO-BE, existe estado intermédio explícito. |
| **Contrato de Abertura** | **Condições Gerais** | As Condições Gerais são **uma das três** peças do contrato (Art. 2.º g)). |
| **Recusa** | **Desistência** | Recusa é decisão da instituição; desistência é abandono pelo cliente. Métricas e obrigações de retenção diferem. |
| **Retenção de 10 anos** | **Encerramento aos 15 anos** | 10 anos é retenção documental (Anexo I); 15 anos é o prazo de encerramento de conta sem movimento (Art. 13.º n.º 8). Regras independentes. |
| **Conta Dormente (24 meses)** | **Conta sem movimento (15 anos)** | A primeira restringe débitos (Art. 7.º); a segunda obriga a encerrar (Art. 13.º n.º 8). |
| **Residência fiscal** | **Residência cambial** | Qualificações distintas com fontes legais distintas. O Anexo I usa expressamente "não residentes cambiais". |
| **Diligência Simplificada** | **Dispensa de campos do Anexo I** | A diligência simplificada reduz a **profundidade** da verificação; **nunca** dispensa os campos mínimos do Anexo I. |
| **Falha técnica** | **Erro de negócio** | A primeira é retentada automaticamente; o segundo segue caminho alternativo modelado. Tratá-los da mesma forma produz pedidos presos ou recusas indevidas. |
