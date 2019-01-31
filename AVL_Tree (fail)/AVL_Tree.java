// LR 회전의 경우, 하단 2개의 RR 회전 후 3개의 LL 회전으로 바라볼 수 있음
// RL 회전은 반대
// 완전 폭망한 case, 가능하면 이후에 수정해볼 생각

package AVL_Tree;

import java.util.*;

public class AVL_Tree {
	private Node root = null;
	// 시작루트는 없음 (null)

	private static Node recent = null;
	// 가장 마지막에 접근한 노드 정보 저장 (Insert에서 사용)

	public static void main(String[] args) {
		AVL_Tree tree = new AVL_Tree();

		tree.Insert(5);
		tree.showStart();
		tree.Insert(7);
		tree.showStart();
		tree.Insert(9);
		tree.showStart();
		tree.Insert(1);
		tree.showStart();
		tree.Insert(2);
		tree.showStart();
		tree.Insert(5);
		tree.showStart();
		tree.Insert(10);
		tree.showStart();
		tree.Insert(-1);
		tree.showStart();
		tree.Insert(-10);
		tree.showStart();
		tree.Insert(-5);
		tree.showStart();
		tree.Insert(6);
		tree.showStart();
		tree.Insert(8);
		tree.showStart();
		
		tree.Delete(11);
		tree.showStart();
		tree.Delete(5);
		tree.showStart();
	}

