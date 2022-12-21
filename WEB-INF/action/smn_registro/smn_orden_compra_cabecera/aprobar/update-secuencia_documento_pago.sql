UPDATE smn_pagos.smn_documento SET
	doc_secuencia = ${fld:secuencia}
WHERE
	smn_documento_id = ${fld:smn_documento_id_pago}

RETURNING smn_documento_id;