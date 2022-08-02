select
		smn_compras.smn_tipo_documento.smn_tipo_documento_id,
	case
	when smn_compras.smn_tipo_documento.tdc_naturaleza='RE' then '${lbl:b_requisicion}'
	when smn_compras.smn_tipo_documento.tdc_naturaleza='CO' then '${lbl:b_cotizacion}'
	when smn_compras.smn_tipo_documento.tdc_naturaleza='OF' then '${lbl:b_oferta}'
	when smn_compras.smn_tipo_documento.tdc_naturaleza='OC' then '${lbl:b_orden_compra}'
	end as tdc_naturaleza,
	smn_compras.smn_tipo_documento.tdc_codigo,
	smn_compras.smn_tipo_documento.tdc_nombre,
	smn_compras.smn_tipo_documento.tdc_fecha_registro
	
from
	smn_compras.smn_tipo_documento
