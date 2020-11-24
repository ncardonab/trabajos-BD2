CREATE TABLE tash_sultana(
    consecutivo NUMBER(8) PRIMARY KEY,
    valornotion NUMBER(8) NOT NULL
);

INSERT INTO tash_sultana VALUES(0, 10);
INSERT INTO tash_sultana VALUES(1, 30);
INSERT INTO tash_sultana VALUES(4, 10);
INSERT INTO tash_sultana VALUES(3, 20);
INSERT INTO tash_sultana VALUES(2, 66);

DROP TABLE indexsegtree;
CREATE TABLE indexsegtree(
    idx NUMBER(8) PRIMARY KEY,
    leftPtr NUMBER(8),
    rightPtr NUMBER(8),
    -- interval VARCHAR2(20) NOT NULL,
    l NUMBER(8) NOT NULL,
    r NUMBER(8) NOT NULL,
    maxnotionvalue NUMBER(8),
    parent NUMBER(8)
);
