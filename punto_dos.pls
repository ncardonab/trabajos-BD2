
-- Ejecutar primero
DROP TABLE sucursal;

-- Ejecutar segundo
CREATE TABLE sucursal(
    cods VARCHAR2(500) PRIMARY KEY,
    nrosucdependientes NUMBER(1) NOT NULL CHECK (nrosucdependientes BETWEEN 0 AND 9)
);

-- Ejecutar tercero
DROP TABLE sucursal_cods;

-- Ejecutar cuarto
CREATE TABLE sucursal_cods AS (SELECT cods FROM sucursal);

-- Ejecutar quinto
CREATE OR REPLACE TRIGGER compuesto
    FOR INSERT OR UPDATE ON sucursal
        COMPOUND TRIGGER

    --Global Declaration
    TYPE aux_type IS RECORD (
        cods sucursal.cods%TYPE,
        sucs sucursal.nrosucdependientes%TYPE
    );

    -- Arreglo
    TYPE t_suc IS TABLE OF aux_type INDEX BY PLS_INTEGER;
    suc t_suc;

    i NUMBER;

    ultima NUMBER;
    primera NUMBER;

    str VARCHAR2(20);
    num NUMBER;
    nom VARCHAR2(20);
    idx_ult_ocurr NUMBER;
    ult_ocurr NUMBER;
    aux_tipo NUMBER;

    BEFORE EACH ROW IS
    BEGIN
    CASE
        WHEN INSERTING THEN
            aux_tipo := 0;
            IF :NEW.nrosucdependientes > 0 THEN
                FOR i IN 1..:NEW.nrosucdependientes LOOP
                    suc(i).cods := :NEW.cods || '.' || i;
                    suc(i).sucs := 0;
                END LOOP;
            END IF;
        WHEN UPDATING THEN

            primera := :OLD.nrosucdependientes;
            ultima := :NEW.nrosucdependientes;

            IF ultima > primera THEN
                aux_tipo := 0;
                primera := primera + 1;
                FOR i IN primera.. ultima LOOP
                        suc(i).cods := :NEW.cods || '.' || i;
                        suc(i).sucs := 0;
                END LOOP;
            ELSIF primera > ultima THEN
                aux_tipo := 1;
                FOR mi_suc IN (SELECT * FROM sucursal_cods) LOOP

                    IF :NEW.cods = substr(mi_suc.cods, 1, LENGTH(:NEW.cods)) THEN
                        idx_ult_ocurr := instr(mi_suc.cods, '.', length(:NEW.cods));
                        IF idx_ult_ocurr > 0 THEN
                            ult_ocurr := instr(mi_suc.cods, '.', -1);
                            EXIT;
                        END IF;
                    end IF;
                END LOOP;
                

                i := 1;
                FOR mi_suc IN (select * from sucursal_cods) LOOP
                    str := mi_suc.cods;

                    IF instr(str, '.', -1) > 0 THEN
                        num := SUBSTR(str, ult_ocurr + 1, 1);
                        nom := SUBSTR(str, 1, ult_ocurr - 1);
                        IF ultima < num AND :NEW.cods = nom THEN
                            suc(i).cods := str;
                            i := i + 1; 
                        END IF;
                    END IF;
                END LOOP;
            END IF;
    END CASE;
    END BEFORE EACH ROW;

    AFTER STATEMENT IS
    BEGIN
        i := suc.First;
        IF aux_tipo = 0 THEN
            WHILE i IS NOT NULL LOOP
                INSERT INTO sucursal_cods VALUES(suc(i).cods);
                INSERT INTO sucursal VALUES(suc(i).cods, suc(i).sucs);
                i := suc.NEXT(i);
            END LOOP;
        ELSIF aux_tipo = 1 THEN
            WHILE i IS NOT NULL LOOP
                DELETE FROM sucursal_cods WHERE cods = suc(i).cods;
                DELETE FROM sucursal WHERE cods = suc(i).cods;
                i := suc.NEXT(i);
            END LOOP;
        END IF;
    END AFTER STATEMENT;

END compuesto;
/

-- Ejecutar Sexto
INSERT INTO sucursal VALUES('Azul', 4);
INSERT INTO sucursal VALUES('Pig', 0);
UPDATE sucursal SET nrosucdependientes = 6 WHERE cods = 'Azul';
UPDATE sucursal SET nrosucdependientes = 5 WHERE cods = 'Azul.3';
UPDATE sucursal SET nrosucdependientes = 4 WHERE cods = 'Pig';
-- UPDATE sucursal SET nrosucdependientes = 2 WHERE cods = 'Azul';

-- Ejecutar Septimo
SELECT * FROM sucursal;