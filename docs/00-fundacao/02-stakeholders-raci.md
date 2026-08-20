# Partes Interessadas e Matriz RACI

> **Princípio (ABPMP CBOK).** Processos atravessam funções; estruturas hierárquicas não. Sem um **Dono do Processo** com autoridade transversal, cada função optimiza a sua parte e o processo ponta-a-ponta não tem responsável. É a causa mais comum de fracasso em iniciativas de BPM.

---

## 1. Papéis

| Papel | Responsabilidade | Autoridade |
|---|---|---|
| **Patrocinador** | Garante financiamento, remove impedimentos organizacionais, arbitra conflitos entre direcções | Decide escopo e prioridades |
| **Dono do Processo (`PRC-01`)** | Responsável único pelo desempenho ponta-a-ponta do processo, transversalmente às funções | **Decide o desenho do processo**; aprova AS-IS e TO-BE |
| **Gestor de Projecto** | Planeamento, execução, riscos, comunicação | Decide sequência e alocação |
| **Arquitecto de Software** | Arquitectura, decisões de arquitectura (ADR), integridade técnica | Decide arquitectura e tecnologia |
| **Analista de Processos (BPM)** | Modelação AS-IS e TO-BE, medição, análise de causas-raiz | Propõe desenho |
| **Chefe de Compliance (BC/FT)** | Conformidade com a Lei n.º 5/20, PEP, sanções, risco | **Veto sobre risco de BC/FT**; aprova diligência reforçada |
| **Chefe de Compliance (KYC)** | Conformidade com o Aviso n.º 1/23 em matéria de identificação, diligência e retenção | **Veto sobre conformidade regulatória** |
| **Direcção Jurídica** | Minutas contratuais, cobertura dos 13 temas, protecção de dados | **Veto sobre conteúdo contratual** |
| **Chefe de Operações** | Execução operacional, filas, excepções, SLA | Decide organização do trabalho |
| **Direcção de Retalho** | Proposta de valor, catálogo de produtos, metas comerciais | Decide oferta |
| **Direcção de Sistemas** | Infra-estrutura, contentores, ambientes, integração com core | Decide operação técnica |
| **Segurança da Informação** | Segurança, criptografia, gestão de segredos, revisão de risco técnico | **Veto sobre risco de segurança** |
| **Auditoria Interna** | Verificação independente da rastreabilidade e dos controlos | Emite constatações |
| **Gestor de Balcão** | Executa o canal presencial | Executa |
| **Analista de Operações** | Resolve excepções documentais e de dados | Executa e decide dentro de alçada |
| **Analista de Compliance** | Revisão manual de identidade, avaliação de justificações | Executa e decide dentro de alçada |
| **Cliente** | Fornece dados e documentos; decide contratar | Consente ou desiste |
| **BNA** | Autoridade reguladora e supervisora | Emite normas; solicita registos |

## 2. Matriz RACI — Fase Conceptual (M0)

`R` executa · `A` responde (um único por linha) · `C` é consultado · `I` é informado

| Entregável | Patroc. | Dono Proc. | GP | Arquit. | Analista BPM | Compl. BC/FT | Compl. KYC | Jurídico | Operações | Retalho | Sistemas | Segurança | Auditoria |
|---|---|---|---|---|---|---|---|---|---|---|---|---|---|
| Termo de Abertura | A | C | R | C | C | C | C | C | C | C | I | I | I |
| Transcrição do Aviso n.º 1/23 | I | C | I | I | R | C | **A** | C | I | I | I | I | C |
| Matriz de requisitos `REG-*` | I | C | I | C | R | C | **A** | C | C | I | I | I | C |
| Glossário | I | **A** | I | C | R | C | C | C | C | C | I | I | I |
| Arquitectura de processos | I | **A** | C | C | R | C | C | I | C | C | I | I | I |
| AS-IS | I | **A** | C | I | R | C | C | I | C | I | I | I | C |
| TO-BE | C | **A** | C | C | R | C | C | C | C | C | C | C | C |
| Regras de negócio `BR-*` | I | A | I | C | R | C | C | C | C | C | I | I | C |
| KPIs e SLA | C | **A** | C | I | R | C | C | I | C | C | I | I | I |
| Matriz de rastreabilidade | I | C | C | C | R | C | **A** | C | I | I | I | I | C |
| Decisões de arquitectura (ADR) | I | C | C | **A** | C | C | C | C | I | I | C | C | I |
| Modelo de domínio | I | C | I | **A** | C | C | C | I | C | I | I | I | I |
| Estrutura base do projecto | I | I | C | **A** | I | I | I | I | I | I | C | C | I |

## 3. Matriz RACI — Execução (M1 a M6)

| Actividade | Dono Proc. | GP | Arquit. | Compl. BC/FT | Compl. KYC | Jurídico | Operações | Sistemas | Segurança | Auditoria |
|---|---|---|---|---|---|---|---|---|---|---|
| Validação do AS-IS na instituição | **A** | C | I | C | C | I | R | I | I | C |
| Congelamento do TO-BE | **A** | C | C | C | C | C | C | I | I | C |
| Modelação BPMN e DMN | A | I | C | C | C | I | C | I | I | I |
| Minutas contratuais e 13 temas | C | I | I | I | C | **A** | I | I | I | C |
| Desenvolvimento do backend | I | C | **A** | I | I | I | I | C | C | I |
| Desenvolvimento do mobile | I | C | **A** | I | I | I | C | C | C | I |
| Integração com core bancário | I | C | A | I | I | I | C | **A** | C | I |
| Verificação de identidade (fornecedor) | C | C | A | C | **A** | C | I | C | C | I |
| Parametrização de risco e diligência | C | I | C | **A** | C | I | I | I | I | C |
| Testes de conformidade `KPC-*` | C | C | C | C | **A** | C | C | I | C | R |
| Revisão de segurança | I | C | C | I | I | I | I | C | **A** | C |
| Passagem a produção | C | **A** | C | C | C | I | C | R | C | I |
| Auditoria de amostra mensal | C | I | I | C | C | I | C | I | I | **A** |
| Revisão mensal de desempenho | **A** | C | I | C | C | I | R | I | I | I |

## 4. Vetos

Três papéis detêm veto. Um veto não é objecção a debater: é bloqueio de passagem a produção até resolução.

| Papel | Âmbito do veto | Fundamento |
|---|---|---|
| Chefe de Compliance (KYC) | Qualquer desvio à matriz `REG-*`; qualquer indicador `KPC-*` abaixo de 100% | Aviso n.º 1/23; responsabilidade pessoal perante o BNA |
| Chefe de Compliance (BC/FT) | Parametrização de risco, diligência, PEP e sanções | Lei n.º 5/20 |
| Segurança da Informação | Tratamento de dados biométricos e pessoais, criptografia, gestão de segredos | Lei n.º 22/11; risco operacional |

## 5. Dependências externas

| Dependência | Responsável interno | Risco |
|---|---|---|
| Fornecedor de verificação de identidade (OCR, face, prova de vida) | Arquitecto + Compliance KYC | Alto — ver premissa A3 |
| Listas de PEP e de sanções | Compliance BC/FT | Médio |
| API do core bancário | Direcção de Sistemas | Alto — ver premissa A2 |
| Minutas contratuais | Direcção Jurídica | Alto — ver premissa A4 |
| Registo de instituições que aplicam diligência | Compliance BC/FT | Médio — ver premissa A6 |
| Interpretação do BNA sobre conservação tecnológica | Compliance KYC | Médio — ver premissa A7 |
