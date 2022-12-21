SELECT
	smn_compras.smn_oferta.*,
	smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id,
	smn_compras.smn_requisicion_cabecera.req_numero, 
	smn_compras.smn_requisicion_cabecera.smn_entidad_id,
	smn_compras.smn_requisicion_cabecera.smn_sucursal_id,
	smn_base.smn_usuarios.smn_auxiliar_rf
FROM
	smn_compras.smn_oferta 
	INNER JOIN	smn_compras.smn_cotizacion ON
		smn_compras.smn_oferta.smn_cotizacion_id = smn_compras.smn_cotizacion.smn_cotizacion_id
	INNER JOIN smn_compras.smn_requisicion_detalle ON
		smn_compras.smn_cotizacion.smn_requisicion_detalle_id = smn_compras.smn_requisicion_detalle.smn_requisicion_detalle_id
	INNER JOIN smn_compras.smn_requisicion_cabecera ON
		smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id = smn_compras.smn_requisicion_detalle.smn_requisicion_cabecera_id
	INNER JOIN smn_seguridad.s_user ON 
		smn_compras.smn_requisicion_cabecera.req_usuario = smn_seguridad.s_user.userlogin
	INNER JOIN smn_base.smn_usuarios ON 
		smn_base.smn_usuarios.smn_user_rf = smn_seguridad.s_user.user_id 	
WHERE
	ofe_estatus IN ('AP') and
	smn_entidad_id=${fld:smn_entidad_id} and
	smn_compras.smn_oferta.smn_proveedor_id=${fld:smn_proveedor_id} 

ORDER BY 
	smn_entidad_id,smn_sucursal_id,smn_proveedor_id