INSERT INTO smn_compras.smn_oferta
(
	smn_oferta_id, 
	smn_cotizacion_id, 
	smn_documento_id, 
	ofe_numero_documento, 
	smn_item_compras_id, 
	smn_servicios_compras_id, 
	smn_activo_fijo_compras_id, 
	ofe_producto_alterno, 
	ofe_item_alter_prov, 
	ofe_activo_fijo_alter_prov, 
	ofe_cantidad, 
	ofe_precio_ml, 
	ofe_monto_ml, 
	ofe_moneda_id, 
	ofe_tasa, 
	ofe_precio_ma, 
	ofe_monto_ma, 
	ofe_observaciones, 
	ofe_fecha_de_requerido, 
	smn_condicion_financiera_rf, 
	ofe_aplica_anticipo, 
	ofe_porcentaje_anticipo, 
	ofe_monto_anticipo_ml, 
	smn_rel_cotizacion_proveedor_id, 
	smn_proveedor_id, 
	ofe_estatus, 
	ofe_idioma, 
	ofe_usuario, 
	ofe_fecha_registro, 
	ofe_hora, 
	ofe_monto_anticipo_ma
)
VALUES
(
	nextval('smn_compras.seq_smn_oferta'), 
	0, 
	${fld:smn_documento_id}, 
	${fld:secuencia}, 
	${fld:smn_item_id}, 
	${fld:smn_servicio_id}, 
	${fld:smn_afijo_id}, 
	${fld:rpp_producto_alterno}, 
	null, 
	null, 
	${fld:rss_cantidad}, 
	${fld:rpp_precio_ml}, 
	${fld:ofe_monto_ml}, 
	${fld:smn_moneda_id}, 
	${fld:smn_tasa_rf}, 
	${fld:rpp_precio_ma}, 
	${fld:ofe_monto_ma}, 
	${fld:rrs_observaciones}, 
	${fld:rrs_fecha_de_requerido}, 
	${fld:smn_condicion_financiera_rf}, 
	${fld:prv_anticipo}, 
	null, 
	null, 
	0, 
	${fld:smn_proveedor_id}, 
	'RE',
	'${def:locale}',
    '${def:user}',
    {d '${def:date}'},
    '${def:time}', 
	null    
)
RETURNING smn_oferta_id AS smn_oferta_id;