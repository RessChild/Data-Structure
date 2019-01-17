package Kruscal_Alg;

import java.util.*; // 스캐너 활용

public class Kruscal_Alg {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		// 키입력을 받을 스캐너 생성

		System.out.print("전체 노드 수 : ");
		int node_n = sc.nextInt(); // 노드 수를 입력받음
		// 해당 노드 번호는 0부터 n-1 까지가 차지할 것

		if (node_n < 1) { // 노드 수 검사
			System.out.println("<Error> 노드 수는 1개 이상이여야 합니다");
			return; // 종료
		}
		System.out.print("전체 엣지 수 : ");
		int edge_n = sc.nextInt(); // 엣지 수를 입력받음

		if (edge_n < node_n - 1) { // 엣지 수 검사
			System.out.println("<Error> 엣지 수가 부족합니다.");
		}

		Edge[] edge = new Edge[edge_n];

		System.out.println("엣지 weight / 노드번호 1 / 노드번호 2");
		for (int i = 0; i < edge_n; i++) {
			System.out.print((i + 1) + "번 째 엣지 정보 : ");
			int input = sc.nextInt();
			int n_num1 = sc.nextInt();
			int n_num2 = sc.nextInt();
			// 키보드로 값을 찬찬히 입력받음
			if (n_num1 < 0 || n_num1 >= node_n || n_num2 < 0 || n_num2 >= node_n) {
				// 입력받은 노드 번호가 해당 수의 범주를 넘어서면 존재하지 않는 노드
				System.out.println("<Error> 해당 노드는 그래프에 존재하지 않습니다.");
				return; // 함수 종료
			}
			edge[i] = new Edge(input, n_num1, n_num2); // 엣지를 배열에 추가
		} // 엣지추가 반복문 종료

		Node.t_graph_num = node_n; // 초반 그래프 수는 노드 수와 동일 (독립적)
		Node[] node = new Node[node_n]; // 노드 수만큼 배열을 생성 (공간할당)
		for (int i = 0; i < node_n; i++)
			node[i] = new Node(i); // 생성된 공간에 정보를 넣어 실제 객체 생성

		for (int i = 0; i < edge_n; i++)
			for (int j = i; j < edge_n; j++) { // 엣지에 대한 정렬 시도
				if (edge[i].weight > edge[j].weight) { // 자리바꿈을 통한 sorting 시도
					Edge change = edge[j];
					edge[j] = edge[i];
					edge[i] = change; // 변경 완료 (정렬)
				}
			} // sorting 반복문 종료

		for (int i = 0; i < edge_n; i++) { // 모든 엣지를 다 볼때까지

			Node[] save = new Node[2]; // 두개의 공간 할당
			for (int k = 0; k < 2; k++) {
				save[k] = node[edge[i].link[k]]; // 정보 저장
				while (save[k].parent != null) { // 현재 선택된 노드의 부모를 거슬러 올라간다
					save[k] = save[k].parent; // 부모로 이동
				}
			} // 2개의 정보 저장

			if (save[0].number < save[1].number) { // 0번이 작은 경우,
				save[1].parent = save[0]; // 큰 쪽에서 작은쪽 연결
				Node.t_graph_num--; // 두개의 union이 합쳐졌으므로 -1
				edge[i].used = true; // 해당 엣지가 사용되었음
			} else if (save[0].number > save[1].number) { // 반대의 경우
				save[0].parent = save[1]; // 반대로 연결
				Node.t_graph_num--; // 두개의 union이 합쳐졌으므로 -1
				edge[i].used = true; // 해당 엣지가 사용되었음
			}
			// 그 외의 남은 경우는 두 결과가 같은경우. 따라서 의미 x

			if (Node.t_graph_num == 1) { // 모든 노드가 합쳐져 1개만남으면
				System.out.println("모든 그래프가 합쳐졌습니다.");
				break; // 끝 (종료)
			}
		} // 엣지 사용 반복문 종료

		int total = 0;
		for (int i = 0; i < edge_n; i++) {
			if (edge[i].used) {
				System.out.println(edge[i].link[0] + " / " + edge[i].link[1] + " 를  " + edge[i].weight + " 값으로 연결");
				total += edge[i].weight; // 더함
			}
		}
		System.out.println("전체 엣지 총 합 : " + total);

		sc.close(); // 스캐너 닫기
	}
}