/* This is the API helper, for communication
It is solely for sending and receiving data, not involved in UI
All components can just import and use the functions here - getARecipes(), createRecipe(), updateRecipe(), deleteRecipe()
*/

import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:8080', // Match Spring Boot backend
  headers: {
    'Content-Type': 'application/json',
  },
});

// Fetch all recipes (with optional pagination/search params)
export const getRecipes = (params = {}) => {
  return apiClient.get('/recipes', { params });
};

// Add a new recipe
export const createRecipe = (recipeData) => {
  return apiClient.post('/recipes', recipeData);
};

// Update an existing recipe
export const updateRecipe = (id, recipeData) => {
  return apiClient.put(`/recipes/${id}`, recipeData);
};

// Delete a recipe
export const deleteRecipe = (id) => {
  return apiClient.delete(`/recipes/${id}`);
};

