# Registo de Riscos

**Escala:** Probabilidade e Impacto de 1 (muito baixo) a 5 (muito alto). Exposição = P × I.
**Prioridade:** `≥ 15` crítico · `9–14` alto · `4–8` médio · `≤ 3` baixo.

---

## Riscos de conformidade

| ID | Risco | P | I | Exp. | Resposta | Dono |
|---|---|---|---|---|---|---|
| `R-01` | Interpretação divergente do BNA sobre suficiência da verificação remota de identidade ao abrigo do Art. 3.º n.º 5 | 3 | 5 | **15** | **Mitigar** — consulta prévia formal ao BNA antes do piloto; documentar controlos de prova de vida e correspondência facial; manter canal presencial operacional como alternativa | Compliance KYC |
| `R-02` | Falso negativo na triagem PEP conduz a relação de negócio sem diligência reforçada | 3 | 5 | **15** | **Mitigar** — triagem de todas as partes (`BR-PEP-01`); taxonomia integral; auditoria mensal de amostra; `KPC-02` a 100% | Compliance BC/FT |
| `R-03` | Evidência de disponibilização prévia das Condições Gerais insuficiente em auditoria | 2 | 5 | 10 | **Mitigar** — *gateway* de anterioridade `E10` impede tecnicamente a celebração (`INV-02`); evidência com hash e timestamp | Compliance KYC |
| `R-04` | Conservação exclusivamente por processo tecnológico não aceite pelo BNA | 2 | 4 | 8 | **Mitigar** — Anexo I secção II admite expressamente "qualquer processo tecnológico"; obter confirmação escrita; arquitectura permite arquivo físico paralelo | Compliance KYC |
| `R-05` | Minuta de Condições Gerais em uso não cobre os 13 temas | 3 | 4 | 12 | **Mitigar** — `dmn-cobertura-cg` valida por instância; falha suspende o processo e não é contornável (`BR-INF-03`) | Jurídico |
| `R-06` | Tratamento de dados biométricos incumpre a Lei n.º 22/11 | 2 | 5 | 10 | **Mitigar** — minimização de dados; consentimento explícito versionado; cifra em repouso e em trânsito; retenção limitada à finalidade; veto de Segurança | Segurança |
| `R-07` | Alteração normativa durante o desenvolvimento invalida regras implementadas | 3 | 3 | 9 | **Aceitar com controlo** — regras em DMN versionado; matriz `REG-*` como ponto único de actualização; revisão trimestral | Compliance KYC |
| `R-08` | Dossiê expurgado antes dos 10 anos por erro operacional | 2 | 5 | 10 | **Mitigar** — WORM com bloqueio técnico de expurgo (`BR-RET-01`); teste `RetencaoTest#expurgoAntesDe10AnosRejeitado` | Arquitecto |

## Riscos de projecto

| ID | Risco | P | I | Exp. | Resposta | Dono |
|---|---|---|---|---|---|---|
| ~~`R-09`~~ | ~~Dono do Processo não é nomeado~~ | — | — | — | **Materializado e reclassificado** pela emenda E-01. Não existe instituição, logo não existe Dono a nomear. Passa a cláusula do compromisso de instalação; ver `R-29` | — |
| `R-10` | Nenhum fornecedor de verificação de identidade atinge fiabilidade utilizável com documentos angolanos | 3 | 4 | 12 | **Mitigar e diferir** — impacto reduzido pela emenda E-01: a fatia vertical usa implementação simulada atrás de interface estável, pelo que o risco deixa de bloquear a construção e passa a incidir sobre a substituição. Prova de conceito com amostra real antes da primeira instalação; critério de insucesso `S2` | Arquitecto |
| `R-11` | Core bancário não expõe API de criação e activação de conta | 3 | 3 | 9 | **Mitigar e diferir** — adaptador simulado atrás de contrato de referência publicado (`1P.5`); a estratégia alternativa por ficheiro ou fila é escolhida por instituição na instalação | Arquitecto |
| `R-12` | AS-IS não é validado e o TO-BE resolve problemas hipotéticos | 4 | 4 | **16** | **Aceite com controlo** — probabilidade subiu com a emenda E-01, porque a validação deixou de ser possível antes do desenho. Controlos: AS-IS marcado como não validado em todo lugar onde é citado; estimativas nunca apresentadas como medições; protocolo de validação preservado como pré-requisito de instalação; parametrização por instituição como mecanismo de absorção da divergência | Arquitecto |
| `R-13` | Alargamento de escopo — pressão para incluir movimentação, extractos ou crédito no Escopo 1 | 4 | 3 | 12 | **Mitigar** — escopo explícito no Termo de Abertura; alterações só por decisão do Patrocinador com replaneamento | Gestor de Projecto |
| `R-14` | Minutas contratuais não são fornecidas no prazo, bloqueando a Fase E | 3 | 4 | 12 | **Mitigar** — arranque de `1.4` em paralelo com `1.2`; minuta provisória para desenvolvimento, marcada como não válida para produção | Jurídico |
| `R-15` | Fase conceptual é vista como documentação a saltar para "começar a programar" | 3 | 4 | 12 | **Mitigar** — a matriz de rastreabilidade é a especificação; sem `REG-*` não há teste, e sem teste não há passagem a produção | Gestor de Projecto |
| `R-16` | Registo de instituições que aplicam diligência não é constituído | 3 | 3 | 9 | **Diferir** — passa a pré-requisito de instalação, por instituição; sem registo, `REG-FUN-02` não é verificável | Compliance BC/FT |
| `R-29` | Produto construído sem cliente resolve um problema mal caracterizado, ou bem caracterizado mas que ninguém quer resolver agora | 3 | 5 | **15** | **Mitigar por validação comercial** — apresentar a três interlocutores antes de investir na segunda fatia. A mitigação não é técnica: é tratar a ausência de interesse como informação sobre o diagnóstico, e não como falha de venda. Critério de insucesso `S5` | Gestor de Projecto |

