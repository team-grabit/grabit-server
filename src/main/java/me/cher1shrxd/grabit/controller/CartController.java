package me.cher1shrxd.grabit.controller;

import me.cher1shrxd.grabit.dto.res.BaseResponse;
import me.cher1shrxd.grabit.dto.res.CartResponse;
import me.cher1shrxd.grabit.entity.CartEntity;
import me.cher1shrxd.grabit.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/")
    public BaseResponse<List<CartResponse>> getAllCarts() {
        return cartService.getAllCarts();
    }

    @GetMapping("/{cartId}")
    public BaseResponse<CartResponse> getCart(@PathVariable Long cartId) {

    }
}
