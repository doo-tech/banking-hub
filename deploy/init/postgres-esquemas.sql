-- Banking Hub — um esquema por modulo.
--
-- Regra estrutural do mapa de modulos: cada modulo tem esquema proprio e nao
-- existe JOIN entre esquemas. E esta separacao que preserva a opcao de extrair
-- um modulo para servico independente sem reescrita (ADR-0002).
--
-- As tabelas sao criadas pelas migracoes Flyway de cada modulo, nao aqui.

CREATE SCHEMA IF NOT EXISTS bh_tenant;
CREATE SCHEMA IF NOT EXISTS bh_customer;
CREATE SCHEMA IF NOT EXISTS bh_document;
CREATE SCHEMA IF NOT EXISTS bh_kyc;
CREATE SCHEMA IF NOT EXISTS bh_contract;
CREATE SCHEMA IF NOT EXISTS bh_funding;
CREATE SCHEMA IF NOT EXISTS bh_account;
CREATE SCHEMA IF NOT EXISTS bh_archive;
CREATE SCHEMA IF NOT EXISTS bh_audit;
CREATE SCHEMA IF NOT EXISTS bh_notification;
CREATE SCHEMA IF NOT EXISTS bh_orchestration;

COMMENT ON SCHEMA bh_customer   IS 'Ficha de Cliente, partes, elegibilidade — REG-KYC-01/02';
COMMENT ON SCHEMA bh_document   IS 'Catalogo, checklist, verificacao documental — REG-KYC-10/11/12';
COMMENT ON SCHEMA bh_kyc        IS 'Identidade, PEP, sancoes, risco, BE — REG-KYC-05..09';
COMMENT ON SCHEMA bh_contract   IS 'CG, CP, FTI, assinatura, celebracao — REG-INF-01..04, REG-ABR-07';
COMMENT ON SCHEMA bh_funding    IS 'Entrega inicial de fundos — REG-FUN-01..03';
COMMENT ON SCHEMA bh_account    IS 'Conta, IBAN, activacao, menores — REG-ABR-03/08, REG-MEN-01..04';
COMMENT ON SCHEMA bh_archive    IS 'Dossie, evidencias, retencao de 10 anos — REG-RET-01..03';
COMMENT ON SCHEMA bh_audit      IS 'Cadeia de eventos encadeada por hash — REG-RET-02';
