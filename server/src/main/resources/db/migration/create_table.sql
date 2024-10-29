CREATE TABLE Clients
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name  VARCHAR(100) NOT NULL,
    status     VARCHAR(100) NOT NULL,
    category   VARCHAR(100) NOT NULL,
    created_at DATE         NOT NULL DEFAULT CURRENT_DATE
);
