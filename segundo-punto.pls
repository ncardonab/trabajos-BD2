
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
BEFORE INSERT ON sucursal
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
BEFORE UPDATE ON sucursal
FOR EACH ROW
DECLARE
    ultima_instancia NUMBER;
BEGIN
    DBMS_OUTPUT.PUT_LINE('Trigger update accionado!');

    IF :NEW.nrosucdependientes > :OLD.nrosucdependientes THEN
        -- 
        FOR i IN ultima_instancia..n LOOP
            INSERT INTO sucursal VALUES(:NEW.cods || '.' ||i, );
        END LOOP;
    END IF;
END;
/