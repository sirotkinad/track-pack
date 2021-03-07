CREATE TABLE Users
(
    id        UUID PRIMARY KEY NOT NULL,
    firstName VARCHAR(30)      NOT NULL,
    lastName  VARCHAR(40)      NOT NULL,
    email     VARCHAR(40)      NOT NULL,
    password  VARCHAR(20)      NOT NULL
);

CREATE TABLE GeoPoint
(
    id        UUID PRIMARY KEY NOT NULL,
    accuracy  VARCHAR(10),
    latitude  VARCHAR(15)      NOT NULL,
    longitude VARCHAR(15)      NOT NULL
);

CREATE TABLE GeoLocation
(
    id              UUID PRIMARY KEY NOT NULL,
    name            VARCHAR(40)      NOT NULL,
    type            VARCHAR(40),
    geographicPoint UUID REFERENCES GeoPoint (id) ON DELETE RESTRICT
);


CREATE TABLE SubAddress
(
    id                  UUID PRIMARY KEY NOT NULL,
    type                VARCHAR(40),
    name                VARCHAR(40),
    subUnitType         VARCHAR(40),
    subUnitNumber       VARCHAR(10),
    levelNumber         VARCHAR(10),
    buildingName        VARCHAR(40),
    privateStreetName   VARCHAR(40),
    privateStreetNumber VARCHAR(10)
);


CREATE TABLE Address
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
    geographicLocation UUID REFERENCES GeoLocation (id) ON DELETE RESTRICT,
    subAddress         UUID REFERENCES SubAddress (id) ON DELETE RESTRICT
);

CREATE TABLE ShipmentTracking
(
    id                    UUID PRIMARY KEY         NOT NULL,
    href                  VARCHAR(50),
    carrier               VARCHAR(50),
    trackingCode          VARCHAR(50)              NOT NULL,
    carrierTrackingUrl    VARCHAR(50),
    trackingDate          TIMESTAMP WITH TIME ZONE NOT NULL,
    status                VARCHAR(50)              NOT NULL,
    statusChangeDate      TIMESTAMP WITH TIME ZONE,
    statusChangeReason    VARCHAR(50),
    weight                REAL,
    estimatedDeliveryDate TIMESTAMP WITH TIME ZONE,
    addressFrom           UUID REFERENCES Address (id) ON DELETE RESTRICT,
    addressTo             UUID REFERENCES Address (id) ON DELETE RESTRICT
);


CREATE TABLE CheckPoint
(
    id              UUID PRIMARY KEY         NOT NULL,
    status          VARCHAR(50)              NOT NULL,
    message         VARCHAR(50),
    date            TIMESTAMP WITH TIME ZONE NOT NULL,
    checkPost       VARCHAR(50)              NOT NULL,
    city            VARCHAR(40)              NOT NULL,
    stateOrProvince VARCHAR(40),
    country         VARCHAR(40)              NOT NULL
);


CREATE TABLE UserParcel
(
    userId   UUID REFERENCES Users (id) ON DELETE RESTRICT,
    parcelId UUID REFERENCES ShipmentTracking (id) ON DELETE RESTRICT,
    PRIMARY KEY (userId, parcelId)
);

CREATE TABLE CheckPointParcel
(
    checkPointId UUID REFERENCES CheckPoint (id),
    parcelId     UUID REFERENCES ShipmentTracking (id),
    PRIMARY KEY (checkPointId, parcelId)
);