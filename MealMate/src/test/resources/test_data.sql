USE mealmateDB_test;

DELETE FROM user;
INSERT INTO user(userId, firstName, lastName, password) VALUES(1,"James", "Smith","password");

DELETE FROM ingredient;
INSERT INTO ingredient(ingredientid, ingredientName) values(1, "chickpea");
INSERT INTO ingredient(ingredientid, ingredientName) values(2, "lemon");
INSERT INTO ingredient(ingredientid, ingredientName) values(3, "potato");
INSERT INTO ingredient(ingredientid, ingredientName) values(4, "corn");
INSERT INTO ingredient(ingredientid, ingredientName) values(5, "spinach");
INSERT INTO ingredient(ingredientid, ingredientName) values(6, "chocolate");
INSERT INTO ingredient(ingredientid, ingredientName) values(7, "tofu");
INSERT INTO ingredient(ingredientid, ingredientName) values(8, "tempeh");
INSERT INTO ingredient(ingredientid, ingredientName) values(9, "orange");
INSERT INTO ingredient(ingredientid, ingredientName) values(10,"watermelon");

DELETE FROM recipe;
INSERT INTO recipe(recipeId, title, userId) VALUES(1, "fruit salad", 1);
INSERT INTO recipe(recipeId, title, userId) VALUES(2, "soup", 1);

DELETE FROM recipeIngredient;
INSERT INTO recipeIngredient(recipeId, ingredientId, quantity, unit) VALUES(1, 9, 1, "cup");
INSERT INTO recipeIngredient(recipeId, ingredientId, quantity, unit) VALUES(1, 10, 2, "cup");


INSERT INTO recipeIngredient(recipeId, ingredientId, quantity, unit) VALUES(2, 1, 1, "cup");
INSERT INTO recipeIngredient(recipeId, ingredientId, quantity, unit) VALUES(2, 2, 500, "g");
