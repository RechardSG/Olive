<template>
  <el-form
    :model="form"
    label-width="120px"
    style="max-width: 600px; margin: 0 auto"
  >
    <el-form-item label="Title">
      <el-input v-model="form.title" />
    </el-form-item>

    <el-form-item label="Difficulty">
      <el-select v-model="form.difficulty" placeholder="Select difficulty">
        <el-option label="Easy" value="EASY" />
        <el-option label="Medium" value="MEDIUM" />
        <el-option label="Hard" value="HARD" />
      </el-select>
    </el-form-item>

    <el-form-item label="Creator">
      <el-input v-model="form.creatorName" />
    </el-form-item>

    <el-form-item label="Instructions">
      <el-input type="textarea" v-model="form.instructions" :rows="4" />
    </el-form-item>

    <el-form-item label="Image URL">
      <el-input v-model="form.imageUrl" placeholder="Optional" />
    </el-form-item>

    <el-form-item label="Ingredients">
      <div
        v-for="(ingredient, index) in form.ingredients"
        :key="index"
        style="display: flex; gap: 8px; margin-bottom: 8px"
      >
        <el-input
          v-model="form.ingredients[index]"
          placeholder="Ingredient name"
          style="flex: 1"
        />
        <el-button type="danger" @click="removeIngredient(index)">Remove</el-button>
      </div>
      <el-button type="primary" @click="addIngredient">+ Add Ingredient</el-button>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="handleSubmit">Update</el-button>
      <el-button @click="resetForm">Reset</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup lang="ts">
import { reactive, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { getRecipeById, updateRecipe } from '@/api/recipes';
import { ElMessage } from 'element-plus';

const route = useRoute();
const router = useRouter();
const recipeId = Number(route.params.id);

const form = reactive({
  title: '',
  difficulty: '',
  creatorName: '',
  instructions: '',
  imageUrl: '',
  ingredients: [],
});

onMounted(async () => {
  try {
    const response = await getRecipeById(recipeId);
    const recipe = response.data;
    form.title = recipe.title;
    form.difficulty = recipe.difficulty;
    form.creatorName = recipe.creatorName;
    form.instructions = recipe.instructions;
    form.imageUrl = recipe.imageUrl;
    form.ingredients = recipe.ingredients || [];
  } catch (error) {
    ElMessage.error('Failed to load recipe.');
  }
});

const addIngredient = () => {
  form.ingredients.push('');
};

const removeIngredient = (index: number) => {
  form.ingredients.splice(index, 1);
};

const handleSubmit = async () => {
  try {
    await updateRecipe(recipeId, form);
    ElMessage.success('Recipe updated successfully!');
    router.push('/');
  } catch (error) {
    console.error(error);
    ElMessage.error('Failed to update recipe.');
  }
};

const resetForm = () => {
  // Just re-fetch from backend if needed, or clear fields
  form.title = '';
  form.difficulty = '';
  form.creatorName = '';
  form.instructions = '';
  form.imageUrl = '';
  form.ingredients = [];
};
</script>
