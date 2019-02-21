package Baekjoon;

// 사실 해당 문제의 풀이법은 "이분매칭"을 사용하는 것 
// But, 나는 DAG를 사용하여 문제를 풀어보고자 하였으나 실패.

import java.util.*; //scanner 사용

public class No1671 {

	int size; // 상어의 크기
	int speed; // 상어의 속도
	int intell; // 상어의 지능
	boolean isLive; // 생존 여부

	int count; // 나를 잡아먹을 수 있는 상어의 수
	List<No1671> can_eat; // 내가 잡아먹을 수 있는 상어 목록

	static int total; // 전체 상어 수

	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in); //정보입력 스캐너
		
		total = sc.nextInt(); //상어의 마릿수를 입력받음
		No1671 list[] = new No1671[total];
		for(int i=0;i<total;i++) {
			list[i]=new No1671(sc);
		}
		// 배열 생성
		

		
		for(int i=0;i<total;i++) {
//			System.out.println("주체 : "+list[i]);
			for(int k=0;k<total;k++) {
//				System.out.println("대상 :"+list[k]);
				if(i != k) { // 상어가 자기 자신이 아니고
					if(list[i].size >= list[k].size // 크기가 크고
							&& list[i].speed >= list[k].speed //속도가 빠르고
							&& list[i].intell >= list[k].intell) { //지능도 높다면
						// i번째 상어는 K번째 상어를 잡아먹을 수 있음
//						System.out.println("잡아먹음");
						
						list[i].can_eat.add(list[k]); //i의 식단목록에 k 추가 (먹을 수 있음)
						list[k].count++; //나를 잡아먹을 수 있는 상어 수는 증가
					}
				}
			}
		} // 전체 상어에 대해 값 계산 끝 (먹이사슬 구성)
		
		No1671 save = null; //스왑을 위한 임시 데이터공간
		
		for(int i=0;i<total-1;i++) {
			for(int k=i+1;k<total;k++) {
				if(list[i].count > list[k].count) {
					// count 값을 오른차 순으로 정렬
					save = list[i];
					list[i] = list[k];
					list[k] = save; //스와핑
				}
			}
		}
		
		for(int i=0;i<total;i++) { //전체에 대해서
			
			int num = list[i].can_eat.size(); //현재까지 들어간 상어의 수만큼
			No1671 big[] = new No1671[2];
				//가장 큰 놈, 그 다음으로 큰 놈
			for(int k=0;k<num;k++) { // 먹을 수 있는 상어들 중,
				// count 값이 가장 작은 두 놈을 선택
				
				No1671 output = list[i].can_eat.get(k); //k번째 인덱스 값을 얻어옴
							
				if(!output.isLive) //만일 다른 상어에게 이미 먹혔다면
					continue; //넘김
				
				for(int l=0;l<2;l++) {
					if(big[l]!=null) { //비어있지 않다면
						if(big[l].count > output.count) {
							// 새로 뽑은 count가 기존 값보다 크다면
							save = big[l];
							big[l] = output;
							output = save;
						}
					}
					else { //비어있다면
						big[l] = output; //선택
						break; // 함수 종료
					}
				} //먹을 수 있는 놈들 중, 연결고리가 가장 적은 놈을 2놈 선택
				
				for(int l=0;l<2;l++) {
					if(big[l]!= null) //선택이 되었다면
						big[l].isLive = false; // 잡아먹음
				}
			}
		} //알고리즘 순회 끝
		
		
//		for(int i=0;i<total;i++) {
//			System.out.print(list[i].size+" : "+list[i].speed+" : "+list[i].intell+" : "+
//						list[i].count);
//			if(list[i].isLive)
//					System.out.println(" = 생존");
//			else
//				System.out.println();
//		}
			
		int answer = 0; //남은 상어 수
		for(int i=0;i<total;i++) {
			if(list[i].isLive) { //살아있다면
				answer++; //살아있음
			}
		}
	
		System.out.println(answer);
		
		sc.close(); //스캐너 반환
	}

	public No1671(Scanner sc) {
		this.size = sc.nextInt();
		this.speed = sc.nextInt();
		this.intell = sc.nextInt();

		isLive = true; // 아직은 안잡아먹힘
		count = 0; // 얘를 먹을 수 있는 놈
		can_eat = new ArrayList<No1671>();
	}
}
