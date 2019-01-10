/*
������ Node* ������ i_point�� o_point ������ �����ؼ� �����Ϸ�������
heap�� ����ȭ ��Ű�� �������� ������ �ʹ� �����ϰ� �ڼ�����
�ᱹ stack�� ����ϱ�� ��
*/

#include<iostream>
#include<time.h>

#define STACK_SIZE 256

using namespace std;

class Node { //�� Ʈ���� �����ϴ� �������
public:
	int value; //���� ��
	Node* L; //���� �ּ�
	Node* R; // ������ �ּ�
	Node* parent; //�ڽ��� �θ� �ּ�
	Node();
	Node(int val, Node* p); //������ (�ʱ�ȭ)
	~Node(); //�Ҹ���
};

class Heap { //��ü Ʈ��
private:
	Node * Root; //��Ʈ
	bool stack[STACK_SIZE]; //������ġ, ������ �߰���ġ�� Ȯ���� stack����
	int depth; //������ stack�� �ִ� �ε��� ���� ������ ����
public:
	Heap(); //������
	~Heap(); //�Ҹ���
	void Push(int push); //����
	void Pop(); //����

	void Show();
	void Show(Node* now);
	void Clear();
	void Clear(Node* now); //�޸� ������ �Լ�
};

int main() {
	Heap priory_queue; //�켱���� ť
	int count; //�Է� ���� ��
	int num;

	cout << "������ ��� �� : ";
	cin >> count;
	for (int i = 0; i < count; i++) {
		cout << "�� : ";
		cin >> num; //���� �Է¹���
		priory_queue.Push(num); //����

		priory_queue.Show(); //���
		cout << endl;
	}

	cout << "������ ��� �� : ";
	cin >> count;
	for (int i = 0; i < count; i++) {
		priory_queue.Pop(); //����
		priory_queue.Show(); //���
		cout << endl;
	}
}

