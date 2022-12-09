SELECT 
	smn_requisicion_detalle_id, 
	rrs_fecha_de_requerido,
	smn_item_id,
	smn_servicio_id,
	smn_afijo_id,
	CASE
		WHEN smn_naturaleza_id = 'IT' THEN smn_item_id
		WHEN smn_naturaleza_id = 'SE' THEN smn_servicio_id
		ELSE smn_afijo_id
	END AS producto
FROM 
	smn_compras.smn_requisicion_detalle 
WHERE 
	smn_compras.smn_requisicion_detalle.smn_requisicion_cabecera_id = ${fld:smn_requisicion_cabecera_id}
