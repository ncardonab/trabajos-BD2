SELECT DISTINCT venta.codpv, proveedor.nompv, venta.codproducto
-- ,LEAD (venta.codpv, 1) OVER (ORDER BY venta.codpv) AS proxcodpv -- Avanza una fila más y guarda el codpv en otra columna como PROXCODPV
FROM venta
INNER JOIN proveedor ON proveedor.codpv = venta.codpv
ORDER BY venta.codpv;