## Riscos técnicos

| ID | Risco | P | I | Exp. | Resposta | Dono |
|---|---|---|---|---|---|---|
| `R-17` | Instâncias presas por falha de serviço externo sem tratamento distinto de erro de negócio | 3 | 4 | 12 | **Mitigar** — separação estrita entre falha técnica (retentativa) e erro de negócio (caminho modelado); temporizador obrigatório em todo estado não terminal (`INV-10`) | Arquitecto |
| `R-18` | Duplicação de contas no core por reenvio de mensagem | 3 | 4 | 12 | **Mitigar** — idempotência obrigatória em todos os *workers* e endpoints de escrita; chave de idempotência derivada de `pedidoId` | Arquitecto |
| `R-19` | Falha após criação da conta no core deixa conta órfã | 3 | 3 | 9 | **Mitigar** — padrão Saga com compensação explícita; conta permanece `ABERTA_NAO_ACTIVA` até verificação de fundos | Arquitecto |
| `R-20` | Regras de negócio migram silenciosamente para código, tornando-se inauditáveis | 4 | 4 | **16** | **Mitigar** — `ADR-0003`; verificação de integridade da matriz na integração contínua (regra sem entrada falha o *build*) | Arquitecto |
| `R-21` | Modelo BPMN cresce até tornar-se ingovernável, com uma variante por perfil de cliente | 3 | 3 | 9 | **Mitigar** — um único modelo com subprocessos condicionais e decisões DMN (`ADR-0004`); variante nova é linha de tabela DMN, não novo diagrama | Arquitecto |
| `R-22` | Dados pessoais e biométricos expostos em registos de aplicação, telemetria ou variáveis de processo | 3 | 5 | **15** | **Mitigar** — proibição de dados pessoais em variáveis de processo (apenas referências); redacção automática em registos; revisão de Segurança obrigatória | Segurança |
| `R-23` | Isolamento entre instituições falha em instalação multi-instituição | 2 | 5 | 10 | **Mitigar** — `tenantId` obrigatório em todo agregado; teste de isolamento por instituição na integração contínua | Arquitecto |
| `R-24` | Divergência entre a versão de regra aplicada e a registada, impossibilitando explicar decisões antigas | 2 | 4 | 8 | **Mitigar** — versão da decisão persistida na instância (`INV-09`); minuta contratual congelada por pedido (`BR-INF-05`) | Arquitecto |

## Riscos operacionais e de adopção

| ID | Risco | P | I | Exp. | Resposta | Dono |
|---|---|---|---|---|---|---|
| `R-25` | Equipas de balcão resistem ao canal remoto por perceberem perda de relevância | 3 | 3 | 9 | **Mitigar** — envolver gestores de balcão nas oficinas de TO-BE; canal presencial digitalizado valoriza o papel em vez de o eliminar | Operações |
| `R-26` | Compliance é sobrecarregado por excesso de excepções na entrada em produção | 3 | 3 | 9 | **Mitigar** — calibrar limiares de risco no piloto; painel de filas com alerta de capacidade | Compliance BC/FT |
| `R-27` | Clientes abandonam por dificuldade de captura de documento ou selfie em dispositivos de gama baixa | 4 | 3 | 12 | **Mitigar** — validação de qualidade no dispositivo com orientação em tempo real; retomar pedido mais tarde; canal presencial como alternativa declarada | Retalho |
| `R-28` | Indicadores de desempenho optimizados à custa de conformidade | 2 | 5 | 10 | **Mitigar** — contra-indicadores obrigatórios (`KPI-13`–`KPI-16`); veto de Compliance | Dono do Processo |

## Riscos críticos — resumo

Actualizado pela emenda E-01.

| ID | Risco | Exp. | Dono |
|---|---|---|---|
| `R-20` | Regras de negócio migram para código | 16 | Arquitecto |
| `R-12` | AS-IS não validado — probabilidade subiu no Modo Produto | 16 | Arquitecto |
| `R-01` | Interpretação do BNA sobre verificação remota | 15 | Compliance KYC |
| `R-02` | Falso negativo na triagem PEP | 15 | Compliance BC/FT |
| `R-22` | Exposição de dados pessoais e biométricos | 15 | Segurança |
| `R-29` | Produto sem cliente resolve o problema errado | 15 | Gestor de Projecto |

**Saíram da lista:** `R-09` materializou-se e foi reclassificado; `R-10` desceu a 12 por a construção deixar de depender do fornecedor real.

**Entraram:** `R-29`, criado pela emenda; `R-12` subiu de 12 para 16, e é agora o risco que melhor descreve a fragilidade do modo actual.

**Governação.** Registo revisto quinzenalmente pelo Gestor de Projecto durante as Fases 1 a 3, e mensalmente depois. Risco crítico exige plano de resposta escrito com prazo e revisão semanal até descer de 15.
