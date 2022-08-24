SELECT
    smn_compras.smn_requisicion_detalle.smn_requisicion_detalle_id,
    smn_compras.smn_requisicion_cabecera.req_estatus AS req_estatus_pl0,
    smn_compras.smn_requisicion_cabecera.req_descripcion AS smn_requisicion_cabecera_id,
    smn_compras.smn_lineas.lin_codigo || ' - ' || smn_compras.smn_lineas.lin_nombre AS smn_linea_id,
    smn_base.smn_contrato_base.ctr_codigo || ' - ' || smn_base.smn_contrato_base.ctr_nombre AS smn_contrato_id,
    smn_compras.smn_requisicion_detalle.rss_cantidad,
    smn_compras.smn_requisicion_detalle.rrs_precio,
    smn_compras.smn_requisicion_detalle.rrs_monto,
    smn_compras.smn_requisicion_detalle.rrs_fecha_registro,
CASE  
        WHEN smn_compras.smn_requisicion_detalle.smn_naturaleza_id = 'IT' THEN
        smn_base.smn_item.itm_codigo || ' - ' || smn_base.smn_item.itm_nombre 
        WHEN smn_compras.smn_requisicion_detalle.smn_naturaleza_id = 'SE' THEN
        smn_compras.smn_servicio.sco_codigo || ' - ' || smn_compras.smn_servicio.sco_nombre 
        WHEN smn_compras.smn_requisicion_detalle.smn_naturaleza_id = 'AF' THEN
        smn_base.smn_activo_fijo.acf_codigo || ' - ' || smn_base.smn_activo_fijo.acf_nombre 
    END AS smn_item_id,
    smn_compras.smn_requisicion_detalle.rrs_especificaciones_del_requerimiento
    
    
FROM
    smn_compras.smn_requisicion_detalle
    LEFT OUTER JOIN smn_compras.smn_requisicion_cabecera ON smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id = smn_compras.smn_requisicion_detalle.smn_requisicion_cabecera_id
    LEFT OUTER JOIN smn_compras.smn_lineas ON smn_compras.smn_lineas.smn_lineas_id = smn_compras.smn_requisicion_detalle.smn_linea_id
    LEFT OUTER JOIN smn_base.smn_contrato_base ON smn_base.smn_contrato_base.smn_contrato_base_id = smn_compras.smn_requisicion_detalle.smn_contrato_id
    LEFT OUTER JOIN smn_base.smn_item ON smn_base.smn_item.smn_item_id = smn_compras.smn_requisicion_detalle.smn_item_id
    LEFT OUTER JOIN smn_compras.smn_servicio ON smn_compras.smn_servicio.smn_servicio_id = smn_compras.smn_requisicion_detalle.smn_servicio_id
    LEFT OUTER JOIN smn_base.smn_activo_fijo ON smn_base.smn_activo_fijo.smn_afijo_id = smn_compras.smn_requisicion_detalle.smn_afijo_id 
WHERE
    smn_compras.smn_requisicion_detalle.smn_requisicion_cabecera_id = ${fld:id2}
ORDER BY
    rrs_fecha_registro DESC