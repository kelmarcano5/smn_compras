INSERT INTO smn_compras.smn_orden_compra_cabecera
(
	smn_orden_compra_cabecera_id,
	smn_requisicion_cabecera_id,  
	smn_entidad_rf,
	smn_sucursal_rf,
	smn_documento_id,  
	occ_orden_compra_numero,
	occ_descripcion,   
	smn_proveedor_id, 
	smn_auxiliar_rf,  
	occ_fecha_elaboracion,
	occ_fecha_orde_compra,
	smn_forma_pago_rf,  
	smn_condicion_pago_rf, 
	occ_monto_ml,
	occ_monto_neto_ml, 
	occ_estatus,
	occ_idioma,
	occ_usuario,
    occ_fecha_registro,
	occ_hora  
)
VALUES
(
	nextval('smn_compras.seq_smn_orden_compra_cabecera'),
	${fld:smn_requisicion_cabecera_id},
	${fld:smn_entidad_id},
	${fld:smn_sucursal_id},
	(SELECT
		smn_compras.smn_documentos.smn_documentos_id
	 FROM
		smn_compras.smn_documentos
	 INNER JOIN
		smn_compras.smn_tipo_documento
	 ON
		smn_compras.smn_documentos.smn_tipo_documento_id = smn_compras.smn_tipo_documento.smn_tipo_documento_id
	 WHERE
		smn_compras.smn_tipo_documento.tdc_naturaleza = 'OC'
	),
	(SELECT 
		COUNT(occ_orden_compra_numero)+1 
	 FROM 
		smn_compras.smn_orden_compra_cabecera
	),
	${fld:req_descripcion},
	(SELECT 
		smn_proveedor_rf 
	 FROM 
	 	smn_compras.smn_rel_proveedor_producto
	 WHERE
	 	rpp_id_producto = 
	 	(SELECT
            CASE
	            WHEN smn_naturaleza_id = 'IT' THEN smn_item_id
	            WHEN smn_naturaleza_id = 'SE' THEN smn_servicio_id
	            ELSE smn_afijo_id
            END AS producto
         FROM 
         	smn_compras.smn_requisicion_detalle
         WHERE 
         	smn_compras.smn_requisicion_detalle.smn_requisicion_detalle_id = ${fld:smn_requisicion_detalle_id}
        )
	),
	'0',/*smn_auxiliar_rf*/
	${fld:fecha_actual},
	${fld:fecha_actual},
	(SELECT 
		smn_forma_pago_rf 
	 FROM 
		smn_compras.smn_proveedor
	 INNER JOIN
		 smn_compras.smn_rel_proveedor_producto
	 ON
		smn_compras.smn_proveedor.smn_proveedor_id =  smn_compras.smn_rel_proveedor_producto.smn_proveedor_rf
	 WHERE
	 	 smn_compras.smn_rel_proveedor_producto.rpp_id_producto = 
	 	(SELECT
            CASE
	            WHEN smn_naturaleza_id = 'IT' THEN smn_item_id
	            WHEN smn_naturaleza_id = 'SE' THEN smn_servicio_id
	            ELSE smn_afijo_id
            END AS producto
         FROM 
         	smn_compras.smn_requisicion_detalle
         WHERE 
         	smn_compras.smn_requisicion_detalle.smn_requisicion_detalle_id = ${fld:smn_requisicion_detalle_id}
        )
	),
	(SELECT 
		aux_cond_pago_rf 
	 FROM
	 	smn_base.smn_auxiliar
	 INNER JOIN 
	 	smn_compras.smn_proveedor
	 ON
	 	smn_base.smn_auxiliar.smn_auxiliar_id = smn_compras.smn_proveedor.smn_auxiliar_rf
	 INNER JOIN
	 	smn_compras.smn_rel_proveedor_producto
	 ON
	 	smn_compras.smn_proveedor.smn_proveedor_id =  smn_compras.smn_rel_proveedor_producto.smn_proveedor_rf
	 WHERE
	 	 smn_compras.smn_rel_proveedor_producto.rpp_id_producto = 
	 	(SELECT
            CASE
	            WHEN smn_naturaleza_id = 'IT' THEN smn_item_id
	            WHEN smn_naturaleza_id = 'SE' THEN smn_servicio_id
	            ELSE smn_afijo_id
            END AS producto
         FROM 
         	smn_compras.smn_requisicion_detalle
         WHERE 
         	smn_compras.smn_requisicion_detalle.smn_requisicion_detalle_id = ${fld:smn_requisicion_detalle_id}
        )
	),
	'0.0',
	'0.0',
	'RE',
	'${def:locale}',
    '${def:user}',
    {d '${def:date}'},
    '${def:time}'
)
RETURNING smn_orden_compra_cabecera_id AS orden_compra_id;