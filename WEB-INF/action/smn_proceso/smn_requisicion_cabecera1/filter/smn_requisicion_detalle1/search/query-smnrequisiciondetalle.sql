select
	smn_compras.smn_requisicion_detalle.smn_requisicion_detalle_id,
	smn_compras.smn_requisicion_cabecera.req_estatus as req_estatus_pl0,
	smn_compras.smn_requisicion_cabecera.req_descripcion as smn_requisicion_cabecera_id,
	smn_compras.smn_lineas.lin_codigo||' - '|| smn_compras.smn_lineas.lin_nombre as smn_linea_id,
	smn_base.smn_contrato_base.ctr_codigo||' - '||smn_base.smn_contrato_base.ctr_nombre as smn_contrato_id,
	smn_compras.smn_requisicion_detalle.rss_cantidad,
	smn_compras.smn_requisicion_detalle.rrs_precio,
	smn_compras.smn_requisicion_detalle.rrs_monto,
	smn_compras.smn_requisicion_detalle.rrs_fecha_registro,
case
        when smn_compras.smn_requisicion_detalle.smn_naturaleza_id = 'IT' THEN smn_base.smn_item.itm_codigo ||' - '|| smn_base.smn_item.itm_nombre
        when smn_compras.smn_requisicion_detalle.smn_naturaleza_id = 'SE' THEN smn_compras.smn_servicio.sco_codigo|| ' - ' || smn_compras.smn_servicio.sco_nombre
        when smn_compras.smn_requisicion_detalle.smn_naturaleza_id = 'AF' THEN smn_base.smn_activo_fijo.acf_codigo ||' - '|| smn_base.smn_activo_fijo.acf_nombre
    end as smn_item_id
    --smn_base.smn_item.itm_codigo ||' - '|| smn_base.smn_item.itm_nombre as smn_item_id
    
from
    smn_compras.smn_requisicion_detalle
    left OUTER join smn_compras.smn_requisicion_cabecera on smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id = smn_compras.smn_requisicion_detalle.smn_requisicion_cabecera_id
    left outer join smn_compras.smn_lineas on smn_compras.smn_lineas.smn_lineas_id = smn_compras.smn_requisicion_detalle.smn_linea_id
    left outer join smn_base.smn_contrato_base on smn_base.smn_contrato_base.smn_contrato_base_id = smn_compras.smn_requisicion_detalle.smn_contrato_id
    left outer join smn_base.smn_item on smn_base.smn_item.smn_item_id = smn_compras.smn_requisicion_detalle.smn_item_id
    left outer join smn_compras.smn_servicio on smn_compras.smn_servicio.smn_servicio_id = smn_compras.smn_requisicion_detalle.smn_servicio_id
    left outer join smn_base.smn_activo_fijo on smn_base.smn_activo_fijo.smn_afijo_id = smn_compras.smn_requisicion_detalle.smn_afijo_id
where 
		smn_compras.smn_requisicion_detalle.smn_requisicion_cabecera_id=${fld:id2}
		order by rrs_fecha_registro desc 