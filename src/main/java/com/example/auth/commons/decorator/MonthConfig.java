package com.example.auth.commons.decorator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonthConfig {
    int getPurchaseHistoryMonthDifference =3;
}
