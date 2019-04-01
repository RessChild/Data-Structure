#include<iostream>
#include<math.h> 

using namespace std;

class Node{
	public:
		int value[2]; //실제 값 
		int usedSpace; //현재 사용되는 공간 갯수 
		Node* child[3]; //자식 노드포인터 3개 
		Node(int v){
			for(int i=0;i<3;i++)
				this->child[i] = NULL;
				
			this->value[0] = v; // 새로 들어온 값을 넣어줌
			this->value[1] = v; //예외처리를 줄이기 위해 같은값을 넣어줌. 
			this->usedSpace = 1; //값이 하나 들어왔기 때문. 
		}
};
void Show(Node* n, int num);

class Tree_2_3{ //2-3트리 
	public:
		int num;
		Node** stack; //자신이 온 길(부모)을 알아야 하기 때문에 스택 사용
			// new로할당 하며, 할당된 값이 가질 값은 Node*이므로 더블포인터 
			//할당할 값은 log n 크기의 배열 
		Node* root; //루트 포인터. 
		Tree_2_3(){
			this->num = 0;
			this->root = NULL; //처음값 없음 
			this->stack = NULL;
		}
		~Tree_2_3(){
			Erase(this->root);
		}
		void Erase(Node* n);
		bool Search(int v,int& top); //탐색 
		void Insert(int v); //삽입 
};

int main(){

Tree_2_3 tree;


while(true){
	int add = 0;
	cout<<" 입력할 숫자 :";
	cin>>add;
	tree.Insert(add);
	
	Show(tree.root,0);
}



return 0;	
}

bool Tree_2_3::Search(int v,int& top){
		
	int n = log2(this->num); 
	
	if(!(n<1)){
	this->stack = new Node*[n]; //log2 num만큼의 스택을 생성 
	
	Node* path = this->root; //길의 시작은 root 
	
	while(path!=NULL){ //더이상 가지 못할 때 까지. 
				
			if(path->value[0] == v || path->value[1] == v) //현재 노드의 값과 찾는 값이 같으면 
			{
				return true; //찾았당 ㅎ 
				
			}
			else if(path->value[0] > v){ //작은 값보다 찾는 값이 더 작으면 
			 //왼쪽 길로 
			 stack[top++] = path; //스택에 현재 노드주소를 저장
			 path = path->child[0]; //왼쪽 child로 이동 
			} 
			else if(path->value[1] < v){ //큰 값보다 찾는 값이 더 크면 
				stack[top++] = path;
				path = path->child[2]; //오른쪽 자식으로 이동 
			}
			else{ //사이값이면 
				stack[top++] = path;
				path = path->child[1]; //사이로 이동 
			}
			
		//값이 하나이면, 0,1에 들어간 값이 동일하므로 사이값 존재 x
		//또한 크거나, 같거나 같은 역할을 하게됨. 
	}
}
	
	return false; //더이상 나아갈 곳이 없으면 존재x false를 return 시킴. 
}

