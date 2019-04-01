#include<iostream>
#include<math.h> 

using namespace std;

class Node{
	public:
		int value[2]; //���� �� 
		int usedSpace; //���� ���Ǵ� ���� ���� 
		Node* child[3]; //�ڽ� ��������� 3�� 
		Node(int v){
			for(int i=0;i<3;i++)
				this->child[i] = NULL;
				
			this->value[0] = v; // ���� ���� ���� �־���
			this->value[1] = v; //����ó���� ���̱� ���� �������� �־���. 
			this->usedSpace = 1; //���� �ϳ� ���Ա� ����. 
		}
};
void Show(Node* n, int num);

class Tree_2_3{ //2-3Ʈ�� 
	public:
		int num;
		Node** stack; //�ڽ��� �� ��(�θ�)�� �˾ƾ� �ϱ� ������ ���� ���
			// new���Ҵ� �ϸ�, �Ҵ�� ���� ���� ���� Node*�̹Ƿ� ���������� 
			//�Ҵ��� ���� log n ũ���� �迭 
		Node* root; //��Ʈ ������. 
		Tree_2_3(){
			this->num = 0;
			this->root = NULL; //ó���� ���� 
			this->stack = NULL;
		}
		~Tree_2_3(){
			Erase(this->root);
		}
		void Erase(Node* n);
		bool Search(int v,int& top); //Ž�� 
		void Insert(int v); //���� 
};

int main(){

Tree_2_3 tree;


while(true){
	int add = 0;
	cout<<" �Է��� ���� :";
	cin>>add;
	tree.Insert(add);
	
	Show(tree.root,0);
}



return 0;	
}

bool Tree_2_3::Search(int v,int& top){
		
	int n = log2(this->num); 
	
	if(!(n<1)){
	this->stack = new Node*[n]; //log2 num��ŭ�� ������ ���� 
	
	Node* path = this->root; //���� ������ root 
	
	while(path!=NULL){ //���̻� ���� ���� �� ����. 
				
			if(path->value[0] == v || path->value[1] == v) //���� ����� ���� ã�� ���� ������ 
			{
				return true; //ã�Ҵ� �� 
				
			}
			else if(path->value[0] > v){ //���� ������ ã�� ���� �� ������ 
			 //���� ��� 
			 stack[top++] = path; //���ÿ� ���� ����ּҸ� ����
			 path = path->child[0]; //���� child�� �̵� 
			} 
			else if(path->value[1] < v){ //ū ������ ã�� ���� �� ũ�� 
				stack[top++] = path;
				path = path->child[2]; //������ �ڽ����� �̵� 
			}
			else{ //���̰��̸� 
				stack[top++] = path;
				path = path->child[1]; //���̷� �̵� 
			}
			
		//���� �ϳ��̸�, 0,1�� �� ���� �����ϹǷ� ���̰� ���� x
		//���� ũ�ų�, ���ų� ���� ������ �ϰԵ�. 
	}
}
	
	return false; //���̻� ���ư� ���� ������ ����x false�� return ��Ŵ. 
}

