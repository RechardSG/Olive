import { createRouter, createWebHistory } from 'vue-router';
import RecipeList from '@/components/RecipeList.vue';
import AddRecipe from '@/components/AddRecipe.vue';
import EditRecipe from '@/components/EditRecipe.vue';


const routes = [
  { path: '/', name: 'Home', component: RecipeList },
  { path: '/add', name: 'AddRecipe', component: AddRecipe },
  { path: '/edit/:id', component: EditRecipe },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;

