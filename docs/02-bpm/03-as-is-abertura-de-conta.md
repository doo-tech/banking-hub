# AS-IS — `PRC-01` Abertura de Conta de Depósito

> **Aviso metodológico (ABPMP CBOK).** O AS-IS descreve o processo **como é**, incluindo os seus defeitos. Descrever o processo idealizado no lugar do real é o erro mais frequente na fase de Análise e invalida tudo o que se segue: sem AS-IS fiel não há causas-raiz, e sem causas-raiz o TO-BE resolve problemas que não existem.
>
> **Estado deste documento: `AS-IS DE REFERÊNCIA` — requer validação.**
> Este modelo representa o padrão observável na banca de retalho angolana e é consistente com a estrutura obrigatória do Aviso n.º 1/23. **Não substitui a observação directa na instituição de acolhimento.** Antes de congelar o TO-BE, cada instituição deve executar o protocolo de validação da secção 8. Os tempos e volumes indicados são **estimativas de referência**, marcadas com `†`, e devem ser substituídos por medição real.

**Fonte de partida:** `as-is-bp.md` na raiz do repositório (11 passos, fluxo de conta convencional).
**Cobertura normativa:** Aviso n.º 1/23 do BNA — Art. 3.º, 4.º, 5.º; Anexo I.

---

## 1. Enquadramento

| Item | Descrição |
|---|---|
| Processo | `PRC-01` — Abertura de Conta de Depósito |
| Âmbito do AS-IS | Conta convencional de depósito, pessoa singular e pessoa colectiva, canal presencial de balcão |
| Gatilho | Cliente comparece ao balcão ou contacta a instituição manifestando interesse em abrir conta |
| Estado terminal esperado | Conta activa e processo arquivado em suporte físico |
| Sistemas envolvidos | Core bancário (entrada manual), folha de cálculo de controlo de pendentes, correio electrónico, arquivo físico |
| Notação | BPMN 2.0 — `bpmn/as-is/PRC-01-abertura-conta-as-is.bpmn` |

## 2. Fluxo AS-IS — sequência de actividades

Cada actividade indica: pista responsável, tipo (`Manual`, `Utilizador`, `Serviço`), classificação de valor (`VA` agrega valor ao cliente · `BVA` necessária ao negócio ou à conformidade · `NVA` desperdício), requisito regulatório de origem e tempo estimado.

