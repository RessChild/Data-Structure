#include<iostream>
#include<cstring>

class KMP {
public:
	char str; //������ �����ϴ� ����
	int move; //������ Ƚ��
	KMP();
	~KMP();
};

using namespace std;

int main() {
	
	int length; //���ϱ��� �����
	cout << "Ž���� ���� ���� : ";
	cin >> length; //�Է¹���
	getchar();

	KMP* pattern = new KMP[length]; 
	//KMP ������ ������ �迭�� �������� ����

	cout << "���� �Է� : ";
	if (length > 0) { // 0���� ũ�ٸ�?	
		pattern[0].str = getchar(); // ù ���� �Է¹���
		pattern[0].move = 1; // KMP�� ���� (1)

		int cal; //�̵���ų �Ÿ� ����
		for (int i = 1; i < length; i++) { //��� �迭�� ������ ���� �� ���� �ݺ�
			cal = pattern[i - 1].move; //������ ���� �̵����� Ȱ��
			pattern[i].str = getchar();
			while (cal <= i - 1) { //�迭�� ������ �Ѿ ��� �׸��R
				if (pattern[i - 1].str == pattern[i - 1 - cal].str) { // �� ���ڰ� �������
					break; //�ݺ��� ������ ����
				}
				else {
					cal += pattern[i - 1 - cal].move; //�� �ٽ� �̵���Ű�� ���� ���� ���
				}
			}
			pattern[i].move = cal; //������� �̵���Ų ����
		}


		for (int i = 0; i < length; i++) {
			cout << pattern[i].str << "\t" << pattern[i].move << endl;
		}
	}
	else {
		cout << "���ڿ��� ���̴� ��� �̻��̿����մϴ�" << endl;
		return -1; //���� ���
	}
	//--- ���̺� �����

	char book[100]; //������ ��� ����
	cout << "������ ã�� ���� (�ִ� 100��) : ";
	cin >> book; //������ ����

	int index = 0; //���� �д� ���̺��� ��ǥ
	for (int i = 0; i < 100; i++) {

		if (index == length) {
			cout << i - length + 1 << "���� " << i + 1 << "���̿� ������ �����մϴ�." << endl;
			break; // ������ ã��
		}

		if (book[i] == '\0') {
			//�ش� ���ڰ� �� �����ΰ��
			cout << "������ �������� �ʽ��ϴ�." << endl;
			break;
		}
		else if (book[i] == pattern[index].str) { //���� �д����̺��̶� ���ڰ� ������
			index++; // �д� ���̺� ��ġ �̵� (+1)
			continue; // ���
		}
		else { //�ٸ� ���
			if (index > 0) { //ĭ�� �ű� �� �ִ� ���
				index -= pattern[index].move; //�ε��� ��ġ ����
				i--; //for���� ���� �ڵ� i++ �ǹǷ�, �̸� ��ȿȭ ��Ű��
			}
			// �ű� �� ���°��� for���� ���� �ڵ����� ������ ��ĭ �Ű���

			continue; //�ٽ��ѹ� �ش� ���ڸ� �õ�
		}
	}

	return 0;
}

KMP::KMP() {}
KMP::~KMP() {}