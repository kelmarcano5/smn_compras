if ('${fld:reference}' == 'true')
{
	//force close dialog
	setTimeout(function(){
		  	//alert("3");
			if ('${fld:reference}' == 'true')
			{
				//alert("4");
				parent.closeDialog();
				parent.setID('${fld:id}');
				document.getElementById('id2').value = '${fld:id}' ;
				//debugger;
				//alert('${fld:reference} - ${fld:source}')
				var cod = '${fld:id}';//document.getElementById("id2").value;
				//alert(cod);
				//alert('${fld:reference} - ${fld:source}')						
				var cod1 = document.form1.itm_codigo.value;	
				var nom = document.form1.itm_nombre.value;	
				nom1 = cod1 +'-'+ nom;	
				
				//alert('Código: ' + cod + ' - Nombre: ' + nom + "FUENTE: "+ '${fld:source}');
				if ('${fld:source}' == 'item')
				{
					//alert("5");
					parent.setValueComboBoxItem(cod,
											nom1,
											'smn_item_rf',
											true);
				}
					
			}
		 },1000);		
}
else
{
	addNew();
	alertBox ('${lbl:b_record_added}: ${fld:id}', '${lbl:b_continue_button}', null, 'parent.setFocusOnForm("form1");');
}

