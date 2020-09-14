
-- Ejecutar primero
DROP TABLE sucursal;

-- Ejecutar segundo
CREATE TABLE sucursal(
    cods VARCHAR2(500) PRIMARY KEY,
    nrosucdependientes NUMBER(1) NOT NULL CHECK (nrosucdependientes BETWEEN 0 AND 9)
);

CREATE TABLE sucursal_cods AS (SELECT cods FROM sucursal);

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
    idx_ult_ocurr NUMBER;
    ult_ocurr NUMBER;
    aux_tipo NUMBER;

    BEFORE EACH ROW IS
    BEGIN
    CASE
        WHEN INSERTING THEN
            DBMS_OUTPUT.PUT_LINE('Trigger insert accionado!');
            aux_tipo := 0;
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

            DBMS_OUTPUT.PUT_LINE('Primera: ' || primera || ', Ultima: ' || ultima);


            IF ultima > primera THEN
                aux_tipo := 0;
                DBMS_OUTPUT.PUT_LINE('ultima es mayor que primera');
                primera := primera + 1;
                FOR i IN primera.. ultima LOOP
                        suc(i).cods := :NEW.cods || '.' || i;
                        suc(i).sucs := 0;
                END LOOP;
            ELSIF primera > ultima THEN
                aux_tipo := 1;
                DBMS_OUTPUT.PUT_LINE('DELETEeeeeeee ' || aux_tipo);
                DBMS_OUTPUT.PUT_LINE('primera es mayor que ultima');
                DBMS_OUTPUT.PUT_LINE('Estado inicial del array');
                DBMS_OUTPUT.PUT_LINE('CODS   |   NROSUCDEPENDIENTES');
                DBMS_OUTPUT.PUT_LINE('-----------------------------');
                -- Mapear la tabla en el arreglo suc (sucursal)
                -- FOR mi_suc IN (SELECT * FROM sucursal) LOOP
                --     suc(k) := mi_suc;
                --     DBMS_OUTPUT.PUT_LINE(suc(k).cods || '        ' || suc(k).sucs);
                --     k := k + 1;
                -- END LOOP;
                -- Para seleccionar indice de la ultima ocurrencia
                FOR mi_suc IN (SELECT * FROM sucursal_cods) LOOP

                    IF :NEW.cods = substr(mi_suc.cods, 1, LENGTH(:NEW.cods)) THEN
                        DBMS_OUTPUT.PUT_LINE(':NEW.cods coincide con el match '|| substr(mi_suc.cods, 1, LENGTH(:NEW.cods)) || ' = '|| :NEW.cods);
                        idx_ult_ocurr := instr(mi_suc.cods, '.', length(:NEW.cods));
                        DBMS_OUTPUT.PUT_LINE(idx_ult_ocurr);
                        IF idx_ult_ocurr > 0 THEN
                            DBMS_OUTPUT.PUT_LINE('el indice es mayor que 0: ' || idx_ult_ocurr);
                            ult_ocurr := instr(mi_suc.cods, '.', -1);
                            EXIT;
                        END IF;
                    end IF;
                END LOOP;
                
                DBMS_OUTPUT.PUT_LINE('Indice ocurrencia ultimo punto (.): '|| ult_ocurr);

                i := 1;
                FOR mi_suc IN (select * from sucursal_cods) LOOP
                    str := mi_suc.cods;

                    IF instr(str, '.', -1) > 0 THEN
                        DBMS_OUTPUT.PUT_LINE('String: '|| str);
                        num := SUBSTR(str, ult_ocurr + 1, 1);

                        DBMS_OUTPUT.PUT_LINE('Num: '|| num);

                        nom := SUBSTR(str, 1, ult_ocurr - 1);
                        DBMS_OUTPUT.PUT_LINE(nom);
                        IF ultima < num AND :NEW.cods = nom THEN
                            suc(i).cods := str;
                            DBMS_OUTPUT.PUT_LINE('. BORRADO! => ' || str);
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
        DBMS_OUTPUT.PUT_LINE('VARIABLE AUUUUUUX ' || aux_tipo);
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
            DBMS_OUTPUT.PUT_LINE('se esta borrando');
        END IF;
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