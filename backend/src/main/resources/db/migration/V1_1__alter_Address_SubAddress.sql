ALTER TABLE Address DROP CONSTRAINT address_subaddress_fkey;

ALTER TABLE SubAddress
    ADD COLUMN address UUID;

ALTER TABLE SubAddress ADD CONSTRAINT subaddress_address_fkey
    FOREIGN KEY (address) REFERENCES Address (id);

