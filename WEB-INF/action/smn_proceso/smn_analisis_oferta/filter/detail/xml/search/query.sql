select
select
select
select
select
select
	smn_base.smn_activo_fijo.acf_nombre,
	smn_base.smn_item.itm_nombre,
	smn_base.smn_monedas.mon_nombre,
	smn_base.smn_tasas_de_cambio.tca_tasa_cambio,
	case
	when smn_compras.smn_oferta.ofe_producto_alterno='SI' then '${lbl:b_yes}'
	when smn_compras.smn_oferta.ofe_producto_alterno='NO' then '${lbl:b_not}'
	end as ofe_producto_alterno,
select
select
select
select
	case
	when smn_compras.smn_oferta.ofe_estatus='RE' then '${lbl:b_received}'
	when smn_compras.smn_oferta.ofe_estatus='AN' then '${lbl:b_analyzed}'
	when smn_compras.smn_oferta.ofe_estatus='AP' then '${lbl:b_aprobated}'
	when smn_compras.smn_oferta.ofe_estatus='RZ' then '${lbl:b_rejected}'
	end as ofe_estatus,
	smn_compras.smn_oferta.*
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
	smn_oferta_id = ${fld:id}
