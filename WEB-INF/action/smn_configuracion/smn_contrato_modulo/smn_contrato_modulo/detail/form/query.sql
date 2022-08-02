select
	smn_base.smn_direccion.dir_descripcion,
		smn_compras.smn_contrato_modulo.smn_contrato_modulo_id,
	smn_base.smn_contrato_base.ctr_codigo||' - '||smn_base.smn_contrato_base.ctr_nombre as smn_contrato_base_rf,
	smn_compras.smn_documentos.dcc_codigo||' - '||smn_compras.smn_documentos.dcc_nombre as smn_documentos_id,
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
	left outer join smn_base.smn_direccion on smn_base.smn_direccion.smn_direccion_id = smn_compras.smn_contrato_modulo.ctm_direccion_rf
	left outer join smn_base.smn_contrato_base on smn_base.smn_contrato_base.smn_contrato_base_id = smn_compras.smn_contrato_modulo.smn_contrato_base_rf
	left outer join smn_compras.smn_documentos on smn_compras.smn_documentos.smn_documentos_id = smn_compras.smn_contrato_modulo.smn_documentos_id
where
	smn_contrato_modulo_id = ${fld:id}
