package com.pengchang.strategy;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author pengchang
 * @date 2023/09/10 17:58
 **/
public class CommonTest {
    @Test
    void testList() {
        List<List<Long>> factorIds = ImmutableList.of(
                ImmutableList.of(1L, 2L), ImmutableList.of(3L, 4L)
        );
        System.out.println(factorIds);

        String str = "{\"orderId\":12345677,\"poiId\":123456,\"orderPrice\":987,\"orderStatus\":\"5150\",\n" +
                "\"payTime\":150329201470,\"checkInTime\":150329201470,\"checkOutTime\":150329201470}";

        System.out.println(str.getBytes().length);
    }
}
