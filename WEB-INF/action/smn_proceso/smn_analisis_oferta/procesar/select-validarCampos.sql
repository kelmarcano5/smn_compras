SELECT
	ofe_precio_ml,
	ofe_precio_ma,
	smn_condicion_financiera_rf
FROM
	smn_compras.smn_oferta
WHERE
	smn_oferta_id = ${fld:smn_oferta_id}
AND
	ofe_estatus = 'GE'