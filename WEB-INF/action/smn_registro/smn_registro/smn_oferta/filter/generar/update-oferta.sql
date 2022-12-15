UPDATE smn_compras.smn_oferta SET
	ofe_estatus = 'GE'
WHERE
	smn_oferta_id = ${fld:smn_oferta_id}
	
RETURNING smn_oferta_id;