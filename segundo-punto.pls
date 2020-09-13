
-- Ejecutar primero
DROP TABLE sucursal;

-- Ejecutar segundo
CREATE TABLE sucursal(
    cods VARCHAR2(500) PRIMARY KEY,
    nrosucdependientes NUMBER(1) NOT NULL CHECK (nrosucdependientes BETWEEN 0 AND 9)
);

-- Ejecutar Cuarto
INSERT INTO sucursal VALUES('Azul', 4);
INSERT INTO sucursal VALUES('Pig', 0); 

-- Ejecutar tercero
CREATE OR REPLACE TRIGGER insercion_no_suc_dependientes
AFTER INSERT ON sucursal
FOR EACH ROW
BEGIN
    DBMS_OUTPUT.PUT_LINE('Trigger accionado!');
    IF :NEW.nrosucdependientes > 0 THEN
        FOR i IN 1..:NEW.nrosucdependientes LOOP
            INSERT INTO sucursal VALUES( :NEW.cods || '.' || i, 0);
        END LOOP;
    END IF;
END;
/

CREATE OR REPLACE TRIGGER insercion_no_suc_dependientes2
AFTER UPDATE ON sucursal
FOR EACH ROW
WHEN (NEW.nrosucdependientes > OLD.nrosucdependientes)
DECLARE
    ultima NUMBER;
    primera NUMBER;
BEGIN 
    DBMS_OUTPUT.PUT_LINE('Trigger update accionado!');
    primera := :OLD.nrosucdependientes + 1;
    ultima := :NEW.nrosucdependientes;
    
    FOR i IN primera.. ultima LOOP
        -- DBMS_OUTPUT.PUT_LINE(i);
        INSERT INTO sucursal VALUES(:NEW.cods || '.' ||i, 0);
    END LOOP;
END;
/


--------------------------------------------------------------------------------

CREATE OR REPLACE TRIGGER compuesto
    FOR INSERT OR UPDATE ON sucursal
        COMPOUND TRIGGER

    --Global Declaration
    TYPE aux_type IS RECORD (
        cods sucursal.cods%TYPE,
        sucs sucursal.nrosucdependientes%TYPE
    );

    TYPE t_aux_type IS TABLE OF aux_type INDEX BY PLS_INTEGER;
    t_aux t_aux_type;

    i NUMBER;

    ultima NUMBER;
    primera NUMBER;

    BEFORE EACH ROW IS
    BEGIN
    CASE
        WHEN INSERTING THEN
            DBMS_OUTPUT.PUT_LINE('Trigger insert accionado!');
            IF :NEW.nrosucdependientes > 0 THEN
                FOR i IN 1..:NEW.nrosucdependientes LOOP
                    t_aux(i).cods := :NEW.cods || '.' || i;
                    t_aux(i).sucs := 0;
                    DBMS_OUTPUT.PUT_LINE('cod' || t_aux(i).cods || t_aux(i).sucs);
                    -- INSERT INTO sucursal VALUES( :NEW.cods || '.' || i, 0);
                END LOOP;
            END IF;
        WHEN UPDATING THEN
            DBMS_OUTPUT.PUT_LINE('Trigger update accionado!');
            primera := :OLD.nrosucdependientes + 1;
            ultima := :NEW.nrosucdependientes;
            FOR i IN primera.. ultima LOOP
                    t_aux(i).cods := :NEW.cods || '.' || i;
                    t_aux(i).sucs := 0;
                    -- INSERT INTO sucursal VALUES(:NEW.cods || '.' ||i, 0);
            END LOOP;
    END CASE;
    END BEFORE EACH ROW;

    AFTER STATEMENT IS
    BEGIN
        i := t_aux.First;
        WHILE i IS NOT NULL LOOP
            INSERT INTO sucursal VALUES(t_aux(i).cods, t_aux(i).sucs);
            i := t_aux.NEXT(i);
        END LOOP;
    END AFTER STATEMENT;

END compuesto;
/