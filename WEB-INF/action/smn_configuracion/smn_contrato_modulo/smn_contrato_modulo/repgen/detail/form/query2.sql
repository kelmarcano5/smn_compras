select
		smn_compras.smn_contrato_modulo.smn_contrato_base_rf,
	smn_compras.smn_contrato_modulo.smn_documentos_id,
	smn_compras.smn_contrato_modulo.ctm_dia_factura,
	smn_compras.smn_contrato_modulo.ctm_cantidad,
	smn_compras.smn_contrato_modulo.ctm_precio,
	smn_compras.smn_contrato_modulo.ctm_monto,
	smn_compras.smn_contrato_modulo.ctm_porcentaje_incremento,
	smn_compras.smn_contrato_modulo.ctm_direccion_rf,
	smn_compras.smn_contrato_modulo.ctm_fecha_renovacion,
	smn_compras.smn_contrato_modulo.ctm_fecha_registro
from
	smn_compras.smn_contrato_modulo 
where
	smn_compras.smn_contrato_modulo.smn_contrato_modulo_id = ${fld:id}
