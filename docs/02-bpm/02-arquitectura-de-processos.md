# Arquitectura de Processos — Banking Hub

> Conforme o **ABPMP BPM CBOK**, antes de modelar qualquer processo é necessário situá-lo na arquitectura de processos da organização. Modelar um processo isolado, sem fronteiras nem contexto, produz um diagrama que ninguém consegue governar.

---

## 1. Cadeia de Valor do Onboarding Bancário

```
                    ┌──────────────────── CICLO DE VIDA DO CLIENTE ────────────────────┐
                    │                                                                  │
   ATRAIR      →   CONHECER      →   CONTRATAR    →   SERVIR      →   MANTER    →   TERMINAR
                    │                                                                  │
   Marketing        Identificação     Celebração       Movimentação    Actualização    Encerramento
   Captação         Diligência        do contrato      Extractos       Reavaliação     Sucessão
                    Avaliação risco   Activação                        de risco        Reversão Estado
                    │                                                                  │
                    └──── ESCOPO PRIMÁRIO DO BANKING HUB ────┘
                              (Aviso n.º 1/23 do BNA)
```

O Aviso n.º 1/23 regula quatro momentos: **abertura**, **manutenção**, **movimentação** e **encerramento**. O Banking Hub cobre integralmente **abertura** e serve de sistema de registo para os restantes.

## 2. Hierarquia de processos

Níveis conforme CBOK: `0` cadeia de valor · `1` grupo de processos · `2` processo · `3` subprocesso · `4` actividade · `5` tarefa.

| Nível | Código | Designação | Dono do Processo | Escopo |
|---|---|---|---|---|
| 0 | `CV-ONB` | **Onboarding e Ciclo de Vida da Conta Bancária** | Director de Retalho | — |
| 1 | `GP-01` | Constituição da Relação de Negócio | Director de Retalho | Escopo 1 |
| 2 | `PRC-01` | **Abertura de Conta de Depósito** | Gestor de Processo de Onboarding | **Escopo 1** |
| 3 | `PRC-01.1` | Captação e Elegibilidade | Gestor de Processo de Onboarding | Escopo 1 |
| 3 | `PRC-01.2` | Recolha e Verificação de Identidade | Chefe de Compliance (KYC) | Escopo 1 |
| 3 | `PRC-01.3` | Recolha e Validação Documental | Chefe de Operações | Escopo 1 |
| 3 | `PRC-01.4` | Triagem PEP, Sanções e Avaliação de Risco | Chefe de Compliance (BC/FT) | Escopo 1 |
| 3 | `PRC-01.5` | Identificação de Beneficiário Efectivo | Chefe de Compliance (BC/FT) | Escopo 1 |
| 3 | `PRC-01.6` | Informação Pré-Contratual e Celebração | Direcção Jurídica | Escopo 1 |
| 3 | `PRC-01.7` | Entrega Inicial de Fundos e Activação | Chefe de Operações | Escopo 1 |
| 3 | `PRC-01.8` | Arquivo e Retenção do Dossiê | Chefe de Compliance (KYC) | Escopo 1 |
| 2 | `PRC-02` | Manutenção de Dados de Cliente e Reavaliação de Risco | Chefe de Compliance (KYC) | Escopo 2 |
| 2 | `PRC-03` | Gestão de Meios de Movimentação | Chefe de Operações | Escopo 2 |
| 1 | `GP-02` | Manutenção da Relação de Negócio | Chefe de Operações | Escopo 2/3 |
| 2 | `PRC-04` | Gestão de Contas Dormentes | Chefe de Operações | Escopo 3 |
| 2 | `PRC-05` | Tratamento de Eventos de Vida (óbito, insolvência) | Direcção Jurídica | Escopo 3 |
| 1 | `GP-03` | Extinção da Relação de Negócio | Chefe de Operações | Escopo 3 |
| 2 | `PRC-06` | Encerramento de Conta | Chefe de Operações | Escopo 3 |

**Processos de suporte transversais** (invocados por vários processos de negócio):

| Código | Designação | Natureza |
|---|---|---|
| `SUP-01` | Gestão de Excepções e Retrabalho | Suporte |
| `SUP-02` | Gestão de Consentimentos e Dados Pessoais | Suporte / conformidade |
| `SUP-03` | Auditoria e Resposta a Pedido de Autoridade Competente | Suporte / conformidade |
| `SUP-04` | Gestão de Versões de Minutas Contratuais e Regras | Governação |
| `SUP-05` | Parametrização por Instituição (*tenant*) | Governação |

