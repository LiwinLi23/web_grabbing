package my.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import my.function.mylog;
import my.function.pb;

public class db {
	
	
	public static boolean insert(Connection con,String stable,Map rmap)
	{
		String head="";
		String value="";
		for (Object o : rmap.keySet()) 
    	{
			 if (head.length()==0) 
			 {
				 head=o.toString(); 
				 value="?";
			 }
			 else
			 {
				 head=head+","+o.toString();
				 value=value=value+",?";
			 }	 
    	}
		
		String sSql="insert into "+stable+"("+head+")values("+value+")";
		
		PreparedStatement pstmt=null;
		try {
			pstmt = con.prepareStatement(sSql) ;
			int i=1;
			for (Object o : rmap.keySet()) 
	    	{
				pstmt.setObject(i,rmap.get(o));
				i++;
	    	}
			
			int t = pstmt.executeUpdate() ; // 执行更新 
			if (t>0) return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			if (pstmt!=null)
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return false;
		
	}
	
	public static int ExceSql(Connection con,String sSql)
	{
		
		mylog.info("db",sSql);
		Statement stmt=null;
		try {
			stmt = con.createStatement();
			return stmt.executeUpdate(sSql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			mylog.Err(sSql);
			mylog.Err(e.getMessage());
			return -1;
		} 
		finally
		{
			if (stmt != null) {  
                try {  
                	stmt.close();  
                } catch (SQLException sqlEx) {  
                    //throw new RuntimeException(sqlEx.toString());  
                	
                }  
            }
		}
	}
	
	public static int ExceSql(String sdatabase,String sSql)
	{
		//mylog.info("db",sdatabase+":"+sSql);
		Connection con=C3P0Factory.getC3P0Connection(sdatabase);
		if (con!=null)
		{
			Statement stmt=null;
			try {
				stmt = con.createStatement();
				return stmt.executeUpdate(sSql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				mylog.Err(sSql);
				mylog.Err(e.getMessage());
				return -1;
			} 
			finally
			{
				if (stmt != null) {  
	                try {  
	                	stmt.close();  
	                } catch (SQLException sqlEx) {  
	                    //throw new RuntimeException(sqlEx.toString());  
	                	
	                }  
	            }
				if (con!=null)
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
		else
		{
			return -1;
		}
	}
	
	public static ResultSet OpenSql(Connection con,String sSql)
	{
		mylog.info(sSql);
		Statement stmt=null;
		try {
			stmt = con.createStatement();
			return stmt.executeQuery(sSql);	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			mylog.Err(sSql);
			mylog.Err(e.getMessage());
			return null;
		} 
		/*finally
		{
			if (stmt != null) {  
                try {  
                	stmt.close();  
                } catch (SQLException sqlEx) {  
                    //throw new RuntimeException(sqlEx.toString());  
                }  
            }  
			
		}*/
	}
	
	//执行多条语句
	public static ResultSet ExceAndOpenSql(Connection con,String sSqls)
	{
		mylog.info("db",sSqls);
		try {
			Statement stmt = con.createStatement();
			stmt.execute(sSqls);	
			//stmt.addBatch(sSqls);
			//stmt.addBatch("SELECT LAST_INSERT_ID() as iCentreID");
			//stmt.executeBatch();
			stmt.getMoreResults();
			return stmt.getResultSet(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			mylog.Err("db",sSqls);
			mylog.Err("db",e.getMessage());
			return null;
		} 
	}
	
	//执行多条语句
	public static boolean ExceAnySql(Connection con,String sSqls)
	{
		mylog.info("db",sSqls);
		try {
			Statement stmt = con.createStatement();
			stmt.execute(sSqls);	
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			mylog.Err("db",sSqls);
			mylog.Err("db",e.getMessage());
			return false;
		} 			
	}
	
	//执行一条语句，返回记录集
	public static List OpenSql_toList(Connection con,String sSqls)
	{
		mylog.info("db",sSqls);
		List jarr=new ArrayList();;
		int irow=0;
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery(sSqls);
			ResultSet rs=stmt.getResultSet();
			
			ResultSetMetaData data=rs.getMetaData();
			while(rs.next())
			{
				int columnCount=data.getColumnCount();//获得所有列的数目及实际列数
				Map map_data=new HashMap();
				for(int i=1;i<=columnCount;i++)
				{
					//String columnName = data.getColumnName(i);//数据库中的实际标题
					String columnName = data.getColumnLabel(i);//建议标题
					String columnValue = rs.getString(i);
					
					int columnType=data.getColumnType(i);
					if (columnType==93) columnValue=pb.deletehm(columnValue);
					
					if (columnValue==null)
					{
						if (columnType==12) columnValue="";
					}
						
					map_data.put(columnName,columnValue);
				
				}
				jarr.add(map_data);
				irow++;
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			mylog.Err("db",sSqls);
			mylog.Err("db",e.getMessage());
			return null;
		} 
		return jarr;
	
	}
	
	//执行一条语句，返回记录集
	public static List OpenSql_toList(String sdatabase,String sSqls)
	{
		//mylog.info("db",sSqls);
		Connection con=C3P0Factory.getC3P0Connection(sdatabase);
		if (con!=null)
		{
			List jarr=new ArrayList();
			Statement stmt=null;
			ResultSet rs=null;
			int irow=0;
			try {
				stmt = con.createStatement();
				stmt.executeQuery(sSqls);
				 rs=stmt.getResultSet();
				
				ResultSetMetaData data=rs.getMetaData();
				while(rs.next())
				{
					int columnCount=data.getColumnCount();//获得所有列的数目及实际列数
					Map map_data=new HashMap();
					for(int i=1;i<=columnCount;i++)
					{
						//String columnName = data.getColumnName(i);//数据库中的实际标题
						String columnName = data.getColumnLabel(i);//建议标题
						String columnValue = rs.getString(i);
						
						int columnType=data.getColumnType(i);
						if (columnType==93) columnValue=pb.deletehm(columnValue);
						
						if (columnValue==null)
						{
							if (columnType==12) columnValue="";
						}
							
						map_data.put(columnName,columnValue);
					
					}
					jarr.add(map_data);
					irow++;
				 }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				mylog.Err("db",sSqls);
				mylog.Err("db",e.getMessage());
				return null;
			}
			finally
			{
				/*if (rs != null) {  
	                try {  
	                	rs.close();  
	                } catch (SQLException sqlEx) {  
	                    //throw new RuntimeException(sqlEx.toString());  
	                	sqlEx.printStackTrace();
	                	
	                }  
	            }
				if (stmt != null) {  
	                try {  
	                	stmt.close();  
	                } catch (SQLException sqlEx) {  
	                    //throw new RuntimeException(sqlEx.toString());  
	                	
	                }  
	            }*/
				if (con!=null)
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			return jarr;
			
		}
		else
		{
			return null;
		}

	}
	
	//执行一条语句，返回记录集
	public static Map OpenOneSql_toMap(Connection con,String sSql)
	{
		mylog.info("db",sSql);
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery(sSql);
			ResultSet rs=stmt.getResultSet();
			
			ResultSetMetaData data=rs.getMetaData();
			if(rs.next())
			{
				int columnCount=data.getColumnCount();//获得所有列的数目及实际列数
				Map map_data=new HashMap();
				for(int i=1;i<=columnCount;i++)
				{
					//String columnName = data.getColumnName(i);//数据库中的实际标题
					String columnName = data.getColumnLabel(i);//建议标题
					String columnValue = rs.getString(i);
					
					int columnType=data.getColumnType(i);
					if (columnType==93) columnValue=pb.deletehm(columnValue);
					
					if (columnValue==null)
					{
						if (columnType==12) columnValue="";
					}
					map_data.put(columnName,columnValue);
				}
				return map_data;
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			mylog.Err("db",sSql);
			mylog.Err("db",e.getMessage());
			return null;
		} 
		return null;
	
	}
	
	//执行一条语句，返回记录集
	public static Map OpenOneSql_toMap(String sdatabase,String sSql)
	{
		//mylog.info("db",sSql);
		Connection con=C3P0Factory.getC3P0Connection(sdatabase);
		if (con!=null)
		{
			Statement stmt=null;
			ResultSet rs=null;
			try {
				stmt = con.createStatement();
				stmt.executeQuery(sSql);
				rs=stmt.getResultSet();
				
				ResultSetMetaData data=rs.getMetaData();
				if(rs.next())
				{
					int columnCount=data.getColumnCount();//获得所有列的数目及实际列数
					Map map_data=new HashMap();
					for(int i=1;i<=columnCount;i++)
					{
						String columnName = data.getColumnLabel(i);//建议标题
						map_data.put(columnName,rs.getObject(i));
						
						//String columnName = data.getColumnName(i);//数据库中的实际标题
						/*String columnName = data.getColumnLabel(i);//建议标题
						String columnValue = rs.getString(i);
						int columnType=data.getColumnType(i);
						if (columnType==93) columnValue=pb.deletehm(columnValue);
						
						if (columnValue==null)
						{
							if (columnType==12) columnValue="";
						}
						map_data.put(columnName,columnValue);*/
					}
					return map_data;
				 }
				else
				{
					return null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				mylog.Err("db",sSql);
				mylog.Err("db",e.getMessage());
				return null;
			}
			finally
			{
				if (con!=null)
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			
		}
		else
		{
			return null;	
		}
	
	}
	
	//执行多条语句 要输入起始页
	public static Map ExceAndOpenSql_byPage(Connection con,String sSqls,String spageindex,String spagesize)
	{
		if (spageindex==null) spageindex="";
		if (spagesize==null) spagesize="";
		
		int ipageindex=1;
		int ipagesize=10;
		if ((spageindex.equals("")) || (spageindex.equals("0"))) 
			ipageindex=1;
		else
			ipageindex=pb.atoi(spageindex);
		if (spagesize.equals("")) ipagesize=10;
		else 
			ipagesize=pb.atoi(spagesize);
		
		if (ipageindex<=0) ipageindex=1;
		if (ipagesize<=0) ipagesize=10;
		
		int ilimit=(ipageindex-1)*ipagesize;
		
		sSqls=sSqls+" LIMIT "+ilimit+","+ipagesize+";";
		sSqls=sSqls+"SELECT FOUND_ROWS();";
		
		mylog.info("db",sSqls);
		
		Map map_out=new HashMap();
		int irow=0;
		try {
			Statement stmt = con.createStatement();
			stmt.execute(sSqls);
			
			ResultSet rs=stmt.getResultSet();
			
			List jarr=new ArrayList();
			try {
				ResultSetMetaData data=rs.getMetaData();
				while(rs.next())
				{
					int columnCount=data.getColumnCount();//获得所有列的数目及实际列数
					Map map_data=new HashMap();
					for(int i=1;i<=columnCount;i++)
					{
						//String columnName = data.getColumnName(i);//数据库中的实际标题
						String columnName = data.getColumnLabel(i);//建议标题
						String columnValue = rs.getString(i);
						
						int columnType=data.getColumnType(i);
						if (columnType==93) columnValue=pb.deletehm(columnValue);
						
						if (columnValue==null)
						{
							if (columnType==12) columnValue="";
						}
							
						map_data.put(columnName,columnValue);
					
					}
					jarr.add(map_data);
					irow++;
				 }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mylog.Err("db","getJsonObject:"+e.getMessage());
			}
		
			map_out.put("data", jarr);
			map_out.put("irow", irow);
			map_out.put("pagesize", ipagesize);
			map_out.put("pageindex", ipageindex);
			
			if(stmt.getMoreResults())
			{
				rs=stmt.getResultSet();
				if (rs!=null)
				{
					if (rs.next())
					{
						int recordcount=rs.getInt(1);
						map_out.put("recordcount", recordcount);
					}
				}	
			}	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			mylog.Err("db",sSqls);
			mylog.Err("db",e.getMessage());
			return null;
		
		} 
		return map_out;
	}
	
	
	

}
