/*
원래는 Node* 정보인 i_point와 o_point 정보를 수정해서 구현하려했으나
heap을 안정화 시키는 과정에서 정보가 너무 복잡하게 뒤섞여서
결국 stack을 사용하기로 함
*/

#include<iostream>
#include<time.h>

#define STACK_SIZE 256

using namespace std;

class Node { //각 트리를 구성하는 노드조각
public:
	int value; //보유 값
	Node* L; //왼쪽 주소
	Node* R; // 오른쪽 주소
	Node* parent; //자신의 부모 주소
	Node();
	Node(int val, Node* p); //생성자 (초기화)
	~Node(); //소멸자
};

class Heap { //전체 트리
private:
	Node * Root; //루트
	bool stack[STACK_SIZE]; //삽입위치, 마지막 추가위치를 확인할 stack정보
	int depth; //봐야할 stack의 최대 인덱스 값을 결정할 변수
public:
	Heap(); //생성자
	~Heap(); //소멸자
	void Push(int push); //삽입
	void Pop(); //삭제

	void Show();
	void Show(Node* now);
	void Clear();
	void Clear(Node* now); //메모리 정리용 함수
};

int main() {
	Heap priory_queue; //우선순위 큐
	int count; //입력 받을 수
	int num;

	cout << "생성할 노드 수 : ";
	cin >> count;
	for (int i = 0; i < count; i++) {
		cout << "값 : ";
		cin >> num; //수를 입력받음
		priory_queue.Push(num); //삽입

		priory_queue.Show(); //출력
		cout << endl;
	}

	cout << "제거할 노드 수 : ";
	cin >> count;
	for (int i = 0; i < count; i++) {
		priory_queue.Pop(); //삽입
		priory_queue.Show(); //출력
		cout << endl;
	}
}

