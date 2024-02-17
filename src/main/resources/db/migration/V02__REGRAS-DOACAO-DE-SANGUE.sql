INSERT INTO tipo_sanguineo (id_tipo_sanguineo,sorotipagem) VALUES (1,'A+');
INSERT INTO tipo_sanguineo (id_tipo_sanguineo,sorotipagem) VALUES (2,'A-');
INSERT INTO tipo_sanguineo (id_tipo_sanguineo,sorotipagem) VALUES (3,'B+');
INSERT INTO tipo_sanguineo (id_tipo_sanguineo,sorotipagem) VALUES (4,'B-');
INSERT INTO tipo_sanguineo (id_tipo_sanguineo,sorotipagem) VALUES (5,'AB+');
INSERT INTO tipo_sanguineo (id_tipo_sanguineo,sorotipagem) VALUES (6,'AB-');
INSERT INTO tipo_sanguineo (id_tipo_sanguineo,sorotipagem) VALUES (7,'O+');
INSERT INTO tipo_sanguineo (id_tipo_sanguineo,sorotipagem) VALUES (8,'O-');


-- A+ pode doar para AB+ e A+
INSERT INTO doacao (ID_TIPO_SANGUINEO_DOADOR, ID_TIPO_SANGUINEO_RECEPTOR) VALUES
                                                                              (1, 1), -- A+ para A+
                                                                              (1, 4); -- A+ para AB+

-- A+ pode receber de A+, A-, O+ e O-
INSERT INTO doacao (ID_TIPO_SANGUINEO_DOADOR, ID_TIPO_SANGUINEO_RECEPTOR) VALUES
                                                                              (2, 1), -- A- para A+
                                                                              (3, 1), -- B+ para A+
                                                                              (5, 1), -- AB+ para A+
                                                                              (7, 1); -- O+ para A-

-- A- pode doar para A+, A-, AB+ e AB-
INSERT INTO doacao (ID_TIPO_SANGUINEO_DOADOR, ID_TIPO_SANGUINEO_RECEPTOR) VALUES
                                                                              (1, 2), -- A+ para A-
                                                                              (2, 2), -- A- para A-
                                                                              (4, 2), -- B- para A-
                                                                              (5, 2); -- AB+ para A-

-- A- pode receber de A- e O-
INSERT INTO doacao (ID_TIPO_SANGUINEO_DOADOR, ID_TIPO_SANGUINEO_RECEPTOR) VALUES
                                                                              (2, 2), -- A- para A-
                                                                              (8, 2); -- O- para A-
-- B+ pode doar para B+ e AB+
INSERT INTO doacao (ID_TIPO_SANGUINEO_DOADOR, ID_TIPO_SANGUINEO_RECEPTOR) VALUES
                                                                              (3, 3), -- B+ para B+
                                                                              (3, 4); -- B+ para AB+

-- B+ pode receber de B+, B-, O+ e O-
INSERT INTO doacao (ID_TIPO_SANGUINEO_DOADOR, ID_TIPO_SANGUINEO_RECEPTOR) VALUES
                                                                              (3, 3), -- B+ para B+
                                                                              (4, 3), -- B- para B+
                                                                              (5, 3), -- AB+ para B+
                                                                              (7, 3); -- O+ para B+
-- B- pode doar para B-, B+, AB+ e AB-
INSERT INTO doacao (ID_TIPO_SANGUINEO_DOADOR, ID_TIPO_SANGUINEO_RECEPTOR) VALUES
                                                                              (4, 3), -- B- para B+
                                                                              (4, 4), -- B- para B-
                                                                              (4, 5), -- B- para AB+
                                                                              (4, 6); -- B- para AB-

-- B- pode receber de B- e O-
INSERT INTO doacao (ID_TIPO_SANGUINEO_DOADOR, ID_TIPO_SANGUINEO_RECEPTOR) VALUES
                                                                              (2, 2), -- A- para A-
                                                                              (8, 2); -- O- para A-

-- AB+ pode doar para AB+
INSERT INTO doacao (ID_TIPO_SANGUINEO_DOADOR, ID_TIPO_SANGUINEO_RECEPTOR) VALUES
    (5, 5); -- AB+ para AB+

-- AB+ pode receber de A+, B+, AB+, O+, A-, B-, O- e AB-
INSERT INTO doacao (ID_TIPO_SANGUINEO_DOADOR, ID_TIPO_SANGUINEO_RECEPTOR) VALUES
                                                                              (1, 5), -- A+ para AB+
                                                                              (3, 5), -- B+ para AB+
                                                                              (4, 5), -- B- para AB+
                                                                              (5, 5), -- AB+ para AB+
                                                                              (6, 5), -- AB- para AB+
                                                                              (7, 5), -- O+ para AB+
                                                                              (8, 5), -- O- para AB-
                                                                              (2, 5); -- A- para AB+

-- AB- pode doar para AB+ e AB-
INSERT INTO doacao (ID_TIPO_SANGUINEO_DOADOR, ID_TIPO_SANGUINEO_RECEPTOR) VALUES
                                                                              (5, 5), -- AB- para AB+
                                                                              (6, 6); -- AB- para AB-

-- AB- pode receber de A-, B-, O- e AB-
INSERT INTO doacao (ID_TIPO_SANGUINEO_DOADOR, ID_TIPO_SANGUINEO_RECEPTOR) VALUES
                                                                              (2, 6), -- A- para AB-
                                                                              (4, 6), -- B- para AB-
                                                                              (7, 6), -- O+ para AB-
                                                                              (8, 6), -- O- para AB-
                                                                              (6, 6); -- AB- para AB-
-- O+ pode doar para A+, B+, O+ e AB+
INSERT INTO doacao (ID_TIPO_SANGUINEO_DOADOR, ID_TIPO_SANGUINEO_RECEPTOR) VALUES
                                                                              (1, 7), -- A+ para O+
                                                                              (3, 7), -- B+ para O+
                                                                              (5, 7), -- AB+ para O+
                                                                              (7, 7); -- O+ para O+

-- O+ pode receber de O+ e O-
INSERT INTO doacao (ID_TIPO_SANGUINEO_DOADOR, ID_TIPO_SANGUINEO_RECEPTOR) VALUES
                                                                              (7, 7), -- O+ para O+
                                                                              (8, 7); -- O- para O+
-- O- pode doar para A+, B+, O+, AB+, A-, B-, O- e AB-
INSERT INTO doacao (ID_TIPO_SANGUINEO_DOADOR, ID_TIPO_SANGUINEO_RECEPTOR) VALUES
                                                                              (1, 8), -- A+ para O-
                                                                              (3, 8), -- B+ para O-
                                                                              (5, 8), -- AB+ para O-
                                                                              (7, 8), -- O+ para O-
                                                                              (2, 8), -- A- para O-
                                                                              (4, 8), -- B- para O-
                                                                              (7, 8), -- O+ para O-
                                                                              (8, 8); -- O- para O-

-- O- pode receber de O-
INSERT INTO doacao (ID_TIPO_SANGUINEO_DOADOR, ID_TIPO_SANGUINEO_RECEPTOR) VALUES
    (8, 8); -- O- para O-
