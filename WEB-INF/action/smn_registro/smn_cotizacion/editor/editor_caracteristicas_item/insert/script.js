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
				var cod = document.getElementById("smn_item_rf").value;
				var combo = document.getElementById("smn_item_rf");
				var selected = combo.options[combo.selectedIndex].text;
				//alert(selected);
				//alert(cod);
				//alert('${fld:reference} - ${fld:source}')						
				//var cod1 = document.form1.itm_codigo.value;	
				//var nom = document.form1.itm_nombre.value;	
				//nom1 = cod1 +'-'+ nom;	
				
				//alert('Código: ' + cod + " FUENTE:"+'${fld:source1}');
				if ('${fld:source1}' == 'caracteristicas')
				{
					//alert("5");
					parent.setValueComboBoxItem2(cod,
											selected,
											'smn_item_rf',
											true);
				}
					
			}
		 },1000);	
}
else
{
	//alert('No referenced')
	addNew();
	alertBox ('${lbl:b_record_added}: ${fld:id}', '${lbl:b_continue_button}', null, 'parent.setFocusOnForm("form1");');
	//alertBox ('${lbl:b_record_added}: ${fld:id}', '${lbl:b_continue_button}', null, 'parent.search(); parent.setFocusOnForm("form1");');
}
