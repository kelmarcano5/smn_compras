INSERT INTO smn_compras.smn_rel_auxiliar_ceco_estorg
(
	smn_rel_auxiliar_ceco_estorg_id,
	smn_auxiliar_rf,
	smn_centro_costo_rf,
	smn_estructura_organizacional_rf,
	rac_idioma,
	rac_usuario,
	rac_fecha_registro,
	rac_hora
)
VALUES
(
	${seq:currval@smn_compras.seq_smn_rel_auxiliar_ceco_estorg},
	${fld:smn_auxiliar_rf},
	${fld:smn_centro_costo_rf},
	${fld:smn_estructura_organizacional_rf},
	'${def:locale}',
	'${def:user}',
	{d '${def:date}'},
	'${def:time}'
)
