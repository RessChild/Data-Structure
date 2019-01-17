package Kruscal_Alg;

public class Node {

	public static int t_graph_num; // 전체 그래프의 총 수
	public int number = 0; //노드의 고유번호
	public Node parent = null;
		// 자신이 소속된 그래프 (트리)의 부모
		// 자신과 연결된 노드 번호 중, 가장 작은 번호를 저장
	
	public Node() {}
	public Node(int input) {
		number = input; //고유번호로 등록
	}
}
