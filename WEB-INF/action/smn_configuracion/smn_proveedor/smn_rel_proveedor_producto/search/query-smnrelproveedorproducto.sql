select
	smn_compras.smn_rel_proveedor_producto.smn_rel_proveedor_producto_id,
	smn_compras.smn_proveedor.smn_proveedor_id,
	CASE  
        WHEN smn_compras.smn_rel_proveedor_producto.smn_item_rf IS NOT NULL THEN
        it.itm_codigo || ' - ' || it.itm_nombre 
        WHEN smn_compras.smn_rel_proveedor_producto.smn_servicios_compras_id IS NOT NULL THEN
        smn_compras.smn_servicio.sco_codigo || ' - ' || smn_compras.smn_servicio.sco_nombre 
        WHEN smn_compras.smn_rel_proveedor_producto.smn_afijos_rf IS NOT NULL THEN
        smn_base.smn_activo_fijo.acf_codigo || ' - ' || smn_base.smn_activo_fijo.acf_nombre 
    END AS rpp_producto,
	smn_compras.smn_rel_proveedor_producto.rpp_fecha_registro,
	smn_compras.smn_rel_proveedor_producto.rpp_producto_alterno,
	smn_compras.smn_rel_proveedor_producto.rpp_existencia_proveedor,
	smn_compras.smn_rel_proveedor_producto.rpp_precio_ml,
	smn_compras.smn_rel_proveedor_producto.rpp_fecha_valides
from
	smn_compras.smn_proveedor
	inner join smn_compras.smn_rel_proveedor_producto on smn_compras.smn_rel_proveedor_producto.smn_proveedor_id = smn_compras.smn_proveedor.smn_proveedor_id
	left outer join smn_base.smn_item it on it.smn_item_id = smn_compras.smn_rel_proveedor_producto.smn_item_rf 
	inner join smn_base.smn_auxiliar on smn_base.smn_auxiliar.smn_auxiliar_id = smn_compras.smn_proveedor.smn_auxiliar_rf
	left outer join smn_compras.smn_servicio on smn_compras.smn_servicio.smn_servicio_id = smn_compras.smn_rel_proveedor_producto.smn_servicios_compras_id
	LEFT OUTER JOIN smn_base.smn_activo_fijo ON smn_base.smn_activo_fijo.smn_afijo_id = smn_compras.smn_rel_proveedor_producto.smn_afijos_rf
where
	smn_compras.smn_proveedor.smn_proveedor_id=${fld:id2}
