CREATE TABLE IF NOT EXISTS td_cars (
    id INT PRIMARY KEY AUTO_INCREMENT,
    model VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    daily_rate number NOT NULL,
    is_available BOOLEAN DEFAULT TRUE,
    is_active BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS td_clients (
    id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    address VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    has_accidents BOOLEAN DEFAULT FALSE,
    is_active BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS td_offers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    client_id INT,
    car_id INT,
    rental_start VARCHAR(20) NOT NULL,
    rental_end VARCHAR(20) NOT NULL,
    total_price DECIMAL(10, 2),
    accepted BOOLEAN DEFAULT FALSE,
    is_active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (client_id) REFERENCES td_clients(id),
    FOREIGN KEY (car_id) REFERENCES td_cars(id)
);