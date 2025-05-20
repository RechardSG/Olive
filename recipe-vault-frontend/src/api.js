/* This is the API helper, for communication
It is solely for sending and receiving data, not involved in UI
All components can just import and use the functions here - getAllRecipes(), addRecipe() and deleteRecipe()
*/

const API_BASE = 'http://localhost:8080';

export async function getAllRecipes() { //export is to make this function available for components
  const res = await fetch(`${API_BASE}/recipes`); //fetch(8080/recipes) is to send GET request to Spring Boot endpoint
  return await res.json(); //res.json() converts response (in json) into js data
}

export async function addRecipe(recipe) {
  const res = await fetch(`${API_BASE}/recipes`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' }, //to inform spring boot the request body is in json
    body: JSON.stringify(recipe) //converts js data into json
  });
  return await res.json();
}

export async function deleteRecipe(id) {
  await fetch(`${API_BASE}/recipes/${id}`, {
    method: 'DELETE'
  });
}
