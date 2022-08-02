package domain.repgen.smn_configuracion.smn_rel_proveedor_producto;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import dinamica.ImportExcel;

public class CustomImportExcel extends ImportExcel
{	
	@Override
	public InputStream getInputStream(File file) throws Throwable 
	{

		InputStream f = new FileInputStream(file); 
		InputStream xls = new domain.convert.ExcelConvertHelper().convertFromXLSXtoXLS(f);
		
		return xls;
	}
	
	 @Override
     public String[] getParamsNames() throws Throwable 
	 {
        String[] params = new String[] {"smn_proveedor_ref",
        		                        "smn_item_ref",
        		                        "rpp_codigo_proveedor",
        		                        "rpp_descripcion_proveedor",
        		                        "rpp_existencia_proveedor",
        		                        "rpp_precio_ml",
        		                        "rpp_precio_ma",
        		                        "smn_moneda_ref",
        		                        "rpp_fecha_valides"};
        return params;
     }
	 


}
