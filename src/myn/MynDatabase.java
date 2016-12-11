package myn;
import myn.message;
import java.io.*;
import java.sql.*;
import java.util.*;
import jxl.write.*;
import jxl.*;



public class MynDatabase {
	//���ݿ����
	public String _usr;
	public String _psd;
	//���������
	Connection con;
	Statement stmt;
	int flag;
	public MynDatabase(String username,String password){
		_usr=username;
		_psd=password;
		flag=0;
	}
	/**
	 * ��ʼ������
	 * @return
	 */
	public boolean initDriver(){
		try{
			Class.forName("com.mysql.jdbc.Driver"); 
			String url="jdbc:mysql://localhost:3306/message?characterEncoding=utf8&useSSL=true";     
			con=DriverManager.getConnection(url,_usr,_psd);
			flag=1;
			return true;
		}
		catch(Exception e){
			System.out.println(e);
			return false;
		}
	}
	/**
	 * �ر�����
	 * @return
	 */
	public boolean close(){
		try{
			if(flag>=1)
				con.close();
			if(flag>=2)
				stmt.close();
			flag=0;
			return true;
		}
		catch(Exception e){
			System.out.println(e);
			return false;
		}
	}
	/**
	 * ����һ������
	 * @param a
	 * @return
	 */
	public boolean insert(message a){	
		try {
			if(flag>=1){
				stmt=con.createStatement();
				flag=2;
			}
			else return false;
		}
		catch (Exception e){
			System.out.println(e);
			return false;
		}		
		try {
			if(flag<2)
				return false;
			String sql = "INSERT INTO `list` (`from`, `to`, `msg`,`time`,`kind`) "
					+ "VALUES"+"(\""
					+a.getFrom()+"\", \""
					+a.getTo()+"\", \""
					+a.getMsg()+"\", \""
					+a.getTime()+"\", \""
					+a.getKind()+"\");";
			stmt.executeUpdate(sql);
			System.out.println("����message�ɹ�");
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}
	/**
	 * չʾ��������
	 */
	public void show(){
		try {
			if(flag>=1){
				stmt=con.createStatement();
				flag=2;
			}
			else return;
		}
		catch (Exception e){
			System.out.println(e);
			return;
		}		
		try {
			if(flag<2)
				return ;
			String sql = "SELECT * FROM `list`;";
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println("��ѯȫ��:");
			System.out.println("--------------------------------------------------------");
			int n =0;
			while(rs.next()){
				n++;
				message cur=new message(
						rs.getString("id"),rs.getString("from"),
						rs.getString("to"),rs.getString("msg"),
						rs.getString("kind"),rs.getString("time"));
				System.out.println(cur.toString());
			}
			System.out.println("��"+n+"��");
			System.out.println("--------------------------------------------------------");
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	/**
	 * ����ѡ������
	 * @param from
	 * @param to
	 * @return
	 */
	public boolean select(String from,String to){
		try {
			if(flag>=1){
				stmt=con.createStatement();
				flag=2;
			}
			else return false;
		}
		catch (Exception e){
			System.out.println(e);
			return false;
		}		
		try {
			if(flag<2)
				return false;
			String sql = "SELECT * FROM `list` WHERE `from` =\""+ from +"\" and `to` =\""+to+"\";";
			ResultSet rs = stmt.executeQuery(sql);
			if (!rs.next()){
				System.out.println("��ѯʧ��!�����ڸ���Ϣ");
				return false;
			}
			else{
				int n =0;
				System.out.println("��ѯ"+from+"��"+to+"����Ϣ:");
				System.out.println("--------------------------------------------------------");
				do{
					n++;
					message cur=new message(
							rs.getString("id"),rs.getString("from"),
							rs.getString("to"),rs.getString("msg"),
							rs.getString("kind"),rs.getString("time"));
					System.out.println(cur.toString());
				}while(rs.next());
				System.out.println("��"+n+"��");
				System.out.println("--------------------------------------------------------");
				
			}
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}
	/**
	 * ɾ��ѡ������
	 * @param id
	 * @return
	 */
	public boolean delete(int id){	
		try {
			if(flag>=1){
				stmt=con.createStatement();
				flag=2;
			}
			else return false;
		}
		catch (Exception e){
			System.out.println(e);
			return false;
		}		
		try {
			if(flag<2)
				return false;
			String sql = "SELECT * FROM `list` WHERE `id` ="+ id +";";
			ResultSet rs = stmt.executeQuery(sql);
			if (!rs.next()){
				System.out.println("ɾ��ʧ��!�����ڸ�����");
				return false;
			}	
			sql = "DELETE FROM `list` WHERE `id` = "+id+";";
			int result = stmt.executeUpdate(sql);
			System.out.println("ɾ����"+result+"������");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * �޸�ѡ������
	 * @param id
	 * @param newone
	 * @return
	 */
	public boolean edit(int id,message newone){	
		try {
			if(flag>=1){
				stmt=con.createStatement();
				flag=2;
			}
			else return false;
		}
		catch (Exception e){
			System.out.println(e);
			return false;
		}		
		try {
			if(flag<2)
				return false;
			this.delete(id);
			this.insert(newone);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * ɾ�����ݱ�
	 * @param id
	 * @param newone
	 * @return
	 */
	public boolean clear(){	
		try {
			if(flag>=1){
				stmt=con.createStatement();
				flag=2;
			}
			else return false;
		}
		catch (Exception e){
			System.out.println(e);
			return false;
		}		
		try {
			if(flag<2)
				return false;
			String sql = "TRUNCATE list;";
			stmt.executeUpdate(sql);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * �ڲ��ࣺ������Excel
	 * messagedb.xls
	 */
	public class TestDbToExcel {
				
	    public List<message> getAllByDb(){
	        List<message> list=new ArrayList<message>();
	        try {
	            String sql="select * from `list`;";
	            ResultSet rs= stmt.executeQuery(sql);
	            while (rs.next()) {
	            	message cur=new message(
							rs.getString("id"),rs.getString("from"),
							rs.getString("to"),rs.getString("msg"),
							rs.getString("kind"),rs.getString("time"));
	                list.add(cur);
	            }	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return list;
	    }
		
	    public void run() {
	        try {
	        	WritableWorkbook wwb = null;
	            // ������д���Excel������
	        	String fileName = "./messagedb.xls";
	        	File file=new File(fileName);
	        	if (!file.exists()) {
	        		file.createNewFile();
	        	}
	        	//��fileNameΪ�ļ���������һ��Workbook
	        	wwb = Workbook.createWorkbook(file);
	        	// ����������
	        	WritableSheet ws = wwb.createSheet("test 1", 0);
	        	//��ѯ���ݿ������е�����
	        	List<message> list= this.getAllByDb();
	        	//Ҫ���뵽��Excel�����кţ�Ĭ�ϴ�0��ʼ
	        	Label labelId= new Label(0, 0, "id(id)");
	        	Label labelFrom= new Label(1, 0, "���ͷ�(from)");
	        	Label labelTo= new Label(2, 0, "���շ�(to)");  
	        	Label labelMsg= new Label(3, 0, "����(meesage)");
	        	Label labelKind= new Label(4, 0, "����(kind)");
	        	Label labelTime= new Label(5, 0, "ʱ��(time)");
	        	ws.addCell(labelId);
	        	ws.addCell(labelFrom);
	        	ws.addCell(labelTo);
	        	ws.addCell(labelMsg);
	        	ws.addCell(labelKind);
	        	ws.addCell(labelTime);
	        	
	        	for (int i = 0; i < list.size(); i++) {
	                   Label labelId_i= new Label(0, i+1, list.get(i).getId()+"");
	                   Label labelFrom_i= new Label(1, i+1, list.get(i).getFrom()+"");
	   	        	   Label labelTo_i= new Label(2, i+1,list.get(i).getTo()+"");
	   	        	   Label labelMsg_i= new Label(3, i+1,list.get(i).getMsg()+"");
	   	        	   Label labelKind_i= new Label(4, i+1,list.get(i).getKind()+"");
	   	        	   Label labelTime_i= new Label(5, i+1, list.get(i).getTime()+"");
	   	        	   ws.addCell(labelId_i);
		        	   ws.addCell(labelFrom_i);
		        	   ws.addCell(labelTo_i);
		        	   ws.addCell(labelMsg_i);
		        	   ws.addCell(labelKind_i);
		        	   ws.addCell(labelTime_i);
	               }    
	              //д���ĵ�
	               wwb.write();
	              // �ر�Excel����������
	               wwb.close();      
	               System.out.println("������Excel�ɹ�,����Ϊmessagedb.xls");
	        } catch (Exception e) {
	            e.printStackTrace();
	        } 
	    }
	}
	
	/**
	 * �ڲ���  ��Excel �������ݿ�
	 * messagedb.xls
	 */
	public class TestExcelToDb {
		
	    public boolean getAllByExcel(String file){
	        try {
	            System.out.println("��ȡExcel��...");
	            Workbook rwb=Workbook.getWorkbook(new File(file));
	            Sheet rs=rwb.getSheet("test 1");
	            int clos=rs.getColumns();//�õ����е���
	            int rows=rs.getRows();//�õ����е���
	            
	            System.out.println(clos+" rows:"+rows);
	            for (int i = 1; i < rows; i++) {
	                for (int j = 0; j < clos; j++) {
	                    //��һ�����������ڶ���������
	                	message cur=new message(
	                			rs.getCell(j++, i).getContents(),
	                			rs.getCell(j++, i).getContents(),
	                			rs.getCell(j++, i).getContents(),
	                			rs.getCell(j++, i).getContents(),
	                			rs.getCell(j++, i).getContents(),
	                			rs.getCell(j++, i).getContents());
		               
	                    insert(cur);
	                }
	            }
	            System.out.println("��ȡ��Excel���");
	            return true;
	        } catch (Exception e) {
	            System.out.println("��ȡ��Excel��ʽ����ȷ! ��ȡ�ж�!");
	            System.out.println(e);
	            return false;
	        } 
	    }
		
	    public void run(String fileName) {
	        getAllByExcel(fileName);
	    }
	}

}
