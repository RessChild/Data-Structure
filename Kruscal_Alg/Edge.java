package Kruscal_Alg;

public class Edge {
	public int weight; //���� weight
	public int[] link = new int[2];
	//������ ������ ������ �迭
	public boolean used = false; // ���� �������� ����
	
	public Edge() {}
	public Edge(int input, int n_num1, int n_num2) {
		weight = input;
		link[0] = n_num1;
		link[1] = n_num2;
	}
}
