SELECT
	doc_secuencia+1 AS secuencia
FROM
	smn_pagos.smn_documento
WHERE
	smn_documento_id = ${fld:smn_documento_id_pago}