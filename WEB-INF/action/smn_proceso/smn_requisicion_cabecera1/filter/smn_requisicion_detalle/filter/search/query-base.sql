SELECT
	smn_compras.smn_requisicion_detalle.smn_requisicion_detalle_id,
	smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id,
	smn_compras.smn_requisicion_cabecera.req_estatus AS req_estatus_pl0,
-- smn_compras.smn_requisicion_cabecera.req_descripcion as smn_requisicion_cabecera_id,
	smn_compras.smn_lineas.lin_codigo || ' - ' || smn_compras.smn_lineas.lin_nombre AS smn_linea_id,
	smn_base.smn_contrato_base.ctr_codigo || ' - ' || smn_base.smn_contrato_base.ctr_nombre AS smn_contrato_id,
	smn_compras.smn_requisicion_detalle.rss_cantidad,
CASE
		
		WHEN smn_compras.smn_requisicion_detalle.smn_naturaleza_id = 'IT' THEN
		'${lbl:b_item}' 
		WHEN smn_compras.smn_requisicion_detalle.smn_naturaleza_id = 'SE' THEN
		'${lbl:b_services}' 
		WHEN smn_compras.smn_requisicion_detalle.smn_naturaleza_id = 'AF' THEN
		'${lbl:b_actfij}' 
	END AS smn_naturaleza_id,
	smn_base.smn_servicios.svc_codigo || ' - ' || smn_base.smn_servicios.svc_descripcion AS smn_servicio_id,
	smn_base.smn_item.itm_codigo || ' - ' || smn_base.smn_item.itm_nombre AS smn_item_id,
	smn_base.smn_activo_fijo.acf_codigo || ' - ' || smn_base.smn_activo_fijo.acf_nombre AS smn_afijo_id,
	smn_compras.smn_requisicion_detalle.rrs_especificaciones_del_requerimiento,
	smn_compras.smn_requisicion_detalle.rrs_fecha_de_requerido,
	smn_compras.smn_requisicion_detalle.rrs_motivo_variacion,
	smn_base.smn_monedas.mon_codigo || '-' || smn_base.smn_monedas.mon_nombre AS smn_moneda_id,
	smn_compras.smn_requisicion_detalle.rrs_monto_moneda_alterna,
	smn_compras.smn_requisicion_detalle.rrs_precio_moneda_alterna,
	smn_compras.smn_requisicion_detalle.smn_proveedor_id,
	smn_compras.smn_requisicion_detalle.rrs_observaciones,
	smn_compras.smn_requisicion_detalle.rrs_porcentaje,
	smn_compras.smn_requisicion_detalle.rrs_producto_encontrado,
	smn_compras.smn_requisicion_detalle.rrs_precio,
	smn_compras.smn_requisicion_detalle.rrs_monto,
	smn_compras.smn_requisicion_detalle.rrs_fecha_registro 
FROM
	smn_compras.smn_requisicion_detalle
	LEFT OUTER JOIN smn_compras.smn_requisicion_cabecera ON smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id = smn_compras.smn_requisicion_detalle.smn_requisicion_cabecera_id
	LEFT OUTER JOIN smn_compras.smn_lineas ON smn_compras.smn_lineas.smn_lineas_id = smn_compras.smn_requisicion_detalle.smn_linea_id
	LEFT OUTER JOIN smn_base.smn_contrato_base ON smn_base.smn_contrato_base.smn_contrato_base_id = smn_compras.smn_requisicion_detalle.smn_contrato_id
	LEFT OUTER JOIN smn_base.smn_item ON smn_base.smn_item.smn_item_id = smn_compras.smn_requisicion_detalle.smn_item_id
	LEFT OUTER JOIN smn_base.smn_servicios ON smn_base.smn_servicios.smn_servicios_id = smn_compras.smn_requisicion_detalle.smn_servicio_id
	LEFT OUTER JOIN smn_base.smn_activo_fijo ON smn_base.smn_activo_fijo.smn_afijo_id = smn_compras.smn_requisicion_detalle.smn_afijo_id
	LEFT OUTER JOIN smn_base.smn_monedas ON smn_base.smn_monedas.smn_monedas_id = smn_compras.smn_requisicion_detalle.smn_moneda_id 
WHERE
	smn_requisicion_detalle_id IS NOT NULL 
	AND smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id =${fld:id2}
	${filter} 
ORDER BY
	smn_requisicion_detalle_id