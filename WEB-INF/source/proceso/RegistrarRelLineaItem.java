package proceso;

import dinamica.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.sql.DataSource;

public class RegistrarRelLineaItem extends GenericTransaction
{
	
	public int service(Recordset inputParams) throws Throwable
	{
		int rc = 1;	//variable a retornar.
		//String mensaje = ""; //mensaje a retornar
		
		String jndiName = (String)getContext().getAttribute("dinamica.security.datasource");
		
		if (jndiName==null)
			throw new Throwable("Context attribute [dinamica.security.datasource] is null, check your security filter configuration.");
		
		//establecer la conexion con la base de datos.
		
			DataSource ds = Jndi.getDataSource(jndiName); 
			Connection conn = ds.getConnection();
			this.setConnection(conn);
		
		//**
		
		String fechaActual= new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String sistemaOperativo = System.getProperty("os.name");
		String file;
		  
		if(sistemaOperativo.equals("Windows 7") || sistemaOperativo.equals("Windows 8") || sistemaOperativo.equals("Windows 10")) 
			file =  "C:/log/logRegistrarLineaItem_"+fechaActual+".txt";
		else
			file = "./logRequisiciones_"+fechaActual+".txt";
		
		File newLogFile = new File(file);
		FileWriter fw;
		String str="";
			
		if(!newLogFile.exists())
			fw = new FileWriter(newLogFile);
		else
			fw = new FileWriter(newLogFile,true);
			
		BufferedWriter bw=new BufferedWriter(fw);
			
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());	
			
		conn.setAutoCommit(false);
	
		try
		{
			str = "----------"+timestamp+"----------";	
			bw.write(str);
			bw.flush();
			bw.newLine();
			bw.newLine();
			
			Db db = getDb(); //objeto de conexion.
			
			String items[] = StringUtil.split(inputParams.getString("smn_item_id"), ";");
			
			str = "<Procesando items>"+items[0];	
			bw.write(str);
			bw.flush();
			bw.newLine();
			
			for(int p=0; p<items.length; p++)
			{
				inputParams.setValue("item_id", Integer.parseInt(items[p]));
				
				String sql = getSQL(getResource("insert-smn_rel_linea_item.sql"),inputParams); 
				db.exec(sql);
				
				rc = 0;
				
				str = "Se registro el item ID "+Integer.parseInt(items[p]);	
				bw.write(str);
				bw.flush();
				bw.newLine();
			}
		}
		catch(Throwable e)
		{
			bw.close();
			conn.rollback();
			throw e;
		}
		
		finally
		{		
			bw.close();
			if(rc==0)
				conn.commit();
			else
				conn.rollback();
			
			if(conn!=null)
				conn.close();
		}
		
		return rc;
	}
}