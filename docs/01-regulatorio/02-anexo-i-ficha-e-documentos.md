# Anexo I do Aviso n.º 1/23 — Formulário de Abertura de Contas Bancárias

> *Diário da República*, I Série — N.º 20, de 30 de Janeiro de 2023, pp. 277–285.
> Este anexo é a **fonte normativa directa do modelo de dados da Ficha de Cliente** e do **catálogo de documentos** do Banking Hub.

---

## I. Recolha de Informações Relativa aos Clientes — princípios

1. As obrigações de identificação aplicam-se **não apenas a novos clientes**, como devem igualmente aplicar-se a **clientes já existentes**, em função da avaliação de risco de BC/FT/FPADM associada aos mesmos.
2. Os campos delimitados são os **mínimos** exigidos pela regulamentação vigente, podendo as Instituições solicitar informações adicionais que considerem relevantes para avaliação adequada do perfil de risco do cliente.
3. As Instituições devem **recolher e conservar todos os registos relativos a clientes por um período mínimo de 10 (dez) anos**, bem como garantir que os registos se encontram disponíveis atempadamente, para que a autoridade competente possa consultá-los caso considere necessário. *(Nota de pé de página 1: conforme o Aviso n.º 14/20, de 22 de Junho, sobre prevenção do branqueamento de capitais e financiamento do terrorismo.)*
4. **Em caso de dúvidas quanto à verdadeira identidade do cliente e, se aplicável, do representante legal ou do beneficiário efectivo, que não possa ser resolvida de forma satisfatória, deve a Instituição recusar a realização de quaisquer operações.**

> **Implicação de desenho:** o ponto 4 é uma regra de *hard stop*. No Banking Hub traduz-se num estado terminal `RECUSADO_IDENTIDADE_NAO_COMPROVADA`, e não num "pendente" indefinido. Ver `BR-KYC-09`.

---

## I.1 — Identificação de Pessoas Singulares (campos mínimos)

| # | Campo (texto normativo) | Campo canónico | Obrigatório | Notas |
|---|---|---|---|---|
| 1 | Nome Completo | `nomeCompleto` | Sim | |
| 2 | Assinatura | `assinatura` | Sim | Art. 4.º n.º 2; biometria se não souber/puder assinar (Art. 4.º n.º 3) |
| 3 | Data de Nascimento | `dataNascimento` | Sim | Determina menoridade (Art. 3.º n.º 2, Art. 9.º) |
| 4 | Nacionalidade | `nacionalidade` | Sim | ISO 3166-1 alpha-3 |
| 5 | Naturalidade | `naturalidade` | Sim | Local de nascimento |
| 6 | Morada completa da residência habitual (incluindo um ponto de referência) | `moradaHabitual` + `pontoReferencia` | Sim | O "ponto de referência" é campo distinto e obrigatório |
| 7 | Morada completa da residência alternativa (incluindo um ponto de referência) | `moradaAlternativa` + `pontoReferencia` | Sim (campo previsto) | Tratado como opcional-condicional no TO-BE quando não exista |
| 8 | Profissão/ocupação e entidade patronal, se aplicável | `profissao`, `entidadePatronal` | Sim / condicional | Entidade patronal "se aplicável" |
| 9 | Nome do documento de identificação utilizado | `docIdentificacao.tipo` | Sim | |
| 10 | Número identificação | `docIdentificacao.numero` | Sim | |
| 11 | Data de expiração do documento de identificação | `docIdentificacao.dataExpiracao` | Sim | Valida documento não expirado |
| 12 | Entidade emissora do documento de identificação | `docIdentificacao.entidadeEmissora` | Sim | |
| 13 | Natureza e montante do rendimento | `rendimento.natureza`, `rendimento.montante` | Sim | Input do *risk scoring* |
| 14 | Número de Identificação Fiscal | `nif` | Sim | |
| 15 | Enquadramento como Pessoa Politicamente Exposta | `pepStatus`, `pepCategoria` | Sim | Ver taxonomia abaixo |

