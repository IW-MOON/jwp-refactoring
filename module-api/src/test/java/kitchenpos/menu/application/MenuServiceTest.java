package kitchenpos.application.unit.menu;

import kitchenpos.menu.application.MenuService;
import kitchenpos.menu.dto.MenuResponse;
import kitchenpos.product.application.ProductService;
import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuProduct;
import kitchenpos.menugroup.domain.MenuGroup;
import kitchenpos.product.domain.Product;
import kitchenpos.menu.dto.MenuRequest;
import kitchenpos.menu.repository.MenuRepository;
import kitchenpos.menugroup.repository.MenuGroupRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @Mock
    MenuRepository menuRepository;

    @Mock
    MenuGroupRepository menuGroupRepository;

    @Mock
    ProductService productService;

    @DisplayName("메뉴를 등록한다.")
    @Test
    void createTest() {

        MenuGroup 한마리메뉴 = new MenuGroup("한마리메뉴");
        Product 후라이드 = new Product(1L, "후라이드", BigDecimal.valueOf(16000));

        Menu 후라이드치킨 = new Menu("후라이드치킨", BigDecimal.valueOf(16000), 한마리메뉴);

        MenuProduct 후라이드치킨_상품 = new MenuProduct(후라이드치킨, 후라이드, 1L);
        후라이드치킨.addMenuProduct(후라이드치킨_상품);

        when(menuGroupRepository.findById(후라이드치킨.getMenuGroup().getId())).thenReturn(Optional.of(한마리메뉴));
        when(productService.getProduct(후라이드.getId())).thenReturn(후라이드);
        MenuService menuService = new MenuService(menuRepository, menuGroupRepository, productService);

        Menu expectedMenu = mock(Menu.class);
        when(expectedMenu.getId()).thenReturn(1L);
        when(expectedMenu.getName()).thenReturn("후라이드치킨");
        when(expectedMenu.getPrice()).thenReturn(BigDecimal.valueOf(16000));

        when(menuRepository.save(후라이드치킨)).thenReturn(expectedMenu);

        // when
        MenuResponse created_후라이드치킨 = menuService.create(MenuRequest.from(후라이드치킨));
        // then
        assertThat(created_후라이드치킨.getId()).isNotNull();
        assertThat(created_후라이드치킨.getName()).isEqualTo(후라이드치킨.getName());
        assertThat(created_후라이드치킨.getPrice()).isEqualTo(후라이드치킨.getPrice());
    }

    @DisplayName("메뉴의 목록을 조회한다.")
    @Test
    void getListTest() {
        // given
        Menu menu = mock(Menu.class);

        when(menuRepository.findAll()).thenReturn(Arrays.asList(menu));
        MenuService menuService = new MenuService(menuRepository, menuGroupRepository, productService);

        // when
        List<MenuResponse> menus = menuService.list();
        // then
        assertThat(menus).contains(MenuResponse.from(menu));
    }

}