void Tree_2_3::Insert(int v){
	int top =0 ; 
	
	if(!Search(v,top)) //ã�� ���ϸ� ���� �ִ´�. 
	{
		
		if(this->root!=NULL){
		Node* pre_node = NULL;
			Node* node;
		if(stack == NULL)
			node = this->root;
		else
			node = stack[--top]; //�θ�� ���ư���. 
		Node* new_link[2] = {NULL,NULL}; //���� ������� ��ũ 

		while(node!=NULL){			
		if(node->usedSpace == 1) //1ĭ�� ����������
		{
			if(node->value[0] < v ) //�ԷµȰ��� ū ��� 
				node->value[1] = v; //���� 
			else
				node->value[0] = v; //���� ��� ���� ������ ���� 
	
	//		������ ��Ʈ�� ���� �ľ��ؾ��� 
			if(pre_node !=NULL){ //������ �ƴϸ� 
				Node* node_sort[3];
			
				if(node->child[0] == pre_node){
					node_sort[0] = new_link[0];
					node_sort[1] = new_link[1];
					node_sort[2] = node->child[2]; //0�� ���ϵ忡�� �ö�°�� 
				}
				else{
					node_sort[0] = node->child[0];
					node_sort[1] = new_link[0];
					node_sort[2] = new_link[1]; //1�� ���ϵ忡�� �ö�� ��� 
				}
				
				node->child[0]= node_sort[0];
				node->child[1]= node_sort[1];
				node->child[2]= node_sort[2]; //���� 
			}
	//
			node->usedSpace++; //���� �ִ� ���� ���� �ø�.
			node = NULL; 
		} 
		//������ child �� ���� ������ �̰� ���� x 
		else{ //2ĭ�� ���� �ִ� ��� , ���� 

			int num_list[3] = {node->value[0],node->value[1],v}; //�� ���ڸ� sort��Ű�� ����	
			
			for(int i=0;i<3;i++)
				for(int j=i;j<3;j++){
					if(num_list[i] > num_list[j]){ //�񱳰��� �� ������ 
						int save = num_list[i];
						num_list[i]= num_list[j];
						num_list[j]= save; //��ȯ, ���� �� ������ sort ��Ŵ 
					}
				}
			
			Node* new_node1 = new Node(num_list[0]); // ���� ������ ������ 
			Node* new_node2 = new Node(num_list[2]); // ū ������ ��带 ���� 
			
			if(pre_node != NULL){ //�������ƴϸ�, ���λ����� ��忡 �ּҰ��� ���� 	
	//		������ ��Ʈ�� �����ľ��ؾ���. 
			Node* copy_child[4] = {node->child[0] ,node->child[2],new_link[0],new_link[1]}; 
			//���� child 2��, ���� ����ö�� ������ 2�� 
						
			for(int i=0,j=0;i<4;i++,j++){ //4���� ��ĭ�� ä��� ����, ���� 3�����ε��� �� 
				if(node->child[j] != pre_node){ //���� ����� �ּҿ� �ش� child�� �ּҰ� �ٸ��� 
					copy_child[i] = node->child[j]; //�� child�� �迭�� �ִ´� 
				}
				else{ //������ 
					copy_child[i++] = new_link[0]; //���ο� ��� 2���� �����Ѵ�. 
					copy_child[i]=new_link[1];
				}
			}
				new_node1->child[0] = copy_child[0];
				new_node1->child[2] = copy_child[1];
				new_node2->child[0] = copy_child[2];
				new_node2->child[2] = copy_child[3]; //���ĵ� ���·� ���� child�� ���� 
			}
	//			
			new_link[0] = new_node1;
			new_link[1] = new_node2; //�Բ� ������ö� �� �ּ��� ������ 
			
			pre_node=node;
			if(top>0){ //��Ʈ�� �ƴѰ��	
				v = num_list[1]; //�߾� ���� ������ �־�� �� ������ ����.
				node= stack[--top];  //�θ�� �ö� 
			}
			else { //��Ʈ�̸� 
				Node* new_root = new Node(num_list[1]); //�߾Ӱ����� �� ��Ʈ��带 ���� 
				new_root->child[0] = new_node1; //������ ��Ʈ�� ������ 
				new_root->child[2] = new_node2;  //������ ��Ʈ �� ū��
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
	
		cout<<num<<"�� ��� "<<n->value[0]<<", "<<n->value[1]<<endl;
		Show(n->child[0],num);
		cout<<n->value[0]<<", "<<n->value[1]<<"���� child Ž�� ��"<<endl;
		Show(n->child[1],num);
		cout<<n->value[0]<<", "<<n->value[1]<<"�߾� child Ž�� ��"<<endl;
		Show(n->child[2],num);
		cout<<n->value[0]<<", "<<n->value[1]<<"������ child Ž�� ��"<<endl; 
	}
}