### Campo PEP — texto normativo

> "Indivíduos nacionais ou estrangeiras que desempenham, ou desempenharam funções públicas proeminentes em Angola, ou em qualquer outro país ou jurisdição ou em qualquer organização internacional, conforme o n.º 31 do artigo 3.º da Lei n.º 05/20, de 27 de Janeiro."

### Taxonomia PEP (nota de pé de página 2 do Anexo I)

**I. Altos cargos de natureza política ou pública:**

| Cód. | Cargo |
|---|---|
| `PEP_I_01` | Chefe de Estado |
| `PEP_I_02` | Titular do Poder Executivo |
| `PEP_I_03` | Vice-Presidente |
| `PEP_I_04` | Membros do Governo, designadamente Ministros de Estado, Ministros, Secretários de Estado, Governadores, Vice-Governadores, Administradores Municipais e os Autarcas |
| `PEP_I_05` | Deputados ou membros de câmaras parlamentares |
| `PEP_I_06` | Magistrados de tribunais superiores e membros do conselho superior da magistratura judicial, cujas decisões não possam ser objecto de recurso, salvo em circunstâncias excepcionais |
| `PEP_I_07` | Membros de órgãos de administração e fiscalização do Banco Nacional de Angola |
| `PEP_I_08` | Chefes de missões diplomáticas e postos consulares |
| `PEP_I_09` | Oficiais de alta patente das Forças Armadas e da Polícia |
| `PEP_I_10` | Membros dos órgãos de administração e de fiscalização de empresas públicas e de sociedades anónimas de capitais exclusiva ou maioritariamente públicos, institutos públicos, fundações e fundos públicos, estabelecimentos públicos, qualquer que seja o modo da sua designação, incluindo os órgãos de gestão das empresas integrantes dos sectores empresariais e locais |
| `PEP_I_11` | Membros dos órgãos executivos de organizações de Direito Internacional |

**II. Membros próximos da família:**

| Cód. | Relação |
|---|---|
| `PEP_II_01` | Cônjuge ou pessoas com as quais se encontrem a viver em união de facto |
| `PEP_II_02` | Os pais, os filhos e os respectivos cônjuges ou pessoas com as quais se encontrem a viver em união de facto |

**III. Pessoas que reconhecidamente tenham com elas relações de natureza societária ou comercial:**

| Cód. | Relação |
|---|---|
| `PEP_III_01` | Qualquer pessoa singular, que seja notoriamente conhecida como proprietária conjunta com o titular do cargo de natureza política ou pública de uma pessoa colectiva, de um centro de interesses colectivos sem personalidade jurídica ou que com ele tenha relações comerciais próximas |
| `PEP_III_02` | Qualquer pessoa singular que seja proprietária do capital social ou dos direitos de voto de uma pessoa colectiva ou do património de um centro de interesses colectivos sem personalidade jurídica, que seja notoriamente conhecido como tendo como único beneficiário efectivo o titular do alto cargo de natureza política ou pública |

---

## I.1.1 — Comerciantes em nome individual (além das informações de I.1)

| Campo | Campo canónico |
|---|---|
| Denominação social completa | `denominacaoSocial` |
| Morada da sede | `moradaSede` |
| Número de Identificação Fiscal (NIF) | `nifEmpresarial` |
| Objecto Social | `objectoSocial` |
| Relatório de Sustentabilidade | `relatorioSustentabilidade` |
| Montante do Rendimento | `rendimentoMontante` |

---

## I.2 — Identificação de pessoas colectivas

> Aplica-se **também a condomínios de imóveis em regime de propriedade horizontal e patrimónios autónomos**.

