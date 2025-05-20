<!--RecipeList.vue
This component loads recipes from the backend (GET /recipes)
Shows them in a list
Let user delete (DELETE /recipes/:id)
-->

<template>
  <div>
    <el-input
      v-model="searchText"
      placeholder="Search by title"
      clearable
      @input="fetchRecipes"
      style="margin-bottom: 16px; width: 300px"
    />

    <el-table :data="recipes" stripe style="width: 100%">
      <el-table-column prop="title" label="Title" />
      <el-table-column prop="difficulty" label="Difficulty" />
      <el-table-column prop="creatorName" label="Creator" />
      <el-table-column label="# Ingredients">
        <template #default="scope">
          {{ scope.row.ingredients?.length || 0 }}
        </template>
      </el-table-column>
      <el-table-column prop="createdDate" label="Created">
        <template #default="scope">
          {{ formatDate(scope.row.createdDate) }}
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      v-model:current-page="currentPage"
      :page-size="pageSize"
      :total="totalRecipes"
      layout="prev, pager, next"
      background
      @current-change="fetchRecipes"
      style="margin-top: 20px; text-align: right"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getRecipes } from '@/api/recipes';

const recipes = ref([]);
const currentPage = ref(1);
const pageSize = 5; // change as needed
const totalRecipes = ref(0);
const searchText = ref('');

const fetchRecipes = async () => {
  try {
    const params = {
      page: currentPage.value,
      size: pageSize,
      title: searchText.value || undefined, // if supported by backend
    };
    const response = await getRecipes(params);
    recipes.value = response.data.content || response.data; // support for paginated and non-paginated
    totalRecipes.value = response.data.totalElements || response.data.length || 0;
  } catch (err) {
    console.error('Failed to fetch recipes:', err);
  }
};

const formatDate = (datetime) => {
  return new Date(datetime).toLocaleString();
};

onMounted(fetchRecipes);
</script>
