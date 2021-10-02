package com.lejian.laogang.controller.contract.request;

import lombok.Data;

import java.util.List;

@Data
public class GetTrendRequest {
        private List<Integer> types;
}
