# Banking Hub

**Plataforma de onboarding bancário para Angola** — processo completo de abertura de conta, em conformidade com o **Aviso n.º 1/23 do Banco Nacional de Angola**, instalável em qualquer Instituição Financeira Bancária sediada no país.

| | |
|---|---|
| **Base regulatória** | Aviso n.º 1/23 do BNA, de 30 de Janeiro de 2023 (*Diário da República*, I Série n.º 20) |
| **Metodologia de processos** | ABPMP International — BPM CBOK |
| **Canal** | Android nativo (Kotlin) · abertura com e sem presença física |
| **Núcleo** | Java · Spring Boot · monólito modular |
| **Orquestração** | Camunda 8 — BPMN 2.0 e DMN |
| **Empacotamento** | Contentores, integralmente |
| **Estado** | **Fase 0 — Conceptualização concluída** |

---

## O problema

O processo actual de abertura de conta consome 3 a 5 horas de trabalho humano qualificado por conta, demora 3 a 30 dias úteis, perde entre 15% e 30% dos clientes pelo caminho, e **cumpre a norma em substância mas não consegue provar esse cumprimento** de forma estruturada e pesquisável.

A eficiência de ciclo é de 2 a 5% — ou seja, mais de 95% do prazo é espera pura. O rendimento acumulado ao longo das 11 etapas é de aproximadamente 31%: duas em cada três instâncias sofrem pelo menos um defeito no percurso.

Entretanto, o Aviso n.º 1/23 **permite expressamente** a abertura de conta sem presença física (Art. 3.º n.º 4 e n.º 5) — uma permissão que a generalidade da banca angolana ainda não aproveita.

## A abordagem

Não retirar controlos, mas transformá-los de actos manuais em actos **executados e evidenciados por sistema**, reservando o julgamento humano para o que exige efectivamente julgamento: risco elevado, PEP e excepções.

| Alvo | AS-IS | TO-BE |
|---|---|---|
| Tempo de ciclo (PS residente, risco baixo) | 3–5 dias úteis | **< 15 minutos** |
| Eficiência do ciclo | 2–5% | **> 60%** |
| Processamento sem intervenção humana | 0% | **> 60%** |
| Resposta a pedido de autoridade competente | 2 h – 5 dias | **< 5 minutos** |
| Cobertura de evidência de conformidade | Parcial, em papel | **100%, verificável por teste** |

---

## Como navegar a documentação

Ler nesta ordem. Cada documento pressupõe o anterior.

### 1. Fundação — porquê e para quê

| Documento | Conteúdo |
|---|---|
| [Termo de Abertura](docs/00-fundacao/01-project-charter.md) | Justificação, objectivos, escopo, premissas, marcos, **critérios de insucesso** |
| [Partes Interessadas e RACI](docs/00-fundacao/02-stakeholders-raci.md) | Papéis, matriz RACI, os três vetos |
| [Roteiro e Fases](docs/00-fundacao/03-roadmap-e-fases.md) | Ciclo de vida BPM, fases, caminho crítico |
| [Registo de Riscos](docs/00-fundacao/04-riscos-e-premissas.md) | 28 riscos, 6 críticos, com resposta e dono |

### 2. Regulatório — a norma é o contrato

| Documento | Conteúdo |
|---|---|
| [Aviso n.º 1/23 — transcrição](docs/01-regulatorio/01-bna-aviso-01-23-transcricao.md) | 18 artigos, transcritos para rastreabilidade |
| [Anexo I — ficha e documentos](docs/01-regulatorio/02-anexo-i-ficha-e-documentos.md) | Campos mínimos por perfil, taxonomia PEP, catálogo de 25 tipos documentais |
| [Matriz de requisitos `REG-*`](docs/01-regulatorio/03-matriz-requisitos-regulatorios.md) | **53 requisitos atómicos e testáveis** — a especificação de mais alto nível |

### 3. BPM — o processo

