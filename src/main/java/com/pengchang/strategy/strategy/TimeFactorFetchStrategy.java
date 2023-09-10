package com.pengchang.strategy.strategy;

import com.google.common.collect.Maps;
import com.pengchang.strategy.entity.FactorFetchDTO;
import com.pengchang.strategy.utils.FactorParamParseHelper;
import com.pengchang.strategy.enums.FactorTemplateTypeEnum;
import com.pengchang.strategy.enums.ValueParseTypeEnum;
import com.pengchang.strategy.enums.VarTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author pengchang
 * @date 2023/08/19 11:28
 **/
public class TimeFactorFetchStrategy extends AbstractFactorFetchStrategy<TimeFactorFetchStrategy.Request, Boolean> {
    @Override
    protected Map<Request, Boolean> batchFetchResultByRequest(List<Request> requestList) {
        Map<Request, Boolean> request2ResultMap = Maps.newHashMapWithExpectedSize(requestList.size());
        // todo 真实查询
        requestList.forEach(request -> {
            request2ResultMap.put(request, true);
        });
        return request2ResultMap;
    }

    @Override
    protected Request convert2Request(String dimensionId, FactorFetchDTO fetch) {
        // todo
        List<Object> parseResult = FactorParamParseHelper.parse(fetch.getVariableMap()
                .get("ids"), VarTypeEnum.LONG, ValueParseTypeEnum.LIST);
        return Optional.ofNullable(parseResult).map(result ->
                new Request().setIds(parseResult.stream().map(t -> (long) t).collect(Collectors.toList()))).orElse(null);
    }

    @Override
    public FactorTemplateTypeEnum supportTemplate() {
        return FactorTemplateTypeEnum.TIME;
    }

    @Data
    @Accessors(chain = true)
    public static class Request {
        private List<Long> ids;
    }
}
