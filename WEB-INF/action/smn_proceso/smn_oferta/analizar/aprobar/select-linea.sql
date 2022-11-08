SELECT
	smn_lineas_id
FROM
	smn_compras.smn_rel_linea_item
WHERE
	smn_item_id = ${fld:smn_item_compras_id}
UNION
SELECT
	smn_lineas_id
FROM
	smn_compras.smn_rel_linea_servicio
WHERE
	smn_servicio_id = ${fld:smn_servicios_compras_id}
UNION
SELECT
	smn_lineas_id
FROM
	smn_compras.smn_rel_linea_afijo
WHERE
	smn_afijo_id = ${fld:smn_activo_fijo_compras_id}