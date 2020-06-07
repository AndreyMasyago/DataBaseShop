CREATE SEQUENCE IF NOT EXISTS catalog_idx OWNED BY catalog.detail_id;
CREATE SEQUENCE IF NOT EXISTS delivery_idx OWNED BY delivery.delivery_id;
CREATE SEQUENCE IF NOT EXISTS delivery_content_idx OWNED BY delivery_content.delivery_content_id;
CREATE SEQUENCE IF NOT EXISTS goods_idx OWNED BY goods.goods_id;
CREATE SEQUENCE IF NOT EXISTS order_content_idx OWNED BY order_content.order_content_id;
CREATE SEQUENCE IF NOT EXISTS order_entity_idx OWNED BY order_entity.order_id;
CREATE SEQUENCE IF NOT EXISTS provider_idx OWNED BY provider.provider_id;
CREATE SEQUENCE IF NOT EXISTS reject_idx OWNED BY reject.reject_id;
CREATE SEQUENCE IF NOT EXISTS storage_idx OWNED BY storage.cells_id;
CREATE SEQUENCE IF NOT EXISTS storage_transactions_idx OWNED BY storage_transactions.storage_transaction_id;