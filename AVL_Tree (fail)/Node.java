package AVL_Tree;

public class Node {
	public Node P; //�θ�����
	public Node L = null, R= null;
	//AVL Ʈ���� ������ �¿� �ڽĳ�� ��
	public int label_L = 0, label_R = 0;
	// �� ��带 �������� �¿��� depth���� ������ int ����
	public int value; //�� ��尡 ������ ��	
	
	public Node() {}
	public Node(int input, Node parent) {
		value = input; //input ���� ����
		P = parent; //�θ� ���� ����
	}
}