| Campo | Campo canónico | Notas |
|---|---|---|
| Denominação social completa | `denominacaoSocial` | |
| Objecto social e finalidade do negócio | `objectoSocial`, `finalidadeNegocio` | |
| Endereço da sede | `enderecoSede` | |
| Número de Identificação Fiscal (NIF) | `nif` | |
| Número de matrícula do registo comercial | `matriculaRegistoComercial` | |
| Identidade dos titulares de participações no capital e nos direitos de voto da pessoa colectiva de valor **igual ou superior a 20%** | `titularesParticipacao[]` | Nota 3 do Anexo: "Este campo deverá ser completo através do preenchimento também da secção de beneficiário efectivo" |
| Identidade dos procuradores da pessoa colectiva e respectivo mandato | `procuradores[]`, `mandato` | |

> **Limiar de 20%** — é o limiar de identificação de titulares de participações fixado pelo Aviso 1/23. Codificado como `BR-UBO-01`, parametrizável por instituição apenas para valores **mais restritivos** (< 20%).

---

## I.2.1 — Organizações sem fins lucrativos (além das informações de I.2)

| Campo | Campo canónico |
|---|---|
| Localização geográfica | `localizacaoGeografica` |
| Estrutura organizacional | `estruturaOrganizacional` |
| Natureza das doações e voluntariado | `naturezaDoacoes` |
| Natureza dos fundos e dos gastos, incluindo informação dos beneficiários | `naturezaFundosGastos`, `beneficiarios[]` |

---

## I.3 — Instituições de caridade sem personalidade jurídica, órgãos de igrejas ou locais de culto

| Campo | Campo canónico |
|---|---|
| Nome completo | `nomeCompleto` |
| Morada | `morada` |
| Número do documento comprovativo da sua legalização pelas autoridades estatais | `docLegalizacaoEstatal` |
| Natureza e objecto das actividades da organização | `naturezaActividades` |
| Nomes de todos os gestores | `gestores[]` |
| Nomes ou classes de beneficiários | `beneficiarios[]` |

---

## I.4 — Beneficiário efectivo

| Campo | Campo canónico |
|---|---|
| Nome completo | `nomeCompleto` |
| Assinatura | `assinatura` |
| Data de Nascimento | `dataNascimento` |
| Nacionalidade | `nacionalidade` |
| Naturalidade | `naturalidade` |
| Morada completa da residência habitual (incluindo um ponto de referência) | `moradaHabitual`, `pontoReferencia` |
| Profissão/ocupação e entidade patronal, se aplicável | `profissao`, `entidadePatronal` |
| Nome do documento de identificação utilizado | `docIdentificacao.tipo` |
| Número de identificação | `docIdentificacao.numero` |
| Data de expiração do documento de identificação | `docIdentificacao.dataExpiracao` |
| Entidade emissora do documento de identificação | `docIdentificacao.entidadeEmissora` |
| Natureza e montante do rendimento | `rendimento.natureza`, `rendimento.montante` |
| Número de Identificação Fiscal | `nif` |

---

## II. Documentos que devem ser solicitados ao cliente

> "As informações apresentadas devem ser **verificadas mediante a apresentação dos documentos listados**, que contêm todos os registos que devem ser conservados na forma de documentos físicos e qualquer processo tecnológico nos termos a estabelecer pelo Banco Nacional da Angola e mantidos pela Instituição por um período mínimo de 10 (dez) anos."

> **Implicação de desenho:** a norma admite expressamente conservação por "qualquer processo tecnológico" — é esta a base legal do arquivo digital do Banking Hub. Ver `ADR-0006`.

### II.1 — Pessoas Singulares

