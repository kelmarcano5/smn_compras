INSERT INTO smn_inventario.smn_despacho_detalle
(
	smn_despacho_detalle_id,
	smn_despacho_id,
	smn_caracteristica_item_id,
	dde_cantidad_solicitada,
	dde_cantidad_despachada,
	dde_costo_ml,
	dde_costo_ma,
	dde_motivo,
	smn_usuario_aprobador_rf,
	dde_fecha_aprobacion,
	dde_fecha_cierre,
	dde_estatus_transaccion,
	dde_idioma,
	dde_usuario,
	dde_fecha_registro,
	dde_hora
)
VALUES
(
	nextval('smn_inventario.seq_smn_despacho_detalle'),
	${fld:smn_despacho_id},
	${fld:smn_item_id},
	${fld:rss_cantidad},
	null,
	${fld:rrs_monto},
	${fld:rrs_monto_moneda_alterna},
	null,
	null,
	null,
	null,
	'ER',
	'${def:locale}',
    '${def:user}',
    {d '${def:date}'},
    '${def:time}'
)

RETURNING smn_despacho_detalle_id;