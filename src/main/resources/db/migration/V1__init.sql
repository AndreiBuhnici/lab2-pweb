CREATE TABLE user_entity (
                       id UUID PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP NOT NULL,
                       updated_at TIMESTAMP NOT NULL
);


CREATE TABLE user_file (
                       id UUID PRIMARY KEY,
                       path VARCHAR(255) NOT NULL,
                       name VARCHAR(255) NOT NULL,
                       description VARCHAR(4095),
                       userId UUID NOT NULL,
                       created_at TIMESTAMP NOT NULL,
                       updated_at TIMESTAMP NOT NULL
);