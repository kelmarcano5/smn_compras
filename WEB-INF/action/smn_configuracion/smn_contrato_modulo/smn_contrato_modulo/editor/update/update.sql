UPDATE smn_compras.smn_contrato_modulo SET
	smn_contrato_base_rf=${fld:smn_contrato_base_rf},
	smn_documentos_id=${fld:smn_documentos_id},
	ctm_dia_factura=${fld:ctm_dia_factura},
	ctm_cantidad=${fld:ctm_cantidad},
	ctm_precio=${fld:ctm_precio},
	ctm_monto=${fld:ctm_monto},
	ctm_porcentaje_incremento=${fld:ctm_porcentaje_incremento},
	ctm_direccion_rf=${fld:ctm_direccion_rf},
	ctm_fecha_renovacion=${fld:ctm_fecha_renovacion},
	ctm_idioma='${def:locale}',
	ctm_usuario_id='${def:user}',
	ctm_fecha_registro={d '${def:date}'},
	ctm_hora='${def:time}'

WHERE
	smn_contrato_modulo_id=${fld:id}

