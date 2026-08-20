# Post 12 — Guardar 10 anos é a parte fácil

**Gancho:** quebra de padrão sobre um requisito que todos acham simples
**Ideia única:** a segunda metade da frase é o requisito difícil, e o papel não a cumpre
**Etiquetas:** #BankingHub #Compliance #Arquitetura #BNA #Angola

---

Guardar os registos de um cliente durante 10 anos é a parte fácil.

O requisito que quase ninguém cumpre é a segunda metade da frase.

O Anexo I do Aviso 01/2023 diz que os registos têm de ser conservados no mínimo 10 anos. E que têm de estar disponíveis atempadamente para consulta pela autoridade competente.

São duas obrigações, não uma.

A primeira, o arquivo em papel cumpre. Uma pasta numa estante dura 10 anos sem esforço.

A segunda, não cumpre. Hoje, localizar um processo arquivado leva entre 2 horas e 5 dias, dependendo de onde a pasta parou e de quem estava de férias.

Guardar sem conseguir encontrar é cumprir metade do requisito. E é a metade errada, porque a que interessa é a que o supervisor testa.

Há uma frase no Anexo I que abre a porta para resolver isto. Diz que os registos podem ser conservados em documentos físicos e por qualquer processo tecnológico.

É essa frase que autoriza o arquivo digital. Sem ela, não havia conversa.

Mas passar para digital levanta logo a pergunta certa: quem garante que ninguém apaga?

A forma comum de responder é guardar os ficheiros normalmente e escrever no sistema uma regra a proibir a eliminação antes dos 10 anos.

Rejeitei essa forma.

Uma regra escrita no sistema é uma regra que um erro de configuração contorna. E o dia em que isso acontecer não é o dia em que se descobre — descobre-se dez anos depois, quando alguém pede o processo e ele não está lá.

No Banking Hub, o ficheiro é gravado com uma data até à qual não pode ser alterado nem apagado. A proibição está no armazenamento, não no programa.

Nem um administrador com todas as permissões consegue apagar.

Cada processo leva também uma impressão digital do seu conteúdo. Se alguma coisa for alterada, detecta-se.

E depois há duas subtilezas que geram erros com frequência.

A primeira: 10 anos e 15 anos não são a mesma regra. Os 10 anos são de arquivo dos registos, no Anexo I. Os 15 anos são o prazo que obriga a encerrar uma conta sem qualquer movimento, no Artigo 13.º. Artigos diferentes, obrigações diferentes.

A segunda é a que mais me surpreendeu: a contagem dos 10 anos começa no fim do processo, e o processo tem de ser arquivado mesmo quando termina em recusa.

Parece contra-intuitivo. Um pedido recusado parece não valer nada.

Mas se o BNA perguntar por que razão aquele cliente foi recusado, a resposta tem de existir.

Já tiveste de responder a um pedido de informação sobre um processo de há oito anos? Quanto tempo levou a encontrar?

#BankingHub #Compliance #Arquitetura #BNA #Angola
