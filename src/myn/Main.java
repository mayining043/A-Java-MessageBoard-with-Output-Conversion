package myn;
import java.util.Scanner;
import myn.MynDatabase.TestDbToExcel;
import myn.MynDatabase.TestExcelToDb;

/**
 * @author mayining
 * ������Ϣ����ϵͳ
 */

public class Main {

	public static void main(String[] args) {
		System.out.println("���Դ������ݿ�������...");
		MynDatabase temp = new MynDatabase("root","123");
		System.out.println("���ݿ����Ӵ����ɹ�!");
		if(!temp.initDriver()){
			System.out.println("�������ݿ�ʧ��!");
			System.exit(1);
		}
		System.out.println("���ݿ������ʼ���ɹ�!");
		System.out.println("��ӭ����������Ϣ����ϵͳ!");
		System.out.println("����˵��:");
		System.out.println("0: ��ѯȫ��");
		System.out.println("1: ���� from to msg kind ����һ����¼");
		System.out.println("2: ���� id ɾ��һ����¼");
		System.out.println("3: ���� from to 0��ѯһ����¼");
		System.out.println("4: ���ȫ��");
		System.out.println("5: ������excel���");
		System.out.println("6: ����excel�ļ�Ŀ¼���뵽���ݿ�");
		System.out.println("7: �˳�");
		System.out.println();
		while (true){
			try{
				Scanner sc = new Scanner(System.in);
				int in = sc.nextInt();
				switch (in){
				case 7:
					System.out.println("�������˳�");
					sc.close();
					System.exit(0);	
					break;
				case 0:
					temp.show();
					break;
				case 1:
					System.out.println("������4��Ҫ��������ݣ�ÿһ��Ϊ from to msg kind��\n");
					sc.nextLine();
					temp.insert(new message( sc.nextLine(),sc.nextLine(),
							sc.nextLine(),sc.nextLine()));
					break;
				case 2:
					temp.delete( sc.nextInt());
					break;
				case 3:
					temp.select( sc.next().trim(),sc.next().trim());
					break;
				case 4:
					if(temp.clear())
						System.out.println("��ճɹ���");
					else
						System.out.println("���ʧ�ܣ�");
					break;
				case 5:
					TestDbToExcel MysqltoExl = temp.new TestDbToExcel();
					MysqltoExl.run();
					break;
				case 6:
					TestExcelToDb ExltoMysql = temp.new TestExcelToDb();
					System.out.println("������Ҫ�����excel�ļ�����λ��ΪĬ��·������");
					String str=sc.next().trim();
					ExltoMysql.run(str);
					break;
				default:
					System.out.println("�����ʽ��������������");
				}
			}catch (Exception e){
				System.out.println("�����ʽ��������������");
			}
		}
	}
}
