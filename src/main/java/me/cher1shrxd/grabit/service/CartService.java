package me.cher1shrxd.grabit.service;

import me.cher1shrxd.grabit.dto.req.CartRequestDTO;
import me.cher1shrxd.grabit.dto.res.BaseResponseDTO;
import me.cher1shrxd.grabit.dto.res.CartResponseDTO;
import me.cher1shrxd.grabit.entity.CartEntity;
import me.cher1shrxd.grabit.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public BaseResponseDTO<List<CartResponseDTO>> getAllCarts() {
        List<CartEntity> carts = cartRepository.findAll();
        List<CartResponseDTO> cartsList = carts.stream().map(entity -> {
            CartResponseDTO cartResponseDTO = new CartResponseDTO();
            cartResponseDTO.setId(entity.getId());
            cartResponseDTO.setName(entity.getName());
            return cartResponseDTO;
        }).toList();

        return BaseResponseDTO.of(cartsList, "success", 200);
    }

    public boolean createCart(CartRequestDTO cartRequestDTO) {
        CartEntity cartEntity = new CartEntity();
        cartEntity.setName(cartRequestDTO.getName());
        CartEntity saved = cartRepository.save(cartEntity);
        return saved != null;
    }

    public BaseResponseDTO<Long> deleteCartById(Long itemId) {
        if (cartRepository.existsById(itemId)) {
            cartRepository.deleteById(itemId);
            return BaseResponseDTO.of(itemId, "success", 200);
        }
        return BaseResponseDTO.of(null, "can't find item", 404);
    }
    
    public BaseResponseDTO<CartResponseDTO> updateCartById(Long cartId, CartRequestDTO request) {
        Optional<CartEntity> cartEntity = cartRepository.findById(cartId);
        if (cartEntity.isPresent()) {
            CartEntity cart = cartEntity.get();
            cart.setName(request.getName());
            CartEntity saved = cartRepository.save(cart);
            if (saved != null) {
                CartResponseDTO cartResponseDTO = new CartResponseDTO();
                cartResponseDTO.setId(saved.getId());
                cartResponseDTO.setName(saved.getName());
                return BaseResponseDTO.of(cartResponseDTO, "success", 201);
            }
            return BaseResponseDTO.of(null, "item not saved", 400);
        }

        return BaseResponseDTO.of(null, "can't find cart", 404);
    }
}
