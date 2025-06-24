package me.cher1shrxd.grabit.service;

import me.cher1shrxd.grabit.dto.res.BaseResponseDTO;
import me.cher1shrxd.grabit.dto.res.CartResponseDTO;
import me.cher1shrxd.grabit.dto.res.ItemResponseDTO;
import me.cher1shrxd.grabit.entity.CartEntity;
import me.cher1shrxd.grabit.entity.ItemEntity;
import me.cher1shrxd.grabit.repository.CartRepository;
import me.cher1shrxd.grabit.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CartRepository cartRepository;

    public BaseResponseDTO<List<ItemResponseDTO>> getItemsByCart(Long cartId) {
        Optional<CartEntity> cartEntity = cartRepository.findById(cartId);
        if (cartEntity.isPresent()) {
            CartEntity cart = cartEntity.get();
            List<ItemEntity> items = cart.getItems();
            List<ItemResponseDTO> itemList = items.stream().map(entity -> {
                ItemResponseDTO itemResponseDTO = new ItemResponseDTO();
                itemResponseDTO.setName(entity.getName());
                itemResponseDTO.setAmount(entity.getAmount());
                return itemResponseDTO;
            }).toList();

            return BaseResponseDTO.of(itemList, "success", 200);
        }
        return BaseResponseDTO.of(null, "can't find cart", 404);
    }

    public BaseResponseDTO<Long> deleteByItemId(Long itemId) {
        if (itemRepository.existsById(itemId)) {
            itemRepository.deleteById(itemId);
            return BaseResponseDTO.of(itemId, "success", 200);
        }
        return BaseResponseDTO.of(null, "can't find item", 404);
    }

    public BaseResponseDTO<ItemResponseDTO> updateItemAmountById(Long itemId) {
        Optional<ItemEntity> optional = itemRepository.findById(itemId);
        if (optional.isPresent()) {
            ItemEntity itemEntity = optional.get();
            itemEntity.setAmount(itemEntity.getAmount());
            ItemEntity saved = itemRepository.save(itemEntity);
            if (saved != null) {
                ItemResponseDTO itemResponseDTO = new ItemResponseDTO();
                itemResponseDTO.setAmount(saved.getAmount());
                itemResponseDTO.setName(saved.getName());
                return BaseResponseDTO.of(itemResponseDTO, "success", 200);
            }
            return BaseResponseDTO.of(null, "item not saved", 400);
        }
        return BaseResponseDTO.of(null, "can't find item", 404);
    }
}
