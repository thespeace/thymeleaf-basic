package thespeace.thymeleaf.itemservice.web.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import thespeace.thymeleaf.itemservice.domain.item.MessageItem;
import thespeace.thymeleaf.itemservice.domain.item.MessageItemRepository;

import java.util.List;

@Controller
@RequestMapping("/message/items")
@RequiredArgsConstructor
public class MessageItemController {

    private final MessageItemRepository itemRepository;


    @GetMapping("/intro")
    public String intro() {
        return "message/intro";
    }

    @GetMapping
    public String items(Model model) {
        List<MessageItem> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "message/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable("itemId") long itemId, Model model) {
        MessageItem item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "message/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new MessageItem());
        return "message/addForm";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute MessageItem item, RedirectAttributes redirectAttributes) {
        MessageItem savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/message/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable("itemId") Long itemId, Model model) {
        MessageItem item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "message/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable("itemId") Long itemId, @ModelAttribute MessageItem item) {
        itemRepository.update(itemId, item);
        return "redirect:/message/items/{itemId}";
    }

}

