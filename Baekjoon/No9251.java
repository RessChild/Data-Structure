package Baekjoon;

import java.util.*;

public class No9251 {

	public String A = null; // 문자열 A
	public String B = null; // 문자열 B
	public int[][] list = null;
	private int length_A;
	private int length_B;
	private int result = -1;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner sc = new Scanner(System.in); // 값 입력을 위한 스캐너

		No9251 answer = new No9251();

		answer.A = sc.nextLine();
		answer.B = sc.nextLine();
		// 문자열을 입력받음

		answer.Setting(); // 배열 생성 과정
		answer.LCS(0, 0, 0);
		answer.Show();

		sc.close(); // 스캐너 종료
	}

	public void Setting() { // 배열 초기화
		length_A = A.length();
		length_B = B.length(); // 두 문자의 길이를 따로 저장

		list = new int[length_A][length_B]; // 최대길이값을 저장한 변수
		for (int i = 0; i < length_A; i++) {
			for (int k = 0; k < length_B; k++) {
				list[i][k] = -1;
			}
		}
	}

	public void LCS(int a, int b, int count) {

		if (a >= length_A || b >= length_B) { // 끝자리에 도달한경우
			if (result < count)
				result = count;
			return;
		} // count 반환
			// 이때, 두 문자열의 길이가 다른 경우 인덱스 범위를 넘어설 가능성이 있으므로
			// 배열의 크기에 맞춰 크거나 같은 경우에 무조껀 return 시킴 (뒤로 빠꾸)
		else // a,b 모두 끝에 도달하지 않은 경우

		{

			if (list[a][b] > count) // 저장된 값이 현재까지의 계산값보다 큰 경우
				return; // 종료

			if (A.charAt(a) == B.charAt(b)) { // 만일 두 문자가 같다고 판명이 난 경우
				list[a][b] = count + 1; // 기존 길이를 대입
				// 다만, 끝에서 대각선 이동할 경우 배열범위를 벗어나게 되므로, 예외처리
				LCS(a + 1, b + 1, count + 1);

				// 대각선으로 간 경우 시도
			} else
				list[a][b] = count; // 기존 길이를 대입

			if (a != length_A) // 내려갈 공간이 있으면
				LCS(a + 1, b, count); // 아래로 내려간 경우 시도

			if (b != length_B) // 옆으로 갈 공간이 있으면
				LCS(a, b + 1, count); // 옆으로 간 경우 시도

			// 이때, 하단,오른쪽 이동의 경우 길이가 증가하지 않음. 따라서 고정.
			// 대각선 이동의 경우에만 count값 +1

			// 가장 큰 값을 선발하여 return 시킴
			return; // 종료
		}

	}

	public void Show() {
		System.out.println(result); // 실행 (깊이탐색)

//		for (int i = 0; i < length_A; i++) {
//			for (int k = 0; k < length_B; k++) {
//				System.out.print(list[i][k] + "  ");
//			}
//			System.out.print("\n");
//		}
	}

}
