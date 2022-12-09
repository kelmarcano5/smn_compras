select
	smn_compras.smn_requisicion_detalle.smn_requisicion_detalle_id,
	smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id,
	smn_compras.smn_requisicion_cabecera.req_estatus as req_estatus_pl0,
	smn_compras.smn_requisicion_detalle.smn_requisicion_cabecera_id,
	smn_compras.smn_lineas.lin_codigo||' - '|| smn_compras.smn_lineas.lin_nombre as smn_linea_id,
	smn_base.smn_contrato_base.ctr_codigo||' - '||smn_base.smn_contrato_base.ctr_nombre as smn_contrato_id,
	smn_compras.smn_requisicion_detalle.rss_cantidad,
	case
		when smn_compras.smn_requisicion_detalle.smn_naturaleza_id='IT' then '${lbl:b_item}'
		when smn_compras.smn_requisicion_detalle.smn_naturaleza_id='SE' then '${lbl:b_services}'
		when smn_compras.smn_requisicion_detalle.smn_naturaleza_id='AF' then '${lbl:b_actfij}'
	end as smn_naturaleza_id,
	smn_base.smn_servicios.svc_codigo ||' - '|| smn_base.smn_servicios.svc_descripcion as smn_servicio_id,
	smn_base.smn_item.itm_codigo ||' - '|| smn_base.smn_item.itm_nombre as smn_item_id,
	smn_base.smn_activo_fijo.acf_codigo ||' - '|| smn_base.smn_activo_fijo.acf_nombre as smn_afijo_id,
	smn_compras.smn_requisicion_detalle.rrs_especificaciones_del_requerimiento,
	smn_compras.smn_requisicion_detalle.rrs_fecha_de_requerido,
	smn_compras.smn_requisicion_detalle.rrs_motivo_variacion,
	smn_base.smn_monedas.mon_codigo||'-'||smn_base.smn_monedas.mon_nombre as smn_moneda_id,
	smn_compras.smn_requisicion_detalle.rrs_monto_moneda_alterna,
	smn_compras.smn_requisicion_detalle.rrs_precio_moneda_alterna,
	smn_compras.smn_requisicion_detalle.smn_proveedor_id,
	smn_compras.smn_requisicion_detalle.rrs_observaciones,
	smn_compras.smn_requisicion_detalle.rrs_porcentaje,
	smn_compras.smn_requisicion_detalle.rrs_producto_encontrado,
	smn_compras.smn_requisicion_detalle.rrs_precio,
	smn_compras.smn_requisicion_detalle.rrs_monto,
	smn_compras.smn_requisicion_detalle.rrs_fecha_registro
	
from
	smn_compras.smn_requisicion_detalle
	left OUTER join smn_compras.smn_requisicion_cabecera on smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id = smn_compras.smn_requisicion_detalle.smn_requisicion_cabecera_id
	left outer join smn_compras.smn_lineas on smn_compras.smn_lineas.smn_lineas_id = smn_compras.smn_requisicion_detalle.smn_linea_id
	left outer join smn_base.smn_contrato_base on smn_base.smn_contrato_base.smn_contrato_base_id = smn_compras.smn_requisicion_detalle.smn_contrato_id
	left outer join smn_base.smn_item on smn_base.smn_item.smn_item_id = smn_compras.smn_requisicion_detalle.smn_item_id
	left outer join smn_base.smn_servicios on smn_base.smn_servicios.smn_servicios_id = smn_compras.smn_requisicion_detalle.smn_servicio_id
	left outer join smn_base.smn_activo_fijo on smn_base.smn_activo_fijo.smn_afijo_id = smn_compras.smn_requisicion_detalle.smn_afijo_id
	left outer join smn_base.smn_monedas on smn_base.smn_monedas.smn_monedas_id = smn_compras.smn_requisicion_detalle.smn_moneda_id
where
	smn_compras.smn_requisicion_detalle.smn_requisicion_detalle_id = ${fld:id}
