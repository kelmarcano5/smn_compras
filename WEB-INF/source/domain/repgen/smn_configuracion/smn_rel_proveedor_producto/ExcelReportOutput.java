package domain.repgen.smn_configuracion.smn_rel_proveedor_producto;

import jxl.*;
import jxl.write.*;
import dinamica.*;
import dinamica.xml.Element;

public class ExcelReportOutput extends GenericExcelOutput
{
	@Override
	public void setColumns(Element[] cols, WritableSheet sheet,
			WritableCellFormat wcf, GenericTransaction data, int columnCount,
			int rowCount) throws Throwable 
	{
		
		super.setColumns(cols, sheet, wcf, data, columnCount, rowCount);

		CellView cellView = new CellView();
		cellView.setHidden(true); //set hidden
				
		sheet.setColumnView(9, cellView);
		sheet.setColumnView(10, cellView);
		sheet.setColumnView(11, cellView);
	}
}
