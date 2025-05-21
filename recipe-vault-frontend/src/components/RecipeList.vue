<!--RecipeList.vue
This component loads recipes from the backend (GET /recipes)
Shows them in a list
Let user delete (DELETE /recipes/:id)
-->

<template>
  <div>
    <el-table :data="recipes" style="width: 100%" stripe>
      <el-table-column prop="title" label="Title" />
      <el-table-column prop="difficulty" label="Difficulty" />
      <el-table-column prop="creatorName" label="Creator" />
      <el-table-column label="Ingredient Count">
        <template #default="scope">
          {{ scope.row.ingredients?.length ?? 0 }}
        </template>
      </el-table-column>
      <el-table-column prop="createdDate" label="Created">
        <template #default="scope">
          {{ formatDate(scope.row.createdDate) }}
        </template>
      </el-table-column>
      <el-table-column label="Actions" width="160">
        <template #default="scope">
          <div style="display: flex; gap: 8px"></div>
          <el-button
            type="primary"
            size="small"
            @click="$router.push(`/edit/${scope.row.id}`)"
          >
            <el-icon><Edit /></el-icon>
            Edit
          </el-button>
          <el-button
            type="danger"
            size="small"
            @click="confirmDelete(scope.row.id)"
          >
            Delete
          </el-button>
        </template>
      </el-table-column>

    </el-table>

    <el-pagination
      v-model:current-page="currentPage"
      :page-size="pageSize"
      :total="total"
      layout="prev, pager, next"
      background
      @current-change="fetchRecipes"
      style="margin-top: 20px; text-align: right"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { getRecipes } from '@/api/recipes';
import type { Recipe } from '@/types/Recipe';
import { deleteRecipe } from '@/api/recipes';
import { ElMessageBox, ElMessage } from 'element-plus';
import { Edit } from '@element-plus/icons-vue';


const recipes = ref<Recipe[]>([]);
const currentPage = ref(1);
const pageSize = 5;
const total = ref(0);

const fetchRecipes = async () => {

  try {
    const params = { page: currentPage.value - 1, size: pageSize };
    const response = await getRecipes(params);
    const data = response.data;

    if (Array.isArray(data)) {
      recipes.value = data;
      total.value = data.length;
    } else if (data?.content) {
      recipes.value = data.content;
      total.value = data.totalElements ?? data.content.length;
    } else {
      console.warn('Unexpected API response shape:', data);
    }
  } catch (error) {
    console.error('Failed to fetch recipes:', error);
  }

};

const confirmDelete = (id: number) => {
  ElMessageBox.confirm(
    'Are you sure you want to delete this recipe?',
    'Warning',
    {
      confirmButtonText: 'Yes',
      cancelButtonText: 'Cancel',
      type: 'warning',
    }
  )
    .then(async () => {
      await deleteRecipe(id);
      ElMessage.success('Recipe deleted');
      await fetchRecipes(); // Refresh list
    })
    .catch(() => {
      // User cancelled
    });
};

const formatDate = (datetime: string) => {
  return new Date(datetime).toLocaleString();
};

onMounted(fetchRecipes);
</script>