| # | Actividade | Pista | Tipo | Valor | `REG-*` | T. proc.† | T. espera† |
|---|---|---|---|---|---|---|---|
| 1 | **Contacto e escolha do canal** — o cliente inicia o processo com ou sem presença física. Se à distância, apenas por meio de comunicação à distância reconhecido (telefónico, electrónico, telemático). Pode também ser iniciado por entidade terceira com competência legal ou contratual. | L1 → L3 | Manual | VA | `REG-ABR-04` `REG-ABR-05` `REG-ABR-06` | 10 min | 20–90 min (fila) |
| 2 | **Preenchimento da Ficha de Cliente** — recolha, em papel, de todos os dados de identificação e caracterização do cliente e dos seus representantes legais, incluindo o motivo da abertura. | L1 + L3 | Manual | BVA | `REG-KYC-01` `REG-KYC-02` | 25–40 min | — |
| 3 | **Triagem PEP** — verificação, tipicamente por consulta visual e conhecimento do gestor, se o cliente, representante ou beneficiário efectivo se enquadra como Pessoa Politicamente Exposta, accionando diligência reforçada. | L3 → L7 | Manual | BVA | `REG-KYC-08` | 10 min | 4 h – 3 dias |
| 4 | **Recolha documental diferenciada por tipo de cliente** — o conjunto de documentos varia conforme o perfil: pessoa singular, comerciante em nome individual, pessoa colectiva, ou organização sem fins lucrativos. Cada perfil tem exigências adicionais próprias (ex.: matrícula comercial e identificação de titulares ≥ 20% para pessoas colectivas). | L1 + L3 | Manual | BVA | `REG-KYC-10` `REG-KYC-06` `REG-KYC-12` | 20 min | **1–15 dias** (cliente regressa com documentos em falta) |
| 5 | **Identificação do Beneficiário Efectivo** — quando aplicável, recolha de nome, assinatura, nacionalidade, documento de identificação e NIF do beneficiário efectivo. | L3 → L7 | Manual | BVA | `REG-KYC-07` | 20–60 min | 1–10 dias |
| 6 | **Assinatura da Ficha de Cliente** — pelos titulares e representantes. Se não souberem ou não puderem assinar, recurso a meios biométricos. | L1 + L3 | Manual | BVA | `REG-KYC-03` `REG-KYC-04` | 10 min | — |
| 7 | **Disponibilização das Condições Gerais e Particulares** — entrega ao cliente antes da abertura, com arquivo de evidência da disponibilização. As Condições Gerais têm de cobrir os 13 temas mínimos. | L3 | Manual | BVA | `REG-INF-01` `REG-INF-02` `REG-INF-03` `REG-INF-04` | 10 min | — |
| 8 | **Celebração do Contrato de Abertura de Conta** — formado pela junção de Ficha de Cliente + Condições Gerais + Condições Particulares (quando aplicável). | L1 + L3 | Manual | VA | `REG-ABR-07` | 10 min | — |
| 8b | **Registo no core bancário** — transcrição manual dos dados da ficha em papel para o sistema central. | L3 → L9 | Manual | NVA | — | 20–35 min | 1–3 dias |
| 8c | **Conferência em *back-office*** — segunda verificação da ficha e dos documentos por analista de operações; devolução ao balcão em caso de inconformidade. | L6 | Utilizador | BVA | `REG-KYC-10` | 15–30 min | **1–5 dias** |
| 9 | **Entrada inicial de fundos** — obrigatoriamente por transferência bancária que identifique o ordenante, oriunda de instituição que comprovadamente aplique diligência. Se os fundos vierem de conta de terceiro, exige-se justificação credível adicional. | L1 → L9 | Manual | BVA | `REG-FUN-01` `REG-FUN-02` `REG-FUN-03` | 10 min | 1–3 dias |
| 10 | **Activação da conta** — o estado passa a `ACTIVA`. | L9 | Manual | VA | `REG-ABR-07` | 5 min | 0–2 dias |
| 11 | **Retenção do processo** — conservação dos registos por, no mínimo, 10 anos, em arquivo físico. | L3 → arquivo | Manual | BVA | `REG-RET-01` `REG-RET-02` `REG-RET-03` | 15 min | 5–30 dias até arquivo definitivo |

### Actividades ocultas não declaradas no fluxo nominal

O fluxo nominal de 11 passos omite trabalho que ocorre invariavelmente. Não modelá-lo é o que faz o AS-IS parecer mais eficiente do que é:

| # | Actividade oculta | Pista | Valor | Frequência† |
|---|---|---|---|---|
| H1 | Reconstituir contexto de um processo pendente (procurar pasta, reler ficha, telefonar ao colega) | L3, L6 | NVA | Todas as instâncias com espera > 1 dia |
| H2 | Contactar o cliente para pedir documento em falta ou ilegível | L3 | NVA | 40–60% das instâncias |
| H3 | Corrigir dado transcrito incorrectamente para o core | L6, L9 | NVA | 10–20% das instâncias |
| H4 | Actualizar folha de cálculo de controlo de pendentes | L3, L6 | NVA | Diária, por analista |
| H5 | Produzir relatório manual de pendentes e prazos para a chefia | L6 | NVA | Semanal |
| H6 | Localizar processo em arquivo físico para responder a pedido interno ou de autoridade | Arquivo | BVA | Por pedido — 2 h a 5 dias |

## 3. Análise de passagens (*handoffs*)

Cada passagem é um ponto de espera, de perda de informação e de diluição de responsabilidade.

| # | De → Para | Meio | Risco dominante |
|---|---|---|---|
| P1 | Cliente → Gestor de Balcão | Presencial, papel | Dados ilegíveis ou incompletos detectados tarde |
| P2 | Gestor de Balcão → Compliance (triagem PEP) | Correio electrónico ou telefone | Sem prazo nem estado; perde-se na caixa de entrada |
| P3 | Compliance → Gestor de Balcão | Correio electrónico | Fundamento da decisão não fica registado no processo |
| P4 | Gestor de Balcão → Core bancário | Transcrição manual | Erro de transcrição; divergência entre papel e sistema |
| P5 | Gestor de Balcão → *Back-office* | Malote físico ou digitalização | Trânsito de 1 a 3 dias; extravio |
| P6 | *Back-office* → Gestor de Balcão (devolução) | Correio electrónico + malote | Ciclo completo de retrabalho, custo duplicado |
| P7 | Cliente → Instituição (fundos iniciais) | Transferência interbancária | Conciliação manual do comprovativo com o pedido |
| P8 | Gestor de Balcão → Arquivo físico | Malote | Pasta arquivada sem índice pesquisável |

