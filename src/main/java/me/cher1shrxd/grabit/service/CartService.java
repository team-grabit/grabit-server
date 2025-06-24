package me.cher1shrxd.grabit.service;

import me.cher1shrxd.grabit.dto.req.CartRequestDTO;
import me.cher1shrxd.grabit.entity.CartEntity;
import me.cher1shrxd.grabit.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public boolean createCart(CartRequestDTO cartRequestDTO) {
        CartEntity cartEntity = new CartEntity();
        cartEntity.setName(cartRequestDTO.getName());
        CartEntity saved = cartRepository.save(cartEntity);
        return saved != null;
    }
}
