# Visão de Arquitectura

> Estruturada em níveis C4: Contexto, Contentores, Componentes. As decisões que a justificam estão em `adr/`.

---

## Nível 1 — Contexto

```
                              ┌───────────────────────────────┐
        Cliente / Repr. ─────►│                               │
        (App Android)         │                               │◄──── Gestor de Balcão
                              │        BANKING HUB            │      (App do gestor)
        Entidade terceira ────►│                               │
        mandatada             │  Plataforma de Onboarding     │◄──── Analista Operações
                              │  Bancário — Aviso 1/23 BNA    │      Analista Compliance
                              │                               │      Chefe Compliance
                              └───┬───────┬───────┬───────┬───┘
                                  │       │       │       │
                    ┌─────────────┘       │       │       └──────────────┐
                    ▼                     ▼       ▼                      ▼
           ┌────────────────┐   ┌──────────────┐ ┌────────────────┐ ┌──────────────┐
           │ Core Bancário  │   │ Verificação  │ │ Listas PEP e   │ │ Notificações │
           │ (conta, IBAN,  │   │ de Identidade│ │ de Sanções     │ │ SMS / e-mail │
           │  activação)    │   │ OCR/face/vida│ │                │ │ / push       │
           └────────────────┘   └──────────────┘ └────────────────┘ └──────────────┘

           Consumidores de saída: BNA (autoridade competente) · Auditoria interna e externa
```

| Actor / sistema | Relação |
|---|---|
| Cliente / Representante legal | Inicia e conduz o pedido pela app Android; submete dados e documentos; assina |
| Gestor de Balcão | Conduz o canal presencial na app do gestor |
| Analista de Operações | Resolve excepções documentais e de dados |
| Analista de Compliance | Revisão manual de identidade; avaliação de justificações de fundos |
| Chefe de Compliance | Aprova diligência reforçada |
| Entidade terceira mandatada | Inicia pedidos ao abrigo do Art. 3.º n.º 6 |
| Core bancário | Cria, marca e activa a conta; devolve número e IBAN |
| Verificação de identidade | OCR/MRZ, validação documental, correspondência facial, prova de vida |
| Listas PEP e sanções | Fonte de rastreio das partes |
| Notificações | Comunicação com o cliente |
| BNA | Solicita registos ao abrigo do Anexo I |

## Nível 2 — Contentores

```
┌──────────────────────────────────────────────────────────────────────────────┐
│  CANAIS                                                                      │
│  ┌────────────────────────┐   ┌────────────────────────┐                     │
│  │ App Android (Kotlin)   │   │ App do Gestor (Kotlin) │                     │
│  │ Cliente final          │   │ Balcão / tablet        │                     │
│  └───────────┬────────────┘   └───────────┬────────────┘                     │
└──────────────┼────────────────────────────┼──────────────────────────────────┘
               │            HTTPS / REST + JSON, OIDC
               ▼                            ▼
┌──────────────────────────────────────────────────────────────────────────────┐
│  BORDA                                                                        │
│  ┌────────────────────────────────────────────────────────────────────────┐   │
│  │ bh-onboarding-bff — Spring Boot                                        │   │
│  │ Agregação para mobile · autenticação · limitação de taxa               │   │
│  └────────────────────────────────┬───────────────────────────────────────┘   │
└───────────────────────────────────┼──────────────────────────────────────────┘
                                    ▼
┌──────────────────────────────────────────────────────────────────────────────┐
│  ORQUESTRAÇÃO                                                                 │
│  ┌───────────────────────────┐        ┌────────────────────────────────────┐  │
│  │ Camunda 8 (Zeebe)         │◄──────►│ bh-orchestration — Spring Boot     │  │
│  │ BPMN · DMN · Tasklist     │        │ Job workers · mapeamento de erros  │  │
│  │ Operate                   │        │ compensação (Saga)                 │  │
│  └───────────────────────────┘        └────────────────┬───────────────────┘  │
└────────────────────────────────────────────────────────┼─────────────────────┘
                                                         ▼
┌──────────────────────────────────────────────────────────────────────────────┐
│  MÓDULOS DE DOMÍNIO — Spring Boot (Java)                                      │
│  ┌───────────┐ ┌───────────┐ ┌───────────┐ ┌───────────┐ ┌───────────┐        │
│  │bh-customer│ │bh-document│ │  bh-kyc   │ │bh-contract│ │bh-funding │        │
│  │ Ficha de  │ │ Docs, OCR │ │ Identidade│ │ CG/CP/FTI │ │ Entrega   │        │
│  │ Cliente   │ │ checklist │ │ PEP risco │ │ assinatura│ │ inicial   │        │
│  └───────────┘ └───────────┘ └───────────┘ └───────────┘ └───────────┘        │
│  ┌───────────┐ ┌───────────┐ ┌───────────┐ ┌────────────────┐                 │
│  │bh-account │ │bh-archive │ │ bh-audit  │ │bh-notification │                 │
│  │ Conta,IBAN│ │ Dossiê,   │ │ Cadeia de │ │ SMS/e-mail/    │                 │
│  │ activação │ │ retenção  │ │ hash      │ │ push           │                 │
│  └───────────┘ └───────────┘ └───────────┘ └────────────────┘                 │
└──────────────────────────────────────────────────────────────────────────────┘
                                    │
┌───────────────────────────────────┼──────────────────────────────────────────┐
│  PERSISTÊNCIA E INFRA-ESTRUTURA                                               │
│  ┌────────────┐ ┌────────────┐ ┌────────────┐ ┌────────────┐ ┌────────────┐  │
│  │ PostgreSQL │ │ Objectos   │ │  Kafka /   │ │ Keycloak   │ │ Observabi- │  │
│  │ por módulo │ │ WORM (S3)  │ │  Redpanda  │ │  (OIDC)    │ │ lidade     │  │
│  └────────────┘ └────────────┘ └────────────┘ └────────────┘ └────────────┘  │
└──────────────────────────────────────────────────────────────────────────────┘
                                    │
┌───────────────────────────────────┼──────────────────────────────────────────┐
│  ADAPTADORES DE SAÍDA                                                         │
│  ┌──────────────────┐ ┌──────────────────┐ ┌──────────────────┐              │
│  │ Adaptador Core   │ │ Adaptador Ident. │ │ Adaptador Listas │              │
│  │ (API/ficheiro)   │ │ (fornecedor)     │ │ (PEP/sanções)    │              │
│  └──────────────────┘ └──────────────────┘ └──────────────────┘              │
└──────────────────────────────────────────────────────────────────────────────┘
```

