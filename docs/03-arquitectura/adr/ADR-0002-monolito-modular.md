# ADR-0002 — Monólito modular antes de microserviços

**Estado:** Aceite · **Data:** 2026-08-19 · **Decisor:** Arquitecto de Software

## Contexto

O domínio tem nove contextos delimitados identificados. A tentação natural é implantar cada um como serviço independente.

Os dados relevantes: volume alvo de 5 000 pedidos por dia por instituição; equipa única; ausência de exigência de escalabilidade diferenciada entre módulos; obrigação de instalar em bancos com maturidade operacional heterogénea, alguns sem plataforma de orquestração de contentores.

Microserviços resolvem problemas organizacionais e de escala que este projecto não tem, ao custo de problemas de consistência distribuída que teria de resolver.

## Decisão

Monólito modular: módulos Maven independentes, com fronteiras rigorosamente verificadas, compostos num único artefacto implantável (`bh-app`). Cada módulo tem esquema de base de dados próprio e comunica por interfaces explícitas.

## Alternativas consideradas

| Alternativa | Porque foi rejeitada |
|---|---|
| Microserviços desde o início | Consistência distribuída, observabilidade e operação complexas para um volume que um único processo serve com folga. Instalar 12 serviços num banco sem Kubernetes é obstáculo comercial |
| Monólito sem fronteiras internas | Sem verificação de dependências, os módulos degradam-se em acoplamento mútuo em poucos meses. Perde-se a opção de extrair mais tarde |
| Modularidade por convenção, sem verificação automática | Convenção não verificada é convenção violada |

## Decisão complementar: caminho de extracção

A modularidade é mantida ao nível que permite extrair um módulo para serviço independente sem reescrita:

- Esquema de base de dados próprio por módulo — sem `JOIN` entre esquemas.
- Comunicação por interface, nunca por acesso directo a entidades de outro módulo.
- Matriz de dependências verificada por ArchUnit no *build*.
- `bh-orchestration` já comunica com módulos por contrato, e não por chamada interna acoplada.

**Gatilhos que justificariam extracção:** necessidade de escalar independentemente (previsivelmente `bh-kyc`, por causa do processamento de imagem); requisito de isolamento regulatório de dados biométricos; equipas separadas com cadências de entrega distintas.

## Consequências

**Positivas** — transacções locais em vez de distribuídas; implantação de um artefacto; rastreio simples; instalável em qualquer banco com um motor de contentores; opção de extracção preservada.

**Negativas aceites** — todos os módulos escalam juntos; falha de um módulo pode afectar o processo (mitigado por isolamento de recursos e disjuntores nos adaptadores externos); disciplina de fronteiras depende de verificação automática, não de bom senso.
