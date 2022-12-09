select
	drc_porcentaje,
	${field}
from
	smn_base.smn_descuento,
	smn_compras.smn_req_detalle_dcto_retenc
where
	smn_base.smn_descuento.smn_descuento_id = smn_compras.smn_req_detalle_dcto_retenc.drc_porcentaje and
		smn_compras.smn_req_detalle_dcto_retenc.smn_req_detalle_dcto_retenc_id is not null
