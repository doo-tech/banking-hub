# App Android — Banking Hub

Android nativo em Kotlin (ADR-0005). Uma base de código, dois modos:

| Modo | Utilizador | Canal |
|---|---|---|
`cliente` | Cliente final | `REMOTO` — abertura sem presença física (Art. 3.º n.º 5) |
`gestor` | Gestor de balcão | `PRESENCIAL` |

## Porque nativo

O canal remoto depende de capacidades que **são a condição de possibilidade** da abertura sem presença física, e não funcionalidades acessórias:

- Captura de documento com validação de qualidade em tempo real — foco, enquadramento, reflexos, corte.
- Captura facial com **prova de vida**, que exige acesso ao fluxo de câmara em tempo real.
- Material criptográfico em hardware seguro (Keystore com apoio de *StrongBox*).
- Retomada de pedido em rede intermitente.
- Comportamento previsível em dispositivos de gama baixa, que dominam o mercado angolano (`R-27`).

## Estrutura

```
app/src/main/kotlin/ao/bankinghub/mobile/
├── onboarding/   Percurso do Pedido de Abertura, fase a fase (A a G do TO-BE)
├── captura/      Captura documental, facial e prova de vida (CameraX)
├── rede/         Cliente do bh-onboarding-bff, retomada, idempotência
└── ui/           Componentes Compose partilhados
```

## Princípio: a app é fina

A lógica de processo vive no `bh-onboarding-bff` e no motor de processos, **não na app**. A app apresenta, captura e submete.

Duas razões. Primeira: quando iOS existir, o que há a duplicar é interface, não regras. Segunda, mais importante: uma regra do Aviso n.º 1/23 implementada na app é uma regra que depende de o utilizador ter actualizado a aplicação — o que é inaceitável para um controlo regulatório.

## Regras de desenho do percurso

| # | Regra | Origem |
|---|---|---|
| 1 | A checklist documental é apresentada **antes** de qualquer submissão, com estimativa de prazo | Princípio "falhe cedo" do TO-BE |
| 2 | Nenhum dado é pedido duas vezes na mesma instância | Princípio "recolha uma vez" |
| 3 | Dados extraídos do documento por OCR são **confirmados** pelo cliente, não redigitados | Fase B do TO-BE |
| 4 | O cliente nunca fica bloqueado à espera de sistema externo; é notificado quando há progresso | Princípio "assíncrono por omissão" |
| 5 | O pedido é retomável — fechar a app não perde o progresso | `R-27` |
| 6 | As Condições Gerais, Particulares e a Ficha Técnica Informativa são apresentadas antes da assinatura, com confirmação de leitura registada | `REG-INF-01` |
| 7 | Existe caminho explícito para quem não sabe ou não pode assinar | `REG-KYC-04` |

## Estado — Fase 0

Estrutura de pacotes definida. A implementação arranca na Fase 2 (`2.11` do roteiro), depois de o contrato do `bh-onboarding-bff` estar fixado e de o fornecedor de verificação de identidade estar selecionado (`1.5`) — sem isso, a camada de captura seria construída contra um SDK desconhecido.
