select	
	smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id, 
	smn_compras.smn_requisicion_cabecera.req_estatus as req_estatus_pl0,
	(select smn_compras.smn_requisicion_cabecera.req_numero|| ' - ' || smn_compras.smn_requisicion_cabecera.req_descripcion from  smn_compras.smn_requisicion_cabecera where smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id is not null  and smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id=smn_compras.smn_requisicion_detalle.smn_requisicion_cabecera_id) as smn_requisicion_cabecera_id,
	(select smn_compras.smn_requisicion_cabecera.smn_cabecera_version_id from  smn_compras.smn_requisicion_cabecera where smn_compras.smn_requisicion_cabecera.smn_cabecera_version_id is not null  and smn_compras.smn_requisicion_cabecera.smn_cabecera_version_id=smn_compras.smn_requisicion_detalle.smn_cabecera_version_id) as smn_cabecera_version_id,
	case
		when smn_compras.smn_requisicion_detalle.smn_naturaleza_id='IT' then '${lbl:b_item}'
		when smn_compras.smn_requisicion_detalle.smn_naturaleza_id='SE' then '${lbl:b_services}'
		when smn_compras.smn_requisicion_detalle.smn_naturaleza_id='AF' then '${lbl:b_actfij'
	end as smn_naturaleza_id,
	(select smn_compras.smn_servicio.sco_codigo|| ' - ' || smn_compras.smn_servicio.sco_codigo from  smn_compras.smn_servicio where smn_compras.smn_servicio.smn_servicio_id is not null  and smn_compras.smn_servicio.smn_servicio_id=smn_compras.smn_requisicion_detalle.smn_servicio_id) as smn_servicio_id,
	(select smn_base.smn_item.itm_codigo|| ' - ' || smn_base.smn_item.itm_nombre from  smn_base.smn_item where smn_base.smn_item.smn_item_id is not null  and smn_base.smn_item.smn_item_id=smn_compras.smn_requisicion_detalle.smn_item_id) as smn_item_id,
	(select smn_base.smn_activo_fijo.acf_codigo|| ' - ' || smn_base.smn_activo_fijo.acf_nombre from  smn_base.smn_activo_fijo where smn_base.smn_activo_fijo.smn_afijo_id is not null  and smn_base.smn_activo_fijo.smn_afijo_id=smn_compras.smn_requisicion_detalle.smn_afijo_id) as smn_afijo_id,
	case
		when smn_compras.smn_requisicion_detalle.rrs_producto_encontrado='SI' then '${lbl:b_yes}'
		when smn_compras.smn_requisicion_detalle.rrs_producto_encontrado='NO' then '${lbl:b_not}'
	end as rrs_producto_encontrado,
	(select smn_base.smn_contrato_base.ctr_codigo ||' - '||  smn_base.smn_contrato_base.ctr_nombre from  smn_base.smn_contrato_base where smn_base.smn_contrato_base.smn_contrato_base_id is not null  and smn_base.smn_contrato_base.smn_contrato_base_id=smn_compras.smn_requisicion_detalle.smn_contrato_id) as smn_contrato_id,
	(select smn_base.smn_monedas.mon_codigo|| ' - ' || smn_base.smn_monedas.mon_nombre from  smn_base.smn_monedas where smn_base.smn_monedas.smn_monedas_id is not null  and smn_base.smn_monedas.smn_monedas_id=smn_compras.smn_requisicion_detalle.smn_moneda_id) as smn_moneda_id,
	(select smn_compras.smn_proveedor.prv_auxiliar_categoria_id|| ' - ' || smn_compras.smn_proveedor.smn_clase_auxiliar_id from  smn_compras.smn_proveedor where smn_compras.smn_proveedor.smn_proveedor_id is not null  and smn_compras.smn_proveedor.smn_proveedor_id=smn_compras.smn_requisicion_detalle.smn_proveedor_id) as smn_proveedor_id,
	smn_compras.smn_requisicion_detalle.*
from
	smn_compras.smn_requisicion_cabecera,
	smn_compras.smn_requisicion_detalle 
where
	smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id=smn_compras.smn_requisicion_detalle.smn_requisicion_cabecera_id 
 and 
	smn_requisicion_detalle_id = ${fld:id}
