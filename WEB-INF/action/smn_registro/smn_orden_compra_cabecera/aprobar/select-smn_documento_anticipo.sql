SELECT
	smn_pagos.smn_documento.smn_documento_id
FROM
	smn_pagos.smn_documento
INNER JOIN
	smn_compras.smn_documentos ON smn_pagos.smn_documento.smn_documentos_generales_rf = smn_compras.smn_documentos.dcc_transaccion_id
INNER JOIN
	smn_pagos.smn_tipo_documento ON smn_pagos.smn_tipo_documento.smn_tipo_documento_id = smn_pagos.smn_documento.smn_tipo_documento_id
WHERE
	smn_compras.smn_documentos.smn_documentos_id = ${fld:smn_documento_id}
AND
	smn_pagos.smn_tipo_documento.tdo_codigo = 'AN'