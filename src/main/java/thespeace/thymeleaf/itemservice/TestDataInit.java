package thespeace.thymeleaf.itemservice;

import jakarta.annotation.PostConstruct;
import thespeace.thymeleaf.itemservice.domain.item.Item;
import thespeace.thymeleaf.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import thespeace.thymeleaf.itemservice.domain.item.MessageItem;
import thespeace.thymeleaf.itemservice.domain.item.MessageItemRepository;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final ItemRepository itemRepository;
    private final MessageItemRepository messageItemRepository;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));

        messageItemRepository.save(new MessageItem("itemA", 10000, 10));
        messageItemRepository.save(new MessageItem("itemB", 20000, 20));
    }

}