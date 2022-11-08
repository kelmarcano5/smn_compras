select
	smn_compras.smn_impto_deduc_oferta.smn_impuest_deducc_oferta_id,
	smn_compras.smn_oferta.smn_oferta_id,
	smn_base.smn_descuento.dct_codigo||'-'||smn_base.smn_descuento.dct_nombre as rio_cod_descuento,
	smn_base.smn_codigos_impuestos.imp_codigo||'-'||smn_base.smn_codigos_impuestos.imp_descripcion as rio_cod_impuesto_deduc_id,
	smn_compras.smn_impto_deduc_oferta.rio_monto_impuesto_ml,
	smn_compras.smn_impto_deduc_oferta.rio_monto_base_ml,
	smn_compras.smn_impto_deduc_oferta.rio_porcentaje_descuento,
	smn_compras.smn_impto_deduc_oferta.rio_monto_descuento_ml
	
	
	from 
	smn_compras.smn_impto_deduc_oferta
	INNER JOIN smn_compras.smn_oferta on smn_compras.smn_oferta.smn_oferta_id = smn_compras.smn_impto_deduc_oferta.smn_oferta_id
	LEFT OUTER JOIN smn_base.smn_descuento on smn_base.smn_descuento.smn_descuento_id = smn_compras.smn_impto_deduc_oferta.rio_cod_descuento
LEFT OUTER JOIN smn_base.smn_codigos_impuestos on smn_base.smn_codigos_impuestos.smn_codigos_impuestos_id = smn_compras.smn_impto_deduc_oferta.rio_cod_impuesto_deduc_id

where
	smn_compras.smn_impto_deduc_oferta.smn_impuest_deducc_oferta_id is not null
	${filter} 
order by
		smn_compras.smn_impto_deduc_oferta.smn_impuest_deducc_oferta_id
