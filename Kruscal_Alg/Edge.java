package Kruscal_Alg;

public class Edge {
	public int weight; //엣지 weight
	public int[] link = new int[2];
	//연결노드 정보를 저장할 배열
	public boolean used = false; // 사용된 엣지인지 여부
	
	public Edge() {}
	public Edge(int input, int n_num1, int n_num2) {
		weight = input;
		link[0] = n_num1;
		link[1] = n_num2;
	}
}
