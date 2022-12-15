select
	smn_base.smn_descuento.dct_nombre,
	smn_base.smn_codigos_impuestos.imp_descripcion,
	smn_base.smn_monedas.mon_nombre,
	smn_base.smn_tasas_de_cambio.tca_tasa_cambio,
		smn_compras.smn_impto_deduc_oferta.smn_impto_deduc_oferta_id,
	${field}
from
	smn_base.smn_descuento,
	smn_base.smn_codigos_impuestos,
	smn_base.smn_monedas,
	smn_base.smn_tasas_de_cambio,
	smn_compras.smn_impto_deduc_oferta
where
	smn_base.smn_descuento.smn_descuento_id = smn_compras.smn_impto_deduc_oferta.rio_cod_descuento and
	smn_base.smn_codigos_impuestos.smn_codigos_impuestos_id = smn_compras.smn_impto_deduc_oferta.rio_cod_impuesto_deduc_id and
	smn_base.smn_monedas.smn_monedas_id = smn_compras.smn_impto_deduc_oferta.rio_moneda_rf and
	smn_base.smn_tasas_de_cambio.smn_tasas_de_cambio_id = smn_compras.smn_impto_deduc_oferta.rio_tasa_rf and
		smn_compras.smn_impto_deduc_oferta.smn_impto_deduc_oferta_id is not null
	${filter}
	
	
