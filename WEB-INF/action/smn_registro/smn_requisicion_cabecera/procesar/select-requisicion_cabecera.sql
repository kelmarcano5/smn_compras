SELECT 
	smn_compras.smn_requisicion_cabecera.*,
    smn_inventario.smn_caracteristica_almacen.cal_tipo_almacen
FROM 
	smn_compras.smn_requisicion_cabecera 
left join smn_inventario.smn_caracteristica_almacen on smn_inventario.smn_caracteristica_almacen.smn_almacen_rf = smn_compras.smn_requisicion_cabecera.smn_almacen_solicitante_rf
WHERE 
	smn_requisicion_cabecera_id = ${fld:smn_requisicion_cabecera_id}
AND
	req_estatus = 'SO' 
