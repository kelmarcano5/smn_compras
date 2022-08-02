INSERT INTO smn_compras.smn_rel_requisicion_f_entrega
(
  smn_rel_requisicion_f_entrega_id,
  smn_requisicion_detalle_id,
  cfe_consecutivo,
  cfe_cantidad,
  cfe_fecha_de_entrega,
  cfe_idioma,
  cfe_usuario,
  cfe_fecha_registro,
  cfe_hora
)
VALUES
(
	nextval('smn_compras.seq_smn_rel_requisicion_f_entrega'),--smn_rel_requisicion_f_entrega_id
  	${fld:req_detalle_id}, --smn_requisicion_detalle_id
  	${fld:cfe_consecutivo}, --cfe_consecutivo
  	${fld:cfe_cantidad}, --cfe_cantidad
  	${fld:cfe_fecha_de_entrega}, --cfe_fecha_de_entrega
  	'${def:locale}', --cfe_idioma
    '${def:user}', --cfe_usuario
    {d '${def:date}'}, --cfe_fecha_registro
    '${def:time}' --cfe_hora
)

RETURNING smn_rel_requisicion_f_entrega_id;