| Documento | Conteúdo |
|---|---|
| [Glossário](docs/02-bpm/01-glossario.md) | Linguagem Ubíqua; termos legais, de processo, de BPM e técnicos; **distinções que geram erro** |
| [Arquitectura de Processos](docs/02-bpm/02-arquitectura-de-processos.md) | Cadeia de valor, hierarquia, SIPOC, pistas, governação |
| [AS-IS](docs/02-bpm/03-as-is-abertura-de-conta.md) | Processo actual com os seus defeitos; 8 passagens, 7 desperdícios, 12 riscos, protocolo de validação |
| [TO-BE](docs/02-bpm/04-to-be-abertura-de-conta.md) | 7 fases, máquina de estados, **10 invariantes**, 13 variantes, mapa de controlos |
| [Regras de Negócio `BR-*`](docs/02-bpm/05-regras-de-negocio.md) | **75 regras** declarativas e falsificáveis |
| [KPIs e SLA](docs/02-bpm/06-kpis-sla-e-medicao.md) | 12 KPIs, 11 indicadores de conformidade, **contra-indicadores** |
| [Matriz de Rastreabilidade](docs/02-bpm/07-matriz-rastreabilidade.md) | Norma → regra → processo → módulo → **teste** |

### 4. Arquitectura — como se constrói

| Documento | Conteúdo |
|---|---|
| [Visão de Arquitectura](docs/03-arquitectura/01-visao-arquitectura.md) | C4, 12 princípios, requisitos não funcionais |
| [Modelo de Domínio](docs/03-arquitectura/02-modelo-de-dominio.md) | 9 contextos, agregados, eventos, invariantes |
| [Mapa de Módulos](docs/03-arquitectura/03-mapa-de-modulos.md) | 15 módulos, matriz de dependências, convenções |
| [Decisões de Arquitectura](docs/03-arquitectura/adr/) | 10 ADR, com alternativas rejeitadas e consequências aceites |

### 5. Comunicação — documentar a jornada

| Documento | Conteúdo |
|---|---|
| [Série LinkedIn](docs/04-comunicacao/linkedin/README.md) | Plano editorial, regras de escrita, posts publicados e fila de rascunhos |

---

## Estrutura do repositório

```
banking-hub/
├── docs/            Documentação — fundação, regulatório, BPM, arquitectura, comunicação
├── bpmn/            Modelos BPMN e DMN, e a especificação das 9 tabelas de decisão
├── backend/         15 módulos Maven — Java 21, Spring Boot, monólito modular
├── mobile/android/  App Kotlin — modo cliente e modo gestor de balcão
├── deploy/          docker-compose, Dockerfile, configuração de ambiente
└── tools/           trace-check — verificação de integridade da rastreabilidade
```

## Arrancar o ambiente

```bash
cp deploy/env/local.env.example deploy/.env
cd deploy && docker compose up -d
```

| Serviço | Endereço |
|---|---|
| Banking Hub | http://localhost:8080 |
| Camunda Operate / Tasklist | http://localhost:8088 |
| Keycloak | http://localhost:8081 |
| Arquivo de objectos (consola) | http://localhost:9001 |

```bash
cd backend && mvn verify        # inclui os testes de arquitectura
```

---

## Os cinco princípios que governam este projecto

1. **A norma é o contrato.** A matriz `REG-*` é a especificação de mais alto nível. Requisito sem teste falha o *build*.
2. **Conformidade por desenho, não por conferência.** Se o Art. 5.º n.º 1 exige disponibilização prévia, o sistema torna a celebração tecnicamente impossível sem evidência anterior — não confia na disciplina do operador.
3. **Regras fora do código.** Limiares regulatórios vivem em DMN versionado. Uma alteração normativa é uma publicação, não um ciclo de desenvolvimento ([ADR-0003](docs/03-arquitectura/adr/ADR-0003-regras-em-dmn.md)).
4. **Humano onde há julgamento.** Aprovação de PEP e avaliação de justificação credível de fundos permanecem humanas por decisão de desenho. Automatizar julgamento é o modo mais rápido de criar risco regulatório.
5. **BVA não é desperdício.** Em banca regulada, os controlos impostos pela norma não se eliminam — automatiza-se a sua execução e prova-se a sua conformidade.

## Estado actual e próximo passo

A **Fase 0 está concluída**: 14 entregáveis conceptuais, 53 requisitos regulatórios mapeados, 75 regras de negócio, 10 decisões de arquitectura e a estrutura base do projecto.

O próximo passo **não é escrever código**. É a **Fase 1 — Validação**, e o seu pré-requisito é a nomeação do Dono do Processo. O AS-IS neste repositório é um *AS-IS de referência*: consistente com a norma e com o padrão observável na banca angolana, mas **não substitui a observação directa** na instituição de acolhimento. Congelar o TO-BE sobre um AS-IS não validado é desenhar solução para um problema que talvez não exista.

Ver [Roteiro, Fase 1](docs/00-fundacao/03-roadmap-e-fases.md) e o [protocolo de validação](docs/02-bpm/03-as-is-abertura-de-conta.md).
