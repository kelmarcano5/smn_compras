select
	smn_compras.smn_req_detalle_impuesto.smn_req_detalle_impuesto_id,
	smn_compras.smn_req_detalle_impuesto.smn_requisicion_detalle_id,
	smn_compras.smn_req_detalle_impuesto.rim_monto_base,
	smn_base.smn_codigos_impuestos.imp_codigo||'-'||smn_base.smn_codigos_impuestos.imp_descripcion as smn_cod_impuesto_deduc_rf,
	smn_base.smn_codigos_impuestos.imp_porcentaje_base as smn_porcentaje_impuesto,
	smn_base.smn_codigos_impuestos.imp_monto_sustraendo as smn_sustraendo,
	smn_compras.smn_req_detalle_impuesto.rim_monto_impuesto,
	smn_compras.smn_req_detalle_impuesto.rim_fecha_registro
	
from
	smn_compras.smn_req_detalle_impuesto
	LEFT OUTER JOIN smn_base.smn_codigos_impuestos on smn_base.smn_codigos_impuestos.smn_codigos_impuestos_id = smn_compras.smn_req_detalle_impuesto.smn_cod_impuesto_deduc_rf
where
	smn_req_detalle_impuesto_id is not null
	${filter}
order by
		smn_req_detalle_impuesto_id
