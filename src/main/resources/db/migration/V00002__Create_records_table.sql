-- create table records

CREATE TABLE records(id INT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    start_time TIME NOT NULL,
    id_services INT NOT NULL,
    CONSTRAINT fk_category_goods FOREIGN KEY (id_services) REFERENCES services (id));