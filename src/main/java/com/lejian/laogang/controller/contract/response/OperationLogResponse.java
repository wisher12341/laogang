package com.lejian.laogang.controller.contract.response;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class OperationLogResponse {

    private List<LogGroup> logGroupList;

    @Data
    public static class LogGroup{
        private List<LogData> logDataList;
        private String traceId;
        private String time;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class LogData{
        private String oldData;
        private String newData;
        private String field;
        private String type;
        /**
         * 操作人
         */
        private String operator;
        private String time;
        private String traceId;
    }
}
