select
		smn_compras.smn_tipo_documento.tdc_codigo
from
		smn_compras.smn_tipo_documento
where
		upper(smn_compras.smn_tipo_documento.tdc_codigo) = upper(${fld:tdc_codigo})
