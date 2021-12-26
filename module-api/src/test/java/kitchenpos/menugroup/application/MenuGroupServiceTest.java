package kitchenpos.menugroup.application;

import kitchenpos.menugroup.domain.MenuGroup;
import kitchenpos.menugroup.dto.MenuGroupRequest;
import kitchenpos.menugroup.dto.MenuGroupResponse;
import kitchenpos.menugroup.repository.MenuGroupRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuGroupServiceTest {

    @Mock
    private MenuGroupRepository menuGroupRepository;

    @DisplayName("메뉴 그룹을 등록한다.")
    @Test
    void createTest() {
        // given
        MenuGroup menuGroup = new MenuGroup("한식");

        MenuGroup expectedMenuGroup = mock(MenuGroup.class);
        when(expectedMenuGroup.getId()).thenReturn(1L);
        when(expectedMenuGroup.getName()).thenReturn("한식");

        when(menuGroupRepository.save(menuGroup)).thenReturn(expectedMenuGroup);
        MenuGroupService menuGroupService = new MenuGroupService(menuGroupRepository);

        // when
        MenuGroupResponse savedMenuGroup = menuGroupService.create(MenuGroupRequest.from(menuGroup));

        // then
        assertThat(savedMenuGroup.getId()).isNotNull();
        assertThat(savedMenuGroup.getName()).isEqualTo(menuGroup.getName());
    }

    @DisplayName("메뉴 그룹의 목록을 조회한다.")
    @Test
    void getListTest() {
        // given
        MenuGroup expectedMenuGroup = mock(MenuGroup.class);
        when(menuGroupRepository.findAll()).thenReturn(Arrays.asList(expectedMenuGroup));
        MenuGroupService menuGroupService = new MenuGroupService(menuGroupRepository);

        // when
        List<MenuGroupResponse> menuGroups = menuGroupService.list();

        // then
        assertThat(menuGroups).contains(MenuGroupResponse.from(expectedMenuGroup));
    }
}
