package DataBase.Repository;

import java.util.List;
import java.util.Optional;

public interface MonthlyAverageSales {
    public List<Object[]> getMonthlyAverageSales2(
            Optional<Integer> detailId,
            Optional<Integer> month,
            Optional<Long> amountFrom,
            Optional<Long> amountTo,
            Optional<String> producerSearch,
            Optional<String> orderBy,
            Optional<Integer> pageSize,
            Optional<Integer> page
    );

    public Integer getMonthlyAverageSalesCount(
            Optional<Integer> detailId,
            Optional<Integer> month,
            Optional<Long> amountFrom,
            Optional<Long> amountTo,
            Optional<String> producerSearch
    );
}
