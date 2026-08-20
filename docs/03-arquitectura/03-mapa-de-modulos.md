# Mapa de Módulos

---

## 1. Módulos

| Módulo | Responsabilidade | `REG-*` que sustenta |
|---|---|---|
| `bh-bom` | Gestão centralizada de versões de dependências. Nenhuma versão declarada fora daqui. | — |
| `bh-common` | Tipos partilhados: `TenantId`, `PedidoId`, `Dinheiro`, `Nif`, `Hash`, `VersaoRecurso`, erros, idempotência, contexto de auditoria. **Sem lógica de negócio.** | — |
| `bh-onboarding-bff` | Borda para as apps Android: agregação, autenticação, limitação de taxa, tradução de erros. | — |
| `bh-orchestration` | *Job workers*, publicação de mensagens, mapeamento erro técnico ↔ erro de negócio, compensação Saga. Recursos BPMN e DMN. | Todos, indirectamente |
| `bh-customer` | Ficha de Cliente, partes, elegibilidade, perfil de cliente. | `REG-ABR-01`, `REG-ABR-02`, `REG-ABR-06`, `REG-KYC-01`, `REG-KYC-02` |
| `bh-document` | Catálogo documental, geração de checklist, submissão, verificação, armazenamento. | `REG-KYC-10`, `REG-KYC-11`, `REG-KYC-12` |
| `bh-kyc` | Verificação de identidade, triagem PEP, rastreio de sanções, beneficiário efectivo, risco, nível de diligência. | `REG-KYC-05`–`REG-KYC-09` |
| `bh-contract` | Geração de CG, CP e FTI; validação dos 13 temas; disponibilização evidenciada; assinatura; celebração. | `REG-ABR-07`, `REG-KYC-03`, `REG-KYC-04`, `REG-INF-01`–`REG-INF-04` |
| `bh-funding` | Entrega inicial de fundos, ordenante, instituição de origem, justificação credível. | `REG-FUN-01`–`REG-FUN-03` |
| `bh-account` | Conta, IBAN, titularidade, marcação de não residente, produtos, limites de menor, activação. Adaptador do core. | `REG-ABR-03`, `REG-ABR-08`, `REG-INF-05`, `REG-MEN-01`–`REG-MEN-04` |
| `bh-archive` | Dossiê, evidências, política de retenção, WORM, índice de recuperação. | `REG-RET-01`–`REG-RET-03`, `REG-INF-02` |
| `bh-audit` | Cadeia de eventos encadeada por hash, consulta de trilho. | `REG-RET-02` |
| `bh-notification` | SMS, e-mail e *push*; modelos por instituição. | — |
| `bh-tenant` | Parametrização por instituição: minutas, limiares, catálogos, registo de instituições elegíveis. | `REG-FUN-02` e todos os parametrizáveis |
| `bh-app` | Composição executável: agrega os módulos num único artefacto implantável. | — |

## 2. Matriz de dependências permitidas

`✓` permitido · vazio proibido. Verificado por teste de arquitectura — dependência não declarada falha o *build*.

| ↓ depende de → | common | tenant | customer | document | kyc | contract | funding | account | archive | audit | notif. |
|---|---|---|---|---|---|---|---|---|---|---|---|
| `bh-onboarding-bff` | ✓ | ✓ | | | | | | | | | |
| `bh-orchestration` | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ |
| `bh-customer` | ✓ | ✓ | | | | | | | ✓ | ✓ | |
| `bh-document` | ✓ | ✓ | | | | | | | ✓ | ✓ | |
| `bh-kyc` | ✓ | ✓ | | | | | | | ✓ | ✓ | |
| `bh-contract` | ✓ | ✓ | | | | | | | ✓ | ✓ | |
| `bh-funding` | ✓ | ✓ | | | | | | | ✓ | ✓ | |
| `bh-account` | ✓ | ✓ | | | | | | | ✓ | ✓ | |
| `bh-archive` | ✓ | ✓ | | | | | | | | ✓ | |
| `bh-audit` | ✓ | ✓ | | | | | | | | | |
| `bh-notification` | ✓ | ✓ | | | | | | | | ✓ | |
| `bh-tenant` | ✓ | | | | | | | | | ✓ | |

