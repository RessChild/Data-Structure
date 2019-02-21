package Baekjoon;

// ��� �ش� ������ Ǯ�̹��� "�̺и�Ī"�� ����ϴ� �� 
// But, ���� DAG�� ����Ͽ� ������ Ǯ����� �Ͽ����� ����.

import java.util.*; //scanner ���

public class No1671 {

	int size; // ����� ũ��
	int speed; // ����� �ӵ�
	int intell; // ����� ����
	boolean isLive; // ���� ����

	int count; // ���� ��Ƹ��� �� �ִ� ����� ��
	List<No1671> can_eat; // ���� ��Ƹ��� �� �ִ� ��� ���

	static int total; // ��ü ��� ��

	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in); //�����Է� ��ĳ��
		
		total = sc.nextInt(); //����� �������� �Է¹���
		No1671 list[] = new No1671[total];
		for(int i=0;i<total;i++) {
			list[i]=new No1671(sc);
		}
		// �迭 ����
		

		
		for(int i=0;i<total;i++) {
//			System.out.println("��ü : "+list[i]);
			for(int k=0;k<total;k++) {
//				System.out.println("��� :"+list[k]);
				if(i != k) { // �� �ڱ� �ڽ��� �ƴϰ�
					if(list[i].size >= list[k].size // ũ�Ⱑ ũ��
							&& list[i].speed >= list[k].speed //�ӵ��� ������
							&& list[i].intell >= list[k].intell) { //���ɵ� ���ٸ�
						// i��° ���� K��° �� ��Ƹ��� �� ����
//						System.out.println("��Ƹ���");
						
						list[i].can_eat.add(list[k]); //i�� �Ĵܸ�Ͽ� k �߰� (���� �� ����)
						list[k].count++; //���� ��Ƹ��� �� �ִ� ��� ���� ����
					}
				}
			}
		} // ��ü �� ���� �� ��� �� (���̻罽 ����)
		
		No1671 save = null; //������ ���� �ӽ� �����Ͱ���
		
		for(int i=0;i<total-1;i++) {
			for(int k=i+1;k<total;k++) {
				if(list[i].count > list[k].count) {
					// count ���� ������ ������ ����
					save = list[i];
					list[i] = list[k];
					list[k] = save; //������
				}
			}
		}
		
		for(int i=0;i<total;i++) { //��ü�� ���ؼ�
			
			int num = list[i].can_eat.size(); //������� �� ����� ����ŭ
			No1671 big[] = new No1671[2];
				//���� ū ��, �� �������� ū ��
			for(int k=0;k<num;k++) { // ���� �� �ִ� ���� ��,
				// count ���� ���� ���� �� ���� ����
				
				No1671 output = list[i].can_eat.get(k); //k��° �ε��� ���� ����
							
				if(!output.isLive) //���� �ٸ� ���� �̹� �����ٸ�
					continue; //�ѱ�
				
				for(int l=0;l<2;l++) {
					if(big[l]!=null) { //������� �ʴٸ�
						if(big[l].count > output.count) {
							// ���� ���� count�� ���� ������ ũ�ٸ�
							save = big[l];
							big[l] = output;
							output = save;
						}
					}
					else { //����ִٸ�
						big[l] = output; //����
						break; // �Լ� ����
					}
				} //���� �� �ִ� ��� ��, ������� ���� ���� ���� 2�� ����
				
				for(int l=0;l<2;l++) {
					if(big[l]!= null) //������ �Ǿ��ٸ�
						big[l].isLive = false; // ��Ƹ���
				}
			}
		} //�˰��� ��ȸ ��
		
		
//		for(int i=0;i<total;i++) {
//			System.out.print(list[i].size+" : "+list[i].speed+" : "+list[i].intell+" : "+
//						list[i].count);
//			if(list[i].isLive)
//					System.out.println(" = ����");
//			else
//				System.out.println();
//		}
			
		int answer = 0; //���� ��� ��
		for(int i=0;i<total;i++) {
			if(list[i].isLive) { //����ִٸ�
				answer++; //�������
			}
		}
	
		System.out.println(answer);
		
		sc.close(); //��ĳ�� ��ȯ
	}

	public No1671(Scanner sc) {
		this.size = sc.nextInt();
		this.speed = sc.nextInt();
		this.intell = sc.nextInt();

		isLive = true; // ������ ����Ƹ���
		count = 0; // �긦 ���� �� �ִ� ��
		can_eat = new ArrayList<No1671>();
	}
}