	public boolean Insert(int value) {
		Node find = Search(value); // 탐색 시도

		if (find == null) { // 되돌려 받은 값이 null인 경우
			// 해당 값이 없으므로 추가 가능 (recent 사용)
			System.out.println(value + " 값 노드 추가");

			Node make = new Node(value, recent); // 새 노드를 생성
			Node save = recent; // L,R값을 조정하고
			Node before = make;

			Stack<Boolean> direction = new Stack<Boolean>();
			// true는 왼쪽, false는 오른쪽

			if (save != null) {
				if (save.value > value) { // 추가할 노드의 대소관계에 따라 위치 변경
					save.L = make; // 왼쪽 추가
				} else {
					save.R = make; // 오른쪽 추가
				}
			} else {
				root = make; // 루트로 붙임
				return true; // 끝
			}

			while (save != null) {
				if (save.value > before.value) { // 추가할 노드의 대소관계에 따라 depth값 변경
					save.label_L++; // 왼쪽 증가
					direction.add(true); // 왼쪽에서 올라왔음
				} else {
					save.label_R++; // 오른쪽 증가
					direction.add(false); // 오른쪽에서올라왔음
				}
				int result = save.label_L - save.label_R;

				System.out.println(save.value + "의 라벨 차이(좌,우,차이) :" + save.label_L + "-" + save.label_R + "=" + result);
				if (result == 0)
					// 만일 값이 증가했는데 본인 상위값이 변동될 일 없다면
					break; // 위쪽은 볼 일없음

				if (!(Math.abs(result) < 2)) {
					// 좌우 깊이의 차이가 2를 넘는다면
					// 회전 함수 호출 필요
					Node first = save;
					Node second = null;
					Node third = null;

					int select = 0; // 어떤 회전인지 선택
					if (direction.pop() == true) { // 왼쪽이면
						second = save.L; // 왼쪽노드 정보 저장
						save = save.L;
					} else {
						second = save.R; // 오른쪽 노드 저장
						save = save.R;
						select += 1;
					}
					if (direction.pop() == true) // 두번째도 pop
					{
						third = save.L; // 왼쪽노드 정보 저장
					} else {
						third = save.R; // 오른쪽 노드 저장
						select += 10;
					}

					System.out.println(first + "|" + second + "|" + third);
					System.out.print(first.value + "/" + second.value + "/");
					if(third != null)
						System.out.println(third.value);
					else
						System.out.println("X");

					// 0, 1, 10, 11. 총 4가지 존재

					if (select == 0) { // LL회전
						// 한쪽으로치우친 무게를 돌려서 분산
						if (first.P != null) { // 부모가 null이 아닌경우 (first가 루트가 아닌경우)
							if (first.P.L == first) { // 부모의 왼쪽에 달린경우
								first.P.L = second; // 자식내용 변경
							} else {
								first.P.R = second;
							}
						} else // first가 루트이면
							root = second; // 루트 변경

						second.P = first.P;
						first.P = second;
						// first를 second의 밑으로 붙이고

						if (second.R != null) // 자식이 있으면
							second.R.P = first;
						first.L = second.R;
						// second의 원래 자식을 옮기는 작업수행
						second.R = first; // 마무리

						// *** Label을 변경하는 단계

						second.label_R++;
						first.label_L -= 2;

						save = second;
						System.out.println("LL회전");
					} else if (select == 1) { // RL회전
						if (first.P != null) { // 부모가 null이 아닌경우 (first가 루트가 아닌경우)
							if (first.P.L == first) { // 부모의 왼쪽에 달린경우
								first.P.L = third; // 자식내용 변경
							} else {
								first.P.R = third;
							}
						} else // first가 루트이면
							root = third; // 루트 변경

						third.P = first.P;
						first.P = third;
						second.P = third;
						// first, second를 third의 밑으로 붙이고
						if (third.R != null)
							third.R.P = second; // third의 왼쪽은 second로
						if (third.L != null)
							third.L.P = first; // third의 오른쪽은 first로 붙음
						second.L = third.R;
						first.R = third.L;
						// 각 자식들을 떠넘김
						third.R = second;
						third.L = first; // 마무리

						// *** Label을 변경하는 단계

						first.label_R -= 3;
						third.label_R+=2;
						third.label_L+=2;

						save = third;

						System.out.println("RL회전");

					} else if (select == 10) { // LR회전
						// RL과 대칭
						// third가 새로운 중심으로 치고 올라오면서 본인 자식을 좌우로 뿌림
						if (first.P != null) { // 부모가 null이 아닌경우 (first가 루트가 아닌경우)
							if (first.P.L == first) { // 부모의 왼쪽에 달린경우
								first.P.L = third; // 자식내용 변경
							} else {
								first.P.R = third;
							}
						} else // first가 루트이면
							root = third; // 루트 변경

						third.P = first.P;
						first.P = third;
						second.P = third;
						// first, second를 third의 밑으로 붙이고
						if (third.L != null)
							third.L.P = second; // third의 왼쪽은 second로
						if (third.R != null)
							third.R.P = first; // third의 오른쪽은 first로 붙음
						second.R = third.L;
						first.L = third.R;
						// 각 자식들을 떠넘김
						third.L = second;
						third.R = first; // 마무리

						// *** Label을 변경하는 단계

						first.label_L -= 3;
						third.label_L+=2;
						third.label_R+=2;

						save = third;

						System.out.println("LR회전");

					} else if (select == 11) { // RR회전
						// LL과 대칭
						if (first.P != null) { // 부모가 null이 아닌경우 (first가 루트가 아닌경우)
							if (first.P.L == first) { // 부모의 왼쪽에 달린경우
								first.P.L = second; // 자식내용 변경
							} else {
								first.P.R = second;
							}
						} else // first가 루트이면
							root = second; // 루트 변경

						second.P = first.P;
						first.P = second;
						// first를 second의 밑으로 붙이고
						if (second.L != null)
							second.L.P = first;
						first.R = second.L;
						// second의 원래 자식을 옮기는 작업수행
						second.L = first; // 마무리

						// *** Label을 변경하는 단계

						second.label_L++;
						first.label_R -= 2;

						save = second; // 2번노드가 새로운 중심

						System.out.println("RR회전");

					}

					return true; // 회전을 시키면 위쪽은 변동없으므로 return true
				}

				before = save; // 현재 위치를 저장시키고
				save = save.P; // 부모로 한칸 위로 올라감
			}
			// 해당 노드를 생성, 추가하는 과정

			return true;
		} else {
			System.out.println("<Error : Search> 해당 값은 이미 존재합니다.");
			return false;
		}
	} // insert끝

