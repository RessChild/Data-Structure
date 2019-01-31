// LR ȸ���� ���, �ϴ� 2���� RR ȸ�� �� 3���� LL ȸ������ �ٶ� �� ����
// RL ȸ���� �ݴ�
// ���� ������ case, �����ϸ� ���Ŀ� �����غ� ����

package AVL_Tree;

import java.util.*;

public class AVL_Tree {
	private Node root = null;
	// ���۷�Ʈ�� ���� (null)

	private static Node recent = null;
	// ���� �������� ������ ��� ���� ���� (Insert���� ���)

	public static void main(String[] args) {
		AVL_Tree tree = new AVL_Tree();

		tree.Insert(5);
		tree.showStart();
		tree.Insert(7);
		tree.showStart();
		tree.Insert(9);
		tree.showStart();
		tree.Insert(1);
		tree.showStart();
		tree.Insert(2);
		tree.showStart();
		tree.Insert(5);
		tree.showStart();
		tree.Insert(10);
		tree.showStart();
		tree.Insert(-1);
		tree.showStart();
		tree.Insert(-10);
		tree.showStart();
		tree.Insert(-5);
		tree.showStart();
		tree.Insert(6);
		tree.showStart();
		tree.Insert(8);
		tree.showStart();
		
		tree.Delete(11);
		tree.showStart();
		tree.Delete(5);
		tree.showStart();
	}

