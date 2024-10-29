package com.mealmate.mealmate.controller;


import com.mealmate.mealmate.dao.RecipeNotFoundException;
import com.mealmate.mealmate.dao.RecipeTitleEmptyException;
import com.mealmate.mealmate.dao.ZeroRowsAffectedException;
import com.mealmate.mealmate.dao.MismatchIdException;
import com.mealmate.mealmate.dto.Ingredient;
import com.mealmate.mealmate.dto.Recipe;
import com.mealmate.mealmate.service.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private static final Logger log = LoggerFactory.getLogger(RecipeController.class);

    @Autowired
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{recipeId}")
    public ResponseEntity<Recipe> getRecipeByRecipeId(
            @PathVariable int recipeId,
            @RequestParam Integer userId) {
        Recipe result = recipeService.findRecipeById(recipeId);
        if (result.getUserId() != userId) {
            return new ResponseEntity(null, HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public List<Recipe> getAllRecipe(@RequestParam Integer userId) {
        return recipeService.getAllRecipes(userId);
    }

    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
        try {
            return ResponseEntity.ok(recipeService.addRecipe(recipe));
        } catch (RecipeTitleEmptyException e) {
            return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PutMapping("/{recipeId}")
    public ResponseEntity updateRecipe(@PathVariable int recipeId, @RequestBody Recipe recipe) {
        try {
            recipeService.updateRecipe(recipeId, recipe);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (MismatchIdException e) {
            return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (RecipeNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (DataAccessException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<?> deleteRecipe(@PathVariable int recipeId) {
        recipeService.deleteRecipe(recipeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ExceptionHandler(RecipeTitleEmptyException.class)
    public final ResponseEntity<Error> handleRecipeTitleEmptyException(
            RecipeTitleEmptyException e, WebRequest request) {
        Error err = new Error();
        err.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
    }

    @ExceptionHandler(RecipeNotFoundException.class)
    public final ResponseEntity<Error> handleRecipeNotFoundException(
            RecipeNotFoundException e, WebRequest request) {
        Error err = new Error();
        err.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public final ResponseEntity<Error> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e, WebRequest request) {
        Error err = new Error();
        err.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Error> handleUnknownException(
            Exception e, WebRequest request) {
        log.error("An unhandled error occurred", e);
        Error err = new Error();
        err.setMessage("Something went wrong. Internal server error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }
}
