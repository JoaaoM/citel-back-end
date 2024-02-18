CREATE TABLE `meudb`.`endereco`
(
    ID_ENDERECO         BIGINT NOT NULL AUTO_INCREMENT,
    ESTADO              VARCHAR(2),
    CIDADE              VARCHAR(100),
    BAIRRO              VARCHAR(100),
    RUA                 VARCHAR(100),
    NUMERO              INT,
    PRIMARY KEY (ID_ENDERECO)
);
CREATE TABLE `meudb`.`telefone`
(
    ID_TELEFONE         BIGINT NOT NULL AUTO_INCREMENT,
    FIXO                VARCHAR(15),
    CELULAR             VARCHAR(15),
    PRIMARY KEY (ID_TELEFONE)
);

CREATE TABLE `meudb`.`tipo_sanguineo`
(
    id_tipo_sanguineo   BIGINT NOT NULL AUTO_INCREMENT,
    sorotipagem         VARCHAR(3),
    PRIMARY KEY (id_tipo_sanguineo)
);

CREATE TABLE `meudb`.`doacao`
(
    ID_DOACAO                   BIGINT NOT NULL AUTO_INCREMENT,
    ID_TIPO_SANGUINEO_DOADOR    BIGINT NOT NULL,
    ID_TIPO_SANGUINEO_RECEPTOR  BIGINT NOT NULL,
    PRIMARY KEY (ID_DOACAO),
    CONSTRAINT FK_DOACAO_TIPO_SANGUINEO_DOADOR FOREIGN KEY (ID_TIPO_SANGUINEO_DOADOR) REFERENCES tipo_sanguineo(id_tipo_sanguineo),
    CONSTRAINT FK_DOACAO_TIPO_SANGUINEO_RECEPTOR FOREIGN KEY (ID_TIPO_SANGUINEO_RECEPTOR) REFERENCES tipo_sanguineo(id_tipo_sanguineo)
);

CREATE TABLE `meudb`.`importacao`
(
    ID_IMPORTACAO           BIGINT NOT NULL AUTO_INCREMENT,
    NOME                    VARCHAR(100),
    DATA_IMPORTACAO         DATE,
    PRIMARY KEY (ID_IMPORTACAO)
);

CREATE TABLE `meudb`.`pessoa`
(
    ID_PESSOA                   BIGINT NOT NULL AUTO_INCREMENT,
    NOME                        VARCHAR(100),
    CPF                         VARCHAR(20),
    RG                          VARCHAR(20),
    DATA_NASC                   DATE,
    SEXO                        VARCHAR(9),
    MAE                         VARCHAR(100),
    PAI                         VARCHAR(100),
    ALTURA                      FLOAT,
    PESO                        INT,
    ID_TIPO_SANGUINEO           BIGINT NOT NULL,
    ID_ENDERECO                 BIGINT NOT NULL,
    ID_TELEFONE                 BIGINT NOT NULL,
    ID_IMPORTACAO               BIGINT NOT NULL,
    PRIMARY KEY (ID_PESSOA),
    CONSTRAINT FK_TIPO_SANGUINEO FOREIGN KEY (ID_TIPO_SANGUINEO) REFERENCES tipo_sanguineo(id_tipo_sanguineo),
    CONSTRAINT FK_ENDERECO FOREIGN KEY (ID_ENDERECO) REFERENCES endereco(ID_ENDERECO),
    CONSTRAINT FK_TELEFONE FOREIGN KEY (ID_TELEFONE) REFERENCES telefone(ID_TELEFONE),
    CONSTRAINT FK_IMPORTACAO FOREIGN KEY (ID_IMPORTACAO) REFERENCES importacao(ID_IMPORTACAO)
);



