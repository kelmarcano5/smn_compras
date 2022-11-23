SELECT
	rss_cantidad
FROM
	smn_compras.smn_requisicion_detalle
INNER JOIN
	smn_compras.smn_cotizacion
ON
	smn_compras.smn_requisicion_detalle.smn_requisicion_detalle_id = smn_compras.smn_cotizacion.smn_requisicion_detalle_id
INNER JOIN
	smn_compras.smn_oferta
ON
	smn_compras.smn_cotizacion.smn_cotizacion_id = smn_compras.smn_oferta.smn_cotizacion_id
WHERE
	smn_compras.smn_oferta.smn_oferta_id = ${fld:smn_oferta_id}