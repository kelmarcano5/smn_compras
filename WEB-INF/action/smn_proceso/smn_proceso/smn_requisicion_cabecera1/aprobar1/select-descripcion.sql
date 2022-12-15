SELECT 
	cit_descripcion_tecnica AS descripcion
FROM
	smn_inventario.smn_caracteristica_item
WHERE
	smn_item_rf = ${fld:smn_item_id}
UNION
SELECT 
	sco_nombre AS descripcion
FROM
	smn_compras.smn_servicio
WHERE
	smn_servicio_id = ${fld:smn_servicio_id}
UNION
SELECT 
	act_descripcion AS descripcion
FROM
	smn_activo_fijo.smn_activo
WHERE
	smn_activo_id = ${fld:smn_afijo_id}