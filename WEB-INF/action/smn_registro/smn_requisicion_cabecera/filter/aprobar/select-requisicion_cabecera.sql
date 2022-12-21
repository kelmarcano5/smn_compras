SELECT 
	smn_documento_id,
	req_numero,
	req_usuario,
	smn_entidad_id,
	smn_sucursal_id,
	req_descripcion,
	smn_modulo_rf,
	req_fecha_requerido,
    smn_almacen_solicitante_rf,
	smn_compras.smn_requisicion_detalle.smn_proveedor_id
FROM 
	smn_compras.smn_requisicion_cabecera
INNER JOIN
	smn_compras.smn_requisicion_detalle
ON 
	smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id = smn_compras.smn_requisicion_detalle.smn_requisicion_cabecera_id
WHERE
	smn_compras.smn_requisicion_detalle.smn_requisicion_cabecera_id = ${fld:smn_requisicion_cabecera_id}
