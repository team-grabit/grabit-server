package me.cher1shrxd.grabit.service;

import me.cher1shrxd.grabit.dto.req.CartRequest;
import me.cher1shrxd.grabit.dto.res.BaseResponse;
import me.cher1shrxd.grabit.dto.res.CartResponse;
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

    public BaseResponse<List<CartResponse>> getAllCarts() {
        List<CartEntity> carts = cartRepository.findAll();
        List<CartResponse> cartsList = carts.stream().map(entity -> {
            CartResponse cartResponse = new CartResponse();
            cartResponse.setId(entity.getId());
            cartResponse.setName(entity.getName());
            return cartResponse;
        }).toList();

        return BaseResponse.of(cartsList, "success", 200);
    }

    public boolean createCart(CartRequest cartRequest) {
        CartEntity cartEntity = new CartEntity();
        cartEntity.setName(cartRequest.getName());
        CartEntity saved = cartRepository.save(cartEntity);
        return saved != null;
    }

    public BaseResponse<Long> deleteCartById(Long itemId) {
        if (cartRepository.existsById(itemId)) {
            cartRepository.deleteById(itemId);
            return BaseResponse.of(itemId, "success", 200);
        }
        return BaseResponse.of(null, "can't find item", 404);
    }
    
    public BaseResponse<CartResponse> updateCartById(Long cartId, CartRequest request) {
        Optional<CartEntity> cartEntity = cartRepository.findById(cartId);
        if (cartEntity.isPresent()) {
            CartEntity cart = cartEntity.get();
            cart.setName(request.getName());
            CartEntity saved = cartRepository.save(cart);
            if (saved != null) {
                CartResponse cartResponse = new CartResponse();
                cartResponse.setId(saved.getId());
                cartResponse.setName(saved.getName());
                return BaseResponse.of(cartResponse, "success", 201);
            }
            return BaseResponse.of(null, "item not saved", 400);
        }

        return BaseResponse.of(null, "can't find cart", 404);
    }
}