	public boolean Delete(int value) {
		Node find = Search(value); // 탐색 시도
		Node save = null; // 회전시 저장을 위한 변수
		Node before = null;
		
		if (find != null) { // 되돌려 받은 값이 null이 아닌 경우
			// 해당 값이 존재, 삭제가능
			System.out.println(value + " 값 노드 삭제");
			
			if (find.label_L == 0 && find.label_R == 0) {
				// 둘다 깊이가 0인 경우 (노드가 없는경우)
				// 바로 삭제
				if (find.P != null) { // 부모가 존재하면
					if (recent.value > find.value)
						recent.L = null; // 왼쪽에 붙은 놈을 제거
					else
						recent.R = null; // 오른쪽에 붙은 놈 제가
				}
				else
					root = null; //루트에 붙은놈이므로 삭제
				
			} else if (find.label_L == 0) { // 왼쪽만 빈 경우
				if (recent.value > find.value) { // 부모의 왼쪽에 붙은 놈이면
					recent.L = find.R; // 오른쪽에 붙은놈을 선택
					find.P = recent; // 부모도 변경 (연결)
				} else {
					recent.R = find.R; // 오른쪽에 붙은놈을 선택
					find.P = recent; // 부모도 변경 (연결)
				}
			} else if (find.label_R == 0) { // 오른쪽만 빈 경우
				if (recent.value > find.value) {
					recent.L = find.L; // 오른쪽에 붙은놈을 선택
					find.P = recent; // 부모도 변경 (연결)
				} else {
					recent.R = find.L; // 오른쪽에 붙은놈을 선택
					find.P = recent; // 부모도 변경 (연결)
				}
			} else { // 둘다 노드가 붙어있는경우
				// 한쪽끝을 새 parent로 연결시도

				// ***
				Node b_head = null; // 서브루트를 떼어내기 위해 저장할 부모 노드 정보
				Node n_head = find.L; // 새로운 서브루트가 될 녀석
				Node parent = recent; // 가장 최근의 부모를 parent 변수에 대입

				while (n_head.R != null) { // 이동이 불가할때 까지 계속 이동
					b_head = n_head; // 갱신
					n_head = n_head.R; // 이동
				}
				
				if (parent != null)
					if (parent.value > n_head.value) // while 문을 통해 찾아온 끝 값을 대리루트로 붙이는 작업
						parent.L = n_head;
					else
						parent.R = n_head;
				else
					root = n_head; // 지우는 놈이 루트인 경우, 루트로 갱신

				n_head.R = find.R; // 새 루트에, 기존의 반대편에 있던 놈을 붙임
				if (b_head != null) { // 부모가 NULL이 아니면, 바로 다음 자식이 붙는게 아님 중간에 위치함
					b_head.R = n_head.L; // 새로 정한 루트의 기존 길을 위쪽에 붙이고
					n_head.L = find.L; // 기존 루트의 왼쪽을 새 루트에 연결
				}
				
				save = b_head.P;
				before = b_head;
			}
			// 해당 노드를 삭제하는 과정

			Stack<Boolean> direction = new Stack<Boolean>();
			// true는 왼쪽, false는 오른쪽

			while (save != null) {
				if (save.value > before.value) { // 추가할 노드의 대소관계에 따라 depth값 변경
					save.label_L--; // 왼쪽 감소
					direction.add(true); // 왼쪽에서 올라왔음
				} else {
					save.label_R--; // 오른쪽 감소
					direction.add(false); // 오른쪽에서올라왔음
				}
				int result = save.label_L - save.label_R;

				System.out.println(save.value + "의 라벨 차이(좌,우,차이) :" + save.label_L + "-" + save.label_R + "=" + result);
				
				if (result == 0)
					// 만일 값이 증가했는데 본인 상위값이 변동될 일 없다면
					break; // 위쪽은 볼 일없음

				if (!(Math.abs(result) < 2)) {
					// 좌우 깊이의 차이가 2를 넘는다면
					// 회전 함수 호출 필요
					Node first = save;
					Node second = null;
					Node third = null;

					int select = 0; // 어떤 회전인지 선택
					if (direction.pop() == true) { // 왼쪽이면
						second = save.L; // 왼쪽노드 정보 저장
						save = save.L;
					} else {
						second = save.R; // 오른쪽 노드 저장
						save = save.R;
						select += 1;
					}
					if (direction.pop() == true) // 두번째도 pop
					{
						third = save.L; // 왼쪽노드 정보 저장
					} else {
						third = save.R; // 오른쪽 노드 저장
						select += 10;
					}

					System.out.println(first + "|" + second + "|" + third);
					System.out.println(first.value + "/" + second.value + "/" + third.value);

					// 0, 1, 10, 11. 총 4가지 존재

					if (select == 0) { // LL회전
						// 한쪽으로치우친 무게를 돌려서 분산
						if (first.P != null) { // 부모가 null이 아닌경우 (first가 루트가 아닌경우)
							if (first.P.L == first) { // 부모의 왼쪽에 달린경우
								first.P.L = second; // 자식내용 변경
							} else {
								first.P.R = second;
							}
						} else // first가 루트이면
							root = second; // 루트 변경

						second.P = first.P;
						first.P = second;
						// first를 second의 밑으로 붙이고

						if (second.R != null) // 자식이 있으면
							second.R.P = first;
						first.L = second.R;
						// second의 원래 자식을 옮기는 작업수행
						second.R = first; // 마무리

						// *** Label을 변경하는 단계

						second.label_R++;
						first.label_L -= 2;

						save = second;
						System.out.println("LL회전");
					} else if (select == 1) { // RL회전
						if (first.P != null) { // 부모가 null이 아닌경우 (first가 루트가 아닌경우)
							if (first.P.L == first) { // 부모의 왼쪽에 달린경우
								first.P.L = third; // 자식내용 변경
							} else {
								first.P.R = third;
							}
						} else // first가 루트이면
							root = third; // 루트 변경

						third.P = first.P;
						first.P = third;
						second.P = third;
						// first, second를 third의 밑으로 붙이고
						if (third.R != null)
							third.R.P = second; // third의 왼쪽은 second로
						if (third.L != null)
							third.L.P = first; // third의 오른쪽은 first로 붙음
						second.L = third.R;
						first.R = third.L;
						// 각 자식들을 떠넘김
						third.R = second;
						third.L = first; // 마무리

						// *** Label을 변경하는 단계

						first.label_R -= 2;
						second.label_L--;
						third.label_R++;
						third.label_L++;

						save = third;

						System.out.println("RL회전");

					} else if (select == 10) { // LR회전
						// RL과 대칭
						// third가 새로운 중심으로 치고 올라오면서 본인 자식을 좌우로 뿌림
						if (first.P != null) { // 부모가 null이 아닌경우 (first가 루트가 아닌경우)
							if (first.P.L == first) { // 부모의 왼쪽에 달린경우
								first.P.L = third; // 자식내용 변경
							} else {
								first.P.R = third;
							}
						} else // first가 루트이면
							root = third; // 루트 변경

						third.P = first.P;
						first.P = third;
						second.P = third;
						// first, second를 third의 밑으로 붙이고
						if (third.L != null)
							third.L.P = second; // third의 왼쪽은 second로
						if (third.R != null)
							third.R.P = first; // third의 오른쪽은 first로 붙음
						second.R = third.L;
						first.L = third.R;
						// 각 자식들을 떠넘김
						third.L = second;
						third.R = first; // 마무리

						// *** Label을 변경하는 단계

						first.label_L -= 2;
						second.label_R--;
						third.label_L++;
						third.label_R++;

						save = third;

						System.out.println("LR회전");

					} else if (select == 11) { // RR회전
						// LL과 대칭
						if (first.P != null) { // 부모가 null이 아닌경우 (first가 루트가 아닌경우)
							if (first.P.L == first) { // 부모의 왼쪽에 달린경우
								first.P.L = second; // 자식내용 변경
							} else {
								first.P.R = second;
							}
						} else // first가 루트이면
							root = second; // 루트 변경

						second.P = first.P;
						first.P = second;
						// first를 second의 밑으로 붙이고
						if (second.L != null)
							second.L.P = first;
						first.R = second.L;
						// second의 원래 자식을 옮기는 작업수행
						second.L = first; // 마무리

						// *** Label을 변경하는 단계

						second.label_L++;
						first.label_R -= 2;

						save = second; // 2번노드가 새로운 중심

						System.out.println("RR회전");

					}
					continue; // 회전을 시키면 위쪽은 변동없으므로 return true
				}
			}
			
			// AVL 트리의 규칙에 따라 노드를 검사, 수정하는 과정
			return true;
		} else {
			System.out.println("<Error : Delete> 해당 값은 존재하지 않습니다.");
			return false;
		}
	}

