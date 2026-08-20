# ADR-0009 — Dados pessoais fora das variáveis de processo

**Estado:** Aceite · **Data:** 2026-08-19 · **Decisor:** Arquitecto + Segurança da Informação

## Contexto

O motor de processos persiste variáveis de instância e mantém histórico. É a propriedade que sustenta `REG-RET-02` e a inspeccionabilidade do processo.

É também um problema de privacidade, se usada sem cuidado. Colocar a Ficha de Cliente completa numa variável de processo significa: dados de identificação, morada, rendimento, qualificação PEP e potencialmente referências a material biométrico replicados no histórico do motor, visíveis na consola de operação, exportados em diagnósticos, e sujeitos a uma política de retenção que não é a do arquivo.

A Lei n.º 22/11 impõe minimização. O risco `R-22` classifica esta exposição com exposição 15.

## Decisão

**Variáveis de processo transportam apenas identificadores, referências, resultados de decisão e sinalizadores de controlo de fluxo.** Nunca dados pessoais.

| Permitido em variável de processo | Proibido em variável de processo |
|---|---|
| `pedidoId`, `tenantId`, `fichaClienteRef` | `nomeCompleto`, `nif`, `dataNascimento`, `morada` |
| `perfilCliente`, `canalOrigem` | Conteúdo ou imagem de documento |
| `nivelRisco`, `nivelDiligencia`, `estadoPep` | Nome ou cargo concreto do PEP |
| `checklistCompleta` (booleano), contagens | Ficha de Cliente serializada |
| `versaoProcesso`, `versaoRegras` | Material ou vectores biométricos |
| `ordenanteCoincideComTitular` (booleano) | Nome do ordenante |

Os dados residem nos módulos de domínio, sob o controlo de acesso e a política de retenção desses módulos. O processo coordena por referência.

Nota: `estadoPep` e `nivelRisco` são resultados de decisão necessários ao encaminhamento do fluxo e não identificam a pessoa por si; a identificação concreta permanece em `bh-kyc`.

## Alternativas consideradas

| Alternativa | Porque foi rejeitada |
|---|---|
| Dados completos em variáveis, com controlo de acesso ao motor | Replica dados sensíveis num sistema cuja finalidade não é guardá-los, com política de retenção distinta da do arquivo. Contraria a minimização |
| Dados cifrados em variáveis de processo | O motor não pode avaliar expressões sobre dados que não consegue ler; a cifra torna a variável inútil para o fluxo e mantém o dado onde não devia estar |
| Sem regra explícita, decidido caso a caso | Sem regra, a conveniência ganha. Dados pessoais aparecem em variáveis na primeira semana de pressa |

## Consequências

**Positivas** — superfície de exposição reduzida; consola de operação utilizável por Operações sem acesso a dados pessoais; política de retenção de dados pessoais concentrada nos módulos e no arquivo; redacção em registos e diagnósticos torna-se simples, porque não há o que redigir.

**Negativas aceites** — depuração exige consultar o módulo para saber a que pessoa corresponde um `pedidoId`; mais chamadas entre orquestração e módulos; disciplina que tem de ser verificada, não presumida.

## Verificação

- Teste automatizado que inspecciona o conjunto de variáveis de todas as instâncias de teste e falha ao encontrar chave da lista proibida.
- Revisão obrigatória de Segurança em qualquer nova variável de processo declarada.
- Redacção automática em registos de aplicação, como segunda linha de defesa.
