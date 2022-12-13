INSERT INTO smn_compras.smn_cotizacion_f_entrega
(
   smn_cotizacion_f_entrega_id,
   smn_cotizacion_id,
   cfe_consecutivo,
   cfe_cantidad,
   cfe_fecha_entrega,
   cfe_status
)
VALUES
(
	nextval('smn_compras.seq_smn_cotizacion_f_entrega'), --smn_cotizacion_f_entrega_id
  	${fld:smn_cotizacion_id}, --smn_cotizacion_id
   	${fld:cfe_consecutivo}, --cfe_consecutivo
   	${fld:cfe_cantidad}, --cfe_cantidad
   	${fld:cfe_fecha_de_entrega}, --cfe_fecha_entrega
   	'RE' --cfe_status
)
Returning smn_cotizacion_f_entrega_id;