**Regras estruturais:**

1. **Nenhum módulo de domínio depende de outro módulo de domínio.** `bh-kyc` não importa `bh-customer`. Precisa de dados do cliente? Recebe-os do orquestrador ou consome evento. Isto mantém as fronteiras reais em vez de decorativas.
2. **Só `bh-orchestration` conhece todos.** É a única camada autorizada a coordenar módulos, e não contém regras de domínio.
3. **`bh-archive` e `bh-audit` são destinos, nunca origens.** Recebem evidências e eventos; não invocam domínio.
4. **`bh-common` não contém regras de negócio.** Se um tipo em `bh-common` tem lógica de decisão, está no lugar errado.
5. **Sem dependências circulares.** Verificado por ArchUnit e pelo grafo de dependências Maven.

## 3. Estrutura de repositório

```
banking-hub/
├── README.md
├── as-is-bp.md                          Fluxo AS-IS original (fonte de partida)
│
├── docs/
│   ├── 00-fundacao/                     Termo de abertura, RACI, roteiro, riscos
│   ├── 01-regulatorio/                  Aviso 1/23, Anexo I, matriz REG-*
│   ├── 02-bpm/                          Glossário, arquitectura de processos,
│   │                                    AS-IS, TO-BE, regras BR-*, KPIs, rastreabilidade
│   ├── 03-arquitectura/                 Visão, domínio, módulos, contratos, ADRs
│   └── _assets/aviso-01-23/             Páginas do Diário da República (fonte)
│
├── bpmn/
│   ├── as-is/                           PRC-01-abertura-conta-as-is.bpmn
│   ├── to-be/                           PRC-01-abertura-conta.bpmn + subprocessos
│   ├── dmn/                             9 decisões
│   └── forms/                           Formulários de tarefas de utilizador
│
├── backend/
│   ├── pom.xml                          POM agregador
│   ├── bh-bom/                          Gestão de versões
│   ├── bh-common/
│   ├── bh-tenant/
│   ├── bh-customer/
│   ├── bh-document/
│   ├── bh-kyc/
│   ├── bh-contract/
│   ├── bh-funding/
│   ├── bh-account/
│   ├── bh-archive/
│   ├── bh-audit/
│   ├── bh-notification/
│   ├── bh-orchestration/
│   ├── bh-onboarding-bff/
│   └── bh-app/                          Composição executável
│
├── mobile/
│   └── android/                         App Kotlin (cliente + gestor)
│
├── deploy/
│   ├── docker-compose.yml               Ambiente local completo
│   ├── docker-compose.observability.yml
│   └── env/                             Configuração por ambiente
│
└── tools/
    └── trace-check/                     Verificação de integridade da matriz
```

## 4. Convenções

| Aspecto | Convenção |
|---|---|
| Pacote base | `ao.bankinghub.<modulo>` |
| Nomes de domínio | Português, conforme o glossário — `FichaCliente`, não `CustomerForm` |
| Nomes de tabela | `snake_case` em português — `ficha_cliente`, `beneficiario_efectivo` |
| Esquema de base de dados | Um por módulo — `bh_customer`, `bh_kyc`, … |
| Tipo de tarefa BPMN | `<modulo>.<accao>` — `kyc.triarPep`, `contract.disponibilizarCondicoes` |
| Variáveis de processo | `camelCase` em português; apenas referências e identificadores |
| Endpoints REST | `/api/v1/<recurso>` em português |
| Anotação de rastreabilidade | `@Regulatorio("REG-KYC-08")` em classes e testes; `REG-*` em documentação BPMN |
| Migrações | Flyway, por módulo, `V<n>__<descricao>.sql` |
| Testes | `<Classe>Test` unitário · `<Fluxo>IT` integração · `<Processo>ProcessTest` processo |