## Nível 3 — Componentes de um módulo (arquitectura hexagonal)

Todos os módulos `bh-*` seguem a mesma estrutura interna. A uniformidade é deliberada: reduz o custo cognitivo de navegar entre módulos.

```
bh-<modulo>/
└── src/main/java/ao/bankinghub/<modulo>/
    ├── api/                    Adaptadores de entrada (REST, consumidores de eventos)
    │   ├── rest/
    │   └── event/
    ├── application/            Casos de uso; orquestração intra-módulo; transacções
    │   ├── command/
    │   ├── query/
    │   └── port/               Portos de saída (interfaces)
    ├── domain/                 Modelo de domínio puro — sem Spring, sem JPA
    │   ├── model/              Agregados, entidades, objectos de valor
    │   ├── event/              Eventos de domínio
    │   └── rule/               Regras invariantes (BR-*)
    └── infrastructure/         Adaptadores de saída
        ├── persistence/        JPA, repositórios
        ├── client/             Clientes HTTP, mensageria
        └── config/             Configuração Spring
```

**Regra de dependência:** `api → application → domain` e `infrastructure → application`. O pacote `domain` **não depende de nada** — nem de Spring, nem de JPA, nem de outro módulo. Verificado por teste de arquitectura (ArchUnit).

## Princípios de arquitectura

| # | Princípio | Consequência prática |
|---|---|---|
| 1 | **A norma é o contrato.** A matriz `REG-*` é a especificação de mais alto nível | Requisito sem teste falha o *build* |
| 2 | **Regras de negócio fora do código imperativo** | Elegibilidade, requisitos documentais, risco e diligência em DMN versionado (`ADR-0003`) |
| 3 | **Um modelo de processo, muitas variantes** | Subprocessos condicionais e decisões DMN; nunca um diagrama por perfil (`ADR-0004`) |
| 4 | **Domínio puro** | `domain` sem dependências de infra-estrutura |
| 5 | **Módulo = contexto delimitado** | Fronteira explícita, base de dados própria por esquema, integração por contrato |
| 6 | **Evidência é cidadã de primeira classe** | Todo acto regulatoriamente exigido produz evidência imutável no momento em que ocorre |
| 7 | **Idempotência em toda escrita** | Chave de idempotência obrigatória; reenvio não duplica efeito |
| 8 | **Sem dados pessoais em variáveis de processo** | Variáveis carregam referências e identificadores; dados sensíveis ficam nos módulos |
| 9 | **Falha técnica ≠ erro de negócio** | Primeira é retentada; segundo segue caminho modelado |
| 10 | **Multi-instituição desde o desenho** | `tenantId` obrigatório em todo agregado, esquema e mensagem |
| 11 | **Monólito modular primeiro** | Módulos com fronteiras rigorosas, implantáveis juntos; separáveis quando houver razão operacional (`ADR-0002`) |
| 12 | **Tudo em contentores** | Nenhuma dependência de instalação manual em máquina |

## Requisitos não funcionais

| Categoria | Requisito |
|---|---|
| Disponibilidade | 99,5% em horário de expediente; degradação graciosa quando fornecedor externo indisponível |
| Desempenho | p95 de resposta da API < 500 ms; decisão DMN < 100 ms |
| Capacidade | 500 pedidos concorrentes; 5 000 pedidos por dia por instituição |
| Segurança | TLS em trânsito; cifra em repouso; segredos em cofre; OIDC; princípio do menor privilégio |
| Privacidade | Minimização de dados; consentimento versionado; redacção automática em registos; retenção limitada à finalidade |
| Auditabilidade | Cadeia de hash de todos os eventos de pedido; versões de processo e regras persistidas |
| Retenção | ≥ 10 anos em WORM, com bloqueio técnico de expurgo antecipado |
| Recuperação | Dossiê completo recuperável em < 5 min (p95) |
| Observabilidade | Rastreio distribuído correlacionado por `pedidoId`; métricas de negócio e de conformidade |
| Portabilidade | Nenhuma dependência de serviço proprietário de nuvem específica |