	public boolean Insert(int value) {
		Node find = Search(value); // Ž�� �õ�

		if (find == null) { // �ǵ��� ���� ���� null�� ���
			// �ش� ���� �����Ƿ� �߰� ���� (recent ���)
			System.out.println(value + " �� ��� �߰�");

			Node make = new Node(value, recent); // �� ��带 ����
			Node save = recent; // L,R���� �����ϰ�
			Node before = make;

			Stack<Boolean> direction = new Stack<Boolean>();
			// true�� ����, false�� ������

			if (save != null) {
				if (save.value > value) { // �߰��� ����� ��Ұ��迡 ���� ��ġ ����
					save.L = make; // ���� �߰�
				} else {
					save.R = make; // ������ �߰�
				}
			} else {
				root = make; // ��Ʈ�� ����
				return true; // ��
			}

			while (save != null) {
				if (save.value > before.value) { // �߰��� ����� ��Ұ��迡 ���� depth�� ����
					save.label_L++; // ���� ����
					direction.add(true); // ���ʿ��� �ö����
				} else {
					save.label_R++; // ������ ����
					direction.add(false); // �����ʿ����ö����
				}
				int result = save.label_L - save.label_R;

				System.out.println(save.value + "�� �� ����(��,��,����) :" + save.label_L + "-" + save.label_R + "=" + result);
				if (result == 0)
					// ���� ���� �����ߴµ� ���� �������� ������ �� ���ٸ�
					break; // ������ �� �Ͼ���

				if (!(Math.abs(result) < 2)) {
					// �¿� ������ ���̰� 2�� �Ѵ´ٸ�
					// ȸ�� �Լ� ȣ�� �ʿ�
					Node first = save;
					Node second = null;
					Node third = null;

					int select = 0; // � ȸ������ ����
					if (direction.pop() == true) { // �����̸�
						second = save.L; // ���ʳ�� ���� ����
						save = save.L;
					} else {
						second = save.R; // ������ ��� ����
						save = save.R;
						select += 1;
					}
					if (direction.pop() == true) // �ι�°�� pop
					{
						third = save.L; // ���ʳ�� ���� ����
					} else {
						third = save.R; // ������ ��� ����
						select += 10;
					}

					System.out.println(first + "|" + second + "|" + third);
					System.out.print(first.value + "/" + second.value + "/");
					if(third != null)
						System.out.println(third.value);
					else
						System.out.println("X");

					// 0, 1, 10, 11. �� 4���� ����

					if (select == 0) { // LLȸ��
						// ��������ġ��ģ ���Ը� ������ �л�
						if (first.P != null) { // �θ� null�� �ƴѰ�� (first�� ��Ʈ�� �ƴѰ��)
							if (first.P.L == first) { // �θ��� ���ʿ� �޸����
								first.P.L = second; // �ڽĳ��� ����
							} else {
								first.P.R = second;
							}
						} else // first�� ��Ʈ�̸�
							root = second; // ��Ʈ ����

						second.P = first.P;
						first.P = second;
						// first�� second�� ������ ���̰�

						if (second.R != null) // �ڽ��� ������
							second.R.P = first;
						first.L = second.R;
						// second�� ���� �ڽ��� �ű�� �۾�����
						second.R = first; // ������

						// *** Label�� �����ϴ� �ܰ�

						second.label_R++;
						first.label_L -= 2;

						save = second;
						System.out.println("LLȸ��");
					} else if (select == 1) { // RLȸ��
						if (first.P != null) { // �θ� null�� �ƴѰ�� (first�� ��Ʈ�� �ƴѰ��)
							if (first.P.L == first) { // �θ��� ���ʿ� �޸����
								first.P.L = third; // �ڽĳ��� ����
							} else {
								first.P.R = third;
							}
						} else // first�� ��Ʈ�̸�
							root = third; // ��Ʈ ����

						third.P = first.P;
						first.P = third;
						second.P = third;
						// first, second�� third�� ������ ���̰�
						if (third.R != null)
							third.R.P = second; // third�� ������ second��
						if (third.L != null)
							third.L.P = first; // third�� �������� first�� ����
						second.L = third.R;
						first.R = third.L;
						// �� �ڽĵ��� ���ѱ�
						third.R = second;
						third.L = first; // ������

						// *** Label�� �����ϴ� �ܰ�

						first.label_R -= 3;
						third.label_R+=2;
						third.label_L+=2;

						save = third;

						System.out.println("RLȸ��");

					} else if (select == 10) { // LRȸ��
						// RL�� ��Ī
						// third�� ���ο� �߽����� ġ�� �ö���鼭 ���� �ڽ��� �¿�� �Ѹ�
						if (first.P != null) { // �θ� null�� �ƴѰ�� (first�� ��Ʈ�� �ƴѰ��)
							if (first.P.L == first) { // �θ��� ���ʿ� �޸����
								first.P.L = third; // �ڽĳ��� ����
							} else {
								first.P.R = third;
							}
						} else // first�� ��Ʈ�̸�
							root = third; // ��Ʈ ����

						third.P = first.P;
						first.P = third;
						second.P = third;
						// first, second�� third�� ������ ���̰�
						if (third.L != null)
							third.L.P = second; // third�� ������ second��
						if (third.R != null)
							third.R.P = first; // third�� �������� first�� ����
						second.R = third.L;
						first.L = third.R;
						// �� �ڽĵ��� ���ѱ�
						third.L = second;
						third.R = first; // ������

						// *** Label�� �����ϴ� �ܰ�

						first.label_L -= 3;
						third.label_L+=2;
						third.label_R+=2;

						save = third;

						System.out.println("LRȸ��");

					} else if (select == 11) { // RRȸ��
						// LL�� ��Ī
						if (first.P != null) { // �θ� null�� �ƴѰ�� (first�� ��Ʈ�� �ƴѰ��)
							if (first.P.L == first) { // �θ��� ���ʿ� �޸����
								first.P.L = second; // �ڽĳ��� ����
							} else {
								first.P.R = second;
							}
						} else // first�� ��Ʈ�̸�
							root = second; // ��Ʈ ����

						second.P = first.P;
						first.P = second;
						// first�� second�� ������ ���̰�
						if (second.L != null)
							second.L.P = first;
						first.R = second.L;
						// second�� ���� �ڽ��� �ű�� �۾�����
						second.L = first; // ������

						// *** Label�� �����ϴ� �ܰ�

						second.label_L++;
						first.label_R -= 2;

						save = second; // 2����尡 ���ο� �߽�

						System.out.println("RRȸ��");

					}

					return true; // ȸ���� ��Ű�� ������ ���������Ƿ� return true
				}

				before = save; // ���� ��ġ�� �����Ű��
				save = save.P; // �θ�� ��ĭ ���� �ö�
			}
			// �ش� ��带 ����, �߰��ϴ� ����

			return true;
		} else {
			System.out.println("<Error : Search> �ش� ���� �̹� �����մϴ�.");
			return false;
		}
	} // insert��

	public boolean Delete(int value) {
		Node find = Search(value); // Ž�� �õ�
		Node save = null; // ȸ���� ������ ���� ����
		Node before = null;
		
		if (find != null) { // �ǵ��� ���� ���� null�� �ƴ� ���
			// �ش� ���� ����, ��������
			System.out.println(value + " �� ��� ����");
			
			if (find.label_L == 0 && find.label_R == 0) {
				// �Ѵ� ���̰� 0�� ��� (��尡 ���°��)
				// �ٷ� ����
				if (find.P != null) { // �θ� �����ϸ�
					if (recent.value > find.value)
						recent.L = null; // ���ʿ� ���� ���� ����
					else
						recent.R = null; // �����ʿ� ���� �� ����
				}
				else
					root = null; //��Ʈ�� �������̹Ƿ� ����
				
			} else if (find.label_L == 0) { // ���ʸ� �� ���
				if (recent.value > find.value) { // �θ��� ���ʿ� ���� ���̸�
					recent.L = find.R; // �����ʿ� �������� ����
					find.P = recent; // �θ� ���� (����)
				} else {
					recent.R = find.R; // �����ʿ� �������� ����
					find.P = recent; // �θ� ���� (����)
				}
			} else if (find.label_R == 0) { // �����ʸ� �� ���
				if (recent.value > find.value) {
					recent.L = find.L; // �����ʿ� �������� ����
					find.P = recent; // �θ� ���� (����)
				} else {
					recent.R = find.L; // �����ʿ� �������� ����
					find.P = recent; // �θ� ���� (����)
				}
			} else { // �Ѵ� ��尡 �پ��ִ°��
				// ���ʳ��� �� parent�� ����õ�

				// ***
				Node b_head = null; // �����Ʈ�� ����� ���� ������ �θ� ��� ����
				Node n_head = find.L; // ���ο� �����Ʈ�� �� �༮
				Node parent = recent; // ���� �ֱ��� �θ� parent ������ ����

				while (n_head.R != null) { // �̵��� �Ұ��Ҷ� ���� ��� �̵�
					b_head = n_head; // ����
					n_head = n_head.R; // �̵�
				}
				
				if (parent != null)
					if (parent.value > n_head.value) // while ���� ���� ã�ƿ� �� ���� �븮��Ʈ�� ���̴� �۾�
						parent.L = n_head;
					else
						parent.R = n_head;
				else
					root = n_head; // ����� ���� ��Ʈ�� ���, ��Ʈ�� ����

				n_head.R = find.R; // �� ��Ʈ��, ������ �ݴ��� �ִ� ���� ����
				if (b_head != null) { // �θ� NULL�� �ƴϸ�, �ٷ� ���� �ڽ��� �ٴ°� �ƴ� �߰��� ��ġ��
					b_head.R = n_head.L; // ���� ���� ��Ʈ�� ���� ���� ���ʿ� ���̰�
					n_head.L = find.L; // ���� ��Ʈ�� ������ �� ��Ʈ�� ����
				}
				
				save = b_head.P;
				before = b_head;
			}
			// �ش� ��带 �����ϴ� ����

			Stack<Boolean> direction = new Stack<Boolean>();
			// true�� ����, false�� ������

			while (save != null) {
				if (save.value > before.value) { // �߰��� ����� ��Ұ��迡 ���� depth�� ����
					save.label_L--; // ���� ����
					direction.add(true); // ���ʿ��� �ö����
				} else {
					save.label_R--; // ������ ����
					direction.add(false); // �����ʿ����ö����
				}
				int result = save.label_L - save.label_R;

				System.out.println(save.value + "�� �� ����(��,��,����) :" + save.label_L + "-" + save.label_R + "=" + result);
				
				if (result == 0)
					// ���� ���� �����ߴµ� ���� �������� ������ �� ���ٸ�
					break; // ������ �� �Ͼ���

				if (!(Math.abs(result) < 2)) {
					// �¿� ������ ���̰� 2�� �Ѵ´ٸ�
					// ȸ�� �Լ� ȣ�� �ʿ�
					Node first = save;
					Node second = null;
					Node third = null;

					int select = 0; // � ȸ������ ����
					if (direction.pop() == true) { // �����̸�
						second = save.L; // ���ʳ�� ���� ����
						save = save.L;
					} else {
						second = save.R; // ������ ��� ����
						save = save.R;
						select += 1;
					}
					if (direction.pop() == true) // �ι�°�� pop
					{
						third = save.L; // ���ʳ�� ���� ����
					} else {
						third = save.R; // ������ ��� ����
						select += 10;
					}

					System.out.println(first + "|" + second + "|" + third);
					System.out.println(first.value + "/" + second.value + "/" + third.value);

					// 0, 1, 10, 11. �� 4���� ����

					if (select == 0) { // LLȸ��
						// ��������ġ��ģ ���Ը� ������ �л�
						if (first.P != null) { // �θ� null�� �ƴѰ�� (first�� ��Ʈ�� �ƴѰ��)
							if (first.P.L == first) { // �θ��� ���ʿ� �޸����
								first.P.L = second; // �ڽĳ��� ����
							} else {
								first.P.R = second;
							}
						} else // first�� ��Ʈ�̸�
							root = second; // ��Ʈ ����

						second.P = first.P;
						first.P = second;
						// first�� second�� ������ ���̰�

						if (second.R != null) // �ڽ��� ������
							second.R.P = first;
						first.L = second.R;
						// second�� ���� �ڽ��� �ű�� �۾�����
						second.R = first; // ������

						// *** Label�� �����ϴ� �ܰ�

						second.label_R++;
						first.label_L -= 2;

						save = second;
						System.out.println("LLȸ��");
					} else if (select == 1) { // RLȸ��
						if (first.P != null) { // �θ� null�� �ƴѰ�� (first�� ��Ʈ�� �ƴѰ��)
							if (first.P.L == first) { // �θ��� ���ʿ� �޸����
								first.P.L = third; // �ڽĳ��� ����
							} else {
								first.P.R = third;
							}
						} else // first�� ��Ʈ�̸�
							root = third; // ��Ʈ ����

						third.P = first.P;
						first.P = third;
						second.P = third;
						// first, second�� third�� ������ ���̰�
						if (third.R != null)
							third.R.P = second; // third�� ������ second��
						if (third.L != null)
							third.L.P = first; // third�� �������� first�� ����
						second.L = third.R;
						first.R = third.L;
						// �� �ڽĵ��� ���ѱ�
						third.R = second;
						third.L = first; // ������

						// *** Label�� �����ϴ� �ܰ�

						first.label_R -= 2;
						second.label_L--;
						third.label_R++;
						third.label_L++;

						save = third;

						System.out.println("RLȸ��");

					} else if (select == 10) { // LRȸ��
						// RL�� ��Ī
						// third�� ���ο� �߽����� ġ�� �ö���鼭 ���� �ڽ��� �¿�� �Ѹ�
						if (first.P != null) { // �θ� null�� �ƴѰ�� (first�� ��Ʈ�� �ƴѰ��)
							if (first.P.L == first) { // �θ��� ���ʿ� �޸����
								first.P.L = third; // �ڽĳ��� ����
							} else {
								first.P.R = third;
							}
						} else // first�� ��Ʈ�̸�
							root = third; // ��Ʈ ����

						third.P = first.P;
						first.P = third;
						second.P = third;
						// first, second�� third�� ������ ���̰�
						if (third.L != null)
							third.L.P = second; // third�� ������ second��
						if (third.R != null)
							third.R.P = first; // third�� �������� first�� ����
						second.R = third.L;
						first.L = third.R;
						// �� �ڽĵ��� ���ѱ�
						third.L = second;
						third.R = first; // ������

						// *** Label�� �����ϴ� �ܰ�

						first.label_L -= 2;
						second.label_R--;
						third.label_L++;
						third.label_R++;

						save = third;

						System.out.println("LRȸ��");

					} else if (select == 11) { // RRȸ��
						// LL�� ��Ī
						if (first.P != null) { // �θ� null�� �ƴѰ�� (first�� ��Ʈ�� �ƴѰ��)
							if (first.P.L == first) { // �θ��� ���ʿ� �޸����
								first.P.L = second; // �ڽĳ��� ����
							} else {
								first.P.R = second;
							}
						} else // first�� ��Ʈ�̸�
							root = second; // ��Ʈ ����

						second.P = first.P;
						first.P = second;
						// first�� second�� ������ ���̰�
						if (second.L != null)
							second.L.P = first;
						first.R = second.L;
						// second�� ���� �ڽ��� �ű�� �۾�����
						second.L = first; // ������

						// *** Label�� �����ϴ� �ܰ�

						second.label_L++;
						first.label_R -= 2;

						save = second; // 2����尡 ���ο� �߽�

						System.out.println("RRȸ��");

					}
					continue; // ȸ���� ��Ű�� ������ ���������Ƿ� return true
				}
			}
			
			// AVL Ʈ���� ��Ģ�� ���� ��带 �˻�, �����ϴ� ����
			return true;
		} else {
			System.out.println("<Error : Delete> �ش� ���� �������� �ʽ��ϴ�.");
			return false;
		}
	}

