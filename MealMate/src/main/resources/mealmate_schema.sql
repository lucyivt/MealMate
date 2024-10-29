-- Active: 1729064024953@@172.17.0.2@3306
DROP DATABASE IF EXISTS mealmate;

CREATE DATABASE mealmate;

USE mealmate;

CREATE TABLE user (
    userId INT AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE recipe (
    recipeId INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    url VARCHAR(50),
    summary VARCHAR(100),
    yield VARCHAR(50),
    time VARCHAR(50),
    img TEXT,
    userId INT NOT NULL,

    CONSTRAINT fk_recipe_user
        FOREIGN KEY (userId)
        REFERENCES user(userId)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE ingredient (
    ingredientId INT AUTO_INCREMENT PRIMARY KEY,
    ingredientName VARCHAR(50) NOT NULL
);

CREATE TABLE recipeIngredient (
    recipeId INT NOT NULL,
    ingredientId INT NOT NULL,
    quantity INT NOT NULL,
    unit VARCHAR(50) NOT NULL,

    CONSTRAINT pk_recipeIngredient
        PRIMARY KEY (recipeId, ingredientId),

    CONSTRAINT fk_recipeIngredient_recipe
        FOREIGN KEY (recipeId)
        REFERENCES recipe(recipeId)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_recipeIngredient_ingredient
        FOREIGN KEY (ingredientId)
        REFERENCES ingredient(ingredientId)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE nutrient (
    nutrientId INT AUTO_INCREMENT PRIMARY KEY,
    label VARCHAR(50) NOT NULL,
    quantity DOUBLE NOT NULL,
    unit VARCHAR(50) NOT NULL,
    recipeId INT NOT NULL,

    CONSTRAINT fk_nutrient_recipe
        FOREIGN KEY (recipeId)
        REFERENCES recipe(recipeId)
        ON DELETE CASCADE
        ON UPDATE CASCADE
)