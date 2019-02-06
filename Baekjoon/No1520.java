package Baekjoon;

import java.util.*; // ��ĳ�� ����

public class No1520 {

	private int height; // �� ����� ���� ���̰�
	private int way; // �ش� ���̱��� ������ �� �ִ� ���� ��
	private static No1520[][] map;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner sc = new Scanner(System.in);
		int vertical = 0, horizon = 0; // �¿� ����

		vertical = sc.nextInt(); // ���α���
		horizon = sc.nextInt(); // ���α���

		map = new No1520[vertical + 2][horizon + 2];
			// ���� �׵θ��� �� ����
		
		for(int k=0;k<vertical+2;k++) { //��� �ʿ� ���� �ʱ�ȭ
			for(int i=0;i<horizon+2;i++) {
				map[k][i]=new No1520();

			}
		}
		
		int input;
		for (int k = 1; k < vertical + 1; k++) { // ��� �ݺ����� ������
			for (int i = 1; i < horizon + 1; i++) {
				input = sc.nextInt(); // ���� �Է¹޾Ƽ�
				map[k][i] = new No1520(input); // ���� ����
			}
		}
		// *** �������
		
		System.out.println(Stair(vertical, horizon));	
		
	}

	public No1520() {
		height = -1;
		// ���� ����� ������������ ū ���� ���� �Ž��� �ö󰡴� ���
		// so, �ֺ� ���� �ڽź��� ���� ���� ���� ����
		// ���� ���� ���� �ּҰ��� -1�� ����
		way = 0;
	} // �Ϲ� ������

	public No1520(int input) {
		height = input; // ���� ����
		way = -1; // �����ϴ� ���� -1 �ʱ�ȭ ����
	}
	
	public static int Stair(int x,int y) { //����Լ� ȣ�⿹��
		int total = 0; //map[x][y]�� way��
		if(map[x][y].way != -1 ) { //������ ����� ���� �ִٸ� �� ���� ���
			return map[x][y].way;
		}
		else if(x ==1&& y==1) {
			return map[x][y].way = 1;
			
		}
		else { // ��갪�� ���� ������
			if(map[x][y].height < map[x-1][y].height) //���ʿ��� ������ �� �ִ°��
				total += Stair(x-1,y);
			if(map[x][y].height < map[x+1][y].height) //�����ʿ��� ������ �� �ִ°��
				total += Stair(x+1,y);
			if(map[x][y].height < map[x][y-1].height) //���ʿ��� ������ �� �ִ°��
				total += Stair(x,y-1);
			if(map[x][y].height < map[x][y+1].height) //�Ʒ��ʿ��� ������ �� �ִ°��
				total += Stair(x,y+1);
			
			map[x][y].way = total;
			return total;
		}
	}
}
