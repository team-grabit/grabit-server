package me.cher1shrxd.grabit.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemRequest {
    private String name;
    private Long amount;
    private Long cartId;
}
