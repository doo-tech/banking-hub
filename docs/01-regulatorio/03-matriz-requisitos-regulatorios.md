# Matriz de Requisitos Regulatórios — `REG-*`

Cada linha é um requisito **atómico e testável** extraído do Aviso n.º 1/23 do BNA. Este é o identificador canónico usado por toda a documentação, pelos modelos BPMN/DMN, pelas regras de negócio (`BR-*`) e pelos testes automatizados.

**Convenção de identificação:** `REG-<DOMÍNIO>-<NN>`

| Domínio | Significado |
|---|---|
| `ABR` | Abertura de conta |
| `KYC` | Identificação e diligência |
| `INF` | Informação a prestar ao cliente |
| `FUN` | Entrega inicial de fundos |
| `MOV` | Movimentação |
| `MEN` | Contas de menores |
| `FX` | Moeda estrangeira |
| `DOR` | Contas dormentes |
| `EVE` | Eventos de vida (óbito, insolvência) |
| `ENC` | Encerramento |
| `RET` | Retenção e arquivo |

**Aplicabilidade ao Escopo 1 (abertura de conta via mobile):** `S` = no escopo do MVP · `P` = parcialmente (o Banking Hub produz/consome o dado, o ciclo de vida corre fora) · `N` = fora do escopo do MVP, mas modelado no domínio.

---

## Abertura de conta

| ID | Requisito | Fonte | Escopo 1 | Verificação |
|---|---|---|---|---|
| `REG-ABR-01` | Pessoas singulares e colectivas, residentes e não residentes, podem ser titulares de contas em moeda nacional e/ou estrangeira | Art. 3.º n.º 1 | S | Teste de elegibilidade por combinação (tipo de pessoa × residência × moeda) |
| `REG-ABR-02` | Menores podem ser titulares de contas de depósito, abertas pelos seus representantes legais | Art. 3.º n.º 2 | S | Fluxo de menor exige ≥1 representante legal identificado e verificado |
| `REG-ABR-03` | Contas tituladas por não residentes devem ser devidamente identificadas como tal, independentemente da moeda | Art. 3.º n.º 3 | S | Atributo `residenteFlag` persistido na conta e propagado ao core bancário; asserção em teste de integração |
| `REG-ABR-04` | A abertura pode ser efectuada com ou sem presença física do cliente | Art. 3.º n.º 4 | S | Canais `PRESENCIAL` e `REMOTO` suportados pelo mesmo processo |
| `REG-ABR-05` | Sem presença física, a abertura só pode ser efectuada mediante uso **exclusivo** de meios de comunicação à distância | Art. 3.º n.º 5 | S | Nenhum passo obrigatório do fluxo `REMOTO` exige presença física; teste de cobertura de fluxo |
| `REG-ABR-06` | A abertura pode ser feita por entidades terceiras com competência legal ou contratualmente atribuída | Art. 3.º n.º 6 | P | Canal `TERCEIRO_MANDATADO` com registo da fonte de competência (mandato/contrato) |
| `REG-ABR-07` | O contrato de abertura de conta é constituído por Ficha de Cliente + Condições Gerais + Condições Particulares (quando aplicável) | Art. 2.º g) | S | O agregado `ContratoAberturaConta` só transita para `CELEBRADO` com as três componentes referenciadas |
| `REG-ABR-08` | Regimes de titularidade suportados: singular, colectiva solidária, colectiva conjunta, colectiva mista | Art. 2.º e), f) | S | Enum fechado; regra de movimentação derivada do regime |

## Identificação e diligência (KYC/CDD)

