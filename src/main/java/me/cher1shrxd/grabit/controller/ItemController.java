package me.cher1shrxd.grabit.controller;

import me.cher1shrxd.grabit.dto.req.ItemRequest;
import me.cher1shrxd.grabit.dto.req.ItemUpdateRequest;
import me.cher1shrxd.grabit.dto.res.BaseResponse;
import me.cher1shrxd.grabit.dto.res.ItemResponse;
import me.cher1shrxd.grabit.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping("/{cartId}")
    public BaseResponse<List<ItemResponse>> getItemsByCart(@PathVariable Long cartId) {
        return itemService.getItemsByCart(cartId);
    }

    @PostMapping
    public BaseResponse<Long> createItem(@RequestBody ItemRequest request) {
        return itemService.createItem(request);
    }

    @DeleteMapping("/{itemId}")
    public BaseResponse<Long> deleteItem(@PathVariable Long itemId) {
        return itemService.deleteByItemId(itemId);
    }

    @PutMapping("/{itemId}")
    public BaseResponse<ItemResponse> updateItem(@PathVariable Long itemId, @RequestBody ItemUpdateRequest request) {
        return itemService.updateItemAmountById(itemId, request);
    }
}
