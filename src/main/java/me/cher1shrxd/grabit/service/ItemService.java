package me.cher1shrxd.grabit.service;

import me.cher1shrxd.grabit.dto.req.ItemRequest;
import me.cher1shrxd.grabit.dto.req.ItemUpdateRequest;
import me.cher1shrxd.grabit.dto.res.BaseResponse;
import me.cher1shrxd.grabit.dto.res.ItemResponse;
import me.cher1shrxd.grabit.entity.CartEntity;
import me.cher1shrxd.grabit.entity.ItemEntity;
import me.cher1shrxd.grabit.repository.CartRepository;
import me.cher1shrxd.grabit.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CartRepository cartRepository;

    public BaseResponse<List<ItemResponse>> getItemsByCart(Long cartId) {
        Optional<CartEntity> cartEntity = cartRepository.findById(cartId);
        if (cartEntity.isPresent()) {
            CartEntity cart = cartEntity.get();
            List<ItemEntity> items = cart.getItems();
            List<ItemResponse> itemList = items.stream().map(entity -> {
                ItemResponse itemResponse = new ItemResponse();
                itemResponse.setName(entity.getName());
                itemResponse.setAmount(entity.getAmount());
                return itemResponse;
            }).toList();

            return BaseResponse.of(itemList, "success", 200);
        }
        return BaseResponse.of(null, "can't find cart", 404);
    }

    public BaseResponse<Long> deleteByItemId(Long itemId) {
        if (itemRepository.existsById(itemId)) {
            itemRepository.deleteById(itemId);
            return BaseResponse.of(itemId, "success", 200);
        }
        return BaseResponse.of(null, "can't find item", 404);
    }

    public BaseResponse<ItemResponse> updateItemAmountById(Long itemId, ItemUpdateRequest request) {
        Optional<ItemEntity> optional = itemRepository.findById(itemId);
        if (optional.isPresent()) {
            ItemEntity itemEntity = optional.get();
            itemEntity.setAmount(request.getAmount());
            ItemEntity saved = itemRepository.save(itemEntity);
            if (saved != null) {
                ItemResponse itemResponse = new ItemResponse();
                itemResponse.setAmount(saved.getAmount());
                itemResponse.setName(saved.getName());
                return BaseResponse.of(itemResponse, "success", 200);
            }
            return BaseResponse.of(null, "item not saved", 400);
        }
        return BaseResponse.of(null, "can't find item", 404);
    }

    public BaseResponse<Long> createItem(ItemRequest request) {
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setAmount(request.getAmount());
        itemEntity.setName(request.getName());

        Optional<CartEntity> cartEntity = cartRepository.findById(request.getCartId());
        if (cartEntity.isPresent()) {
            CartEntity cart = cartEntity.get();
            itemEntity.setCart(cart);
            ItemEntity saved = itemRepository.save(itemEntity);
            if (saved != null) {
                return BaseResponse.of(saved.getId(), "success", 201);
            }
            return BaseResponse.of(null, "item not saved", 400);
        }
        return BaseResponse.of(null, "can't find cart", 404);

    }
}