| ID | Requisito | Fonte | Escopo 1 | Verificação |
|---|---|---|---|---|
| `REG-KYC-01` | A instituição deve dispor de fichas de cliente que assegurem a recolha de toda a informação necessária para identificar e caracterizar o cliente e os seus representantes | Art. 4.º n.º 1; Anexo I | S | Schema da Ficha de Cliente cobre 100% dos campos do Anexo I; teste de completude por tipo de cliente |
| `REG-KYC-02` | A ficha deve registar o **motivo para a abertura da conta** | Art. 4.º n.º 1 | S | Campo `motivoAbertura` obrigatório, de vocabulário controlado + texto livre |
| `REG-KYC-03` | A ficha deve ser assinada pelos titulares e/ou seus representantes | Art. 4.º n.º 2 | S | Evidência de assinatura vinculada ao hash do documento assinado |
| `REG-KYC-04` | Devem ser utilizados meios biométricos para servir a função da assinatura sempre que o cliente não saiba ou não possa assinar | Art. 4.º n.º 3 | S | Caminho alternativo `ASSINATURA_BIOMETRICA` no fluxo, activado por declaração de incapacidade de assinar |
| `REG-KYC-05` | As obrigações de identificação aplicam-se também a clientes já existentes, em função da avaliação de risco de BC/FT/FPADM | Anexo I, I.1 | P | Processo de *periodic review* modelado; execução fora do MVP |
| `REG-KYC-06` | Devem ser identificados os titulares de participações no capital e direitos de voto ≥ 20% de pessoas colectivas | Anexo I, I.2 | S | Regra de decisão DMN `dmn-ubo-threshold`; soma de participações validada |
| `REG-KYC-07` | O beneficiário efectivo deve ser identificado com o conjunto completo de campos do Anexo I, I.4 | Anexo I, I.4 | S | Schema `BeneficiarioEfectivo` obrigatório quando aplicável |
| `REG-KYC-08` | Deve ser aferido o enquadramento como Pessoa Politicamente Exposta (cliente, representante e beneficiário efectivo), nos termos do n.º 31 do art. 3.º da Lei n.º 5/20 | Anexo I, I.1 + nota 2 | S | Triagem PEP sobre todas as partes; categoria PEP registada com a taxonomia `PEP_I/II/III` |
| `REG-KYC-09` | Havendo dúvidas sobre a verdadeira identidade do cliente, representante legal ou beneficiário efectivo, não resolúveis satisfatoriamente, a instituição deve **recusar a realização de quaisquer operações** | Anexo I, I.1 ponto 4 | S | Estado terminal `RECUSADO_IDENTIDADE_NAO_COMPROVADA`; proibida transição para `ACTIVA` |
| `REG-KYC-10` | As informações prestadas devem ser verificadas mediante apresentação dos documentos listados no Anexo I, secção II | Anexo I, II | S | Matriz documento×informação; nenhum campo verificável fica sem documento associado |
| `REG-KYC-11` | Para morada e profissão admitem-se "documentos, meios ou diligências considerados válidos, idóneos e suficientes" | Anexo I, II.1 e II.3 | S | Catálogo extensível por instituição, com registo obrigatório da fundamentação de idoneidade |
| `REG-KYC-12` | Pessoas colectivas não residentes: documento de registo certificado no país de residência **e** autenticado pela representação consular de Angola no país de origem | Anexo I, II.2 | S | Dupla validação obrigatória (`certificadoOrigem`, `autenticacaoConsular`) |

## Informação a prestar ao cliente

| ID | Requisito | Fonte | Escopo 1 | Verificação |
|---|---|---|---|---|
| `REG-INF-01` | **Previamente** à abertura, disponibilizar exemplar das Condições Gerais e Particulares | Art. 5.º n.º 1 | S | Ordenação temporal validada: `timestamp(disponibilizacao) < timestamp(celebracao)` |
| `REG-INF-02` | Arquivar evidência da disponibilização das Condições Gerais e Particulares | Art. 5.º n.º 1 | S | Registo imutável com versão do documento, canal, timestamp e hash |
| `REG-INF-03` | As Condições Gerais devem cobrir, no mínimo, os 13 temas das alíneas a) a m) | Art. 5.º n.º 2 | S | *Checklist* de conformidade da minuta contratual — 13 asserções (ver abaixo) |
| `REG-INF-04` | Disponibilizar ao cliente a Ficha Técnica Informativa, nos termos da regulamentação sobre Deveres de Informação no âmbito dos depósitos bancários | Art. 5.º n.º 3 | S | Documento gerado e evidência arquivada |
| `REG-INF-05` | Disponibilizar extractos das contas de depósito através dos canais acordados; primeira via sem custos | Art. 6.º | P | Preferência de canal de extracto capturada na abertura; emissão fora do MVP |

### Os 13 temas mínimos das Condições Gerais (`REG-INF-03`)

