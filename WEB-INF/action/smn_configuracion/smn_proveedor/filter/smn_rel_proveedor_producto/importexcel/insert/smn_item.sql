SELECT 
	COALESCE( 
		(SELECT 
			smn_base.smn_item.smn_item_id  
		FROM   
			smn_base.smn_item
		WHERE  
			UPPER(TRIM(smn_base.smn_item.itm_nombre)) = UPPER(TRIM(${fld:smn_item_desc}))
	)) AS smn_item_ref	