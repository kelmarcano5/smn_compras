SELECT
	smn_compras.smn_proveedor.prv_anticipo
FROM
	smn_compras.smn_proveedor
INNER JOIN
	smn_compras.smn_oferta ON smn_compras.smn_proveedor.smn_proveedor_id = smn_compras.smn_oferta.smn_proveedor_id
WHERE
	smn_compras.smn_oferta.smn_oferta_id = ${fld:id}
