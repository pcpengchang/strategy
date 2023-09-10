package com.pengchang.strategy.flagship;

import com.google.common.collect.Lists;
import com.pengchang.strategy.flagship.enums.RuleTypeEnum;
import com.pengchang.strategy.flagship.rule.DisplayRuleDTO;
import com.pengchang.strategy.flagship.rule.FilterRuleDTO;

/**
 * @author pengchang
 * @date 2023/09/07 20:38
 **/
public class DefaultRuleFactory {
    public static DisplayRuleDTO displayRule = new DisplayRuleDTO();

    public static FilterRuleDTO filterRule = new FilterRuleDTO();

    static {
        // 规则之间为且关系，不符合则直接过滤
        displayRule.setRuleType(RuleTypeEnum.DISPLAY_RULE.getValue());
        displayRule.setName(RuleTypeEnum.DISPLAY_RULE.getDesc());
        displayRule.setRules(Lists.newArrayList("flagshipFeature.displayFeature" +
                        ".startTime<=currentTime&&flagshipFeature.displayFeature.endTime>=currentTime"
                , "flagshipFeature.displayFeature.maxDisplayTimes > userFeature.userDisplayFeature.usedNum"
                , "userFeature.userDisplayFeature.startTime==0||dayToNow(userFeature,currentTime)<flagshipFeature" +
                        ".displayFeature.maxDisplayTime"));

        filterRule.setRuleType(RuleTypeEnum.FILTER_RULE.getValue());
        filterRule.setName(RuleTypeEnum.FILTER_RULE.getDesc());
        filterRule.setRules(Lists.newArrayList("memberFilter(flagshipFeature,userFeature)"
                , "userFeature.userMemberFeature.memberFeature"));
    }
}
