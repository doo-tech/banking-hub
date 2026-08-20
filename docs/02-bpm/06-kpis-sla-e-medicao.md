# KPIs, SLA e Plano de Medição

> **Princípio (ABPMP CBOK — Monitorização e Controlo).** Um processo que não é medido não é gerido. A definição do indicador, o ponto de captura e a linha de base têm de existir **antes** da entrada em produção — caso contrário, o projecto perde a capacidade de demonstrar que melhorou algo.
>
> **Regra anti-vaidade.** Todo indicador tem um **contra-indicador** que impede a sua optimização abusiva. Reduzir tempo de ciclo à custa de recusar menos casos de risco elevado não é melhoria: é transferência de risco para o balanço da instituição.

---

## 1. Indicadores de desempenho do processo

| ID | Indicador | Fórmula | Ponto de captura | AS-IS† | Alvo | Frequência |
|---|---|---|---|---|---|---|
| `KPI-01` | **Tempo de Ciclo** | `dataEstadoTerminal − dataInicio` | Motor de processos | 3–5 d (PS) / 15–30 d (PC) | < 15 min (PS risco baixo) / < 5 d (PC) | Contínua |
| `KPI-02` | **Tempo de Processamento humano** | Σ duração de tarefas de utilizador | Tasklist | 3–5 h | < 5 min | Contínua |
| `KPI-03` | **Eficiência do Ciclo** | `KPI-02 ÷ KPI-01` | Derivado | 2–5% | > 60% | Semanal |
| `KPI-04` | **STP** (*Straight-Through Processing*) | instâncias sem tarefa de utilizador ÷ total | Motor de processos | 0% | > 60% (risco baixo) | Diária |
| `KPI-05` | **FTR** (*First Time Right*) | instâncias sem RFI nem retrabalho ÷ total | Motor de processos | 40–60% | > 90% | Diária |
| `KPI-06` | **RTY** (*Rolled Throughput Yield*) | Π (rendimento de cada fase) | Derivado por fase | ≈ 31% | > 85% | Semanal |
| `KPI-07` | **Taxa de abandono** | instâncias `DESISTIDO` + `EXPIRADO` ÷ total iniciadas | Motor de processos | 15–30% | < 10% | Diária |
| `KPI-08` | **Tempo de recuperação de dossiê** | tempo entre pedido e entrega do dossiê completo | Módulo de arquivo | 2 h – 5 d | < 5 min (p95) | Por pedido |
| `KPI-09` | **Taxa de retrabalho** | instâncias com ≥ 1 devolução ÷ total | Motor de processos | 20–35% | < 5% | Semanal |
| `KPI-10` | **Custo por conta aberta** | custo total do processo ÷ contas activadas | Financeiro + motor | Alto | Redução ≥ 70% | Mensal |
| `KPI-11` | **Tempo até primeira resposta em RFI** | `dataRespostaCliente − dataEnvioRFI` | Motor de processos | Não medido | < 24 h (mediana) | Semanal |
| `KPI-12` | **Distribuição por canal** | instâncias por `CanalOrigem` ÷ total | Motor de processos | 100% presencial | > 70% remoto ao 12.º mês | Mensal |

## 2. Indicadores de conformidade

Estes indicadores não admitem alvo inferior a 100%. Um valor abaixo de 100% é incidente de conformidade, não desvio de desempenho.

| ID | Indicador | Fórmula | Alvo | Frequência |
|---|---|---|---|---|
| `KPC-01` | Cobertura de evidência de disponibilização prévia de CG/CP | instâncias celebradas com evidência anterior ÷ instâncias celebradas | **100%** | Diária |
| `KPC-02` | Cobertura de triagem PEP de todas as partes | instâncias activadas com triagem completa ÷ activadas | **100%** | Diária |
| `KPC-03` | Cobertura de checklist documental integralmente verificada | instâncias activadas com checklist completa ÷ activadas | **100%** | Diária |
| `KPC-04` | Cobertura de verificação de entrega inicial de fundos | instâncias activadas com verificação ÷ activadas | **100%** | Diária |
| `KPC-05` | Cobertura dos 13 temas das Condições Gerais | minutas em uso conformes ÷ minutas em uso | **100%** | Por publicação |
| `KPC-06` | Dossiês selados com retenção ≥ 10 anos aplicada | dossiês em estado terminal selados ÷ estados terminais | **100%** | Diária |
| `KPC-07` | Aprovações de diligência reforçada com autor e fundamento registados | aprovações completas ÷ aprovações | **100%** | Diária |
| `KPC-08` | Decisões automáticas com versão de regra registada | decisões com versão ÷ decisões automáticas | **100%** | Diária |
| `KPC-09` | Contas de não residente marcadas em atributo estruturado | contas marcadas ÷ contas de não residente | **100%** | Diária |
| `KPC-10` | Instâncias em estado não terminal com temporizador activo | instâncias com temporizador ÷ instâncias não terminais | **100%** | Contínua |
| `KPC-11` | Dúvidas de identidade resolvidas em estado terminal dentro do prazo | resolvidas no prazo ÷ total de revisões manuais | **100%** | Diária |

## 3. Contra-indicadores

