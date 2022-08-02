INSERT INTO smn_compras.smn_rel_linea_servicio
(
	smn_rel_linea_servicio_id,
	smn_lineas_id,
	smn_servicio_id
)
VALUES
(
	${seq:currval@smn_compras.seq_smn_rel_linea_servicio},
	${fld:smn_lineas_id},
	${fld:smn_servicio_id}
)
