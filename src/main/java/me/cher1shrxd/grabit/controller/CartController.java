package me.cher1shrxd.grabit.controller;

import me.cher1shrxd.grabit.dto.req.CartRequest;
import me.cher1shrxd.grabit.dto.res.BaseResponse;
import me.cher1shrxd.grabit.dto.res.CartResponse;
import me.cher1shrxd.grabit.entity.CartEntity;
import me.cher1shrxd.grabit.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/carts")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping
    public BaseResponse<List<CartResponse>> getAllCarts() {
        return cartService.getAllCarts();
    }

    @PostMapping
    public BaseResponse<CartResponse> createCart(@RequestBody CartRequest cartRequest) {
        return cartService.createCart(cartRequest);
    }

    @DeleteMapping("/{cartId}")
    public BaseResponse<Long> deleteCartById(@PathVariable Long cartId) {
        return cartService.deleteCartById(cartId);
    }

    @PutMapping("{cartId}")
    public BaseResponse<CartResponse> updateCart(@PathVariable Long cartId, @RequestBody CartRequest cartRequest) {
        return cartService.updateCartById(cartId, cartRequest);
    }
}
