package com.pengchang.strategy.function;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorBoolean;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.pengchang.strategy.flagship.model.FlagshipRecommendFeatureDTO;
import com.pengchang.strategy.flagship.model.RecommendMemberFeatureDTO;
import com.pengchang.strategy.flagship.model.UserFeatureDTO;
import com.pengchang.strategy.flagship.model.user.UserMemberFeatureDTO;

import java.util.Map;

/**
 * @author pengchang
 * @date 2023/08/27 09:56
 **/
public class MemberFilterFunction extends AbstractFunction {

    private static final int DEFAULT_USER_SCOPE = 0;
    private static final int MEMBER_SCOPE = 1;
    private static final int NOT_MEMBER_SCOPE = 2;

    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
        FlagshipRecommendFeatureDTO flagshipFeature = (FlagshipRecommendFeatureDTO) FunctionUtils.getJavaObject(arg1,
                env);
        UserFeatureDTO userFeature = (UserFeatureDTO) FunctionUtils.getJavaObject(arg2, env);
        UserMemberFeatureDTO userMemberFeature = userFeature.getUserMemberFeature();
        RecommendMemberFeatureDTO flagshipMemberFeature = flagshipFeature.getMemberFeature();
        if (flagshipMemberFeature == null || !flagshipMemberFeature.isOpen()) {
            return AviatorBoolean.valueOf(true);
        }
        if (flagshipMemberFeature.getUserScope() == DEFAULT_USER_SCOPE) {
            return AviatorBoolean.valueOf(true);
        }
        if (flagshipMemberFeature.getUserScope() == MEMBER_SCOPE) {
            return AviatorBoolean.valueOf(userMemberFeature.isMemberFeature());
        }
        if (flagshipMemberFeature.getUserScope() == NOT_MEMBER_SCOPE) {
            return AviatorBoolean.valueOf(!userMemberFeature.isMemberFeature());
        }
        return AviatorBoolean.valueOf(false);
    }

    @Override
    public String getName() {
        return "memberFilter";
    }
}
