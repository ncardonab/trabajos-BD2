
-- Ejecutar primero
DROP TABLE sucursal;

-- Ejecutar segundo
CREATE TABLE sucursal(
    cods VARCHAR2(500) PRIMARY KEY,
    nrosucdependientes NUMBER(1) NOT NULL CHECK (nrosucdependientes BETWEEN 0 AND 9)
);

-- Ejecutar tercero

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

    k NUMBER(8) := 1;

    str VARCHAR2(20);
    num NUMBER;
    nom VARCHAR2(20);
    ult_ocurr NUMBER;

    BEFORE EACH ROW IS
    BEGIN
    CASE
        WHEN INSERTING THEN
            DBMS_OUTPUT.PUT_LINE('Trigger insert accionado!');
            IF :NEW.nrosucdependientes > 0 THEN
                FOR i IN 1..:NEW.nrosucdependientes LOOP
                    suc(i).cods := :NEW.cods || '.' || i;
                    suc(i).sucs := 0;
                    DBMS_OUTPUT.PUT_LINE('cod' || suc(i).cods || suc(i).sucs);
                END LOOP;
            END IF;
        WHEN UPDATING THEN
            DBMS_OUTPUT.PUT_LINE('Trigger update accionado!');

            primera := :OLD.nrosucdependientes;
            ultima := :NEW.nrosucdependientes;
            -- IF ultima > primera THEN
                primera := primera + 1;
                FOR i IN primera.. ultima LOOP
                        suc(i).cods := :NEW.cods || '.' || i;
                        suc(i).sucs := 0;
                END LOOP;
            -- ELSIF primera > ultima THEN
                -- DBMS_OUTPUT.PUT_LINE('Es mayor el anterior')
                -- DBMS_OUTPUT.PUT_LINE('Es mayor el anterior')
                -- DBMS_OUTPUT.PUT_LINE('Estado inicial del array');
                -- DBMS_OUTPUT.PUT_LINE('CODS   |   NROSUCDEPENDIENTES');
                -- DBMS_OUTPUT.PUT_LINE('-----------------------------');
                -- FOR mi_suc IN (SELECT * FROM sucursal) LOOP
                --     suc(k) := mi_suc;
                --     DBMS_OUTPUT.PUT_LINE(suc(k).cods || '        ' || suc(k).sucs);
                --     k := k + 1;
                -- END LOOP;

                -- str := suc(2).cods;
                -- DBMS_OUTPUT.PUT_LINE('Primer string: '|| str);
                -- ult_ocurr := INSTR(str, '.', -1);
                -- DBMS_OUTPUT.PUT_LINE('Indice ocurrencia ultimo punto (.): '|| ult_ocurr);

                -- FOR i IN 1..suc.COUNT LOOP
                --     str := suc(i).cods;

                --     IF instr(str, '.', -1) > 0 THEN
                --         DBMS_OUTPUT.PUT_LINE('String: '|| str);
                --         num := SUBSTR(str, ult_ocurr + 1, 1);

                --         DBMS_OUTPUT.PUT_LINE('Num: '|| num);

                --         nom := SUBSTR(str, 1, ult_ocurr - 1); 
                --         DBMS_OUTPUT.PUT_LINE(nom);
                --         IF num > :NEW.nrosucdependencias AND nom = :NEW.cods THEN
                --         -- IF num > 2 AND nom = 'Azul' THEN
                --             DELETE FROM sucursal
                --             WHERE cods = str;
                --             DBMS_OUTPUT.PUT_LINE('.          Borrado! => ' || str);
                --         END IF;
                --     END IF;
                -- END LOOP;

                -- DBMS_OUTPUT.PUT_LINE('count: ' || suc.COUNT);
                -- DBMS_OUTPUT.PUT_LINE('Estado final del array');
                -- DBMS_OUTPUT.PUT_LINE('CODS   |   NROSUCDEPENDIENTES');
                -- DBMS_OUTPUT.PUT_LINE('-----------------------------');

                -- FOR mi_suc IN (SELECT * FROM sucursal) LOOP
                --     DBMS_OUTPUT.PUT_LINE(mi_suc.cods || '        ' || mi_suc.nrosucdependientes);
                -- END LOOP;
            -- END IF;
            
    END CASE;
    END BEFORE EACH ROW;

    AFTER STATEMENT IS
    BEGIN
        i := suc.First;
        WHILE i IS NOT NULL LOOP
            INSERT INTO sucursal VALUES(suc(i).cods, suc(i).sucs);
            i := suc.NEXT(i);
        END LOOP;
    END AFTER STATEMENT;

END compuesto;
/

-- Ejecutar Cuarto
INSERT INTO sucursal VALUES('Azul', 4);
INSERT INTO sucursal VALUES('Pig', 0);
UPDATE sucursal SET nrosucdependientes = 6 WHERE cods = 'Azul';
UPDATE sucursal SET nrosucdependientes = 2 WHERE cods = 'Azul.3';
UPDATE sucursal SET nrosucdependientes = 4 WHERE cods = 'Pig';
-- UPDATE sucursal SET nrosucdependientes = 2 WHERE cods = 'Azul';

-- Ejecutar Quinto
SELECT * FROM sucursal;