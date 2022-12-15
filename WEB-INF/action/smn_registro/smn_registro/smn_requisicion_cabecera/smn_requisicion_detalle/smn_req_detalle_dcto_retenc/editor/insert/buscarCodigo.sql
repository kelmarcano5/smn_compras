select
		smn_compras.smn_req_detalle_dcto_retenc.smn_codigo_descuento_rf
from
		smn_compras.smn_req_detalle_dcto_retenc
where
		smn_compras.smn_req_detalle_dcto_retenc.smn_codigo_descuento_rf = ${fld:smn_codigo_descuento_rf}
