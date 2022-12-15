UPDATE smn_compras.smn_oferta SET
	ofe_estatus = 'GE'
WHERE 
	smn_oferta_id = ${fld:id}
