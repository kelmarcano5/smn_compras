INSERT INTO smn_compras.smn_rel_cotizacion_proveedor
(
	smn_rel_cotizacion_proveedor_id,
	smn_cotizacion_id,
	smn_proveedor_id
)
VALUES
(
	nextval('smn_compras.seq_smn_rel_cotizacion_proveedor'),
	${fld:smn_cotizacion_id}, --smn_cotizacion_id
	${fld:smn_proveedor_id}  --smn_proveedor_id
)

RETURNING smn_rel_cotizacion_proveedor_id AS relacion_id;