| Informação a verificar | Documento exigido |
|---|---|
| Nome Completo | **Residentes** — bilhete de identidade ou cartão de residente emitido pelo órgão competente, onde conste fotografia, nome completo, data de nascimento e nacionalidade.<br>**Não residentes** — passaporte, à excepção de não residentes de nacionalidade angolana mediante apresentação de bilhete de identidade, onde conste fotografia, nome completo, data de nascimento e nacionalidade. |
| Nome Completo — **menores** sem os documentos acima, em razão da idade | **Residente** — exibição de cédula pessoal; ou<br>**Não residente** — por documento público equivalente, a apresentar por quem demonstre legitimidade enquanto seu representante legal para o estabelecimento da relação de negócio, devendo ser verificada a respectiva identidade do mesmo aquando do início da relação de negócio. |
| Morada completa da residência habitual (incluindo um ponto de referência)<br>Profissão/ocupação e entidade patronal, se aplicável | Documentos, meios ou diligências considerados **válidos, idóneos e suficientes** para a demonstração das informações prestadas. |
| Número de Identificação Fiscal | Cartão de identificação fiscal ou equivalente emitido pela Administração Geral Tributária (AGT). |

### II.2 — Pessoas Colectivas

| Informação a verificar | Documento exigido |
|---|---|
| Denominação social completa | **Pessoas colectivas residentes:** Certidão do registo comercial emitida pela Conservatória do Registo Comercial ou outro documento público comprovativo, nomeadamente o exemplar do *Diário da República* ou cópia que comprova a publicação no site oficial de entidade pública competente, contendo a publicação dos estatutos, ou certidão notarial de escritura da constituição.<br>**Pessoas colectivas não residentes:** Comprovativo do registo comercial ou outro documento público válido, devidamente certificado pelas entidades competentes do país de residência, e autenticado pela representação consular de Angola no país de origem. |
| Número de Identificação fiscal (NIF) | Cartão de Identificação Fiscal ou equivalente emitido pela Administração Geral Tributária. |
| Identidade dos titulares de participações no capital e nos direitos de voto da pessoa colectiva de valor igual ou superior a 20% | Acta da Assembleia-Geral Constituinte assim como a acta de alteração à estrutura accionista ou de sócios. |
| Identidade dos procuradores da pessoa colectiva e respectivo mandato | Declaração escrita emitida pela própria pessoa colectiva, contendo o nome dos titulares dos órgãos de gestão, procuradores e representantes. |

### II.3 — Beneficiário efectivo

| Informação a verificar | Documento exigido |
|---|---|
| Nome Completo | Documento autenticado que confirme a identidade do beneficiário efectivo:<br>• **Residentes** — bilhete de identidade ou cartão de residente emitido pelo órgão competente, onde conste fotografia, nome completo, data de nascimento e nacionalidade;<br>• **Não residentes** — passaporte, à excepção de **não residentes cambiais** de nacionalidade angolana mediante apresentação de bilhete de identidade, onde conste fotografia, nome completo, data de nascimento e nacionalidade.<br>**Menores** sem esses documentos: Residente — exibição de cédula pessoal; Não residente — por documento público equivalente, a apresentar por quem demonstre legitimidade enquanto seu representante legal, devendo ser verificada a respectiva identidade aquando do início da relação de negócio. |
| Morada completa da residência habitual (incluindo um ponto de referência)<br>Profissão/ocupação e entidade patronal, aplicável | Quaisquer documentos, meios ou diligências considerados válidos, idóneos e suficientes para a demonstração das informações prestadas. |
| Número de Identificação Fiscal | Cartão de identificação fiscal ou equivalente emitido pela Administração Geral Tributária. |
| — | Cópia do acordo fiduciário ou acordo de parceria, ou outro documento equivalente. |
| — | Acta da Assembleia-Geral Constituinte assim como a acta de alteração à estrutura accionista ou de sócios. |
| — | Outras informações fidedignas, que estejam publicamente disponíveis e a Instituição Financeira Bancária considere relevante. |

---

## Catálogo canónico de tipos documentais derivado do Anexo I

