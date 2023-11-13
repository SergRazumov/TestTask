-- create table records

CREATE TABLE records(id SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    start_time TIMESTAMP NOT NULL,
    id_services INT NOT NULL,
    end_time TIMESTAMP NOT NULL,
    CONSTRAINT fk_category_goods FOREIGN KEY (id_services) REFERENCES services (id) on delete cascade);