CREATE TABLE IF NOT EXISTS Users
(
    id        UUID PRIMARY KEY NOT NULL,
    firstName VARCHAR(30)      NOT NULL,
    lastName  VARCHAR(40)      NOT NULL,
    email     VARCHAR(40)      NOT NULL,
    password  VARCHAR(20)      NOT NULL
);

CREATE TABLE IF NOT EXISTS GeoPoint
(
    id        UUID PRIMARY KEY NOT NULL,
    accuracy  VARCHAR(10),
    latitude  VARCHAR(15)      NOT NULL,
    longitude VARCHAR(15)      NOT NULL
);

CREATE TABLE IF NOT EXISTS GeoLocation
(
    id              UUID PRIMARY KEY NOT NULL,
    name            VARCHAR(40)      NOT NULL,
    type            VARCHAR(40),
    geographicPoint UUID REFERENCES GeoPoint (id) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS Address
(
    id                 UUID PRIMARY KEY NOT NULL,
    streetNr           VARCHAR(10)      NOT NULL,
    streetNrSuffix     VARCHAR(10),
    streetNrLast       VARCHAR(10),
    streetNrLastSuffix VARCHAR(10),
    streetName         VARCHAR(40)      NOT NULL,
    streetType         VARCHAR(20),
    streetSuffix       VARCHAR(10),
    postcode           VARCHAR(20)      NOT NULL,
    locality           VARCHAR(40),
    city               VARCHAR(40)      NOT NULL,
    stateOrProvince    VARCHAR(40)      NOT NULL,
    country            VARCHAR(40)      NOT NULL,
    geographicLocation UUID REFERENCES GeoLocation (id) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS SubAddress
(
    id                  UUID PRIMARY KEY NOT NULL,
    type                VARCHAR(40),
    name                VARCHAR(40),
    subUnitType         VARCHAR(40),
    subUnitNumber       VARCHAR(10),
    levelNumber         VARCHAR(10),
    buildingName        VARCHAR(40),
    privateStreetName   VARCHAR(40),
    privateStreetNumber VARCHAR(10),
    address UUID REFERENCES Address (id) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS ShipmentTracking
(
    id                    UUID PRIMARY KEY         NOT NULL,
    href                  VARCHAR(50),
    carrier               VARCHAR(50),
    trackingCode          VARCHAR(50)              NOT NULL,
    carrierTrackingUrl    VARCHAR(50),
    trackingDate          TIMESTAMPTZ              NOT NULL,
    status                VARCHAR(50)              NOT NULL,
    statusChangeDate      TIMESTAMPTZ,
    statusChangeReason    VARCHAR(50),
    weight                REAL,
    estimatedDeliveryDate TIMESTAMPTZ,
    addressFrom           UUID REFERENCES Address (id) ON DELETE RESTRICT,
    addressTo             UUID REFERENCES Address (id) ON DELETE RESTRICT
);


CREATE TABLE IF NOT EXISTS CheckPoint
(
    id              UUID PRIMARY KEY         NOT NULL,
    status          VARCHAR(50)              NOT NULL,
    message         VARCHAR(50),
    date            TIMESTAMPTZ              NOT NULL,
    checkPost       VARCHAR(50)              NOT NULL,
    city            VARCHAR(40)              NOT NULL,
    stateOrProvince VARCHAR(40),
    country         VARCHAR(40)              NOT NULL
);


CREATE TABLE IF NOT EXISTS UserParcel
(
    userId      UUID REFERENCES Users (id) ON DELETE RESTRICT,
    parcelId    UUID REFERENCES ShipmentTracking (id) ON DELETE RESTRICT,
    addDate        TIMESTAMPTZ NOT NULL,
    isFavourite BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (userId, parcelId)
);

CREATE TABLE IF NOT EXISTS CheckPointParcel
(
    checkPointId UUID REFERENCES CheckPoint (id),
    parcelId     UUID REFERENCES ShipmentTracking (id),
    PRIMARY KEY (checkPointId, parcelId)
);