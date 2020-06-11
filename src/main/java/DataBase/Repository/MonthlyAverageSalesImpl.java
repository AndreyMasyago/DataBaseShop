package DataBase.Repository;

import DataBase.Domain.Goods;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MonthlyAverageSalesImpl implements MonthlyAverageSales {
    @Autowired
    EntityManager entityManager;

    public List<Object[]> getMonthlyAverageSales2(
            Optional<Integer> detailId,
            Optional<Integer> month,
            Optional<Long> amountFrom,
            Optional<Long> amountTo,
            Optional<String> producerSearch,
            Optional<String> orderBy,
            Optional<Integer> pageSize,
            Optional<Integer> page
    ) {
        String queryString = "SELECT goods, EXTRACT(MONTH FROM orderEntity.orderDate), SUM(orderContent.amount) " +
                "FROM OrderContent orderContent " +
                "INNER JOIN orderContent.orderEntity orderEntity " +
                "INNER JOIN orderContent.goods goods " +
                "INNER JOIN goods.catalog c ";

        List<String> where = new ArrayList<>();
        List<String> having = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();

        if (detailId.isPresent()) {
            where.add("c.detailId = :detailId");
            params.put("detailId", detailId.get());
        }

        if (month.isPresent()) {
            where.add("EXTRACT(MONTH FROM orderEntity.orderDate) = :month");
            params.put("month", month.get());
        }

        if (amountFrom.isPresent()) {
            having.add("SUM(orderContent.amount) >= :amountFrom");
            params.put("amountFrom", amountFrom.get());
        }

        if (amountTo.isPresent()) {
            having.add("SUM(orderContent.amount) <= :amountTo");
            params.put("amountTo", amountTo.get());
        }

        if (producerSearch.isPresent()) {
            where.add("LOWER(goods.producer) LIKE CONCAT('%', LOWER(:producerSearch), '%')");
            params.put("producerSearch", producerSearch.get());
        }

        if (!where.isEmpty()) {
            queryString += " WHERE " + String.join(" AND ", where) + " ";
        }

        queryString += " GROUP BY goods.goodsId, EXTRACT(MONTH FROM orderEntity.orderDate), c.detailId ";

        if (!having.isEmpty()) {
            queryString += " HAVING " + String.join(" AND ", having) + " ";
        }

        Map<String, String> orderByMap = Stream.of(new String[][] {
                { "goodsName", "c.goodsName" },
                { "month", "EXTRACT(MONTH FROM orderEntity.orderDate)" },
                { "producer", "goods.producer" },
                { "amount", "SUM(orderContent.amount)" },
                { "-goodsName", "c.goodsName DESC" },
                { "-month", "EXTRACT(MONTH FROM orderEntity.orderDate) DESC" },
                { "-producer", "goods.producer DESC" },
                { "-amount", "SUM(orderContent.amount) DESC" }
        }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

        if (orderBy.isPresent()) {
            queryString += " ORDER BY " + orderByMap.get(orderBy.get()) + " ";
        } else {
            queryString += " ORDER BY EXTRACT(MONTH FROM orderEntity.orderDate), goods.goodsId ";
        }

        TypedQuery<Object[]> query = entityManager.createQuery(queryString, Object[].class);
        for (Map.Entry<String, Object> param : params.entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }

        if (pageSize.isPresent()) {
            query.setMaxResults(pageSize.get());

            if (page.isPresent()) {
                query.setFirstResult(pageSize.get() * page.get());
            }
        }

        return query.getResultList();
    }

    public Integer getMonthlyAverageSalesCount(
            Optional<Integer> detailId,
            Optional<Integer> month,
            Optional<Long> amountFrom,
            Optional<Long> amountTo,
            Optional<String> producerSearch
    ) {
        String queryString = "SELECT goods, EXTRACT(MONTH FROM orderEntity.orderDate), SUM(orderContent.amount) " +
                "FROM OrderContent orderContent " +
                "INNER JOIN orderContent.orderEntity orderEntity " +
                "INNER JOIN orderContent.goods goods " +
                "INNER JOIN goods.catalog c ";

        List<String> where = new ArrayList<>();
        List<String> having = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();

        if (detailId.isPresent()) {
            where.add("c.detailId = :detailId");
            params.put("detailId", detailId.get());
        }

        if (month.isPresent()) {
            where.add("EXTRACT(MONTH FROM orderEntity.orderDate) = :month");
            params.put("month", month.get());
        }

        if (amountFrom.isPresent()) {
            having.add("SUM(orderContent.amount) >= :amountFrom");
            params.put("amountFrom", amountFrom.get());
        }

        if (amountTo.isPresent()) {
            having.add("SUM(orderContent.amount) <= :amountTo");
            params.put("amountTo", amountTo.get());
        }

        if (producerSearch.isPresent()) {
            where.add("LOWER(goods.producer) LIKE CONCAT('%', LOWER(:producerSearch), '%')");
            params.put("producerSearch", producerSearch.get());
        }

        if (!where.isEmpty()) {
            queryString += " WHERE " + String.join(" AND ", where) + " ";
        }

        queryString += " GROUP BY goods.goodsId, EXTRACT(MONTH FROM orderEntity.orderDate), c.detailId ";

        if (!having.isEmpty()) {
            queryString += " HAVING " + String.join(" AND ", having) + " ";
        }

        TypedQuery<Object[]> query = entityManager.createQuery(queryString, Object[].class);
        for (Map.Entry<String, Object> param : params.entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }

        return query.getResultList().size();
    }
}