| Código | Designação | Aplica a | Verifica |
|---|---|---|---|
| `DOC_BI` | Bilhete de Identidade | PS residente, BE residente | Nome, data nasc., nacionalidade, fotografia |
| `DOC_CARTAO_RESIDENTE` | Cartão de residente | PS residente estrangeiro, BE | Nome, data nasc., nacionalidade, fotografia |
| `DOC_PASSAPORTE` | Passaporte | PS não residente, BE não residente | Nome, data nasc., nacionalidade, fotografia |
| `DOC_CEDULA_PESSOAL` | Cédula pessoal | Menor residente | Identificação do menor |
| `DOC_PUBLICO_EQUIV_MENOR` | Documento público equivalente | Menor não residente | Identificação do menor |
| `DOC_CARTAO_NIF` | Cartão de identificação fiscal (AGT) ou equivalente | PS, PC, BE, CNI | NIF |
| `DOC_COMPROV_MORADA` | Comprovativo de morada / diligência idónea | PS, BE | Morada habitual e ponto de referência |
| `DOC_COMPROV_PROFISSAO` | Comprovativo de profissão / entidade patronal | PS, BE | Profissão, entidade patronal, rendimento |
| `DOC_CERTIDAO_REG_COMERCIAL` | Certidão do registo comercial (Conservatória) | PC residente, CNI | Denominação social |
| `DOC_DR_ESTATUTOS` | Exemplar do Diário da República / publicação de estatutos | PC residente | Denominação social |
| `DOC_CERTIDAO_NOTARIAL` | Certidão notarial de escritura da constituição | PC residente | Denominação social |
| `DOC_REG_COMERCIAL_ESTRANGEIRO` | Comprovativo de registo comercial estrangeiro, certificado e autenticado consularmente | PC não residente | Denominação social |
| `DOC_ACTA_AG_CONSTITUINTE` | Acta da Assembleia-Geral Constituinte | PC, BE | Titulares ≥ 20% |
| `DOC_ACTA_ALTERACAO_SOCIETARIA` | Acta de alteração à estrutura accionista ou de sócios | PC, BE | Titulares ≥ 20% |
| `DOC_DECLARACAO_ORGAOS_GESTAO` | Declaração escrita da pessoa colectiva com órgãos de gestão, procuradores e representantes | PC | Procuradores e mandato |
| `DOC_ACORDO_FIDUCIARIO` | Cópia do acordo fiduciário / acordo de parceria ou equivalente | BE | Estrutura de controlo |
| `DOC_LEGALIZACAO_ESTATAL` | Documento comprovativo de legalização pelas autoridades estatais | Instituições de caridade sem personalidade jurídica, igrejas, locais de culto | Legalização |
| `DOC_RELATORIO_SUSTENTABILIDADE` | Relatório de Sustentabilidade | Comerciante em nome individual | Caracterização |
| `DOC_TERMO_RESPONSABILIDADE_CARTAO` | Termo de responsabilidade do representante legal (cartão de débito de menor ≥ 14 anos) | Conta de menor | Art. 9.º n.º 2 |
| `DOC_COMPROV_TRANSFERENCIA_INICIAL` | Comprovativo de transferência de entrega inicial de fundos com identificação do ordenante | Canal não presencial | Art. 4.º n.º 4 |
| `DOC_JUSTIFICACAO_FUNDOS_TERCEIRO` | Justificação credível de fundos com origem em conta de terceiro | Qualquer canal | Art. 3.º n.º 7 |
| `DOC_EVIDENCIA_CG_CP` | Evidência de disponibilização das Condições Gerais e Particulares | Todos | Art. 5.º n.º 1 |
| `DOC_FICHA_TECNICA_INFORMATIVA` | Ficha Técnica Informativa (Deveres de Informação — depósitos) | Todos | Art. 5.º n.º 3 |
| `DOC_CERTIDAO_OBITO` | Certidão de óbito | Sucessão | Art. 11.º n.º 1 |
| `DOC_HABILITACAO_HERDEIROS` | Certidão de habilitação de herdeiros ou documento equiparado | Sucessão | Art. 11.º n.º 1 |

**Legenda:** PS = pessoa singular · PC = pessoa colectiva · BE = beneficiário efectivo · CNI = comerciante em nome individual.
