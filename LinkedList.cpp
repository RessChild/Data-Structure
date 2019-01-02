#include<iostream>
#include<time.h>

#define NODE_COUNT 15

class Node {
public:
	Node* next; //다음 노드를 가리키는 포인터
	int value; //값
	Node(int val);
};

class LinkedList {
public:
	Node* head;
	LinkedList(); //생성자
	~LinkedList(); //소멸자
	bool Insert(int val); //삽입
	bool Delete(int val); //삭제
};

using namespace std;

int main() {

	srand((unsigned)time(NULL));

	LinkedList list;
	for (int i = 0; i < NODE_COUNT; i++) {
		list.Insert(rand() % 50);
	}
	{
		Node* now = list.head; //현재 위치는 초기값이 헤드
		Node* front = NULL; //이전 정보는 일단 없음
		while (now != NULL) { //전부 본다

			cout << now->value << "\t";

			front = now; //지금까지 보던걸 맡기고
			now = now->next; //다음으로 넘어감
		}
		cout << endl;
	}

	{
		int num;
		for (int i = 0; i < NODE_COUNT; i++) {
			num = rand() % 50;
			cout << i << "번째 지울 값 : " << num << endl;
			list.Delete(num);
		}
	}

	{
		Node* now = list.head; //현재 위치는 초기값이 헤드
		Node* front = NULL; //이전 정보는 일단 없음
		while (now != NULL) { //전부 본다

			cout << now->value << "\t";

			front = now; //지금까지 보던걸 맡기고
			now = now->next; //다음으로 넘어감
		}
		cout << endl;
	}
}

Node::Node(int val) {
	this->value = val;
	next = NULL;
}

LinkedList::LinkedList() : head(NULL) { //인자값은 초기화
}

LinkedList::~LinkedList() {
	Node* erase; //삭제를 위해 선언한 erase 변수
	while (head != NULL) {
		erase = head; //대입
		head = head->next; //현재 헤드는 다음으로 이동
		delete erase; //노드 제거
	}
}
bool LinkedList::Insert(int val){
	Node* now = head; //현재 위치는 초기값이 헤드
	Node* front = NULL; //이전 정보는 일단 없음

	while (now != NULL) { //전부 본다
		
		if (now->value > val) { // 현재 값이 더 큰가?
			break;
		}
		else if (now->value == val) { //현재 노드의 값이 이미 있으면
			cout << val << " 값은 이미 존재" << endl;
			return false; //추가 실패
		}

		front = now; //지금까지 보던걸 맡기고
		now = now->next; //다음으로 넘어감
	}

	Node* new_n = new Node(val); //새롭게 노드 할당
	if (front != NULL) { //첫 헤드가 아니라면
		new_n->next = front->next; //기존에 있던 연결고리를 옮기고
		front->next = new_n; //그 뒤에 삽입
	}
	else {
		new_n->next = head; //기존 헤드가 밀려나고 
		head = new_n; // 헤드를 바꿈
	}

	return true; //성공
}
bool LinkedList::Delete(int val){
	bool has = false; //값을 갖고있는지 확인하는 변수
	Node* now = head; //현재 위치는 초기값이 헤드
	Node* front = NULL; //이전 정보는 일단 없음

	while (now != NULL) { //전부 본다

		if (now->value > val) { // 현재 값이 더 큰가?
			cout << val << " 값이 존재하지 않음" << endl;
			break;
		}
		else if (now->value == val) { //현재 노드의 값이 이미 있으면
			Node* erase; //삭제할 노드 주소
			if (front == NULL) { //가장 처음 값인 경우
				head = now->next; //헤드를 옮기고 삭제
				delete now;
			}
			else {
				front->next = now->next; //앞 노드와 뒤 노드를 연결 후 삭제
				delete now;
			}
			return true; //지우는데 성공
		}

		front = now; //지금까지 보던걸 맡기고
		now = now->next; //다음으로 넘어감
	}
	
	cout << "삭제 실패" << endl;
	return false; //실패
}