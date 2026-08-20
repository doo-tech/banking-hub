# ADR-0004 — Um modelo BPMN com variantes por decisão

**Estado:** Aceite · **Data:** 2026-08-19 · **Decisor:** Arquitecto + Dono do Processo

## Contexto

O Anexo I define sete perfis de cliente com conjuntos distintos de campos e documentos. O Aviso define três canais de abertura. Existem variantes por residência, por menoridade, por qualificação PEP e por capacidade de assinar.

Combinatoriamente, isto excede trinta variantes de percurso. Um diagrama BPMN por variante produz trinta diagramas a manter em sincronia — e uma alteração normativa transversal, como uma mudança na retenção documental, obrigaria a trinta edições coerentes. É insustentável.

## Decisão

**Um único modelo BPMN** para `PRC-01`, no qual as variantes se expressam por:

1. **Decisões DMN** que determinam campos, documentos e limiares aplicáveis.
2. **Subprocessos condicionais** activados por variável de processo (representantes legais, titulares ≥ 20%, beneficiário efectivo).
3. **Subprocessos multi-instância** para colecções (documentos da checklist, partes a triar).
4. ***Gateways* exclusivos** para caminhos genuinamente distintos: assinatura manuscrita ou biométrica, diligência normal ou reforçada, ordenante coincidente ou não.

Uma variante nova é uma **linha de tabela DMN**, não um diagrama novo.

## Alternativas consideradas

| Alternativa | Porque foi rejeitada |
|---|---|
| Um modelo por perfil de cliente | Trinta diagramas em sincronia; alteração transversal multiplica-se por trinta; divergência inevitável |
| Um modelo por canal | Contraria `REG-ABR-04`, que trata os canais como modos do mesmo processo, não processos distintos |
| Modelo genérico com um único *script task* que decide tudo | Devolve a lógica ao código e anula a inspeccionabilidade que justifica usar BPMN |

## Consequências

**Positivas** — uma alteração normativa transversal é uma edição; o processo é compreensível como um todo por Compliance; variantes são dados, não estrutura; cobertura de teste de processo é mensurável sobre um só modelo.

**Negativas aceites** — o modelo é maior e exige leitura atenta; subprocessos condicionais têm de estar bem nomeados para o diagrama permanecer legível; a complexidade desloca-se para as tabelas DMN, onde é mais fácil de gerir mas ainda existe.

## Salvaguarda

Se o modelo único crescer até deixar de ser legível numa página A3, a resposta correcta é extrair subprocessos reutilizáveis com fronteiras claras — **não** duplicar o modelo por variante.