Node::Node(){}
Node::Node(int val, Node* p) : value(val){
	L =	R = NULL;
	parent = p; // 부모노드를 갖고있는다
}
Node::~Node(){}
Heap::Heap() : depth(0) {
	Root = NULL;
	memset(stack, false, STACK_SIZE); //스택은 전부 false 로 초기화
}
Heap::~Heap(){}
void Heap::Push(int push) {
	
	Node* move = Root; //루트부터 방향성을 가지고 이동할 예정
	for (int i = 0; i < depth - 1; i++) {
		if (!stack[i]) { // stack의 해당 값이 false이면 왼쪽이동
			move = move->L;
		}
		else //true면 오른쪽 이동
			move = move->R;
	}
	// depth  -2 까지만 확인하는 이유는 depth-1 인덱스의 값이 빈 위치를 의미하기 때문

	Node* n_node = new Node(push, move); //현재의 위치 밑에 달릴 것이므로
	// 부모노드 = move

	// 값을 넣는 과정 (stack 정보까지 처리)
	if (move != NULL) { //처음 넣는 값이 아니라면
		if (!stack[depth - 1]) { //false값이라면
			move->L = n_node; //부모의 왼쪽에 들러붙고
			stack[depth - 1] = true; //오른쪽이 비었다고 변경
		}
		else { //true면
			move->R = n_node; //부모 오른쪽에 붙음

			for (int i = depth - 1; i >= 0; i--) {
				if (!stack[i]) { // false값이라면 해당 위치의 오른쪽이 비었단 이야기이므로, 이동, 내려감
					stack[i] = true; //오른쪽이동으로 변경 후 종료
					break;
				}
				else { // true 인 경우, 오른쪽까지 가득 찬 경우
					if (i == 0)  //루트인 경우
						depth++; // 해당 depth의 허용공간이 꽉 찼다는 것을 의미
						// so, 다음 depth의 자식으로 채우기 시작하고 모든 공간 초기화
					
					stack[i] = false; //false로 바꾸고 계속 올라감
				}
			}
		}
	}
	else { //처음이라면
		Root = n_node; //해당 노드를 루트로 지정
		depth++;
	}
	
	// Heap의 조건을 유지하도록 수정
	Node* change; //바꾸기 위한 임시저장
	while (move != NULL) { //기존에 있던 move를 부모노드로 재활용 할 예정 
		// move (부모) 가 null이 되면, 해당 n_node가 루트까지 거슬러 올라갔음을 의미
		if (move->value < n_node->value) { // 부모의 값이 새 노드값보다 작으면 (우선순위가 낮으면)
			//Heap 유지를 위해 부모와 n_node를 교환

			cout << n_node->value << "\t" << move->value << " 위치 수정" << endl;

			n_node->parent = move->parent; //과거 부모의 부모에 n_node 가 자식으로 붙음
			move->parent = n_node; // 그리고 새 노드의 밑으로 move가 붙음
								   // 부모 정보 교환 끝

			if (move->L == n_node) { //왼쪽에서 올라온 경우
				
				if(move->R != NULL)
					move->R->parent = n_node; //기존 반대편 노드의 부모를 새 노드로 변경
				
				move->L = n_node->L;
				n_node->L = move; //서로 갖고있던 왼쪽정보를 수정
				
				change = move->R; //오른쪽 정보는 서로 교환
				move->R = n_node->R;
				n_node->R = change;
			}
			else { //오른쪽에서 올라온 경우, 반대로 행동
				if(move->L != NULL)
					move->L->parent = n_node;

				move->R = n_node->R;
				n_node->R = move; //서로 갖고있던 오른쪽정보를 수정
				
				change = move->L; //왼쪽 정보는 서로 교환
				move->L = n_node->L;
				n_node->L = change;
			} // 자식 정보 교환 끝

			//두 값의 정보교환이 이루어졌으니, 그 상단, 하단 노드에서 값의 갱신이 필요
			if (move->L != NULL) //기존에 붙어있던 하단 자식에서 정보를 갱신
				move->L->parent = move; //변경
			if (move->R != NULL)
				move->R->parent = move;

			if (n_node->parent == NULL) { //더이상 올라갈 수 있는 부모가 없는 경우
				Root = n_node; //루트를 변경
				break; //더이상 볼 필요없음
			}

			// 기존에  move를 자식으로 가지던 부모의 정보 갱신
			// 위쪽 조건문에 의해 NULL인 상태는 다 걸러짐
			if (n_node->parent->L == move) { //이전 상태가 왼쪽과 연결된상태였다면
				n_node->parent->L = n_node;
			}
			else { //오른쪽과 연결된 상태였다면
				n_node->parent->R = n_node;
			}

			move = n_node->parent; //move가 가리키는 노드를 새 부모로 갱신
		}
		else { // Heap 조건이 깨지지 않는다면
			break; // 볼 필요가 없음 빠져나옴
		}
	}
}
void Heap::Pop(){
	//stack에서 과거 위치를 찾아가는 과정
	for (int i = depth - 1; i >= 0; i--) {
		// 가장 밑바닥부터 stack의 마지막 push 위치를 변경
		if (stack[i]) {//true 인 경우 (오른쪽에 차례 = 왼쪽이 차있음)
			stack[i] = false; //어차피 뺄 위치이므로 값 수정, 끝
			break;
		}
		else { //이전에 왼쪽에 넣어서 스택을 바꾼 상태라면
			stack[i] = true; //이전으로 되돌림

			if (i == 0) { //마지막 까지 거슬러 올라갔다면
				depth--; //depth값 1 감소
			}
		}
	}
	



	// 실제로 Root를 대체할 노드를 찾는과정
	Node* n_root = Root;
	if (n_root == NULL) { // 루트가 현재 NULL 이라면
		cout << "Heap 내에 Node가 존재하지 않습니다.";
		return;
	}
	for (int i = 0; i < depth; i++) {
		if (!stack[i]) // false 인경우, n_root는 왼쪽이동
			n_root = n_root->L;
		else
			n_root = n_root->R; // true 이면 오른쪽 이동
	}// 해당 루프가 끝난 후엔 n_node 값은 가장 마지막에 추가한 노드를 가리킴

		
	if (n_root->parent != NULL) { //덧붙일 놈이 존재하는 경우
		if (!stack[depth - 1]) //마지막에 이동한 방향에 따라서
			n_root->parent->L = NULL; // 빼낼 노드와의 연결을 끊어냄
		else
			n_root->parent->R = NULL;

		n_root->parent = NULL; //빼낼 노드의 부모정보도 삭제

		n_root->L = Root->L;
		n_root->R = Root->R; // 새로운 루트에 정보를 삽입

		if (Root->L != NULL) // 자식들의 부모정보도 갱신
			Root->L->parent = n_root;
		if (Root->R != NULL)
			Root->R->parent = n_root;
	}
	else {
		n_root = NULL;
	}

	cout << "Pop 값 : " << Root->value << endl;
	delete Root; //루트 제거
	Root = n_root; //새로운 루트를 연결
	// 루트를 Pop 시키고 새 루트를 연결까지 완성


	// Heap의 조건을 유지하도록 수정
	bool isLeft;
	Node* child = Root; // 루트와 위치를 바꿀 자식노드
	Node* change; //바꾸기 위한 임시저장
	while (Root != NULL) {// 위의 예외처리를 위함

		if (n_root->L != NULL && n_root->R != NULL) { //좌우 자식이 전부 NULL이 아니고
			if (n_root->L->value > n_root->R->value) { // 위로 올린 노드의 좌우 중, 우선순위가 큰놈을 pick
				child = n_root->L; //왼쪽 선택
				isLeft = true;
			}
			else {
				child = n_root->R; //오른쪽 픽
				isLeft = false;
			}
		}
		else if (n_root->L != NULL) {
			//그중 왼쪽자식이 있는 경우
			child = n_root->L; //왼쪽을pick
			isLeft = true;
		}
		else { //자식은 왼쪽, 오른쪽 순으로 들어가므로, 왼쪽이 없으면 오른쪽도 없음
			break; //가장 밑바닥이므로 끝. 볼필요 x
		}

		if (child->value < n_root->value) { // 아이 값이 자신의 위쪽보다 우선순위가 작은 경우
			break; // 바꿀필요 없음. 끝
		}
		else { // 우선순위가 큰 경우는 바꿔야함
			

			cout << n_root->value << "\t" << child->value << " 위치 수정" << endl;

			child->parent = n_root->parent; // 자식이 위로 올라오므로, 내가 갖고있던부모값을 대입
			n_root->parent = child; // 내 위가 자식으로 변환
			   // 부모 정보 교환 끝
			
			if (isLeft) { //왼쪽과 연결된 자식이었으면
				if (n_root->R != NULL)
					n_root->R->parent = child; //기존 반대편 노드의 부모를 새 노드로 변경

				n_root->L = child->L; //서로 갖고있던 왼쪽정보를 수정
				child->L = n_root;

				change = child->R; //오른쪽 정보는 서로 교환
				child->R = n_root->R;
				n_root->R = change;
			}
			else { //오른쪽에서 올라온 경우, 반대로 행동
				if (n_root->L != NULL)
					n_root->L->parent = child; //기존 반대편 노드의 부모를 새 노드로 변경

				n_root->R = child->R; //서로 갖고있던 오른쪽 정보 수정
				child->R = n_root;

				change = child->L; //왼쪽 정보는 서로 교환
				child->L = n_root->L;
				n_root->L = change;
			} // 자식 정보 교환 끝

			  //두 값의 정보교환이 이루어졌으니, 그 상단, 하단 노드에서 값의 갱신이 필요
			if (n_root->L != NULL) { //기존에 붙어있던 하단 자식에서 정보를 갱신
				n_root->L->parent = n_root; //변경
			}
			if (n_root->R != NULL) {
				n_root->R->parent = n_root;
			}

			// 기존에  n_root를 자식으로 가지던 부모의 정보 갱신
			if (child->parent == NULL)
				Root = child; // 이동시킨 자식이 루트로 바뀌면 루트정보 변환
			else { // 부모가 존재한다면
				if (child->parent->L == n_root) { //이전 상태가 왼쪽과 연결된상태였다면
					child->parent->L = child;
				}
				else { //오른쪽과 연결된 상태였다면
					child->parent->R = child;
				}
			}
		}
	}

}
void Heap::Show() { //private인 root 정보를 show로 넘겨주기 위함
	Show(Root);
}
void Heap::Show(Node* now){ // InFix 방식 탐색
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
		delete now; //메모리 정리
	}
}