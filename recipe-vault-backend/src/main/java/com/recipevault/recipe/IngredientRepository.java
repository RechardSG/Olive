package com.recipevault.recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import com.recipevault.recipe.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}

