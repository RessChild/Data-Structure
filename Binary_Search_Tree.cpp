#include<iostream>
#include<time.h>

class Node { //�� Ʈ���� �����ϴ� �������
public:
	int value; //���� ��
	Node* L; //���� �ּ�
	Node* R; // ������ �ּ�
	Node(int val); //������ (�ʱ�ȭ)
	~Node(); //�Ҹ���
};

class Binary_Tree { //��ü Ʈ��
public:
	Node * Root; //��Ʈ

	Binary_Tree(); //������
	~Binary_Tree(); //�Ҹ���
	bool Search(int find, Node** get, Node** p); //Ž��
	bool Insert(int push); //����
	bool Delete(int cut); //����

	void Show(Node* now);
	void Clear(Node* now); //�޸� ������ �Լ�
};

using namespace std;

int main() {
	Binary_Tree BST; //����Ž��Ʈ�� ����
	srand((unsigned)time(NULL));

	int count;
	cout << "�߰��� Ƚ�� : ";
	cin >> count;

	for (int i = 0; i < count; i++) {
		int check;
		cout << i + 1 << "��° ���� �� : ";
		cin >> check;
		BST.Insert(check);
		BST.Show(BST.Root);
		cout << endl;
	}

	cout << "������ Ƚ�� : ";
	cin >> count;

	for (int i = 0; i < count; i++) {
		int check;
		cout << i + 1 << "��° ���� �� : ";
		cin >> check;
		BST.Delete(check);
		BST.Show(BST.Root);
		cout << endl;
	}
	return 0;
}

Node::Node(int val):value(val){ //��� �ʱ�ȭ
	L = NULL;
	R = NULL;
}
Node::~Node(){}
Binary_Tree::Binary_Tree(){ //Ʈ�� �ʱ�ȭ
	Root = NULL;
}
Binary_Tree::~Binary_Tree(){
	Clear(Root);
}
bool Binary_Tree::Search(int find, Node** get, Node** p){ //Ž��
	Node* move = Root; //���� ��ġ
	Node* before = NULL; //������ �ִ� ��ġ
	while (move != NULL) { //move�� �̵� �����ϸ� ���
		if (move->value > find) { //ã������ ���� ���� ��庸�� ���� ���
			before = move;
			move = move->L; //�������� �̵�
		}
		else if(move->value < find){ //ã������ ���� ���� ��庸�� ū ���
			before = move;
			move = move->R; //���������� �̵�
		}
		else { //ã�� ���
			*get = move; //ã�� ��ġ�� ����
			*p = before;
			return true; //ã�Ҵ�
		}
	}

	*get = move; //ã�� ���� ��ġ�� ����
	*p = before;
	cout << "Ž�� ����" << endl;
	return false; //����
}
bool Binary_Tree::Insert(int push) { //����
	Node* place = NULL; //�߰��� ���
	Node* parent = NULL;
	if (!Search(push, &place, &parent)) { //���� ã�ƺ���, ��Ҹ� ����
		//�̶�, search�� bool ���� return �ϹǷ�, true �� ���� �̹� ����, ���� �Ұ�

		Node* n_node = new Node(push); //�߰��Ϸ��� ���� ���� ��� ����

		if (parent == NULL) { //���� �߰��Ϸ��� ��Ұ� �� �����̸�
			// ó���̶�� �ǹ��̹Ƿ� root�� �����Ŵ
			Root = n_node; 
		}
		else { //����� ��ġ�� �ִ� ���
			if (parent->value > push) { //�߰��Ϸ��� ���� �� ���� ���, ���ʿ� �߰�
				parent->L = n_node;
			}
			else { //ū ���, �����ʿ� �߰�
				parent->R = n_node;
			}
		}

		return true; //����
	}
	else { //�̹� �����ϴ� ���
		// ����
		cout << "�̹� �����ϴ� ���Դϴ�" << endl;
		return false;
	}
}
bool Binary_Tree::Delete(int cut){ //����
	Node* place = NULL; //�߰��� ���
	Node* parent = NULL;
	if (Search(cut, &place, &parent)) { //���� ã�ƺ���, ��Ҹ� ����
								 //�̶�, search�� bool ���� return �ϹǷ�, true �� ���� �̹� ����, ���� �Ұ�
		bool isLeft; //������ ��尡 �����ΰ�?
		
		//���� ������ ���� ã�Ƽ� �׳��̶� ���� �ٲٴ� ���� �ʿ�
		if (parent != NULL)
			if (parent->L == place) //�θ��� ���� ���� ���
				isLeft = true; //���ʰ��̴�
			else //������ ���� ���
				isLeft = false;

		// ��Ʈ�� �������� ��츦 ����� ��

		if (place->L == NULL && place->R == NULL) {
			//������ ��尡 �ڽ��� ���� ���
			if (parent != NULL) //�θ� ��Ʈ�� �ƴ� ���
				if (isLeft)
					parent->L = NULL; //�����߱� ������ ������ ����
				else
					parent->R = NULL;
			else
				Root = NULL; //��Ʈ�� �ʱ�ȭ
		}
		else if (place->L != NULL && place->R != NULL) {
			// �Ѵ� ���� ���
			
			Node* b_head = NULL; // �����Ʈ�� ����� ���� ������ �θ� ��� ����
			Node* n_head = place->L; //���ο� �����Ʈ�� �� �༮

			while (n_head->R != NULL) { //�̵��� �Ұ��Ҷ� ���� ��� �̵�
				b_head = n_head; // ����
				n_head = n_head->R; // �̵�
			}
			
			if (parent != NULL)
				if (isLeft) //while ���� ���� ã�ƿ� �� ���� �븮��Ʈ�� ���̴� �۾�
					parent->L = n_head;
				else
					parent->R = n_head;
			else
				Root = n_head; // ����� ���� ��Ʈ�� ���, ��Ʈ�� ����

			n_head->R = place->R; // �� ��Ʈ��, ������ �ݴ��� �ִ� ���� ����
			if (b_head != NULL) { //�θ� NULL�� �ƴϸ�, �ٷ� ���� �ڽ��� �ٴ°� �ƴ� �߰��� ��ġ��
				b_head->R = n_head->L; //���� ���� ��Ʈ�� ���� ���� ���ʿ� ���̰�
				n_head->L = place->L; //���� ��Ʈ�� ������ �� ��Ʈ�� ����	
			}
		}
		else { //���� �ϳ��� ���� ���
			Node* link = NULL;//�����ų ���
			if (place->L != NULL) { //������ ����� ���
				link = place->L;
			}
			else { //�������� ����� ���
				link = place->R;
			}
			if (parent != NULL)
				if (isLeft) //���ʿ� ����
					parent->L = link;
				else
					parent->R = link; //�����ʿ� ����
			else
				Root = link; // ���� ���� ��Ʈ�� ����

		}
		delete place; //����
		cout << "���� ����" << endl;
		return true; //����
	}
	else { //�̹� �����ϴ� ���
		   // ����
		cout << "�������� �ʴ� ���Դϴ�" << endl;
		return false;
	}
}
void Binary_Tree::Show(Node* now) {
	if (now != NULL) { //������ �ƴ϶��
		Show(now->L);
		cout << now->value << "\t";
		Show(now->R);
	} //infix Ž��
}
void Binary_Tree::Clear(Node* now) { //�޸� ������ �Լ�
	if (now != NULL) {
		Clear(now->L);
		Clear(now->R);
		delete now; //����
	}
}