| Cód. | Alínea | Tema |
|---|---|---|
| `CG-A` | a) | Regime de titularidade de conta bancária |
| `CG-B` | b) | Meios de comunicação entre a instituição e o cliente |
| `CG-C` | c) | Condições de movimentação das contas |
| `CG-D` | d) | Meios de movimentação das contas |
| `CG-E` | e) | Condições dos lançamentos a débito e a crédito pela instituição |
| `CG-F` | f) | Tratamento das instruções dos clientes e dos erros no processamento |
| `CG-G` | g) | Compensação de créditos |
| `CG-H` | h) | Dever de comunicação pelo cliente de alterações dos seus elementos de identificação |
| `CG-I` | i) | Tratamento de contas sem movimentos |
| `CG-J` | j) | Tratamento de dados pessoais |
| `CG-K` | k) | Termos, condições e procedimentos de encerramento de contas |
| `CG-L` | l) | Condições gerais de prestação de serviços de pagamento (processamento de transferências, prazos de execução, data-valor, informação sobre operações, operações não autorizadas ou incorrectamente executadas e respectivas responsabilidades) |
| `CG-M` | m) | Dever de informar o cliente de alterações às condições gerais e/ou particulares e os prazos para tal |

## Entrega inicial de fundos

| ID | Requisito | Fonte | Escopo 1 | Verificação |
|---|---|---|---|---|
| `REG-FUN-01` | No canal não presencial, a entrega inicial de fundos deve ser efectuada por **transferência bancária que permita a identificação do ordenante** | Art. 4.º n.º 4 | S | Rejeição de entrada inicial em numerário ou sem identificação do ordenante no canal `REMOTO` |
| `REG-FUN-02` | A conta de origem dos fundos deve estar aberta junto de instituição que **comprovadamente aplique** medidas de identificação e diligência dos seus clientes | Art. 4.º n.º 4 | S | Verificação contra registo de instituições elegíveis com evidência da aferição |
| `REG-FUN-03` | Entrega inicial com origem em conta titulada por pessoa diferente do cliente apenas é aceite mediante apresentação de **justificação credível** | Art. 3.º n.º 7 | S | Comparação ordenante×titular; se divergirem, exige documento `DOC_JUSTIFICACAO_FUNDOS_TERCEIRO` + decisão humana registada |

## Movimentação e manutenção

| ID | Requisito | Fonte | Escopo 1 | Verificação |
|---|---|---|---|---|
| `REG-MOV-01` | A movimentação deve respeitar o regime acordado nas Condições Gerais e Particulares | Art. 8.º | P | Regime persistido e aplicado pelo core; asserção no contrato de saída |
| `REG-MEN-01` | Contas de menores são movimentadas pelos representantes legais, no balcão, por *internet banking* ou cartão de débito quando disponibilizado | Art. 9.º n.º 1 | P | Mandato de movimentação do representante legal registado |
| `REG-MEN-02` | Cartão de débito sobre conta de menor permitido a partir dos 14 anos, mediante solicitação do representante legal e **termo de responsabilidade** assinado | Art. 9.º n.º 2 | S | Regra de idade + documento `DOC_TERMO_RESPONSABILIDADE_CARTAO` obrigatório |
| `REG-MEN-03` | Movimentos a débito com cartão limitados a valores máximos diários acordados no acto de solicitação | Art. 9.º n.º 3 | S | Limite diário capturado na abertura e transmitido ao emissor de cartões |
| `REG-MEN-04` | Em contas de menores não é permitida contratação de crédito nem instrumentos de pagamento fora do n.º 2 | Art. 9.º n.º 4 | S | Catálogo de produtos filtrado por `titularMenor = true` |
| `REG-FX-01` | Movimentação de contas em moeda estrangeira obedece à legislação cambial | Art. 10.º n.º 1 | N | Modelado no domínio; execução fora do MVP |
| `REG-FX-02` | Transferências a débito de contas em moeda estrangeira a favor de residentes só em moeda estrangeira nos casos: relação de grupo (PC), relação de parentesco (PS), ou ordenante = beneficiário | Art. 10.º n.º 2 | N | Regra DMN modelada; execução fora do MVP |
| `REG-FX-03` | Não é permitida concessão de crédito em contas em moeda estrangeira a favor de entidades não exportadoras | Art. 10.º n.º 3 | N | Flag `entidadeExportadora` capturada na abertura de conta FX |
| `REG-DOR-01` | Conta dormente = sem movimento a débito por período ≥ 24 meses | Art. 7.º n.º 1 | N | Definição codificada como parâmetro do domínio |
| `REG-DOR-02` | Devem existir procedimentos para identificar contas dormentes e aplicar restrições à movimentação a débito | Art. 7.º n.º 2 | N | Processo `PRC-04` modelado no TO-BE de longo prazo |

## Eventos de vida

