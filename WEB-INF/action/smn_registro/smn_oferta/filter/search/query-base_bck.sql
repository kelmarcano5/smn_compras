select
	smn_compras.smn_oferta.smn_oferta_id,
	smn_compras.smn_cotizacion.smn_cotizacion_id,
	smn_compras.smn_requisicion_detalle.smn_requisicion_detalle_id,
	smn_compras.smn_requisicion_cabecera.req_cabecera_version,
	smn_compras.smn_cotizacion.cot_numero_documento as numero_cotizacion,
	smn_compras.smn_oferta.ofe_numero_documento as numero_oferta,
	smn_base.smn_auxiliar.aux_codigo || ' - ' || smn_base.smn_auxiliar.aux_descripcion as smn_proveedor_id,
	smn_base.smn_item.itm_codigo ||' - '|| smn_base.smn_item.itm_nombre as smn_item_id,
	smn_compras.smn_oferta.ofe_precio_ml as precio,
	smn_compras.smn_oferta.ofe_cantidad as cantidad,
	smn_compras.smn_oferta.ofe_monto_ml as monto_total_oferta,
	case 
		when smn_compras.smn_oferta.ofe_estatus='GE' then '${lbl:b_generated}'
		when smn_compras.smn_oferta.ofe_estatus='RE' then '${lbl:b_received}'
		when smn_compras.smn_oferta.ofe_estatus='AN' then '${lbl:b_analyzed}'
		when smn_compras.smn_oferta.ofe_estatus='AP' then '${lbl:b_aprobated}'
		when smn_compras.smn_oferta.ofe_estatus='RZ' then '${lbl:b_rejected}'
	end as ofe_estatus,
	smn_compras.smn_oferta.ofe_fecha_registro
	from
	smn_compras.smn_oferta
	inner join smn_compras.smn_cotizacion on smn_compras.smn_cotizacion.smn_cotizacion_id = smn_compras.smn_oferta.smn_cotizacion_id
	inner join smn_base.smn_item on smn_base.smn_item.smn_item_id = smn_compras.smn_oferta.smn_item_compras_id
	inner join smn_compras.smn_documentos on smn_compras.smn_documentos.smn_documentos_id = smn_compras.smn_oferta.smn_documento_id
	inner join smn_compras.smn_proveedor on smn_compras.smn_proveedor.smn_proveedor_id = smn_compras.smn_oferta.smn_proveedor_id
	inner join smn_base.smn_auxiliar on smn_base.smn_auxiliar.smn_auxiliar_id = smn_compras.smn_proveedor.smn_auxiliar_rf
	inner join smn_compras.smn_requisicion_detalle on smn_compras.smn_requisicion_detalle.smn_requisicion_detalle_id = smn_compras.smn_cotizacion.smn_requisicion_detalle_id
	inner join smn_compras.smn_requisicion_cabecera on smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id = smn_compras.smn_requisicion_detalle.smn_requisicion_cabecera_id
where	
	smn_compras.smn_oferta.smn_oferta_id is not null
	${filter}
order by
	smn_compras.smn_oferta.smn_oferta_id