	public Node Search(int value) {

		Node find = root; // �ش� ���� ã������ �ӽú���
		recent = null; // �ֱ���ġ �ʱ�ȭ
		while (find != null) { // ������ ���� ������� ���� ����
			recent = find; // ���� �������� ������ ��ġ�� ����
			if (find.value > value) { // ���� ��ġ�� ���� ã������ ������ ū ���
				find = find.L; // �������� �̵�
			} else if (find.value < value) { // ���� ��ġ ���� �� ���� ���
				find = find.R; // ���������� �̵�
			} else { // ��� ���迡�� �� ���� ���� ���� ��� �� (ã��)
				break; // ���� �߰������Ƿ� break
				// ���� return find�� ���� ���� ��ȯ
			}
		} // root�� null�ΰ��� �ٷ� ��������

		return find; // Search �Լ� ����
	}

	public void show(Node p) {
		if (p != null) {
			System.out.println("[" + p.value + "]�� ���� �ڽ� " + p.label_L);
			show(p.L);
			System.out.println("[" + p.value + "]����");
			System.out.println("[" + p.value + "]�� ������ �ڽ� " + p.label_R);
			show(p.R);
		}
	}

	public void showStart() {
		System.out.println("-------");
		show(root);
		System.out.println("*******");
	}
}
