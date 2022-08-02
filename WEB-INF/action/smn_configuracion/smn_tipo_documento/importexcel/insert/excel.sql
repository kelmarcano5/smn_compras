INSERT INTO smn_compras.smn_tipo_documento
(
	smn_tipo_documento_id,
	tdc_codigo,
	tdc_nombre,
	tdc_naturaleza
)
VALUES
(
	${seq:nextval@smn_compras.seq_smn_tipo_documento},
	?,
	?,
	?
)
