package com.recipevault.service;

import com.recipevault.dto.RecipeCreateDTO;
import com.recipevault.dto.RecipeResponseDTO;
import org.springframework.data.domain.Page;

public interface RecipeService {
    RecipeResponseDTO createRecipe(RecipeCreateDTO dto);
    RecipeResponseDTO getRecipeById(Long id);
    Page<RecipeResponseDTO> getPagedRecipes(int page, int size);

}
