CREATE TABLE IF NOT EXISTS users
(
    id         UUID PRIMARY KEY NOT NULL DEFAULT gen_random_uuid(),
    first_name VARCHAR(30)      NOT NULL,
    last_name  VARCHAR(40)      NOT NULL,
    email      VARCHAR(50)      NOT NULL,
    password   VARCHAR(255)      NOT NULL
);

CREATE TABLE IF NOT EXISTS geo_point
(
    id        UUID PRIMARY KEY NOT NULL DEFAULT gen_random_uuid(),
    accuracy  VARCHAR(10),
    latitude  VARCHAR(15)      NOT NULL,
    longitude VARCHAR(15)      NOT NULL
);

CREATE TABLE IF NOT EXISTS geo_location
(
    id               UUID PRIMARY KEY NOT NULL DEFAULT gen_random_uuid(),
    name             VARCHAR(40)      NOT NULL,
    type             VARCHAR(40),
    geographic_point UUID REFERENCES geo_point (id) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS address
(
    id                    UUID PRIMARY KEY NOT NULL DEFAULT gen_random_uuid(),
    street_nr             VARCHAR(10)      NOT NULL,
    street_nr_suffix      VARCHAR(10),
    street_nr_last        VARCHAR(10),
    street_nr_last_suffix VARCHAR(10),
    street_name           VARCHAR(40)      NOT NULL,
    street_type           VARCHAR(20),
    street_suffix         VARCHAR(10),
    postcode              VARCHAR(20)      NOT NULL,
    locality              VARCHAR(40),
    city                  VARCHAR(40)      NOT NULL,
    state_or_province     VARCHAR(40)      NOT NULL,
    country               VARCHAR(40)      NOT NULL,
    geographic_location   UUID REFERENCES geo_location (id) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS sub_address
(
    id                    UUID PRIMARY KEY NOT NULL DEFAULT gen_random_uuid(),
    type                  VARCHAR(40),
    name                  VARCHAR(40),
    sub_unit_type         VARCHAR(40),
    sub_unit_number       VARCHAR(10),
    level_number          VARCHAR(10),
    building_name         VARCHAR(40),
    private_street_name   VARCHAR(40),
    private_street_number VARCHAR(10),
    address               UUID REFERENCES address (id) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS shipment_tracking
(
    id                      UUID PRIMARY KEY NOT NULL DEFAULT gen_random_uuid(),
    href                    VARCHAR(50),
    carrier                 VARCHAR(50),
    tracking_code           VARCHAR(50)      NOT NULL,
    carrier_tracking_url    VARCHAR(50),
    tracking_date           TIMESTAMPTZ      NOT NULL,
    status                  VARCHAR(50)      NOT NULL,
    status_change_date      TIMESTAMPTZ,
    status_change_reason    VARCHAR(50),
    weight                  REAL,
    estimated_delivery_date TIMESTAMPTZ,
    address_from            UUID REFERENCES address (id) ON DELETE RESTRICT,
    address_to              UUID REFERENCES address (id) ON DELETE RESTRICT
);


CREATE TABLE IF NOT EXISTS check_point
(
    id                UUID PRIMARY KEY NOT NULL DEFAULT gen_random_uuid(),
    status            VARCHAR(50)      NOT NULL,
    message           VARCHAR(50),
    date              TIMESTAMPTZ      NOT NULL,
    check_post        VARCHAR(50)      NOT NULL,
    city              VARCHAR(40)      NOT NULL,
    state_or_province VARCHAR(40),
    country           VARCHAR(40)      NOT NULL
);


CREATE TABLE IF NOT EXISTS user_parcel
(
    user_id      UUID REFERENCES users (id) ON DELETE RESTRICT,
    parcel_id    UUID REFERENCES shipment_tracking (id) ON DELETE RESTRICT,
    parcel_name  VARCHAR(50),
    add_date     TIMESTAMPTZ,
    is_favourite BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (user_id, parcel_id)
);

CREATE TABLE IF NOT EXISTS check_point_parcel
(
    check_point_id UUID REFERENCES check_point (id),
    parcel_id      UUID REFERENCES shipment_tracking (id),
    PRIMARY KEY (check_point_id, parcel_id)
);