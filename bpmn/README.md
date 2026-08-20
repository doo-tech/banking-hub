# Modelos de Processo e Decisão

> **Notação única (regra de governação 1 da arquitectura de processos).** Todo processo é modelado em BPMN 2.0 e toda decisão em DMN. Fluxogramas informais, tabelas de passos e descrições em prosa não substituem o modelo.

## Estrutura

```
bpmn/
├── as-is/     Modelo do processo actual, com os seus defeitos (linha de base)
├── to-be/     Modelo executável — um único modelo, variantes por decisão (ADR-0004)
├── dmn/       9 decisões — toda regra parametrizável vive aqui (ADR-0003)
└── forms/     Formulários das tarefas de utilizador
```

## Modelos

| Ficheiro | Nível | Documento de referência |
|---|---|---|
| `as-is/PRC-01-abertura-conta-as-is.bpmn` | Documental | `docs/02-bpm/03-as-is-abertura-de-conta.md` |
| `to-be/PRC-01-abertura-conta.bpmn` | **Executável** | `docs/02-bpm/04-to-be-abertura-de-conta.md` |

## Decisões DMN

| Ficheiro | Decisão | Política de acerto |
|---|---|---|
| `dmn/elegibilidade.dmn` | Elegibilidade e perfil de cliente | Unique |
| `dmn/requisitos-documentais.dmn` | Checklist documental por perfil × residência × canal | Collect |
| `dmn/ubo-threshold.dmn` | Limiar de 20% e identificação de beneficiário efectivo | Collect |
| `dmn/pep-categoria.dmn` | Categoria PEP (taxonomia `PEP_I/II/III`) | First |
| `dmn/risco-bcft.dmn` | Pontuação de risco BC/FT/FPADM | Collect (sum) |
| `dmn/nivel-diligencia.dmn` | Nível de diligência e aprovações exigidas | Priority |
| `dmn/entrega-fundos.dmn` | Aceitação da entrega inicial de fundos | Unique |
| `dmn/cobertura-condicoes-gerais.dmn` | Cobertura dos 13 temas do Art. 5.º n.º 2 | Unique |
| `dmn/limites-menor.dmn` | Cartão de débito e limites de conta de menor | Unique |

## Convenções obrigatórias

| Aspecto | Convenção |
|---|---|
| Tipo de tarefa de serviço | `<modulo>.<accao>` — ex. `kyc.triarPep`, `contract.disponibilizarCondicoes` |
| Anotação de rastreabilidade | Todo elemento com origem normativa declara o seu `REG-*` na documentação do elemento |
| Variáveis de processo | `camelCase` em português; **apenas** referências, identificadores, resultados de decisão e sinalizadores. Nunca dados pessoais (ADR-0009) |
| Erro de negócio | Código `BPMN_ERRO_<CAUSA>` — ex. `BPMN_ERRO_IDENTIDADE_NAO_COMPROVADA`. Distinto de falha técnica, que é retentada |
| Temporizadores | Obrigatórios em todo estado não terminal (`INV-10`) |
| Compensação | Todo passo com efeito externo declara *compensation handler* (ADR-0010) |

## Estado — Fase 0

Os ficheiros nesta pasta são **esqueletos anotados**: declaram a estrutura, os tipos de tarefa, os códigos de erro e as anotações `REG-*` acordadas na fase conceptual. Os modelos executáveis completos são produzidos na actividade `1.8` do roteiro, após o congelamento do TO-BE (marco M2), e validados por Compliance antes de qualquer implementação.

Modelar antes de M2 seria modelar sobre um AS-IS não validado — exactamente o erro que o protocolo de validação existe para evitar.
