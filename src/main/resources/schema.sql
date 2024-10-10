DROP TABLE IF EXISTS notes CASCADE;
DROP TABLE IF EXISTS debtors CASCADE;
DROP SEQUENCE IF EXISTS debtors_id_seq;
DROP SEQUENCE IF EXISTS notes_id_seq;
DROP SEQUENCE IF EXISTS nip_seq;
DROP SEQUENCE IF EXISTS receipt_seq;

-- CREATE SEQUENCE nip_seq START 1000000;

CREATE SEQUENCE receipt_seq START 1000001;

CREATE TABLE debtors (
     id BIGSERIAL PRIMARY KEY,
     name varchar(255),
     email varchar(255),
     nip varchar(20)
);

CREATE TABLE notes (
    id BIGSERIAL PRIMARY KEY,
    amount INT NOT NULL,
    receipt varchar(20) NOT NULL DEFAULT ('NSP' || nextval('receipt_seq')),
    debtor_id BIGINT,
    status SMALLINT NOT NULL DEFAULT 0,
    CONSTRAINT fk_debtor FOREIGN KEY (debtor_id) REFERENCES debtors(id) ON DELETE CASCADE,
    CONSTRAINT chk_status CHECK (status IN (-1, 0, 1, 2))
);
