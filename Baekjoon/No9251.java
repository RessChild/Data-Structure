package Baekjoon;

import java.util.*;

public class No9251 {

	public String A = null; // ���ڿ� A
	public String B = null; // ���ڿ� B
	public int[][] list = null;
	private int length_A;
	private int length_B;
	private int result = -1;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner sc = new Scanner(System.in); // �� �Է��� ���� ��ĳ��

		No9251 answer = new No9251();

		answer.A = sc.nextLine();
		answer.B = sc.nextLine();
		// ���ڿ��� �Է¹���

		answer.Setting(); // �迭 ���� ����
		answer.LCS(0, 0, 0);
		answer.Show();

		sc.close(); // ��ĳ�� ����
	}

	public void Setting() { // �迭 �ʱ�ȭ
		length_A = A.length();
		length_B = B.length(); // �� ������ ���̸� ���� ����

		list = new int[length_A][length_B]; // �ִ���̰��� ������ ����
		for (int i = 0; i < length_A; i++) {
			for (int k = 0; k < length_B; k++) {
				list[i][k] = -1;
			}
		}
	}

	public void LCS(int a, int b, int count) {

		if (a >= length_A || b >= length_B) { // ���ڸ��� �����Ѱ��
			if (result < count)
				result = count;
			return;
		} // count ��ȯ
			// �̶�, �� ���ڿ��� ���̰� �ٸ� ��� �ε��� ������ �Ѿ ���ɼ��� �����Ƿ�
			// �迭�� ũ�⿡ ���� ũ�ų� ���� ��쿡 ������ return ��Ŵ (�ڷ� ����)
		else // a,b ��� ���� �������� ���� ���

		{

			if (list[a][b] > count) // ����� ���� ��������� ��갪���� ū ���
				return; // ����

			if (A.charAt(a) == B.charAt(b)) { // ���� �� ���ڰ� ���ٰ� �Ǹ��� �� ���
				list[a][b] = count + 1; // ���� ���̸� ����
				// �ٸ�, ������ �밢�� �̵��� ��� �迭������ ����� �ǹǷ�, ����ó��
				LCS(a + 1, b + 1, count + 1);

				// �밢������ �� ��� �õ�
			} else
				list[a][b] = count; // ���� ���̸� ����

			if (a != length_A) // ������ ������ ������
				LCS(a + 1, b, count); // �Ʒ��� ������ ��� �õ�

			if (b != length_B) // ������ �� ������ ������
				LCS(a, b + 1, count); // ������ �� ��� �õ�

			// �̶�, �ϴ�,������ �̵��� ��� ���̰� �������� ����. ���� ����.
			// �밢�� �̵��� ��쿡�� count�� +1

			// ���� ū ���� �����Ͽ� return ��Ŵ
			return; // ����
		}

	}

	public void Show() {
		System.out.println(result); // ���� (����Ž��)

//		for (int i = 0; i < length_A; i++) {
//			for (int k = 0; k < length_B; k++) {
//				System.out.print(list[i][k] + "  ");
//			}
//			System.out.print("\n");
//		}
	}

}
