SELECT
	*
FROM
	smn_compras.smn_requisicion_detalle AS requisicion_detalle
INNER JOIN
	smn_compras.smn_cotizacion AS cotizacion
	ON
	cotizacion.smn_requisicion_detalle_id = requisicion_detalle.smn_requisicion_detalle_id
WHERE
	cotizacion.smn_cotizacion_id = ${fld:smn_cotizacion_id}