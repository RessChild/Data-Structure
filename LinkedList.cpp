#include<iostream>
#include<time.h>

#define NODE_COUNT 15

class Node {
public:
	Node* next; //���� ��带 ����Ű�� ������
	int value; //��
	Node(int val);
};

class LinkedList {
public:
	Node* head;
	LinkedList(); //������
	~LinkedList(); //�Ҹ���
	bool Insert(int val); //����
	bool Delete(int val); //����
};

using namespace std;

int main() {

	srand((unsigned)time(NULL));

	LinkedList list;
	for (int i = 0; i < NODE_COUNT; i++) {
		list.Insert(rand() % 50);
	}
	{
		Node* now = list.head; //���� ��ġ�� �ʱⰪ�� ���
		Node* front = NULL; //���� ������ �ϴ� ����
		while (now != NULL) { //���� ����

			cout << now->value << "\t";

			front = now; //���ݱ��� ������ �ñ��
			now = now->next; //�������� �Ѿ
		}
		cout << endl;
	}

	{
		int num;
		for (int i = 0; i < NODE_COUNT; i++) {
			num = rand() % 50;
			cout << i << "��° ���� �� : " << num << endl;
			list.Delete(num);
		}
	}

	{
		Node* now = list.head; //���� ��ġ�� �ʱⰪ�� ���
		Node* front = NULL; //���� ������ �ϴ� ����
		while (now != NULL) { //���� ����

			cout << now->value << "\t";

			front = now; //���ݱ��� ������ �ñ��
			now = now->next; //�������� �Ѿ
		}
		cout << endl;
	}
}

Node::Node(int val) {
	this->value = val;
	next = NULL;
}

LinkedList::LinkedList() : head(NULL) { //���ڰ��� �ʱ�ȭ
}

LinkedList::~LinkedList() {
	Node* erase; //������ ���� ������ erase ����
	while (head != NULL) {
		erase = head; //����
		head = head->next; //���� ���� �������� �̵�
		delete erase; //��� ����
	}
}
bool LinkedList::Insert(int val){
	Node* now = head; //���� ��ġ�� �ʱⰪ�� ���
	Node* front = NULL; //���� ������ �ϴ� ����

	while (now != NULL) { //���� ����
		
		if (now->value > val) { // ���� ���� �� ū��?
			break;
		}
		else if (now->value == val) { //���� ����� ���� �̹� ������
			cout << val << " ���� �̹� ����" << endl;
			return false; //�߰� ����
		}

		front = now; //���ݱ��� ������ �ñ��
		now = now->next; //�������� �Ѿ
	}

	Node* new_n = new Node(val); //���Ӱ� ��� �Ҵ�
	if (front != NULL) { //ù ��尡 �ƴ϶��
		new_n->next = front->next; //������ �ִ� ������� �ű��
		front->next = new_n; //�� �ڿ� ����
	}
	else {
		new_n->next = head; //���� ��尡 �з����� 
		head = new_n; // ��带 �ٲ�
	}

	return true; //����
}
bool LinkedList::Delete(int val){
	bool has = false; //���� �����ִ��� Ȯ���ϴ� ����
	Node* now = head; //���� ��ġ�� �ʱⰪ�� ���
	Node* front = NULL; //���� ������ �ϴ� ����

	while (now != NULL) { //���� ����

		if (now->value > val) { // ���� ���� �� ū��?
			cout << val << " ���� �������� ����" << endl;
			break;
		}
		else if (now->value == val) { //���� ����� ���� �̹� ������
			Node* erase; //������ ��� �ּ�
			if (front == NULL) { //���� ó�� ���� ���
				head = now->next; //��带 �ű�� ����
				delete now;
			}
			else {
				front->next = now->next; //�� ���� �� ��带 ���� �� ����
				delete now;
			}
			return true; //����µ� ����
		}

		front = now; //���ݱ��� ������ �ñ��
		now = now->next; //�������� �Ѿ
	}
	
	cout << "���� ����" << endl;
	return false; //����
}