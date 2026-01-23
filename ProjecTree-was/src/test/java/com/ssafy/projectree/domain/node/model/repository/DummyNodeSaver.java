package com.ssafy.projectree.domain.node.model.repository;

import com.ssafy.projectree.domain.node.enums.NodeStatus;
import com.ssafy.projectree.domain.node.enums.Priority;
import com.ssafy.projectree.domain.node.model.entity.*;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 이 줄 추가
class DummyNodeSaver {
	@Autowired
	private NodeRepository nodeRepository;


	@Commit
	@Test
	//@Disabled
	public void saveFullDummyNodeTree() {
		// 1. Root: Project Node (최상위)
		Node root = ProjectNode.builder()
				.name("P성향 여행자를 위한 여행도우미 앱 (P-PliP)")
				.description("계획은 귀찮지만 효율적인 여행을 원하는 P형 사용자를 위한 실시간 추천 서비스")
				.priority(Priority.P0)
				.identifier("PROJ-001")
				.status(NodeStatus.TODO)
				.build();
		nodeRepository.saveRoot(root);

		// 2. EPIC: 추천 엔진 (가장 핵심 가치)
		Node epicRecommendation = EpicNode.builder()
				.name("[추천 엔진] 실시간 위치 기반 Just-in-Time 장소 추천")
				.description("""
                목표: 사용자의 현재 위치와 상황을 분석하여 즉흥적으로 방문할 곳을 추천함
                비즈니스 가치: 결정 장애 해소 및 실시간 만족도 극대화
                """)
				.priority(Priority.P0)
				.identifier("EPIC-002")
				.status(NodeStatus.IN_PROGRESS)
				.build();
		nodeRepository.saveWithParent(root.getId(), epicRecommendation);

		// 3. STORY: 특정 상황에서의 추천 시나리오
		Node storyJit = StoryNode.builder()
				.name("[추천] 사용자는 현재 근처의 '웨이팅이 적은' 맛집을 즉시 추천받을 수 있다.")
				.description("""
                1. User Story:
                As a (배고픈 여행자) I want to (줄 서지 않는 식당 추천을 받음) So that (길바닥에서 시간을 버리지 않음)
                
                2. Acceptance Criteria:
                - Google Maps API 실시간 혼잡도 데이터 연동
                - 현재 위치 반경 500m 이내 식당 필터링
                - '즉시 입장 가능' 태그 표시
                """)
				.priority(Priority.P1)
				.identifier("STORY-005")
				.status(NodeStatus.TODO)
				.build();
		nodeRepository.saveWithParent(epicRecommendation.getId(), storyJit);

		// 4. TASK: 구체적인 개발 작업 단위
		Node taskMapApi = TaskNode.builder()
				.name("Google Maps Places API 연동 및 데이터 파싱")
				.description("""
                - Places Search API를 이용한 주변 상권 정보 수집
                - 영업 시간 및 별점 데이터 DB 매핑
                """)
				.priority(Priority.P2)
				.identifier("TASK-010")
				.status(NodeStatus.TODO)
				.build();
		nodeRepository.saveWithParent(storyJit.getId(), taskMapApi);

		Node taskLogic = TaskNode.builder()
				.name("실시간 혼잡도 기반 가중치 추천 알고리즘 구현")
				.description("거리 40%, 평점 30%, 혼잡도 30%를 반영한 추천 스코어링 로직 작성")
				.priority(Priority.P1)
				.identifier("TASK-011")
				.status(NodeStatus.TODO)
				.build();
		nodeRepository.saveWithParent(storyJit.getId(), taskLogic);

		// 5. ADVANCE: 기술적 고도화 또는 리팩토링 (추가 확장)
		Node advanceOptimization = AdvanceNode.builder()
				.name("Redis를 활용한 주변 장소 캐싱 처리")
				.description("""
                목표: 동일 지역 사용자의 중복 API 호출 방지 및 응답 속도 개선
                상세: 
                - Geo-Hash 알고리즘을 이용한 지역 기반 캐싱
                - TTL 30분 설정으로 실시간성 유지
                """)
				.priority(Priority.P2)
				.identifier("ADV-001")
				.status(NodeStatus.IN_PROGRESS)
				.build();
		nodeRepository.saveWithParent(taskMapApi.getId(), advanceOptimization);

		// 추가적인 EPIC (계획 보조)
		Node epicScratchpad = EpicNode.builder()
				.name("[보조] 무계획 여행자를 위한 낙서장(Scratchpad)")
				.description("가고 싶은 곳을 순서 없이 던져두면 자동으로 동선을 최적화해주는 기능")
				.priority(Priority.P1)
				.identifier("EPIC-003")
				.status(NodeStatus.TODO)
				.build();
		nodeRepository.saveWithParent(root.getId(), epicScratchpad);
	}
}