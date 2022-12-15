SELECT
	ofe_aplica_anticipo,
	ofe_porcentaje_anticipo,
	ofe_monto_anticipo_ml,
	ofe_moneda_id,
	ofe_tasa,
	ofe_monto_anticipo_ma
FROM
	smn_compras.smn_oferta
WHERE
	smn_oferta_id = ${fld:smn_oferta_id}