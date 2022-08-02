select
	case
		when smn_compras.smn_tipo_documento.tdc_naturaleza='RE' then '${lbl:b_requisicion}'
		when smn_compras.smn_tipo_documento.tdc_naturaleza='CO' then '${lbl:b_cotizacion}'
		when smn_compras.smn_tipo_documento.tdc_naturaleza='OF' then '${lbl:b_oferta}'
		when smn_compras.smn_tipo_documento.tdc_naturaleza='OC' then '${lbl:b_orden_compra}'
	end as tdc_naturaleza,
	smn_compras.smn_tipo_documento.*
from 
	smn_compras.smn_tipo_documento
where
	smn_tipo_documento_id = ${fld:id}
