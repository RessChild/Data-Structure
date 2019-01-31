package AVL_Tree;

public class Node {
	public Node P; //부모정보
	public Node L = null, R= null;
	//AVL 트리를 구성할 좌우 자식노드 값
	public int label_L = 0, label_R = 0;
	// 각 노드를 기준으로 좌우의 depth값을 저장할 int 변수
	public int value; //각 노드가 소유할 값	
	
	public Node() {}
	public Node(int input, Node parent) {
		value = input; //input 값을 소유
		P = parent; //부모 정보 저장
	}
}
