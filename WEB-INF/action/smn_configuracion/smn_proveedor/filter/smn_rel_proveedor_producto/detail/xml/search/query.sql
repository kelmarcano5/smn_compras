select
	smn_compras.smn_proveedor.smn_proveedor_id,
	smn_compras.smn_proveedor.prv_idioma as prv_idioma_pl0,
select
	case
	when smn_compras.smn_rel_proveedor_producto.rpp_tipo_producto='AF' then '${lbl:b_activos_fijos}'
	when smn_compras.smn_rel_proveedor_producto.rpp_tipo_producto='IT' then '${lbl:b_item}'
	when smn_compras.smn_rel_proveedor_producto.rpp_tipo_producto='SE' then '${lbl:b_servicios}'
	end as rpp_tipo_producto,
	smn_compras.smn_rel_proveedor_producto.*
from
	smn_compras.smn_proveedor,
	smn_compras.smn_rel_proveedor_producto
where
	smn_compras.smn_proveedor.smn_proveedor_id=smn_compras.smn_rel_proveedor_producto.smn_proveedor_rf
	and
	smn_rel_proveedor_producto_id = ${fld:id}