## 3. SIPOC de `PRC-01` — Abertura de Conta de Depósito

| Fornecedores | Entradas | Processo | Saídas | Clientes |
|---|---|---|---|---|
| Cliente / Representante legal | Dados de identificação e caracterização | **1.** Captar pedido e aferir elegibilidade | Conta bancária activa com IBAN | Cliente |
| Cliente | Documentos comprovativos (Anexo I, II) | **2.** Verificar identidade | Contrato de Abertura de Conta celebrado | Instituição (Retalho) |
| Entidade terceira mandatada | Pedido intermediado + prova de mandato | **3.** Validar documentação | Dossiê de Abertura arquivado (10 anos) | Compliance |
| Conservatória do Registo Comercial | Certidão de registo comercial | **4.** Triar PEP e sanções | Pontuação e Nível de Risco atribuídos | BNA (autoridade competente) |
| AGT | Cartão / comprovativo de NIF | **5.** Avaliar risco BC/FT/FPADM | Evidências de conformidade | Auditoria interna e externa |
| Direcção Jurídica | Minutas de Condições Gerais e Particulares | **6.** Identificar beneficiário efectivo | Ficha de Cliente assinada | UIF / autoridades |
| Instituição de origem dos fundos | Transferência com identificação do ordenante | **7.** Disponibilizar informação pré-contratual | Registo de auditoria imutável | |
| Listas de sanções e PEP | Registos de rastreio | **8.** Celebrar contrato | Recusa fundamentada (quando aplicável) | |
| Core bancário | Numeração de conta, IBAN | **9.** Processar entrega inicial de fundos | | |
| | | **10.** Activar conta e arquivar dossiê | | |

**Fronteiras do processo.** Início: manifestação de interesse do cliente em abrir conta. Fim: um dos estados terminais — `ACTIVA`, `RECUSADO`, `DESISTIDO`, `EXPIRADO` — **com dossiê arquivado**. O arquivo faz parte do processo: um pedido recusado sem dossiê retido é uma instância incompleta, não uma instância terminada.

## 4. Participantes e papéis (*swimlanes*)

| Pista | Papel | Natureza | Presente no AS-IS | Presente no TO-BE |
|---|---|---|---|---|
| L1 | **Cliente / Representante Legal** | Externo | Sim | Sim |
| L2 | **Canal Digital (App Android)** | Sistema | Não | Sim |
| L3 | **Gestor de Balcão** | Humano | Sim | Sim (apenas canal presencial) |
| L4 | **Motor de Processos (Camunda)** | Sistema | Não | Sim |
| L5 | **Verificação de Identidade** | Sistema/serviço | Não (manual) | Sim |
| L6 | **Analista de Operações (*back-office*)** | Humano | Sim | Sim (só excepções) |
| L7 | **Analista de Compliance / KYC** | Humano | Sim | Sim (só risco elevado e PEP) |
| L8 | **Chefe de Compliance** | Humano | Sim | Sim (aprovação de EDD) |
| L9 | **Core Bancário** | Sistema | Sim (manual) | Sim (integrado) |
| L10 | **Arquivo / Retenção** | Sistema | Não (papel) | Sim |
| L11 | **Entidade Terceira Mandatada** | Externo | Parcial | Sim |

## 5. Regras de governação da arquitectura

1. **Notação única.** Todo processo é modelado em BPMN 2.0. Fluxogramas informais, tabelas de passos e descrições em prosa não substituem o modelo.
2. **Dono de Processo nomeado.** Nenhum processo entra em produção sem Dono nomeado, com autoridade para alterar o desenho.
3. **Regras fora do diagrama.** Lógica decisional vive em DMN ou no repositório de regras, nunca embutida em condições de *gateway* com expressões extensas. Um *gateway* invoca uma decisão; não a implementa.
4. **Rastreabilidade obrigatória.** Toda actividade que exista por imposição normativa referencia o seu `REG-*`. Actividade sem `REG-*` nem justificação de valor é candidata a eliminação.
5. **Versionamento.** Modelos de processo, decisões e minutas contratuais são versionados e a instância registra a versão sob a qual correu. Sem isto é impossível responder a uma autoridade competente sobre uma abertura de há sete anos.
6. **Medição desde o desenho.** Todo processo publicado tem KPI definido, ponto de captura instrumentado e linha de base registada antes da entrada em produção.
