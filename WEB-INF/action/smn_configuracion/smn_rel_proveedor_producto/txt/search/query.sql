select
		smn_compras.smn_rel_proveedor_producto.smn_rel_proveedor_producto_id,
	smn_compras.smn_proveedor.smn_proveedor_id,
	smn_compras.smn_proveedor.prv_idioma as prv_idioma_pl0,
select
		smn_compras.smn_rel_proveedor_producto.smn_rel_proveedor_producto_id,
	case
	when smn_compras.smn_rel_proveedor_producto.rpp_tipo_producto='AF' then '${lbl:b_activos_fijos}'
	when smn_compras.smn_rel_proveedor_producto.rpp_tipo_producto='IT' then '${lbl:b_item}'
	when smn_compras.smn_rel_proveedor_producto.rpp_tipo_producto='SE' then '${lbl:b_servicios}'
	end as rpp_tipo_producto_combo,
	smn_compras.smn_rel_proveedor_producto.smn_proveedor_rf,
	smn_compras.smn_rel_proveedor_producto.rpp_id_producto,
	smn_compras.smn_rel_proveedor_producto.rpp_tipo_producto,
	smn_compras.smn_rel_proveedor_producto.rpp_producto_alterno,
	smn_compras.smn_rel_proveedor_producto.rpp_fecha_registro
	
from
	smn_compras.smn_proveedor,
	smn_compras.smn_rel_proveedor_producto
where
	smn_compras.smn_proveedor.smn_proveedor_id=smn_compras.smn_rel_proveedor_producto.smn_proveedor_rf
