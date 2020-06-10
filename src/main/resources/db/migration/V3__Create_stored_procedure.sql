CREATE OR REPLACE FUNCTION finance_report (date_start DATE, date_end DATE)
    RETURNS TABLE (d DATE, income BIGINT, expense BIGINT, rejects BIGINT, row_total BIGINT)
AS $$
DECLARE
    total_income BIGINT := 0;
    total_expense BIGINT := 0;
    total_rejects BIGINT := 0;
BEGIN

d := date_start;

LOOP
    income := (SELECT
                    COALESCE(sum("order_content"."amount" * "goods"."selling_price"), 0)
                FROM
                    "order_entity"
                    INNER JOIN "order_content" ON "order_content"."order_id" = "order_entity"."order_id"
                    INNER JOIN "goods" ON "goods"."goods_id" = "order_content"."goods_id"
                WHERE
                    "order_entity"."order_date" = d);

    expense := (SELECT
                    COALESCE(sum("delivery_content"."amount" * "goods"."purchase_price"), 0)
                FROM
                    "delivery"
                    INNER JOIN "delivery_content" ON "delivery_content"."delivery_id" = "delivery"."delivery_id"
                    INNER JOIN "goods" ON "goods"."goods_id" = "delivery_content"."goods_id"
                WHERE
                    "delivery"."arriving_date_on_storage" = d);

    rejects := (SELECT
                    COALESCE(sum("reject"."amount" * "goods"."selling_price"), 0)
                FROM
                    "order_entity"
                    INNER JOIN "reject" ON "reject"."order_id" = "order_entity"."order_id"
                    INNER JOIN "goods" ON "goods"."goods_id" = "reject"."goods_id"
                WHERE
                    "order_entity"."order_date" = d);

    row_total := income - expense - rejects;

    RETURN NEXT;

    total_income := total_income + income;
    total_expense := total_expense + expense;
    total_rejects := total_rejects + rejects;

    d := d + 1;
    EXIT WHEN d > date_end;
END LOOP;

d := NULL;
income := total_income;
expense := total_expense;
rejects := total_rejects;
row_total := total_income - total_expense - total_rejects;
RETURN NEXT;

END; $$

LANGUAGE 'plpgsql';