| Indicador optimizado | Contra-indicador | Porquê |
|---|---|---|
| `KPI-01` Tempo de Ciclo | `KPC-02`, `KPC-03`, `KPI-14` (taxa de recusa por risco) | Acelerar recusando menos casos de risco elevado transfere risco para o balanço |
| `KPI-04` STP | `KPI-13` (taxa de reversão de decisão automática em auditoria) | STP alto com decisões automáticas erradas é pior do que STP baixo |
| `KPI-05` FTR | `KPI-15` (taxa de campos preenchidos com valor por omissão) | FTR alto obtido por aceitar dados de baixa qualidade é ilusório |
| `KPI-07` Taxa de abandono | `KPI-16` (taxa de elegibilidade recusada tardiamente) | Abandono baixo por deixar o cliente avançar sabendo que não é elegível desperdiça o tempo dele |
| `KPI-10` Custo por conta | `KPC-01`–`KPC-11` | Reduzir custo removendo controlos é incumprimento |

| ID | Contra-indicador | Alvo |
|---|---|---|
| `KPI-13` | Taxa de reversão de decisão automática em auditoria de amostra | < 2% |
| `KPI-14` | Taxa de recusa por risco (monitorizada por estabilidade, não por nível) | Sem queda abrupta não explicada |
| `KPI-15` | Taxa de campos com valor por omissão aceite | < 5% |
| `KPI-16` | Instâncias recusadas por elegibilidade após a Fase A | < 1% |

## 4. SLA internos por fase

Contagem em horas úteis. RFI ao cliente aplica *stop-the-clock*.

| Fase | Actividade | SLA | Escalonamento |
|---|---|---|---|
| A | Elegibilidade e checklist | 60 s | Incidente técnico |
| B | Verificação automática de identidade | 5 min | Fila de revisão manual |
| B7 | Revisão manual de identidade | 4 h | Chefe de Compliance às 4 h; recusa às 24 h |
| C5 | Verificação automática de documento | 10 min | Fila de revisão manual |
| C5 | Revisão manual de documento | 8 h | Chefe de Operações às 8 h |
| C6 | RFI — prazo do cliente | 7 dias corridos | Recordatório ao 3.º e 6.º dia; `EXPIRADO` ao 7.º |
| D4/D5 | Triagem PEP e sanções | 2 min | Incidente técnico |
| D6/D7 | Risco e diligência | 30 s | Incidente técnico |
| D9 | Aprovação de diligência reforçada | 16 h | Direcção de Compliance às 16 h |
| E | Geração e disponibilização de CG/CP/FTI | 60 s | Incidente técnico |
| F4 | Espera de fundos iniciais | 15 dias corridos | Recordatório ao 5.º e 10.º dia; `EXPIRADO` ao 15.º |
| F9 | Avaliação de justificação credível | 8 h | Chefe de Compliance às 8 h |
| G | Selagem e arquivo do dossiê | 5 min | Incidente bloqueante — instância não fecha |

## 5. SLA externos (compromisso com o cliente)

| Cenário | Compromisso comunicado |
|---|---|
| PS residente, remoto, risco baixo, documentação completa | Conta activa no mesmo dia, após recepção dos fundos iniciais |
| PS residente, remoto, risco médio | Até 1 dia útil |
| PS ou representante qualificado como PEP | Até 3 dias úteis |
| Pessoa colectiva | Até 5 dias úteis após checklist completa |
| Resposta a RFI | Análise em até 1 dia útil após submissão |

## 6. Plano de medição

| Dimensão | Fonte de dados | Mecanismo |
|---|---|---|
| Tempos de fase e de instância | Motor de processos (histórico de instâncias) | Exportação para armazém analítico |
| Tarefas humanas | Tasklist | Exportação |
| Decisões e versões de regra | Registo de avaliação DMN | Persistido no dossiê |
| Conformidade | Registo de auditoria encadeado | Consulta directa |
| Custo | Sistema financeiro + tempos de tarefa | Cálculo mensal |
| Experiência do cliente | Telemetria da app + inquérito pós-activação | Agregado, sem dados pessoais |

**Painéis obrigatórios:**

| Painel | Audiência | Conteúdo |
|---|---|---|
| Operacional | Chefe de Operações | Fila por fase, instâncias em risco de SLA, excepções abertas |
| Conformidade | Chefe de Compliance | `KPC-01`–`KPC-11`, aprovações pendentes de EDD, recusas por fundamento |
| Desempenho de processo | Dono do Processo | `KPI-01`–`KPI-12` com tendência e contra-indicadores |
| Executivo | Direcção de Retalho | Contas activadas, custo unitário, distribuição por canal, abandono |

## 7. Governação da medição

1. **Linha de base antes de produção.** Nenhuma fase entra em produção sem os valores `†` do AS-IS substituídos por medição real de, no mínimo, 30 instâncias.
2. **Revisão mensal de desempenho** conduzida pelo Dono do Processo, com Compliance e Operações presentes. Desvio sustentado de KPI por dois ciclos abre acção de refinamento.
3. **Revisão trimestral de regras.** Compliance confirma que os limiares parametrizáveis (`R` e `L` no repositório de regras) continuam adequados e que nenhum limiar `F` foi alterado.
4. **Auditoria de amostra mensal.** 30 instâncias, das quais no mínimo 10 com decisão automática, verificadas manualmente contra a matriz `REG-*`. Alimenta `KPI-13`.
5. **Indicador de conformidade abaixo de 100% é incidente**, com prazo de resolução de 24 h e comunicação obrigatória ao Chefe de Compliance.
