select
	smn_compras.smn_rel_proveedor_producto.smn_rel_proveedor_producto_id,
	smn_compras.smn_proveedor.smn_proveedor_id,
	smn_compras.smn_proveedor.prv_idioma as prv_idioma_pl0,
	case
	when smn_compras.smn_rel_proveedor_producto.rpp_tipo_producto='AF' then '${lbl:b_activos_fijos}'
	when smn_compras.smn_rel_proveedor_producto.rpp_tipo_producto='IT' then '${lbl:b_item}'
	when smn_compras.smn_rel_proveedor_producto.rpp_tipo_producto='SE' then '${lbl:b_servicios}'
	end as rpp_tipo_producto,
	smn_base.smn_auxiliar.aux_codigo ||' - '|| smn_base.smn_auxiliar.aux_descripcion as smn_proveedor_rf,
	it.itm_codigo||' - '|| it.itm_nombre as rpp_id_producto,
	--it2.itm_codigo||' - '|| it2.itm_nombre as rpp_producto_alterno,
	smn_compras.smn_servicio.sco_nombre as smn_servicio_id,
	smn_compras.smn_rel_proveedor_producto.rpp_fecha_registro
	
from
	smn_compras.smn_proveedor
	inner join smn_compras.smn_rel_proveedor_producto on smn_compras.smn_rel_proveedor_producto.smn_proveedor_id = smn_compras.smn_proveedor.smn_proveedor_id
	left outer join smn_base.smn_item it on it.smn_item_id = smn_compras.smn_rel_proveedor_producto.smn_item_rf 
	--left outer join smn_base.smn_item it2 on it2.smn_item_id = smn_compras.smn_rel_proveedor_producto.rpp_producto_alterno
	inner join smn_base.smn_auxiliar on smn_base.smn_auxiliar.smn_auxiliar_id = smn_compras.smn_proveedor.smn_auxiliar_rf
	left outer join smn_compras.smn_servicio on smn_compras.smn_servicio.smn_servicio_id = smn_compras.smn_rel_proveedor_producto.smn_servicios_compras_id
	
where
	smn_compras.smn_proveedor.smn_proveedor_id=${fld:id2}
