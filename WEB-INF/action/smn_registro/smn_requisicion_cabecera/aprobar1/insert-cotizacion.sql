INSERT INTO smn_compras.smn_cotizacion
(
	smn_cotizacion_id, 
  	smn_requisicion_detalle_id, 
  	cot_secuencia, 
  	smn_item_id,
	smn_servicio_id,
	smn_activo_fijo_rf,
  	cot_fecha_envio, 
  	cot_fecha_requerido, 
  	cot_estatus, 
  	cot_idioma, 
  	cot_usuario, 
  	cot_fecha_registro, 
  	cot_hora, 
  	smn_documento_id, 
 	cot_numero_documento
)
VALUES
(
	nextval('smn_compras.seq_smn_cotizacion'),
	${fld:smn_requisicion_detalle_id},
	(SELECT COUNT(cot_secuencia)+1 FROM smn_compras.smn_cotizacion WHERE smn_requisicion_detalle_id = ${fld:smn_requisicion_detalle_id}),
	${fld:smn_item_id}, --item
	${fld:smn_servicio_id}, --servicio
	${fld:smn_afijo_id}, --activo fijo
	${fld:rrs_fecha_de_requerido},
	${fld:rrs_fecha_de_requerido},
	'RE',
	'${def:locale}',
    '${def:user}',
    {d '${def:date}'},
    '${def:time}',
    (SELECT smn_documento_id FROM smn_compras.smn_requisicion_cabecera WHERE smn_requisicion_cabecera_id = ${fld:smn_requisicion_cabecera_id}),
    (SELECT 
    	dcc_secuencia+1
     FROM 
     	smn_compras.smn_documentos 
     INNER JOIN
     	smn_compras.smn_requisicion_cabecera 
     ON
     	smn_compras.smn_documentos.smn_documentos_id = smn_compras.smn_requisicion_cabecera.smn_documento_id 
     WHERE 
     	smn_compras.smn_requisicion_cabecera.smn_requisicion_cabecera_id = ${fld:smn_requisicion_cabecera_id}
    )
)
RETURNING smn_cotizacion_id;