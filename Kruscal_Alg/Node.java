package Kruscal_Alg;

public class Node {

	public static int t_graph_num; // ��ü �׷����� �� ��
	public int number = 0; //����� ������ȣ
	public Node parent = null;
		// �ڽ��� �Ҽӵ� �׷��� (Ʈ��)�� �θ�
		// �ڽŰ� ����� ��� ��ȣ ��, ���� ���� ��ȣ�� ����
	
	public Node() {}
	public Node(int input) {
		number = input; //������ȣ�� ���
	}
}
