select
	smn_base.smn_activo_fijo.acf_nombre,
	smn_base.smn_item.itm_nombre,
	smn_base.smn_monedas.mon_nombre,
	smn_base.smn_tasas_de_cambio.tca_tasa_cambio,
	${field}
from
	smn_base.smn_activo_fijo,
	smn_base.smn_item,
	smn_base.smn_monedas,
	smn_base.smn_tasas_de_cambio,
	smn_compras.smn_oferta
where
	smn_base.smn_activo_fijo.smn_activo_fijo_id = smn_compras.smn_oferta.ofe_activo_fijo_alter_prov and
	smn_base.smn_item.smn_item_id = smn_compras.smn_oferta.ofe_item_alter_prov and
	smn_base.smn_monedas.smn_monedas_id = smn_compras.smn_oferta.ofe_moneda_id and
	smn_base.smn_tasas_de_cambio.smn_tasas_de_cambio_id = smn_compras.smn_oferta.ofe_tasa and
		smn_compras.smn_oferta.smn_oferta_id is not null
