INSERT INTO smn_compras.smn_rel_linea_item
(
	smn_rel_linea_item_id,
	smn_lineas_id,
	smn_item_id
)
VALUES
(
	nextval('smn_compras.seq_smn_rel_linea_item'),
	${fld:smn_lineas_id},
	${fld:item_id}
)
