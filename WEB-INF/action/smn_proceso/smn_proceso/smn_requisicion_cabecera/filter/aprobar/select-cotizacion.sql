SELECT
	smn_documento_id,
	cot_numero_documento
FROM
	smn_compras.smn_cotizacion
WHERE
	smn_cotizacion_id = ${fld:smn_cotizacion_id}