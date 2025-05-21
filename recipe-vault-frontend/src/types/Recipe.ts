export interface Recipe {
  id: number;
  title: string;
  instructions: string;
  difficulty: 'EASY' | 'MEDIUM' | 'HARD';
  creatorName: string;
  createdDate: string;
  ingredients: string[];
  imageUrl?: string;
}

export interface RecipeCreateDTO {
  title: string;
  instructions: string;
  difficulty: string;  // "EASY" | "MEDIUM" | "HARD"
  creatorName: string;
  ingredients: string[];
}

export interface RecipeResponseDTO extends RecipeCreateDTO {
  id: number;
  createdDate: string;
  imageUrl?: string;
}