Node::Node(){}
Node::Node(int val, Node* p) : value(val){
	L =	R = NULL;
	parent = p; // �θ��带 �����ִ´�
}
Node::~Node(){}
Heap::Heap() : depth(0) {
	Root = NULL;
	memset(stack, false, STACK_SIZE); //������ ���� false �� �ʱ�ȭ
}
Heap::~Heap(){}
void Heap::Push(int push) {
	
	Node* move = Root; //��Ʈ���� ���⼺�� ������ �̵��� ����
	for (int i = 0; i < depth - 1; i++) {
		if (!stack[i]) { // stack�� �ش� ���� false�̸� �����̵�
			move = move->L;
		}
		else //true�� ������ �̵�
			move = move->R;
	}
	// depth  -2 ������ Ȯ���ϴ� ������ depth-1 �ε����� ���� �� ��ġ�� �ǹ��ϱ� ����

	Node* n_node = new Node(push, move); //������ ��ġ �ؿ� �޸� ���̹Ƿ�
	// �θ��� = move

	// ���� �ִ� ���� (stack �������� ó��)
	if (move != NULL) { //ó�� �ִ� ���� �ƴ϶��
		if (!stack[depth - 1]) { //false���̶��
			move->L = n_node; //�θ��� ���ʿ� �鷯�ٰ�
			stack[depth - 1] = true; //�������� ����ٰ� ����
		}
		else { //true��
			move->R = n_node; //�θ� �����ʿ� ����

			for (int i = depth - 1; i >= 0; i--) {
				if (!stack[i]) { // false���̶�� �ش� ��ġ�� �������� ����� �̾߱��̹Ƿ�, �̵�, ������
					stack[i] = true; //�������̵����� ���� �� ����
					break;
				}
				else { // true �� ���, �����ʱ��� ���� �� ���
					if (i == 0)  //��Ʈ�� ���
						depth++; // �ش� depth�� �������� �� á�ٴ� ���� �ǹ�
						// so, ���� depth�� �ڽ����� ä��� �����ϰ� ��� ���� �ʱ�ȭ
					
					stack[i] = false; //false�� �ٲٰ� ��� �ö�
				}
			}
		}
	}
	else { //ó���̶��
		Root = n_node; //�ش� ��带 ��Ʈ�� ����
		depth++;
	}
	
	// Heap�� ������ �����ϵ��� ����
	Node* change; //�ٲٱ� ���� �ӽ�����
	while (move != NULL) { //������ �ִ� move�� �θ���� ��Ȱ�� �� ���� 
		// move (�θ�) �� null�� �Ǹ�, �ش� n_node�� ��Ʈ���� �Ž��� �ö����� �ǹ�
		if (move->value < n_node->value) { // �θ��� ���� �� ��尪���� ������ (�켱������ ������)
			//Heap ������ ���� �θ�� n_node�� ��ȯ

			cout << n_node->value << "\t" << move->value << " ��ġ ����" << endl;

			n_node->parent = move->parent; //���� �θ��� �θ� n_node �� �ڽ����� ����
			move->parent = n_node; // �׸��� �� ����� ������ move�� ����
								   // �θ� ���� ��ȯ ��

			if (move->L == n_node) { //���ʿ��� �ö�� ���
				
				if(move->R != NULL)
					move->R->parent = n_node; //���� �ݴ��� ����� �θ� �� ���� ����
				
				move->L = n_node->L;
				n_node->L = move; //���� �����ִ� ���������� ����
				
				change = move->R; //������ ������ ���� ��ȯ
				move->R = n_node->R;
				n_node->R = change;
			}
			else { //�����ʿ��� �ö�� ���, �ݴ�� �ൿ
				if(move->L != NULL)
					move->L->parent = n_node;

				move->R = n_node->R;
				n_node->R = move; //���� �����ִ� ������������ ����
				
				change = move->L; //���� ������ ���� ��ȯ
				move->L = n_node->L;
				n_node->L = change;
			} // �ڽ� ���� ��ȯ ��

			//�� ���� ������ȯ�� �̷��������, �� ���, �ϴ� ��忡�� ���� ������ �ʿ�
			if (move->L != NULL) //������ �پ��ִ� �ϴ� �ڽĿ��� ������ ����
				move->L->parent = move; //����
			if (move->R != NULL)
				move->R->parent = move;

			if (n_node->parent == NULL) { //���̻� �ö� �� �ִ� �θ� ���� ���
				Root = n_node; //��Ʈ�� ����
				break; //���̻� �� �ʿ����
			}

			// ������  move�� �ڽ����� ������ �θ��� ���� ����
			// ���� ���ǹ��� ���� NULL�� ���´� �� �ɷ���
			if (n_node->parent->L == move) { //���� ���°� ���ʰ� ����Ȼ��¿��ٸ�
				n_node->parent->L = n_node;
			}
			else { //�����ʰ� ����� ���¿��ٸ�
				n_node->parent->R = n_node;
			}

			move = n_node->parent; //move�� ����Ű�� ��带 �� �θ�� ����
		}
		else { // Heap ������ ������ �ʴ´ٸ�
			break; // �� �ʿ䰡 ���� ��������
		}
	}
}
void Heap::Pop(){
	//stack���� ���� ��ġ�� ã�ư��� ����
	for (int i = depth - 1; i >= 0; i--) {
		// ���� �عٴں��� stack�� ������ push ��ġ�� ����
		if (stack[i]) {//true �� ��� (�����ʿ� ���� = ������ ������)
			stack[i] = false; //������ �� ��ġ�̹Ƿ� �� ����, ��
			break;
		}
		else { //������ ���ʿ� �־ ������ �ٲ� ���¶��
			stack[i] = true; //�������� �ǵ���

			if (i == 0) { //������ ���� �Ž��� �ö󰬴ٸ�
				depth--; //depth�� 1 ����
			}
		}
	}
	



	// ������ Root�� ��ü�� ��带 ã�°���
	Node* n_root = Root;
	if (n_root == NULL) { // ��Ʈ�� ���� NULL �̶��
		cout << "Heap ���� Node�� �������� �ʽ��ϴ�.";
		return;
	}
	for (int i = 0; i < depth; i++) {
		if (!stack[i]) // false �ΰ��, n_root�� �����̵�
			n_root = n_root->L;
		else
			n_root = n_root->R; // true �̸� ������ �̵�
	}// �ش� ������ ���� �Ŀ� n_node ���� ���� �������� �߰��� ��带 ����Ŵ

		
	if (n_root->parent != NULL) { //������ ���� �����ϴ� ���
		if (!stack[depth - 1]) //�������� �̵��� ���⿡ ����
			n_root->parent->L = NULL; // ���� ������ ������ ���
		else
			n_root->parent->R = NULL;

		n_root->parent = NULL; //���� ����� �θ������� ����

		n_root->L = Root->L;
		n_root->R = Root->R; // ���ο� ��Ʈ�� ������ ����

		if (Root->L != NULL) // �ڽĵ��� �θ������� ����
			Root->L->parent = n_root;
		if (Root->R != NULL)
			Root->R->parent = n_root;
	}
	else {
		n_root = NULL;
	}

	cout << "Pop �� : " << Root->value << endl;
	delete Root; //��Ʈ ����
	Root = n_root; //���ο� ��Ʈ�� ����
	// ��Ʈ�� Pop ��Ű�� �� ��Ʈ�� ������� �ϼ�


	// Heap�� ������ �����ϵ��� ����
	bool isLeft;
	Node* child = Root; // ��Ʈ�� ��ġ�� �ٲ� �ڽĳ��
	Node* change; //�ٲٱ� ���� �ӽ�����
	while (Root != NULL) {// ���� ����ó���� ����

		if (n_root->L != NULL && n_root->R != NULL) { //�¿� �ڽ��� ���� NULL�� �ƴϰ�
			if (n_root->L->value > n_root->R->value) { // ���� �ø� ����� �¿� ��, �켱������ ū���� pick
				child = n_root->L; //���� ����
				isLeft = true;
			}
			else {
				child = n_root->R; //������ ��
				isLeft = false;
			}
		}
		else if (n_root->L != NULL) {
			//���� �����ڽ��� �ִ� ���
			child = n_root->L; //������pick
			isLeft = true;
		}
		else { //�ڽ��� ����, ������ ������ ���Ƿ�, ������ ������ �����ʵ� ����
			break; //���� �عٴ��̹Ƿ� ��. ���ʿ� x
		}

		if (child->value < n_root->value) { // ���� ���� �ڽ��� ���ʺ��� �켱������ ���� ���
			break; // �ٲ��ʿ� ����. ��
		}
		else { // �켱������ ū ���� �ٲ����
			

			cout << n_root->value << "\t" << child->value << " ��ġ ����" << endl;

			child->parent = n_root->parent; // �ڽ��� ���� �ö���Ƿ�, ���� �����ִ��θ��� ����
			n_root->parent = child; // �� ���� �ڽ����� ��ȯ
			   // �θ� ���� ��ȯ ��
			
			if (isLeft) { //���ʰ� ����� �ڽ��̾�����
				if (n_root->R != NULL)
					n_root->R->parent = child; //���� �ݴ��� ����� �θ� �� ���� ����

				n_root->L = child->L; //���� �����ִ� ���������� ����
				child->L = n_root;

				change = child->R; //������ ������ ���� ��ȯ
				child->R = n_root->R;
				n_root->R = change;
			}
			else { //�����ʿ��� �ö�� ���, �ݴ�� �ൿ
				if (n_root->L != NULL)
					n_root->L->parent = child; //���� �ݴ��� ����� �θ� �� ���� ����

				n_root->R = child->R; //���� �����ִ� ������ ���� ����
				child->R = n_root;

				change = child->L; //���� ������ ���� ��ȯ
				child->L = n_root->L;
				n_root->L = change;
			} // �ڽ� ���� ��ȯ ��

			  //�� ���� ������ȯ�� �̷��������, �� ���, �ϴ� ��忡�� ���� ������ �ʿ�
			if (n_root->L != NULL) { //������ �پ��ִ� �ϴ� �ڽĿ��� ������ ����
				n_root->L->parent = n_root; //����
			}
			if (n_root->R != NULL) {
				n_root->R->parent = n_root;
			}

			// ������  n_root�� �ڽ����� ������ �θ��� ���� ����
			if (child->parent == NULL)
				Root = child; // �̵���Ų �ڽ��� ��Ʈ�� �ٲ�� ��Ʈ���� ��ȯ
			else { // �θ� �����Ѵٸ�
				if (child->parent->L == n_root) { //���� ���°� ���ʰ� ����Ȼ��¿��ٸ�
					child->parent->L = child;
				}
				else { //�����ʰ� ����� ���¿��ٸ�
					child->parent->R = child;
				}
			}
		}
	}

}
void Heap::Show() { //private�� root ������ show�� �Ѱ��ֱ� ����
	Show(Root);
}
void Heap::Show(Node* now){ // InFix ��� Ž��
	if (now != NULL) {
		Show(now->L);
		cout << now->value << "\t";
		Show(now->R);
	}
}
void Heap::Clear() {
	Clear(Root);
}
void Heap::Clear(Node* now){
	if (now != NULL) {
		Clear(now->L);
		Clear(now->R);
		delete now; //�޸� ����
	}
}