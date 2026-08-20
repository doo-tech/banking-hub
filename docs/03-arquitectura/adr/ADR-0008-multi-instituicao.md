# ADR-0008 — Multi-instituição por `tenantId` em esquema partilhado

**Estado:** Aceite · **Data:** 2026-08-19 · **Decisor:** Arquitecto de Software

## Contexto

O objectivo O7 do Termo de Abertura é entregar um produto instalável em **qualquer** IFB sediada em Angola. Isto pode significar duas coisas muito diferentes: uma instalação por banco, ou uma instalação a servir vários bancos.

A realidade do mercado angolano aponta para instalação dedicada por instituição — os bancos exigem controlo sobre os seus dados de clientes e, em geral, sobre a infra-estrutura. Mas o produto tem de ser **parametrizável** por instituição de qualquer modo: minutas contratuais, limiares de risco, catálogos de produtos e registo de instituições elegíveis diferem entre bancos.

Se a parametrização for tratada como configuração global, a segunda instalação exige alteração de código — o que falha o critério de sucesso 6.

## Decisão

`tenantId` obrigatório em todo agregado, todo esquema e toda mensagem, mesmo em instalação dedicada a uma única instituição.

Isolamento por **filtro obrigatório ao nível do repositório**, não por confiança na camada de aplicação. O `tenantId` é derivado do contexto de autenticação e nunca aceito como parâmetro de entrada da API.

Parametrização por instituição concentrada em `bh-tenant`: minutas contratuais versionadas, limiares parametrizáveis (`R` e `L` no repositório de regras), catálogos de produtos e documentos, registo de instituições que aplicam diligência, modelos de notificação.

## Alternativas consideradas

| Alternativa | Porque foi rejeitada |
|---|---|
| Sem multi-instituição — configuração global por instalação | A segunda instalação exigiria alteração de código. Falha o critério de sucesso 6 e o objectivo O7 |
| Base de dados por instituição | Complexidade operacional de migrações multiplicada; sem benefício real na instalação dedicada, que é o cenário provável |
| Esquema por instituição na mesma base de dados | Migrações por esquema tornam-se frágeis à medida que o número cresce; ganho de isolamento marginal face ao filtro obrigatório |
| `tenantId` opcional, aplicado só quando houver mais de uma instituição | Campo opcional é campo esquecido. O isolamento tem de ser estrutural desde a primeira linha |

## Consequências

**Positivas** — segunda instalação é parametrização, não desenvolvimento; limiares regulatórios ajustáveis por instituição no sentido mais restritivo, conforme o repositório de regras; suporta cenário futuro de instalação partilhada sem redesenho.

**Negativas aceites** — `tenantId` em todas as tabelas e mensagens, com sobrecarga de escrita; risco de fuga entre instituições se o filtro for omitido, mitigado por teste de isolamento na integração contínua (`R-23`); complexidade adicional numa instalação que servirá provavelmente um só banco.

## Verificação

- Teste de arquitectura: todo repositório aplica filtro por `tenantId`.
- Teste de isolamento: consulta autenticada como instituição A não devolve nenhum registo da instituição B, em todos os módulos.
- Nenhum endpoint aceita `tenantId` como parâmetro de entrada.
