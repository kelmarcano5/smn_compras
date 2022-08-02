select
		smn_compras.smn_tipo_documento.tdc_nombre
from
		smn_compras.smn_tipo_documento
where
		upper(smn_compras.smn_tipo_documento.tdc_nombre) = upper(${fld:tdc_nombre})
