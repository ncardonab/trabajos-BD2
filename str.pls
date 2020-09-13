SET SERVEROUTPUT ON

-- DECLARE
--     nom VARCHAR2(20) := 'Azul.3.1k';
--     primero NUMBER;
-- BEGIN
--     DBMS_OUTPUT.PUT_LINE('String a analizar: ' || nom);
--     DBMS_OUTPUT.PUT_LINE('Indice de aparicion del ultimo (.): ' || INSTR(nom, '.', -1));

--     primero := SUBSTR(nom, INSTR(nom,'.', -1)+1, 1);
--     DBMS_OUTPUT.PUT_LINE('Inicio: ' || primero);

-- END;
-- /

DECLARE
    TYPE aux_type IS RECORD (
        cods sucursal.cods%TYPE,
        sucs sucursal.nrosucdependientes%TYPE
    );

    TYPE t_suc IS TABLE OF aux_type INDEX BY PLS_INTEGER;
    suc t_suc;

    k NUMBER(8) := 1;

    str VARCHAR2(20);
    num NUMBER;
    nom VARCHAR2(20);
    ult_ocurr NUMBER;
BEGIN
    DBMS_OUTPUT.PUT_LINE('Estado inicial del array');
    DBMS_OUTPUT.PUT_LINE('CODS   |   NROSUCDEPENDIENTES');
    DBMS_OUTPUT.PUT_LINE('-----------------------------');
    FOR mi_suc IN (SELECT * FROM sucursal) LOOP
        suc(k) := mi_suc;
        DBMS_OUTPUT.PUT_LINE(suc(k).cods || '        ' || suc(k).sucs);
        k := k + 1;
    END LOOP;

    str := suc(2).cods;
    DBMS_OUTPUT.PUT_LINE('Primer string: '|| str);
    ult_ocurr := INSTR(str, '.', -1);
    DBMS_OUTPUT.PUT_LINE('Indice ocurrencia ultimo punto (.): '|| ult_ocurr);

    FOR i IN 1..suc.COUNT LOOP
        str := suc(i).cods;

        IF instr(str, '.', -1) > 0 THEN
            DBMS_OUTPUT.PUT_LINE('String: '|| str);
            num := SUBSTR(str, ult_ocurr + 1, 1);

            DBMS_OUTPUT.PUT_LINE('Num: '|| num);

            nom := SUBSTR(str, 1, ult_ocurr - 1); 
            DBMS_OUTPUT.PUT_LINE(nom);
            -- IF num > :NEW.nrosucdependencias AND nom = :NEW.cods THEN
            IF num > 2 AND nom = 'Azul' THEN
                DELETE FROM sucursal
                WHERE cods = str;
                DBMS_OUTPUT.PUT_LINE('.          Borrado! => ' || str);
            END IF;
        END IF;
    END LOOP;

    DBMS_OUTPUT.PUT_LINE('count: ' || suc.COUNT);
    DBMS_OUTPUT.PUT_LINE('Estado final del array');
    DBMS_OUTPUT.PUT_LINE('CODS   |   NROSUCDEPENDIENTES');
    DBMS_OUTPUT.PUT_LINE('-----------------------------');

    FOR mi_suc IN (SELECT * FROM sucursal) LOOP
        DBMS_OUTPUT.PUT_LINE(mi_suc.cods || '        ' || mi_suc.nrosucdependientes);
    END LOOP;
END;
/

-- SUBSTR
-- INSTR