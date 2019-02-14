package Baekjoon;

import java.util.*;

public class No1463 {

	private static int list[] = null; // 미모아이제이션을 위한 배열 선언
	private static int input = 0;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		No1463 val = new No1463();
		
		Scanner sc = new Scanner(System.in); // 키보드 입력

		input = sc.nextInt(); // 값을 받아옴
		list = new int[input]; // 입력받은 값만큼 배열 할당

		val.calculate(); //계산함수 호출

		System.out.println(list[0]); // 출력구문
		sc.close();
	}

	public void calculate() {

		for (int k = 0; k < input; k++) {
			list[k] = 400000; // 각 배열을 400000으로 초기화
				//cause, 들어올 수 있는 최대값은 1000000 이므로, 나올 수 있는 최대 결과값임
		}
		
		list[input-1] = 0; //계산의 첫 값은 0 (0회 시도)
		Divide_3(input);
		Divide_2(input);
		Minus_1(input);
		// 어떤 경우가 가장 좋은지 모름
	}

	public void Divide_3(int tag) {

		if (tag == 1) // 이미 1에 도달했으면
			return; // 종료

		if (tag % 3 == 0) { // 3으로 나눠떨어지면
			int value = tag / 3; // 새로운 값은 input을 3으로 나눈 값

			if (list[value - 1] > list[tag - 1] + 1) { // 기존 계산수보다 작은 경우
				list[value - 1] = list[tag - 1] + 1; // 리스트에 새로운 계산횟수 저장

				Divide_3(value);
				Divide_2(value);
				Minus_1(value);
				// 각각에 경우에 대한 재귀호출
			}
		}
	}

	public void Divide_2(int tag) {
		if (tag == 1) // 이미 1에 도달했으면
			return; // 종료

		if (tag % 2 == 0) { // 2로 나눠떨어지는가?
			int value = tag / 2;

			if (list[value - 1] > list[tag - 1] + 1) { // 기존 계산수보다 작은 경우
				list[value - 1] = list[tag - 1] + 1; // 리스트에 새로운 계산횟수 저장

				Divide_3(value);
				Divide_2(value);
				Minus_1(value);
				// 각각에 경우에 대한 재귀호출
			}
		}
	}

	public void Minus_1(int tag) {
		
		if (tag == 1) // 이미 1에 도달했으면
			return; // 종료
		
		int value = tag - 1;

		if (list[value - 1] > list[tag - 1] + 1) { // 기존 계산수보다 작은 경우
			list[value - 1] = list[tag - 1] + 1; // 리스트에 새로운 계산횟수 저장

			Divide_3(value);
			Divide_2(value);
			Minus_1(value);
			// 각각에 경우에 대한 재귀호출
		}
	}

}
