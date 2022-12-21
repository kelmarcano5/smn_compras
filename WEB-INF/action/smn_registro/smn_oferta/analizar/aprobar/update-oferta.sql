UPDATE smn_compras.smn_oferta SET
	ofe_estatus = 'AP'
WHERE
	smn_oferta_id = ${fld:smn_oferta_id}
	
RETURNING smn_oferta_id;