SET SERVEROUTPUT on

CREATE OR REPLACE PROCEDURE CrearST IS
-- DECLARE
    n pls_integer;
    l NUMBER(8);
    r NUMBER(8);
    idx NUMBER(8);
    parent NUMBER(8);
    max_value NUMBER(8);
    value_1 NUMBER(8);
    value_2 NUMBER(8);

BEGIN
    DELETE indexsegtree;
    SELECT COUNT(*) INTO n FROM tash_sultana;
    SELECT MIN(consecutivo) INTO l FROM tash_sultana;
    SELECT MAX(consecutivo) INTO r FROM tash_sultana;

    idx := 1;
    INSERT INTO indexsegtree VALUES (idx, idx*2, idx*2 + 1, '[' || to_char(l) || ', ' || to_char(r) || ']', l, r, NULL, NULL);
    idx := idx*2;
    l := l;
    r := FLOOR((l + r)/2);
    WHILE TRUE LOOP
        IF l != r THEN
            DBMS_OUTPUT.PUT_LINE('Diferentes!');
            IF idx = 1 THEN
                DBMS_OUTPUT.PUT_LINE('Inserta el nodo raiz');
                -- INSERT INTO indexsegtree (idx, leftPtr, rightPtr, interval, l, r, maxnotionvalue, parent) VALUES (idx, idx*2, idx*2 + 1, '[' || to_char(l) || ', ' || to_char(r) || ']', l, r, null, null);
                -- idx := idx*2;
                -- l := l;
                -- r := FLOOR((l + r)/2);
            ELSIF MOD(idx, 2) = 0 THEN
                DBMS_OUTPUT.PUT_LINE('Inserta nodo: ' || idx || ', l: ' || l || ', r: ' || r);
                INSERT INTO indexsegtree VALUES (idx, idx*2, idx*2 + 1, '[' || to_char(l) || ', ' || to_char(r) || ']', l, r, null, idx / 2);
                idx := idx*2;
                l := l;
                r := FLOOR((l + r)/2);
            ELSIF MOD(idx, 2) != 0 THEN
                DBMS_OUTPUT.PUT_LINE('Inserta nodo: ' || idx || ', l: ' || l || ', r: ' || r);
                INSERT INTO indexsegtree VALUES (idx, idx*2, idx*2 + 1, '[' || to_char(l) || ', ' || to_char(r) || ']', l, r, null, (idx - 1) / 2);
                idx := idx*2;
                l := l;
                r := FLOOR((l + r)/2);
            END IF;
        ELSIF l = r THEN
            DBMS_OUTPUT.PUT_LINE('Iguales!');
            IF idx = 1 THEN
                DBMS_OUTPUT.PUT_LINE('Inserta el nodo raiz');
                SELECT valornotion INTO max_value FROM tash_sultana WHERE consecutivo = l;
                INSERT INTO indexsegtree VALUES (idx, null, null, '[' || to_char(l) || ', ' || to_char(r) || ']', l, l, max_value, null);
                
            ELSIF MOD(idx, 2) = 0 THEN
                DBMS_OUTPUT.PUT_LINE('Inserta nodo: ' || idx || ', l: ' || l || ', r: ' || r);
                SELECT valornotion INTO max_value FROM tash_sultana WHERE consecutivo = l;
                INSERT INTO indexsegtree VALUES (idx, null, null, '[' || to_char(l) || ', ' || to_char(r) || ']', l, l, max_value, idx / 2);
                SELECT l, r INTO l, r FROM indexsegtree WHERE idx = idx/2;
                idx := idx + 1;
                l := FLOOR((l + r)/2) + 1;
                r := r;
            ELSIF MOD(idx, 2) != 0 THEN
                DBMS_OUTPUT.PUT_LINE('Inserta nodo: ' || idx || ', l: ' || l || ', r: ' || r);
                SELECT valornotion INTO max_value FROM tash_sultana WHERE consecutivo = l;
                INSERT INTO indexsegtree VALUES (idx, null, null, '[' || to_char(l) || ', ' || to_char(r) || ']', l, l, max_value, (idx - 1) / 2);
                parent := (idx - 1)/2;
                
                IF MOD(parent, 2) = 0 THEN
                    SELECT l, r INTO l, r FROM indexsegtree WHERE idx = parent / 2;
                    l := FLOOR((l + r)/2) + 1;
                    r := r;
                    idx := parent + 1;
                ELSIF mod(parent, 2) != 0 THEN
                    SELECT max(maxnotionvalue) INTO max_value FROM indexsegtree WHERE idx between idx - 1 and idx;
                    UPDATE indexsegtree SET maxnotionvalue=max_value WHERE idx = parent;
                    IF parent = 1 THEN
                        EXIT;    
                    END IF;
                    idx := parent;
                END IF;

            END IF;
        END IF;
    END LOOP;
END;
/

begin
    CrearST();
end;
/