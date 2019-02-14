package Baekjoon;

import java.util.*;

public class No1463 {

	private static int list[] = null; // �̸�������̼��� ���� �迭 ����
	private static int input = 0;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		No1463 val = new No1463();
		
		Scanner sc = new Scanner(System.in); // Ű���� �Է�

		input = sc.nextInt(); // ���� �޾ƿ�
		list = new int[input]; // �Է¹��� ����ŭ �迭 �Ҵ�

		val.calculate(); //����Լ� ȣ��

		System.out.println(list[0]); // ��±���
		sc.close();
	}

	public void calculate() {

		for (int k = 0; k < input; k++) {
			list[k] = 400000; // �� �迭�� 400000���� �ʱ�ȭ
				//cause, ���� �� �ִ� �ִ밪�� 1000000 �̹Ƿ�, ���� �� �ִ� �ִ� �������
		}
		
		list[input-1] = 0; //����� ù ���� 0 (0ȸ �õ�)
		Divide_3(input);
		Divide_2(input);
		Minus_1(input);
		// � ��찡 ���� ������ ��
	}

	public void Divide_3(int tag) {

		if (tag == 1) // �̹� 1�� ����������
			return; // ����

		if (tag % 3 == 0) { // 3���� ������������
			int value = tag / 3; // ���ο� ���� input�� 3���� ���� ��

			if (list[value - 1] > list[tag - 1] + 1) { // ���� �������� ���� ���
				list[value - 1] = list[tag - 1] + 1; // ����Ʈ�� ���ο� ���Ƚ�� ����

				Divide_3(value);
				Divide_2(value);
				Minus_1(value);
				// ������ ��쿡 ���� ���ȣ��
			}
		}
	}

	public void Divide_2(int tag) {
		if (tag == 1) // �̹� 1�� ����������
			return; // ����

		if (tag % 2 == 0) { // 2�� �����������°�?
			int value = tag / 2;

			if (list[value - 1] > list[tag - 1] + 1) { // ���� �������� ���� ���
				list[value - 1] = list[tag - 1] + 1; // ����Ʈ�� ���ο� ���Ƚ�� ����

				Divide_3(value);
				Divide_2(value);
				Minus_1(value);
				// ������ ��쿡 ���� ���ȣ��
			}
		}
	}

	public void Minus_1(int tag) {
		
		if (tag == 1) // �̹� 1�� ����������
			return; // ����
		
		int value = tag - 1;

		if (list[value - 1] > list[tag - 1] + 1) { // ���� �������� ���� ���
			list[value - 1] = list[tag - 1] + 1; // ����Ʈ�� ���ο� ���Ƚ�� ����

			Divide_3(value);
			Divide_2(value);
			Minus_1(value);
			// ������ ��쿡 ���� ���ȣ��
		}
	}

}
