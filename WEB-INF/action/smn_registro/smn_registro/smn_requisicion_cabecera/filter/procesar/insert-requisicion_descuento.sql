INSERT INTO smn_compras.smn_req_detalle_dcto_retenc
(
  smn_req_detalle_dcto_retenc_id,
  smn_requisicion_detalle_id,
  smn_codigo_descuento_rf,
  drc_monto_base,
  drc_porcentaje,
  drc_monto_descuento,
  drc_idioma,
  drc_usuario,
  drc_fecha_registro,
  drc_hora,
  drc_monto_descuento_ma
)
VALUES
(
	nextval('smn_compras.seq_smn_req_detalle_dcto_retenc'), --smn_req_detalle_dcto_retenc_id
  	${fld:req_detalle_id}, --smn_requisicion_detalle_id
  	${fld:smn_codigo_descuento_rf}, --smn_codigo_descuento_rf
  	${fld:drc_monto_base}, --drc_monto_base
 	${fld:drc_porcentaje}, --drc_porcentaje
  	${fld:drc_monto_descuento}, --drc_monto_descuento
  	'${def:locale}', --drc_idioma
    '${def:user}', --drc_usuario
    {d '${def:date}'}, --drc_fecha_registro
    '${def:time}', --drc_hora
	${fld:drc_monto_descuento_ma} --drc_monto_descuento_ma
)

RETURNING smn_req_detalle_dcto_retenc_id;