# ADR-0003 — Regras regulatórias em DMN versionado, não em código

**Estado:** Aceite · **Data:** 2026-08-19 · **Decisor:** Arquitecto + Chefe de Compliance KYC

## Contexto

O Aviso n.º 1/23 e a legislação conexa fixam limiares numéricos que mudam por acto normativo:

| Limiar | Fonte |
|---|---|
| 20% de participação no capital ou direitos de voto | Anexo I, I.2 |
| 24 meses para conta dormente | Art. 7.º n.º 1 |
| 14 anos para cartão de débito de menor | Art. 9.º n.º 2 |
| 60 dias de notificação de encerramento | Art. 13.º n.º 4 |
| 15 anos de inactividade para encerramento | Art. 13.º n.º 8 |
| 10 anos de retenção documental | Anexo I, I.1 |
| 13 temas mínimos das Condições Gerais | Art. 5.º n.º 2 |

O Art. 17.º do próprio Aviso revoga três avisos anteriores — prova de que este corpo normativo muda. Se cada limiar for um literal em código Java, cada acto normativo do BNA torna-se um ciclo de desenvolvimento, e a pergunta "que regra aplicaram a este pedido em 2027?" não tem resposta consultável.

Há um segundo problema, mais grave: Compliance não consegue revisar código Java. Uma regra que Compliance não consegue ler é uma regra que Compliance não pode aprovar, e a responsabilidade perante o BNA é de Compliance.

## Decisão

Toda regra decisional vive em DMN versionado, e não em código imperativo. Nove decisões identificadas em `docs/02-bpm/04-to-be-abertura-de-conta.md` secção 5.

Cada avaliação de decisão persiste, na instância, a **versão** da decisão aplicada (`INV-09`).

**Fica em código** apenas o que não é decisional: invariantes de agregado (`INV-01` a `INV-10`), validação estrutural de schema e integração.

## Distinção operacional

| Vive em DMN | Vive em código |
|---|---|
| Limiar de participação de 20% | Que a soma de participações não exceda 100% |
| Que perfil exige que documentos | Que um documento submetido tenha ficheiro e tipo válidos |
| Como se calcula a pontuação de risco | Que uma pontuação de risco não exista sem versão de decisão |
| Que nível de risco activa diligência reforçada | Que diligência reforçada exija aprovação humana registada |
| Idade mínima para cartão de débito | Que a conta não active sem contrato celebrado |

Regra de arbitragem: **se um acto normativo pode alterar o valor, é DMN. Se alterar exigiria alterar o significado do domínio, é código.**

## Alternativas consideradas

| Alternativa | Porque foi rejeitada |
|---|---|
| Constantes em código | Cada alteração normativa é um ciclo de desenvolvimento; Compliance não consegue revisar; sem histórico consultável de que regra foi aplicada |
| Tabela de configuração em base de dados | Alterável sem revisão, sem versionamento e sem representação legível; pior do que código, porque também não é auditável |
| Motor de regras dedicado (Drools) | Capacidade acima da necessidade; sem integração nativa com o motor de processos; DMN é padrão OMG e legível por não programadores |
| Regras nas condições dos *gateways* BPMN | Expressões extensas em diagramas tornam-se ilegíveis; a decisão fica presa ao processo em vez de ser reutilizável |

## Consequências

**Positivas** — alteração normativa é publicação de nova versão de decisão; Compliance revê tabelas de decisão em vez de código; toda decisão automática é explicável pela versão que a produziu; decisões reutilizáveis entre processos.

**Negativas aceites** — mais uma linguagem no projecto (FEEL); risco de lógica migrar silenciosamente para código, mitigado pela verificação de integridade da matriz de rastreabilidade no *build* (`R-20`); tabelas DMN grandes exigem disciplina de organização.

## Verificação

- Toda `BR-*` de tipo `DERIVACAO` ou `RESTRICAO` com limiar parametrizável mapeia para uma decisão DMN nomeada.
- A verificação de integridade da matriz falha o *build* se existir regra sem entrada, ou entrada sem teste.