**Total: 8 passagens formais** em 11 actividades nominais. Um processo com mais passagens do que fases de valor está estruturalmente desenhado para atrasar.

## 4. Linha de base de desempenho

Valores de referência a substituir por medição real (protocolo na secção 8).

| Indicador | Valor† de referência | Observação |
|---|---|---|
| Tempo de Ciclo — pessoa singular, documentação completa | 3–5 dias úteis | |
| Tempo de Ciclo — pessoa singular, documentação incompleta | 10–20 dias úteis | Cenário maioritário |
| Tempo de Ciclo — pessoa colectiva | 15–30 dias úteis | Estrutura societária e BE |
| Tempo de Ciclo — cliente PEP | 20–45 dias úteis | Diligência reforçada sem prazo definido |
| Tempo de Processamento agregado | 3 h – 5 h | |
| **Eficiência do Ciclo** | **≈ 2–5%** | 95% ou mais do prazo é espera pura |
| Taxa de *First Time Right* (FTR) | 40–60% | |
| Taxa de abandono pelo cliente | 15–30% | Concentrada no passo 4 |
| Taxa de retrabalho (devolução do *back-office*) | 20–35% | |
| Processamento sem intervenção humana (STP) | **0%** | Nenhuma instância dispensa intervenção manual |
| Custo por conta aberta | Alto — 2 a 4 h de trabalho humano qualificado | |
| Tempo de resposta a pedido de autoridade competente | 2 h – 5 dias por processo | Depende de localização física |

**Rendimento acumulado (RTY).** Com rendimento por etapa de 90% ao longo de 11 etapas, o rendimento acumulado é `0,90¹¹ ≈ 31%`. Ou seja: cerca de duas em cada três instâncias sofrem pelo menos um defeito no percurso. Esta é a razão matemática pela qual "cada etapa está boa" e o processo global não está.

## 5. Análise de valor agregado

| Classificação | Actividades | Tempo de processamento† | % do tempo |
|---|---|---|---|
| **VA** — agrega valor ao cliente | 3 (1, 8, 10) | ≈ 25 min | 10% |
| **BVA** — exigida por norma ou negócio | 8 (2, 3, 4, 5, 6, 7, 8c, 9, 11) | ≈ 2 h 20 min | 55% |
| **NVA** — desperdício | 1 nominal (8b) + 6 ocultas | ≈ 1 h 30 min | 35% |

> **Leitura correcta.** Em banca regulada, **BVA não é desperdício**: os passos 2, 4, 5, 6, 7 e 11 existem porque o Aviso n.º 1/23 os impõe. O TO-BE não os elimina — **automatiza a sua execução e prova a sua conformidade**. O alvo da eliminação é o bloco NVA (35% do tempo de trabalho e a quase totalidade do tempo de espera).

## 6. Os sete desperdícios no processo actual

| Desperdício | Manifestação concreta |
|---|---|
| **Espera** | 95% do tempo de ciclo; cliente regressa ao balcão múltiplas vezes |
| **Transporte** | Malotes de papel entre balcão, *back-office* e arquivo |
| **Movimentação** | Deslocações repetidas do cliente à agência |
| **Sobreprocessamento** | Dupla e tripla conferência da mesma ficha por papéis distintos |
| **Inventário** | Pilha de processos pendentes sem visibilidade de prazo nem prioridade |
| **Defeitos** | Erros de transcrição, documentos ilegíveis, campos em branco, retrabalho de 20–35% |
| **Talento subaproveitado** | Analistas de Compliance a conferir campos de formulário em vez de avaliar risco |

## 7. Riscos e lacunas de conformidade no AS-IS

Riscos identificados face ao Aviso n.º 1/23. Não são hipóteses: são consequências directas de o processo ser manual e em papel.

