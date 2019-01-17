package Kruscal_Alg;

import java.util.*; // ��ĳ�� Ȱ��

public class Kruscal_Alg {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		// Ű�Է��� ���� ��ĳ�� ����

		System.out.print("��ü ��� �� : ");
		int node_n = sc.nextInt(); // ��� ���� �Է¹���
		// �ش� ��� ��ȣ�� 0���� n-1 ������ ������ ��

		if (node_n < 1) { // ��� �� �˻�
			System.out.println("<Error> ��� ���� 1�� �̻��̿��� �մϴ�");
			return; // ����
		}
		System.out.print("��ü ���� �� : ");
		int edge_n = sc.nextInt(); // ���� ���� �Է¹���

		if (edge_n < node_n - 1) { // ���� �� �˻�
			System.out.println("<Error> ���� ���� �����մϴ�.");
		}

		Edge[] edge = new Edge[edge_n];

		System.out.println("���� weight / ����ȣ 1 / ����ȣ 2");
		for (int i = 0; i < edge_n; i++) {
			System.out.print((i + 1) + "�� ° ���� ���� : ");
			int input = sc.nextInt();
			int n_num1 = sc.nextInt();
			int n_num2 = sc.nextInt();
			// Ű����� ���� ������ �Է¹���
			if (n_num1 < 0 || n_num1 >= node_n || n_num2 < 0 || n_num2 >= node_n) {
				// �Է¹��� ��� ��ȣ�� �ش� ���� ���ָ� �Ѿ�� �������� �ʴ� ���
				System.out.println("<Error> �ش� ���� �׷����� �������� �ʽ��ϴ�.");
				return; // �Լ� ����
			}
			edge[i] = new Edge(input, n_num1, n_num2); // ������ �迭�� �߰�
		} // �����߰� �ݺ��� ����

		Node.t_graph_num = node_n; // �ʹ� �׷��� ���� ��� ���� ���� (������)
		Node[] node = new Node[node_n]; // ��� ����ŭ �迭�� ���� (�����Ҵ�)
		for (int i = 0; i < node_n; i++)
			node[i] = new Node(i); // ������ ������ ������ �־� ���� ��ü ����

		for (int i = 0; i < edge_n; i++)
			for (int j = i; j < edge_n; j++) { // ������ ���� ���� �õ�
				if (edge[i].weight > edge[j].weight) { // �ڸ��ٲ��� ���� sorting �õ�
					Edge change = edge[j];
					edge[j] = edge[i];
					edge[i] = change; // ���� �Ϸ� (����)
				}
			} // sorting �ݺ��� ����

		for (int i = 0; i < edge_n; i++) { // ��� ������ �� ��������

			Node[] save = new Node[2]; // �ΰ��� ���� �Ҵ�
			for (int k = 0; k < 2; k++) {
				save[k] = node[edge[i].link[k]]; // ���� ����
				while (save[k].parent != null) { // ���� ���õ� ����� �θ� �Ž��� �ö󰣴�
					save[k] = save[k].parent; // �θ�� �̵�
				}
			} // 2���� ���� ����

			if (save[0].number < save[1].number) { // 0���� ���� ���,
				save[1].parent = save[0]; // ū �ʿ��� ������ ����
				Node.t_graph_num--; // �ΰ��� union�� ���������Ƿ� -1
				edge[i].used = true; // �ش� ������ ���Ǿ���
			} else if (save[0].number > save[1].number) { // �ݴ��� ���
				save[0].parent = save[1]; // �ݴ�� ����
				Node.t_graph_num--; // �ΰ��� union�� ���������Ƿ� -1
				edge[i].used = true; // �ش� ������ ���Ǿ���
			}
			// �� ���� ���� ���� �� ����� �������. ���� �ǹ� x

			if (Node.t_graph_num == 1) { // ��� ��尡 ������ 1����������
				System.out.println("��� �׷����� ���������ϴ�.");
				break; // �� (����)
			}
		} // ���� ��� �ݺ��� ����

		int total = 0;
		for (int i = 0; i < edge_n; i++) {
			if (edge[i].used) {
				System.out.println(edge[i].link[0] + " / " + edge[i].link[1] + " ��  " + edge[i].weight + " ������ ����");
				total += edge[i].weight; // ����
			}
		}
		System.out.println("��ü ���� �� �� : " + total);

		sc.close(); // ��ĳ�� �ݱ�
	}
}