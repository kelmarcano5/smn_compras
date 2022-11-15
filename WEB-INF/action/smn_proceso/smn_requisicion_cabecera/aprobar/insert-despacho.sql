INSERT INTO smn_inventario.smn_despacho
(
	smn_despacho_id,
	smn_modulo_rf, 
	smn_documento_origen_id,
	des_numero_documento_origen,
	smn_usuario_solicitante_rf,
	smn_documento_id,
	des_numero_documento,
	des_descripcion,
	smn_entidad_rf,
	smn_sucursal_rf,
	smn_almacen_rf,
	smn_clase_auxiliar_rf,
	smn_auxiliar_rf,
	smn_unidad_organizativa_rf,
	smn_centro_costo_rf,
	smn_direccion_rf,
	smn_zona_rf,
	smn_ruta_id,
	smn_usuario_transportista_rf,
	smn_transporte_id,
	des_chofer,
	des_fecha_solicitud,
	des_fecha_despacho,
	des_estatus,
	des_monto_pedido_ml,
	des_monto_impuesto_ml,
	des_monto_descuento_ml,
	des_monto_bonificacion_ml,
	des_monto_neto_ml,
	smn_moneda_rf,
	smn_tasa_rf,
	des_monto_pedido_ma,
	des_monto_impuesto_ma,
	des_monto_descuento_ma,
	des_monto_bonificacion_ma,
	des_monto_neto_ma,
	des_idioma,
    des_usuario,
    des_fecha_registro,
    des_hora
)
VALUES
(
	nextval('smn_inventario.seq_smn_despacho'),
	${fld:smn_modulo_rf},
	${fld:smn_documento_id}, /*documento origen*/
	${fld:req_numero},
	(SELECT
		smn_base.smn_usuarios.smn_usuarios_id
	 FROM
	 	smn_base.smn_usuarios
	 INNER JOIN
	 	smn_seguridad.s_user
	 ON
	 	smn_base.smn_usuarios.smn_user_rf = smn_seguridad.s_user.user_id
	 INNER JOIN
	 	smn_compras.smn_requisicion_cabecera
	 ON
	 	smn_seguridad.s_user.userlogin = smn_compras.smn_requisicion_cabecera.req_usuario
	 WHERE	
	 	smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id = ${fld:smn_requisicion_cabecera_id}
	),
	(SELECT 
		smn_inventario.smn_documento.smn_documento_id
	FROM
	 	smn_compras.smn_requisicion_cabecera
	INNER JOIN
	 	smn_compras.smn_documentos
	ON
		smn_compras.smn_requisicion_cabecera.smn_documento_id = smn_compras.smn_documentos.smn_documentos_id
	INNER JOIN
		smn_base.smn_documentos_generales
	ON
		smn_compras.smn_documentos.dcc_transaccion_id = smn_base.smn_documentos_generales.smn_documentos_generales_id
	INNER JOIN
		smn_inventario.smn_documento
	ON
		smn_base.smn_documentos_generales.smn_documentos_generales_id = smn_inventario.smn_documento.smn_documento_general_rf
	WHERE
	 	smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id = ${fld:smn_requisicion_cabecera_id}
	),
	(SELECT 
		(doc_secuencia+1)
	 FROM
		smn_inventario.smn_documento 
	 WHERE
	 	smn_documento_id = (SELECT 
			smn_inventario.smn_documento.smn_documento_id
		FROM
		 	smn_compras.smn_requisicion_cabecera
		INNER JOIN
		 	smn_compras.smn_documentos
		ON
			smn_compras.smn_requisicion_cabecera.smn_documento_id = smn_compras.smn_documentos.smn_documentos_id
		INNER JOIN
			smn_base.smn_documentos_generales
		ON
			smn_compras.smn_documentos.dcc_transaccion_id = smn_base.smn_documentos_generales.smn_documentos_generales_id
		INNER JOIN
			smn_inventario.smn_documento
		ON
			smn_base.smn_documentos_generales.smn_documentos_generales_id = smn_inventario.smn_documento.smn_documento_general_rf
		WHERE
		 	smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id = ${fld:smn_requisicion_cabecera_id}
	 	)
	),
	${fld:req_descripcion},
	${fld:smn_entidad_id},
	${fld:smn_sucursal_id},
	(SELECT
		smn_compras.smn_lineas.smn_almacen_consumo_rf AS smn_almacen_rf
	FROM
		smn_inventario.smn_caracteristica_almacen
		INNER JOIN smn_base.smn_almacen ON smn_inventario.smn_caracteristica_almacen.smn_almacen_rf = smn_base.smn_almacen.smn_almacen_id
		INNER JOIN smn_compras.smn_requisicion_cabecera ON smn_compras.smn_requisicion_cabecera.smn_entidad_id = smn_base.smn_almacen.alm_empresa
		INNER JOIN smn_compras.smn_requisicion_detalle ON smn_compras.smn_requisicion_detalle.smn_requisicion_cabecera_id = smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id
		INNER JOIN smn_compras.smn_rel_linea_item ON smn_compras.smn_requisicion_detalle.smn_item_id = smn_compras.smn_rel_linea_item.smn_item_id
		INNER JOIN smn_compras.smn_lineas ON smn_compras.smn_rel_linea_item.smn_lineas_id = smn_compras.smn_lineas.smn_lineas_id

	 WHERE
	 	smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id = ${fld:smn_requisicion_cabecera_id}
	 AND
	 	smn_inventario.smn_caracteristica_almacen.cal_tipo_almacen = 'DE'
	),
	(SELECT 
		smn_clase_auxiliar_rf
	 FROM
	 	smn_base.smn_usuarios
	 INNER JOIN
	 	smn_seguridad.s_user
	 ON
	 	smn_base.smn_usuarios.smn_user_rf = smn_seguridad.s_user.user_id
	 INNER JOIN
	 	smn_compras.smn_requisicion_cabecera
	 ON
	 	smn_seguridad.s_user.userlogin = smn_compras.smn_requisicion_cabecera.req_usuario
	 WHERE
	 	smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id = ${fld:smn_requisicion_cabecera_id}
	),
	(SELECT 
		smn_auxiliar_rf
	 FROM
	 	smn_base.smn_usuarios
	 INNER JOIN
	 	smn_seguridad.s_user
	 ON
	 	smn_base.smn_usuarios.smn_user_rf = smn_seguridad.s_user.user_id
	 INNER JOIN
	 	smn_compras.smn_requisicion_cabecera
	 ON
	 	smn_seguridad.s_user.userlogin = smn_compras.smn_requisicion_cabecera.req_usuario
	 WHERE
	 	smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id = ${fld:smn_requisicion_cabecera_id}
	),
	(SELECT
		smn_compras.smn_rel_auxiliar_ceco_estorg.smn_estructura_organizacional_rf
	FROM
		smn_base.smn_usuarios
		INNER JOIN smn_seguridad.s_user ON smn_seguridad.s_user.user_id = smn_base.smn_usuarios.smn_user_rf
		INNER JOIN smn_compras.smn_requisicion_cabecera ON smn_compras.smn_requisicion_cabecera.req_usuario = smn_seguridad.s_user.userlogin
		INNER JOIN smn_compras.smn_rel_auxiliar_ceco_estorg ON smn_base.smn_usuarios.smn_auxiliar_rf = smn_compras.smn_rel_auxiliar_ceco_estorg.smn_auxiliar_rf
	 WHERE
	 	smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id = ${fld:smn_requisicion_cabecera_id}
	),
	(SELECT
		smn_compras.smn_rel_auxiliar_ceco_estorg.smn_centro_costo_rf
	FROM
		smn_base.smn_usuarios
		INNER JOIN smn_seguridad.s_user ON smn_seguridad.s_user.user_id = smn_base.smn_usuarios.smn_user_rf
		INNER JOIN smn_compras.smn_requisicion_cabecera ON smn_compras.smn_requisicion_cabecera.req_usuario = smn_seguridad.s_user.userlogin
		INNER JOIN smn_compras.smn_rel_auxiliar_ceco_estorg ON smn_base.smn_usuarios.smn_auxiliar_rf = smn_compras.smn_rel_auxiliar_ceco_estorg.smn_auxiliar_rf
	 WHERE
	 	smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id = ${fld:smn_requisicion_cabecera_id}
	),
	null,
	null,
	null,
	null,
	null,
	null,
	${fld:req_fecha_requerido}, --fecha_solicitud
	null,
	'GE',
	null, /*des_monto_pedido_ml*/
	(SELECT
		SUM(rim_monto_impuesto)
	 FROM
	 	smn_compras.smn_req_detalle_impuesto
	 INNER JOIN
	 	smn_compras.smn_requisicion_detalle
	 ON
	 	smn_compras.smn_requisicion_detalle.smn_requisicion_detalle_id = smn_compras.smn_req_detalle_impuesto.smn_requisicion_detalle_id
	 WHERE
	 	smn_compras.smn_requisicion_detalle.smn_requisicion_cabecera_id = ${fld:smn_requisicion_cabecera_id}
	),
	(SELECT
		SUM(drc_monto_descuento)
	 FROM
	 	smn_compras.smn_req_detalle_dcto_retenc
	 INNER JOIN
	 	smn_compras.smn_requisicion_detalle
	 ON
	 	smn_compras.smn_requisicion_detalle.smn_requisicion_detalle_id = smn_compras.smn_req_detalle_dcto_retenc.smn_requisicion_detalle_id
	 WHERE
	 	smn_compras.smn_requisicion_detalle.smn_requisicion_cabecera_id = ${fld:smn_requisicion_cabecera_id}
	),
	null, /*monto bonificacion*/
	null, /*monto neto*/
	null,
	null,
	null,
	null,
	null,
	null,
	null,
	'${def:locale}',
    '${def:user}',
    {d '${def:date}'},
    '${def:time}'
)

RETURNING smn_despacho_id;