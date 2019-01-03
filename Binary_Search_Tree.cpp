#include<iostream>
#include<time.h>

class Node { //각 트리를 구성하는 노드조각
public:
	int value; //보유 값
	Node* L; //왼쪽 주소
	Node* R; // 오른쪽 주소
	Node(int val); //생성자 (초기화)
	~Node(); //소멸자
};

class Binary_Tree { //전체 트리
public:
	Node * Root; //루트

	Binary_Tree(); //생성자
	~Binary_Tree(); //소멸자
	bool Search(int find, Node** get, Node** p); //탐색
	bool Insert(int push); //삽입
	bool Delete(int cut); //삭제

	void Show(Node* now);
	void Clear(Node* now); //메모리 정리용 함수
};

using namespace std;

int main() {
	Binary_Tree BST; //이진탐색트리 생성
	srand((unsigned)time(NULL));

	int count;
	cout << "추가할 횟수 : ";
	cin >> count;

	for (int i = 0; i < count; i++) {
		int check;
		cout << i + 1 << "번째 생성 값 : ";
		cin >> check;
		BST.Insert(check);
		BST.Show(BST.Root);
		cout << endl;
	}

	cout << "제거할 횟수 : ";
	cin >> count;

	for (int i = 0; i < count; i++) {
		int check;
		cout << i + 1 << "번째 삭제 값 : ";
		cin >> check;
		BST.Delete(check);
		BST.Show(BST.Root);
		cout << endl;
	}
	return 0;
}

Node::Node(int val):value(val){ //노드 초기화
	L = NULL;
	R = NULL;
}
Node::~Node(){}
Binary_Tree::Binary_Tree(){ //트리 초기화
	Root = NULL;
}
Binary_Tree::~Binary_Tree(){
	Clear(Root);
}
bool Binary_Tree::Search(int find, Node** get, Node** p){ //탐색
	Node* move = Root; //현재 위치
	Node* before = NULL; //이전에 있던 위치
	while (move != NULL) { //move가 이동 가능하면 계속
		if (move->value > find) { //찾으려는 값이 현재 노드보다 작은 경우
			before = move;
			move = move->L; //왼쪽으로 이동
		}
		else if(move->value < find){ //찾으려는 값이 현재 노드보다 큰 경우
			before = move;
			move = move->R; //오른쪽으로 이동
		}
		else { //찾은 경우
			*get = move; //찾은 위치를 저장
			*p = before;
			return true; //찾았다
		}
	}

	*get = move; //찾기 직전 위치를 저장
	*p = before;
	cout << "탐색 실패" << endl;
	return false; //실패
}
bool Binary_Tree::Insert(int push) { //삽입
	Node* place = NULL; //추가할 장소
	Node* parent = NULL;
	if (!Search(push, &place, &parent)) { //값을 찾아보고, 장소를 얻어옴
		//이때, search는 bool 값을 return 하므로, true 인 경우는 이미 존재, 삽입 불가

		Node* n_node = new Node(push); //추가하려는 값을 가진 노드 생성

		if (parent == NULL) { //만일 추가하려는 장소가 빈 공간이면
			// 처음이라는 의미이므로 root에 연결시킴
			Root = n_node; 
		}
		else { //연결된 위치가 있는 경우
			if (parent->value > push) { //추가하려는 값이 더 작은 경우, 왼쪽에 추가
				parent->L = n_node;
			}
			else { //큰 경우, 오른쪽에 추가
				parent->R = n_node;
			}
		}

		return true; //성공
	}
	else { //이미 존재하는 경우
		// 실패
		cout << "이미 존재하는 값입니다" << endl;
		return false;
	}
}
bool Binary_Tree::Delete(int cut){ //삭제
	Node* place = NULL; //추가할 장소
	Node* parent = NULL;
	if (Search(cut, &place, &parent)) { //값을 찾아보고, 장소를 얻어옴
								 //이때, search는 bool 값을 return 하므로, true 인 경우는 이미 존재, 삽입 불가
		bool isLeft; //삭제할 노드가 왼쪽인가?
		
		//가장 인접한 놈을 찾아서 그놈이랑 값을 바꾸는 과정 필요
		if (parent != NULL)
			if (parent->L == place) //부모의 왼쪽 값인 경우
				isLeft = true; //왼쪽값이다
			else //오른쪽 값인 경우
				isLeft = false;

		// 루트가 지워지는 경우를 고려할 것

		if (place->L == NULL && place->R == NULL) {
			//삭제할 노드가 자식이 없는 경우
			if (parent != NULL) //부모가 루트가 아닌 경우
				if (isLeft)
					parent->L = NULL; //삭제했기 때문에 내용을 제거
				else
					parent->R = NULL;
			else
				Root = NULL; //루트를 초기화
		}
		else if (place->L != NULL && place->R != NULL) {
			// 둘다 가진 경우
			
			Node* b_head = NULL; // 서브루트를 떼어내기 위해 저장할 부모 노드 정보
			Node* n_head = place->L; //새로운 서브루트가 될 녀석

			while (n_head->R != NULL) { //이동이 불가할때 까지 계속 이동
				b_head = n_head; // 갱신
				n_head = n_head->R; // 이동
			}
			
			if (parent != NULL)
				if (isLeft) //while 문을 통해 찾아온 끝 값을 대리루트로 붙이는 작업
					parent->L = n_head;
				else
					parent->R = n_head;
			else
				Root = n_head; // 지우는 놈이 루트인 경우, 루트로 갱신

			n_head->R = place->R; // 새 루트에, 기존의 반대편에 있던 놈을 붙임
			if (b_head != NULL) { //부모가 NULL이 아니면, 바로 다음 자식이 붙는게 아님 중간에 위치함
				b_head->R = n_head->L; //새로 정한 루트의 기존 길을 위쪽에 붙이고
				n_head->L = place->L; //기존 루트의 왼쪽을 새 루트에 연결	
			}
		}
		else { //둘중 하나만 가진 경우
			Node* link = NULL;//연결시킬 노드
			if (place->L != NULL) { //왼쪽이 연결된 경우
				link = place->L;
			}
			else { //오른쪽이 연결된 경우
				link = place->R;
			}
			if (parent != NULL)
				if (isLeft) //왼쪽에 연결
					parent->L = link;
				else
					parent->R = link; //오른쪽에 연결
			else
				Root = link; // 남은 놈을 루트로 연결

		}
		delete place; //삭제
		cout << "삭제 성공" << endl;
		return true; //성공
	}
	else { //이미 존재하는 경우
		   // 실패
		cout << "존재하지 않는 값입니다" << endl;
		return false;
	}
}
void Binary_Tree::Show(Node* now) {
	if (now != NULL) { //공백이 아니라면
		Show(now->L);
		cout << now->value << "\t";
		Show(now->R);
	} //infix 탐색
}
void Binary_Tree::Clear(Node* now) { //메모리 정리용 함수
	if (now != NULL) {
		Clear(now->L);
		Clear(now->R);
		delete now; //삭제
	}
}