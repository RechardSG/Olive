/*Controller handles HTTP requests - CRUD, in HTTP format (header with content)
*
* */

package com.recipevault.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeRepository recipeRepository;

    // Inject the repository
    @Autowired
    public RecipeController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    // GET /recipes
    @GetMapping("/paged")
    public Page<Recipe> getPagedRecipes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Recipe> pagedRecipes = recipeRepository.findAll(pageable);

        // Force ingredients to load (to avoid lazy loading issues)
        pagedRecipes.getContent().forEach(recipe -> recipe.getIngredients().size());

        return pagedRecipes;
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Recipe>> filterRecipes(
            @RequestParam(required = false) String difficulty,
            @RequestParam(required = false) Integer ingredientCount) {

        Difficulty parsedDifficulty = null;

        try {
            if (difficulty != null && !difficulty.isEmpty()) {
                parsedDifficulty = Difficulty.valueOf(difficulty.toUpperCase());
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }

        List<Recipe> results = recipeRepository.findByDifficultyAndIngredientCount(parsedDifficulty, ingredientCount);
        results.forEach(r -> r.getIngredients().size()); // Load ingredients

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Recipe>> searchByTitle(@RequestParam String title) {
        List<Recipe> results = recipeRepository.findByTitleContainingIgnoreCase(title);
        results.forEach(r -> r.getIngredients().size()); // load ingredients
        return new ResponseEntity<>(results, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        return recipeRepository.findById(id)
                .map(recipe -> {
                    // Force ingredients to load (important if FetchType.LAZY)
                    recipe.getIngredients().size();
                    return new ResponseEntity<>(recipe, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("")
    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();

        // Force ingredients to load (since FetchType.LAZY is default)
        for (Recipe recipe : recipes) {
            recipe.getIngredients().size();  // triggers loading
        }

        return recipes;
    }

    // POST /recipes
    @PostMapping("")
    public ResponseEntity<Recipe> createRecipe(@RequestBody Map<String, Object> body) {
        // 1. Parse basic recipe fields
        String title = (String) body.get("title");
        String instructions = (String) body.get("instructions");
        String difficultyStr = (String) body.get("difficulty");
        String creatorName = (String) body.get("creatorName");

        Difficulty difficulty = Difficulty.valueOf(difficultyStr);

        // 2. Create recipe object
        Recipe recipe = new Recipe(title, instructions, difficulty, creatorName);

        // 3. Parse ingredients (if any)
        List<String> ingredientNames = (List<String>) body.get("ingredients");
        if (ingredientNames != null) {
            List<Ingredient> ingredients = new ArrayList<>();
            for (String name : ingredientNames) {
                ingredients.add(new Ingredient(name, recipe));
            }
            recipe.setIngredients(ingredients);
        }

        // 4. Save and return
        Recipe saved = recipeRepository.save(recipe);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // PUT /recipes
    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody Recipe updatedRecipe) {
        return recipeRepository.findById(id)
                .map(recipe -> {
                    recipe.setTitle(updatedRecipe.getTitle());
                    recipe.setDifficulty(updatedRecipe.getDifficulty());
                    recipe.setInstructions(updatedRecipe.getInstructions());
                    recipe.setImageUrl(updatedRecipe.getImageUrl());
                    recipe.setCreatorName(updatedRecipe.getCreatorName());
                    return new ResponseEntity<>(recipeRepository.save(recipe), HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable Long id) {
        if (recipeRepository.existsById(id)) {
            recipeRepository.deleteById(id);
            return new ResponseEntity<>("Recipe deleted successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Recipe not found.", HttpStatus.NOT_FOUND);
        }
    }

}

