DROP TABLE SUPP_REQUESTS;
CREATE TABLE SUPP_REQUESTS(
  REQUEST_ID INTEGER PRIMARY KEY,
  NOME       VARCHAR(40),
  SOBRENOME  VARCHAR(40),
  EMAIL      VARCHAR(40),
  FONE       VARCHAR(40),
  SOFTWARE   VARCHAR(40),
  SO         VARCHAR(40),
  PROBLEMA   VARCHAR(256)
);

DROP TABLE SEQ_NO;
CREATE TABLE SEQ_NO(
  PROX_NUM   INTEGER
);

INSERT INTO SEQ_NO VALUES(0);
