select
	smn_compras.smn_req_detalle_impuesto.smn_req_detalle_impuesto_id,
	smn_compras.smn_req_detalle_impuesto.smn_requisicion_detalle_id,
	smn_compras.smn_req_detalle_impuesto.rim_monto_base,
	smn_base.smn_codigos_impuestos.imp_codigo||'-'||smn_base.smn_codigos_impuestos.imp_descripcion as smn_cod_impuesto_deduc_rf,
	smn_base.smn_codigos_impuestos.imp_porcentaje_base as smn_porcentaje_impuesto,
	smn_base.smn_codigos_impuestos.imp_monto_sustraendo as smn_sustraendo,
	smn_compras.smn_req_detalle_impuesto.rim_monto_impuesto,
	smn_compras.smn_req_detalle_impuesto.rim_fecha_registro,
	smn_base.smn_monedas.mon_codigo||' - '||smn_base.smn_monedas.mon_nombre as smn_moneda_rf,
	ts.mon_codigo ||' - '|| ts.mon_nombre as smn_tasa_rf,
	smn_compras.smn_req_detalle_impuesto.rim_monto_imp_moneda_alterna
	
from
	smn_compras.smn_req_detalle_impuesto
	LEFT OUTER JOIN smn_base.smn_codigos_impuestos on smn_base.smn_codigos_impuestos.smn_codigos_impuestos_id = smn_compras.smn_req_detalle_impuesto.smn_cod_impuesto_deduc_rf
	left outer join smn_base.smn_monedas on smn_base.smn_monedas.smn_monedas_id = 	smn_compras.smn_req_detalle_impuesto.smn_moneda_rf
	left outer join smn_base.smn_tasas_de_cambio on smn_base.smn_tasas_de_cambio.smn_tasas_de_cambio_id = smn_base.smn_monedas.smn_monedas_id
		left outer join smn_base.smn_monedas ts on ts.smn_monedas_id = smn_base.smn_tasas_de_cambio.smn_monedas_id
where
	smn_req_detalle_impuesto_id = ${fld:id}
