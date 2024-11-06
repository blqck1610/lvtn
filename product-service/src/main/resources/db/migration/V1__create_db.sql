CREATE TABLE "category"
(
    "id"            uuid PRIMARY KEY,
    "name"          varchar,
    "description"   varchar,
    "is_delete"     bool,
    "last_modified" timestamp,
    "created_at"    timestamp
);

CREATE TABLE "brand"
(
    "id"            uuid PRIMARY KEY,
    "name"          varchar,
    "description"   varchar,
    "is_delete"     bool,
    "last_modified" timestamp,
    "created_at"    timestamp
);

CREATE TABLE "product"
(
    "id"            uuid PRIMARY KEY,
    "name"          varchar,
    "brand_id"      uuid,
    "category_id"   uuid,
    "gender"        varchar,
    "description"   varchar,
    "thumbnail"     varchar,
    "is_delete"     bool,
    "last_modified" timestamp,
    "created_at"    timestamp
);

CREATE TABLE "price_history"
(
    "id"            uuid PRIMARY KEY,
    "product_id"    uuid,
    "price"         float,
    "last_modified" timestamp,
    "is_delete"     bool,
    "created_at"    timestamp
);

CREATE TABLE "product_media"
(
    "id"            uuid PRIMARY KEY,
    "product_id"    uuid,
    "resource"      varchar,
    "media_type"    varchar,
    "media_info"    varchar,
    "is_delete"     bool,
    "last_modified" timestamp,
    "created_at"    timestamp
);

CREATE TABLE "review"
(
    "id"            uuid PRIMARY KEY,
    "user_id"       uuid,
    "product_id"    uuid,
    "rating"        int,
    "comment"       varchar,
    "is_delete"     bool,
    "last_modified" timestamp,
    "created_at"    timestamp
);

CREATE TABLE "review_media"
(
    "id"            uuid PRIMARY KEY,
    "review_id"     uuid,
    "resource"      varchar,
    "media_type"    varchar,
    "is_delete"     bool,
    "last_modified" timestamp,
    "created_at"    timestamp
);

ALTER TABLE "product"
    ADD FOREIGN KEY ("category_id") REFERENCES "category" ("id");

ALTER TABLE "product"
    ADD FOREIGN KEY ("brand_id") REFERENCES "brand" ("id");

ALTER TABLE "product_media"
    ADD FOREIGN KEY ("product_id") REFERENCES "product" ("id");

ALTER TABLE "review_media"
    ADD FOREIGN KEY ("review_id") REFERENCES "review" ("id");

ALTER TABLE "review"
    ADD FOREIGN KEY ("product_id") REFERENCES "product" ("id");

ALTER TABLE "price_history"
    ADD FOREIGN KEY ("product_id") REFERENCES "product" ("id");
