select	
	smn_base.smn_item.*
from
	smn_base.smn_item 
where
	smn_item_id is not null
	${filter}