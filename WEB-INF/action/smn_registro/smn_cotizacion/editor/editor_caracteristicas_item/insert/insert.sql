INSERT INTO smn_inventario.smn_caracteristica_item
(
	smn_caracteristica_item_id,
	smn_item_rf,
	cit_codigo_barra,
	cit_codigo_qr,
	cit_codigo_alterno,
	cit_descripcion_tecnica,
	smn_unidad_medida_base_rf,
	smn_unidad_medida_compra_rf,
	smn_unidad_medida_venta_rf,
	smn_unidad_medida_almacenamiento_rf,
	smn_unidad_medida_despacho_rf,
	cit_peso,
	smn_unidad_medida_peso_rf,
	cit_alto,
	smn_unidad_medida_alto_rf,
	cit_ancho,
	smn_unidad_medida_ancho_rf,
	cit_profundidad,
	smn_unidad_medida_profundidad_rf,
	cit_es_medicamento,
	smn_principio_activo_rf,
	cit_req_control_lote,
	cit_req_control_vencimiento,
	cit_dias_ant_vencimiento,
	cit_tipo_costo,
	cit_valoracion_inventario,
	cit_metodo_despacho,
	cit_es_reusable,
	cit_reuso_apertura,
	cit_cant_reuso,
	cit_origen_producto,
	cit_descripcion_compra,
	cit_codigo_arancel,
	cit_dias_entrega,
	smn_centro_costo_rf,
	cit_item_compuesto,
	cit_proveedor_exclusivo,
	cit_almacenado,
	cit_estatus,
	cit_vigencia,
	cit_idioma,
	cit_usuario,
	cit_fecha_registro,
	cit_hora,
	cit_tipo_producto,
	cit_referencia,
	smn_clasificacion_abc
)
VALUES
(
	${seq:currval@smn_inventario.seq_smn_caracteristica_item},
	${fld:smn_item_rf},
	${fld:cit_codigo_barra},
	${fld:cit_codigo_qr},
	${fld:cit_codigo_alterno},
	${fld:cit_descripcion_tecnica},
	${fld:smn_unidad_medida_base_rf},
	${fld:smn_unidad_medida_compra_rf},
	${fld:smn_unidad_medida_venta_rf},
	${fld:smn_unidad_medida_almacenamiento_rf},
	${fld:smn_unidad_medida_despacho_rf},
	${fld:cit_peso},
	${fld:smn_unidad_medida_peso_rf},
	${fld:cit_alto},
	${fld:smn_unidad_medida_alto_rf},
	${fld:cit_ancho},
	${fld:smn_unidad_medida_ancho_rf},
	${fld:cit_profundidad},
	${fld:smn_unidad_medida_profundidad_rf},
	${fld:cit_es_medicamento},
	${fld:smn_principio_activo_rf},
	${fld:cit_req_control_lote},
	${fld:cit_req_control_vencimiento},
	${fld:cit_dias_ant_vencimiento},
	${fld:cit_tipo_costo},
	${fld:cit_valoracion_inventario},
	${fld:cit_metodo_despacho},
	${fld:cit_es_reusable},
	${fld:cit_reuso_apertura},
	${fld:cit_cant_reuso},
	${fld:cit_origen_producto},
	${fld:cit_descripcion_compra},
	${fld:cit_codigo_arancel},
	${fld:cit_dias_entrega},
	${fld:smn_centro_costo_rf},
	${fld:cit_item_compuesto},
	${fld:cit_proveedor_exclusivo},
	${fld:cit_almacenado},
	${fld:cit_estatus},
	{d '${def:date}'},
	'${def:locale}',
	'${def:user}',
	{d '${def:date}'},
	'${def:time}',
	${fld:cit_tipo_producto},
	${seq:nextval@smn_inventario.seq_cit_referencia},
	${fld:smn_clasificacion_abc}



)
