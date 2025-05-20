<!--Frontend File Structure
recipe-vault-frontend/
├── src/
│   ├── App.vue              ← Main UI layout - in which components are imported
│   ├── api.js               ← Backend connector
│   └── components/
│       ├── RecipeList.vue   ← Component to show recipes (GET)
│       └── AddRecipe.vue    ← Component to add recipes (POST)
-->

<!--App.vue as the main container, homepage-->

<!--UI (HTML like)-->
<template>
  <div id="app">
    <h1>Recipe Vault</h1>
    <AddRecipe /> <!--place where AddRecipe.vue will be rendered-->
    <RecipeList /> <!--place where RecipeList.vue will be rendered-->
  </div>
</template>

<!--Declare logic, imports and components.
Even tho this section comes behind template section, Vue loads this <script> part first.
RecipeList is returned object from RecipeList.vue, defined by the export section;
AddRecipe is similar-->
<script>
import RecipeList from './components/RecipeList.vue';
import AddRecipe from './components/AddRecipe.vue';

export default {
  components: { //this step is essential to register child components (RecipeList and AddRecipe)
    RecipeList,
    AddRecipe
  }
};
</script>

