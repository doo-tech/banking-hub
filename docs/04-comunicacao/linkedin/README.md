# Série LinkedIn — Jornada do Banking Hub

Documentação pública da construção do projecto, passo a passo.

## Audiência

Mistura deliberada: engenheiros de software, analistas de negócio, profissionais de banca, e pessoas sem qualquer experiência técnica.

**Consequência editorial:** todo termo técnico ou jurídico é explicado na primeira vez que aparece, na própria frase. Nunca se assume que o leitor sabe o que é BPMN, DMN, PEP, KYC ou beneficiário efectivo. Se um post só faz sentido para engenheiros, está mal escrito.

## Regras de escrita

O padrão obrigatório da série está em **[REGRAS-DE-ESCRITA.md](REGRAS-DE-ESCRITA.md)**.

O ponto mais importante, e o que mais facilmente se perde: **o post fala do projecto e do problema, não do trabalho de documentação**. O leitor não quer saber o que foi produzido — quer saber o que aprendeu ao ler. Se uma frase só faz sentido para quem tem acesso ao repositório, sai.

## Publicados

| # | Tema | Ligação |
|---|---|---|
| 1 | Arranque do projecto — Java, Camunda e o Aviso 01/2023 | [lnkd.in/p/dYFPyrXA](https://lnkd.in/p/dYFPyrXA) *(nota: esta é a ligação indicada como sendo a do primeiro post)* |
| 2 | Porque é que ainda se exige presença física, se a lei já permite abertura remota desde 2023 | [lnkd.in/p/dG9hKbhJ](https://lnkd.in/p/dG9hKbhJ) |

## Em fila

Prontos a publicar, na ordem sugerida. Cada ficheiro contém apenas o texto do post, pronto a copiar.

| # | Ficheiro | Tema | Ângulo |
|---|---|---|---|
| 3 | [A norma que o computador não lê](post-03-li-o-aviso-inteiro.md) | O Aviso circula como imagem, e o Anexo I é a verdadeira especificação | Quebra de padrão |
| 4 | [Cinco palavras que não são sinónimos](post-04-comecei-por-um-dicionario.md) | Confundir os papéis do cliente abre contas fora do processo | Consequência |
| 5 | [3 a 30 dias. O trabalho leva 4 horas.](post-05-o-processo-actual-em-numeros.md) | A espera, as 8 passagens de mão, e a conta dos 31% | Números em tensão |
| 6 | [Metade do que atrasa não pode ser cortado](post-06-nem-tudo-que-demora-e-desperdicio.md) | O alvo da automação não é o controlo, é o desperdício em volta | Contrarian |
| 7 | [O cliente descobre ao dia 12 que falta um documento](post-07-de-tres-dias-para-quinze-minutos.md) | Falhar cedo é a maior alavanca de tempo | Consequência |
| 8 | [A palavra do Artigo 5.º que quase nenhum banco prova](post-08-tornar-o-incumprimento-impossivel.md) | Conformidade por desenho em vez de por conferência | Lacuna de curiosidade |
| 9 | [Se o BNA mudar o limiar amanhã, quanto tempo levas?](post-09-os-20-por-cento-nao-estao-no-codigo.md) | Quem responde pela regra tem de a poder ler e alterar | Pergunta que interpela |
| 10 | [O Aviso aponta para o parágrafo errado](post-10-encontrei-uma-remissao-que-nao-fecha.md) | Ambiguidade normativa obriga a decidir e a registar | Rigor |
| 11 | [A lei usa a palavra "credível". Não sei programar isso.](post-11-o-que-nao-automatizei.md) | Onde a norma interpreta, há julgamento humano | Quebra de padrão |
| 12 | [Guardar 10 anos é a parte fácil](post-12-dez-anos-de-arquivo.md) | A segunda metade da frase é o requisito difícil | Quebra de padrão |
| 13 | [Todo indicador pode ser falsificado](post-13-metricas-que-mentem.md) | Cada métrica precisa de um contra-indicador | Gestão |
| 14 | [Tenho o processo desenhado. E não vou escrever código.](post-14-o-proximo-passo-nao-e-codigo.md) | Sem dono do processo, não há transformação | Quebra de padrão |

### Ganchos, para escolher a ordem de publicação

Todos abaixo de 140 caracteres — o corte do LinkedIn no telemóvel. Ficam visíveis por inteiro sem "ver mais".

| # | Primeira linha |
|---|---|
| 3 | *A norma que regula a abertura de todas as contas bancárias em Angola não pode ser lida por um computador.* |
| 4 | *Imagina uma conta aberta no nome de uma criança de 8 anos.* |
| 5 | *Abrir uma conta bancária em Angola leva entre 3 e 30 dias.* |
| 6 | *Metade do que atrasa a abertura de uma conta bancária não pode ser eliminado.* |
| 7 | *O maior desperdício na abertura de uma conta não é a burocracia.* |
| 8 | *O Artigo 5.º do Aviso 01/2023 tem uma palavra que quase nenhum banco em Angola consegue provar.* |
| 9 | *Se o BNA publicar amanhã um aviso a baixar o limiar dos sócios de 20% para 10%, quanto tempo leva o teu banco a estar em conformidade?* |
| 10 | *O Aviso 01/2023 aponta para o parágrafo errado. Descobri ao tentar implementá-lo.* |
| 11 | *O Aviso 01/2023 usa a palavra "credível".* |
| 12 | *Guardar os registos de um cliente durante 10 anos é a parte fácil.* |
| 13 | *Todo indicador que definires pode ser cumprido de uma forma que piora o resultado real.* |
| 14 | *Tenho o processo de abertura de conta inteiro desenhado.* |

## Nota de coerência a resolver

O post #1 anuncia fluxos de **registo (onboarding) e de pedido de crédito**. O escopo actualmente definido no Termo de Abertura cobre **apenas a abertura de conta**; crédito está explicitamente fora de escopo.

Duas saídas possíveis, ambas legítimas:

1. Manter o escopo e, num post futuro, explicar a decisão de sequenciamento — "comecei pelo onboarding porque sem cliente identificado não há crédito". Isto transforma uma incoerência em conteúdo.
2. Alargar o Termo de Abertura para incluir crédito num Escopo 4.

**Recomendação:** opção 1. Reduzir escopo publicamente e explicar porquê é sinal de maturidade de gestão, não de recuo.

## Como manter a série

A cada actualização relevante do projecto, gerar o rascunho do post seguinte a partir do que foi efectivamente feito — nunca do que se planeia fazer. Um post por entrega verificável.

Perguntas que o rascunho tem de responder antes de ser publicável:

1. O que mudou no repositório desde o último post?
2. Que decisão foi tomada, e que alternativa foi rejeitada?
3. O que é que isto ensina a alguém que não trabalha nisto?
4. Que número concreto sustenta a afirmação?