| ID | Risco / lacuna | `REG-*` em causa | Impacto |
|---|---|---|---|
| `RAI-01` | Evidência de disponibilização das Condições Gerais é a assinatura numa folha, sem timestamp fiável nem prova de **anterioridade** face à celebração | `REG-INF-01` `REG-INF-02` | **Crítico** — a norma exige disponibilização *prévia*; o papel não prova a ordem temporal |
| `RAI-02` | Triagem PEP depende do conhecimento pessoal do gestor, sem lista de referência nem registo do critério aplicado | `REG-KYC-08` | **Crítico** — falso negativo de PEP é falha material de BC/FT |
| `RAI-03` | Verificação de titulares ≥ 20% e de beneficiário efectivo feita por leitura de acta, sem cálculo de participações nem registo do raciocínio | `REG-KYC-06` `REG-KYC-07` | **Alto** |
| `RAI-04` | Não há registo estruturado de que a instituição de origem dos fundos "comprovadamente aplique" diligência | `REG-FUN-02` | **Alto** |
| `RAI-05` | Divergência ordenante × titular verificada por inspecção visual; justificação credível arquivada sem decisor identificado | `REG-FUN-03` | **Alto** |
| `RAI-06` | Dúvida sobre identidade resulta em processo "pendente" indefinido, não em recusa formal | `REG-KYC-09` | **Alto** — a norma impõe recusa, não suspensão |
| `RAI-07` | Retenção em papel por 10 anos sem índice pesquisável nem garantia de integridade | `REG-RET-01` `REG-RET-02` `REG-RET-03` | **Alto** — o requisito é conservar *e* disponibilizar atempadamente |
| `RAI-08` | Cobertura dos 13 temas das Condições Gerais não é verificada por instância; depende de a minuta em uso na agência ser a versão correcta | `REG-INF-03` | **Médio** |
| `RAI-09` | Contas de não residentes marcadas por campo de texto livre no core, sem validação | `REG-ABR-03` | **Médio** |
| `RAI-10` | Assinatura biométrica (cliente que não sabe ou não pode assinar) é tratada como excepção sem procedimento padronizado | `REG-KYC-04` | **Médio** |
| `RAI-11` | Ausência de trilho de auditoria unificado: reconstituir *quem decidiu o quê e quando* exige cruzar papel, correio electrónico e core | Art. 16.º; `REG-RET-02` | **Alto** |
| `RAI-12` | Abertura não presencial (Art. 3.º n.º 5) não é praticada, por não existirem meios de verificação remota de identidade | `REG-ABR-05` | **Médio** — não é incumprimento, é oportunidade normativa não aproveitada |

## 8. Protocolo de validação do AS-IS

Executar antes de congelar o TO-BE. Sem isto, o TO-BE é uma hipótese.

| # | Técnica | Participantes | Saída | Duração |
|---|---|---|---|---|
| 1 | **Observação directa** (*go and see*) em 2 agências de perfis distintos | Analista de processos | Registo de desvios entre o processo declarado e o executado | 2 dias |
| 2 | **Oficina de modelação AS-IS** com quem executa, não apenas com quem chefia | Gestores de balcão, *back-office*, Compliance | Diagrama BPMN validado e assinado | 4 h |
| 3 | **Medição de tempos** por amostragem — mínimo 30 instâncias concluídas, estratificadas por perfil de cliente | Analista + Operações | Substituição de todos os valores `†` | 2 semanas |
| 4 | **Análise de causas-raiz** (5 Porquês + Ishikawa) sobre os 3 principais motivos de retrabalho | Equipa multifuncional | Lista de causas-raiz priorizada | 4 h |
| 5 | **Auditoria de conformidade documental** — amostra de 20 dossiês arquivados contra a matriz `REG-*` | Compliance + Auditoria interna | Confirmação ou refutação de `RAI-01` a `RAI-12` | 1 semana |
| 6 | **Validação com o Dono do Processo** | Dono do Processo | Aprovação formal do AS-IS como linha de base | 2 h |

## 9. Conclusão da análise

O processo actual **cumpre a norma** em substância — os 11 passos correspondem à estrutura obrigatória do Aviso n.º 1/23 — mas **não consegue provar** esse cumprimento de forma eficiente, nem escala.

O problema central não é a existência dos controlos: é que **os controlos são executados por pessoas sobre papel, e a prova da sua execução não é estruturada nem pesquisável**. Daí decorrem simultaneamente o desempenho fraco (eficiência de ciclo de 2–5%) e o risco regulatório (`RAI-01`, `RAI-02`, `RAI-11`).

**Direcção do TO-BE:** não retirar controlos, mas transformá-los de actos manuais em actos executados e evidenciados por sistema, com orquestração explícita, e reservar o julgamento humano para o que exige efectivamente julgamento — risco elevado, PEP e excepções.
