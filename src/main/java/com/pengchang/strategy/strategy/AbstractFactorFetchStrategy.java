package com.pengchang.strategy.strategy;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.pengchang.strategy.entity.DomainBatchFactorFetchResponse;
import com.pengchang.strategy.entity.DomainFactorFetchResponse;
import com.pengchang.strategy.entity.FactorFetchDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author pengchang
 * @date 2023/08/19 11:24
 **/
@Slf4j
public abstract class AbstractFactorFetchStrategy<T, R> implements BaseFactorFetchStrategy {

    @Override
    public DomainBatchFactorFetchResponse batchFactorFetch(Map<String, Set<FactorFetchDTO>> dimensionId2FetchMap) throws Exception {
        DomainBatchFactorFetchResponse subResponse = initResponse(dimensionId2FetchMap);

        // 将dimensionId2FetchMap 转化为当前实际的request, Map<dimensionId, Map<fetchDTO, request>>
        Map<String, Map<FactorFetchDTO, T>> requestMap = Maps.newHashMap();
        List<T> requestList = Lists.newArrayList();
        dimensionId2FetchMap.forEach((dimensionId, fetchSet) -> {
            Map<FactorFetchDTO, T> fetch2RequestMap = Maps.newHashMap();
            requestMap.put(dimensionId, fetch2RequestMap);
            fetchSet.forEach(factorFetchDTO -> {
                T request = convert2Request(dimensionId, factorFetchDTO);
                if (request == null) {
                    log.error("batchFactorFetch.convert2Request error, dimensionId:{}, factorFetchDTO:{}",
                            dimensionId, factorFetchDTO);
                    return;
                }
                fetch2RequestMap.put(factorFetchDTO, request);
                requestList.add(request);
            });
        });

        // 根据转化后的request，计算结果
        Map<T, R> request2ResultMap = batchFetchResultByRequest(requestList);

        Map<String, DomainFactorFetchResponse> dimensionId2ResultMap = subResponse.getDimensionId2ResultMap();
        requestMap.forEach((dimensionId, fetch2RequestMap) -> {
            fetch2RequestMap.forEach((fetchDTO, request) -> {
                R fetchResult = request2ResultMap.get(request);
                if (fetchResult != null) {
                    dimensionId2ResultMap.get(dimensionId).getFetch2Result().put(fetchDTO, fetchResult);
                } else {

                }
            });
        });
        return subResponse;
    }

    private DomainBatchFactorFetchResponse initResponse(Map<String, Set<FactorFetchDTO>> dimensionId2FetchMap) {
        Map<String, DomainFactorFetchResponse> dimensionId2ResultMap = Maps.newHashMapWithExpectedSize(dimensionId2FetchMap.size());
        dimensionId2FetchMap.forEach((dimensionId, fetchSet) -> {
           DomainFactorFetchResponse domainFactorFetchResponse = new DomainFactorFetchResponse()
                    .setFetch2Result(Maps.newHashMap());
            dimensionId2ResultMap.put(dimensionId, domainFactorFetchResponse);
        });
        return new DomainBatchFactorFetchResponse().setDimensionId2ResultMap(dimensionId2ResultMap);
    }

    /**
     * 取数结果
     */
    protected abstract Map<T, R> batchFetchResultByRequest(List<T> requestList);

    protected abstract T convert2Request(String dimensionId, FactorFetchDTO fetch);
}