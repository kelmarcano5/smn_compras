select smn_compras.smn_tipo_documento.smn_tipo_documento_id as id, smn_compras.smn_tipo_documento.tdc_codigo||'-'||smn_compras.smn_tipo_documento.tdc_nombre as item from smn_compras.smn_tipo_documento

where smn_compras.smn_tipo_documento.tdc_naturaleza = 'RE' 