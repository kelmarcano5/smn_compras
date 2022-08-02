UPDATE smn_compras.smn_proveedor SET
	smn_clase_auxiliar_rf=${fld:smn_clase_auxiliar_rf},
	smn_auxiliar_rf=${fld:smn_auxiliar_rf},
	smn_forma_pago_rf=${fld:smn_forma_pago_rf},
	smn_clasificacion_abc_rf=${fld:smn_clasificacion_abc_rf},
	prv_estatus=${fld:prv_estatus},
	prv_fecha_vigencia=${fld:prv_fecha_vigencia},
	prv_anticipo=${fld:prv_anticipo},
	prv_fecha_vigencia_anticipo=${fld:prv_fecha_vigencia_anticipo},
	prv_nacional_ext=${fld:prv_nacional_ext},
	prv_idioma='${def:locale}',
	prv_usuario_id='${def:user}',
	prv_fecha_registro={d '${def:date}'},
	prv_hora='${def:time}',
	prv_empresa_relacionada=${fld:prv_empresa_relacionada},
	smn_entidades_id=${fld:smn_entidades_id},
	smn_documentos_id=${fld:smn_documentos_id},
	smn_codigos_impuestos_rf=${fld:smn_codigos_impuestos_rf},
	smn_condicion_financiera_rf = ${fld:smn_condicion_financiera_rf}
WHERE
	smn_proveedor_id=${fld:id}

