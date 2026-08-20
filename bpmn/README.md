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

| Ficheiro | Nível | Estado |
|---|---|---|
| `to-be/PRC-01-abertura-conta.bpmn` | **Executável** | 67 elementos, 71 fluxos. `c8ctl bpmn lint` sem erros nem avisos |
| `forms/*.form` | — | 14 formulários, validados contra o esquema oficial |
| `dmn/*.dmn` | **Executável** | 6 de 9 decisões, `dmnlint` limpo, FEEL verificado |
| `as-is/` | Documental | Por produzir — ver issue de diagramas descritivos |

## Decisões DMN

Ver `dmn/README-tabelas.md` para o estado, as políticas de acerto e as duas restrições da plataforma que condicionaram o desenho.

## Convenções obrigatórias

| Aspecto | Convenção |
|---|---|
| Tipo de tarefa de serviço | `<modulo>.<accao>` — ex. `kyc.triarPep`, `contract.disponibilizarCondicoes` |
| Anotação de rastreabilidade | Todo elemento com origem normativa declara o seu `REG-*` na documentação do elemento |
| Variáveis de processo | `camelCase` em português; **apenas** referências, identificadores, resultados de decisão e sinalizadores. Nunca dados pessoais (ADR-0009) |
| Erro de negócio | Código `BPMN_ERRO_<CAUSA>` — ex. `BPMN_ERRO_IDENTIDADE_NAO_COMPROVADA`. Distinto de falha técnica, que é retentada |
| Temporizadores | Obrigatórios em todo estado não terminal (`INV-10`) |
| Compensação | Todo passo com efeito externo declara *compensation handler* (ADR-0010) |

## Estado

O modelo executável e as seis decisões que ele invoca estão implementados e verificados estruturalmente.

**O que está verificado:** estrutura BPMN (linter sem erros nem avisos), estrutura DMN (`dmnlint` limpo nos seis ficheiros), esquema dos formulários (validador oficial), correspondência nos dois sentidos entre os `formId` do BPMN e os ficheiros em disco, e as expressões FEEL críticas avaliadas com `c8ctl feel evaluate`.

**O que não está verificado:** execução real. Nenhuma instância correu num motor. A validação comportamental — testes de processo cobrindo todos os ramos e os invariantes do desenho — é o passo seguinte, e é o único que prova que as regras certas disparam e não apenas que nada falha.

**O que falta ao modelo:** os subprocessos condicionais de pessoa colectiva (titulares de participação e beneficiário efectivo) e de conta de menor, com as três decisões correspondentes. O percurso implementado é o da primeira fatia: pessoa singular, canal remoto, com todos os guardas regulatórios e os caminhos de recusa.
