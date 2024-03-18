package thespeace.thymeleaf.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MessageItemRepository {

    private static final Map<Long, MessageItem> store = new HashMap<>(); //static
    private static long sequence = 0L; //static

    public MessageItem save(MessageItem item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public MessageItem findById(Long id) {
        return store.get(id);
    }

    public List<MessageItem> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, MessageItem updateParam) {
        MessageItem findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }

}
