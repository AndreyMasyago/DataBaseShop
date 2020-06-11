ALTER TABLE goods ADD COLUMN selling_price_last_modified TIMESTAMP;

CREATE OR REPLACE FUNCTION set_selling_price_last_modified() RETURNS TRIGGER AS $$
    BEGIN
        IF NEW.selling_price != OLD.selling_price THEN
            NEW.selling_price_last_modified = current_timestamp;
        END IF;

        RETURN NEW;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_selling_price_last_modified BEFORE INSERT OR UPDATE ON goods
    FOR EACH ROW EXECUTE PROCEDURE set_selling_price_last_modified();
