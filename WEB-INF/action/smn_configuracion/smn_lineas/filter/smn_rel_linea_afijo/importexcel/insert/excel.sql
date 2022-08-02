INSERT INTO smn_compras.smn_rel_linea_afijo
(
	smn_rel_linea_afijo_id,
	smn_lineas_id,
	smn_afijo_id
)
VALUES
(
	${seq:nextval@smn_compras.seq_smn_rel_linea_afijo},
	?,
	?
)
