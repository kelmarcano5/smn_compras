INSERT INTO smn_compras.smn_oferta
(
	smn_oferta_id,
	smn_cotizacion_id,
	smn_rel_cotizacion_proveedor_id,
	smn_documento_id,
	ofe_numero_documento,
	smn_proveedor_id,
	smn_item_compras_id,
	ofe_item_alter_prov, 
	smn_servicios_compras_id,
	smn_activo_fijo_compras_id,
	ofe_cantidad,
	ofe_precio_ml,
	ofe_precio_ma,
	ofe_fecha_de_requerido,
	smn_condicion_financiera_rf,
	ofe_estatus,
	ofe_idioma,
	ofe_usuario,
	ofe_fecha_registro,
	ofe_hora
	
)
VALUES
(
	nextval('smn_compras.seq_smn_oferta'), --smn_oferta_id
	${fld:smn_cotizacion_id}, --smn_cotización_id
	${fld:smn_rel_cotizacion_proveedor_id}, --smn_rel_cotizacion_proveedor_id
	${fld:smn_documento_id}, --smn_documento_id
	${fld:dcc_secuencia}, --ofe_numero_documento
	${fld:smn_proveedor_id}, --smn_proveedor_id
	${fld:smn_item_id}, --smn_Item_id
	(SELECT 
		smn_compras.smn_rel_proveedor_producto.rpp_producto_alterno
	 FROM
	 	smn_compras.smn_rel_proveedor_producto
	 INNER JOIN
	 	smn_compras.smn_rel_cotizacion_proveedor
	 ON
	 	smn_compras.smn_rel_proveedor_producto.smn_proveedor_id = smn_compras.smn_rel_cotizacion_proveedor.smn_proveedor_id
	 INNER JOIN
	 	smn_compras.smn_cotizacion
	 ON	
	 	smn_compras.smn_rel_cotizacion_proveedor.smn_cotizacion_id = smn_compras.smn_cotizacion.smn_cotizacion_id
	 WHERE
	 	smn_compras.smn_cotizacion.smn_cotizacion_id = ${fld:smn_cotizacion_id}
	 AND
	 	smn_compras.smn_rel_proveedor_producto.smn_item_rf = ${fld:producto}
	 UNION
	 SELECT 
		smn_compras.smn_rel_proveedor_producto.rpp_producto_alterno
	 FROM
	 	smn_compras.smn_rel_proveedor_producto
	 INNER JOIN
	 	smn_compras.smn_rel_cotizacion_proveedor
	 ON
	 	smn_compras.smn_rel_proveedor_producto.smn_proveedor_id = smn_compras.smn_rel_cotizacion_proveedor.smn_proveedor_id
	 INNER JOIN
	 	smn_compras.smn_cotizacion
	 ON	
	 	smn_compras.smn_rel_cotizacion_proveedor.smn_cotizacion_id = smn_compras.smn_cotizacion.smn_cotizacion_id
	 WHERE
	 	smn_compras.smn_cotizacion.smn_cotizacion_id = ${fld:smn_cotizacion_id}
	 AND
	 	smn_compras.smn_rel_proveedor_producto.smn_servicios_compras_id = ${fld:producto}
	 UNION
	 SELECT 
		smn_compras.smn_rel_proveedor_producto.rpp_producto_alterno
	 FROM
	 	smn_compras.smn_rel_proveedor_producto
	 INNER JOIN
	 	smn_compras.smn_rel_cotizacion_proveedor
	 ON
	 	smn_compras.smn_rel_proveedor_producto.smn_proveedor_id = smn_compras.smn_rel_cotizacion_proveedor.smn_proveedor_id
	 INNER JOIN
	 	smn_compras.smn_cotizacion
	 ON	
	 	smn_compras.smn_rel_cotizacion_proveedor.smn_cotizacion_id = smn_compras.smn_cotizacion.smn_cotizacion_id
	 WHERE
	 	smn_compras.smn_cotizacion.smn_cotizacion_id = ${fld:smn_cotizacion_id}
	 AND
	 	smn_compras.smn_rel_proveedor_producto.smn_afijos_rf = ${fld:producto}
	),
	${fld:smn_servicio_id}, --smn_servicios_id
	${fld:smn_activo_fijo_rf}, --smn_activo_fijo_id
	${fld:rss_cantidad}, --ofe_cantidad
	${fld:rpp_precio_ml}, --ofe_precio_ml
	${fld:rpp_precio_ma}, --ofe_precio_ma
	${fld:cot_fecha_requerido}, --ofe_fecha_de_requerido
	${fld:smn_condicion_financiera_rf}, --smn_condicion_financiera_rf
	'RE', --ofe_estatus
	'${def:locale}', --ofe_idioma
    '${def:user}', --ofe_usuario
    {d '${def:date}'}, --ofe_fecha_registro
    '${def:time}' --ofe_hora
)

RETURNING smn_oferta_id;