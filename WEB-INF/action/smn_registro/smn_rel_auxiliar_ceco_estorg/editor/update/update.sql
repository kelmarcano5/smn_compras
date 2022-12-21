UPDATE smn_compras.smn_rel_auxiliar_ceco_estorg SET
	smn_auxiliar_rf=${fld:smn_auxiliar_rf},
	smn_centro_costo_rf=${fld:smn_centro_costo_rf},
	smn_estructura_organizacional_rf=${fld:smn_estructura_organizacional_rf},
	rac_idioma='${def:locale}',
	rac_usuario='${def:user}',
	rac_fecha_registro={d '${def:date}'},
	rac_hora='${def:time}'

WHERE
	smn_rel_auxiliar_ceco_estorg_id=${fld:id}

