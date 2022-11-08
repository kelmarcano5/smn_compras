SELECT
	smn_compras.smn_proveedor.smn_auxiliar_rf
FROM
	smn_compras.smn_oferta
INNER JOIN
	smn_compras.smn_proveedor
ON
	smn_compras.smn_oferta.smn_proveedor_id = smn_compras.smn_proveedor.smn_proveedor_id
WHERE
	smn_compras.smn_oferta.smn_oferta_id = ${fld:smn_oferta_id}