<!--Frontend File Structure
recipe-vault-frontend/
├── src/
│   ├── App.vue              ← Main UI layout - in which components are imported
│   ├── recipes.js               ← Backend connector
│   └── components/
│       ├── RecipeList.vue   ← Component to show recipes (GET)
│       └── AddRecipe.vue    ← Component to add recipes (POST)
-->

<!--App.vue as the main container, homepage-->

<template>
  <el-container style="min-height: 100vh;">
    <el-header style="background-color: #409EFF; color: white; padding: 1rem;">
      <el-menu mode="horizontal" :default-active="$route.path" router>
        <el-menu-item index="/">Recipe List</el-menu-item>
        <el-menu-item index="/add">Add Recipe</el-menu-item>
      </el-menu>
    </el-header>

    <el-main style="padding: 2rem;">
      <router-view />
    </el-main>
  </el-container>
</template>

<script>
export default {
  name: 'App'
};
</script>
