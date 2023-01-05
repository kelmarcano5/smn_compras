select
	smn_compras.smn_rel_cotizacion_proveedor.smn_rel_cotizacion_proveedor_id,
	smn_compras.smn_rel_cotizacion_proveedor.smn_cotizacion_id,
	smn_base.smn_auxiliar.aux_codigo ||' - '|| smn_base.smn_auxiliar.aux_descripcion as smn_proveedor_id,
	smn_compras.smn_cotizacion.cot_numero_documento as numero_cotizacion,
	smn_base.smn_item.itm_codigo ||' - '|| smn_base.smn_item.itm_nombre as smn_item_rf,
	smn_compras.smn_requisicion_detalle.rss_cantidad as cantidad,
	smn_compras.smn_cotizacion_f_entrega.cfe_fecha_entrega,
	smn_compras.smn_cotizacion.cot_fecha_requerido
from
	smn_compras.smn_rel_cotizacion_proveedor
	inner join smn_compras.smn_proveedor on smn_compras.smn_proveedor.smn_proveedor_id = smn_compras.smn_rel_cotizacion_proveedor.smn_proveedor_id
	inner join smn_base.smn_auxiliar on smn_base.smn_auxiliar.smn_auxiliar_id = smn_compras.smn_proveedor.smn_auxiliar_rf
	inner join smn_compras.smn_cotizacion on smn_compras.smn_cotizacion.smn_cotizacion_id = smn_compras.smn_rel_cotizacion_proveedor.smn_cotizacion_id
	left outer join smn_compras.smn_requisicion_detalle on smn_compras.smn_requisicion_detalle.smn_requisicion_detalle_id = smn_compras.smn_cotizacion.smn_requisicion_detalle_id
	left join smn_base.smn_item on smn_base.smn_item.smn_item_id = smn_compras.smn_cotizacion.smn_item_id
	left outer join smn_compras.smn_cotizacion_f_entrega ON smn_compras.smn_cotizacion_f_entrega.smn_cotizacion_id=smn_compras.smn_rel_cotizacion_proveedor.smn_cotizacion_id
	left outer join smn_compras.smn_documentos on smn_compras.smn_documentos.smn_documentos_id = smn_compras.smn_cotizacion.smn_documento_id
	INNER JOIN smn_compras.smn_rel_usuario_documento ON smn_compras.smn_rel_usuario_documento.smn_documento_id=smn_compras.smn_cotizacion.smn_documento_id
	INNER JOIN smn_seguridad.s_user UL ON UL.user_id = smn_compras.smn_rel_usuario_documento.smn_usuario_id
WHERE 
	smn_compras.smn_cotizacion.cot_estatus='RE'
	AND ul.userlogin = '${def:user}'	
order by 
	smn_proveedor_id desc