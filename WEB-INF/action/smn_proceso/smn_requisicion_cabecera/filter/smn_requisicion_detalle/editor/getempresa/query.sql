select DISTINCT smn_base.smn_item.smn_item_id as id, smn_base.smn_item.itm_codigo|| ' - ' || smn_base.smn_item.itm_nombre as item from smn_compras.smn_requisicion_detalle
inner join smn_compras.smn_requisicion_cabecera on smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id = smn_compras.smn_requisicion_detalle.smn_requisicion_cabecera_id
inner join smn_base.smn_item on smn_base.smn_item.smn_item_id = smn_compras.smn_requisicion_detalle.smn_item_id
inner join smn_base.smn_entidades on smn_base.smn_entidades.smn_entidades_id = smn_compras.smn_requisicion_cabecera.smn_entidad_id
left join smn_base.smn_almacen on smn_base.smn_almacen.alm_empresa = smn_compras.smn_requisicion_cabecera.smn_entidad_id
inner join smn_compras.smn_rel_linea_item on smn_compras.smn_rel_linea_item.smn_item_id = smn_base.smn_item.smn_item_id
where 
smn_compras.smn_requisicion_cabecera.smn_entidad_id=${fld:id}
