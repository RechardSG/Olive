import { createRouter, createWebHistory } from 'vue-router';
import RecipeList from '@/components/RecipeList.vue';
import AddRecipe from '@/components/AddRecipe.vue';
import EditRecipe from '@/components/EditRecipe.vue';
import RecipeDetail from '@/pages/RecipeDetail.vue';

const routes = [
  { path: '/', component: RecipeList },
  { path: '/add', component: AddRecipe },
  { path: '/edit/:id', component: EditRecipe },
  { path: '/recipes/:id', component: RecipeDetail },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;