| ID | Requisito | Fonte | Escopo 1 | Verificação |
|---|---|---|---|---|
| `REG-EVE-01` | Ao tomar conhecimento do falecimento de cliente: bloquear a débito as contas tituladas | Art. 11.º n.º 1 | N | Modelado |
| `REG-EVE-02` | Movimentação apenas por herdeiros, mediante certidão de óbito e habilitação de herdeiros ou documento equiparado | Art. 11.º n.º 1 | N | Modelado |
| `REG-EVE-03` | Disponibilizar aos herdeiros toda a informação solicitada, comprovada a qualidade de herdeiro | Art. 11.º n.º 2 | N | Modelado |
| `REG-EVE-04` | Concluído o processo sucessório: encerrar a conta ou alterar titulares, transferindo valores conforme definido | Art. 11.º n.º 3 | N | Modelado |
| `REG-EVE-05` | Em falência/insolvência de titular: bloquear a débito a conta, singular ou colectiva, e agir nos termos instruídos pelas autoridades judiciais | Art. 12.º | N | Modelado |

## Encerramento

| ID | Requisito | Fonte | Escopo 1 | Verificação |
|---|---|---|---|---|
| `REG-ENC-01` | Encerramento a pedido dos titulares/representantes ou por iniciativa da instituição | Art. 13.º n.º 1 | N | Modelado |
| `REG-ENC-02` | Encerramento a pedido do cliente depende de instrução de **todos** os titulares/representantes, com indicação do destino do saldo | Art. 13.º n.º 2 | N | Modelado |
| `REG-ENC-03` | A instituição pode recusar encerrar se existir saldo devedor, ordens/operações pendentes ou responsabilidades por liquidar, ou imposição judicial/impossibilidade legal | Art. 13.º n.º 3 | N | Modelado |
| `REG-ENC-04` | Encerramento por iniciativa da instituição exige notificação com pelo menos **60 dias** de antecedência | Art. 13.º n.º 4 | N | Modelado |
| `REG-ENC-05` | Encerramento com efeitos imediatos nas 6 situações das alíneas a) a f) do n.º 5 | Art. 13.º n.º 5 | N | Modelado |
| `REG-ENC-06` | O encerramento implica cancelamento imediato de todos os meios de movimentação entregues ao cliente | Art. 13.º n.º 6 | N | Modelado |
| `REG-ENC-07` | Sem instrução do cliente sobre o saldo, os fundos podem ser transferidos para conta contabilística interna | Art. 13.º n.º 7 | N | Modelado |
| `REG-ENC-08` | Encerrar contas sem movimentos a débito ou crédito por **15 anos**, antecedido de diligências de contacto incluindo publicação de editais no jornal de maior circulação | Art. 13.º n.º 8 | N | Modelado |
| `REG-ENC-09` | Não havendo oposição, o valor em depósito reverte ao Estado nos termos do Decreto-Lei n.º 187/70 | Art. 13.º n.º 9 | N | Modelado |

## Retenção e arquivo

| ID | Requisito | Fonte | Escopo 1 | Verificação |
|---|---|---|---|---|
| `REG-RET-01` | Recolher e conservar **todos** os registos relativos a clientes por período **mínimo de 10 anos** | Anexo I, I.1 ponto 3 (rem. Aviso n.º 14/20) | S | Política de retenção `>= 10 anos` aplicada e testada; expurgo bloqueado antes do prazo |
| `REG-RET-02` | Garantir que os registos estão **disponíveis atempadamente** para consulta pela autoridade competente | Anexo I, I.1 ponto 3 | S | SLA de recuperação de processo completo definido e medido (ver KPI `KPI-08`) |
| `REG-RET-03` | Os registos podem ser conservados em documentos físicos **e por qualquer processo tecnológico** nos termos a estabelecer pelo BNA | Anexo I, II | S | Arquivo digital com integridade verificável (hash + WORM); ver `ADR-0006` |

---

## Cobertura do Escopo 1

| Escopo | Requisitos | % |
|---|---|---|
| `S` — no MVP | 33 | 62% |
| `P` — parcial | 5 | 9% |
| `N` — modelado, fora do MVP | 15 | 28% |
| **Total** | **53** | **100%** |

Nenhum requisito do Aviso n.º 1/23 fica sem endereçamento explícito: os `N` estão modelados no domínio e no mapa de processos, com implementação diferida para os Escopos 2 e 3 do roteiro (`docs/00-fundacao/03-roadmap-e-fases.md`).