	public Node Search(int value) {

		Node find = root; // 해당 값을 찾기위한 임시변수
		recent = null; // 최근위치 초기화
		while (find != null) { // 움직일 놈이 비어있지 않을 동안
			recent = find; // 가장 마지막에 도달한 위치를 저장
			if (find.value > value) { // 현재 위치의 값이 찾으려는 값보다 큰 경우
				find = find.L; // 왼쪽으로 이동
			} else if (find.value < value) { // 현재 위치 값이 더 작은 경우
				find = find.R; // 오른쪽으로 이동
			} else { // 대소 관계에서 그 외의 경우는 같은 경우 뿐 (찾음)
				break; // 값을 발견했으므로 break
				// 이후 return find를 통해 값을 반환
			}
		} // root가 null인경우는 바로 빠져나옴

		return find; // Search 함수 종료
	}

	public void show(Node p) {
		if (p != null) {
			System.out.println("[" + p.value + "]의 왼쪽 자식 " + p.label_L);
			show(p.L);
			System.out.println("[" + p.value + "]복귀");
			System.out.println("[" + p.value + "]의 오른쪽 자식 " + p.label_R);
			show(p.R);
		}
	}

	public void showStart() {
		System.out.println("-------");
		show(root);
		System.out.println("*******");
	}
}
