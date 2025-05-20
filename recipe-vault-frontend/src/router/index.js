import { createRouter, createWebHistory } from 'vue-router';
import RecipeList from '@/components/RecipeList.vue';
import AddRecipe from '@/components/AddRecipe.vue';

const routes = [
  { path: '/', name: 'Home', component: RecipeList },
  { path: '/add', name: 'AddRecipe', component: AddRecipe }, // ✅ this line is key
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;

