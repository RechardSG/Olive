
<!--AddRecipe, the form component
Content of form fields
Upon Submit, it calls addRecipe() in api.js
Sends a POST request to backend
-->
<template>
  <div>
    <h2>Add a New Recipe</h2>
    <form @submit.prevent="submit"> <!--submit event listener, call submit() method from <script> section-->
      <input v-model="title" placeholder="Title" required /> <!--v-model binds input to this.title in data(), title will update automatically-->

      <select v-model="difficulty" required> <!--selete means dropdown. v-model binds select to this.difficulty in data()-->
        <option disabled value="">Select difficulty</option> <!--disabled value ="" is placeholder-->
        <option value="EASY">Easy</option>
        <option value="MEDIUM">Medium</option>
        <option value="HARD">Hard</option>
      </select>

      <textarea v-model="instructions" placeholder="Instructions" required></textarea>

      <input v-model="creatorName" placeholder="Creator Name" required />

      <button type="submit">Add Recipe</button>
    </form>
  </div>
</template>

<script>
import { addRecipe } from '../api.js'; //addRecipe() method sends POST request to backend

export default {
  data() { //define form's reactive variables, which are linked via v-model, defined in <template> section
    return {
      title: '',
      difficulty: '',
      instructions: '',
      creatorName: ''
    };
  },
  methods: {
    async submit() {
      if (
        !this.title.trim() || //custom validation - as compared with HTML validation (required) used in <template> section
        !this.difficulty ||
        !this.instructions.trim() ||
        !this.creatorName.trim()
      ) {
        alert("Please fill in all fields");
        return;
      }

      await addRecipe({ //await ensures the call complets before continuing
        title: this.title,
        difficulty: this.difficulty,
        instructions: this.instructions,
        creatorName: this.creatorName,
        imageUrl: null // optional
      });

      alert('Recipe added!');
      this.title = '';
      this.difficulty = '';
      this.instructions = '';
      this.creatorName = '';
    }
  }
};
</script>
