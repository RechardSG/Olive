<!--AddRecipe, the form component
Content of form fields
Upon Submit, it calls addRecipe()
Sends a POST request to backend
-->

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

    <!-- Ingredient Inputs -->
    <el-form-item label="Ingredients">
      <div
        v-for="(ingredient, index) in form.ingredients"
        :key="index"
        style="display: flex; gap: 8px; margin-bottom: 8px;"
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
      <el-button type="primary" @click="handleSubmit">Submit</el-button>
      <el-button @click="resetForm">Reset</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup>
import { reactive } from 'vue';
import { createRecipe } from '@/api/recipes'; 
import { ElMessage } from 'element-plus';

const form = reactive({
  title: '',
  difficulty: '',
  creatorName: '',
  instructions: '',
  imageUrl: '',
  ingredients: [],
});

const addIngredient = () => {
  form.ingredients.push('');
};

const removeIngredient = (index) => {
  form.ingredients.splice(index, 1);
};

const handleSubmit = async () => {
  if (!form.title || !form.difficulty || !form.creatorName) {
    ElMessage.warning('Please fill in required fields.');
    return;
  }

  form.ingredients = form.ingredients.filter(i => i.trim() !== '');

  try {
    await createRecipe(form);
    ElMessage.success('Recipe added successfully!');
    resetForm();
  } catch (error) {
    console.error('Error adding recipe:', error);
    ElMessage.error('Failed to add recipe.');
  }
};

const resetForm = () => {
  form.title = '';
  form.difficulty = '';
  form.creatorName = '';
  form.instructions = '';
  form.imageUrl = '';
  form.ingredients = [];
};
</script>
