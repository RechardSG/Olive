<!--RecipeList.vue
This component loads recipes from the backend (GET /recipes)
Shows them in a list
Let user delete (DELETE /recipes/:id)
-->


<template>
  <div>
    <h2>All Recipes</h2>
    <ul>
      <li v-for="recipe in recipes" :key="recipe.id"> <!--v-for loops thry the recipe array and render in UI-->
        {{ recipe.title }} - {{ recipe.difficulty }}
        <button @click="remove(recipe.id)">Delete</button>
      </li>
    </ul>
  </div>
</template>

<script>
import { getAllRecipes, deleteRecipe } from '../api.js';

export default { //this whole object is the default export of this RecipeList.vue
  data() { //data(), as a function, returns variable recipe. Each component instance gets new variable.
    return {
      recipes: []  //this is like python's key:value. recipes will be array of data.
    };
  },

  watch (){
    
  }
  async mounted() { //mounted() automatically runs after the component appears in browser, where component added to DOM
    this.recipes = await getAllRecipes(); //await is to wait for API to finish before assign result
  },
  methods: { //methods: defines logic to load data
    async remove(id) {
      await deleteRecipe(id);
      this.recipes = await getAllRecipes(); // refresh list
    }
  }
};
</script>
