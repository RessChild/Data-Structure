#include<iostream>
#include<cstring>

class KMP {
public:
	char str; //패턴을 구성하는 문자
	int move; //움직일 횟수
	KMP();
	~KMP();
};

using namespace std;

int main() {
	
	int length; //패턴길이 저장용
	cout << "탐색할 패턴 길이 : ";
	cin >> length; //입력받음
	getchar();

	KMP* pattern = new KMP[length]; 
	//KMP 정보를 저장할 배열을 동적으로 선언

	cout << "패턴 입력 : ";
	if (length > 0) { // 0보다 크다면?	
		pattern[0].str = getchar(); // 첫 문자 입력받음
		pattern[0].move = 1; // KMP는 시작 (1)

		int cal; //이동시킬 거리 측정
		for (int i = 1; i < length; i++) { //모든 배열에 정보를 넣을 때 까지 반복
			cal = pattern[i - 1].move; //이전에 구한 이동값을 활용
			pattern[i].str = getchar();
			while (cal <= i - 1) { //배열의 끝까지 넘어간 경우 그만둚
				if (pattern[i - 1].str == pattern[i - 1 - cal].str) { // 두 문자가 같은경우
					break; //반복문 밖으로 나감
				}
				else {
					cal += pattern[i - 1 - cal].move; //또 다시 이동시키며 이의 합을 계산
				}
			}
			pattern[i].move = cal; //현재까지 이동시킨 문자
		}


		for (int i = 0; i < length; i++) {
			cout << pattern[i].str << "\t" << pattern[i].move << endl;
		}
	}
	else {
		cout << "문자열의 길이는 양수 이상이여야합니다" << endl;
		return -1; //에러 출력
	}
	//--- 테이블 만들기

	char book[100]; //내용이 담길 변수
	cout << "패턴을 찾을 내용 (최대 100자) : ";
	cin >> book; //내용을 적음

	int index = 0; //현재 읽는 테이블의 좌표
	for (int i = 0; i < 100; i++) {

		if (index == length) {
			cout << i - length + 1 << "부터 " << i + 1 << "사이에 패턴이 존재합니다." << endl;
			break; // 패턴을 찾음
		}

		if (book[i] == '\0') {
			//해당 글자가 널 문자인경우
			cout << "패턴이 존재하지 않습니다." << endl;
			break;
		}
		else if (book[i] == pattern[index].str) { //현재 읽는테이블이랑 문자가 같으면
			index++; // 읽는 테이블 위치 이동 (+1)
			continue; // 계속
		}
		else { //다른 경우
			if (index > 0) { //칸을 옮길 수 있는 경우
				index -= pattern[index].move; //인덱스 위치 조정
				i--; //for문에 의해 자동 i++ 되므로, 이를 무효화 시키고
			}
			// 옮길 수 없는경우는 for문에 의해 자동으로 옆으로 한칸 옮겨짐

			continue; //다시한번 해당 글자를 시도
		}
	}

	return 0;
}

KMP::KMP() {}
KMP::~KMP() {}