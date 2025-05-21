<template>
  <div class="recipe-detail" style="max-width: 600px; margin: 0 auto">
    <el-card v-if="recipe" shadow="always">
      <h2>{{ recipe.title }}</h2>

      <p><strong>Difficulty:</strong> {{ recipe.difficulty }}</p>
      <p><strong>Creator:</strong> {{ recipe.creatorName }}</p>
      <p><strong>Created On:</strong> {{ new Date(recipe.createdDate).toLocaleString() }}</p>

      <div v-if="recipe.imageUrl" style="margin: 16px 0">
        <img :src="recipe.imageUrl" alt="Recipe Image" style="max-width: 100%; border-radius: 8px" />
      </div>

      <p><strong>Instructions:</strong></p>
      <el-card style="margin-bottom: 16px; background: #f9f9f9">{{ recipe.instructions }}</el-card>

      <p><strong>Ingredients ({{ recipe.ingredients.length }}):</strong></p>
      <ul>
        <li v-for="(ingredient, index) in recipe.ingredients" :key="index"> {{ ingredient }}</li>
      </ul>

      <el-button type="primary" style="margin-top: 16px" @click="goBack">Back</el-button>
    </el-card>

    <el-empty v-else description="Recipe not found" />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { getRecipeById } from '@/api/recipes';
import { ElMessage } from 'element-plus';

const route = useRoute();
const router = useRouter();
const recipeId = Number(route.params.id);
const recipe = ref<any | null>(null);

onMounted(async () => {
  try {
    const response = await getRecipeById(recipeId);
    recipe.value = response.data;
  } catch (error) {
    console.error('Failed to load recipe:', error);
    ElMessage.error('Recipe not found.');
  }
});

const goBack = () => {
  router.push('/');
};
</script>

<style scoped>
ul {
  padding-left: 16px;
}
li {
  margin-bottom: 4px;
}
</style>
