package Baekjoon;

import java.util.*; // 스캐너 생성

public class No1520 {

	private int height; // 각 블록이 가진 높이값
	private int way; // 해당 높이까지 도달할 수 있는 길의 수
	private static No1520[][] map;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner sc = new Scanner(System.in);
		int vertical = 0, horizon = 0; // 좌우 길이

		vertical = sc.nextInt(); // 세로길이
		horizon = sc.nextInt(); // 가로길이

		map = new No1520[vertical + 2][horizon + 2];
			// 맵의 테두리를 쓸 예정
		
		for(int k=0;k<vertical+2;k++) { //모든 맵에 대해 초기화
			for(int i=0;i<horizon+2;i++) {
				map[k][i]=new No1520();

			}
		}
		
		int input;
		for (int k = 1; k < vertical + 1; k++) { // 모든 반복문을 돌리며
			for (int i = 1; i < horizon + 1; i++) {
				input = sc.nextInt(); // 값을 입력받아서
				map[k][i] = new No1520(input); // 맵을 생성
			}
		}
		// *** 계산지점
		
		System.out.println(Stair(vertical, horizon));	
		
	}

	public No1520() {
		height = -1;
		// 현재 방식은 도착지점부터 큰 값을 따라 거슬러 올라가는 방식
		// so, 주변 벽중 자신보다 작은 값은 보지 않음
		// 쓰지 않을 벽은 최소값인 -1로 설정
		way = 0;
	} // 일반 생성자

	public No1520(int input) {
		height = input; // 값을 넣음
		way = -1; // 도달하는 길은 -1 초기화 기준
	}
	
	public static int Stair(int x,int y) { //재귀함수 호출예정
		int total = 0; //map[x][y]의 way값
		if(map[x][y].way != -1 ) { //이전에 계산한 적이 있다면 그 값을 사용
			return map[x][y].way;
		}
		else if(x ==1&& y==1) {
			return map[x][y].way = 1;
			
		}
		else { // 계산값이 아직 없으면
			if(map[x][y].height < map[x-1][y].height) //왼쪽에서 내려올 수 있는경우
				total += Stair(x-1,y);
			if(map[x][y].height < map[x+1][y].height) //오른쪽에서 내려올 수 있는경우
				total += Stair(x+1,y);
			if(map[x][y].height < map[x][y-1].height) //위쪽에서 내려올 수 있는경우
				total += Stair(x,y-1);
			if(map[x][y].height < map[x][y+1].height) //아래쪽에서 내려올 수 있는경우
				total += Stair(x,y+1);
			
			map[x][y].way = total;
			return total;
		}
	}
}
