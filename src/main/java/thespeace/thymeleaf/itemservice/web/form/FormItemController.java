package thespeace.thymeleaf.itemservice.web.form;

import lombok.extern.slf4j.Slf4j;
import thespeace.thymeleaf.itemservice.domain.item.DeliveryCode;
import thespeace.thymeleaf.itemservice.domain.item.Item;
import thespeace.thymeleaf.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import thespeace.thymeleaf.itemservice.domain.item.ItemType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/form/items")
@RequiredArgsConstructor
public class FormItemController {

    private final ItemRepository itemRepository;

    /**
     * <h2>@ModelAttribute의 특별한 사용법</h2>
     * 등록 폼, 상세화면, 수정 폼에서 모두 서울, 부산, 제주라는 체크 박스를 반복해서 보여주어야 한다. 이렇게 하려면 각각
     * 의 컨트롤러에서 {@code model.addAttribute(...)}을 사용해서 체크 박스를 구성하는 데이터를 반복해서 넣어주어야
     * 한다.
     * <br>
     * {@code @ModelAttribute}는 이렇게 컨트롤러에 있는 별도의 메서드에 적용할 수 있다.
     * 이렇게하면 해당 컨트롤러를 요청할 때 `regions`에서 반환한 값이 자동으로 모델(`model`)에 담기게 된다.
     * 물론 이렇게 사용하지 않고, 각각의 컨트롤러 메서드에서 모델에 직접 데이터를 담아서 처리해도 된다.
     */
    @ModelAttribute("regions")
    public Map<String, String> regions() {
        Map<String, String> regions = new LinkedHashMap<>();//순서보장을 위해 LinkedHashMap 사용.
        regions.put("SEOUL", "서울");
        regions.put("BUSAN", "부산");
        regions.put("JEJU", "제주");
        return regions;
    }

    /**
     * <h2>타임리프에서 ENUM 직접 사용하기(참고만, 권장 X)</h2>
     * 모델에 ENUM을 담아서 전달하는 대신에 타임리프는 자바 객체에 직접 접근할 수 있다.<br><br>
     * {@code <div th:each="type : ${T(thespeace.thymeleaf.itemservice.domain.item.ItemType).values()}">} <br><br>
     * 스프링EL 문법으로 ENUM을 직접 사용 가능, {@code values()}를 호출하면 해당 ENUM의 모든 정보가 배열로 반환.<br><br>
     * 이렇게 사용하면 ENUM의 패키지 위치가 변경되거나 할때 자바 컴파일러가 타임리프까지 컴파일 오류를 잡을 수 없으므로 추천하지는 않는다.
     */
    @ModelAttribute("itemTypes")
    public ItemType[] itemTypes() {
        return ItemType.values(); //ItemType.values() : ENUM의 모든 정보를 배열로 반환.
    }

    @ModelAttribute("deliveryCodes")
    public List<DeliveryCode> deliveryCodes() {
        List<DeliveryCode> deliveryCodes = new ArrayList<>();
        deliveryCodes.add(new DeliveryCode("FAST", "빠른 배송"));
        deliveryCodes.add(new DeliveryCode("NORMAL", "일반 배송"));
        deliveryCodes.add(new DeliveryCode("SLOW", "느린 배송"));
        return deliveryCodes;
    }

    @GetMapping("/intro")
    public String intro() {
        return "form/intro";
    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "form/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable("itemId") long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "form/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item()); //`th:object`를 적용하려면 먼저 해당 오브젝트 정보를 넘겨주어야 한다.
                                                            //등록 폼이기 때문에 데이터가 비어있는 빈 오브젝트를 만들어서 뷰에 전달하자(빈 객체는 비용이 거의 들지않는다.).
        return "form/addForm";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {

        log.info("item.open={}", item.getOpen());
        log.info("item.regions={}", item.getRegions());
        log.info("item.itemTypes={}", item.getItemType());

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/form/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable("itemId") Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "form/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable("itemId") Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/form/items/{itemId}";
    }

}

