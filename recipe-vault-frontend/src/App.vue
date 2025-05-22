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
    <!-- Sidebar -->
    <el-aside width="220px" style="background-color: #2d3a4b; color: white;">
      <el-menu
        :default-active="$route.path"
        router
        class="el-menu-vertical-demo"
        background-color="#2d3a4b"
        text-color="#fff"
        active-text-color="#409EFF"
      >
        <el-menu-item index="/">
          <el-icon><List /></el-icon>
          <span>Recipe List</span>
        </el-menu-item>
        <el-menu-item index="/add">
          <el-icon><Plus /></el-icon>
          <span>Add Recipe</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header
        height="60px"
        style="background: linear-gradient(to right, #c9dc5c, #a3c73d, #5ea443); color: #ffffff; display: flex; align-items: center; padding-left: 20px;"
      >
        <img src="/olive-icon.png" alt="Olive Logo" style="height: 40px; margin-right: 12px;" />
        <h2 style="margin: 0;">Recipe Vault Dashboard</h2>
      </el-header>

      <el-main style="padding: 40px; background-color: #f5f7fa;">
        <router-view />
      </el-main>

      <el-footer height="40px" style="text-align: center; color: #999;">
        © 2025 Recipe Vault
      </el-footer>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref } from 'vue';
import RecipeList from './components/RecipeList.vue';
import AddRecipe from './components/AddRecipe.vue';
import { List, Plus } from '@element-plus/icons-vue';

const activeTab = ref('list');

function handleMenuClick(index) {
  activeTab.value = index;
}
</script>

<style>
body {
  margin: 0;
  font-family: 'Roboto', sans-serif;
  background-color: #0e101a;
  color: #d9e4ff;
}

.el-card {
  background-color: #1e1e2f;
  border: 1px solid #2c2c3e;
  color: #c5d9ff;
}

.el-button--primary {
  background-color: #00bcd4;
  border-color: #00bcd4;
}

.el-input__inner,
.el-select .el-input__inner {
  background-color: #2b2b3b;
  border-color: #444;
  color: #fff;
}

.el-table th {
  background-color: #1b1b2b;
  color: #a8dadc;
}

.el-pagination {
  --el-color-primary: #00bcd4;
}

.el-form-item__label {
  color: #90caf9;
}

</style>
