SELECT
	*
FROM
	smn_compras.smn_oferta
WHERE
	ofe_estatus IN ('AP')
ORDER BY 
	smn_proveedor_id