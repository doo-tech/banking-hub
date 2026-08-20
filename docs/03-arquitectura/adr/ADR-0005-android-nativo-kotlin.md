# ADR-0005 — Android nativo em Kotlin

**Estado:** Aceite · **Data:** 2026-08-19 · **Decisor:** Arquitecto de Software

## Contexto

Restrição declarada do projecto (`C7`). Esta ADR registra a fundamentação técnica, que é substantiva e não apenas de conformidade com a restrição.

O canal remoto depende de capacidades que não são acessórias ao produto — são a condição de possibilidade da abertura sem presença física ao abrigo do Art. 3.º n.º 5:

- Captura de documento com validação de qualidade em tempo real (foco, enquadramento, reflexos, corte).
- Captura facial com **prova de vida**, que exige acesso a fluxo de câmara em tempo real e, em algumas implementações, a sensores de profundidade.
- Armazenamento de material criptográfico em hardware seguro (Keystore com apoio de *StrongBox*).
- Funcionamento aceitável em condições de rede intermitente, com retomada de pedido.
- Comportamento previsível em dispositivos de gama baixa, que dominam o mercado angolano (`R-27`).

## Decisão

Android nativo em Kotlin, com Jetpack Compose, CameraX e Keystore. Uma base de código com dois modos de aplicação: cliente final e gestor de balcão.

## Alternativas consideradas

| Alternativa | Porque foi rejeitada |
|---|---|
| React Native ou Flutter | Prova de vida e captura documental de qualidade dependem de acesso profundo à câmara e, em geral, de SDK nativo do fornecedor de identidade. A ponte acrescenta latência e risco no percurso mais crítico do produto |
| Aplicação web progressiva | Sem acesso a hardware seguro para material criptográfico; captura de câmara limitada; prova de vida fiável não é praticável |
| Híbrido com módulos nativos para captura | Combina a complexidade das duas abordagens; a parte crítica ficaria nativa de qualquer modo |

## Consequências

**Positivas** — acesso pleno a câmara, sensores e hardware seguro; desempenho previsível em gama baixa; integração directa com SDK do fornecedor de identidade; comportamento offline controlado.

**Negativas aceites** — iOS exigirá base de código separada (fora do Escopo 1, decisão consciente de sequenciamento); duas equipas mobile no futuro; lógica de apresentação duplicada entre plataformas quando iOS chegar.

## Mitigação da duplicação futura

O `bh-onboarding-bff` concentra agregação, orquestração de passos e tradução de erros. A app é deliberadamente fina: apresenta, captura e submete. Quando iOS existir, a lógica a duplicar é de interface, não de processo.
