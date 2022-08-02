INSERT INTO smn_activo_fijo.smn_activo_fijo_recepcion
(
	smn_activo_recepcion_id,
  	smn_entidades_rf,
	smn_sucursales_rf,
	smn_orden_compra_id,
	smn_centro_costo_rf,
	smn_auxiliar_rf,
	smn_proveedor_rf,
	afr_estatus,
	afr_idioma,
	afr_usuario,
	afr_fecha_registro,
	afr_hora
)
VALUES
(
	nextval('smn_activo_fijo.seq_smn_activo_fijo_recepcion'), --smn_activo_recepcion_id
  	${fld:smn_entidad_rf}, --smn_entidades_rf
	${fld:smn_entidad_rf}, --smn_sucursales_rf
	${fld:smn_orden_compra_cabecera_id}, --smn_orden_compra_id
	${fld:smn_centro_costo_rf}, --smn_centro_costo_rf
	${fld:smn_auxiliar_rf}, --smn_auxiliar_rf
	${fld:smn_proveedor_id}, --smn_proveedor_rf
	'PR', --afr_estatus
	'${def:locale}', --afr_idioma
	'${def:user}', --afr_usuario
	{d '${def:date}'}, --afr_fecha_registro
	'${def:time}' --afr_hora
)