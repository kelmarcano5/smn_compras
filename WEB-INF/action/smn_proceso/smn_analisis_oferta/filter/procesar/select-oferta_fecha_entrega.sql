SELECT
	smn_oferta_f_entrega.*
FROM
	smn_compras.smn_oferta_f_entrega
WHERE
	smn_oferta_id = ${fld:smn_oferta_id}