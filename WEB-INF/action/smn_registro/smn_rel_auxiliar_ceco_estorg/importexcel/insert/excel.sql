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
	${seq:nextval@smn_compras.seq_smn_rel_auxiliar_ceco_estorg},
	?,
	?,
	?,
	'${def:locale}',
	'${def:user}',
	{d '${def:date}'},
	'${def:time}'
)
