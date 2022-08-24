select distinct
	smn_compras.smn_cotizacion.smn_cotizacion_id,
	smn_compras.smn_requisicion_detalle.smn_requisicion_detalle_id,
	smn_compras.smn_requisicion_detalle.smn_naturaleza_id as smn_naturaleza_id_pl0,
	case
		when smn_compras.smn_cotizacion.cot_estatus='SO' then '${lbl:b_solicited}'
		when smn_compras.smn_cotizacion.cot_estatus='AP' then '${lbl:b_aprobated}'
		when smn_compras.smn_cotizacion.cot_estatus='RE' then 'Registrada'
		when smn_compras.smn_cotizacion.cot_estatus='GE' then 'Generada'
	end as cot_estatus,
	smn_compras.smn_requisicion_cabecera.req_cabecera_version,
	smn_compras.smn_documentos.dcc_codigo|| ' - ' || smn_compras.smn_documentos.dcc_nombre as smn_documento_id,
	smn_compras.smn_cotizacion.cot_numero_documento as numero_cotizacion,
	smn_compras.smn_cotizacion.cot_numero_documento,
	smn_compras.smn_requisicion_cabecera.req_numero as numero_requisicion,
	smn_compras.smn_cotizacion.cot_fecha_requerido as fecha_requerida,
	--smn_base.smn_auxiliar.aux_codigo || ' - ' || smn_base.smn_auxiliar.aux_descripcion as smn_proveedor_id,
	smn_compras.smn_cotizacion.cot_fecha_registro,
	smn_compras.smn_requisicion_detalle.rss_cantidad as cantidad,
	smn_base.smn_item.itm_codigo ||' - '|| smn_base.smn_item.itm_nombre as smn_item_rf,
	usuarios.aux_descripcion as smn_solicitante
from
	smn_compras.smn_cotizacion
	left outer join smn_compras.smn_requisicion_detalle on smn_compras.smn_requisicion_detalle.smn_requisicion_detalle_id = smn_compras.smn_cotizacion.smn_requisicion_detalle_id
	left outer join smn_compras.smn_requisicion_cabecera on smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id = smn_compras.smn_requisicion_detalle.smn_requisicion_cabecera_id
	left outer join smn_compras.smn_documentos on smn_compras.smn_documentos.smn_documentos_id = smn_compras.smn_cotizacion.smn_documento_id
	inner join smn_seguridad.s_user on smn_seguridad.s_user.userlogin = smn_compras.smn_requisicion_cabecera.req_usuario
	inner join smn_base.smn_usuarios on smn_base.smn_usuarios.smn_user_rf = smn_seguridad.s_user.user_id
	inner join smn_compras.smn_roles on smn_compras.smn_roles.smn_usuarios_id = smn_base.smn_usuarios.smn_usuarios_id
	left outer join smn_base.smn_auxiliar usuarios on usuarios.smn_auxiliar_id = smn_base.smn_usuarios.smn_auxiliar_rf
	left join smn_base.smn_item on smn_base.smn_item.smn_item_id = smn_compras.smn_cotizacion.smn_item_id
	--LEFT OUTER JOIN smn_compras.smn_rel_cotizacion_proveedor on smn_compras.smn_rel_cotizacion_proveedor.smn_cotizacion_id = smn_compras.smn_cotizacion.smn_cotizacion_id
	--left outer join smn_compras.smn_proveedor on smn_compras.smn_proveedor.smn_proveedor_id = smn_compras.smn_rel_cotizacion_proveedor.smn_proveedor_id
	--left outer join smn_base.smn_auxiliar on smn_base.smn_auxiliar.smn_auxiliar_id = smn_compras.smn_proveedor.smn_auxiliar_rf
where 
	smn_compras.smn_cotizacion.cot_estatus='RE'
order by 
	smn_compras.smn_cotizacion.cot_fecha_registro desc