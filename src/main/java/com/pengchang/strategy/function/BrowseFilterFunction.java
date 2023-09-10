package com.pengchang.strategy.function;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorBoolean;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.pengchang.strategy.flagship.model.FlagshipRecommendFeatureDTO;
import com.pengchang.strategy.flagship.model.RecommendBrowseFeatureDTO;
import com.pengchang.strategy.flagship.model.UserFeatureDTO;
import com.pengchang.strategy.flagship.model.user.UserBrowseFeatureDTO;
import com.pengchang.strategy.utils.DateUtils;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author pengchang
 * @date 2023/09/10 22:11
 **/
public class BrowseFilterFunction extends AbstractFunction {
    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2, AviatorObject arg3) {
        FlagshipRecommendFeatureDTO flagshipFeature = (FlagshipRecommendFeatureDTO) FunctionUtils.getJavaObject(arg1,
                env);
        UserFeatureDTO userFeature = (UserFeatureDTO) FunctionUtils.getJavaObject(arg2, env);
        Number number = FunctionUtils.getNumberValue(arg3, env);
        long currentTime = number.longValue();
        List<UserBrowseFeatureDTO> userBrowseFeatures = userFeature.getUserBrowseFeatures();
        RecommendBrowseFeatureDTO flagshipBrowseFeature = flagshipFeature.getBrowseFeature();
        // 没有配置浏览特征信息，则不进行规则过滤
        if (flagshipBrowseFeature == null) {
            return AviatorBoolean.valueOf(true);
        }

        // 是否存在回溯范围内的浏览数据
        if (CollectionUtils.isNotEmpty(userBrowseFeatures)) {
            List<UserBrowseFeatureDTO> target = userBrowseFeatures.stream()
                    .filter(e -> DateUtils.getDistanceDay(currentTime, e.getBrowseActionTime()) <= flagshipBrowseFeature.getTraceDay())
                    .collect(Collectors.toList());

            return AviatorBoolean.valueOf(CollectionUtils.isNotEmpty(target));
        }

        return AviatorBoolean.valueOf(false);
    }

    @Override
    public String getName() {
        return "browseFilter";
    }
}