void Tree_2_3::Insert(int v){
	int top =0 ; 
	
	if(!Search(v,top)) //찾지 못하면 값을 넣는다. 
	{
		
		if(this->root!=NULL){
		Node* pre_node = NULL;
			Node* node;
		if(stack == NULL)
			node = this->root;
		else
			node = stack[--top]; //부모로 돌아간다. 
		Node* new_link[2] = {NULL,NULL}; //새로 만들어질 링크 

		while(node!=NULL){			
		if(node->usedSpace == 1) //1칸만 쓰고있으면
		{
			if(node->value[0] < v ) //입력된값이 큰 경우 
				node->value[1] = v; //대입 
			else
				node->value[0] = v; //작은 경우 작은 공간에 대입 
	
	//		없어진 루트가 뭔지 파악해야함 
			if(pre_node !=NULL){ //리프가 아니면 
				Node* node_sort[3];
			
				if(node->child[0] == pre_node){
					node_sort[0] = new_link[0];
					node_sort[1] = new_link[1];
					node_sort[2] = node->child[2]; //0번 차일드에서 올라온경우 
				}
				else{
					node_sort[0] = node->child[0];
					node_sort[1] = new_link[0];
					node_sort[2] = new_link[1]; //1번 차일드에서 올라온 경우 
				}
				
				node->child[0]= node_sort[0];
				node->child[1]= node_sort[1];
				node->child[2]= node_sort[2]; //대입 
			}
	//
			node->usedSpace++; //쓰고 있는 공간 수를 늘림.
			node = NULL; 
		} 
		//어차피 child 가 없기 때문에 이건 생각 x 
		else{ //2칸을 쓰고 있는 경우 , 분할 

			int num_list[3] = {node->value[0],node->value[1],v}; //세 인자를 sort시키기 위함	
			
			for(int i=0;i<3;i++)
				for(int j=i;j<3;j++){
					if(num_list[i] > num_list[j]){ //비교값이 더 작으면 
						int save = num_list[i];
						num_list[i]= num_list[j];
						num_list[j]= save; //교환, 작은 값 순서로 sort 시킴 
					}
				}
			
			Node* new_node1 = new Node(num_list[0]); // 작은 값으로 노드생성 
			Node* new_node2 = new Node(num_list[2]); // 큰 값으로 노드를 생성 
			
			if(pre_node != NULL){ //리프가아니면, 새로생성한 노드에 주소값을 대입 	
	//		없어질 루트가 뭔지파악해야함. 
			Node* copy_child[4] = {node->child[0] ,node->child[2],new_link[0],new_link[1]}; 
			//기존 child 2개, 새로 끌고올라온 포인터 2개 
						
			for(int i=0,j=0;i<4;i++,j++){ //4개의 빈칸을 채우기 위함, 또한 3개의인덱스 비교 
				if(node->child[j] != pre_node){ //이전 노드의 주소와 해당 child의 주소가 다르면 
					copy_child[i] = node->child[j]; //그 child를 배열에 넣는다 
				}
				else{ //같으면 
					copy_child[i++] = new_link[0]; //새로운 노드 2개를 삽입한다. 
					copy_child[i]=new_link[1];
				}
			}
				new_node1->child[0] = copy_child[0];
				new_node1->child[2] = copy_child[1];
				new_node2->child[0] = copy_child[2];
				new_node2->child[2] = copy_child[3]; //정렬된 상태로 각각 child로 넣음 
			}
	//			
			new_link[0] = new_node1;
			new_link[1] = new_node2; //함께 가지고올라갈 새 주소의 포인터 
			
			pre_node=node;
			if(top>0){ //루트가 아닌경우	
				v = num_list[1]; //중앙 값을 다음에 넣어야 할 값으로 수정.
				node= stack[--top];  //부모로 올라감 
			}
			else { //루트이면 
				Node* new_root = new Node(num_list[1]); //중앙값으로 새 루트노드를 생성 
				new_root->child[0] = new_node1; //분할한 루트중 작은놈 
				new_root->child[2] = new_node2;  //분할한 루트 중 큰놈
				node = NULL;
				this->root= new_root;
			}
			delete pre_node;
		}
	}
	
		if(stack != NULL){
			delete[] stack;
			stack = NULL;
		}
		
		this->num++;
}
else{
Node* n = new Node(v);
this->root = n;
}
}
}

void Tree_2_3::Erase(Node* n){
	if(n!=NULL){
		Erase(n->child[0]);
		Erase(n->child[1]);
		Erase(n->child[2]);
		delete n;
	}
}
void Show(Node* n, int num){
	if(n!=NULL){
		num++;
	
		cout<<num<<"층 노드 "<<n->value[0]<<", "<<n->value[1]<<endl;
		Show(n->child[0],num);
		cout<<n->value[0]<<", "<<n->value[1]<<"왼쪽 child 탐색 끝"<<endl;
		Show(n->child[1],num);
		cout<<n->value[0]<<", "<<n->value[1]<<"중앙 child 탐색 끝"<<endl;
		Show(n->child[2],num);
		cout<<n->value[0]<<", "<<n->value[1]<<"오른쪽 child 탐색 끝"<<endl; 
	}
}
