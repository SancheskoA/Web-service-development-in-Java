CREATE TABLE cars (
    id SERIAL PRIMARY KEY,
    make VARCHAR(50),
    model VARCHAR(50),
    year INT,
    color VARCHAR(30),
    mileage INT
);

INSERT INTO cars (make, model, year, color, mileage) VALUES
('Toyota', 'Camry', 2020, 'White', 15000),
('Honda', 'Civic', 2019, 'Black', 20000),
('Ford', 'Mustang', 2021, 'Red', 5000),
('Chevrolet', 'Malibu', 2018, 'Blue', 30000),
('Nissan', 'Altima', 2020, 'Silver', 25000),
('Hyundai', 'Elantra', 2019, 'Gray', 18000),
('Kia', 'Optima', 2021, 'Green', 12000),
('Subaru', 'Impreza', 2020, 'Yellow', 15000),
('Volkswagen', 'Jetta', 2018, 'Brown', 35000),
('Mazda', '3', 2019, 'Orange', 22000);
