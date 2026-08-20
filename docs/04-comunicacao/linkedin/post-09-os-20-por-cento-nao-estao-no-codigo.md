# Post 9 — Se o BNA mudar o limiar amanhã, quanto tempo levas a cumprir?

**Gancho:** pergunta que interpela o leitor e expõe risco no sistema dele
**Ideia única:** limiares regulatórios têm de ser alteráveis por quem responde por eles
**Etiquetas:** #BankingHub #Arquitetura #Compliance #BNA #Fintech

---

Se o BNA publicar amanhã um aviso a baixar o limiar dos sócios de 20% para 10%, quanto tempo leva o teu banco a estar em conformidade?

Na maioria dos sistemas, a resposta é: o tempo de um projecto de desenvolvimento.

Alguém abre um pedido. Um programador altera o número. Alguém testa. Alguém publica. E no meio disso o banco está fora de conformidade sem que ninguém tenha errado.

Olha para os números que o Aviso 01/2023 fixa:

20% — participação mínima de um sócio para ser obrigatório identificá-lo
24 meses — quando uma conta passa a dormente
14 anos — idade mínima para um menor ter cartão de débito
60 dias — aviso prévio para o banco encerrar uma conta
15 anos — inactividade que obriga a encerrar
10 anos — tempo mínimo de arquivo dos registos
13 — temas que as Condições Gerais têm de cobrir, no mínimo

Seis prazos e dois limiares. Todos podem mudar com um novo aviso.

E há prova de que mudam: o próprio Artigo 17.º deste Aviso revoga três avisos anteriores. Este corpo de normas está em movimento permanente.

Mas há um problema pior do que a demora. E foi este que decidiu a questão para mim.

O responsável de compliance não sabe ler código.

E é ele que responde perante o BNA.

Ou seja: estamos a pedir a alguém que assuma responsabilidade legal por uma regra que não tem forma de verificar. Ele confia que o programador entendeu o Aviso. O programador confia que entendeu bem. Ninguém mentiu, e a regra pode estar errada há três anos.

No Banking Hub, as regras da norma não vivem dentro do programa.

Vivem em tabelas de decisão. Condições nas colunas da esquerda, resultado na coluna da direita. Um formato padrão internacional, que se lê como uma tabela de Excel.

O responsável de compliance abre a tabela. Lê. Aprova ou corrige.

E cada decisão que o sistema toma guarda a versão da tabela que usou. Isso significa que em 2033 é possível perguntar "que regra foi aplicada a este processo em 2026?" — e ter a resposta exacta, não uma reconstituição.

A fronteira que uso é simples:

Se um novo aviso pode mudar o valor, vive na tabela.
Se mudá-lo mudaria o significado do negócio, vive no código.

O limiar de 20% vive na tabela. A regra de que a soma das participações não pode passar de 100% vive no código — porque isso não é escolha do regulador, é aritmética.

Para os engenheiros: quantas vezes encontraste um número de negócio enterrado numa constante, sem ninguém a saber de onde tinha vindo?

#BankingHub #Arquitetura #Compliance #BNA #Fintech
