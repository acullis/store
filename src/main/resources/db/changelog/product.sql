-- Create product table
CREATE TABLE product (
                          id BIGSERIAL PRIMARY KEY,
                          description VARCHAR(255) NOT NULL
);

INSERT INTO product (id, description)
SELECT
    ROW_NUMBER() OVER (ORDER BY description) AS UniqueID,
    description
FROM
    (SELECT DISTINCT description FROM "order" ORDER BY description) AS DistinctRecords;


ALTER TABLE "order"
ADD COLUMN product_id BIGINT DEFAULT 0;

UPDATE "order" AS o
SET product_id = p.id
FROM product AS p
WHERE o.description = p.description;

ALTER TABLE "order"
ALTER COLUMN product_id SET NOT NULL;

ALTER TABLE "order"
ADD CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product (id);
