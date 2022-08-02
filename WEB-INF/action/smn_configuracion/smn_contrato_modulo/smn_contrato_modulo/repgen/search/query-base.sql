select
	smn_base.smn_direccion.dir_descripcion,
		smn_compras.smn_contrato_modulo.smn_contrato_modulo_id,
	${field}
from
	smn_base.smn_direccion,
	smn_compras.smn_contrato_modulo
where
	smn_base.smn_direccion.smn_direccion_id = smn_compras.smn_contrato_modulo.ctm_direccion_rf and
		smn_compras.smn_contrato_modulo.smn_contrato_modulo_id is not null
	${filter}
	
	
